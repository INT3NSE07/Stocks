package stocks.view.dialogs;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import stocks.controller.actionlisteners.GoBackListener;


/**
 * This class represents the dialog created when a user tries to save a portfolio they created to a
 * file externally.
 */
public class SavePortfolioDialog extends AbstractDialog {

  private final List<String> names;

  /**
   * This constructor creates a Save Portfolio Dialog that the user can interact with.
   * @param parent the JFrame parent of the application interface
   * @param names the list of all the names of the current portfolios created
   */
  public SavePortfolioDialog(JFrame parent, List<String> names) {
    super(parent);
    this.names = names;
  }

  @Override
  public JDialog createDialog() {
    JDialog dialog = new JDialog(parent);
    dialog.setLocationRelativeTo(parent);
    GridBagLayout layout = new GridBagLayout();
    dialog.setLayout(layout);
    dialog.setTitle("Save Portfolio");

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel choosePortfolio = new JLabel("Choose portfolio save");
    choosePortfolio.setName("Choose label");
    choosePortfolio.setHorizontalAlignment(JTextField.CENTER);
    dialog.add(choosePortfolio, gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;
    JComboBox<String> portfolioNames = new JComboBox<>();
    for (String name : names) {
      portfolioNames.addItem(name);
    }
    portfolioNames.setName("Portfolio Names");
    dialog.add(portfolioNames, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    JButton submitButton = new JButton("Save file to current directory");
    submitButton.setName("Submit");
    dialog.add(submitButton, gbc);

    gbc.gridx = 1;
    gbc.gridy = 1;
    JButton goBackButton = new JButton("Go back");
    goBackButton.addActionListener(new GoBackListener(dialog));
    dialog.add(goBackButton, gbc);
    dialog.pack();
    return dialog;
  }
}
