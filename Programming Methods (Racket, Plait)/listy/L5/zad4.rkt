#lang plait

;(define (perms s)
;  (local
;    [(define (splice l m r)
;      (append
;       (map (lambda (x) (cons m x)) (perms (append l r)))
;       (if (empty? r) '()
;           (splice (append l (list m)) (first r) (rest r)))))]

                              
;  (cond [(empty? s) '()]
;        [(empty? (rest s)) (list s)]
;        [else (splice '() (first s) (rest s))])))


(define (concat-map f xs)
  (if (empty? xs)
      '()
      (append (f (first xs))
              (concat-map f (rest xs)))))

(define (insert-all x xs)
  (if (empty? xs)
      (list (list x))
      (cons (cons x xs)
            (map (lambda (ys) (cons (first xs) ys))
                 (insert-all x (rest xs))))))

(define (perms xs)
  (if (empty? xs)
      (list xs)
      (concat-map (lambda (ys) (insert-all (first xs) ys))
                  (perms (rest xs)))))