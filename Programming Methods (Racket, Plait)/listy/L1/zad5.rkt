#lang racket
( define ( a-plus-abs-b a b)
   (( if ( > b 0) + -) a b))

;funkcja ta oblicza a + |b|, sprawdza czy b jest dodatnie czy niedodatnie,
;wtedy odpowiednio wybiera procedurę dodawania lub odejmowania b.