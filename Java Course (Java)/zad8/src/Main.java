import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import controller.CalendarController;
import model.CalendarModel;
import view.CalendarView;

public class Main {
  public static void main(String[] args) {
    CalendarModel model = new CalendarModel();
    CalendarView view = new CalendarView();
    new CalendarController(model, view);

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JFrame frame = new CalendarAppFrame(view);
        frame.setPreferredSize(new Dimension(800, 510));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
      }
    });
  }

  private static class CalendarAppFrame extends JFrame {
    public CalendarAppFrame(CalendarView panel) {
      setTitle("Kalendarz");
      add(panel);
    }
  }
}