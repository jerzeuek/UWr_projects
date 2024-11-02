#lang racket

;(* (+ 2 2) 5) -> (* 4 5) -> 20

;(* (+ 2 2) (5) ) -> BŁĄD! 5 nie jest procedurą, nie powinna być w nawiasach!

;(*(+(2 2) 5) ) -> BŁĄD!  W nawiasie (2 2) nie ma żadnej procedury!
;Dodatkowo procedura iloczynu ma tylko jeden argument!

;(*(+ 2
;   2) 5) -> (* 4 5) -> 20

;(5 * 4) -> BŁĄD! W Rackecie używamy formy prefiksowej, 5 to nie procedura!

;(5 * (2 + 2) ) -> BŁĄD! Jak wyżej!

;((+ 2 3) ) -> (5) -> BŁĄD! 5 nie jest procedurą, nie powinna być w nawiasach!

;+ -> #<procedure:+>

;( define + (* 2 3) ) -> (define + 6) -> nic nie wypisuje

;+ -> 6

;(* 2 +) -> (* 2 6) -> 12

;( define ( five ) 5) -> nic nie wypisuje

;( define four 4) -> nic nie wypisuje

;( five ) -> 5

;four -> 4

;five -> #<procedure:five>

;( four ) -> (4) -> BŁĄD! 5 nie jest procedurą, nie powinna być w nawiasach!