package view;

import javax.swing.*;
import model.CalendarModel;
import java.awt.Dimension;

public class ToolbarView extends JPanel {
  private JToolBar toolbar;
  private JButton prevMonthButton, nextMonthButton;
  private JSpinner yearSpinner;
  private JSlider monthSlider;
  private JLabel monthLabel;

  String[] months = { "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec",
      "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień" };

  public ToolbarView() {

    int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);

    toolbar = new JToolBar();

    toolbar.add(new JLabel("Rok:"));
    toolbar.addSeparator();

    yearSpinner = new JSpinner(new SpinnerNumberModel(year, 1, 9999, 1));
    yearSpinner.setEditor(new JSpinner.NumberEditor(yearSpinner, "#"));
    toolbar.add(yearSpinner);

    toolbar.addSeparator(new Dimension(100, toolbar.getHeight()));

    toolbar.add(new JLabel("Miesiąc:"));
    toolbar.addSeparator();

    prevMonthButton = new JButton("<");
    toolbar.add(prevMonthButton);
    toolbar.addSeparator();

    monthLabel = new JLabel(months[month]);
    monthLabel.setPreferredSize(new Dimension(90, monthLabel.getPreferredSize().height));
    toolbar.add(monthLabel);

    monthSlider = new JSlider(JSlider.HORIZONTAL, 0, 11, month);
    toolbar.add(monthSlider);

    nextMonthButton = new JButton(">");
    toolbar.add(nextMonthButton);

    add(toolbar);
  }

  public void refreshPanel(CalendarModel model) {
    yearSpinner.setValue(model.getYear());
    monthSlider.setValue(model.getMonth());
    monthLabel.setText(months[model.getMonth()]);
  }

  public JButton getPrevMonthButton() {
    return prevMonthButton;
  }

  public JButton getNextMonthButton() {
    return nextMonthButton;
  }

  public JSpinner getYearSpinner() {
    return yearSpinner;
  }

  public JSlider getMonthScrollBar() {
    return monthSlider;
  }
}