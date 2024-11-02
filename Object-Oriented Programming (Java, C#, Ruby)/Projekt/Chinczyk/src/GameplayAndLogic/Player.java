package GameplayAndLogic;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Klasa Player reprezentuje gracza. Gracz ma przypisane imię, kolor i
 * ArrayList pionków.
 */
public class Player {

    // Basic class fields.
    private String nickname;
    private Color color;
    private ArrayList<Pawn> panws;

    /**
     * Konstruktor Player przypisuje graczowi pionki
     * @param nickname Imię gracza. 
     * @param color Kolor gracza.
     */
    public Player(String nickname, Color color) {
        this.nickname = nickname;
        this.color = color;
        this.panws = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            this.panws.add(new Pawn(0, this));
        }
    }

    /**
     * @return Imię gracza.
     */
    public String getNickname() {
        return this.nickname;
    }

     /**
     *
     * @return Kolor gracza.
     */
    public Color getColor() {
        return this.color;
    }

     /**
     * 
     * @return Kolor gracza jako String.
     */
    public String getColorText() {

        if (this.color == Color.green) {
            return "Zielony";
        } else if (this.color == Color.red) {
            return "Czerwony";
        } else if (this.color == Color.yellow) {
            return "Żółty";
        } else {
            return "Niebieski";
        }        

    }

     /**
     * @return ArrayList z pionkami gracza.
     */
    public ArrayList<Pawn> getPanws() {
        return panws;
    }

     /**
     * Zwraca konkretny pionek gracza.
     * @param pawnNum Numer pionka.
     * @return Pionek przypisany do numeru.
     */
    public Pawn getPawn(int pawnNum) {
        return panws.get(pawnNum);
    }

     /**
     * Ustawia konkretny pionek na nowym miejscu.
     * @param pawnNum Numer pionka.
     * @param pos Nowa pozycja pionka.
     */
    public void setPanws(int pawnNum, int pos) {
        this.panws.get(pawnNum).setPosition(pos);
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
        final Player other = (Player) obj;
        return (this.nickname.equals(other.nickname));
    }

}
