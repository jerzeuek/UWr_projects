var pg = require('pg');
(async function main() {
    var pool = new pg.Pool({
        host: 'localhost',
        database: 'zad2',
        user: 'postgres',
        password: '04012017', 
        port: 3000
    });

    var imie = 'Jacek'
    var nazwisko = 'Ananas'
    try {
        var result = await pool.query(`INSERT INTO osoba (imie, nazwisko) VALUES ('${imie}', '${nazwisko}') RETURNING id`);
        result.rows.forEach(r => {
            console.log(`${r.id}`);
        });
    }
    catch (err) {
        console.log(err);
    }
})();