var pg = require('pg');
(async function main() {
    var pool = new pg.Pool({
        host: 'localhost',
        database: 'zad4',
        user: 'postgres',
        password: '04012017',
        port: 3000
    });
    try {
        var result = await pool.query(
            `WITH first_insert AS 
            (INSERT INTO miejsce_pracy (nazwa, adres) 
            VALUES('Budex S.A.', 'Daszyńskiego 74') RETURNING id)  
            
            INSERT INTO osoba (imie, nazwisko, id_miejsce_pracy) 
            VALUES('Adam', 'Kowalski', (SELECT id FROM first_insert))`);
        console.log('Wstawiono!')
    }
    catch (err) {
        console.log(err);
    }
})();