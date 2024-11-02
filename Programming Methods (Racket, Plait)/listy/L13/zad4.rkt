#lang racket

(define-syntax-rule
  (stream-cons v s)
  (cons v (delay s)))

(define stream-car car)
(define (stream-cdr s)
  (force (cdr s)))

(define (stream-ref s n)
  (if (= n 0)
      (stream-car s)
      (stream-ref (stream-cdr s) (- n 1))))

(define (map2 f xs ys)
  (stream-cons
   (f (stream-car xs)
      (stream-car ys))
   (map2 f (stream-cdr xs) (stream-cdr ys))))

(define ones (stream-cons 1 ones))
(define nats (stream-cons 0 (map2 + nats ones)))
(define nats+ (stream-cdr nats))

(define fact (stream-cons 1 (map2 * fact nats+)))

(define (partial-sums s)
  (letrec [(res (stream-cons (stream-car s)
                             (map2 + res (stream-cdr s))))]
    res))

