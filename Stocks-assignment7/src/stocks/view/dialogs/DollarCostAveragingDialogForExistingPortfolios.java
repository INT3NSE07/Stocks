package stocks.view.dialogs;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;

import stocks.controller.actionlisteners.EndDateListener;
import stocks.controller.actionlisteners.GoBackListener;

/**
 * This class represents the dialog created when a user tries to perform dollar cost averaging
 * on an existing portfolio.
 */
public class DollarCostAveragingDialogForExistingPortfolios extends AbstractDialog {

  private List<String> names;

  /**
   * A super constructor for all the classes that inherit this Abstract class. Each of them has
   * a parent JFrame but might have different other parameters.
   *
   * @param parent the JFrame that the dialog is being created by
   */
  public DollarCostAveragingDialogForExistingPortfolios(JFrame parent, List<String> names) {
    super(parent);
    this.names = names;
  }

  @Override
  public JDialog createDialog() {
    JDialog dialog = new JDialog(parent);
    GridBagLayout layout = new GridBagLayout();
    dialog.setLayout(layout);
    dialog.setTitle("Creating Portfolio with Dollar Cost Averaging");

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;
    dialog.setLocationRelativeTo(parent);

    dialog.setName("Existing Portfolio");
    JLabel choosePortfolio = new JLabel("Choose portfolio to perform dollar cost averaging on");
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
    JButton addButton = new JButton("Add to Dollar Cost Strategy Plan");
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
    tablePane.setSize(5, 5);
    tablePane.setName("Stocks pane");
    dialog.add(tablePane, gbc);

    gbc.gridx = 0;
    gbc.gridy = 7;
    gbc.gridwidth = 1;
    JPanel startDate = new DatePanel().datePanel();
    startDate.setBorder(BorderFactory.createTitledBorder("Start Date"));
    startDate.setName("Date panel");
    dialog.add(startDate, gbc);

    gbc.gridx = 1;
    gbc.gridy = 6;
    JCheckBox checkbox = new JCheckBox("Provide End Date");
    checkbox.setName("End date check box");
    dialog.add(checkbox, gbc);

    gbc.gridx = 1;
    gbc.gridy = 7;
    JPanel endDate = new DatePanel().datePanel();
    for (Component c : endDate.getComponents()) {
      c.setEnabled(false);
    }
    checkbox.addActionListener(new EndDateListener(endDate));
    endDate.setBorder(BorderFactory.createTitledBorder("End Date"));
    endDate.setEnabled(false);
    endDate.setName("Date panel end");
    dialog.add(endDate, gbc);

    gbc.gridx = 0;
    gbc.gridy = 8;
    JLabel frequencyLabel = new JLabel("How many days do you want between investments?");
    frequencyLabel.setName("Frequency label");
    dialog.add(frequencyLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 8;
    JTextField frequency = new JTextField(5);
    frequency.setName("Frequency");
    dialog.add(frequency, gbc);

    gbc.gridx = 0;
    gbc.gridy = 9;
    JButton submitButton = new JButton("Perform the dollar cost averaging plan");
    submitButton.setName("Submit");
    dialog.add(submitButton, gbc);

    gbc.gridx = 1;
    gbc.gridy = 9;
    JButton goBackButton = new JButton("Go back");
    goBackButton.addActionListener(new GoBackListener(dialog));
    dialog.add(goBackButton, gbc);
    dialog.pack();
    return dialog;
  }
}
