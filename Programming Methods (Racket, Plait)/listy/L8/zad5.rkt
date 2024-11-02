#lang plait

(define-type Op-b
  (op-add) (op-mul) (op-sub) (op-div) (op-pow))

(define-type Op-u
  (op-neg) (op-fact))

(define-type Exp
  (exp-number [n : Number])
  (exp-op-binary [op : Op-b] [e1 : Exp] [e2 : Exp])
  (exp-op-unary [op : Op-u] [e1 : Exp]))

(define (parse-Op-b s)
  (let ([sym (s-exp->symbol s)])
  (cond
    [(equal? sym '+) (op-add)]
    [(equal? sym '-) (op-sub)]
    [(equal? sym '*) (op-mul)]
    [(equal? sym '/) (op-div)]
    [(equal? sym '^) (op-pow)])))

(define (parse-Op-u s)
  (let ([sym (s-exp->symbol s)])
  (cond
    [(equal? sym '~) (op-neg)]
    [(equal? sym '!) (op-fact)])))

(define (parse-Exp s)
  (cond
    [(s-exp-number? s) (exp-number (s-exp->number s))]
    [(s-exp-list? s)
     (let ([xs (s-exp->list s)])
       (if (= (length xs) 3)
           (exp-op-binary (parse-Op-b  (first  xs))
                          (parse-Exp (second xs))
                          (parse-Exp (third  xs)))
           (exp-op-unary (parse-Op-u  (first  xs))
                         (parse-Exp (second xs)))))]))

; ==============================================

(define (expt x pow)
  (cond [(= pow 0) 1]
        [(= pow 1) x]
        [else (letrec ([tmp (expt x (floor (/ pow 2)))])
                (if (= (modulo pow 2) 1)
                    (* (* tmp tmp) x)
                    (* tmp tmp)))]))

(define (factorial x)
  (if (= x 0)
      1
      (* x (factorial (- x 1)))))

(define ^ (λ (x y) (expt x y)))
(define ! (λ (x) (factorial x)))
(define ~ (λ (x) (- 0 x)))

; ==============================================

(define (eval-op-b op)
  (type-case Op-b op
    [(op-add) +]
    [(op-sub) -]
    [(op-mul) *]
    [(op-div) /]
    [(op-pow) ^]))

(define (eval-op-u op)
  (type-case Op-u op
    [(op-neg)  ~]
    [(op-fact) !]))

(define (eval e)
  (type-case Exp e
    [(exp-number n)    n]
    [(exp-op-binary op e1 e2)
     ((eval-op-b op) (eval e1) (eval e2))]
    [(exp-op-unary op e)
     ((eval-op-u op) (eval e))]))