// Ksawery Plis
// Lista 7
// javac 17.0.8.1

import javax.swing.*;
import java.awt.*;

public class Editor {

    public Editor(Pojazd pojazd, Samochod samochod, Tramwaj tramwaj) {

        JFrame mainFrame = new JFrame("Edytor pojazdów");
        mainFrame.setPreferredSize(new Dimension(480, 100));
        mainFrame.setLayout(new BorderLayout());
        JLabel subtitleLabel = new JLabel("Wybierz przycisk aby edytować zapisane pojazdy");

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
        centerPanel.add(subtitleLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton editVehicleButton = new JButton("Edytuj pojazd");
        editVehicleButton.addActionListener(e -> {
            PojazdEditor.callEditor(pojazd, "pojazd.ser");
        });

        JButton editCarButton = new JButton("Edytuj samochód");
        editCarButton.addActionListener(e -> {
            SamochodEditor.callEditor(samochod, "samochod.ser");
        });

        JButton editTramButton = new JButton("Edytuj tramwaj");
        editTramButton.addActionListener(e -> {
            TramwajEditor.callEditor(tramwaj, "tramwaj.ser");
        });

        JButton closeButton = new JButton("Zamknij");
        closeButton.addActionListener(e -> mainFrame.dispose());

        buttonPanel.add(editVehicleButton);
        buttonPanel.add(editCarButton);
        buttonPanel.add(editTramButton);
        buttonPanel.add(closeButton);

        mainFrame.add(centerPanel, BorderLayout.CENTER);
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}