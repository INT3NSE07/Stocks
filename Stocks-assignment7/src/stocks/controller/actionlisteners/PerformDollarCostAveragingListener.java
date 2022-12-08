package stocks.controller.actionlisteners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioUIView;

/**
 * Class listener that waits for the "Perform Dollar Cost Averaging" button to be pressed on.
 * That's the button that the user clicks the to perform the dollar cost averaging button in
 * the two dollar cost averaging dialogs.
 * And validates all the user input before telling the model to action.
 */
public class PerformDollarCostAveragingListener implements ActionListener {

  private final PortfolioUIView view;
  private final Map<String, Double> stocks;
  private final PortfolioModel model;
  private final JDialog dcaDialog;

  /**
   * Constructor that creates a listener object, passing the below.
   *
   * @param view   View passed.
   * @param stocks The stocks map, that contains the stocks and shares from the portfolio.
   * @param model  Model passed.
   * @param dialog The specific dialog and in this case: createInvestmentDialog.
   */
  public PerformDollarCostAveragingListener(PortfolioUIView view, Map<String, Double> stocks,
                                  PortfolioModel model, JDialog dialog) {
    this.view = view;
    this.stocks = stocks;
    this.model = model;
    this.dcaDialog = dialog;

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String name = "";
    if (dcaDialog.getName().equals("New Portfolio")) {
      JTextField nameField = view.getPortfolioName(dcaDialog);
      if (model.getPortfolioNames().size() > 0
              && model.getPortfolioNames().contains(nameField.getText())) {
        view.createErrorDialog("Portfolio with name already exists, "
                + "please try with another name", dcaDialog);
        return;
      }
      if (nameField.getText().equals("")) {
        view.createErrorDialog("Portfolio name cannot be blank", dcaDialog);
        return;
      }
      name = nameField.getText();
    } else {
      JComboBox<String> nameList = null;
      for (Component c : dcaDialog.getContentPane().getComponents()) {
        if (c.getName() != null && c.getName().equals("Portfolio Names")) {
          nameList = (JComboBox<String>) c;
        }
      }
      if (nameList == null || nameList.getSelectedItem() == null) {
        view.createErrorDialog("Please select a portfolio", dcaDialog);
        return;
      }
      name = nameList.getSelectedItem().toString();
    }
    Double amount = 0.0;
    int frequency = 1;
    String startDate = LocalDate.now().toString();
    String endDate = LocalDate.now().toString();
    for (Component c : dcaDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("Frequency")) {
        JTextField freq = (JTextField) c;
        try {
          frequency = Integer.parseInt(freq.getText());
          if (frequency < 1) {
            view.createErrorDialog("Frequency must be greater than 1", dcaDialog);
            return;
          }
        } catch (NumberFormatException ex) {
          view.createErrorDialog("Please enter a valid number of days", dcaDialog);
          return;
        }
      }
      if (c.getName() != null && c.getName().equals("Investment amount")) {
        JTextField field = (JTextField) c;
        try {
          amount = Double.valueOf(field.getText());
          if (amount <= 0) {
            view.createErrorDialog("Must invest more than $0. Please try again.",
                    dcaDialog);
            return;
          }
        } catch (NumberFormatException ex) {
          view.createErrorDialog("Value must be a number", dcaDialog);
        }
      }
      if (c.getName() != null && c.getName().equals("Date panel")) {
        JPanel datePanel = (JPanel) c;
        startDate = view.getDate(datePanel);
        try {
          LocalDate.parse(startDate);
        } catch (DateTimeException w) {
          view.createErrorDialog("Please enter a valid date.", dcaDialog);
          return;
        }
      }
      if (c.getName() != null && c.getName().equals("Date panel end")) {
        if (c.isEnabled()) {
          JPanel datePanel = (JPanel) c;
          endDate = view.getDate(datePanel);
          try {
            LocalDate.parse(endDate);
          } catch (DateTimeException w) {
            view.createErrorDialog("Please enter a valid date.", dcaDialog);
            return;
          }
        }
      }
    }
    Double total = 0.0;
    for (Double shares : stocks.values()) {
      total += shares;
    }
    if (total != 100.0) {
      view.createErrorDialog("Please select enter a total percentage of a 100",
              dcaDialog);
      return;
    }
    try {
      model.dollarCostAveraging(name, frequency, startDate, endDate, amount, stocks);
    } catch (RuntimeException ex) {
      view.createErrorDialog(ex.getMessage(), dcaDialog);
    }
    view.createInformationDialog("Successfully executed Dollar cost averaging "
            + "on portfolio " + name, dcaDialog);
    dcaDialog.dispose();
  }

}
