#lang racket

(define-struct leaf () #:transparent)
(define-struct node (l elem r) #:transparent)


(define (flatten t)
  (flat-append t '()))

(define (flat-append t xs)
  (cond [(leaf? t) xs]
        [(node? t) (flat-append (node-l t)
                                (cons (node-elem t)
                                      (flat-append (node-r t) xs)))]
        [else xs]))

( define ex
   ( node
     ( node ( leaf ) 2 ( leaf ))
     5
     ( node ( node ( leaf ) 6 ( leaf ))
            8
            ( node ( leaf ) 9 ( leaf )))))

( define ( list->left-tree xs )
   ( foldl ( lambda (x t) ( node t x ( leaf ))) ( leaf ) xs ))
( define test-tree ( list->left-tree ( build-list 20000 identity )))