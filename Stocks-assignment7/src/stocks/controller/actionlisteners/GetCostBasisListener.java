package stocks.controller.actionlisteners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;

import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioUIView;

/**
 * Class listener that waits for the "GetCostBasis" button to be pressed in
 * That's the button that the user clicks the GetCostBasis button in the getCostBasisDialog.
 * And validates all the user input before telling the model to action.
 */
public class GetCostBasisListener implements ActionListener {

  private final JDialog getCostBasisDialog;
  private final PortfolioUIView view;
  private final PortfolioModel model;

  /**
   * The constructor that takes in the view and model and dialog and then called the super to set
   * the values.
   *
   * @param view   The passed view.
   * @param dialog The passed dialog in this case the getCostBasisDialog.
   * @param model  The passed model.
   */
  public GetCostBasisListener(PortfolioUIView view, JDialog dialog, PortfolioModel model) {
    super();
    this.view = view;
    this.getCostBasisDialog = dialog;
    this.model = model;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JComboBox<String> nameList = null;
    for (Component c : getCostBasisDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("Portfolio Names")) {
        nameList = (JComboBox<String>) c;
      }
    }
    if (nameList == null || nameList.getSelectedItem() == null) {
      view.createErrorDialog("Please select a portfolio", getCostBasisDialog);
      return;
    }
    String name = nameList.getSelectedItem().toString();
    String date = LocalDate.now().toString();
    JLabel valueLabel = null;
    for (Component c : getCostBasisDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("Date panel")) {
        JPanel datePanel = (JPanel) c;
        date = view.getDate(datePanel);
        try {
          LocalDate.parse(date);
        } catch (DateTimeException w) {
          view.createErrorDialog("Please enter a valid date.", getCostBasisDialog);
          return;
        }
      }
      if (c.getName() != null && c.getName().equals("Value label")) {
        valueLabel = (JLabel) c;
      }
    }
    valueLabel.setText("Cost basis is $" + model.getCostBasis(name, date));
  }
}
