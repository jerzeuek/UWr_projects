package Cells;

import java.awt.Color;
import GameplayAndLogic.Pawn;

/**
 * InitCell przechowuje pionki przed wejściem na planszę.
 * 
 */
public class InitCell extends Cell {

    /**
     * Konstruktor InitCell ustawia kolor na biały i "włącza" pole.
     */
    public InitCell() {
        super();
        setBackground(Color.white);
        setEnabled(true);
    }

    /**
     * Zawsze zwraca prawdę 
     * @param curPawn Pionek który chcemy postawić.
     * @return Zawsze true.
     */
    @Override
    public boolean checkCell(Pawn curPawn) {
        return true;
    }

    /**
     * Dodaje pionek do pola
     * @param curPawn Pionek który chcemy dodać.
     * @return Zawsze null bo zawsze możemy postawić pionek.
     */
    @Override
    public Pawn addPawn(Pawn curPawn) {
        setIcon(curPawn.getPawnIcon());
        setCellPawn(curPawn);
        return null;
    }

    /**
     * Usuwa pionek z pola.
     * @return Usunięty pionek.
     */
    @Override
    public Pawn removePawn() {
        Pawn curCellPawn = getCellPawn();

        setIcon(null);
        setCellPawn(null);
        return curCellPawn;
    }

}
