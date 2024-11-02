const fs = require('fs')

fs.readFile('tekst.txt', 'utf8', function (err, data) {

    console.log(data);
});