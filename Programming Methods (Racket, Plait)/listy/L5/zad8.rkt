#lang plait

(define-type Prop
  (var [v : String])
  (conj [l : Prop] [r : Prop])
  (disj [l : Prop] [r : Prop])
  (neg [f : Prop])
  (impl [l : Prop] [r : Prop]))

(define (eval [h : (Hashof String Boolean)] [p : Prop]) : Boolean
  (type-case Prop p
    [(var x)
     (type-case (Optionof Boolean) (hash-ref h x)
       [(none) (error 'val "Nie ma waluacji!")]
       [(some bool) bool])]
    [(conj left right) (and (eval h left)
                            (eval h right))]
    [(disj left right) (or (eval h left)
                           (eval h right))]
    [(impl left right) (or (not (eval h left)) ;(p=>q) = (-p V q)
                           (eval h right))]
    [(neg t) (not (eval h t))]))

(define val (make-hash (list (pair "x" #f)(pair "y" #f)(pair "z" #f))))
(define taut-val (make-hash (list (pair "x" #f))))

;((x /\ ~y) \/ z) -> (x \/ ~x)
(define test : Prop
  (impl (disj (conj (var "x") (neg (var "y")))
              (var "z"))
        (disj (var "x") (neg (var "x")))))

(define taut : Prop
  (disj (var "x") (neg (var "x"))))
