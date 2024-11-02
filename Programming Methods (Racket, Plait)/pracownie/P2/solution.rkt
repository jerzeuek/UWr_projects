#lang racket
(require data/heap)
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

;-------------------STRUKTURY---------------------------

; Symulacja
(struct sim ([time #:mutable] [acts #:mutable]))

; Przewód
(struct wire ([value #:mutable] [acts #:mutable] sim))

;-------------------SYMULACJA---------------------------

; Tworzenie nowej symulacji
(define (make-sim)
  (sim 0 (make-heap comparator)))

; Komparator do budowy kopca
(define (comparator x y) 
  (<= (car x) (car y)))

; Uruchamianie symulacji
(define (sim-wait! sim t) 
  (if (or
       (= 0 (heap-count (sim-acts sim)))
       (> (car (heap-min (sim-acts sim))) (+ t (sim-time sim))))

      (set-sim-time! sim (+ (sim-time sim) t))

      (let ([first-act (heap-min (sim-acts sim))])
        (begin
          (let ([next-t (- (+ (sim-time sim) t) (car first-act))])
            (heap-remove-min! (sim-acts sim))
            (set-sim-time! sim (car first-act))
            ((cdr first-act))
            (sim-wait! sim next-t))))))

; Dodawanie zdarzenia do kolejki
(define (sim-add-action! sim t act)
  (heap-add! (sim-acts sim) (cons (+ (sim-time sim) t) act)))

;-------------------PRZEWODY---------------------------

; Tworzenie nowego przewodu
(define (make-wire sim)
  (wire #f null sim))

; Dodawanie akcji do przewodu
(define (wire-on-change! wire new-act)
  (begin
     (set-wire-acts! wire (append (wire-acts wire) (list new-act)))
     (new-act)))

; Ustawianie wartości przewodu
(define (wire-set! wire val)
  (if (equal? (wire-value wire) val)
      (void)
      (begin
        (set-wire-value! wire val)
        (call (wire-acts wire)))))

; Wywoływanie akcji przewodu
(define (call acts)
  (if (null? acts)
      (void)
      (begin
        ((car acts))
        (call (cdr acts)))))

; Przewód NOT
(define (wire-not w)
  (define aux (make-wire (wire-sim w)))
  (gate-not aux w)
  aux)

; Przewód AND
(define (wire-and w1 w2) 
  (define aux (make-wire (wire-sim w1)))
  (gate-and aux w1 w2)
  aux)

; Przewód NAND
(define (wire-nand w1 w2) 
  (define aux (make-wire (wire-sim w1)))
  (gate-nand aux w1 w2)
  aux)

; Przewód OR
(define (wire-or w1 w2) 
  (define aux (make-wire (wire-sim w1)))
  (gate-or aux w1 w2)
  aux)

; Przewód NOR
(define (wire-nor w1 w2) 
  (define aux (make-wire (wire-sim w1)))
  (gate-nor aux w1 w2)
  aux)

; Przewód XOR
(define (wire-xor w1 w2) 
  (define aux (make-wire (wire-sim w1)))
  (gate-xor aux w1 w2)
  aux)

;-------------------BRAMKI LOGICZNE---------------------------

; Bramka NOT
(define (gate-not w1 w2)
  (define (aux-not)
    (sim-add-action! (wire-sim w1) 1
                     (lambda () (wire-set! w1 (not (wire-value w2))))))
  (wire-on-change! w2 aux-not))

; Bramka AND
(define (gate-and w1 w2 w3) 
  (define (aux-and)
    (sim-add-action! (wire-sim w1) 1
                     (lambda () (wire-set! w1 (and (wire-value w2) (wire-value w3))))))
  (wire-on-change! w2 aux-and)
  (wire-on-change! w3 aux-and))

; Bramka NAND
(define (gate-nand w1 w2 w3) 
  (define (aux-nand)
    (sim-add-action! (wire-sim w1) 1
                     (lambda () (wire-set! w1 (nand (wire-value w2) (wire-value w3))))))
  (wire-on-change! w2 aux-nand)
  (wire-on-change! w3 aux-nand))

; Bramka OR
(define (gate-or w1 w2 w3) 
  (define (aux-or)
    (sim-add-action! (wire-sim w1) 1
                     (lambda () (wire-set! w1 (or (wire-value w2) (wire-value w3))))))
  (wire-on-change! w2 aux-or)
  (wire-on-change! w3 aux-or))

; Bramka NOR
(define (gate-nor w1 w2 w3)
  (define (aux-nor)
    (sim-add-action! (wire-sim w1) 1
                     (lambda () (wire-set! w1 (nor (wire-value w2) (wire-value w3))))))
  (wire-on-change! w2 aux-nor)
  (wire-on-change! w3 aux-nor))

; bramka XOR
(define (gate-xor w1 w2 w3)
  (define (aux-xor)
    (sim-add-action! (wire-sim w1) 2
                     (lambda () (wire-set! w1 (xor (wire-value w2) (wire-value w3))))))
  (wire-on-change! w2 aux-xor)
  (wire-on-change! w3 aux-xor))