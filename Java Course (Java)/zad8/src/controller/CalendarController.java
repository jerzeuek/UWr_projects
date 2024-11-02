package controller;

import model.CalendarModel;
import view.CalendarView;
import view.ToolbarView;
import view.YearView;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CalendarController {
  private CalendarModel calendarModel;
  private CalendarView calendarAppPanel;
  private ToolbarView controlPanel;
  private YearView yearTabPanel;

  public CalendarController(CalendarModel model, CalendarView view) {
    this.calendarModel = model;
    this.calendarAppPanel = view;
    this.controlPanel = view.getControlPanel();
    this.yearTabPanel = view.getYearTabPanel();
    generateCalendar();
    setupControlListeners();
  }

  private void setupControlListeners() {
    controlPanel.getPrevMonthButton().addActionListener(e -> {
      calendarModel.changeMonth(-1);
      controlPanel.refreshPanel(calendarModel);
      calendarAppPanel.refreshPanel(calendarModel);
      generateCalendar();
    });

    controlPanel.getNextMonthButton().addActionListener(e -> {
      calendarModel.changeMonth(1);
      controlPanel.refreshPanel(calendarModel);
      calendarAppPanel.refreshPanel(calendarModel);
      generateCalendar();
    });

    controlPanel.getYearSpinner().addChangeListener(e -> {
      calendarModel.setYear((Integer) controlPanel.getYearSpinner().getValue());
      controlPanel.refreshPanel(calendarModel);
      calendarAppPanel.refreshPanel(calendarModel);
      generateCalendar();
    });

    controlPanel.getMonthScrollBar().addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        int month = source.getValue();
        calendarModel.setMonth(month);
        controlPanel.refreshPanel(calendarModel);
        calendarAppPanel.refreshPanel(calendarModel);
        generateCalendar();
      }
    });

    for (int i = 0; i < 12; i++) {
      final int monthIndex = i;
      yearTabPanel.getMonthPanel(i).addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
          calendarModel.setMonth(monthIndex);
          controlPanel.refreshPanel(calendarModel);
          calendarAppPanel.refreshPanel(calendarModel);
          calendarAppPanel.updateCurrentIndex(1);
          generateCalendar();
        }
      });
    }

    calendarAppPanel.getTabbedPane().addChangeListener(e -> {
      generateCalendar();
    });
  }

  public void generateCalendar() {
    int index = calendarAppPanel.getTabbedPane().getSelectedIndex();

    if (index == 0) {
      controlPanel.getMonthScrollBar().setValue(calendarModel.getMonth());
      controlPanel.getYearSpinner().setValue(calendarModel.getYear());
      String[][] yearTable = calendarModel.getYearTable();
      calendarAppPanel.getYearTabPanel().populateTableModelPublic(yearTable);

    }

    if (index == 1) {
      controlPanel.getMonthScrollBar().setValue(calendarModel.getMonth());
      controlPanel.getYearSpinner().setValue(calendarModel.getYear());
      String[] previousMonth = calendarModel.getPreviousMonth();
      String[] currentMonth = calendarModel.getCurrentMonth();
      String[] nextMonth = calendarModel.getNextMonth();

      calendarAppPanel.getMonthTabPanel().populateListModelPublic(previousMonth, currentMonth, nextMonth);

    }
  }
}