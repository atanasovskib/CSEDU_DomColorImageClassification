package edu.fcse.domcolorclassifier.gui.custcomponents;

import java.util.Map;
import javax.swing.table.AbstractTableModel;

public class GravCenterTableModel extends AbstractTableModel {

    private String[] columnNames;
    private Object[][] data;

    public GravCenterTableModel(Map<String, float[]> map) {
        columnNames = new String[]{"Color name", "Red", "Green", "Blue"};
        data = new Object[map.size()][4];
        int i = 0;
        for (String s : map.keySet()) {
            data[i][0] = s;
            float[] cl = map.get(s);

            data[i][1] = cl[0];
            data[i][2] = cl[1];
            data[i][3] = cl[2];
            i++;
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
