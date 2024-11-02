package view;

import javax.swing.*;
import java.awt.*;

import renderer.ListCellRenderer;

public class MonthView extends JPanel {
  private JList<String> currentMonthList;
  private JList<String> previousMonthList;
  private JList<String> nextMonthList;

  DefaultListModel<String> previousMonthModel = new DefaultListModel<>();
  DefaultListModel<String> currentMonthModel = new DefaultListModel<>();
  DefaultListModel<String> nextMonthModel = new DefaultListModel<>();

  public MonthView() {
    setLayout(new GridLayout(1, 3));
    previousMonthList = new JList<>(previousMonthModel);
    currentMonthList = new JList<>(currentMonthModel);
    nextMonthList = new JList<>(nextMonthModel);

    ListCellRenderer renderer = new ListCellRenderer();
    previousMonthList.setCellRenderer(renderer);
    currentMonthList.setCellRenderer(renderer);
    nextMonthList.setCellRenderer(renderer);

    add(new JScrollPane(previousMonthList));
    add(new JScrollPane(currentMonthList));
    add(new JScrollPane(nextMonthList));
  }

  private void populateListModel(DefaultListModel<String> model, String[] data) {
    for (String day : data) {
      model.addElement(day);
    }
  }

  public void populateListModelPublic(String[] data1, String[] data2, String[] data3) {
    clearModels();
    populateListModel(previousMonthModel, data1);
    populateListModel(currentMonthModel, data2);
    populateListModel(nextMonthModel, data3);
  }

  public void clearModels() {
    previousMonthModel.clear();
    currentMonthModel.clear();
    nextMonthModel.clear();
  }
}