var http = require('http');
var express = require('express');
var multer = require('multer')
var path = require('path')
var app = express();

var storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'files')
    },

    filename: (req, file, cb) => {
        cb(null, Date.now() + path.extname(file.originalname))
    }
})

var upload = multer({storage: storage})


app.set('view engine', 'ejs');

app.get("/", (req, res) => {
    res.render("index");
});

app.post("/", upload.single('file'), (req, res) => {
    res.send("Dodano plik!");
});

http.createServer(app).listen(3000);