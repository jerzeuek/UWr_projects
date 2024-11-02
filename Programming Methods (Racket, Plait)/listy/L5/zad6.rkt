#lang plait

(define-type (RoseTree 'a)
  (leaf [elem : 'a])
  (node [child : (Listof (RoseTree 'a))] ))

(define (inorder tree xs)
  (cond [(leaf? tree) (cons (leaf-elem tree) xs)]
        [(node? tree)(foldr (lambda (t xs) (inorder t xs)) xs (node-child tree))]))


(define test
  (node (list (leaf 3) (leaf 4) (node (list (leaf 5) (leaf 6))) (node (list)))))
