// Ksawery Plis
// Lista 7
// javac 17.0.8.1

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class PojazdEditor extends JComponent {

    public PojazdEditor(Pojazd pojazd, String fileName) {
        JTextField markaField = new JTextField(pojazd.getMarka(), 20);
        JTextField maxPredkoscField = new JTextField(Double.toString(pojazd.getMaxPredkosc()), 20);
        JTextField liczbaPasazerowField = new JTextField(Integer.toString(pojazd.getLiczbaPasazerow()), 20);

        JButton zapiszButton = new JButton("Zapisz");
        JButton anulujButton = new JButton("Anuluj");

        setLayout(new BorderLayout());

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(3, 2));
        fieldsPanel.add(new JLabel("Marka:"));
        fieldsPanel.add(markaField);
        fieldsPanel.add(new JLabel("Maksymalna prędkość:"));
        fieldsPanel.add(maxPredkoscField);
        fieldsPanel.add(new JLabel("Liczba pasażerów:"));
        fieldsPanel.add(liczbaPasazerowField);
        add(fieldsPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(zapiszButton);
        buttonsPanel.add(anulujButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        zapiszButton.addActionListener(e -> {
            pojazd.setMarka(markaField.getText());
            pojazd.setMaxPredkosc(Double.parseDouble(maxPredkoscField.getText()));
            pojazd.setLiczbaPasazerow(Integer.parseInt(liczbaPasazerowField.getText()));
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            frame.dispose();

            System.out.println("Po edycji:");
            System.out.println(pojazd);
            try (ObjectOutputStream vehicleStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
                vehicleStream.writeObject(pojazd);
            } catch (IOException err) {
                err.printStackTrace();
            }
        });

        anulujButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            frame.dispose();
        });
    }

    public static void callEditor(Pojazd pojazd, String fileName) {
        System.out.println("Edytujemy: ");
        System.out.println(pojazd);
        PojazdEditor editor = new PojazdEditor(pojazd, fileName);
        JFrame frame = new JFrame("Edycja pojazdu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(editor);
        frame.pack();
        frame.setVisible(true);
    }
}