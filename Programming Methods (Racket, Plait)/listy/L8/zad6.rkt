#lang racket

(define dict (hash ;znak - lista repr. kod
              #\space " "
              #\A "._"
              #\B "_..."
              #\C "_._."
              #\D "_.."
              #\E "."
              #\F ".._."
              #\G "__."
              #\H "...."
              #\I ".."
              #\J ".___"
              #\K "_._"
              #\L "._.."
              #\M "__"
              #\N "_."
              #\O "___"
              #\P ".__."
              #\Q "__._"
              #\R "._."
              #\S "..."
              #\T "_"
              #\U ".._"
              #\V "..._"
              #\W ".__"
              #\X "_.._"
              #\Y "_.__"
              #\Z "__.."
              #\1 ".____"
              #\2 "..___"
              #\3 "...__"
              #\4 "...._"
              #\5 "....."
              #\6 "_...."
              #\7 "__..."
              #\8 "___.."
              #\9 "____."
              #\0 "_____"
              #\. "._._._"
              #\, "__..__"
              #\: "___..."
              #\? "..__.."
              #\' ".____."
              #\- "_...._"
              #\! "._.._."))


(define (morse-code txt)
  (let code ([orig (string->list (string-upcase txt))] [coded ""])
  (cond
    [(null? orig) (string-trim coded)]

    [(not (char-whitespace? (car orig)))
      (code (cdr orig) (string-append coded (hash-ref dict (car orig)) " "))]

    [else
     (code (list-tail orig (spaces orig)) (string-append coded " "))])))

(define (spaces lst)
  (define (counter lst acc)
    (if
     (or (null? lst) (not (char-whitespace? (car lst))))
      acc
     (counter (cdr lst) (+ acc 1))))
  (counter lst 0))