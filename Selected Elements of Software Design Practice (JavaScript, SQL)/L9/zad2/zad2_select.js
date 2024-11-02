var pg = require('pg');
(async function main() {
    var pool = new pg.Pool({
        host: 'localhost',
        database: 'zad2',
        user: 'postgres',
        password: '04012017', 
        port: 3000
    });
    try {
        var result = await pool.query(`select * from osoba where wiek<=40 and plec='M'`);
        result.rows.forEach(r => {
            console.log(`${r.id} ${r.imie} ${r.nazwisko} ${r.wiek}`);
        });
    }
    catch (err) {
        console.log(err);
    }
})();