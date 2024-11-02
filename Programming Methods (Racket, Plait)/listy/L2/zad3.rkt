#lang racket

(define-struct matrix (a b c d) #:transparent)


(define (matrix-mult m n)
  (make-matrix
   (+ (*(matrix-a m) (matrix-a n)) (* (matrix-b m) (matrix-c n)))
   (+ (*(matrix-a m) (matrix-b n)) (* (matrix-b m) (matrix-d n)))
   (+ (* (matrix-c m) (matrix-a n)) (* (matrix-d m) (matrix-c n)))
   (+ (* (matrix-c m) (matrix-b n)) (* (matrix-d m) (matrix-d n)))
   ))


(define matrix-id
  (make-matrix 1 0 0 1))

(define (matrix-expt m k)
  (define (iter new old acc n)
  (if (= n acc) new
      (iter (matrix-mult new old) old (+ acc 1) n)))

  (if (= k 0)
      matrix-id
  (iter m m 1 k)))


(define (fib-matrix k)
  (matrix-a (matrix-expt (matrix 1 1 1 0) (- k 1))))

      
  