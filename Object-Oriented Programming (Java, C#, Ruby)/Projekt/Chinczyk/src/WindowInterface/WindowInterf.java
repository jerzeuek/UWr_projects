package WindowInterface;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * WindowsInterf to główna klasa zarządzająca interfejsem graficznym gry.
 */
public class WindowInterf extends JFrame {

    private static final Color backgroundColor = Color.white;

    private static final CardLayout crdLyt = new CardLayout(10, 10);
    private static JPanel dynamicJPanel = new JPanel(crdLyt);
    private static SettingsPanel settingsPanel;
    private static GamePanel gamePanel;
    /**
     * JDialog który tworzy settingsPanel i przycisk startu.
     */
    private StartListener.StartJDialog startJDialog = null;

    /**
     * Konstruktor WindowsInterf ustawia podstawowe właściwości JFrame'a.
     */
    public WindowInterf() {

        setTitle("Chińczyk");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(1200, 850));
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setLayout(new BorderLayout());
        dynamicJPanel.setBackground(backgroundColor);
        add(dynamicJPanel, BorderLayout.CENTER);

        settingsPanel = new SettingsPanel();
        dynamicJPanel.add(settingsPanel);

        addWindowListener(new WindowCloseListener());

        pack();

    }

    // GET //
    /**
     * @return Kolor tła.
     */
    public static Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * StartListener tworzy JDialog i pokazuje go 
     */
    private class StartListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (startJDialog == null) {
                startJDialog = new StartJDialog(WindowInterf.this);
            }
            startJDialog.setVisible(true);

        }

        /**
         * StartJDialog to JDialog który tworzy settingsPanel i przycisk startu.
         */
        private class StartJDialog extends JDialog {

            SettingsPanel settingsJDialogPanel = new SettingsPanel();

            public StartJDialog(Frame owner) {
                super(owner, true);
                setTitle("Game Settings");
                setMinimumSize(new Dimension(800, 750));
                setLocationRelativeTo(owner);
                setResizable(false);
                setLayout(new BorderLayout());
                setBackground(backgroundColor);

                JButton startJButton = new JButton(" Start ");
                startJButton.addActionListener(new startGameButtonListener());

                Box startButtonBox = new Box(BoxLayout.X_AXIS);

                startButtonBox.add(Box.createHorizontalGlue());
                startButtonBox.add(startJButton);
                startButtonBox.add(Box.createHorizontalGlue());

                startButtonBox.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(null),
                        BorderFactory.createEmptyBorder(5, 0, 5, 0)));

                add(settingsJDialogPanel, BorderLayout.CENTER);
                add(startButtonBox, BorderLayout.PAGE_END);

            }

            /**
             * StartGameButtonListener przekazuje wartosci do gamePanel z JDialogGamePanel 
             */
            private class startGameButtonListener implements ActionListener {

                @Override
                public void actionPerformed(ActionEvent e) {

                    settingsPanel.setNicknamTextField(settingsJDialogPanel.getNicknamTextField());
                    settingsPanel.setColorComboBox(settingsJDialogPanel.getColorComboBox());

                    StartJDialog.this.dispose();

                }

            }

        }

    }


    /**
     * WindowCloseListener wyświetla okienko z pytaniem czy gracz chce wyłączyć grę. 
     */
    private class WindowCloseListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            int answer = JOptionPane.showConfirmDialog(WindowInterf.this,
                    "Czy na pewno chcesz wyłączyć grę?", "Wyłączanie gry",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (answer == JOptionPane.YES_OPTION) {
                WindowInterf.this.dispose();
            } else {
                // Do nothing.
            }
        }

    }
    /**
     * Rozpoczyna grę.
     */
    public static void gameStart(){
        gamePanel = settingsPanel.gamePanelCreator();
        dynamicJPanel.add(gamePanel);
        crdLyt.next(dynamicJPanel);
        gamePanel.finishMove();
    }

}
