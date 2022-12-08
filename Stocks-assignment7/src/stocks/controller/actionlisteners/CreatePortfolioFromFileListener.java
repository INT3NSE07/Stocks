package stocks.controller.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JDialog;
import javax.swing.JTextField;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioUIView;

/**
 * Class listener that waits for the "CreatePortfolio" button to be pressed in
 * That's teh button that the user clicks on after they entered the file path, commission, name of.
 * the portfolio in the loadFromFileDialog.
 * And validates all the user input before telling the model to action.
 */
public class CreatePortfolioFromFileListener implements ActionListener {
  private PortfolioUIView view;
  private JDialog loadDialog;
  private PortfolioModel model;

  /**
   * Constructor that creates a listener object, passing the below.
   *
   * @param view   View passed.
   * @param dialog The specific dialog and in this case: loadDialog.
   * @param model  Model passed.
   */
  public CreatePortfolioFromFileListener(PortfolioUIView view, JDialog dialog,
                                         PortfolioModel model) {
    this.view = view;
    this.loadDialog = dialog;
    this.model = model;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JTextField name = view.getPortfolioName(loadDialog);
    String fileName = name.getText();
    String filePath = view.getFileChosen(loadDialog).getText();
    if (model.getPortfolioNames().size() > 0
            && model.getPortfolioNames().contains(name.getText())) {
      view.createErrorDialog("Portfolio with name already exists, "
              + "please try with another name", loadDialog);
      return;
    }
    if (name.getText().equals("")) {
      view.createErrorDialog("Portfolio name cannot be blank", loadDialog);
      return;
    }
    Double commission = 0.0;
    try {
      commission = Double.parseDouble(view.commissionAmount(loadDialog).getText());
    } catch (NumberFormatException ex) {
      view.createErrorDialog("Commission must be a number. Please try again",
              loadDialog);
      return;
    }
    try {
      model.createFlexiblePortfolioFromFile(fileName, filePath, LocalDate.now().toString(),
              commission);
    } catch (IOException | RuntimeException ex) {
      view.createErrorDialog("Invalid file format. CSV files must have 3 headers "
              + "with name Ticker, date and shares. Please try again", loadDialog);
      return;
    }
    view.createInformationDialog("Successfully loaded portfolio from file.",
            loadDialog);
    loadDialog.dispose();
  }
}
