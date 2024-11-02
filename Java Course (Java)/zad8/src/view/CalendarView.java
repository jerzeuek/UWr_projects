package view;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import model.CalendarModel;

public class CalendarView extends JPanel {
  private JTabbedPane tabbedPane;
  private ToolbarView toolbarView;

  String[] months = { "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec",
      "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień" };

  public CalendarView() {
    int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);

    setLayout(new java.awt.BorderLayout());

    String yearTabTitle = Integer.toString(year);
    String monthTabTitle = months[month];

    tabbedPane = new JTabbedPane();
    YearView yearTab = new YearView();
    MonthView monthTab = new MonthView();
    tabbedPane.addTab(yearTabTitle, yearTab);
    tabbedPane.addTab(monthTabTitle, monthTab);
    add(tabbedPane, java.awt.BorderLayout.CENTER);
    
    toolbarView = new ToolbarView();
    add(toolbarView, java.awt.BorderLayout.SOUTH);
  }

  public ToolbarView getControlPanel() {
    return toolbarView;
  }

  public YearView getYearTabPanel() {
    return (YearView) tabbedPane.getComponentAt(0);
  }

  public MonthView getMonthTabPanel() {
    return (MonthView) tabbedPane.getComponentAt(1);
  }

  public JTabbedPane getTabbedPane() {
    return tabbedPane;
  }

  public void updateCurrentIndex(int index) {
    tabbedPane.setSelectedIndex(index);
  }

  public void refreshPanel(CalendarModel model) {
    updateTabTitles(model.getYear(), model.getMonth());
  }

  public void updateTabTitles(int year, int month) {
    String yearTabTitle = Integer.toString(year);
    String monthTabTitle = months[month];

    tabbedPane.setTitleAt(0, yearTabTitle);
    tabbedPane.setTitleAt(1, monthTabTitle);
  }
}