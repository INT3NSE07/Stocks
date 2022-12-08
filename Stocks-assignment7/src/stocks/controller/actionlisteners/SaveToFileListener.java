package stocks.controller.actionlisteners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import javax.swing.JDialog;
import javax.swing.JComboBox;


import stocks.model.PortfolioModel;
import stocks.view.PortfolioUIView;

/**
 * Class listener that waits for the "SaveTo" button to be pressed in
 * That's the button that the user clicks the SaveTO button in the SavePortfolioDialog.
 * And validates all the user input before telling the model to action.
 */
public class SaveToFileListener implements ActionListener {

  private JDialog saveDialog;
  private PortfolioUIView view;
  private PortfolioModel model;

  /**
   * Constructor that creates a listener object, passing the below.
   *
   * @param dialog The specific dialog and in this case: saveDialog.
   * @param view   View passed.
   * @param model  Model passed.
   */
  public SaveToFileListener(JDialog dialog, PortfolioUIView view, PortfolioModel model) {
    this.saveDialog = dialog;
    this.view = view;
    this.model = model;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JComboBox<String> nameList = null;
    for (Component c : saveDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("Portfolio Names")) {
        nameList = (JComboBox<String>) c;
      }
    }
    if (nameList == null || nameList.getSelectedItem() == null) {
      view.createErrorDialog("Please select a portfolio", saveDialog);
      return;
    }
    String name = nameList.getSelectedItem().toString();
    model.savePortfolioToFile(name);
    view.createInformationDialog("Portfolio successfully saved "
            + Paths.get("").toAbsolutePath() + "/" + name + ".csv", saveDialog);
  }
}
