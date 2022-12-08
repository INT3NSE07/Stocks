package stocks.view;

import java.awt.FlowLayout;
import java.awt.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JViewport;

import stocks.controller.Features;
import stocks.view.dialogs.CreateBuyDialog;
import stocks.view.dialogs.CreateInvestmentDialog;
import stocks.view.dialogs.CreatePortfolioDialog;
import stocks.view.dialogs.CreatePortfolioNamesDialog;
import stocks.view.dialogs.CreatePortfolioWithDCA;
import stocks.view.dialogs.CreateSellDialog;
import stocks.view.dialogs.DollarCostAveragingDialogForExistingPortfolios;
import stocks.view.dialogs.GetCostBasisDialog;
import stocks.view.dialogs.GetValueDialog;
import stocks.view.dialogs.LoadFromFileDialog;
import stocks.view.dialogs.SavePortfolioDialog;
import stocks.view.dialogs.ViewCompositionDialog;
import stocks.view.dialogs.ViewPerformanceDialog;

/**
 * This class represents the base JFrame of the Graphical User Interface which allows the user to
 * select an action offered by the program. It has all the methods that the controller might use
 * after user input to inform the user about the task visually.
 */
public class PortfolioJFrameViewImpl extends JFrame implements PortfolioUIView {
  private final JComboBox<String> options;
  private final JButton exitButton;
  private final JButton selectButton;

  /**
   * This constructor creates the JFrame base of the application and lets users interact with it.
   * @param caption the caption of the JFrame window
   */
  public PortfolioJFrameViewImpl(String caption) {
    super(caption);

    setSize(500, 800);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new FlowLayout());

    JLabel optionsDisplay = new JLabel("Please select an action to perform: ");
    options = new JComboBox<>();
    String[] validOptions = {"Create a new flexible portfolio", "Load a flexible portfolio from "
        + "file", "Create a portfolio with dollar cost averaging" , "Buy stocks", "Invest in a "
        + "Portfolio", "Sell stocks", "Dollar Cost Averaging on an existing portfolio",
        "Get value of a portfolio", "Get cost basis", "View composition of a portfolio", "View "
        + "performance of a portfolio over time", "View Current Portfolios",
        "Save a portfolio to file"};
    options.setActionCommand("Feature options");
    for (String validOption : validOptions) {
      options.addItem(validOption);
    }
    this.add(optionsDisplay);
    this.add(options);

    selectButton = new JButton("Select Option");
    this.add(selectButton);

    exitButton = new JButton("Exit");
    exitButton.setActionCommand("Exit button");
    this.add(exitButton);

    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    exitButton.addActionListener(evt -> features.exitProgram());
    selectButton.addActionListener(e -> {
      String selectedItem = (String) options.getSelectedItem();
      switch (Objects.requireNonNull(selectedItem)) {
        case "Create a new flexible portfolio":
          features.createNewFlexiblePortfolio();
          break;
        case "Buy stocks":
          features.buyStocks();
          break;
        case "Sell stocks":
          features.sellStocks();
          break;
        case "Get value of a portfolio":
          features.getValue();
          break;
        case "Invest in a Portfolio":
          features.investPortfolio();
          break;
        case "Dollar Cost Averaging on an existing portfolio":
          features.dollarCostAveraging();
          break;
        case "Get cost basis":
          features.getCostBasis();
          break;
        case "View Current Portfolios":
          features.viewPortfolios();
          break;
        case "Load a flexible portfolio from file":
          features.loadPortfolio();
          break;
        case "View composition of a portfolio":
          features.viewComposition();
          break;
        case "Save a portfolio to file":
          features.savePortfolio();
          break;
        case "View performance of a portfolio over time":
          features.viewPerformance();
          break;
        case "Create a portfolio with dollar cost averaging":
          features.createWithDollarCostAveraging();
          break;
        default:
          // should never reach here
      }
    });
  }

  @Override
  public JDialog createPortfolioDialog() {
    return new CreatePortfolioDialog(this).createDialog();
  }

  @Override
  public JDialog createBuyDialog(List<String> names) {
    return new CreateBuyDialog(this, names).createDialog();
  }

  @Override
  public JTextField getTicker(JDialog dialog) {
    Component[] components = dialog.getContentPane().getComponents();
    for (Component component : components) {
      if (component.getName().equals("Ticker")) {
        return (JTextField) component;
      }
    }
    return null;
  }

  @Override
  public JTextField getShares(JDialog dialog) {
    Component[] components = dialog.getContentPane().getComponents();
    for (Component component : components) {
      if (component.getName().equals("Shares")) {
        return (JTextField) component;
      }
    }
    return null;
  }

  @Override
  public void createErrorDialog(String errorMessage, JDialog parent) {
    JOptionPane.showMessageDialog(parent, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void createInformationDialog(String informationMessage, JDialog parent) {
    JOptionPane.showMessageDialog(parent, informationMessage,
            "Success", JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public JButton addShareButton(JDialog dialog) {
    for (Component component : dialog.getContentPane().getComponents()) {
      if (component.getName().equals("Add share")) {
        return (JButton) component;
      }
    }
    return null;
  }

  @Override
  public JTextField getPortfolioName(JDialog dialog) {
    for (Component component : dialog.getContentPane().getComponents()) {
      if (component.getName().equals("Portfolio Name")) {
        return (JTextField) component;
      }
    }
    return null;
  }

  @Override
  public JScrollPane getStocksPane(JDialog dialog) {
    for (Component component : dialog.getContentPane().getComponents()) {
      if (component.getName() != null && component.getName().equals("Stocks pane")) {
        return (JScrollPane) component;
      }
    }
    return null;
  }

  @Override
  public JTable getStocksTable(JScrollPane stocksPane) {
    JViewport view = stocksPane.getViewport();
    Component[] components = view.getComponents();
    for (Component component : components) {
      if (component instanceof JTable) {
        return (JTable) component;
      }
    }
    return null;
  }

  @Override
  public JButton getSubmitButton(JDialog dialog) {
    for (Component component : dialog.getContentPane().getComponents()) {
      if (component.getName().equals("Submit")) {
        return (JButton) component;
      }
    }
    return null;
  }

  @Override
  public JDialog createPortfolioNamesDialog(String[] portfolioNames) {
    return new CreatePortfolioNamesDialog(this, portfolioNames).createDialog();
  }

  @Override
  public JDialog loadFromFileDialog() {
    return new LoadFromFileDialog(this).createDialog();
  }

  @Override
  public JButton getFileChosen(JDialog dialog) {
    for (Component component : dialog.getContentPane().getComponents()) {
      if (component.getName().equals("Choose file label")) {
        return (JButton) component;
      }
    }
    return null;
  }

  @Override
  public JTextField commissionAmount(JDialog dialog) {
    for (Component component : dialog.getContentPane().getComponents()) {
      if (component.getName().equals("Commission amount")) {
        return (JTextField) component;
      }
    }
    return null;
  }

  @Override
  public JDialog viewComposition(List<String> names) {
    return new ViewCompositionDialog(this, names).createDialog();
  }

  @Override
  public String getDate(JPanel datePanel) {
    String day = "";
    String month = "";
    int year = 0;
    for (Component c : datePanel.getComponents()) {
      if (c.getName() != null && c.getName().equals("Day list")) {
        JComboBox<Integer> dayList = (JComboBox<Integer>) c;
        day = String.valueOf((int) dayList.getSelectedItem());
      }
      if (c.getName() != null && c.getName().equals("Month list")) {
        JComboBox<String> monthList = (JComboBox<String>) c;
        month = (String) monthList.getSelectedItem();
      }
      if (c.getName() != null && c.getName().equals("Year list")) {
        JComboBox<Integer> yearList = (JComboBox<Integer>) c;
        year = (int) yearList.getSelectedItem();
      }
    }
    String monthNum = "";
    switch (month) {
      case "January":
        monthNum = "01";
        break;
      case "February":
        monthNum = "02";
        break;
      case "March":
        monthNum = "03";
        break;
      case "April":
        monthNum = "04";
        break;
      case "May":
        monthNum = "05";
        break;
      case "June":
        monthNum = "06";
        break;
      case "July":
        monthNum = "07";
        break;
      case "August":
        monthNum = "08";
        break;
      case "September":
        monthNum = "09";
        break;
      case "October":
        monthNum = "10";
        break;
      case "November":
        monthNum = "11";
        break;
      case "December":
        monthNum = "12";
        break;
      default:
        JOptionPane.showMessageDialog(datePanel, "Something went wrong please try again.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
    assert day != null;
    if (day.length() < 2) {
      day = '0' + day;
    }
    String date = year + "-" + monthNum + "-" + day;
    return date;
  }

  @Override
  public JDialog saveFileDialog(List<String> names) {
    return new SavePortfolioDialog(this, names).createDialog();
  }

  @Override
  public JDialog getValueDialog(List<String> names) {
    return new GetValueDialog(this, names).createDialog();
  }

  @Override
  public JDialog createInvestDialog(List<String> names) {
    return new CreateInvestmentDialog(this, names).createDialog();
  }

  @Override
  public JDialog sellStockDialog(List<String> names) {
    return new CreateSellDialog(this, names).createDialog();
  }

  @Override
  public JDialog costBasisDialog(List<String> names) {
    return new GetCostBasisDialog(this, names).createDialog();
  }

  @Override
  public JDialog performanceDialog(List<String> names, Map<LocalDate, Double> performance) {
    return new ViewPerformanceDialog(this, names).createDialog();
  }

  @Override
  public JDialog createDollarCostAveragingDialogForExistingPortfolios(List<String> names) {
    return new DollarCostAveragingDialogForExistingPortfolios(this, names).createDialog();
  }

  @Override
  public JDialog createPortfolioWithDCADialog() {
    return new CreatePortfolioWithDCA(this).createDialog();
  }


}
