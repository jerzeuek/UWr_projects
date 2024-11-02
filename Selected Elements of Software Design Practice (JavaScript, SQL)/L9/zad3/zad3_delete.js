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
        var result = await pool.query(`DELETE FROM osoba WHERE imie='Kacper'`);
        console.log('Usunięto!')
    }
    catch (err) {
        console.log(err);
    }
})();