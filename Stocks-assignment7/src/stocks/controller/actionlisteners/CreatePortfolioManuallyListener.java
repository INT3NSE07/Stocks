package stocks.controller.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioUIView;

/**
 * Class listener that waits for the "CreatePortfolio" button to be pressed.
 * That's the button that the user clicks on after they entered the  commission, name , and
 * stocks Ticker, Share, Date.
 * the portfolio in the createPortfolioDialog.
 * And validates all the user input before telling the model to action.
 */
public class CreatePortfolioManuallyListener implements ActionListener {

  private final PortfolioUIView view;
  private final Map<String, String> stocks;
  private final PortfolioModel model;
  private final JDialog createPortfolioDialog;

  /**
   * Constructor that creates a listener object, passing the below.
   * @param view   View passed.
   * @param stocks The stocks map, that contains the stocks and shares from the portfolio.
   * @param model  Model passed.
   * @param dialog The specific dialog and in this case: createPortfolioDialog.
   */
  public CreatePortfolioManuallyListener(PortfolioUIView view, Map<String, String> stocks,
                                         PortfolioModel model, JDialog dialog) {
    super();
    this.view = view;
    this.stocks = stocks;
    this.model = model;
    this.createPortfolioDialog = dialog;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JTextField name = view.getPortfolioName(createPortfolioDialog);
    if (model.getPortfolioNames().size() > 0
            && model.getPortfolioNames().contains(name.getText())) {
      view.createErrorDialog("Portfolio with name already exists, "
              + "please try with another name", createPortfolioDialog);
      return;
    }
    if (name.getText().equals("")) {
      view.createErrorDialog("Portfolio name cannot be blank", createPortfolioDialog);
      return;
    }
    Map<String, Map<String, String>> stockMap = new HashMap<>();
    for (Map.Entry<String, String> entry : stocks.entrySet()) {
      Map<String, String> innerMap = new HashMap<>();
      innerMap.put(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
              entry.getValue());
      stockMap.put(entry.getKey(), innerMap);
    }
    model.createFlexiblePortfolioManually(name.getText(), stockMap);
    view.createInformationDialog("Successfully created portfolio "
            + name.getText(), createPortfolioDialog);
    JTable table = view.getStocksTable(view.getStocksPane(createPortfolioDialog));
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0);
    name.setText("");
  }
}
