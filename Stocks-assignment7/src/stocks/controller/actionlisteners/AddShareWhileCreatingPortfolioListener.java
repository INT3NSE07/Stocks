package stocks.controller.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JDialog;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioUIView;

/**
 * Class listener that waits for the "AddShare" button to be pressed in the Create Portfolio
 * Dialog and validates all the user input before telling the model to action.
 */

public class AddShareWhileCreatingPortfolioListener implements ActionListener {
  PortfolioUIView view;
  PortfolioModel model;
  JDialog createPortfolioDialog;
  Map<String, String> stocks;
  List<String> validTickers;

  /**
   * Constructor that creates a listener object, passing the below.
   *
   * @param model        Model passed.
   * @param view         View passed.
   * @param dialog       The specific dialog and in this case: PortfolioDialog.
   * @param stocks       The stocks map.
   * @param validTickers A list of valid tickers and in our case it's NASDAQ.
   */
  public AddShareWhileCreatingPortfolioListener(PortfolioModel model, PortfolioUIView view,
                                                JDialog dialog, Map<String, String> stocks,
                                                List<String> validTickers) {
    super();
    this.model = model;
    this.view = view;
    this.createPortfolioDialog = dialog;
    this.stocks = stocks;
    this.validTickers = validTickers;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String ticker = view.getTicker(createPortfolioDialog).getText();
    String numShares = view.getShares(createPortfolioDialog).getText();
    if (ticker.equals("")) {
      view.createErrorDialog("Ticker cannot be empty please try again",
              createPortfolioDialog);
      return;
    }
    if (numShares.equals("")) {
      view.createErrorDialog("Number of shares cannot be empty please try again",
              createPortfolioDialog);
      return;
    }
    try {
      int x = Integer.parseInt(numShares);
      if (x <= 0) {
        view.createErrorDialog("Number of shares must be greater than 0.",
                createPortfolioDialog);
        return;
      }
    } catch (NumberFormatException ex) {
      view.createErrorDialog("Number of shares must be an integer",
              createPortfolioDialog);
      return;
    }
    if (!validTickers.contains(ticker)) {
      view.createErrorDialog("This ticker is not supported. "
              + "Please enter a ticker that is in NASDAQ.", createPortfolioDialog);
      return;
    }
    stocks.put(ticker, numShares);
    JScrollPane stocksPane = view.getStocksPane(createPortfolioDialog);
    JTable stocksTable = view.getStocksTable(stocksPane);
    DefaultTableModel model = (DefaultTableModel) stocksTable.getModel();
    model.addRow(new Object[]{ticker, numShares});
    model.fireTableDataChanged();
    view.getTicker(createPortfolioDialog).setText("");
    view.getShares(createPortfolioDialog).setText("");
  }

}
