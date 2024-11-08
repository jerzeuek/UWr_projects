#lang racket

(define dict (hash ;lista repr. kod - lista ze znakiem
              " " #\space
              "._"  "A"
              "_..." "B"
              "_._." "C"
              "_.." "D"
              "." "E"
              ".._." "F"
              "__." "G"
              "...." "H"
              ".." "I"
              ".___" "J"
              "_._" "K"
              "._.." "L"
              "__" "M"
              "_." "N"
              "___" "O"
              ".__." "P"
              "__._" "Q"
              "._." "R"
              "..." "S"
              "_" "T"
              ".._" "U"
              "..._" "V"
              ".__" "W"
              "_.._" "X"
              "_.__" "Y"
              "__.." "Z"
              ".____" "1"
              "..___" "2"
              "...__" "3"
              "...._" "4"
              "....." "5"
              "_...." "6"
              "__..." "7"
              "___.." "8"
              "____." "9"
              "_____" "0"
              "._._._" "."
              "__..__" ","
              "___..." ":"
              "..__.." "?"
              ".____." "'"
              "_...._" "-"
              "._.._." "!"))


(define (morse-decode txt)
  (let decode ([orig (append (string->list txt) '(#\space))] [decoded ""] [buf null])
  (cond
    [(null? orig) decoded]

    [(not (char-whitespace? (car orig)))
     (decode (cdr orig) decoded (append buf (list (car orig))))]
                            
    [else
     (let ([whitespaces (spaces orig)])
       (if
        (= whitespaces 2)
        (decode (list-tail orig 2) (string-append decoded (hash-ref dict (list->string buf)) " ") null)
        (decode (cdr orig) (string-append decoded (hash-ref dict (list->string buf))) null)))])))

(define (spaces lst)
  (define (counter lst acc)
    (if
     (or (null? lst) (not (char-whitespace? (car lst))))
      acc
     (counter (cdr lst) (+ acc 1))))
  (counter lst 0))

     