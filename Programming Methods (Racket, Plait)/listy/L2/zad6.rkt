#lang racket

(define (maximum xs)
     (cond
      [(null? xs) -inf.0] 
      [(< (car xs) (maximum (cdr xs))) (maximum (cdr xs))] 
      [else (car xs)]
      )
  )