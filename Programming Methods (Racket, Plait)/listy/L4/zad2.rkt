#lang racket

(define-struct leaf () #:transparent)
(define-struct node (l elem r) #:transparent)

(define (fold-tree f x t)
  (if (leaf? t)
      x
      (f (fold-tree f x (node-l t)) (node-elem t) (fold-tree f x (node-r t)))))

(define (tree-sum t)
  (fold-tree + 0 t))

(define (tree-flip t)
  (fold-tree (lambda (l elem r) (node r elem l)) (leaf) t))

(define (tree-height t)
  (fold-tree (lambda (l elem r) (+ 1 (max l r))) 0 t))

(define (tree-span t)
  (fold-tree (lambda (l elem r) (cons
                                 (if (false? l) elem (car l))
                                 (if (false? r) elem (cdr r))))
             #f t))

(define (flatten t)
  (fold-tree (lambda (l elem r) (append l (cons elem r))) null t))

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

                                    