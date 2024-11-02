#lang racket

(define (suffixes-without-contract xs)
  (if (null? xs)
      null
      (cons xs (suffixes-without-contract (cdr xs)))))

(define/contract (suffixes xs)
  (parametric->/c [a] (-> (listof a) (listof (listof a))))
  (if (null? xs)
      null
      (cons xs (suffixes (cdr xs)))))

;(time (suffixes-without-contract (range 5000)) (void))

;(time (suffixes (range 5000)) (void))