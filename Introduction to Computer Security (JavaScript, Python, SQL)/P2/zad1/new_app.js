const fs = require('fs');
var path = require('path');
var cookieParser = require('cookie-parser');
var validator = require('validator')
var qrCode = require('qrcode');
const speakeasy = require('speakeasy')
const express = require('express');
const session = require('express-session');
const crypto = require('crypto');
const bodyParser = require('body-parser');
const mysql = require('mysql2');
const argon2 = require('argon2');
const { v4: uuidv4 } = require('uuid');
const app = express();
const passport = require('passport');
var userProfile;

app.use(cookieParser());
app.set('view engine', 'ejs');
app.set('views', (path.join(__dirname + '/views')));

app.use(express.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(session({
    secret: generateSecret(),
    resave: false,
    saveUninitialized: true
}));

app.use(passport.initialize());
app.use(passport.session());

// Połączenie z bazą danych
const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'XXXXXXXXXXXXX',
    database: 'bank_db',
    multipleStatements: true
});

db.connect((err) => {
    if (err) {
        throw err;
    }
    console.log('Połączono z bazą danych MySQL');
});

const checkIfLogged = (req, res, next) => {
    if (req.session && req.session.userId) {
        res.redirect('account');
    } else {
        next();
    }
};

const checkSession = (req, res, next) => {
    if (req.session && req.session.userId) {
        next();
    } else {
        res.redirect('login');
    }
};

const signUpValidate = (req, res, next) => {
    const { login, email, password, confirm_password } = req.body;
    try {
        db.query('SELECT * FROM users WHERE email = ?', [email], async (err, result) => {
            if (err) {
                res.status(500).send('Błąd podczas sprawdzania');
            } else if (result.length > 0) {
                res.render('register', { message: 'Istnieje już konto z takim adresem e-mail!' });
            }
        }
        )
    } catch (err) {
        console.error(err);
        res.status(500).send('Błąd przy sprawdzaniu rejestracji!');
    }

    if (!login || !email || !password || !confirm_password) {
        return res.render('register', { message: 'Nie wypełniono wszystkich pól!' });
    }

    else if (!validator.isAlphanumeric(login)) {
        return res.render('register', { message: 'W loginie mogą być tylko znaki alfanumeryczne!' });
    }

    else if (!validator.isEmail(email)) {
        return res.render('register', { message: 'Podano błędny e-mail!' });
    }

    else if (!validator.isStrongPassword(password)) {
        return res.render('register', { message: 'Hasło musi mieć przynajmniej 8 znaków, w tym co najmniej jedną małą literę, dużą literę, cyfrę i znak specjalny!' });
    }

    else if (password != confirm_password) {
        return res.render('register', { message: 'Hasła się nie zgadzają!' });
    }

    next();
}

const resetPasswordValidate = (req, res, next) => {
    const { password, confirm_password } = req.body;
    if (!password || !confirm_password) {
        res.render('reset-password', { message: 'lmao' });
    }

    else if (password != confirm_password) {
        return res.send({ message: 'Hasła się nie zgadzają!' });
    }

    else if (!validator.isStrongPassword(password)) {
        return res.status(400).send("Podaj mocniejsze hasło!")
    }

    next();
}

function generateSecret() {
    return crypto.randomBytes(32).toString('hex');
}

app.get('/success', (req, res) => res.send(userProfile));
app.get('/error', (req, res) => res.render('login', { message: 'Logowanie nieudane!' }));

app.get('/', checkIfLogged, async (req, res) => {
    res.render('index');
});

passport.serializeUser(function (user, cb) {
    cb(null, user);
});

passport.deserializeUser(function (obj, cb) {
    cb(null, obj);
});

app.get('/forgot-password', checkIfLogged, async (req, res) => {
    res.render('forgot-password', { message: undefined });
});

app.post('/forgot-password', async (req, res) => {
    try {
        const { email } = req.body;
        db.query('SELECT * FROM users WHERE email = ?', [email], async (err, result) => {
            if (err) {
                res.status(500).send('Błąd podczas sprawdzania');
            } else {
                if (result.length == 0) {
                    res.render('forgot-password', { message: 'Nie ma konta z takim adresem e-mail!' });
                }
                else {
                    db.query('INSERT INTO tokens (email, token) VALUES (?, ?)', [email, crypto.randomBytes(10).toString('hex')], async (err, result) => {
                        if (err) {
                            res.status(500).send('Bład przy wstawianiu do tabeli!');
                        } else {
                            res.render('forgot-password', { message: 'Link wygenerowany pomyślnie!!' });
                        }
                    })
                }
            }
        });
    } catch (err) {
        console.error(err);
        res.status(500).send('Błąd przy tworzeniu tokenu');
    }
})

app.get('/reset-password/:token', checkIfLogged, async (req, res) => {
    const { token } = req.params;
    req.session.param1 = token.toString();
    db.query('SELECT * FROM tokens WHERE token = ?', [token], async (err, result) => {
        if (err) {
            res.status(500).send('Błąd podczas szukania tokenu!');
        } else {
            if (result.length > 0) {
                const db_token = result[0];

                if (new Date() >= new Date(db_token.exp_date)) {
                    res.status(500).send('Link wygasł!');
                }

                else {
                    res.render('reset-password', { token: token, message: undefined });
                }
            }
            else {
                res.status(500).send('Zły link!');
            }
        }
    })
});

app.post('/reset-password', resetPasswordValidate, async (req, res) => {
    const { password, confirm_password } = req.body;
    const token = req.session.param1;

    const hashedPassword = await argon2.hash(password);
    try {
        db.query('UPDATE users SET password = ? WHERE email = (SELECT email FROM tokens WHERE token = ?)', [hashedPassword, token], async (err, result) => {
            if (err) {
                res.status(500).send('Błąd przy zmienianiu hasła!');
            } else {
                db.query('DELETE FROM tokens WHERE token = ?', [token], async (err, result) => {
                    if (err) {
                        res.status(401).send('Błąd przy usuwaniu tokenu!');
                    } else {
                        res.render('login', { message: 'Pomyślnie zmieniono hasło!' });
                    }
                })
            }
        })
    }
    catch (err) {
        console.error(err);
        res.status(500).send('Błąd podczas zmieniania hasła!');
    }


})

app.get('/login', checkIfLogged, async (req, res) => {
    res.render('login', { message: undefined });
});

app.post('/login', checkIfLogged, async (req, res) => {
    const { login, password } = req.body;

    try {
        db.query('SELECT * FROM users WHERE login = ?;', [login], async (err, result) => {
            if (err) {
                res.status(500).send('Błąd podczas logowania');
            } else {
                if (result.length > 0) {
                    const user = result[0];
                    if (user.password == 'google') {
                        res.render('login', { message: 'Konto powiązane z Googlem, zaloguj się w ten sposób!' })
                    }
                    const isValidPassword = await argon2.verify(user.password, password);

                    if (isValidPassword) {
                        try {
                            db.query('SELECT * FROM secrets WHERE email = ?', [user.email], async (err, result2) => {
                                if (err) {
                                    res.status(500).send('Błąd podczas pobierania danych!');
                                }
                                else if (result2.length > 0) {
                                    console.log("YIPPEE")
                                    res.cookie.user = user
                                    res.cookie.totp = result2[0].token;
                                    res.redirect('2fa-login');
                                }
                                else {
                                    req.session.userId = user;
                                    res.render('account', { user: req.session.userId.login, number: req.session.userId.account_nr, totp: false });
                                }
                            });
                        } catch (err) {
                            console.error(err);
                            res.status(500).send('Błąd podczas pobierania danych!');
                        }
                    } else {
                        console.log("A")
                        res.render('login', { message: 'Nieprawidłowy login lub hasło!' });
                    }
                } else {
                    console.log("B")
                    res.render('login', { message: 'Nieprawidłowy login lub hasło!' });
                }
            }
        });
    } catch (err) {
        console.error(err);
        res.status(500).send('Błąd podczas logowania');
    }
});

app.get('/2fa-login', async (req, res) => {
    res.render('2fa-login');
});

app.post('/2fa-login', async (req, res) => {
    const { kod } = req.body;
    const verified = speakeasy.totp.verify({
        secret: res.cookie.totp,
        encoding: 'base32',
        token: kod
    });

    if (verified) {
        console.log("TAK")
        req.session.userId = res.cookie.user;
        res.render('account', { user: req.session.userId.login, number: req.session.userId.account_nr, totp: true });
    }
    else {
        console.log("NIE")
        res.render('2fa-login');
    }
});

app.get('/register', checkIfLogged, async (req, res) => {
    res.render('register', { message: undefined });
});

app.post('/register', signUpValidate, async (req, res) => {
    try {
        const { login, email, password, confirm_password } = req.body;

        let isUnique = false;
        const hashedPassword = await argon2.hash(password);
        let accountNumber;

        while (!isUnique) {
            accountNumber = generateAccountNumber();
            const existingUser = await getUserByAccountNumber(accountNumber);
            if (!existingUser) {
                isUnique = true;
            }
        }

        const user = {
            login,
            email,
            password: hashedPassword,
            account_nr: accountNumber
        };

        db.query('INSERT INTO users SET ?', user, (err, result) => {
            if (err) {
                res.status(500).send('Błąd podczas rejestracji użytkownika');
            } else {
                res.render('login', { message: 'Użytkownik zarejestrowany pomyślnie!' });
            }
        });
    } catch (err) {
        console.error(err);
        res.status(500).send('Błąd podczas hashowania hasła');
    }
});

app.get('/account', checkSession, async (req, res) => {
    try {
        db.query('SELECT * FROM secrets WHERE email = ?', [req.session.userId.email], async (err, result) => {
            if (err) {
                res.status(500).send('Błąd podczas pobierania danych!');
            }
            else if (result.length > 0) {
                res.render('account', { user: req.session.userId.login, number: req.session.userId.account_nr, totp: true });
            }

            res.render('account', { user: req.session.userId.login, number: req.session.userId.account_nr, totp: false });
        });
    } catch (err) {
        console.error(err);
        res.status(500).send('Błąd podczas pobierania danych!');
    }
});

app.get('/transfer', checkSession, async (req, res) => {
    res.render('transfer');
});

app.post('/transfer', async (req, res) => {
    const { nr_rachunku, kwota, tytul } = req.body;
    res.cookie('nr_rachunku', nr_rachunku);
    res.cookie('kwota', kwota);
    res.cookie('tytul', tytul);
    res.redirect('/confirm');
});

app.get('/confirm', checkSession, async (req, res) => {
    res.render('confirm', { nr_rachunku: req.cookies.nr_rachunku, kwota: req.cookies.kwota, tytul: req.cookies.tytul });
});

app.post('/confirm', async (req, res) => {
    try {
        db.query('INSERT INTO transfers (sender_nr, receiver_nr, title, amount ) VALUES (?, ?, ?, ?)', [req.session.userId.account_nr, req.cookies.nr_rachunku, req.cookies.tytul, req.cookies.kwota], async (err, result) => {
            if (err) {
                console.error(err);
                res.status(500).send('Błąd przy wstawianiu do tabeli!');
            } else {
                res.redirect('/confirmation')
            }
        });
    } catch (err) {
        console.error(err);
        res.status(500).send('Błąd przy przetwarzaniu przelewu!');
    }
});

app.get('/confirmation', checkSession, async (req, res) => {
    res.render('confirmation', { nr_rachunku: req.cookies.nr_rachunku, kwota: req.cookies.kwota, tytul: req.cookies.tytul });
});

app.get('/logout', checkSession, async (req, res) => {
    req.session.destroy();
    res.render('index');
});

app.get('/history', checkSession, async (req, res) => {
    try {
        db.query('SELECT * FROM transfers WHERE sender_nr = ?', [req.session.userId.account_nr], async (err, result) => {
            if (err) {
                res.status(500).send('Błąd podczas pobierania danych!');
            }

            res.render('history', { data: result });
        });
    } catch (err) {
        console.error(err);
        res.status(500).send('Błąd podczas pobierania danych!');
    }
});

app.get('/2fa-on', checkSession, async (req, res) => {
    const totpsecret = speakeasy.generateSecret({ length: 20 }).base32;
    res.cookie.totpsecret = totpsecret;
    const code = "otpauth://totp/LegitBank?secret=" + totpsecret;
    qrCode.toDataURL(code, function (err, data_url) {
        res.render('2fa-on', { qr: data_url, totpsecret: totpsecret });
    })
})

app.post('/2fa-on', async (req, res) => {
    const { kod } = req.body;
    const verified = speakeasy.totp.verify({
        secret: res.cookie.totpsecret,
        encoding: 'base32',
        token: kod
    });

    if (verified) {
        console.log("AAAAAAAAAAAAAAAAAAAAA")
        try {
            db.query('INSERT INTO secrets (email, token) VALUES (?, ?)', [req.session.userId.email, res.cookie.totpsecret], async (err, result) => {
                if (err) {
                    res.status(500).send('Błąd podczas wstawiania danych!');
                }
                res.render('account', { user: req.session.userId.login, number: req.session.userId.account_nr, totp: true });
            });
        } catch (err) {
            console.error(err);
            res.status(500).send('Błąd podczas usuwania danych!');
        }
    }
    else {
        console.log("BBBBBBBBBBBBBBBBBB")
        res.render('2fa-on')
    }
});

app.get('/2fa-off', checkSession, async (req, res) => {
    res.render('2fa-off');
})

app.get('/confirm-2fa-off', checkSession, async (req, res) => {
    try {
        db.query('DELETE FROM secrets WHERE email = ?', [req.session.userId.email], async (err, result) => {
            if (err) {
                res.status(500).send('Błąd podczas usuwania danych!');
            }
            else {
                res.render('account', { user: req.session.userId.login, number: req.session.userId.account_nr, totp: false });
            }
        });
    } catch (err) {
        console.error(err);
        res.status(500).send('Błąd podczas usuwania danych!');
    }
})


function generateAccountNumber() {
    const randomDigits = Math.floor(1000000000 + Math.random() * 9000000000); // Losujemy 9 cyfr
    return randomDigits.toString(); // Zwracamy numer konta
}

async function getUserByAccountNumber(accountNumber) {
    return new Promise((resolve, reject) => {
        db.query('SELECT * FROM users WHERE account_nr = ?', [accountNumber], (err, result) => {
            if (err) {
                reject(err);
            } else {
                if (result.length > 0) {
                    resolve(result[0]);
                } else {
                    resolve(null);
                }
            }
        });
    });
}

const http = require('http');
const server = http.createServer(app);

const port = 3000;
server.listen(port, () => {
    console.log(`Server is listening on https://localhost:${port}`);
});

const GoogleStrategy = require('passport-google-oauth').OAuth2Strategy;
const GOOGLE_CLIENT_ID = '346935353812-l4sn50l386a2a7vaio6vhagfmtfvohod.apps.googleusercontent.com';
const GOOGLE_CLIENT_SECRET = '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';
passport.use(new GoogleStrategy({
    clientID: GOOGLE_CLIENT_ID,
    clientSecret: GOOGLE_CLIENT_SECRET,
    callbackURL: "http://localhost:3000/auth/google/callback"
},
    function (accessToken, refreshToken, profile, done) {
        userProfile = profile;
        return done(null, userProfile);
    }
));

app.get('/auth/google',
    passport.authenticate('google', { scope: ['profile', 'email'] }));

app.get('/auth/google/callback',
    passport.authenticate('google', { failureRedirect: '/error' }),
    function (req, res) {
        try {
            db.query('SELECT * FROM users WHERE email = ?', [userProfile.emails[0].value], async (err, result) => {
                if (err) {
                    res.status(500).send('Błąd podczas sprawdzania');
                } else if (result.length > 0) {
                    if (result[0].password != 'google')
                        res.render('login', { message: 'Istnieje już konto z takim adresem e-mail niepowiązane z kontem Google. Zaloguj się tradycyjnie!' });
                }
                else {
                    let accountNumber;
                    let isUnique = false;
                    while (!isUnique) {
                        accountNumber = generateAccountNumber();
                        const existingUser = await getUserByAccountNumber(accountNumber);
                        if (!existingUser) {
                            isUnique = true;
                        }
                    }
                    const user = {
                        login: userProfile.displayName,
                        email: userProfile.emails[0].value,
                        password: 'google',
                        account_nr: accountNumber
                    };
                    try {
                        db.query('INSERT INTO users SET ?', user, (err, result) => {
                            if (err) {
                                res.status(500).send('Błąd podczas rejestracji użytkownika');
                            }
                        })
                    } catch (err) {
                        console.error(err);
                        res.status(500).send('Błąd podczas hashowania hasła');
                    }
                }
            }
            )
        } catch (err) {
            console.error(err);
            res.status(500).send('Błąd przy sprawdzaniu konta!');
        }

        try {
            db.query('SELECT * FROM users WHERE email = ? AND password = ?', [userProfile.emails[0].value, 'google'], (err, result) => {
                if (err) {
                    res.status(500).send('Błąd!');
                }
                else {
                    var user = result[0]
                    try {
                        db.query('SELECT * FROM secrets WHERE email = ?', [user.email], async (err, result) => {
                            if (err) {
                                res.status(500).send('Błąd podczas pobierania danych!');
                            }
                            else if (result.length > 0) {
                                res.cookie.user = user;
                                res.render('2fa-login', { message: undefined });
                            }
                            req.session.userId = user
                            res.render('account', { user: req.session.userId.login, number: req.session.userId.account_nr, totp: false });
                        });
                    } catch (err) {
                        console.error(err);
                        res.status(500).send('Błąd podczas pobierania danych!');
                    }
                }
            })
        } catch (err) {
            console.error(err);
            res.status(500).send('Błąd podczas hashowania hasła');
        }
    });