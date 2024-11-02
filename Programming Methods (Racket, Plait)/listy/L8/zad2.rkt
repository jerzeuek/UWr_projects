#lang racket

(define mlist-rev (mcons 1 (mcons 2 (mcons 3 (mcons 4 null)))))

(define (mreverse! mlist)
  (define (it cur prv)
    (if (null? cur)
        prv
        (let ([nxt (mcdr cur)])
          (set-mcdr! cur prv)
          (it nxt cur))))
  (it mlist null))

(mreverse! mlist-rev)