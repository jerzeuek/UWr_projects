#lang racket

(define (suffixes xs)
  (if (null? xs)
   (list null)
   (append (list xs) (suffixes (cdr xs)))))