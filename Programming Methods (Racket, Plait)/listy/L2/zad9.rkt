#lang racket

(define (select xs)
  (define (minimum xs)
    (cond
      [(null? xs) +inf.0]
      [(> (car xs) (minimum (cdr xs))) (minimum (cdr xs))]
      [else (car xs)]))
  
  (cons (minimum xs) (remove (minimum xs) xs)))

(define (select-sort xs)
  (define (it xs ys)
    (if (null? xs) ys
     (it (cdr (select xs)) (append ys (list(car (select xs)))))))

  (it xs null))