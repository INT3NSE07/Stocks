package stocks.view.dialogs;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import stocks.controller.actionlisteners.GoBackListener;

/**
 * This class represents the dialog created when a user wants to create a portoflio manually by
 * entering stocks manually.
 */
public class CreatePortfolioDialog extends AbstractDialog {

  /**
   * This constructor creates a Create Portfolio dialog.
   * @param parent the JFrame parent of the application interface
   */
  public CreatePortfolioDialog(JFrame parent) {
    super(parent);
  }

  @Override
  public JDialog createDialog() {
    JDialog dialog = new JDialog(parent);
    dialog.setLocationRelativeTo(parent);

    GridBagLayout layout = new GridBagLayout();
    dialog.setLayout(layout);
    dialog.setTitle("Creating Portfolio");

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;

    // Label asking user to enter portfolio name
    JLabel nameLabel = new JLabel("Enter name of portfolio you want to create");
    nameLabel.setName("Name label");
    dialog.add(nameLabel, gbc);

    // Text field where user enters name
    gbc.gridx = 1;
    gbc.gridy = 0;
    JTextField nameField = new JTextField(10);
    nameField.setName("Portfolio Name");
    dialog.add(nameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    JLabel tickerLabel = new JLabel("Enter ticker: ");
    tickerLabel.setName("Ticker label");
    dialog.add(tickerLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 1;
    // Text field where user enters ticker
    JTextField tickerText = new JTextField(5);
    tickerText.setName("Ticker");
    dialog.add(tickerText, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel sharesLabel = new JLabel("Enter number of shares: ");
    sharesLabel.setName("Shares label");
    dialog.add(sharesLabel, gbc);

    // Text field where user enters number of shares
    gbc.gridx = 1;
    gbc.gridy = 2;
    JTextField sharesText = new JTextField(5);
    sharesText.setName("Shares");
    dialog.add(sharesText, gbc);

    //Add to portfolio button
    gbc.gridx = 0;
    gbc.gridy = 4;
    JButton addButton = new JButton("Add to portfolio");
    addButton.setName("Add share");
    dialog.add(addButton, gbc);

    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Ticker");
    model.addColumn("Number of shares");
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

    // Button to submit added stocks and create the portfolio
    gbc.gridx = 0;
    gbc.gridy = 6;
    gbc.gridwidth = 1;
    JButton submitButton = new JButton("Create portfolio");
    submitButton.setName("Submit");
    dialog.add(submitButton, gbc);

    gbc.gridx = 1;
    gbc.gridy = 6;
    JButton goBackButton = new JButton("Go back");
    goBackButton.addActionListener(new GoBackListener(dialog));
    dialog.add(goBackButton, gbc);

    dialog.pack();
    return dialog;
  }
}
