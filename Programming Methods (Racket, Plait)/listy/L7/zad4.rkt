#lang racket

(define/contract (p1 x y)
  [parametric->/c [a b] (-> a b a)]
  x)

(define/contract (p2 f g x)
  [parametric->/c [a b c] (-> (-> a b c) (-> a b) a c)]
  (f x (g x)))

(define/contract (p3 f g)
  [parametric->/c [a b c] (-> (-> b c) (-> a b) (-> a c))]
  (lambda (x) (f (g x))))

(define/contract (p4 f)
  [parametric->/c [a] (-> (-> (-> a a) a) a)]
  (f (lambda (x) (f (lambda (x) x)))))