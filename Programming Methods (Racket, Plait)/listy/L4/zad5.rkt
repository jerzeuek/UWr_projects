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


(define (insert-bst x t)
  (cond [(leaf? t) (node (leaf) x (leaf))]
        [(node? t)
         (if (< x (node-elem t))
             (node (insert-bst x (node-l t))
                   (node-elem t)
                   (node-r t))
             (node (node-l t)
                   (node-elem t)
                   (insert-bst x (node-r t))))]))

(define (treesort xs)
  (define (inserting xs t)
    (if (null? xs) t
    (inserting (cdr xs) (insert-bst (car xs) t))))
  (flatten (inserting xs (leaf))))

