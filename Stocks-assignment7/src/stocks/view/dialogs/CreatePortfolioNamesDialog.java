package stocks.view.dialogs;

import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;

import stocks.controller.actionlisteners.GoBackListener;

/**
 * This class represents the dialog created when a user wants to see the names of all the portfolios
 * that have been created so far.
 */
public class CreatePortfolioNamesDialog extends AbstractDialog {

  private String[] portfolioNames;

  /**
   * This constructor creates the Portfolio Names dialog that the user can interact with.
   * @param parent the JFrame parent of the application interface
   * @param portfolioNames an array of all the current portfolio names
   */
  public CreatePortfolioNamesDialog(JFrame parent, String[] portfolioNames) {
    super(parent);
    this.portfolioNames = portfolioNames;
  }

  @Override
  public JDialog createDialog() {
    JDialog dialog = new JDialog(parent);
    dialog.setLocationRelativeTo(parent);
    dialog.setTitle("Viewing All Portfolios");
    JLabel nameLabel = new JLabel("Current created Portfolio's are:");
    nameLabel.setName("Name label");
    dialog.add(nameLabel);
    JList<String> nameList = new JList<>(portfolioNames);
    nameList.setName("Names list");
    dialog.add(nameList);
    JButton goBackButton = new JButton("Go back");
    goBackButton.addActionListener(new GoBackListener(dialog));
    dialog.add(goBackButton);
    dialog.setLayout(new FlowLayout());
    dialog.pack();
    return dialog;
  }
}
