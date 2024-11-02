package Cells;

import GameplayAndLogic.Pawn;
import java.awt.Color;

/**
 * ColoredSimpleCell to komórka używana jako komórka startowa.
 */ 
public class ColoredSimpleCell extends Cell {

    /**
     * Kolor pola.
     */
    private Color colorOfCell;

    /**
     * Konstruktor ustawia jako kolor pierwszoplanowy kolor oparty na kontraście z kolorem tła, i "włącza" pole.
     * @param colorOfCell Kolor tła.
     */
    public ColoredSimpleCell(Color colorOfCell) {
        super();
        this.colorOfCell = colorOfCell;
        setBackground(this.colorOfCell);

        if (this.colorOfCell == Color.green) {
            setForeground(Color.black);
        } else if (this.colorOfCell == Color.red) {
            setForeground(Color.black);
        } else if (this.colorOfCell == Color.yellow) {
            setForeground(Color.black);
        } else {
            setForeground(Color.black);
        }
        setEnabled(true);

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
     * Usuwa i zwraca pionek który stał na danym polu.
     * @return Usunięty pionek
     */
    @Override
   public Pawn removePawn() {
        setIcon(null);
        Pawn curCellPawn = getCellPawn();
        
        setCellPawn(null);
        return curCellPawn;
    }
}
