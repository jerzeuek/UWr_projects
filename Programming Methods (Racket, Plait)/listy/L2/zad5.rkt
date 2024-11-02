#lang racket

(define(elem? x xs)
  (cond
    [(null? xs) #f]
    [(equal? x (car xs)) #t]
    [else (elem? x (cdr xs))]))