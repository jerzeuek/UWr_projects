package Cells;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import GameplayAndLogic.Pawn;


/**
 * Klasa Cell reprezentuje pole na planszy, jest JButtonem
 */
public abstract class Cell extends JButton{
    
    /**
     * pionek który stoi na polu
     */
    private Pawn cellPawn;

    /**
     * Konstruktor podstawowego Cella ustawia że na polu nie ma żadnego pionka
     * i tworzy czarną obramówkę ze względów wizualnych.
     */
    public Cell() {
        this.cellPawn = null;
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    /**
     * @return Pionek który stoi na polu   
     */
    public Pawn getCellPawn() {
        return cellPawn;
    }

     /**
     * Zmienia pionek na polu.
     * @param curPawn Pionek który chcemy postawić na polu.
     */
    public void setCellPawn(Pawn curPawn) {
        this.cellPawn = curPawn;
    }
    
    
    // Metody abstrakcyjne:
    /**
     * Funkcja informująca czy można postawić na tym polu dany pionek.
     * @param curPawn Pionek który chcemy chcielibyśmy postawić.
     * @return True jeśli można postawić pionek, False w przeciwnym wypadku.
     */
    public abstract boolean checkCell(Pawn curPawn);
    /**
     * Funkcja dodająca dany pionek na pole.
     * @param curPawn pionek który chcemy dodać na pole.
     * @return Zbity pionek jeśli istniał.
     */
    public abstract Pawn addPawn(Pawn curPawn);
    /**
     * Funkcja usuwająca pionek z pola.
     * @return Usunięty pionek.
     */
    public abstract Pawn removePawn();

}
    
   
