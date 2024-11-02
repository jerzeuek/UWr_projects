#lang plait

(define-type (NNF 'v)
   (nnf-lit [ polarity : Boolean ] [ var : 'v ])
   (nnf-conj [l : ( NNF 'v)] [r : ( NNF 'v) ])
   (nnf-disj [l : ( NNF 'v)] [r : ( NNF 'v) ]) )

(define (neg-nnf [form : (NNF 'a)]) : (NNF 'a)
  (type-case (NNF 'v) form
    [(nnf-lit pol v) (nnf-lit (not pol) v)]
    [(nnf-conj l r) (nnf-disj (neg-nnf l) (neg-nnf r))]
    [(nnf-disj l r) (nnf-conj (neg-nnf l) (neg-nnf r))]))

(define test (nnf-conj (nnf-lit #t "p") (nnf-lit #f "q")))