package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

import renderer.TableCellRenderer;

public class YearView extends JPanel {
  private JTable[] monthTable = new JTable[12];
  
  DefaultTableModel[] tableModels = new DefaultTableModel[12];

  public YearView() {
    TableCellRenderer renderer = new TableCellRenderer();
    for (int i = 0; i < 12; i++) {
      tableModels[i] = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
          return false;
       }
      };

      monthTable[i] = new JTable(tableModels[i]);
      monthTable[i].setDefaultRenderer(Object.class, renderer);
    }
    GridLayout gridLayout = new GridLayout(3, 4);
    setLayout(gridLayout);
    Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    for (int i = 0; i < 12; i++) {
      JPanel monthPanel = createMonthPanel(i);
      monthPanel.setBorder(border);
      add(monthPanel);
    }
  }

  private JPanel createMonthPanel(int monthIndex) {
    JPanel monthPanel = new JPanel(new BorderLayout());
    JPanel tablePanel = new JPanel(new BorderLayout());
    JTableHeader tableHeader = monthTable[monthIndex].getTableHeader();
    JLabel monthLabel = new JLabel(getMonthName(monthIndex), SwingConstants.CENTER);
    
    monthTable[monthIndex].setRowSelectionAllowed(false);
    monthPanel.add(monthLabel, BorderLayout.NORTH);
    tablePanel.add(tableHeader, BorderLayout.NORTH);
    tablePanel.add(monthTable[monthIndex], BorderLayout.CENTER);
    monthPanel.add(tablePanel, BorderLayout.CENTER);
    return monthPanel;
  }

  public JPanel getMonthPanel(int monthIndex) {
    return (JPanel) getComponent(monthIndex);
  }

  private String getMonthName(int monthIndex) {
    String[] months = { "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień",
        "Październik", "Listopad", "Grudzień" };
    return months[monthIndex];
  }

  private String getDayName(int dayIndex) {
    String[] days = { "PN", "WT", "ŚR", "CZ", "PT", "SB", "ND" };
    return days[dayIndex];
  }

  private void populateTableModel(DefaultTableModel tableModel, String[][] data, int monthNumber){
    for (int i = 0; i < 7; i++) {
      tableModel.addColumn(getDayName(i));
    }
    
    for(int i = 0; i<6; i++){
      Object[] row = new Object[7];
      for (int j = 0; j< 7; j++){
        row[j] = data[6* monthNumber + i][j];
      }

      tableModel.addRow(row);
    }
    
  }

  public void populateTableModelPublic(String[][] data){
    for(int i = 0; i<12; i++){
      clearModel(tableModels[i]);
      populateTableModel(tableModels[i], data, i);
    }
  }

  public void clearModel(DefaultTableModel tableModel){
    tableModel.setRowCount(0);
    tableModel.setColumnCount(0);
  }
}