package stocks.controller.actionlisteners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioUIView;

/**
 * Class listener that waits for the "SellStock" button to be pressed in.
 * That's the button that the user clicks the SellStock button in the SellStiockDialog.
 * And validates all the user input before telling the model to action.
 */
public class SellStockListener implements ActionListener {

  private PortfolioUIView view;
  private JDialog sellDialog;
  private PortfolioModel model;

  /**
   * Constructor that creates a listener object, passing the below.
   *
   * @param view   View passed.
   * @param dialog The specific dialog and in this case: sellDialog.
   * @param model  Model passed.
   */
  public SellStockListener(PortfolioUIView view, JDialog dialog, PortfolioModel model) {
    super();
    this.view = view;
    this.sellDialog = dialog;
    this.model = model;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JTextField tickerField = view.getTicker(sellDialog);
    JTextField sharesField = view.getShares(sellDialog);
    String ticker = tickerField.getText();
    String shares = sharesField.getText();
    if (ticker.equals("")) {
      view.createErrorDialog("Ticker cannot be empty", sellDialog);
    }
    if (shares.equals("")) {
      view.createErrorDialog("Number of shares cannot be empty", sellDialog);
    }
    try {
      int x = Integer.parseInt(shares);
      if (x <= 0) {
        view.createErrorDialog("Number of shares being sold must be greater than 0.",
                sellDialog);
      }
    } catch (NumberFormatException ex) {
      view.createErrorDialog("Number of shares must be an integer", sellDialog);
      return;
    }
    JComboBox<String> names;
    String portfolioName = "";
    for (Component c : sellDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("Portfolio Names")) {
        names = (JComboBox<String>) c;
        if (names.getSelectedItem() == null) {
          view.createErrorDialog("Please select a portfolio", sellDialog);
          return;
        }
        portfolioName = names.getSelectedItem().toString();
      }
    }
    String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    if (!model.getPortfolio(portfolioName).viewStocks(date).containsKey(ticker)) {
      view.createErrorDialog("No shares available for selling for "
              + "the ticker provided. " +
              "Please try again with a different ticker.", sellDialog);
      return;
    } else if (Double.parseDouble(model.getPortfolio(portfolioName).viewStocks(date).get(ticker))
            < Double.parseDouble(shares)) {
      view.createErrorDialog("Trying to sell more shares than are available."
              + " Please change amount of shares being sold and try again.", sellDialog);
      return;
    }
    Double commission = 0.0;
    try {
      commission = Double.parseDouble(view.commissionAmount(sellDialog).getText());
      if (commission < 0) {
        view.createErrorDialog("Commission must be positive.", sellDialog);
        return;
      }
    } catch (NumberFormatException exception) {
      view.createErrorDialog("Commission amount must be a number.", sellDialog);
      return;
    }
    try {
      model.sellStock(portfolioName, Integer.parseInt(shares), ticker, LocalDate.now().toString(),
              commission);
    } catch (ParseException ex) {
      view.createErrorDialog(ex.getMessage(), sellDialog);
    }
    view.createInformationDialog("Successfully sold " + shares
            + " shares of " + ticker, sellDialog);
    tickerField.setText("");
    sharesField.setText("");
    view.commissionAmount(sellDialog).setText("");
  }

}
