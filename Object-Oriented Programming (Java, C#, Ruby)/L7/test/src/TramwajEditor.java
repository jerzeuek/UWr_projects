import javax.swing.*;
import java.awt.*;

public class TramwajEditor extends JComponent {

    public TramwajEditor(Tramwaj tramwaj) {
        JTextField markaField = new JTextField(tramwaj.getMarka(), 20);
        JTextField maxPredkoscField = new JTextField(Double.toString(tramwaj.getMaxPredkosc()), 20);
        JTextField liczbaPasazerowField = new JTextField(Integer.toString(tramwaj.getLiczbaPasazerow()), 3);
        JTextField dlugoscField = new JTextField(Double.toString(tramwaj.getDlugosc()), 20);
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
        fieldsPanel.add(new JLabel("Długość:"));
        fieldsPanel.add(dlugoscField);
        add(fieldsPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(zapiszButton);
        buttonsPanel.add(anulujButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        zapiszButton.addActionListener(e -> {
            tramwaj.setMarka(markaField.getText());
            tramwaj.setMaxPredkosc(Double.parseDouble(maxPredkoscField.getText()));
            tramwaj.setLiczbaPasazerow(Integer.parseInt(liczbaPasazerowField.getText()));
            tramwaj.setDlugosc(Double.parseDouble(dlugoscField.getText()));
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            frame.dispose();

            System.out.println("Tramwaj po edycji:");
            System.out.println(tramwaj);
        });

        anulujButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            frame.dispose();
        });
    }

    public static void callEditor(Tramwaj tramwaj) {
        TramwajEditor editor = new TramwajEditor(tramwaj);
        JFrame frame = new JFrame("Edycja tramwaju");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(editor);
        frame.pack();
        frame.setVisible(true);
    }
}