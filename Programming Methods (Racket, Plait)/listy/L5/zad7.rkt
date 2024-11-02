#lang plait

(define-type Prop
  (var [v : String])
  (conj [l : Prop] [r : Prop])
  (disj [l : Prop] [r : Prop])
  (neg [f : Prop])
  (impl [l : Prop] [r : Prop]))


(define (free-vars [p : Prop]) : (Listof String)
  (local
    [(define (check [p : Prop] [lst : (Listof String)]) : (Listof String)
       (type-case Prop p
         [(var x) (if (member x lst)
                      lst
                      (cons x lst))]
         [(conj left right)  (check left (check right lst))]
         [(disj left right)  (check left (check right lst))]
         [(impl left right)  (check left (check right lst))]
         [(neg t)  (check t lst)]))]
    (check p '())))

(define p : Prop
  (impl (disj (conj (var "x") (neg (var "y")))
              (var "z"))
        (disj (var "x") (neg (var "x")))))

