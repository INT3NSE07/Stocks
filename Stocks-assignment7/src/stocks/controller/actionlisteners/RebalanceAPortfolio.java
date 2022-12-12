package stocks.controller.actionlisteners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import stocks.model.PortfolioModel;
import stocks.view.PortfolioUIView;

/**
 * Class listener that listens for the rebalance button to be pressed on. Validates the input and
 * passes it the model.
 */
public class RebalanceAPortfolio implements ActionListener {

  private JDialog rebalancePortfolioDialog;
  private final PortfolioUIView view;
  private final PortfolioModel model;

  /**
   * Constructor that creates a listener object for rebalancing a portfolio.
   *
   * @param rebalancePortfolioDialog the rebalance portfolio dialog
   * @param view                     the view that the PortfolioController uses to show messages to
   *                                 the user
   * @param model                    the model used to call the rebalancing functionality
   */
  public RebalanceAPortfolio(JDialog rebalancePortfolioDialog, PortfolioUIView view,
      PortfolioModel model) {
    this.rebalancePortfolioDialog = rebalancePortfolioDialog;
    this.view = view;
    this.model = model;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JComboBox<String> nameList = null;
    for (Component c : rebalancePortfolioDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("Portfolio Names")) {
        nameList = (JComboBox<String>) c;
      }
    }
    if (nameList == null || nameList.getSelectedItem() == null) {
      view.createErrorDialog("Please select a portfolio", rebalancePortfolioDialog);
      return;
    }

    String name = nameList.getSelectedItem().toString();
    String date = LocalDate.now().toString();
    for (Component c : rebalancePortfolioDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("Date panel")) {
        JPanel datePanel = (JPanel) c;
        date = view.getDate(datePanel);
        try {
          LocalDate.parse(date);
        } catch (DateTimeException w) {
          view.createErrorDialog("Please enter a valid date.", rebalancePortfolioDialog);
          return;
        }
      }
    }

    for (Component c : rebalancePortfolioDialog.getContentPane().getComponents()) {
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
              rebalancePortfolioDialog,
              "Successfully Rebalanced",
              "Confirmation",
              JOptionPane.INFORMATION_MESSAGE,
              new ImageIcon());
          rebalancePortfolioDialog.dispose();
        } else {
          Map<String, Double> tickerWeightMap = new HashMap<>();
          JPanel symbolWeightMainPanel = new JPanel();
          rebalancePortfolioDialog.getContentPane().removeAll();
          rebalancePortfolioDialog.repaint();
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
          rebalancePortfolioDialog.add(symbolWeightMainPanel);

          JButton confirmRebalance = new JButton("Confirm");
          String finalDate = date;
          confirmRebalance.addActionListener(e1 -> {
            for (Map.Entry<String, JTextField> ticker : weights.entrySet()) {
              if (ticker.getValue().getText().equals("")
                  || ticker.getValue().getText() == null) {
                view.createErrorDialog("Weights cannot be null or Empty",
                    rebalancePortfolioDialog);
                return;
              }
            }

            for (Map.Entry<String, JTextField> ticker : weights.entrySet()) {
              tickerWeightMap.put(ticker.getKey(),
                  Double.parseDouble(ticker.getValue().getText()));
              model.rebalancePortfolio(name, tickerWeightMap, finalDate);
              JOptionPane.showMessageDialog(
                  rebalancePortfolioDialog,
                  "Successfully Rebalanced",
                  "Confirmation",
                  JOptionPane.INFORMATION_MESSAGE,
                  new ImageIcon());
              rebalancePortfolioDialog.dispose();
            }
          });
          rebalancePortfolioDialog.add(confirmRebalance);
          rebalancePortfolioDialog.revalidate();
          rebalancePortfolioDialog.pack();
        }
      }
    }
  }
}
