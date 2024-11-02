package algorithms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App extends JFrame {

    private BST<String> bst;
    private DrawingPanel drawingPanel;

    public App() {
        bst = new BST<>();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Rysowanie BST");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);
        drawingPanel.setBackground(Color.DARK_GRAY);

        JPanel buttonsPanel = new JPanel();
        JTextField inputField = new JTextField(30);
        JButton wstawButton = new JButton("Wstaw");
        JButton usunButton = new JButton("Usuń");
        JButton wyczyscButton = new JButton("Wyczyść");

        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(wstawButton);
        buttonsPanel.add(usunButton);
        buttonsPanel.add(wyczyscButton);
        buttonsPanel.add(inputField);
        add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.setBackground(Color.DARK_GRAY);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bst.insert(inputField.getText());
                drawingPanel.repaint();
                inputField.setText("");
            }
        });

        wstawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bst.insert(inputField.getText());
                drawingPanel.repaint();
                inputField.setText("");
            }
        });

        usunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bst.remove(inputField.getText());
                drawingPanel.repaint();
            }
        });

        wyczyscButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bst.clear();
                drawingPanel.repaint();
            }
        });

        pack();
        setVisible(true);
        setResizable(false);
    }

    private class DrawingPanel extends JPanel {

        public DrawingPanel() {
            setPreferredSize(new Dimension(1900, 950));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setFont(new Font("Arial", Font.PLAIN, 15));
            g.setColor(Color.WHITE);
            drawBST(g, getWidth() / 2, 30, bst.getRoot(), 1);
        }

        private void drawBST(Graphics g, int x, int y, BST<String>.Node<String> node, int depth) {
            if (node != null) {
                g.drawOval(x - 30, y - 30, 60, 60);
                g.drawString(node.value, x - 5, y + 5);

                if (node.left != null) {
                    g.drawLine(x - 30, y + 5, x - 450 / depth, y + 50);
                    drawBST(g, x - 450 / depth, y + 80, node.left, depth + 1);
                }

                if (node.right != null) {
                    g.drawLine(x + 30, y + 5, x + 450 / depth, y + 50);
                    drawBST(g, x + 450 / depth, y + 80, node.right, depth + 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
            }
        });
    }
}