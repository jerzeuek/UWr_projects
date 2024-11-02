#lang racket

(define (fib n)
  (cond
    [(= n 0) 0]
    [(= n 1) 1]
    [else (+ (fib (- n 1)) (fib (- n 2)))]
    )
  )

;(fib 5) ->
;(+ (fib 4) (fib 3)) ->
;(+ (+ (fib 3) (fib 2)) (+ (fib 2) (fib 1))) ->
;(+ (+ (+ (fib 2) (fib 1)) (+ (fib 1) (fib 0))) (+ (+ (fib 1) (fib 0)) 1)) ->
;(+ (+ (+ (+ (fib 1) (fib 0)) 1) (+ 1 0)) (+ (+ 1 0) 1)) ->
;(+ (+ (+ (+ 1 0) 1) 1) (+ 1 1)) ->
;(+ (+ (+ 1 1) 1) 2) ->
;(+ (+ 2 2) 1) ->
;(+ 4 1) ->
;5


(define (fib-iter n)
  (define (iter a b acc n)
    (cond
         [(= n 0) a]
         [(= n 1) b]
         [(= n acc) (+ a b)]
         [else (iter b (+ a b) (+ acc 1) n)]
         ))
  (iter 0 1 2 n))

;(fib-iter 5) ->
;(iter 0 1 2 5) ->
;(iter 1 1 3 5) ->
;(iter 1 2 4 5) ->
;(iter 2 3 5 5) ->
;(+ 2 3) ->
;5



  
