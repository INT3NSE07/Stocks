package stocks.view.dialogs;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 * This class represents the dialog created when a user tries to view Performance of a portfolio.
 */
public class ViewPerformanceDialog extends AbstractDialog {

  private List<String> names;

  /**
   * A super constructor for all the classes that inherit this Abstract class. Each of them has
   * a parent JFrame but might have different other parameters.
   *
   * @param parent the JFrame that the dialog is being created by
   */
  public ViewPerformanceDialog(JFrame parent, List<String> names) {
    super(parent);
    this.names = names;
  }

  @Override
  public JDialog createDialog() {
    JDialog dialog = new JDialog(parent);
    dialog.setLocationRelativeTo(parent);
    dialog.setTitle("View Performance of Portfolio");
    GridBagLayout layout = new GridBagLayout();
    dialog.setLayout(layout);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel choosePortfolio = new JLabel("Choose portfolio to get performance for");
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
    JLabel timeStampLabel = new JLabel("Please choose a time stamp to view performance over");
    timeStampLabel.setHorizontalAlignment(JTextField.CENTER);
    timeStampLabel.setName("Time stamp label");
    dialog.add(timeStampLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 1;
    JComboBox<String> timeStamp = new JComboBox<>();
    timeStamp.addItem("Daily");
    timeStamp.addItem("Monthly");
    timeStamp.addItem("Yearly");
    timeStamp.setName("Time stamp");
    dialog.add(timeStamp, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    JPanel startDate = new DatePanel().datePanel();
    startDate.setBorder(BorderFactory.createTitledBorder("Start Date"));
    startDate.setName("Date panel");
    dialog.add(startDate, gbc);

    gbc.gridx = 1;
    gbc.gridy = 2;
    JPanel endDate = new DatePanel().datePanel();
    endDate.setBorder(BorderFactory.createTitledBorder("End Date"));
    endDate.setName("Date panel end");
    dialog.add(endDate, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    JButton submitButton = new JButton("View Performance");
    submitButton.setName("Submit");
    dialog.add(submitButton, gbc);
    dialog.pack();
    return dialog;
  }


}
