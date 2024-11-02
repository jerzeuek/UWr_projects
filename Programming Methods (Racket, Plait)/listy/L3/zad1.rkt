#lang racket

; '(( car ( a . b)) (* 2) ) -> (list `( car ( a . b)) `(* 2) )

;‘ ( ,( car '(a . b)) ,(* 2) )

; ((+ 1 2 3) ( cons ) ( cons a b)) -> (list `(+ 1 2 3) '(cons) (list 'a 'b))