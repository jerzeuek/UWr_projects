#lang plait

(define-type-alias StackDcmpl (Listof Exp))
(define mtSDcmpl : StackDcmpl empty)
(define (pushSDcmpl [e : Exp] [s : StackDcmpl]) : StackDcmpl
  (cons e s))
(define (popSDcmpl [s : StackDcmpl]) : (Exp * StackDcmpl)
  (type-case StackDcmpl s
    [empty
     (error 'popS "empty stack")]
    [(cons v s)
     (pair v s)]))

(define (decompile [c : Code]) : Exp
  (local [(define (it c s)
            (type-case Code c
              [empty
               (fst (popSDcmpl s))]
              [(cons i c)
               (type-case Instr i
                 [(pushI n)
                  (it c (pushSDcmpl (numE n) s))]
                 [(opI op)
                  (let* ([n2-s2 (popSDcmpl s)]
                         [n2 (fst n2-s2)]
                         [s2 (snd n2-s2)]
                         [n1-s1 (popSDcmpl s2)]
                         [n1 (fst n1-s1)]
                         [s1 (snd n1-s1)]
                         [s0 (pushSDcmpl (opE op n1 n2) s1)])
                    (it c s0))])]))]
    (it c mtSDcmpl)))
    
(module+ test
  (test (decompile (compile (parse `2)))
        (parse `2))
  (test (decompile (compile (parse `(+ 1 2))))
        (parse `(+ 1 2)))
  (test (decompile (compile (parse `(* (* 8 9) (/ 10 11)))))
        (parse `(* (* 8 9) (/ 10 11))))
  (test (decompile (compile (parse `(/ (* (- 5 9) (+ (/ 10 389) 34)) (/ (* 6 9) (+ 120 43))))))
        (parse `(/ (* (- 5 9) (+ (/ 10 389) 34)) (/ (* 6 9) (+ 120 43))))))