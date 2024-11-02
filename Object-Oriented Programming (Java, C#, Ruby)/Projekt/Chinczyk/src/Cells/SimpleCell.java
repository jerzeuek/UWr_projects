package Cells;

import java.awt.Color;
import GameplayAndLogic.Pawn;

/**
 * SimpleCell jest białym polem po którym poruszamy się na planszy.
 * 
 */
public class SimpleCell extends Cell {

    /**
     * Konstruktor SimpleCell ustawia biały kolor.
     */
    public SimpleCell() {
        super();
        setBackground(Color.white);
    }

    /**
     * Informuje czy możemy na tym polu postawić dany pionek
     * @param curPawn Pionek który chcemy postawić na polu.
     * @return True jeśli pole jest puste lub ma na sobie pionek innego koloru, False w przeciwnym wypadku.
     */
    @Override
    public boolean checkCell(Pawn curPawn) {
        Pawn curCellPawn = getCellPawn();
        return getCellPawn() == null || curCellPawn.getColor() != curPawn.getColor();
    }

    /**
     * Dodaje pionek do pola, zmienia ikonę na ikonę nowego pionka
     * i usuwa stary pionek jeśli należy do innego gracza.
     * @param curPawn Pionek który chcemy dodać.
     * @return Zbity pionek jeśli istniał, null jeśli pole było puste.
     */
    @Override
    public Pawn addPawn(Pawn curPawn) {
        Pawn curCellPawn = getCellPawn();
        Pawn returnPawn = null;

        if (curCellPawn == null) {
            setCellPawn(curPawn);
            setIcon(curPawn.getPawnIcon());
        } else {
            if (!curCellPawn.getOwner().equals(curPawn.getOwner())) {
                returnPawn = removePawn();
            }
                setCellPawn(curPawn);
                setIcon(curPawn.getPawnIcon());
        }
        return returnPawn;
    }

    /**
     * Usuwa pionek z pola.
     * @return Usunięty pionek.
     */
    @Override
    public Pawn removePawn() {
        setIcon(null);
        Pawn curCellPawn = getCellPawn();
        
        setCellPawn(null);
        return curCellPawn;
    }

}
