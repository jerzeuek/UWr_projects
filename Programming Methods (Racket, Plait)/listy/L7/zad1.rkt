#lang plait

(define-type Tree23
  (leaf)
  (node2 [l : Tree23] [elem : Number] [r : Tree23])
  (node3 [l : Tree23] [l_elem : Number] [m : Tree23][r_elem : Number][r : Tree23]))

(define (height t)
  (type-case Tree23 t
    [(leaf) 1]
    [(node2 l elem r) (+ (height l) 1)]
    [(node3 l l_elem m r_elem r) (+ (height l) 1)]))

(define (max t)
  (type-case Tree23 t
    [(leaf) 0]
    [(node2 l elem r) (if (leaf? r) elem (max r))]
    [(node3 l l_elem m r_elem r) (if (leaf? r) r_elem (max r))]))

(define (min t)
  (type-case Tree23 t
    [(leaf) 0]
    [(node2 l elem r) (if (leaf? l) elem (max l))]
    [(node3 l l_elem m r_elem r) (if (leaf? l) l_elem (max l))]))

(define (my-Tree23? t)
  (type-case Tree23 t
    [(leaf) #t]
    [(node2 l elem r) (and (my-Tree23? l)
                           (my-Tree23? r)
                           (= (height l)(height r))
                           (or (< (max l) elem)(leaf? l))
                           (or (< elem (min r))(leaf? r)))]
    [(node3 l l_elem m r_elem r) (and (my-Tree23? l)
                                      (my-Tree23? m)
                                      (my-Tree23? r)
                                      (= (height l)(height m))
                                      (= (height m)(height r))
                                      (or (< (max l) l_elem)(leaf? l))
                                      (or (< l_elem (min m))(leaf? m))
                                      (or (< (max m) r_elem)(leaf? m))
                                      (or (< r_elem (min r))(leaf? r)))]))