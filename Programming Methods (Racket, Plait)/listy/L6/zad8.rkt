#lang plait

(define (sorted? [xs : (Listof Number)]) : Boolean
  (if
   (or (empty? xs) (empty? (rest xs))) #t
   (and (< (first xs) (second xs)) (sorted? (rest xs)))))

(define (insert [x : Number] [xs : (Listof Number)]): (Listof Number)
  (cond
    [(empty? xs) (list x)]
    [(>= (first xs) x) (cons x xs)]
    [else (cons (first xs) (cons x (rest xs)))]))