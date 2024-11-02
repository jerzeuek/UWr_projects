package renderer;

import javax.swing.*;
import java.awt.*;

public class ListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        String dayValue = (String) value;
        if (dayValue.startsWith("Niedziela")) {
            setForeground(Color.RED);
        } else {
            setForeground(Color.BLACK);
        }

        return this;
    }
}