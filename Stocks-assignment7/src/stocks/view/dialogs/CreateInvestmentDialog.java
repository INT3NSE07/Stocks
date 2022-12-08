package stocks.view.dialogs;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import stocks.controller.actionlisteners.GoBackListener;

/**
 * This class represents the dialog created when a user tries to invest in a portfolio using an
 * investment strategy.
 */
public class CreateInvestmentDialog extends AbstractDialog {
  private List<String> names;

  /**
   * This constructor creates an Investment Dialog that the user can interact with.
   * @param parent the JFrame parent of the application interface
   * @param names the list of all the names of the current portfolios created
   */
  public CreateInvestmentDialog(JFrame parent, List<String> names) {
    super(parent);
    this.names = names;
  }

  @Override
  public JDialog createDialog() {
    JDialog dialog = new JDialog(parent);
    dialog.setLocationRelativeTo(parent);

    GridBagLayout layout = new GridBagLayout();
    dialog.setLayout(layout);
    dialog.setTitle("Investing in a Portfolio");

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;

    JLabel choosePortfolio = new JLabel("Choose portfolio to invest in");
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
    JLabel investmentLabel = new JLabel("Enter total amount to be invested");
    investmentLabel.setName("Investment Label");
    dialog.add(investmentLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 1;
    JTextField investmentAmount = new JTextField(5);
    investmentAmount.setName("Investment amount");
    dialog.add(investmentAmount, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel tickerLabel = new JLabel("Enter ticker: ");
    tickerLabel.setName("Ticker label");
    tickerLabel.setHorizontalAlignment(JTextField.CENTER);
    dialog.add(tickerLabel, gbc);
    // Text field where user enters ticker
    gbc.gridx = 1;
    gbc.gridy = 2;
    JTextField tickerText = new JTextField(5);
    tickerText.setName("Ticker");
    dialog.add(tickerText, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    JLabel sharesLabel = new JLabel("Enter Percentage of Shares: ");
    sharesLabel.setName("Shares label");
    dialog.add(sharesLabel, gbc);
    // Text field where user enters ticker
    gbc.gridx = 1;
    gbc.gridy = 3;
    JTextField sharesText = new JTextField(5);
    sharesText.setName("Shares");
    dialog.add(sharesText, gbc);

    //Add to portfolio button
    gbc.gridx = 0;
    gbc.gridy = 4;
    JButton addButton = new JButton("Add to Investment Plan");
    addButton.setName("Add share");
    dialog.add(addButton, gbc);

    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Ticker");
    model.addColumn("Percentage of investment");
    // Table which shows all the data
    JTable stocksTable = new JTable(model);
    stocksTable.setName("Stocks table");
    // Scroll pane which holds the table that should get updated when user presses add to portfolio
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.gridwidth = 2;
    JScrollPane tablePane = new JScrollPane(stocksTable);
    tablePane.setName("Stocks pane");
    dialog.add(tablePane, gbc);

    gbc.gridx = 0;
    gbc.gridy = 6;
    gbc.gridwidth = 1;
    gbc.gridheight = 3;
    JPanel date = new DatePanel().datePanel();
    date.setName("Date panel");
    dialog.add(date, gbc);
    // Button to submit added stocks and create the portfolio
    gbc.gridx = 1;
    gbc.gridy = 7;
    gbc.gridheight = 1;
    JButton submitButton = new JButton("Invest");
    submitButton.setName("Submit");
    dialog.add(submitButton, gbc);

    gbc.gridx = 1;
    gbc.gridy = 8;
    JButton goBackButton = new JButton("Go back");
    goBackButton.addActionListener(new GoBackListener(dialog));
    dialog.add(goBackButton, gbc);
    dialog.pack();
    return dialog;
  }

}
