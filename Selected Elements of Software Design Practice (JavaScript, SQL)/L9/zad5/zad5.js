var pg = require('pg');
(async function main() {
    var pool = new pg.Pool({
        host: 'localhost',
        database: 'zad5',
        user: 'postgres',
        password: '04012017',
        port: 3000
    });
    try {
        var result = await pool.query(
            `WITH first_insert AS 
            (INSERT INTO miejsce_pracy (nazwa, adres) 
            VALUES('Budex S.A.', 'Daszyńskiego 74') RETURNING id),
            
            second_insert AS
            (INSERT INTO osoba (imie, nazwisko) 
            VALUES('Jacek', 'Arbaz') RETURNING id)

            INSERT INTO osoba_pracodawca (osoba_id, miejsce_pracy_id)
            VALUES((SELECT id from second_insert), (SELECT id from first_insert))`);
        console.log('Wstawiono!')
    }
    catch (err) {
        console.log(err);
    }
})();