package stocks.controller.actionlisteners;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioUIView;

public class RebalanceAPortfolio implements ActionListener {
  private JDialog reBalancePortfolioDialog;
  private final PortfolioUIView view;
  private final PortfolioModel model;


  public RebalanceAPortfolio(JDialog reBalancePortfolioDialog, PortfolioUIView view,
                             PortfolioModel model) {
    this.reBalancePortfolioDialog = reBalancePortfolioDialog;
    this.view = view;
    this.model = model;
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    JComboBox<String> nameList = null;
    for (Component c : reBalancePortfolioDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("Portfolio Names")) {
        nameList = (JComboBox<String>) c;
      }
    }
    if (nameList == null || nameList.getSelectedItem() == null) {
      view.createErrorDialog("Please select a portfolio", reBalancePortfolioDialog);
      return;
    }

    String name = nameList.getSelectedItem().toString();
    String date = LocalDate.now().toString();
    for (Component c : reBalancePortfolioDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("Date panel")) {
        JPanel datePanel = (JPanel) c;
        date = view.getDate(datePanel);
        try {
          LocalDate.parse(date);
        } catch (DateTimeException w) {
          view.createErrorDialog("Please enter a valid date.", reBalancePortfolioDialog);
          return;
        }
      }
    }
    for (Component c : reBalancePortfolioDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("checkbox")) {
        JCheckBox symbolWeightCheckBox = (JCheckBox) c;
        if (symbolWeightCheckBox.isSelected()) {
          Map<String, Double> tickerWeightMap = new HashMap<>();
          for (String ticker :
                  model.getComposition(name, date).keySet()) {
            tickerWeightMap.put(ticker, (100.00 / model.getComposition(name, date).size()));
          }
          // direct check invoking rebalance.
          model.rebalancePortfolio(name, tickerWeightMap, date);
          JOptionPane.showMessageDialog(
                  reBalancePortfolioDialog,
                  "Sucessfully Rebalanced",
                  "Confirmation",
                  JOptionPane.INFORMATION_MESSAGE,
                  new ImageIcon());
          reBalancePortfolioDialog.dispose();
        } else {
          Map<String, Double> tickerWeightMap = new HashMap<>();
          JPanel symbolWeightMainPanel = new JPanel();
          reBalancePortfolioDialog.getContentPane().removeAll();
          reBalancePortfolioDialog.repaint();
          symbolWeightMainPanel.setLayout(new BoxLayout(symbolWeightMainPanel, BoxLayout.Y_AXIS));
          Map<String, JTextField> weights = new HashMap<>();
          for (String ticker :
                  model.getComposition(name, date).keySet()) {
            JPanel symbolWeightPanel = new JPanel();

            JLabel symbol = new JLabel("Weight for " + ticker);
            symbolWeightPanel.add(symbol);
            JTextField weight = new JTextField(10);
            weights.put(ticker, weight);
            symbolWeightPanel.add(weight);

            symbolWeightMainPanel.add(symbolWeightPanel);
            symbolWeightMainPanel.revalidate();

          }
          reBalancePortfolioDialog.add(symbolWeightMainPanel);

          JButton confirmRebalance = new JButton("Confirm");
          String finalDate = date;
          confirmRebalance.addActionListener(e1 -> {
            for (Map.Entry<String, JTextField> ticker : weights.entrySet()) {
              if (ticker.getValue().getText().equals("")
                      || ticker.getValue().getText() == null) {
                view.createErrorDialog("Weights cannot be null or Empty",
                        reBalancePortfolioDialog);
                return;
              }
            }


            for (Map.Entry<String, JTextField> ticker : weights.entrySet()) {
              tickerWeightMap.put(ticker.getKey(),
                      Double.parseDouble(ticker.getValue().getText()));
              model.rebalancePortfolio(name, tickerWeightMap, finalDate);
              JOptionPane.showMessageDialog(
                      reBalancePortfolioDialog,
                      "Sucessfully Rebalanced",
                      "Confirmation",
                      JOptionPane.INFORMATION_MESSAGE,
                      new ImageIcon());
              reBalancePortfolioDialog.dispose();
            }
          });
          reBalancePortfolioDialog.add(confirmRebalance);
          reBalancePortfolioDialog.revalidate();
          reBalancePortfolioDialog.pack();
        }
      }
    }


  }
}
