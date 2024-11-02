#lang racket

(define (foldr-reverse xs)
  (foldr (lambda (y ys) (append ys (list y))) null xs))