package GameplayAndLogic;

import java.awt.Color;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Klasa Pawn reprezentuje pionek, ma on przypisaną pozycję (numer pola), właściciela (gracza) i ikonę.
 */
public class Pawn {
    private final String greenPawnImgPath = "Icons" + File.separator + "Pawns" + File.separator + "greenPawn.png";
    private final String redPawnImgPath = "Icons" + File.separator + "Pawns" + File.separator + "redPawn.png";
    private final String yellowPawnImgPath = "Icons" + File.separator + "Pawns" + File.separator + "yellowPawn.png";
    private final String bluePawnImgPath = "Icons" + File.separator + "Pawns" + File.separator + "bluePawn.png";

    private Icon greenPawnImg;
    private Icon redPawnImg;
    private Icon yellowPawnImg;
    private Icon bluePawnImg;

    private int position;
    private Player owner;

    /**
     * Konstruktor Pawn inicjalizuje pozycję, przypisuje właściciela i tworzy
     * odpowiednią ikonę przypisaną do koloru właściciela.
     * Pawn Constructor init position, define owner and create the right pawn
     *
     * @param position Numer pola na planszy.
     * @param owner Gracz posiadający tego pionka.
     */
    public Pawn(int position, Player owner) {
        this.position = position;
        this.owner = owner;

        if (this.owner.getColor() == Color.green) {
            greenPawnImg = new ImageIcon(greenPawnImgPath);
        } else if (this.owner.getColor() == Color.red) {
            redPawnImg = new ImageIcon(redPawnImgPath);
        } else if (this.owner.getColor() == Color.yellow) {
            yellowPawnImg = new ImageIcon(yellowPawnImgPath);
        } else // this.owner.getColor()== Color.blue
        {
            bluePawnImg = new ImageIcon(bluePawnImgPath);
        }
    }

    /**
     * @return Właściciel pionka.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * @return Kolor pionka.
     */
    public Color getColor() {
        return (this.owner.getColor());
    }

    /**
     * @return Ikona pionka.
     */
    public Icon getPawnIcon() {
        Icon pawnIcon = null;

        if (this.owner.getColor() == Color.green) {
            pawnIcon = greenPawnImg;
        } else if (this.owner.getColor() == Color.red) {
            pawnIcon = redPawnImg;
        } else if (this.owner.getColor() == Color.yellow) {
            pawnIcon = yellowPawnImg;
        } else // this.owner.getColor()== Color.blue
        {
            pawnIcon = bluePawnImg;
        }

        return pawnIcon;
    }

    /**
     * @return Pozycja pionka będąca numerem pola.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Ustawia pozycję pionka.
     *
     * @param position Pozycja pionka będąca numerem pola.
     */
    public void setPosition(int position) {
        this.position = position;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Player == false) {
            return false;
        }
        final Pawn other = (Pawn) obj;
        if (this.owner.equals(other.owner)) {
            if (this.position == other.position) {
                return true;
            }
        }

        return false;
    }

}
