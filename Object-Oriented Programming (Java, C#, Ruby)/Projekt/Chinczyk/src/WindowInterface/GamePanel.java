package WindowInterface;

import GameBoardData.GameBoardMap;
import GameplayAndLogic.Gameplay;
import GameplayAndLogic.Player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Klasa GamePanel jest używana do generowania planszy i okolicznych jej
 * elementów.
 */
public class GamePanel extends JPanel {
    private static Color background_color;
    private static int gameBoard_length;
    /**
     * Licznik rund.
     */
    private int roundCounter = -1;
    /**
     * Ścieżka do obrazka symbolizującego że trwa tura danego gracza.
     */
    private final String playImgPath = "Icons" + File.separator + "Status" + File.separator + "play.png";
    /**
     * Ścieżka do obrazka symbolizującego że nie trwa tura danego gracza.
     */
    private final String stopImgPath = "Icons" + File.separator + "Status" + File.separator + "stop.png";
    /**
     * Przechowuje obrazek symbolizujący że trwa tura danego gracza.
     */
    private Icon playImg = new ImageIcon(playImgPath);
    /**
     * Przechowuje obrazek symbolizujący że nie trwa tura danego gracza.
     */
    private Icon stopImg = new ImageIcon(stopImgPath);
    /**
     * Przechowuje informacje o polach planszy.
     */
    private GameBoardMap gameBoardMap;
    /**
     * Logika gry.
     */
    private Gameplay gameplay;
    /**
     * Panel na licznik rund.
     */
    private JLabel roundNumJLabel;
    /**
     * Panel przechowuujący imiona graczy i czyja tura teraz trwa.
     */
    private JPanel nowPlayJPanel;
    /**
     * Pole przetrzymujące imię i czy jest tura pierwszego gracza
     */
    private JCheckBox firstPLayerJCheckBox = null;
    /**
     * Pole przetrzymujące imię i czy jest tura drugiego gracza
     */
    private JCheckBox secondPLayerJCheckBox = null;
    /**
     * Pole przetrzymujące imię i czy jest tura trzeciego gracza
     */
    private JCheckBox thirdPLayerJCheckBox = null;
    /**
     * Pole przetrzymujące imię i czy jest tura czwartego gracza
     */
    private JCheckBox fourthPLayerJCheckBox = null;
    /**
     * Panel na przycisk rzucania kością.
     */
    private JPanel dicePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    /**
     * Panel na obrazek kości.
     */
    private JPanel diceImgPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    /**
     * Przycisk do rzutu kością
     */
    private JButton diceButton = new JButton("Rzuć kostką");
    /**
     * Label przetrzymujący obrazek kostki o danej liczbie oczek albo animację
     * "kręcącej się" kostki.
     */
    private JLabel diceImgLabel = new JLabel();

    /**
     * Konstruktor GamePanel ustawia kolor tła i używa metod
     * pomocniczych do stworzenia panelu.
     *
     * @param players Lista graczy.
     */
    public GamePanel(Player[] players) {
        GamePanel.gameBoard_length = 11;
        this.gameBoardMap = new GameBoardMap();
        setLayout(new BorderLayout());
        setBackground(Color.black);

        gameplay = new Gameplay(players, gameBoardMap, this);

        JPanel gameBoardPanel = createGameBoardPanel();
        JPanel roundAndPlayNowJPanel = createRoundAndPlayNowPanel();
        Box diceAndFinishBox = createDiceAndFinishMovePanel();

        fixColors();

        add(roundAndPlayNowJPanel, BorderLayout.LINE_START);
        add(gameBoardPanel, BorderLayout.CENTER);
        add(diceAndFinishBox, BorderLayout.LINE_END);

    }

    /**
     * @return Panel z planszą.
     *
     */
    private JPanel createGameBoardPanel() {

        JPanel gameBoardPanel = new JPanel(new GridLayout(gameBoard_length, gameBoard_length));
        gameBoardPanel.setBackground(background_color);

        for (int i = 1; i <= gameBoard_length * gameBoard_length; i++) {
            gameBoardPanel.add(this.gameBoardMap.getCell(i));
        }
        return gameBoardPanel;
    }

    /**
     * @return Panel z numerem rundy i informacją czyj jest ruch.
     *
     */
    private JPanel createRoundAndPlayNowPanel() {

        nowPlayJPanel = new JPanel(new BorderLayout(10, 10));
        nowPlayJPanel.add(Box.createRigidArea(new Dimension(150, 100)));

        roundNumJLabel = new JLabel("Runda: ", SwingConstants.CENTER);
        roundNumJLabel.setEnabled(false);
        roundNumJLabel.setFont(new Font("Arial", Font.PLAIN + Font.BOLD, 20));

        Box playersPanel = new Box(BoxLayout.Y_AXIS);
        playersPanel.add(Box.createVerticalGlue());
        playersPanel.add(Box.createRigidArea(new Dimension(14, 0)));
        playersPanel.add(roundNumJLabel);

        for (int i = 0; i < gameplay.getPlayers().size(); i++) {
            if (i == 0) {
                firstPLayerJCheckBox = new JCheckBox(" " + gameplay.getPlayers().get(i).getNickname());
                firstPLayerJCheckBox.setPreferredSize(new Dimension(150, 25));

                if ((gameplay.getPlayers().get(i).getColor() == Color.blue)
                        && (GamePanel.background_color == Color.black)) {
                    firstPLayerJCheckBox.setForeground(new Color(51, 51, 255));
                } else {
                    firstPLayerJCheckBox.setForeground(gameplay.getPlayers().get(i).getColor());
                }

                firstPLayerJCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
                firstPLayerJCheckBox.setBackground(GamePanel.background_color);
                firstPLayerJCheckBox.setIcon(stopImg);

            } else if (i == 1) {
                secondPLayerJCheckBox = new JCheckBox(" " + gameplay.getPlayers().get(i).getNickname());
                secondPLayerJCheckBox.setPreferredSize(new Dimension(150, 25));
                if ((gameplay.getPlayers().get(i).getColor() == Color.blue)
                        && (GamePanel.background_color == Color.black)) {
                    secondPLayerJCheckBox.setForeground(new Color(51, 51, 255));
                } else {
                    secondPLayerJCheckBox.setForeground(gameplay.getPlayers().get(i).getColor());
                }

                secondPLayerJCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
                secondPLayerJCheckBox.setBackground(GamePanel.background_color);
                secondPLayerJCheckBox.setIcon(stopImg);

            } else if (i == 2) {
                thirdPLayerJCheckBox = new JCheckBox(" " + gameplay.getPlayers().get(i).getNickname());
                thirdPLayerJCheckBox.setPreferredSize(new Dimension(150, 25));
                if ((gameplay.getPlayers().get(i).getColor() == Color.blue)
                        && (GamePanel.background_color == Color.black)) {
                    thirdPLayerJCheckBox.setForeground(new Color(51, 51, 255));
                } else {
                    thirdPLayerJCheckBox.setForeground(gameplay.getPlayers().get(i).getColor());
                }
                thirdPLayerJCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
                thirdPLayerJCheckBox.setBackground(GamePanel.background_color);
                thirdPLayerJCheckBox.setIcon(stopImg);
            } else if (i == 3) { //
                fourthPLayerJCheckBox = new JCheckBox(" " + gameplay.getPlayers().get(i).getNickname());
                fourthPLayerJCheckBox.setPreferredSize(new Dimension(150, 25));
                if ((gameplay.getPlayers().get(i).getColor() == Color.blue)
                        && (GamePanel.background_color == Color.black)) {
                    fourthPLayerJCheckBox.setForeground(new Color(51, 51, 255));
                } else {
                    fourthPLayerJCheckBox.setForeground(gameplay.getPlayers().get(i).getColor());
                }
                fourthPLayerJCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
                fourthPLayerJCheckBox.setBackground(GamePanel.background_color);
                fourthPLayerJCheckBox.setIcon(stopImg);
            }
        }

        playersPanel.add(Box.createVerticalGlue());

        for (int i = 0; i < gameplay.getPlayers().size(); i++) {
            if (i == 0) {
                playersPanel.add(Box.createRigidArea(new Dimension(15, 15)));
                playersPanel.add(firstPLayerJCheckBox);

            } else if (i == 1) {
                playersPanel.add(Box.createRigidArea(new Dimension(15, 15)));
                playersPanel.add(secondPLayerJCheckBox);

            } else if (i == 2) {
                playersPanel.add(Box.createRigidArea(new Dimension(15, 15)));
                playersPanel.add(thirdPLayerJCheckBox);

            } else if (i == 3) { //
                playersPanel.add(Box.createRigidArea(new Dimension(15, 15)));
                playersPanel.add(fourthPLayerJCheckBox);
            }
        }
        playersPanel.add(Box.createVerticalGlue());

        nowPlayJPanel.add(playersPanel, BorderLayout.CENTER);

        return nowPlayJPanel;

    }

    /**
     * @return Panel z kością i przyciskami.
     *
     */
    private Box createDiceAndFinishMovePanel() {

        Box diceBox = new Box(BoxLayout.Y_AXIS);
        diceBox.add(Box.createRigidArea(new Dimension(150, 100)));

        // Dice Area.
        dicePanel.setBackground(GamePanel.background_color);
        diceButton.doClick();

        diceButton.setEnabled(false);
        diceButton.addActionListener(new DiceListener());
        dicePanel.add(diceButton);

        diceImgPanel.setBackground(GamePanel.background_color);
        diceImgPanel.add(diceImgLabel);

        // Finish Panel
        JPanel finishMoveJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        finishMoveJPanel.setBackground(background_color);

        diceBox.add(Box.createVerticalGlue());
        diceBox.add(dicePanel);
        diceBox.add(Box.createVerticalGlue());
        diceBox.add(diceImgPanel);
        diceBox.add(Box.createVerticalGlue());
        diceBox.add(finishMoveJPanel);
        diceBox.add(Box.createVerticalGlue());

        return diceBox;
    }

    /**
     * Ustawia kontrast kolorów w panelu logów
     */
    private void fixColors() {
        roundNumJLabel.setBackground(background_color);
        nowPlayJPanel.setBackground(background_color);
        diceButton.setBackground(background_color);

        roundNumJLabel.setForeground(Color.white);
        nowPlayJPanel.setForeground(Color.white);
        diceButton.setForeground(Color.white);
    }

    /**
     * @return Długość planszy
     *
     */
    public int getGameBoard_length() {
        return gameBoard_length;
    }

    /**
     * @return Numer rundy.
     *
     */
    public int getRoundCounter() {
        return roundCounter;
    }

    /**
     * @return Gameplay.
     *
     */
    public Gameplay getGameplay() {
        return gameplay;
    }

    /**
     * @return Przycisk kości.
     *
     */
    public JButton getDiceButton() {
        return diceButton;
    }

    /**
     * @return Obrazek kości.
     *
     */
    public JLabel getDiceImgLabel() {
        return diceImgLabel;
    }

    /**
     * Rzuca kością, sprawdza czy są dostępne ruchy, jeśli tak to włącza możliwość
     * ruchu,
     * jeśli nie to od razu przechodzi do następnej osoby.
     */
    private class DiceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            gameplay.getPlayersDice().diceRoll(GamePanel.this);
            diceButton.setEnabled(false);
            diceImgLabel.setIcon(gameplay.getPlayersDice().getDiceIcon());

            // if have valiv moves
            if (gameplay.checkValidMoves(roundCounter % (gameplay.getPlayers().size()))) {
                gameplay.activeMouseGuide(roundCounter % (gameplay.getPlayers().size()));
            } else {
                finishMove();
            }
        }

    }

    /**
     * Używany do zwiększania licznika rund, pokazywania kogo teraz tura i
     * wyłączania wszystkich pól i
     * aktywacji tych na których stoją pionki obecnego gracza.
     */
    public void finishMove() {
        diceImgLabel.setIcon(gameplay.getPlayersDice().getGif());

        gameplay.disableMouseGuide();

        roundCounter++;

        roundNumJLabel.setText("Runda " + (roundCounter + 1));
        roundNumJLabel.setEnabled(true);

        for (int i = 0; i < gameplay.getPlayers().size(); i++) {

            if (roundCounter % (gameplay.getPlayers().size()) == i) {
                if (i == 0) {
                    firstPLayerJCheckBox.setIcon(playImg);
                } else if (i == 1) {
                    secondPLayerJCheckBox.setIcon(playImg);
                } else if (i == 2) {
                    thirdPLayerJCheckBox.setIcon(playImg);
                } else if (i == 3) { // i==3
                    fourthPLayerJCheckBox.setIcon(playImg);
                }
            } else {
                if (i == 0) {
                    firstPLayerJCheckBox.setIcon(stopImg);
                } else if (i == 1) {
                    secondPLayerJCheckBox.setIcon(stopImg);
                } else if (i == 2) {
                    thirdPLayerJCheckBox.setIcon(stopImg);
                } else if (i == 3) { // i==3
                    fourthPLayerJCheckBox.setIcon(stopImg);
                }
            }
        }
        gameplay.onCurrentOffOther(roundCounter % (gameplay.getPlayers().size()));
        diceButton.setEnabled(true);
        diceImgLabel.setVisible(true);
    }

}
