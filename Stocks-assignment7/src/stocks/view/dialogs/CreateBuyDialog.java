package stocks.view.dialogs;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

import stocks.controller.actionlisteners.GoBackListener;

/**
 * This class represents the dialog created when the user tries to Buy stocks for a portfolio.
 */
public class CreateBuyDialog extends AbstractDialog {

  private final List<String> names;

  /**
   * This constructor creates a Buy Dialog that the user can interact with.
   * @param parent the JFrame parent of the application interface
   * @param names the list of all the names of the current portfolios created
   */
  public CreateBuyDialog(JFrame parent, List<String> names) {
    super(parent);
    this.names = names;
  }

  @Override
  public JDialog createDialog() {
    JDialog dialog = new JDialog(parent);
    dialog.setLocationRelativeTo(parent);

    GridBagLayout layout = new GridBagLayout();
    dialog.setLayout(layout);
    dialog.setTitle("Buying Stock");

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.VERTICAL;
    gbc.gridx = 0;
    gbc.gridy = 0;

    JLabel choosePortfolio = new JLabel("Choose portfolio to buy stocks for");
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
    JLabel tickerLabel = new JLabel("Enter ticker: ");
    tickerLabel.setName("Ticker label");
    dialog.add(tickerLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 1;
    JTextField tickerText = new JTextField(5);
    tickerText.setName("Ticker");
    dialog.add(tickerText, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel sharesLabel = new JLabel("Enter number of shares: ");
    sharesLabel.setName("Shares label");
    dialog.add(sharesLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 2;
    JTextField sharesText = new JTextField(5);
    sharesText.setName("Shares");
    dialog.add(sharesText, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    JLabel commissionLabel = new JLabel("Enter commission paid on transaction: ");
    commissionLabel.setName("Commission label");
    dialog.add(commissionLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 3;
    JTextField commissionAmount = new JTextField(5);
    commissionAmount.setName("Commission amount");
    dialog.add(commissionAmount, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    JButton addButton = new JButton("Add to portfolio");
    addButton.setName("Add share");
    dialog.add(addButton, gbc);

    gbc.gridx = 1;
    gbc.gridy = 4;
    JButton goBackButton = new JButton("Go back");
    goBackButton.addActionListener(new GoBackListener(dialog));
    dialog.add(goBackButton, gbc);

    dialog.pack();
    return dialog;
  }
}
