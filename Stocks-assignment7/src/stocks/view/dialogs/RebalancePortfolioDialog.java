package stocks.view.dialogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class represents the dialog created when a user tries to rebalance an existing portfolio.
 */
public class RebalancePortfolioDialog extends AbstractDialog {

  private List<String> names;

  /**
   * This constructor creates a Load from file dialog that the user can interact with.
   *
   * @param parent the JFrame parent of the application interface
   * @param names  the portfolio names to be loaded into the dropdown
   */
  public RebalancePortfolioDialog(JFrame parent, List<String> names) {
    super(parent);
    this.names = names;
  }

  @Override
  public JDialog createDialog() {
    JDialog dialog = new JDialog(parent);
    dialog.setLocationRelativeTo(parent);

    GridBagLayout layout = new GridBagLayout();
    dialog.setLayout(layout);
    dialog.setTitle("Rebalance a Portfolio");

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel rebalancePortfolio = new JLabel("Choose portfolio to rebalance");
    rebalancePortfolio.setName("Choose label");
    dialog.add(rebalancePortfolio, gbc);

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

    gbc.gridx = 0;
    gbc.gridy = 3;
    JCheckBox equalWeights = new JCheckBox("Check here to distribute " +
        "weights equally among shares.");
    equalWeights.setName("checkbox");
    dialog.add(equalWeights, gbc);

    gbc.gridx = 0;
    gbc.gridy = 5;
    JButton submitButton = new JButton("Proceed");
    submitButton.setName("Submit");
    dialog.add(submitButton, gbc);

    dialog.pack();
    return dialog;
  }
}
