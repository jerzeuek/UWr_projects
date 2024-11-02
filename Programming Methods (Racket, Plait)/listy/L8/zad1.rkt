#lang racket

(define mlist-cyc (mcons 1 (mcons 2 (mcons 3 (mcons 4 null)))))

(define (cycle! mlist)
  (define (it mlist mlist_copy)
    (if (null? (mcdr mlist))
        (set-mcdr! mlist mlist_copy)
        (it (mcdr mlist) mlist_copy)))
  (it mlist mlist))

(cycle! mlist-cyc)
mlist-cyc