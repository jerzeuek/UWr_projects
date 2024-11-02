const fs = require('fs');
var path = require('path');
var cookieParser = require('cookie-parser');
const express = require('express');
const app = express();

app.use(cookieParser());
app.set('view engine', 'ejs');
app.set('views', './views');


app.use(express.urlencoded({extended:true}));

app.get( '/', (req, res) => {
    res.render('index',{nr_rachunku:undefined,tekst:undefined,kwota:undefined,tytul:undefined});
});

app.post('/', (req,res) =>{
    var nr_rachunku=req.body.nr_rachunku;
    var tekst=req.body.dane_odbiorcy;
    var kwota=req.body.kwota;
    var tytul=req.body.tytul;
    console.log(nr_rachunku);
    res.cookie('nr_rachunku',nr_rachunku);
    res.cookie('tekst',tekst);
    res.cookie('kwota',kwota);
    res.cookie('tytul',tytul);
    res.redirect('/confirm');
});

app.get('/confirm', (req,res) =>{
    res.render('confirm',{nr_rachunku:req.cookies.nr_rachunku, dane_odbiorcy:req.cookies.tekst,kwota:req.cookies.kwota,tytul:req.cookies.tytul});
});

const http = require('http');
const server = http.createServer(app);

const port = 3000;
server.listen(port, () => {
  console.log(`Server is listening on https://localhost:${port}`);
});
