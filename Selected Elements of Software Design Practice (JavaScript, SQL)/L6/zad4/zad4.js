var http = require('http');
var express = require('express');
var app = express();
app.set('view engine', 'ejs');
app.set('print', './print');


app.use(express.urlencoded({ extended: true }));
// przy nawigacji do witryny z przeglądarki

app.get('/', (req, res) => {
    res.render('index', { abc: '' });
});
// po odesłaniu formularza z widoku index.ejs
// proszę prześledzić jak w praktyce działa wzorzec Post-Redirect-Get
app.post('/', (req, res) => {
    var abc = req.body.abc;
    // tu walidacja
    // może być prosta, typu czy niepuste
    // może być złożona, np. czy określony format
    res.redirect('/print?abc=' + abc);

    }
);
// po przekierowaniu z poprzedniej strony
app.get('/print', (req, res) => {
    var abc = req.query.abc;
    res.render('print', { abc })
});
http.createServer(app).listen(3000);