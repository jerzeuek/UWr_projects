import javax.swing.*;
import java.awt.*;

public class SamochodEditor extends JComponent {

    public SamochodEditor(Samochod samochod) {
        // Tworzenie pól tekstowych
        JTextField markaField = new JTextField(samochod.getMarka(), 20);
        JTextField maxPredkoscField = new JTextField(Double.toString(samochod.getMaxPredkosc()), 20);
        JTextField liczbaPasazerowField = new JTextField(Integer.toString(samochod.getLiczbaPasazerow()), 3);
        JTextField typSilnikaField = new JTextField(samochod.getTypSilnika(), 20);

        JButton zapiszButton = new JButton("Zapisz");
        JButton anulujButton = new JButton("Anuluj");

        setLayout(new BorderLayout());

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(4, 2));
        fieldsPanel.add(new JLabel("Marka:"));
        fieldsPanel.add(markaField);
        fieldsPanel.add(new JLabel("Maksymalna prędkość:"));
        fieldsPanel.add(maxPredkoscField);
        fieldsPanel.add(new JLabel("Liczba pasażerów:"));
        fieldsPanel.add(liczbaPasazerowField);
        fieldsPanel.add(new JLabel("Typ silnika:"));
        fieldsPanel.add(typSilnikaField);
        add(fieldsPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(zapiszButton);
        buttonsPanel.add(anulujButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        zapiszButton.addActionListener(e -> {
            samochod.setMarka(markaField.getText());
            samochod.setMaxPredkosc(Double.parseDouble(maxPredkoscField.getText()));
            samochod.setLiczbaPasazerow(Integer.parseInt(liczbaPasazerowField.getText()));
            samochod.setTypSilnika(typSilnikaField.getText());
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            frame.dispose();

            System.out.println("Samochód po edycji:");
            System.out.println(samochod);
        });

        anulujButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            frame.dispose();
        });
    }

    public static void callEditor(Samochod samochod) {
        SamochodEditor editor = new SamochodEditor(samochod);
        JFrame frame = new JFrame("Edycja samochodu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(editor);
        frame.pack();
        frame.setVisible(true);
    }
}