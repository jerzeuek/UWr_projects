var http = require('http');
var fs = require('fs');
var server =
    http.createServer(
        (req, res) => {
            res.setHeader('Content-Disposition', 'attachment; filename="cats.jpg"')
            var stream = fs.createReadStream("cats.jpg")
            stream.pipe(res)
        })
server.listen(3000);
console.log('started');