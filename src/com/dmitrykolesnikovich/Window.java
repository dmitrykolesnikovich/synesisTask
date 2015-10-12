package com.dmitrykolesnikovich;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Window {

  private static final Object[] COLUMN_NAME = {"Chain"};
  private JTable table1;
  private JTextArea textArea1;
  private JButton addButton;
  private JPanel rotComponent;
  private JButton removeButton;
  private JButton solveButton;
  private JButton generateTestDataButton;
  private JButton infoButton;
  private DefaultTableModel model;

  public Window() {
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int row = table1.getSelectedRow() + 1;
        model.insertRow(row, new Object[]{""});
        table1.setRowSelectionInterval(row, row);
        removeButton.setEnabled(true);
      }
    });
    removeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int row = table1.getSelectedRow();
        model.removeRow(row);
        row = Math.min(row, table1.getRowCount() - 1);
        if (row != -1) {
          table1.setRowSelectionInterval(row, row);
        } else {
          removeButton.setEnabled(false);
        }
      }
    });

    solveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<String> rows = new ArrayList<String>();
        for (int i = 0; i < model.getRowCount(); i++) {
          String rowValue = (String) model.getValueAt(i, 0);
          rows.add(rowValue);
        }
        try {
          String result = new Solver().solve(rows);
          textArea1.setText(result);
        } catch (Throwable e1) {
          textArea1.setText("Error");
          e1.printStackTrace();
        }
      }
    });
    generateTestDataButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Object[][] data = new Object[3][1];
        data[0][0] = "1, 2, 3, 4, 5";
        data[1][0] = "1, 2, 3, 12, 13";
        data[2][0] = "144, 145, 3, 4, 5";
        model = new DefaultTableModel(data, COLUMN_NAME);
        table1.setModel(model);
      }
    });
    infoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        InfoDialog.showDialog();
      }
    });
  }


  public static void main(String[] args) {
    JFrame frame = new JFrame("Synesis Task - Dmitry Kolesnikovich");
    frame.setContentPane(new Window().rotComponent);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.getContentPane().setPreferredSize(new Dimension(800, 600));

    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

  }

  private void createUIComponents() {
    model = new DefaultTableModel(new Object[][]{}, COLUMN_NAME);
    table1 = new JTable(model);
  }

}
