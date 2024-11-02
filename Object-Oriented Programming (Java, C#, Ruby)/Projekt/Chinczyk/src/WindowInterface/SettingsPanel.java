package WindowInterface;

import GameplayAndLogic.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

/**
 * Klasa SettingsPanel używana jest do tworzenia panelu ustawień, tutaj gracze podają swoje
 * imiona i wybierają kolor pionków.
 */
public class SettingsPanel extends JPanel {

    private static final Color backgroundColour = WindowInterf.getBackgroundColor();
    /**
     * Label do nazwy na ekran początkowy.
     */
    private JLabel welcomeText = new JLabel("Gra planszowa \"Chińczyk\"", SwingConstants.CENTER);
    /**
     * Labele zawierające imiona graczy.
     */
    private JLabel[] nicknameLabel = new JLabel[4];
    /**
     * Pola tekstowe na imiona graczy.
     */
    private JTextField[] nicknamTextField = new JTextField[4];
    /**
     * Labele zawierające kolory.
     */
    private JLabel[] colorLabel = new JLabel[4];
    /**
     * ComboBoxy zawierające nazwy kolorów do wyboru dla graczy.
     */
    private JComboBox<JComboBox<String>> colorComboBox = new JComboBox<>();
    /**
     * Przycisk rozpoczynający grę.
     */
    private JButton submitButton = new JButton("Zacznij grę");

    /**
     * Konstruktor SettingsPanel ustawia kolor tła oraz używa
     * metod pomocniczych aby stworzyć panel.
     */
    public SettingsPanel() {

        setLayout(new GridLayout(4, 1));
        setBackground(WindowInterf.getBackgroundColor());
        welcomeText.setFont(new Font("Arial", Font.PLAIN + Font.BOLD, 60));
        add(welcomeText);
        Box inputAndSettingSPanel = new Box(BoxLayout.Y_AXIS);
        inputAndSettingSPanel.add(createPlayersFormPanel());
        add(inputAndSettingSPanel);

        add(new JLabel());

        submitButton.setFont(new Font("Arial", Font.PLAIN, 40));
        submitButton.addActionListener(new SubmitListener());
        add(submitButton);

    }

    /**
     * 
     * @return GamePanel
     */
    public GamePanel gamePanelCreator() {
        int playersCounter = 0;
        for (JTextField nicknamTextFieldFE : nicknamTextField) {
            if (!nicknamTextFieldFE.getText().isEmpty()) {
                playersCounter++;
            }
        }

        Player[] players = new Player[playersCounter];
        playersCounter = -1;
        for (int i = 0; i < nicknamTextField.length; i++) {
            if (!nicknamTextField[i].getText().isEmpty()) {
                playersCounter++;
                String pawnColorStr = (String) colorComboBox.getItemAt(i).getSelectedItem();
                Color pawnColor;
                if (pawnColorStr.compareTo("Zielony") == 0) {
                    pawnColor = Color.green;
                } else if (pawnColorStr.compareTo("Czerwony") == 0) {
                    pawnColor = Color.red;
                } else if (pawnColorStr.compareTo("Żółty") == 0) {
                    pawnColor = Color.yellow;
                } else { // pawnColorStr.compareTo("Niebieski") == 0
                    pawnColor = Color.blue;
                }

                players[playersCounter] = new Player(nicknamTextField[i].getText(), pawnColor);

            }
        }

        return (new GamePanel(players));

    }

    /**
     * Inicjalizuje ComboBoxy na kolory
     */
    private void createComboBoxes() {

        for (int i = 0; i < 4; i++) {
            ((JLabel) colorComboBox.getItemAt(i).getRenderer()).setHorizontalAlignment(JLabel.CENTER);
            colorComboBox.getItemAt(i).addItem("Zielony");
            colorComboBox.getItemAt(i).addItem("Czerwony");
            colorComboBox.getItemAt(i).addItem("Żółty");
            colorComboBox.getItemAt(i).addItem("Niebieski");

            colorComboBox.getItemAt(i).setMaximumRowCount(4);
            colorComboBox.getItemAt(i).setSelectedIndex(-1);
        }

    }

    /**
     * Tworzy panel na podawanie danych graczy.
     */
    private JPanel createPlayersFormPanel() {

        for (int i = 0; i < 4; i++) {
            nicknameLabel[i] = new JLabel("Imię gracza " + (i + 1) + ": ", JLabel.RIGHT);
            nicknamTextField[i] = new JTextField(20);
            nicknamTextField[i].setHorizontalAlignment(JTextField.CENTER);

            colorLabel[i] = new JLabel("Kolor gracza " + (i + 1) + ": ", JLabel.RIGHT);
            colorComboBox.insertItemAt(new JComboBox<>(), i);
        }

        createComboBoxes();

        JPanel playersInputPanel = new JPanel(new GridLayout(7, 4));
        playersInputPanel.setBackground(backgroundColour);
        playersInputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                "Dane graczy (wymaganych minimum dwóch)", TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Arial", Font.PLAIN + Font.BOLD + Font.ITALIC, 15), Color.BLACK));

        for (int i = 0; i < 4; i++) {
            playersInputPanel.add(Box.createGlue());
        }

        playersInputPanel.add(nicknameLabel[0]);
        playersInputPanel.add(nicknamTextField[0]);
        playersInputPanel.add(nicknameLabel[1]);
        playersInputPanel.add(nicknamTextField[1]);

        playersInputPanel.add(colorLabel[0]);
        playersInputPanel.add(colorComboBox.getItemAt(0));
        playersInputPanel.add(colorLabel[1]);
        playersInputPanel.add(colorComboBox.getItemAt(1));

        for (int i = 0; i < 4; i++) {
            playersInputPanel.add(Box.createGlue());
        }

        playersInputPanel.add(nicknameLabel[2]);
        playersInputPanel.add(nicknamTextField[2]);
        playersInputPanel.add(nicknameLabel[3]);
        playersInputPanel.add(nicknamTextField[3]);

        playersInputPanel.add(colorLabel[2]);
        playersInputPanel.add(colorComboBox.getItemAt(2));
        playersInputPanel.add(colorLabel[3]);
        playersInputPanel.add(colorComboBox.getItemAt(3));

        for (int i = 0; i < 4; i++) {
            playersInputPanel.add(Box.createGlue());
        }

        return playersInputPanel;
    }

    // GETTERS //
    /**
     * 
     * @return Pola tekstowe z imionami graczy.
     */
    public JTextField[] getNicknamTextField() {
        return nicknamTextField;
    }
    /**
     * @return ComboBoxy z kolorami graczy.
     */
    public JComboBox<JComboBox<String>> getColorComboBox() {
        return colorComboBox;
    }
    /**
     * Ustawia pola tekstowe z imionami graczy
     * @param nicknamTextField pola tekstowe z imionami graczy
     */
    public void setNicknamTextField(JTextField[] nicknamTextField) {
        this.nicknamTextField = nicknamTextField;
    }

    /**
     * Ustawia ColorBoxy z kolorami graczy
     * @param colorComboBox ColorBoxy z kolorami graczy
     */
    public void setColorComboBox(JComboBox<JComboBox<String>> colorComboBox) {
        this.colorComboBox = colorComboBox;
    }

    /**
     * Listner na przycisk rozpoczęcia gry, sprawdza czy każdy gracz 
     * który ma wybrany kolor ma wpisane imię i vice versa, czy mają
     * unikalne imiona i czy jest minimum dwóch graczy. Jeśli nie to generuje
     * okienko ze wszystkimi nieprawidłowościami.
     */
    private class SubmitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            boolean checkFlag = false;
            int counter = 0;
            String errorMessage = "";

            for (int i = 0; i < 4; i++) {
                if ((!nicknamTextField[i].getText().isEmpty()) && (colorComboBox.getItemAt(i).getSelectedIndex() != -1)) {
                    counter++;
                }
            }

            if (counter < 2) {
                errorMessage += "Nie wystarczająca ilość graczy.\n";
            }

            // At least two players.
            if (counter >= 2) {
                checkFlag = true;
                for (int i = 0; i < 4; i++) {
                    if ((!nicknamTextField[i].getText().isEmpty()) && (colorComboBox.getItemAt(i).getSelectedIndex() == -1)) {
                        checkFlag = false;
                    }
                    if ((nicknamTextField[i].getText().isEmpty()) && (colorComboBox.getItemAt(i).getSelectedIndex() != -1)) {
                        checkFlag = false;
                    }
                }

                if (checkFlag == false) {
                    errorMessage += "Są podane imiona bez kolorów albo na odwrót.\n";
                }

                if (isUniqueUsersName() == false) {
                    checkFlag = false;
                    errorMessage += "Podane imiona nie są unikalne.\n";
                }

                if (isUniqueColors() == false) {
                    checkFlag = false;
                    errorMessage += "Wybrane kolory nie są unikalne.\n";
                }

            }

            if (checkFlag == false) {
                JOptionPane.showMessageDialog(SettingsPanel.this, errorMessage,
                            "Podano złe dane!", JOptionPane.ERROR_MESSAGE);
            } else {

                // Disable fields.
                for (int i = 0; i < 4; i++) {
                    nicknamTextField[i].setEnabled(false);
                    colorComboBox.getItemAt(i).setEnabled(false);
                }
                submitButton.setEnabled(false);
                WindowInterf.gameStart();

            }

        }

        /**
         * Sprawdza czy kolory wybrane przez graczy są unikatowe
         * @return True jeśli kolory są unikatore, False w przeciwnym przypadku.
         */
        private boolean isUniqueColors() {
            int counter;
            for (int i = 0; i < 4; i++) {
                counter = 0;
                if (colorComboBox.getItemAt(i).getSelectedIndex() != -1) {
                    for (int j = 0; j < 4; j++) {
                        if (colorComboBox.getItemAt(i).getSelectedIndex() == colorComboBox.getItemAt(j).getSelectedIndex()) {
                            counter++;
                        }
                    }
                    if (counter >= 2) {
                        return false;
                    }
                }
            }

            return true;
        }

        /**
         * Sprawdza czy imiona wpisane przez graczy są unikatowe
         * @return True jeśli imiona są unikatore, False w przeciwnym przypadku.
         */
        private boolean isUniqueUsersName() {
            int counter;
            for (int i = 0; i < 4; i++) {
                counter = 0;
                if (!nicknamTextField[i].getText().isEmpty()) {
                    for (int j = 0; j < 4; j++) {
                        if (nicknamTextField[i].getText().compareTo(nicknamTextField[j].getText()) == 0) {
                            counter++;
                        }
                    }
                    if (counter >= 2) {
                        return false;
                    }
                }

            }

            return true;
        }
    }
}
