package stocks.controller.actionlisteners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import javax.swing.table.DefaultTableModel;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioUIView;

/**
 * Class listener that waits for the "ViewComposition" button to be pressed in.
 * That's the button that the user clicks the ViewComposition button in the ViewCompositionDialog.
 * And validates all the user input before telling the model to action.
 */

public class ViewCompositionListener implements ActionListener {

  private final JDialog viewDialog;
  private final PortfolioUIView view;
  private final PortfolioModel model;

  /**
   * Constructor that creates a listener object, passing the below.
   *
   * @param view   View passed.
   * @param dialog The specific dialog and in this case: viewDialog.
   * @param model  Model passed.
   */

  public ViewCompositionListener(PortfolioUIView view, JDialog dialog, PortfolioModel model) {
    super();
    this.view = view;
    this.viewDialog = dialog;
    this.model = model;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JScrollPane stocksPane = view.getStocksPane(viewDialog);
    JTable stocksTable = view.getStocksTable(stocksPane);
    DefaultTableModel dmodel = (DefaultTableModel) stocksTable.getModel();
    dmodel.setRowCount(0);
    JComboBox<String> nameList = null;
    for (Component c : viewDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("Portfolio Names")) {
        nameList = (JComboBox<String>) c;
      }
    }
    if (nameList == null || nameList.getSelectedItem() == null) {
      view.createErrorDialog("Please select a portfolio", viewDialog);
      return;
    }
    String name = nameList.getSelectedItem().toString();
    String date = LocalDate.now().toString();
    for (Component c : viewDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("Date panel")) {
        JPanel datePanel = (JPanel) c;
        date = view.getDate(datePanel);
        try {
          LocalDate.parse(date);
        } catch (DateTimeException w) {
          view.createErrorDialog("Please enter a valid date.", viewDialog);
          return;
        }
      }
    }
    Map<String, String> composition = model.getComposition(name, date);
    for (Map.Entry<String, String> entry : composition.entrySet()) {
      dmodel.addRow(new Object[]{entry.getKey(), entry.getValue()});
    }
    dmodel.fireTableDataChanged();
  }

}
