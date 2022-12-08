package stocks.controller.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioUIView;

/**
 * Class listener that waits for the "AddShare" button to be pressed in the Investment dialog.
 * Dialog and validates all the user input before telling the model to action.
 */

public class AddShareWhileInvesting implements ActionListener {
  PortfolioUIView view;
  PortfolioModel model;
  JDialog investmentDialog;
  Map<String, Double> stocks;
  List<String> validTickers;

  /**
   * Constructor that creates a listener object, passing the below.
   *
   * @param model        Model passed.
   * @param view         View passed.
   * @param dialog       The specific dialog and in this case: InvestmentDialog.
   * @param stocks       The stocks map, that contains the stocks and shares from the portfolio.
   * @param validTickers A list of valid tickers and in our case it's NASDAQ.
   */
  public AddShareWhileInvesting(PortfolioModel model, PortfolioUIView view,
                                JDialog dialog, Map<String, Double> stocks,
                                List<String> validTickers) {
    super();
    this.model = model;
    this.view = view;
    this.investmentDialog = dialog;
    this.stocks = stocks;
    this.validTickers = validTickers;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String ticker = view.getTicker(investmentDialog).getText();
    String numShares = view.getShares(investmentDialog).getText();
    if (ticker.equals("")) {
      view.createErrorDialog("Ticker cannot be empty please try again",
              investmentDialog);
      return;
    }
    if (numShares.equals("")) {
      view.createErrorDialog("Percentage of shares cannot be empty please try again",
              investmentDialog);
      return;
    }
    try {
      double x = Double.parseDouble(numShares);
      if (x > 100) {
        view.createErrorDialog("Percentage of shares cannot be greater than 100.",
                investmentDialog);
        return;
      }
    } catch (NumberFormatException ex) {
      view.createErrorDialog("Percentage of shares must be a number",
              investmentDialog);
      return;
    }
    if (!validTickers.contains(ticker)) {
      view.createErrorDialog("This ticker is not supported. "
              + "Please enter a ticker that is in NASDAQ.", investmentDialog);
      return;
    }
    stocks.put(ticker, Double.valueOf(numShares));
    JScrollPane stocksPane = view.getStocksPane(investmentDialog);
    JTable stocksTable = view.getStocksTable(stocksPane);
    DefaultTableModel model = (DefaultTableModel) stocksTable.getModel();
    model.addRow(new Object[]{ticker, numShares});
    model.fireTableDataChanged();
    view.getTicker(investmentDialog).setText("");
    view.getShares(investmentDialog).setText("");
  }
}
