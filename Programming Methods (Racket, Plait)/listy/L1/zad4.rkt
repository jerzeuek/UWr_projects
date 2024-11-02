#lang racket

(define (lmao a b c)
  (cond
    [(= (max a b c) a) (+ (* a a) (expt (max b c) 2))]
    [(= (max a b c) b) (+ (* b b) (expt (max a c) 2))]
    [else (+ (* c c) (expt (max a b) 2))]))
                           