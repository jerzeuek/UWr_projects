
package Cells;

import java.awt.Color;
import GameplayAndLogic.Pawn;

/**
 * FinalCell jest używane do domków, kiedy wszystkie pionki któregoś gracza stoją na tych polach 
 * to gra się kończy i ten gracz jest zwycięzcą. 
 * 
 */
public class FinalCell extends Cell{
    /**
     * Kolor pola.
     */
     private Color colorOfCell;

    /**
     * Konstruktor FinalCella może ustawia kolor pierwszoplanowy oparty na kontraście z kolorem
     * tła i "włącza" pole.
     * @param colorOfCell Kolor jaki ma przyjąć pole.
     */
    public FinalCell(Color colorOfCell) {
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
            setForeground(Color.white);
        }
        setEnabled(true);
    }
    
     /**
     * Informuje czy możemy na tym polu postawić dany pionek
     * @param curPawn Pionek który chcemy postawić na polu
     * @return True jeśli pole jest puste, False w przeciwnym wypadku.
     */
    @Override
    public boolean checkCell(Pawn curPawn) {
        Pawn curCellPawn = getCellPawn();
        return curCellPawn == null;
    }

    /**
     * Dodaje pionek do pola, zmienia ikonę na ikonę nowego pionka
     * i usuwa stary pionek jeśli tam stał.
     * @param curPawn Pionek który chcemy dodać.
     * @return Stary pionek jeśli istniał, null jeśli pole było puste.
     */
    @Override
    public Pawn addPawn(Pawn curPawn) {
        Pawn returnPawn = null;
        Pawn curCellPawn = getCellPawn();

        if (curCellPawn == null) {
            setCellPawn(curPawn);
            setIcon(curPawn.getPawnIcon());
        } else {
                returnPawn = removePawn();
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
