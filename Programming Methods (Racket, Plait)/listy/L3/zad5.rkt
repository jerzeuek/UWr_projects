#lang racket

(define (negatives n)
  (build-list n (lambda (x) (* (+ x 1) -1))))

(define (reciprocals n)
  (build-list n (lambda (x) (/ 1 (+ x 1)))))

(define (evens n)
  (build-list n (lambda (x) (* x 2))))

(define (identityM n)
  (build-list n (lambda (x) (build-list n (lambda (n) (if (= x n) 1 0))))))