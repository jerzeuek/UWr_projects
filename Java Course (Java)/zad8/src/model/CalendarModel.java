package model;

import javax.swing.AbstractListModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarModel extends AbstractListModel<String> {
  private GregorianCalendar gregorianCalendar;

  public CalendarModel() {
    gregorianCalendar = new GregorianCalendar();
  }

  @Override
  public int getSize() {
    return gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
  }

  @Override
  public String getElementAt(int index) {
    GregorianCalendar tempCalendar = (GregorianCalendar) gregorianCalendar.clone();
    tempCalendar.set(Calendar.DAY_OF_MONTH, index + 1);

    String dayOfWeek = getDayOfWeek(tempCalendar.get(Calendar.DAY_OF_WEEK));
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    String formattedDate = dateFormat.format(tempCalendar.getTime());

    return dayOfWeek + ", " + formattedDate;
  }

  private String getDayOfWeek(int dayOfWeek) {
    String[] days = { "Niedziela", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota" };
    return days[dayOfWeek - 1];
  }

  public void setYear(int year) {
    gregorianCalendar.set(Calendar.YEAR, year);
    fireContentsChanged(this, 0, getSize() - 1);
  }

  public void setMonth(int month) {
    gregorianCalendar.set(Calendar.MONTH, month);
    fireContentsChanged(this, 0, getSize() - 1);
  }

  public void changeMonth(int change) {
    gregorianCalendar.add(Calendar.MONTH, change);
    fireContentsChanged(this, 0, getSize() - 1);
  }

  public int getYear() {
    return gregorianCalendar.get(Calendar.YEAR);
  }

  public int getMonth() {
    return gregorianCalendar.get(Calendar.MONTH);
  }

  public String[] getPreviousMonth() {
    GregorianCalendar tempCalendar = (GregorianCalendar) gregorianCalendar.clone();
    tempCalendar.add(Calendar.MONTH, -1);
    int daysInMonth = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    String[] previousMonth = new String[daysInMonth];

    for (int i = 0; i < daysInMonth; i++) {
      tempCalendar.set(Calendar.DAY_OF_MONTH, i + 1);
      String dayOfWeek = getDayOfWeek(tempCalendar.get(Calendar.DAY_OF_WEEK));
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
      String formattedDate = dateFormat.format(tempCalendar.getTime());
      previousMonth[i] = dayOfWeek + ", " + formattedDate;
    }

    return previousMonth;
  }

  public String[] getCurrentMonth() {
    GregorianCalendar tempCalendar = (GregorianCalendar) gregorianCalendar.clone();
    int daysInMonth = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    String[] currentMonth = new String[daysInMonth];

    for (int i = 0; i < daysInMonth; i++) {
      tempCalendar.set(Calendar.DAY_OF_MONTH, i + 1);
      String dayOfWeek = getDayOfWeek(tempCalendar.get(Calendar.DAY_OF_WEEK));
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
      String formattedDate = dateFormat.format(tempCalendar.getTime());
      currentMonth[i] = dayOfWeek + ", " + formattedDate;
    }

    return currentMonth;
  }

  public String[] getNextMonth() {
    GregorianCalendar tempCalendar = (GregorianCalendar) gregorianCalendar.clone();
    tempCalendar.add(Calendar.MONTH, 1);
    int daysInMonth = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    String[] nextMonth = new String[daysInMonth];

    for (int i = 0; i < daysInMonth; i++) {
      tempCalendar.set(Calendar.DAY_OF_MONTH, i + 1);
      String dayOfWeek = getDayOfWeek(tempCalendar.get(Calendar.DAY_OF_WEEK));
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
      String formattedDate = dateFormat.format(tempCalendar.getTime());
      nextMonth[i] = dayOfWeek + ", " + formattedDate;
    }

    return nextMonth;
  }

  public String[][] getYearTable() {
    GregorianCalendar tempCalendar = (GregorianCalendar) gregorianCalendar.clone();
    tempCalendar.setMinimalDaysInFirstWeek(1);
    String[][] yearTable = new String[72][7];

    for (int i = 0; i < 12; i++) {
      tempCalendar.set(Calendar.MONTH, i);
      tempCalendar.set(Calendar.DAY_OF_MONTH, 1);
      int firstDayOfMonth = (tempCalendar.get(Calendar.DAY_OF_WEEK) + 5) % 7;
      int daysInMonth = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
      for (int j = 0; j < daysInMonth; j++) {
        tempCalendar.set(Calendar.DAY_OF_MONTH, j + 1);
        yearTable[6 * i + ((tempCalendar.get(Calendar.WEEK_OF_MONTH) * 7) - firstDayOfMonth - 1)
            / 7][(tempCalendar.get(Calendar.DAY_OF_WEEK) + 5) % 7] = Integer.toString(j + 1);
      }
    }

    return yearTable;
  }
}
