package stocks.view.dialogs;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import java.util.List;

import stocks.controller.actionlisteners.GoBackListener;

/**
 * This class represents the dialog created when a user tries to view composition of a portfolio.
 */
public class ViewCompositionDialog extends AbstractDialog {

  private List<String> names;

  /**
   * This constructor creates a View Composition Dialog that the user can interact with.
   * @param parent the JFrame parent of the application interface
   * @param names the list of all the names of the current portfolios created
   */
  public ViewCompositionDialog(JFrame parent, List<String> names) {
    super(parent);
    this.names = names;
  }

  @Override
  public JDialog createDialog() {
    JDialog dialog = new JDialog(parent);
    dialog.setLocationRelativeTo(parent);
    GridBagLayout layout = new GridBagLayout();
    dialog.setLayout(layout);
    dialog.setTitle("View Composition of Portfolio");

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel choosePortfolio = new JLabel("Choose portfolio to view composition for");
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
    JButton submitButton = new JButton("View Composition");
    submitButton.setName("Submit");
    dialog.add(submitButton, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    JButton goBackButton = new JButton("Go back");
    goBackButton.addActionListener(new GoBackListener(dialog));
    dialog.add(goBackButton, gbc);

    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Ticker");
    model.addColumn("Number of shares");
    // Table which shows all the data
    JTable stocksTable = new JTable(model);
    stocksTable.setName("Stocks table");
    // Scroll pane which holds the table that should get updated when user presses add to portfolio
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    JScrollPane tablePane = new JScrollPane(stocksTable);
    tablePane.setName("Stocks pane");
    dialog.add(tablePane, gbc);
    dialog.pack();
    return dialog;
  }


}
