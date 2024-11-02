#lang racket
(require data/heap)
(require rackunit)
(provide sim? wire?
         (contract-out
          [make-sim        (-> sim?)]
          [sim-wait!       (-> sim? positive? void?)]
          [sim-time        (-> sim? real?)]
          [sim-add-action! (-> sim? positive? (-> any/c) void?)]

          [make-wire       (-> sim? wire?)]
          [wire-on-change! (-> wire? (-> any/c) void?)]
          [wire-value      (-> wire? boolean?)]
          [wire-set!       (-> wire? boolean? void?)]

          [bus-value (-> (listof wire?) natural?)]
          [bus-set!  (-> (listof wire?) natural? void?)]

          [gate-not  (-> wire? wire? void?)]
          [gate-and  (-> wire? wire? wire? void?)]
          [gate-nand (-> wire? wire? wire? void?)]
          [gate-or   (-> wire? wire? wire? void?)]
          [gate-nor  (-> wire? wire? wire? void?)]
          [gate-xor  (-> wire? wire? wire? void?)]

          [wire-not  (-> wire? wire?)]
          [wire-and  (-> wire? wire? wire?)]
          [wire-nand (-> wire? wire? wire?)]
          [wire-or   (-> wire? wire? wire?)]
          [wire-nor  (-> wire? wire? wire?)]
          [wire-xor  (-> wire? wire? wire?)]

          [flip-flop (-> wire? wire? wire? void?)]))

(define (bus-set! wires value)
  (match wires
    ['() (void)]
    [(cons w wires)
     (begin
       (wire-set! w (= (modulo value 2) 1))
       (bus-set! wires (quotient value 2)))]))

(define (bus-value ws)
  (foldr (lambda (w value) (+ (if (wire-value w) 1 0) (* 2 value)))
         0
         ws))

(define (flip-flop out clk data)
  (define sim (wire-sim data))
  (define w1  (make-wire sim))
  (define w2  (make-wire sim))
  (define w3  (wire-nand (wire-and w1 clk) w2))
  (gate-nand w1 clk (wire-nand w2 w1))
  (gate-nand w2 w3 data)
  (gate-nand out w1 (wire-nand out w3)))
;-------------------------------------------------------

; struktury

(struct sim ([time #:mutable] [actions #:mutable]))

(struct wire ([value #:mutable] [actions #:mutable] sim))

;symulacja

(define (make-sim) ; tworzenie nowej symulacji
  (sim 0 (make-heap bigger?)))

(define (bigger? x y) ; komparator do budowy kopca
  (if (> (car x) (car y))
      #f
      #t))

(define (sim-wait! sim time) ; uruchamianie symulacji
  (if (or
       (> (car (heap-min (sim-actions sim))) (+ time (sim-time sim)))
       (= 0 (heap-count (sim-actions sim))))
      (set-sim-time! sim (+ (sim-time sim) time))
      (let ([first (heap-min (sim-actions sim))])
        (begin
          (define aux (- (+ (sim-time sim) time) (car first)))
          (heap-remove-min! (sim-actions sim))
          (set-sim-time! sim (car first))
          ((cdr first))
          (sim-wait! sim aux)))))

(define (sim-add-action! sim time action) ; dodawanie zdarzenia do kolejki
  (heap-add! (sim-actions sim) (cons (+ (sim-time sim) time) action)))

; przewody

(define (make-wire sim) ; tworzenie nowego przewodu
  (wire #f null sim))

(define (wire-on-change! wire new) ; dodawanie akcji do przewodu
  (begin
     (set-wire-actions! wire (append (wire-actions wire) (list new)))
     (new)))

(define (wire-set! wire value) ; ustawianie wartości przewodu
  (if (equal? value (wire-value wire))
      (void)
      (begin
        (set-wire-value! wire value)
        (call (wire-actions wire)))))

(define (call xs) ; wywoływanie akcji przewodu
  (if (null? xs)
      (void)
      (begin
        ((car xs))
        (call (cdr xs)))))

(define (wire-not w) ; przewód NOT
  (define aux (make-wire (wire-sim w)))
  (gate-not aux w)
  aux)

(define (wire-and w1 w2) ; przewód AND
  (define aux (make-wire (wire-sim w1)))
  (gate-and aux w1 w2)
  aux)

(define (wire-nand w1 w2) ; przewód NAND
  (define aux (make-wire (wire-sim w1)))
  (gate-nand aux w1 w2)
  aux)

(define (wire-or w1 w2) ; przewód OR
  (define aux (make-wire (wire-sim w1)))
  (gate-or aux w1 w2)
  aux)

(define (wire-nor w1 w2) ; przewód NOR
  (define aux (make-wire (wire-sim w1)))
  (gate-nor aux w1 w2)
  aux)

(define (wire-xor w1 w2) ;przewód XOR
  (define aux (make-wire (wire-sim w1)))
  (gate-xor aux w1 w2)
  aux)

; bramki logiczne

(define (gate-not w1 w2) ;bramka NOT
  (define (not-action)
    (sim-add-action! (wire-sim w1) 1
                     (lambda () (wire-set! w1 (not (wire-value w2))))))
  (wire-on-change! w2 not-action))

(define (gate-and w1 w2 w3) ;bramka AND
  (define (and-action)
    (sim-add-action! (wire-sim w1) 1
                     (lambda () (wire-set! w1 (and (wire-value w2) (wire-value w3))))))
  (wire-on-change! w2 and-action)
  (wire-on-change! w3 and-action))

(define (gate-nand w1 w2 w3) ;bramka NAND
  (define (nand-action)
    (sim-add-action! (wire-sim w1) 1
                     (lambda () (wire-set! w1 (nand (wire-value w2) (wire-value w3))))))
  (wire-on-change! w2 nand-action)
  (wire-on-change! w3 nand-action))

(define (gate-or w1 w2 w3) ; bramka OR
  (define (or-action)
    (sim-add-action! (wire-sim w1) 1
                     (lambda () (wire-set! w1 (or (wire-value w2) (wire-value w3))))))
  (wire-on-change! w2 or-action)
  (wire-on-change! w3 or-action))

(define (gate-nor w1 w2 w3) ; bramka NOR
  (define (nor-action)
    (sim-add-action! (wire-sim w1) 1
                     (lambda () (wire-set! w1 (nor (wire-value w2) (wire-value w3))))))
  (wire-on-change! w2 nor-action)
  (wire-on-change! w3 nor-action))

(define (gate-xor w1 w2 w3) ; bramka XOR
  (define (xor-action)
    (sim-add-action! (wire-sim w1) 2
                     (lambda () (wire-set! w1 (xor (wire-value w2) (wire-value w3))))))
  (wire-on-change! w2 xor-action)
  (wire-on-change! w3 xor-action))