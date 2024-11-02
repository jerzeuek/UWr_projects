#lang plait

(define-type (NNF 'v)
   (nnf-lit [ polarity : Boolean ] [ var : 'v ])
   (nnf-conj [l : ( NNF 'v)] [r : ( NNF 'v) ])
   (nnf-disj [l : ( NNF 'v)] [r : ( NNF 'v) ]) )

(define (eval-nnf [f : ('a -> Boolean)] [form : (NNF 'a)]) : Boolean
  (type-case (NNF 'a) form
    [(nnf-lit pol var)
        (if (equal? pol #t)
            (f var)
            (not (f var)))]
    [(nnf-conj l r) 
        (and (eval-nnf f l)
             (eval-nnf f r))]
    [(nnf-disj l r) 
        (or (eval-nnf f l)
            (eval-nnf f r))]))

(define test-form (nnf-conj (nnf-disj (nnf-lit #t "x") (nnf-lit #f "y")) (nnf-lit #t "z")))

(define (test-f var)
  (if (or (equal? var "x") (equal? var "z")) #t #f))