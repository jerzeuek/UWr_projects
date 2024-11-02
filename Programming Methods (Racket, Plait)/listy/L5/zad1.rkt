#lang plait


(define (zad-a x y) ;('a 'b -> 'a)
  x)

(define (zad-b x y z) ;(('a 'b -> 'c) ('a -> 'b) 'a -> 'c)
  (x z(y z)))

(define (zad-c [f : (('a -> 'a) -> 'a)]) ;((('a -> 'a) -> 'a) -> 'a)
  (f (lambda (x) x)))

(define (zad-d f g) ;(('a -> 'b) ('a -> 'c) -> ('a -> ('b * 'c)))
  (lambda (x) (pair (f x) (g x))))

(define (zad-e [f : ('a -> (Optionof('a * 'b)))] [x : 'a]) ;(('a -> (Optionof ('a * 'b))) 'a -> (Listof 'b))
  (list (snd (some-v (f x)))))
