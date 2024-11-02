package GameMain;

import WindowInterface.WindowInterf;

/**
 * Klasa Main jest klasą główną.
 * 
 */
public class Main {

    /**
     * Główna funkcja, tworzy nowy WindowInterface.
     * @param args Nieużywane.
     */
    public static void main(String[] args) {
        new WindowInterf().setVisible(true);
    }
    
}
