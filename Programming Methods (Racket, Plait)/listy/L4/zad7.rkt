#lang racket

(define empty-queue ; pusta kolejka
  (list '() '()))

(define (empty? q) ; czy kolejka jest pusta?
  (null? (car q)))

(define (push-back x q) ; dodaj element na koniec kolejki
  (if (empty? q)
      (cons (list x) '())
      (cons (car q) (cons x (cdr q)))) )

(define (front q) ; podejrzyj element na początku kolejki
  (if (empty? q)
      (error "front called with an empty queue")
      (car (car q))) )

(define (pop q) ; zdejmij element z przodu kolejki
  (if (empty? q)
      (error "pop called with an empty queue")
        (if (null? (cdr (car q)))
            (cons (reverse (cdr q)) '())
            (cons (cdr (car q)) (cdr q))))

(define dupa '((3)))