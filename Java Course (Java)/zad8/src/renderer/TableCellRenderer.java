package renderer;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class TableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (column == 6) {
            setForeground(Color.RED);
        }
        else{
            setForeground(Color.BLACK);
        }

        return this;
    }
}
