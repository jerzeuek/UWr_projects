#lang racket

(provide (struct-out column-info)
         (struct-out table))

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

(define test
  (sort (sort (table-rows cities) #:key (lambda (x) (list-ref x 0)) string<?)
        #:key (lambda (x) (list-ref x 1)) string<?))

;'(country city) -> najpierw city, potem country - od tyłu!


TESTY:

Insert:
(table-rows ( table-insert ( list "Rzeszow" "Poland" 129
#f) cities ))

Project:
(table-project '(city country) cities)

Rename:
(table-rename 'city 'name cities)

Sort:
(table-rows (table-sort '(country city) cities))

Select:
(table-rows (table-select (and-f (eq-f 'capital #t)
                                 (not-f (lt-f 'area
                                              300)))
                          cities))

Cross-join:
(table-cross-join cities
                  (table-rename 'country 'country2 countries))