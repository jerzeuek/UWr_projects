#lang racket

(define-struct leaf () #:transparent)
(define-struct node (l elem r) #:transparent)

(define (bst? t)
  (define (bst?-helper t min max)
    (cond
      [(leaf? t) #t]
      [(and (>= (node-elem t) min)
            (<= (node-elem t) max))
       (and (bst?-helper (node-l t) min (node-elem t))
            (bst?-helper (node-r t) (node-elem t) max))]
      [else #f]))
  (bst?-helper t -inf.0 +inf.0))

(define (sum-paths t)
  (define (sum-paths-helper t sum)
    (if (leaf? t) (leaf)
          (let ((new-sum (+ sum (node-elem t))))
                  (node (sum-paths-helper (node-l t) new-sum)
                        new-sum
                        (sum-paths-helper (node-r t) new-sum)))))

  (sum-paths-helper t 0))

  ( define ex
   ( node
     ( node ( leaf ) 2 ( leaf ))
     5
     ( node ( node ( leaf ) 6 ( leaf ))
            7
            ( node ( leaf ) 9 ( leaf )))))