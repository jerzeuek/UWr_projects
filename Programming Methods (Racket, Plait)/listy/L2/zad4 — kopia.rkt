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

(define (matrix-expt-fast m k)
  (cond
   [(= k 0) matrix-id]
   [(= k 2) (matrix-mult m m)]
   [(= (modulo k 2) 1) (matrix-mult (matrix-expt-fast (matrix-expt-fast m (/ (- k 1) 2)) 2) m)]
   [else (matrix-expt-fast (matrix-expt-fast m (/ k 2)) 2)]))

(define (fib-matrix-fast k)
  (matrix-a (matrix-expt-fast (matrix 1 1 1 0) (- k 1))))

;m^7 -> ((m^3)^2)*m -> ((m^2*m)^2)*m) -> 




