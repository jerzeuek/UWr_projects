package prezentacja;

import rozgrywka.*;
import wyjatki.*;
import obliczenia.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.*;

public class Okno extends Frame {
    Gra gra;
    TextField licznikTextField;
    TextField mianownikTextField;
    Button sprawdzLiczbeButton;
    Button poddajSieButton;
    Button nowaGraButton;
    Button zamknijGreButton;
    Scrollbar wykorzystaneProbyScrollbar;
    Scrollbar wyborZakresuScrollbar;
    Label przedzialyLabel;
    Label bledyLabel;
    Label wykorzystaneProbyLabel;
    Label wyborZakresuLabel;
    Handler fileHandler;
    private static final Logger logger = Logger.getLogger(Okno.class.getName());

    public Okno() {
         try{
            ClassLoader classLoader = Okno.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("logging.properties");
            LogManager.getLogManager().readConfiguration(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.log(Level.INFO, "Uruchomiono aplikację.");

        licznikTextField = new TextField(10);
        licznikTextField.setFont(new Font("Arial", Font.PLAIN, 25));

        mianownikTextField = new TextField(10);
        mianownikTextField.setFont(new Font("Arial", Font.PLAIN, 25));

        sprawdzLiczbeButton = new Button("Sprawdź liczbę");
        wykorzystaneProbyScrollbar = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 20);
        wyborZakresuScrollbar = new Scrollbar(Scrollbar.HORIZONTAL, 5, 1, 5, 21);
        poddajSieButton = new Button("Poddaj się");
        nowaGraButton = new Button("Nowa gra");
        zamknijGreButton = new Button("Zamknij grę");

        przedzialyLabel = new Label("");
        przedzialyLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        bledyLabel = new Label("");
        bledyLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        wykorzystaneProbyLabel = new Label("Wykorzystane próby:");
        wykorzystaneProbyLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        wyborZakresuLabel = new Label("Zakres: 5");
        wyborZakresuLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(przedzialyLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(licznikTextField, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(sprawdzLiczbeButton, gbc);
        sprawdzLiczbeButton.setEnabled(false);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(mianownikTextField, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        add(bledyLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(wyborZakresuLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.ipadx = 350;
        add(wyborZakresuScrollbar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(wykorzystaneProbyLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.ipadx = 350;
        add(wykorzystaneProbyScrollbar, gbc);
        wykorzystaneProbyScrollbar.setEnabled(false);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        add(nowaGraButton, gbc);

        gbc.gridx = 1;
        add(poddajSieButton, gbc);
        poddajSieButton.setEnabled(false);

        gbc.gridx = 2;
        add(zamknijGreButton, gbc);

        setBackground(Color.LIGHT_GRAY);
        setSize(500, 300);
        setResizable(false);
        setVisible(true);

        sprawdzLiczbeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                try {
                    int k = Integer.parseInt(licznikTextField.getText());
                    int m = Integer.parseInt(mianownikTextField.getText());

                    if (m > gra.getZakres()) {
                        throw new TooBigDenominatorException(
                                "Podano za duży mianownik!");
                    }

                    if (k <= 0 || k >= m) {
                        throw new OutsideRangeException("Liczba jest poza zakresem (0; 1)!");
                    }
                    Wymierna proba = new Wymierna(k, m);
                    bledyLabel.setText("Chcesz podać liczbę: " + proba);

                } catch (OutsideRangeException | TooBigDenominatorException | ZeroDenominatorException e) {
                    bledyLabel.setText(e.getMessage());
                }

                catch (NumberFormatException e) {
                    bledyLabel.setText("W jednym z pól nie ma liczby!");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bledyLabel.setText("");
            }
        });

        sprawdzLiczbeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheck();
            }
        });

        poddajSieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleResignation();
            };
        });

        nowaGraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleStart();
            }
        });

        zamknijGreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.log(Level.INFO, "Zamknięto aplikację.");
                System.exit(0);
            }
        });

        wykorzystaneProbyScrollbar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                wykorzystaneProbyScrollbar.setValue(gra.getLicznik());
            }
        });

        wyborZakresuScrollbar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                wyborZakresuLabel.setText("Zakres: " + wyborZakresuScrollbar.getValue());
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logger.log(Level.INFO, "Zamknięto aplikację.");
                System.exit(0);
            }
        });

    }

    public void handleStart() {
        gra = new Gra();
        gra.start(wyborZakresuScrollbar.getValue());
        logger.log(Level.INFO, "Rozpoczęcie nowej gry.");
        switchButtons();
        wykorzystaneProbyScrollbar.setValue(0);
        System.out.println(gra.getLiczba());
        przedzialyLabel.setText("Przedział w którym znajduje się liczba to (" + gra.getMinPrzedzialu() + " ;"
                + gra.getMaksPrzedzialu() + ")");
        wykorzystaneProbyScrollbar.setMaximum(gra.getMaksProb());
    }

    public void handleCheck() {
        try {
            int k = Integer.parseInt(licznikTextField.getText());
            int m = Integer.parseInt(mianownikTextField.getText());

            if (m > gra.getZakres()) {
                throw new TooBigDenominatorException("W mianowniku jest liczba większa od maksymalnej dopuszczalnej!");
            }

            if (k <= 0 || k >= m) {
                throw new OutsideRangeException("Podano liczbę poza zakresem (0; 1)!");
            }

            Wymierna proba = new Wymierna(k, m);

            licznikTextField.setText("");
            mianownikTextField.setText("");

            if (proba.equals(gra.getLiczba())) {
                handleWin();
            } else {
                gra.incrementLicznik();
                wykorzystaneProbyScrollbar.setValue(gra.getLicznik());

                if (proba.compareTo(gra.getLiczba()) < 0) {
                    logger.log(Level.INFO, "Podano liczbę " + proba + ", jest mniejsza od szukanej.");
                    przedzialyLabel.setText("Za mało!");

                    if (proba.compareTo(gra.getMinPrzedzialu()) > 0) {
                        gra.setMinPrzedzialu(new Wymierna(proba.getLicznik(), proba.getMianownik()));
                    }
                }

                else {
                    logger.log(Level.INFO, "Podano liczbę " + proba + ", jest większa od szukanej.");
                    przedzialyLabel.setText("Za dużo!");

                    if (proba.compareTo(gra.getMaksPrzedzialu()) < 0) {
                        gra.setMaksPrzedzialu(new Wymierna(proba.getLicznik(), proba.getMianownik()));
                    }
                }

                przedzialyLabel.setText(przedzialyLabel.getText() + " Przedział w którym znajduje się liczba to ("
                        + gra.getMinPrzedzialu() + ";" + gra.getMaksPrzedzialu() + ")");
            }

            if (gra.getLicznik() >= gra.getMaksProb()) {
                handleLose();
            }
        }

        catch (OutsideRangeException | TooBigDenominatorException | ZeroDenominatorException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Wpisanego ciągu znaków nie da się przekonwertować na liczbę wymierną!");
        }
    }

    public void handleWin() {
        logger.log(Level.INFO, "Gracz wygrał.");
        przedzialyLabel.setText("Wygrałeś, szukana liczba to " + gra.getLiczba());
        switchButtons();
    }

    public void handleLose() {
        logger.log(Level.INFO, "Gracz przegrał, szukana liczba to " + gra.getLiczba());
        przedzialyLabel.setText("Przegrałeś, szukana liczba to " + gra.getLiczba());
        switchButtons();
    }

    public void handleResignation() {
        logger.log(Level.INFO, "Gracz poddał się, szukana liczba to " + gra.getLiczba());
        przedzialyLabel.setText("Poddałeś się, szukana liczba to " + gra.getLiczba());
        switchButtons();
    }

    public void switchButtons() {
        wykorzystaneProbyScrollbar.setEnabled(!wykorzystaneProbyScrollbar.isEnabled());
        poddajSieButton.setEnabled(!poddajSieButton.isEnabled());
        sprawdzLiczbeButton.setEnabled(!sprawdzLiczbeButton.isEnabled());
        wyborZakresuScrollbar.setEnabled(!wyborZakresuScrollbar.isEnabled());
        nowaGraButton.setEnabled(!nowaGraButton.isEnabled());
    }

    public static void main(String[] args) throws Exception {
        new Okno();
    }
}
