#lang racket

(define-struct leaf () #:transparent)
(define-struct node (l elem r) #:transparent)

(define (delete t x)
  (define (find-leftmost t)
    (cond [(leaf? t)
           null]
          [(leaf? (node-l t))
           (node-elem t)]
          [else
           (find-leftmost (node-l t))]))
        
  (define (aux t)
    (let ([nn (find-leftmost (node-r t))])
      (if (null? nn)
          (node-l t)
          (node (node-l t) nn (delete (node-r t) nn)))))
    
  (cond [(leaf? t)
         t]
        [(< x (node-elem t))
         (node (delete (node-l t) x)
               (node-elem t)
               (node-r t))]
        [(> x (node-elem t))
         (node (node-l t)
               (node-elem t)
               (delete (node-r t) x))]
        [else
         (aux t)]))

( define ex
   ( node
     ( node ( leaf ) 2 ( leaf ))
     5
     ( node ( node ( leaf ) 6 ( leaf ))
            7
            (leaf))))
  
     