package stocks.view.dialogs;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPanel;

import stocks.controller.actionlisteners.GoBackListener;

/**
 * This class represents the dialog created when a user chooses to get the cost basis of their
 * portfolio in the application.
 */
public class GetCostBasisDialog extends AbstractDialog {
  private List<String> names;

  /**
   * A super constructor for all the classes that inherit this Abstract class. Each of them has
   * a parent JFrame but might have different other parameters.
   *
   * @param parent the JFrame that the dialog is being created by
   * @param names the list of all the names of the current portfolios created
   */
  public GetCostBasisDialog(JFrame parent, List<String> names) {
    super(parent);
    this.names = names;
  }

  @Override
  public JDialog createDialog() {
    JDialog dialog = new JDialog(parent);
    dialog.setLocationRelativeTo(parent);
    GridBagLayout layout = new GridBagLayout();
    dialog.setLayout(layout);
    dialog.setTitle("Cost Basis of Portfolio");

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel choosePortfolio = new JLabel("Choose portfolio to get cost basis for");
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
    gbc.gridheight = 3;
    JPanel date = new DatePanel().datePanel();
    date.setName("Date panel");
    dialog.add(date, gbc);

    gbc.gridx = 1;
    gbc.gridy = 3;
    gbc.gridheight = 1;
    JButton submitButton = new JButton("Get Cost Basis");
    submitButton.setName("Submit");
    dialog.add(submitButton, gbc);

    gbc.gridx = 0;
    gbc.gridy = 5;
    JButton goBackButton = new JButton("Go back");
    goBackButton.addActionListener(new GoBackListener(dialog));
    dialog.add(goBackButton, gbc);

    gbc.gridx = 1;
    gbc.gridy = 4;
    JLabel costBasisLabel = new JLabel("Cost basis of portfolio will be shown here.");
    costBasisLabel.setName("Value label");
    costBasisLabel.setHorizontalAlignment(JTextField.CENTER);
    dialog.add(costBasisLabel, gbc);
    dialog.pack();
    return dialog;
  }
}
