var pg = require('pg');
(async function main() {
    var pool = new pg.Pool({
        host: 'localhost',
        database: 'zad3',
        user: 'postgres',
        password: '04012017', 
        port: 3000
    });

    try {
        var result = await pool.query(`UPDATE osoba SET imie='Anna' WHERE imie='Aneta'`);
        console.log('Zaktualizowane!')
    }
    catch (err) {
        console.log(err);
    }
})();