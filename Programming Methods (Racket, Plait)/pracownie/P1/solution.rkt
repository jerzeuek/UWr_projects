#lang racket

(provide (struct-out column-info)
         (struct-out table)
         (struct-out and-f)
         (struct-out or-f)
         (struct-out not-f)
         (struct-out eq-f)
         (struct-out eq2-f)
         (struct-out lt-f)
         table-insert
         table-project
         table-sort
         table-select
         table-rename
         table-cross-join
         table-natural-join
)

(define-struct column-info (name type) #:transparent)

(define-struct table (schema rows) #:transparent)

(define cities
  (table
   (list (column-info 'city    'string)
         (column-info 'country 'string)
         (column-info 'area    'number)
         (column-info 'capital 'boolean))
   (list (list "Wrocław" "Poland"  293 #f)
         (list "Warsaw"  "Poland"  517 #t)
         (list "Poznań"  "Poland"  262 #f)
         (list "Berlin"  "Germany" 892 #t)
         (list "Munich"  "Germany" 310 #f)
         (list "Paris"   "France"  105 #t)
         (list "Rennes"  "France"   50 #f))))

(define countries
  (table
   (list (column-info 'country 'string)
         (column-info 'population 'number))
   (list (list "Poland" 38)
         (list "Germany" 83)
         (list "France" 67)
         (list "Spain" 47))))

(define (empty-table columns) (table columns '()))

; Wstawianie
(define (table-insert row tab)
  (cond
    [(not (= (length row) (length (table-schema tab)))) (error "Zla ilosc kolumn!")]
    [(not (check row (table-schema tab))) (error "Zly typ elementu!")]
    [else (table (table-schema tab) (cons row (table-rows tab)))]
  ))

; Konwertowanie symbolu na predykat
(define (convert-insert type)
  (cond
    [(equal? type 'number) number?]
    [(equal? type 'string) string?]
    [(equal? type 'symbol) symbol?]
    [(equal? type 'boolean) boolean?]
    [else (error "Nie ma takiego typu!")]))

; Sprawdzanie czy typ elementu sie zgadza
(define (check row types)
  (cond
    [(null? types) #t]
    [((convert-insert (column-info-type (car types))) (car row)) (check (cdr row) (cdr types))]
    [else #f]))

; Projekcja
(define (table-project cols tab)
  (if (null? cols)
       (table '() '())
       (let ([indexes (map (lambda (x) (find-index x (table-schema tab) 0)) cols)])
         (table
          (create-schema (table-schema tab) '() indexes)
          (map (lambda (x) (fltr x '() indexes)) (table-rows tab))))))

; Tworzenie listy kolumn dla projekcji
(define (create-schema schema nschema indexes)
  (if
   (null? indexes) nschema
   (create-schema schema (append nschema (list(list-ref schema (car indexes)))) (cdr indexes))))

;Filtrowanie rzedow dla projekcji
(define (fltr row nrow indexes)
  (if
   (null? indexes) nrow
   (fltr row (append nrow (list(list-ref row (car indexes)))) (cdr indexes))))
                         
; Szukanie indeksow na podstawie listy kolumn
(define (find-index col schema acc)
  (cond
   [(null? schema) (error "Nie ma takiej kolumny!")] 
   [(equal? col (column-info-name (car schema))) acc]
   [else (find-index col (cdr schema) (+ acc 1))]))


; Sortowanie
(define (table-sort cols tab)
  (if
   (null? cols) tab
   (let ([indexes (map (lambda (x) (find-index x (table-schema tab) 0)) cols)])
     (table (table-schema tab) (sorting (table-schema tab) (table-rows tab) indexes)))))

;Konwertowanie symboli na predykaty sortowania
(define (convert-sort type)
  (cond
    [(equal? type 'number) <]
    [(equal? type 'string) string<?]
    [(equal? type 'symbol) symbol<?]
    [(equal? type 'boolean) boolean<?]
    [else (error "Nie ma takiego typu!")]))

(define (sorting schema rows indexes)
  (if
   (null? indexes) rows
   (sorting schema
            (sort rows #:key (lambda (x) (list-ref x (last indexes)))
                  (convert-sort (column-info-type (list-ref schema (last indexes)))))
            (remove (last indexes) indexes))))

                                                                             
(define (boolean<? bool1 bool2)
   (and (equal? bool1 #f) (equal? bool2 #t)))

; Selekcja

(define-struct and-f (l r))
(define-struct or-f (l r))
(define-struct not-f (e))
(define-struct eq-f (name val))
(define-struct eq2-f (name name2))
(define-struct lt-f (name val))

(define (table-select form tab)
  (table (table-schema tab)
         (filter (lambda (x) (parse x (table-schema tab) form)) (table-rows tab)))) 

(define (parse row schema form)
  (cond
    [(and-f? form) (and (parse row schema (and-f-l form)) (parse row schema (and-f-r form)))]
    [(or-f? form) (or (parse row schema (or-f-l form)) (parse row schema (or-f-r form)))]
    [(not-f? form) (not (parse row schema (not-f-e form)))]
    [(eq-f? form) (equal? (list-ref row (find-index (eq-f-name form) schema 0))(eq-f-val form))]
    [(eq2-f? form) (equal? (list-ref row (find-index (eq2-f-name form) schema 0))
                           (list-ref row (find-index (eq2-f-name2 form) schema 0)))]
    [(lt-f? form) ((convert-sort (find-type (lt-f-name form) schema))
                   (list-ref row (find-index (lt-f-name form) schema 0)) (lt-f-val form))]
    [else (error "Zła formuła!")]))

(define (find-type name schema)
  (if
   (equal? name (column-info-name (car schema)))
   (column-info-type (car schema))
   (find-type name (cdr schema))))

; Zmiana nazwy

(define (table-rename col ncol tab)
  (table
   (change col ncol '() (table-schema tab))
   (table-rows tab)))

(define (change col ncol new old)
  (cond
    [(null? old) (error "Nie ma takiej kolumny!")]
    [(equal? (column-info-name (car old)) col)
     (append new (cons (column-info ncol (column-info-type (car old))) (cdr old)))]
    [else (change col ncol (append new (list (car old))) (cdr old))]))

;Złączenie kartezjańskie

(define (table-cross-join tab1 tab2)
  (if
   (or (null? (table-rows tab1)) (null? (table-rows tab2)))
   (empty-table (append (table-schema tab1) (table-schema tab2)))
   (table
    (append (table-schema tab1) (table-schema tab2))
    (apply append (map (lambda (x) (join x (table-rows tab2) '())) (table-rows tab1))))))

;Łączenie rzędów

(define (join row1 rows2 newrows)
  (if
   (null? rows2) newrows
   (join row1 (cdr rows2) (append newrows (list (append row1 (car rows2)))))))

; Złączenie

(define (table-natural-join tab1 tab2)
  (let ([pairs (apply append (map (lambda (x) (same-name x (table-schema tab1) (table-schema tab2) 0))
                                  (table-schema tab1)))])
    (let ([tab2-new (change-names tab2 (map (lambda (x) (cdr x)) pairs))])
      (let ([good-rows (find-good-rows (table-cross-join tab1 tab2-new)
                                       (map (lambda (x) (cons (car x) (+ (cdr x) (length (table-schema tab1))))) pairs))])
        (let ([good-cols (filter (lambda (x) (not (member x
                                                 (map (lambda (x) (+ (cdr x) (length (table-schema tab1)))) pairs))))
                                (build-list (length (table-schema good-rows)) values))])
          (table-project (map (lambda (x) (column-info-name (list-ref (table-schema good-rows) x))) good-cols) good-rows))))))
      
(define (same-name col1 schema1 schema2 acc)
  (cond
   [(null? schema2) null]
   [(equal? (column-info-name col1) (column-info-name (car schema2)))
    (list(cons (find-index (column-info-name col1) schema1 0) acc))]
   [else (same-name col1 schema1 (cdr schema2) (+ acc 1))]))

(define (change-names tab indexes)
  (if
   (null? indexes) tab
   (let ([orig-name (column-info-name (list-ref (table-schema tab) (car indexes)))])
   (change-names (table-rename orig-name (string->symbol (string-append (symbol->string orig-name) "2")) tab) (cdr indexes)))))

(define (find-good-rows tab indexes)
  (if
   (null? indexes) tab
   (find-good-rows (table-select (eq2-f (column-info-name (list-ref (table-schema tab) (car (car indexes))))
                                        (column-info-name (list-ref (table-schema tab) (cdr (car indexes))))) tab) (cdr indexes))))