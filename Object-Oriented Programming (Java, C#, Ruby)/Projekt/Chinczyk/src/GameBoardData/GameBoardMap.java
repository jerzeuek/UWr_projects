package GameBoardData;

import Cells.Cell;
import Cells.ColoredSimpleCell;
import Cells.FinalCell;
import Cells.SimpleCell;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Klasa GameBoardMap jest używana do przechowywania pól planszy w dwóch TreeMapach: 
 * jednej opartej na pozycji pola (i, j) i komórce, drugiej opartej na pozycji pola licząc ją
 * jako które z kolei jest to pole na planszy oraz pozycji pola (i, j) 
 *
 */
public class GameBoardMap {

    private Color background_color;

    private TreeMap<CellPos, Cell> gameBoardMap;
    private TreeMap<Integer, CellPos> gameBoardPosMap;

    /**
     * Konstruktor GameBoardMap tworzy dwa TreeMapy i zapisuje do nich dane.
     */
    public GameBoardMap() {
        this.gameBoardMap = new TreeMap<>(new colAndRowComparator());
        this.gameBoardPosMap = new TreeMap<>();

        createBoardCells();
        createBoardPosMap();
    }

    /**
     * Dodaje odpowiednie pola na odpowiednie pozycje.
     */
    private void createBoardCells() {
        ArrayList<Integer> initPositions = new ArrayList<>(
                Arrays.asList(13, 14, 20, 21, 24, 25, 31, 32, 90, 91, 97, 98, 101, 102, 108, 109));
        ArrayList<Integer> greenDecPositions = new ArrayList<>(
                Arrays.asList(1, 2, 3, 4, 12, 15, 23, 26, 34, 35, 36, 37));
        ArrayList<Integer> redDecPositions = new ArrayList<>(
                Arrays.asList(8, 9, 10, 11, 19, 22, 30, 33, 41, 42, 43, 44));
        ArrayList<Integer> blueDecPositions = new ArrayList<>(
                Arrays.asList(85, 86, 87, 88, 96, 99, 107, 110, 118, 119, 120, 121));
        ArrayList<Integer> yellowDecPositions = new ArrayList<>(
                Arrays.asList(78, 79, 80, 81, 89, 92, 100, 103, 111, 112, 113, 114));
        ArrayList<Integer> greenColPositions = new ArrayList<>(Arrays.asList(46));
        ArrayList<Integer> redColPositions = new ArrayList<>(Arrays.asList(18));
        ArrayList<Integer> blueColPositions = new ArrayList<>(Arrays.asList(76));
        ArrayList<Integer> yellowColPositions = new ArrayList<>(Arrays.asList(104));
        ArrayList<Integer> simplePositions = new ArrayList<>(Arrays.asList(5, 6, 7, 29, 40, 51, 52, 53, 54, 55, 66, 77,
                75, 74, 73, 84, 95, 106, 117, 116, 115, 93, 82, 71, 70, 69, 68, 67, 56, 45, 47, 48, 49, 38, 27, 16));
        ArrayList<Integer> finalGreen = new ArrayList<>(Arrays.asList(57, 58, 59, 60));
        ArrayList<Integer> finalRed = new ArrayList<>(Arrays.asList(17, 28, 39, 50));
        ArrayList<Integer> finalYellow = new ArrayList<>(Arrays.asList(72, 83, 94, 105));
        ArrayList<Integer> finalBlue = new ArrayList<>(Arrays.asList(62, 63, 64, 65));

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                int cellNum = (11 * i) + j + 1;
                if (initPositions.contains(cellNum)) {
                    this.gameBoardMap.put(new CellPos(i, j), new Cells.InitCell());
                } else if (greenDecPositions.contains(cellNum)) {
                    this.gameBoardMap.put(new CellPos(i, j), new Cells.DecorativeCell(Color.green));
                } else if (redDecPositions.contains(cellNum)) {
                    this.gameBoardMap.put(new CellPos(i, j), new Cells.DecorativeCell(Color.red));
                } else if (blueDecPositions.contains(cellNum)) {
                    this.gameBoardMap.put(new CellPos(i, j), new Cells.DecorativeCell(Color.blue));
                } else if (yellowDecPositions.contains(cellNum)) {
                    this.gameBoardMap.put(new CellPos(i, j), new Cells.DecorativeCell(Color.yellow));
                } else if (greenColPositions.contains(cellNum)) {
                    this.gameBoardMap.put(new CellPos(i, j), new ColoredSimpleCell(Color.green));
                } else if (redColPositions.contains(cellNum)) {
                    this.gameBoardMap.put(new CellPos(i, j), new ColoredSimpleCell(Color.red));
                } else if (blueColPositions.contains(cellNum)) {
                    this.gameBoardMap.put(new CellPos(i, j), new ColoredSimpleCell(Color.blue));
                } else if (yellowColPositions.contains(cellNum)) {
                    this.gameBoardMap.put(new CellPos(i, j), new ColoredSimpleCell(Color.yellow));
                } else if (simplePositions.contains(cellNum)) {
                    this.gameBoardMap.put(new CellPos(i, j), new SimpleCell());
                } else if (finalGreen.contains(cellNum)) {
                    this.gameBoardMap.put(new CellPos(i, j), new FinalCell(Color.green));
                } else if (finalRed.contains(cellNum)) {
                    this.gameBoardMap.put(new CellPos(i, j), new FinalCell(Color.red));
                } else if (finalBlue.contains(cellNum)) {
                    this.gameBoardMap.put(new CellPos(i, j), new FinalCell(Color.blue));
                } else if (finalYellow.contains(cellNum)) {
                    this.gameBoardMap.put(new CellPos(i, j), new FinalCell(Color.yellow));
                } else {
                    this.gameBoardMap.put(new CellPos(i, j),
                            new Cells.DecorativeCell(background_color));
                }

            }

        }

    }

    /**
     * Przechodzi przez całe TreeMap gameBoardMap i przepisuje do drugiego TreeMapa w odpowiednim formacie.
     */
    private void createBoardPosMap() {

        int counter = 0;
        for (Map.Entry<CellPos, Cell> entry : gameBoardMap.entrySet()) {
            counter++;
            gameBoardPosMap.put(counter, entry.getKey());
        }

    }

    /**
     * Getter zwracający pole przypisane do odpowiedniego numeru.
     * @param cellNum Numer pola.
     * @return Pole przypisane do odpowiedniego numeru.
     */
    public Cell getCell(int cellNum) {
        CellPos tempCell = gameBoardPosMap.get(cellNum);
        return gameBoardMap.get(tempCell);
    }

    /**
     * CellPos to klasa pomocnicza reprezentująca pozycję w tablicy.
     */
    private class CellPos {

        private int row;
        private int col;

        public CellPos(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

    }

    /**
     * Komparator który zapewnie odpowiednią kolejność pozycji w TreeMapie gameBoardMap.
     */
    private class colAndRowComparator implements Comparator<CellPos> {

        @Override
        public int compare(CellPos ep, CellPos ep1) {

            if (ep.getRow() < ep1.getRow()) {
                return -1;
            } else if (ep.getRow() > ep1.getRow()) {
                return 1;
            } else {
                return (ep.getCol() - ep1.getCol());
            }
        }
    }

}
