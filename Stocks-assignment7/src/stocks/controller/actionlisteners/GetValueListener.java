package stocks.controller.actionlisteners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;

import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioUIView;

/**
 * Class listener that waits for the "GetValue" button to be pressed in
 * That's the button that the user clicks the GetValue button in the getValueDialog.
 * And validates all the user input before telling the model to action.
 */
public class GetValueListener implements ActionListener {

  private final JDialog getValueDialog;
  private final PortfolioUIView view;
  private final PortfolioModel model;

  /**
   * Constructor that creates a listener object, passing the below.
   *
   * @param view   View passed.
   * @param dialog The specific dialog and in this case: loadDialog.
   * @param model  Model passed.
   */
  public GetValueListener(PortfolioUIView view, JDialog dialog, PortfolioModel model) {
    super();
    this.view = view;
    this.getValueDialog = dialog;
    this.model = model;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JComboBox<String> nameList = null;
    for (Component c : getValueDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("Portfolio Names")) {
        nameList = (JComboBox<String>) c;
      }
    }
    if (nameList == null || nameList.getSelectedItem() == null) {
      view.createErrorDialog("Please select a portfolio", getValueDialog);
      return;
    }
    String name = nameList.getSelectedItem().toString();
    String date = LocalDate.now().toString();
    JLabel valueLabel = null;
    for (Component c : getValueDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("Date panel")) {
        JPanel datePanel = (JPanel) c;
        date = view.getDate(datePanel);
        try {
          LocalDate.parse(date);
        } catch (DateTimeException w) {
          view.createErrorDialog("Please enter a valid date.", getValueDialog);
          return;
        }
      }
      if (c.getName() != null && c.getName().equals("Value label")) {
        valueLabel = (JLabel) c;
      }
    }
    try {
      valueLabel.setText(model.getValueOfPortfolio(name, date));
    } catch (ParseException ex) {
      view.createErrorDialog("Something went wrong please try again", getValueDialog);
    }
  }

}
