#lang racket

(define (my-if cond ifTrue ifFalse)
  (or (and cond ifTrue) ifFalse))