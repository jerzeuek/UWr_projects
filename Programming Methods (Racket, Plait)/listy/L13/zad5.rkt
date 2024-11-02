#lang racket

;===============================================
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
;================================================

(define (merge s1 s2)
  (let ((s1car (stream-car s1))
        (s2car (stream-car s2)))
    (cond ((< s1car s2car)
           (stream-cons s1car (merge (stream-cdr s1) s2)))
          ((> s1car s2car)
           (stream-cons s2car (merge s1 (stream-cdr s2))))
          (else
           (stream-cons s1car
                        (merge (stream-cdr s1)
                               (stream-cdr s2)))))))

(define (scale n stream)
  (stream-cons (* n (stream-car stream))
               (scale n (stream-cdr stream))))

(define S 
  (stream-cons 1 (merge (scale 2 S)
                        (merge (scale 3 S)
                               (scale 5 S)))))