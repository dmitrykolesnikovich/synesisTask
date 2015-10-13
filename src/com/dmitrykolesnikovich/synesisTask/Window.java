package com.dmitrykolesnikovich.synesisTask;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
  private JButton versionsButton;
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
        removSelectedRows();
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
          String result = solve(rows);
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
        removeButton.setEnabled(true);
      }
    });
    infoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        InfoDialog.showDialog();
      }
    });
    versionsButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        VersionsDialog.showDialog();
      }
    });
  }

  private void removSelectedRows() {
    int[] rows = table1.getSelectedRows();
    if (rows.length != 0) {
      int firstRow = rows[0];
      for (int row : rows) {
        model.removeRow(firstRow);
      }
    }
    if (model.getRowCount() == 0) {
      removeButton.setEnabled(false);
    }
  }


  public static void main(String[] args) {
    JFrame frame = new JFrame("Synesis Task - Dmitry Kolesnikovich (version 2)");
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
    table1.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
          removSelectedRows();
        }
      }
    });
  }

  /**
   * @return string representation of algorithm result
   */
  public String solve(List<String> rows) {
    List<List<Integer>> chains = new ArrayList<>();
    for (String row : rows) {
      row = row.replaceAll(" ", "");
      List<Integer> chain = new ArrayList<>();
      for (String element : row.split(",")) {
        chain.add(Integer.valueOf(element));
      }
      chains.add(chain);
    }
    StringBuffer result = new StringBuffer();
    new Solver().solve(chains, result);
    return result.toString();
  }

}
