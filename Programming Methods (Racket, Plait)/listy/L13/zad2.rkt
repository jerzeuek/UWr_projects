#lang racket

; -------------------
(define (divides? a b)
  (= (remainder b a) 0))

(define-syntax-rule
  (stream-cons v s)
  (cons v (delay s)))

(define stream-car car)

(define (stream-cdr s)
  (force (cdr s)))

(define stream-null null)
(define stream-null? null?)

(define (stream-ref s n)
  (if (= n 0)
      (stream-car s)
      (stream-ref (stream-cdr s) (- n 1))))
; ---------------------

(define (next-prime str1 n)
  (let ([x (stream-car str1)])
  (if (> (* x x) n)
      n
      (if (divides? x n)
          (next-prime primes (+ 1 n))
          (next-prime (stream-cdr str1) n)))))

(define (primes-map xs)
  (let ([n (stream-car xs)])
         (stream-cons (next-prime primes (+ n 1))
                       (primes-map (stream-cdr xs)))))

(define primes (stream-cons 2 (primes-map primes)))