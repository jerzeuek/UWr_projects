#lang racket

(define (sorted? xs)
  (cond
    [(or (null? xs) (null? (cdr xs))) #t]
    [(> (car xs) (cadr xs)) #f]
    [else (sorted? (cdr xs))]))