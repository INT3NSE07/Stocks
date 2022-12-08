package stocks.controller.actionlisteners;

import java.awt.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioUIView;

/**
 * Class listener that waits for the "SubmitInvestment" button to be pressed in.
 * That's the button that the user clicks the SubmitInvestment button in the createInvestmentDialog.
 * And validates all the user input before telling the model to action.
 */
public class SubmitInvestmentListener implements ActionListener {
  private final PortfolioUIView view;
  private final Map<String, Double> stocks;
  private final PortfolioModel model;
  private final JDialog createInvestmentDialog;

  /**
   * Constructor that creates a listener object, passing the below.
   *
   * @param view   View passed.
   * @param stocks The stocks map, that contains the stocks and shares from the portfolio.
   * @param model  Model passed.
   * @param dialog The specific dialog and in this case: createInvestmentDialog.
   */
  public SubmitInvestmentListener(PortfolioUIView view, Map<String, Double> stocks,
                                  PortfolioModel model, JDialog dialog) {
    this.view = view;
    this.stocks = stocks;
    this.model = model;
    this.createInvestmentDialog = dialog;

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JComboBox<String> nameList = null;
    Double amount = 0.0;
    String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    for (Component c : createInvestmentDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("Portfolio Names")) {
        nameList = (JComboBox<String>) c;
      }
      if (c.getName() != null && c.getName().equals("Investment amount")) {
        JTextField field = (JTextField) c;
        try {
          amount = Double.valueOf(field.getText());
        } catch (NumberFormatException ex) {
          view.createErrorDialog("Value must be a number", createInvestmentDialog);
        }
      }
      if (c.getName() != null && c.getName().equals("Date panel")) {
        JPanel datePanel = (JPanel) c;
        date = view.getDate(datePanel);
        try {
          LocalDate.parse(date);
        } catch (DateTimeException w) {
          view.createErrorDialog("Please enter a valid date.", createInvestmentDialog);
          return;
        }
      }
    }
    if (nameList == null || nameList.getSelectedItem() == null) {
      view.createErrorDialog("Please select a portfolio", createInvestmentDialog);
      return;
    }
    String name = nameList.getSelectedItem().toString();

    Double total = 0.0;
    for (Double shares : stocks.values()) {
      total += shares;
    }
    if (total != 100.0) {
      view.createErrorDialog("Please select enter a total percentage of a 100",
              createInvestmentDialog);
      return;
    }
    try {
      model.investStocks(name, amount, stocks, date);
    } catch (RuntimeException exception) {
      view.createErrorDialog(exception.getMessage(), createInvestmentDialog);
      return;
    }
    view.createInformationDialog("Successfully invested in Portfolio "
            + name, createInvestmentDialog);
    createInvestmentDialog.dispose();
  }
}
