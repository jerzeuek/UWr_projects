package Cells;

import GameplayAndLogic.Pawn;
import java.awt.Color;

/**
 * DecorativeCell jest używane jako dekoracja pól na których nie możemy stawać
 * (pola otaczające pola startowe oraz pole środkowe)
 * 
 */
public class DecorativeCell extends Cell {

    /**
     * Konstruktor DecorativeCell może przyjąć wiele różnych kolorów w zależności od parametru
     * 
     * @param colorOfCell    Kolor jaki ma przyjąć pole.
     */
    public DecorativeCell(Color colorOfCell) {

        setBackground(colorOfCell);
        setEnabled(false);
    }

    /**
     * Nigdy nie używane dla DecorativeCell
     * 
     * @param curPawn Pionek który chcemy postawić na polu.
     * @return Zawsze false bo na tych polach nie stawiamy pionków.
     */
    @Override
    public boolean checkCell(Pawn curPawn) {
        return false;
    }

    /**
     * Nigdy nie używane dla DecorativeCell
     * 
     * @param curPawn Pionek który chcemy dodać na pole.
     * @return Zawsze null bo na tych polach nie dodajemy pionków.
     */
    @Override
    public Pawn addPawn(Pawn curPawn) {
        return null;
    }

    /**
     * Nigdy nie używane dla DecorativeCell
     * 
     * @return Zawsze false bo na tych polu nigdy nie będzie pionka.
     */
    @Override
    public Pawn removePawn() {
        return null;
    }

}
