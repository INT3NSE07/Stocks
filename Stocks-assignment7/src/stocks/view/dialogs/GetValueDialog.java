package stocks.view.dialogs;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JButton;

import stocks.controller.actionlisteners.GoBackListener;

/**
 * This class represents the dialog created when a user chooses to get the value of a portfolio in
 * the application.
 */
public class GetValueDialog extends AbstractDialog {
  private List<String> names;

  /**
   * This constructor creates a Get Value Dialog that the user can interact with.
   * @param parent the JFrame parent of the application interface
   * @param names the list of all the names of the current portfolios created
   */
  public GetValueDialog(JFrame parent, List<String> names) {
    super(parent);
    this.names = names;
  }

  @Override
  public JDialog createDialog() {
    JDialog dialog = new JDialog(parent);
    dialog.setLocationRelativeTo(parent);
    GridBagLayout layout = new GridBagLayout();
    dialog.setLayout(layout);
    dialog.setTitle("Value of Portfolio");

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel choosePortfolio = new JLabel("Choose portfolio to get value for");
    choosePortfolio.setName("Choose label");
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
    gbc.gridheight = 2;
    JPanel date = new DatePanel().datePanel();
    date.setName("Date panel");
    dialog.add(date, gbc);

    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.gridheight = 1;
    JButton submitButton = new JButton("Get Value");
    submitButton.setName("Submit");
    dialog.add(submitButton, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    JButton goBackButton = new JButton("Go back");
    goBackButton.addActionListener(new GoBackListener(dialog));
    dialog.add(goBackButton, gbc);

    gbc.gridx = 1;
    gbc.gridy = 3;
    JLabel valueLabel = new JLabel("Value of portfolio will be shown here.");
    valueLabel.setName("Value label");
    dialog.add(valueLabel, gbc);
    dialog.pack();
    return dialog;
  }
}
