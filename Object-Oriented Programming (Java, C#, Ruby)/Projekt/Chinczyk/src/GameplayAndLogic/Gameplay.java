package GameplayAndLogic;

import Cells.DecorativeCell;
import Cells.FinalCell;
import Cells.InitCell;
import GameBoardData.GameBoardMap;
import WindowInterface.GamePanel;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * Klasa Gameplay odpowiada za poprawne działanie gry zgodnie z zasadami,
 * i aktualizowanie wyświetlanego pola gry.
 */
public class Gameplay {

    private ArrayList<Player> players;
    private Dice gameDice;
    private GamePanel gamePanel;
    private GameBoardMap gameBoardMap;
    private int gameBoardSize;

    int[] greenInitPos;
    int[] redInitPos;
    int[] yellowInitPos;
    int[] blueInitPos;

    ArrayList<Integer> greenPathList;
    ArrayList<Integer> redPathList;
    ArrayList<Integer> yellowPathList;
    ArrayList<Integer> bluePathList;

    /**
     * Konstruktor Gameplay tworzy kostkę, generuje pola startowe i ścieżki dla graczy,
     * losuje kolejność gry, ustawia pionki na właściwych polach startowych.
     *
     * @param playersTable Wszyscy gracze.
     * @param gameBoardMap Struktura przechowująca dane o polach planszy.
     * @param gamePanel    Plansza i otaczające ją elementy.
     */
    public Gameplay(Player[] playersTable, GameBoardMap gameBoardMap, GamePanel gamePanel) {

        this.players = new ArrayList<>(playersTable.length);
        this.players.addAll(Arrays.asList(playersTable));
        this.gameBoardMap = gameBoardMap;
        this.gamePanel = gamePanel;

        this.gameDice = new Dice();

        this.gameBoardSize = this.gamePanel.getGameBoard_length();

        for (Player player : players) {
            if (player.getColor() == Color.green) {
                greenInitPos = new int[] { 13, 14, 24, 25 };
                greenPathList = greenPath();
            } else if (player.getColor() == Color.red) {
                redInitPos = new int[] { 20, 21, 31, 32 };
                redPathList = redPath();
            } else if (player.getColor() == Color.yellow) {
                yellowInitPos = new int[] { 90, 91, 101, 102 };
                yellowPathList = yellowPath();
            } else { // (players.get(i).getColor() == Color.blue)
                blueInitPos = new int[] { 97, 98, 108, 109 };
                bluePathList = bluePath();
            }
        }

        if(this.players.size() == 2){
            Collections.shuffle(this.players);
        }
        else{
            Player[] ord = new Player[4];
            for(int i = 0; i<this.players.size(); i++){
                if(this.players.get(i).getColor() == Color.green){
                    ord[0] = this.players.get(i); 
                } else if(this.players.get(i).getColor() == Color.red){
                    ord[1] = this.players.get(i); 
                } else if(this.players.get(i).getColor() == Color.blue){
                    ord[2] = this.players.get(i); 
                } else { //this.players.get(i).getColor() == Color.yellow
                    ord[3] = this.players.get(i); 
                }
            }

            ArrayList<Player> ordList = new ArrayList<>();
            for(int i = 0; i<4; i++){
                if(ord[i] != null){
                    ordList.add(ord[i]);
                }
            }
            Random rand = new Random();
            int firstPlayer = rand.nextInt(4);
            while(firstPlayer != 0){
                ordList.add(ordList.get(0));
                ordList.remove(0);
                firstPlayer--;
            }

            this.players = ordList;
        }

        initPawns(this.players.size(), greenInitPos, redInitPos, yellowInitPos, blueInitPos);

    }

    /**
     * @return Kostka.
     */
    public Dice getPlayersDice() {
        return gameDice;
    }

    /**
     * @return ArrayList zawierająca wszystkich graczy.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Stawia pionki każdego gracza na odpowiednie pola startowa.
     *
     * @param playersNum    Liczba graczy.
     * @param greenInitPos  // ArrayList z pozycjami startowymi zielonych pionków.
     * @param redInitPos    // ArrayList z pozycjami startowymi czerwonych pionków.
     * @param yellowInitPos // ArrayList z pozycjami startowymi żółtych pionków.
     * @param blueInitPos   // ArrayList z pozycjami startowymi niebieskich pionków.
     */
    private void initPawns(int playersNum, int[] greenInitPos, int[] redInitPos, int[] yellowInitPos,
            int[] blueInitPos) {

        for (int i = 0; i < playersNum; i++) {
            if (players.get(i).getColor() == Color.green) {
                for (int j = 0; j < players.get(i).getPanws().size(); j++) {
                    players.get(i).setPanws(j, greenInitPos[j]); // give number
                    gameBoardMap.getCell(greenInitPos[j]).addPawn(players.get(i).getPawn(j));
                }
            } else if (players.get(i).getColor() == Color.red) {
                for (int j = 0; j < players.get(i).getPanws().size(); j++) {
                    players.get(i).setPanws(j, redInitPos[j]);
                    gameBoardMap.getCell(redInitPos[j]).addPawn(players.get(i).getPawn(j));
                }
            } else if (players.get(i).getColor() == Color.yellow) {
                for (int j = 0; j < players.get(i).getPanws().size(); j++) {
                    players.get(i).setPanws(j, yellowInitPos[j]);
                    gameBoardMap.getCell(yellowInitPos[j]).addPawn(players.get(i).getPawn(j));
                }
            } else { // players.get(i).getColor()==Color.blue
                for (int j = 0; j < players.get(i).getPanws().size(); j++) {
                    players.get(i).setPanws(j, blueInitPos[j]);
                    gameBoardMap.getCell(blueInitPos[j]).addPawn(players.get(i).getPawn(j));
                }
            }
        }

    }

    /**
     * @return ArrayList zawierające ścieżkę dla zielonych pionków.
     */
    private ArrayList<Integer> greenPath() {
        ArrayList<Integer> greenPathArraylist = new ArrayList<>(
                Arrays.asList(46, 47, 48, 49, 38, 27, 16, 5, 6, 7, 18, 29, 40, 51, 52, 53, 54, 55, 66, 77, 76, 75, 74,
                        73, 84, 95, 106, 117, 116, 115, 104, 93, 82, 71, 70, 69, 68, 67, 56, 57, 58, 59, 60));
        return greenPathArraylist;
    }

    /**
     * @return ArrayList zawierające ścieżkę dla czerwonych pionków.
     */
    private ArrayList<Integer> redPath() {
        ArrayList<Integer> redPathArraylist = new ArrayList<>(
                Arrays.asList(18, 29, 40, 51, 52, 53, 54, 55, 66, 77, 76, 75, 74, 73, 84, 95, 106, 117, 116, 115, 104,
                        93, 82, 71, 70, 69, 68, 67, 56, 45, 46, 47, 48, 49, 38, 27, 16, 5, 6, 17, 28, 39, 50));
        return redPathArraylist;
    }

    /**
     * @return ArrayList zawierające ścieżkę dla żółtych pionków.
     */
    private ArrayList<Integer> yellowPath() {
        ArrayList<Integer> yellowPathArraylist = new ArrayList<>(
                Arrays.asList(104, 93, 82, 71, 70, 69, 68, 67, 56, 45, 46, 47, 48, 49, 38, 27, 16, 5, 6, 7, 18, 29, 40,
                        51, 52, 53, 54, 55, 66, 77, 76, 75, 74, 73, 84, 95, 106, 117, 116, 105, 94, 83, 72));
        return yellowPathArraylist;
    }

    /**
     * @return ArrayList zawierające ścieżkę dla niebieskich pionków.
     */
    private ArrayList<Integer> bluePath() {
        ArrayList<Integer> bluePathArraylist = new ArrayList<>(
                Arrays.asList(76, 75, 74, 73, 84, 95, 106, 117, 116, 115, 104, 93, 82, 71, 70, 69, 68, 67, 56, 45, 46,
                        47, 48, 49, 38, 27, 16, 5, 6, 7, 18, 29, 40, 51, 52, 53, 54, 55, 66, 65, 64, 63, 62));
        return bluePathArraylist;
    }

    /**
     * Dezaktywuje wszystkie pola oprócz tych dekoracyjnych, a potem aktywuje pola
     * na drodze obecnego gracza i jego pola startowe.
     * @param curPlayer Obecny gracz.
     */
    public void onCurrentOffOther(int curPlayer) {
        for (int i = 1; i < (gameBoardSize * gameBoardSize); i++) {

            if (!(gameBoardMap.getCell(i) instanceof DecorativeCell)) {
                gameBoardMap.getCell(i).setEnabled(false);
            }

        }
        if (players.get(curPlayer).getColor() == Color.green) {
            for (int i = 0; i < greenInitPos.length; i++) {
                gameBoardMap.getCell(greenInitPos[i]).setEnabled(true);
            }
            for (Integer greenPathList1 : greenPathList) {
                gameBoardMap.getCell(greenPathList1).setEnabled(true);
            }

        } else if (players.get(curPlayer).getColor() == Color.red) {

            for (int i = 0; i < redInitPos.length; i++) {
                gameBoardMap.getCell(redInitPos[i]).setEnabled(true);
            }
            for (Integer redPathList1 : redPathList) {
                gameBoardMap.getCell(redPathList1).setEnabled(true);
            }

        } else if (players.get(curPlayer).getColor() == Color.yellow) {
            for (int i = 0; i < yellowInitPos.length; i++) {
                gameBoardMap.getCell(yellowInitPos[i]).setEnabled(true);
            }
            for (Integer yellowPathList1 : yellowPathList) {
                gameBoardMap.getCell(yellowPathList1).setEnabled(true);
            }
        } else if (players.get(curPlayer).getColor() == Color.blue) {
            for (int i = 0; i < blueInitPos.length; i++) {
                gameBoardMap.getCell(blueInitPos[i]).setEnabled(true);
            }
            for (Integer bluePathList1 : bluePathList) {
                gameBoardMap.getCell(bluePathList1).setEnabled(true);
            }
        }
    }

    /**
     * Sprawdza czy gracz ma jakieś możliwe ruchy do wykonania.
     * @param curPlayer Obecny gracz.
     * @return True jeśli gracz ma jakieś możliwe ruchy do wykonania, False w przeciwnym wypadku.
     */
    public boolean checkValidMoves(int curPlayer) {

        int validMoveCounter = 0;

        ArrayList<Pawn> curPlayerPwns = players.get(curPlayer).getPanws();
        int pawnPos = -1;
        ArrayList<Integer> colorPath = null;

        if (players.get(curPlayer).getColor() == Color.green) {
            colorPath = greenPathList;
        } else if (players.get(curPlayer).getColor() == Color.red) {
            colorPath = redPathList;
        } else if (players.get(curPlayer).getColor() == Color.yellow) {
            colorPath = yellowPathList;
        } else if (players.get(curPlayer).getColor() == Color.blue) {
            colorPath = bluePathList;
        }

        int curCellPathPos;

        for (int i = 0; i < curPlayerPwns.size(); i++) {
            pawnPos = players.get(curPlayer).getPawn(i).getPosition();
            curCellPathPos = colorPath.indexOf(pawnPos);

            if ((gameBoardMap.getCell(pawnPos) instanceof InitCell) && gameDice.getDiceNum() == 6) {
                validMoveCounter++;
            } else if ((gameBoardMap.getCell(pawnPos) instanceof InitCell) && gameDice.getDiceNum() != 6) {

            } else {
                if (gameDice.getDiceNum() <= (colorPath.size() - (curCellPathPos + 1))) {
                    int eachCellPathCounter = 0; // see it more
                    for (int j = 1; j <= gameDice.getDiceNum(); j++) {
                        if (gameBoardMap.getCell(colorPath.get((curCellPathPos) + j))
                                .checkCell(players.get(curPlayer).getPawn(i))) {
                            eachCellPathCounter++;
                        }
                    }
                    if (gameDice.getDiceNum() == eachCellPathCounter) {
                        validMoveCounter++;
                    }
                }
            }
        }

        return validMoveCounter != 0;
    }

    /**
     * Aktywuje Listener dla komórek z pionkami obecnego gracza.
     * @param curPlayer Obecny gracz.
     */
    public void activeMouseGuide(int curPlayer) {
        for (int i = 1; i < gameBoardSize * gameBoardSize; i++) {

            if (!(gameBoardMap.getCell(i).getCellPawn() == null)) {
                Pawn cellPawn = gameBoardMap.getCell(i).getCellPawn();

                if (cellPawn.getOwner() == players.get(curPlayer)) {
                    int count = -1;

                    ArrayList<Pawn> tempPawns = players.get(curPlayer).getPanws();

                    for (Pawn tempPawn : tempPawns) {
                        count++;
                        if (tempPawn.getPosition() == cellPawn.getPosition()) {
                            break;
                        }
                    }
                    
                    if (players.get(curPlayer).getColor() == Color.green) {
                        gameBoardMap.getCell(i).addMouseListener(new MouseOverAndClickListener(count, i,
                                gameDice.getDiceNum(), players.get(curPlayer), gameBoardMap, greenPathList));
                    } else if (players.get(curPlayer).getColor() == Color.red) {
                        gameBoardMap.getCell(i).addMouseListener(new MouseOverAndClickListener(count, i,
                                gameDice.getDiceNum(), players.get(curPlayer), gameBoardMap, redPathList));

                    } else if (players.get(curPlayer).getColor() == Color.yellow) {
                        gameBoardMap.getCell(i).addMouseListener(new MouseOverAndClickListener(count, i,
                                gameDice.getDiceNum(), players.get(curPlayer), gameBoardMap, yellowPathList));

                    } else if (players.get(curPlayer).getColor() == Color.blue) {
                        gameBoardMap.getCell(i).addMouseListener(new MouseOverAndClickListener(count, i,
                                gameDice.getDiceNum(), players.get(curPlayer), gameBoardMap, bluePathList));
                    }
                }
            }

        }

    }

    /**
     * Usuwa MouseListenery ze wszystkich przycisków ze względów bezpieczeństwa.
     */
    public void disableMouseGuide() {

        for (int i = 1; i < gameBoardSize * gameBoardSize; i++) {

            for (MouseListener ml : gameBoardMap.getCell(i).getMouseListeners()) {
                gameBoardMap.getCell(i).removeMouseListener(ml);
            }

        }
    }

    /**
     * Sprawdza czy wszystkie pionki gracza są w domku, jeśli tak to kończy grę.
     */
    private void isGameFinished() {

        int curPos;
        int countFin;

        for (Player player : this.players) {
            countFin = 0;

            for (int j = 0; j < player.getPanws().size(); j++) {
                curPos = player.getPawn(j).getPosition();
                if (gameBoardMap.getCell(curPos) instanceof FinalCell) {
                    countFin++;
                }
            }
            if (countFin == player.getPanws().size()) {

                JOptionPane.showMessageDialog(gamePanel,
                        "Brawo " + player.getNickname() + ", jesteś zwycięzcą!",
                        "Koniec gry!", JOptionPane.INFORMATION_MESSAGE);

                System.exit(0);
            }
        }

    }

    /**
     * Klasa uzywana do wykrywania eventów wziązanych z ruchami myszą
     * (wjechanie, wyjechanie z pola, kliknięcie w nie).
     */
    private class MouseOverAndClickListener extends MouseAdapter {

        private int playerPawnNum;
        private int curCellNum;
        private int diceNum;
        private Player curPlayer;
        private GameBoardMap gameBoardMap;
        private ArrayList<Integer> colorPath;

        /**
         * Konstruktow MouseOverAndClickListener inicjalizuje pola klasy.
         */
        public MouseOverAndClickListener(int playerPawnNum, int curCellNum, int diceNum, Player curPlayer,
                GameBoardMap gameBoardMap, ArrayList<Integer> colorPath) {
            this.playerPawnNum = playerPawnNum;
            this.curCellNum = curCellNum;
            this.diceNum = diceNum;
            this.curPlayer = curPlayer;
            this.gameBoardMap = gameBoardMap;
            this.colorPath = colorPath;
        }

        /**
         * Kiedy kursor "wjedzie" na pole sprawdza podświetlone pole i pogrubia ramkę pola
         * na którze przemieściłby się pionek.
         * 
         * @param evt Nie uzywane.
         */
        @Override
        public void mouseEntered(MouseEvent evt) {
            Border borderMouseOver = new LineBorder(Color.black, 5);

            if ((gameBoardMap.getCell(curCellNum) instanceof InitCell)
                    && (gameBoardMap.getCell(curCellNum).getCellPawn() == null)) {
                gameBoardMap.getCell(curCellNum).setEnabled(false);
            } else if ((gameBoardMap.getCell(curCellNum) instanceof InitCell) && (diceNum == 6)) {
                gameBoardMap.getCell(colorPath.get(0)).setBorder(borderMouseOver);
            } else if ((gameBoardMap.getCell(curCellNum) instanceof InitCell) && (diceNum != 6)) {
                gameBoardMap.getCell(curCellNum).setEnabled(false);
            } else if (!(gameBoardMap.getCell(curCellNum).getCellPawn() == null)) {
                int curCellPathPos = colorPath.indexOf(curCellNum);

                if (diceNum <= (colorPath.size() - (curCellPathPos + 1))) {
                    gameBoardMap.getCell(colorPath.get(curCellPathPos + diceNum)).setBorder(borderMouseOver);
                } else {
                }

            }
        }

        /**
         * Kiedy kursor "wyjedzie" z pola sprawdza pole na którym była mysz i zmienia ramkę pola
         * na którze przemieściłby się pionek na normalną.
         * 
         * @param evt Nie uzywane.
         */
        @Override
        public void mouseExited(MouseEvent evt) {

            if ((gameBoardMap.getCell(curCellNum) instanceof InitCell)
                    && (gameBoardMap.getCell(curCellNum).getCellPawn() == null)) {
                gameBoardMap.getCell(curCellNum).setEnabled(false);
                gameBoardMap.getCell(colorPath.get(0)).setBorder(BorderFactory.createLineBorder(Color.black));
            } else if ((gameBoardMap.getCell(curCellNum) instanceof InitCell) && (diceNum == 6)) {
                gameBoardMap.getCell(colorPath.get(0)).setBorder(BorderFactory.createLineBorder(Color.black));
            } else if ((gameBoardMap.getCell(curCellNum) instanceof InitCell) && (diceNum != 6)) {
                gameBoardMap.getCell(curCellNum).setEnabled(true);
            } else { // if (!(gameBoardMap.getCell(curCellNum).getCellPawns().isEmpty())) {
                int curCellPathPos = colorPath.indexOf(curCellNum);
                if (diceNum <= (colorPath.size() - (curCellPathPos + 1))) {
                    gameBoardMap.getCell(colorPath.get(curCellPathPos + diceNum))
                            .setBorder(BorderFactory.createLineBorder(Color.black));
                } else {
                    // Do nothnig
                }

            }

        }

        /**
         * Kiedy mysz kliknie na pole to przeprowada całą logikę związaną z ruszaniem danego pionka
         * (czy może się tam ruszyć, czy bije, czy wychodzi z pola startowego, czy rzuca jeszcze raz...)
         * 
         * @param evt Nie używane.
         */
        @Override
        public void mouseClicked(MouseEvent evt) {
            Pawn playerPawn = gameBoardMap.getCell(curCellNum).getCellPawn();

            if (playerPawn != null) {

                if ((gameBoardMap.getCell(curCellNum) instanceof InitCell) && diceNum == 6) {

                    if (gameBoardMap.getCell(colorPath.get(0)).checkCell(playerPawn)) {
                        gameBoardMap.getCell(curCellNum).removePawn();

                        curPlayer.setPanws(playerPawnNum, colorPath.get(0));
                        playerPawn.setPosition(colorPath.get(0));

                        Pawn catchPawn = gameBoardMap.getCell(colorPath.get(0)).addPawn(playerPawn);
                        gameBoardMap.getCell(colorPath.get(0)).setBorder(BorderFactory.createLineBorder(Color.black));

                        if (catchPawn != null) {

                            ArrayList<Pawn> catchPlayerPwans = catchPawn.getOwner().getPanws();

                            int catchPawnPos = -1;
                            for (Pawn catchPlayerPwan : catchPlayerPwans) {
                                catchPawnPos++;
                                if (catchPlayerPwan == catchPawn) {
                                    break;
                                }
                            }

                            if (catchPawn.getOwner().getColor() == Color.green) {
                                catchPawn.getOwner().getPawn(catchPawnPos).setPosition(greenInitPos[catchPawnPos]);
                                gameBoardMap.getCell(greenInitPos[catchPawnPos]).addPawn(catchPawn);
                            } else if (catchPawn.getOwner().getColor() == Color.red) {
                                catchPawn.getOwner().getPawn(catchPawnPos).setPosition(redInitPos[catchPawnPos]);
                                gameBoardMap.getCell(redInitPos[catchPawnPos]).addPawn(catchPawn);
                            } else if (catchPawn.getOwner().getColor() == Color.yellow) {
                                catchPawn.getOwner().getPawn(catchPawnPos).setPosition(yellowInitPos[catchPawnPos]);
                                gameBoardMap.getCell(yellowInitPos[catchPawnPos]).addPawn(catchPawn);
                            } else if (catchPawn.getOwner().getColor() == Color.blue) {
                                catchPawn.getOwner().getPawn(catchPawnPos).setPosition(blueInitPos[catchPawnPos]);
                                gameBoardMap.getCell(blueInitPos[catchPawnPos]).addPawn(catchPawn);
                            }
                        }

                        disableMouseGuide();
                        gamePanel.getDiceImgLabel().setIcon(getPlayersDice().getGif());
                        gamePanel.getDiceButton().setEnabled(true);

                    }
                } else if ((gameBoardMap.getCell(curCellNum) instanceof InitCell) && diceNum != 6) {
                } else {
                    boolean hasMoved = true;
                    int curCellPathPos = colorPath.indexOf(curCellNum);

                    if (diceNum <= (colorPath.size() - (curCellPathPos + 1))) {

                        if (gameBoardMap.getCell(colorPath.get(curCellPathPos + diceNum)).checkCell(playerPawn)) {

                            gameBoardMap.getCell(curCellNum).removePawn();

                            curPlayer.setPanws(playerPawnNum, colorPath.get(curCellPathPos + diceNum));
                            playerPawn.setPosition(colorPath.get(curCellPathPos + diceNum));
                            Pawn catchPawn = gameBoardMap.getCell(colorPath.get(curCellPathPos + diceNum))
                                    .addPawn(playerPawn);
                            gameBoardMap.getCell(colorPath.get(curCellPathPos + diceNum))
                                    .setBorder(BorderFactory.createLineBorder(Color.black));

                            if (catchPawn != null) {

                                ArrayList<Pawn> catchPlayerPwans = catchPawn.getOwner().getPanws();

                                int catchPawnPos = -1;
                                for (Pawn catchPlayerPwan : catchPlayerPwans) {
                                    catchPawnPos++;
                                    if (catchPlayerPwan == catchPawn) {
                                        break;
                                    }
                                }

                                if (catchPawn.getOwner().getColor() == Color.green) {
                                    catchPawn.getOwner().getPawn(catchPawnPos)
                                            .setPosition(greenInitPos[catchPawnPos]);
                                    gameBoardMap.getCell(greenInitPos[catchPawnPos]).addPawn(catchPawn);
                                } else if (catchPawn.getOwner().getColor() == Color.red) {
                                    catchPawn.getOwner().getPawn(catchPawnPos)
                                            .setPosition(redInitPos[catchPawnPos]);
                                    gameBoardMap.getCell(redInitPos[catchPawnPos]).addPawn(catchPawn);
                                } else if (catchPawn.getOwner().getColor() == Color.yellow) {
                                    catchPawn.getOwner().getPawn(catchPawnPos)
                                            .setPosition(yellowInitPos[catchPawnPos]);
                                    gameBoardMap.getCell(yellowInitPos[catchPawnPos]).addPawn(catchPawn);
                                } else if (catchPawn.getOwner().getColor() == Color.blue) {
                                    catchPawn.getOwner().getPawn(catchPawnPos)
                                            .setPosition(blueInitPos[catchPawnPos]);
                                    gameBoardMap.getCell(blueInitPos[catchPawnPos]).addPawn(catchPawn);
                                }
                            }

                            if (gameBoardMap
                                    .getCell(colorPath.get(curCellPathPos + diceNum)) instanceof FinalCell) {

                                ArrayList<Pawn> myPawns = curPlayer.getPanws();
                                for (Pawn myPawns1 : myPawns) {
                                    if (myPawns1 == playerPawn) {
                                        break;
                                    }
                                }
                            }
                        } else {
                            hasMoved = false;
                        }

                    } else {
                        hasMoved = false;
                    }

                    if (diceNum == 6 && hasMoved) {

                        disableMouseGuide();
                        gamePanel.getDiceImgLabel().setIcon(getPlayersDice().getGif());

                        gamePanel.getDiceButton().setEnabled(true);
                    }
                    else if(hasMoved) {
                        gamePanel.finishMove();
                    }
                }
            }

            isGameFinished();
        }

    }

}
