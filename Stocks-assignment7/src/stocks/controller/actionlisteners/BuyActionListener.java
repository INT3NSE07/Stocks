package stocks.controller.actionlisteners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JComboBox;


import stocks.model.PortfolioModel;
import stocks.view.PortfolioUIView;

/**
 * Class listener that waits for the "BuyAction" button to be pressed in the buyDialog where the.
 * User is allowed to buy stocks in the Portfolio.
 * And validates all the user input before telling the model to action.
 */
public class BuyActionListener implements ActionListener {

  private PortfolioUIView view;
  private JDialog buyDialog;
  private PortfolioModel model;
  private List<String> validTickers;

  /**
   * Constructor that creates a listener object, passing the below.
   *
   * @param view         View passed.
   * @param dialog       The specific dialog and in this case: buyDialog.
   * @param model        Model passed.
   * @param validTickers A list of valid tickers and in our case it's NASDAQ.
   */
  public BuyActionListener(PortfolioUIView view, JDialog dialog, PortfolioModel model,
                           List<String> validTickers) {
    super();
    this.view = view;
    this.buyDialog = dialog;
    this.model = model;
    this.validTickers = validTickers;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JTextField tickerField = view.getTicker(buyDialog);
    JTextField sharesField = view.getShares(buyDialog);
    String ticker = tickerField.getText();
    String shares = sharesField.getText();
    if (ticker.equals("")) {
      view.createErrorDialog("Ticker cannot be empty", buyDialog);
    }
    if (shares.equals("")) {
      view.createErrorDialog("Number of shares cannot be empty", buyDialog);
    }
    try {
      int x = Integer.parseInt(shares);
      if (x <= 0) {
        view.createErrorDialog("Number of shares being bought must be greater than 0.",
                buyDialog);
      }
    } catch (NumberFormatException ex) {
      view.createErrorDialog("Number of shares must be an integer", buyDialog);
    }
    JComboBox<String> names;
    String portfolioName = "";
    for (Component c : buyDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("Portfolio Names")) {
        names = (JComboBox<String>) c;
        if (names.getSelectedItem() == null) {
          view.createErrorDialog("Please select a portfolio", buyDialog);
          return;
        }
        portfolioName = names.getSelectedItem().toString();
      }
    }
    if (!validTickers.contains(ticker)) {
      view.createErrorDialog("Please enter a valid NASDAQ ticker and try again",
              buyDialog);
      return;
    }
    Double commission = 0.0;
    try {
      commission = Double.parseDouble(view.commissionAmount(buyDialog).getText());
      if (commission < 0) {
        view.createErrorDialog("Commission must be positive.", buyDialog);
        return;
      }
    } catch (NumberFormatException exception) {
      view.createErrorDialog("Commission amount must be a number.", buyDialog);
      return;
    }
    try {
      model.buyStock(portfolioName, Integer.parseInt(shares), ticker, LocalDate.now().toString(),
              commission);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    view.createInformationDialog("Successfully bought " + shares + " shares of "
            + ticker, buyDialog);
    tickerField.setText("");
    sharesField.setText("");
    view.commissionAmount(buyDialog).setText("");
  }
}
