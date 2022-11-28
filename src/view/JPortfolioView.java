package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import constants.Constants;
import controller.Features;
import enums.MenuItems;
import enums.PortfolioTypes;
import model.Portfolio;
import model.PortfolioValue;
import model.Stock;
import utilities.DisplayUtils;
import utilities.Pair;

public class JPortfolioView extends JFrame implements IGUIPortfolioView, ItemListener {
  private static final String BUY_STOCK = Constants
          .MENU_TYPE.get(MenuItems.CREATE_TRANSACTION.getValue())[1];

  private static final String SELL_STOCK = Constants
          .MENU_TYPE.get(MenuItems.CREATE_TRANSACTION.getValue())[2];

  //  private final JLabel display;
//  private JButton enterButton;
//  public final JTextField input;
  JPanel cards; //a panel that uses CardLayout
  final static String CREATE_PANEL = Constants.
          MENU_TYPE.get(MenuItems.FLEXIBLE_PORTFOLIO.getValue())[1];
  final static String EXAMINE_PANEL = Constants.
          MENU_TYPE.get(MenuItems.FLEXIBLE_PORTFOLIO.getValue())[2];

  final static String VALUE_OF_PORTFOLIO_ON_DATE = Constants.
          MENU_TYPE.get(MenuItems.FLEXIBLE_PORTFOLIO.getValue())[3];

  final static String MAKE_TRANSACTIONS = Constants.
          MENU_TYPE.get(MenuItems.FLEXIBLE_PORTFOLIO.getValue())[4];

  final static String COST_BASIS = Constants.
          MENU_TYPE.get(MenuItems.FLEXIBLE_PORTFOLIO.getValue())[5];

  final static String PERFORMANCE_OF_PORTFOLIO = Constants.
          MENU_TYPE.get(MenuItems.FLEXIBLE_PORTFOLIO.getValue())[6];

  final static String APPLY_A_STRATEGY = "Apply a Strategy";

  // create components
  private final JLabel createPortfolioNameLabel = new JLabel(Constants.TEXT_VIEW_CONSTANTS
          .get(Constants.PROMPT_PORTFOLIO_NAME_KEY) + ": ");
  private final JTextField createPortfolioNameTextField = new JTextField(10);
  private final JButton createPortfolioSubmit = new JButton("Submit");


  // examine components
  private final JLabel examinePortfolioNameLabel = new JLabel(Constants.TEXT_VIEW_CONSTANTS
          .get(Constants.PROMPT_PORTFOLIO_NAME_KEY) + ": ");
  private final JTextField examinePortfolioNameTextField = new JTextField(10);
  private final JLabel examinePortfolioDateLabel = new JLabel(Constants.TEXT_VIEW_CONSTANTS
          .get(Constants.PROMPT_DATE_KEY) + ": ");
  private final JTextField examinePortfolioDateTextField = new JTextField(10);

  private final JButton examinePortfolioSubmit = new JButton("Submit");


  // Value of portfolio Components
  private final JLabel valueOfPortfolioNameLabel = new JLabel(Constants.TEXT_VIEW_CONSTANTS
          .get(Constants.PROMPT_PORTFOLIO_NAME_KEY) + ": ");
  private final JTextField valueOfPortfolioNameTextField = new JTextField(10);
  private final JLabel valueOfPortfolioDateLabel = new JLabel(Constants.TEXT_VIEW_CONSTANTS
          .get(Constants.PROMPT_DATE_KEY) + ": ");
  private final JTextField valueOfPortfolioDateTextField = new JTextField(10);
  private final JButton valueOfPortfolioSubmit = new JButton("Submit");

  // transactions

  // cost basis
  private final JLabel costBasisOfPortfolioNameLabel = new JLabel(Constants.TEXT_VIEW_CONSTANTS
          .get(Constants.PROMPT_PORTFOLIO_NAME_KEY) + ": ");
  private final JTextField costBasisOfPortfolioNameTextField = new JTextField(10);
  private final JLabel costBasisOfPortfolioDateLabel = new JLabel(
          Constants.TEXT_VIEW_CONSTANTS.get(Constants.PROMPT_DATE_KEY) + ": ");
  private final JTextField costBasisOfPortfolioDateTextField = new JTextField(10);
  private final JButton costBasisOfPortfolioSubmit = new JButton("Submit");

  // performance of portfolio
  private final JLabel performanceOfPortfolioNameLabel = new JLabel(
          Constants.TEXT_VIEW_CONSTANTS.get(Constants.PROMPT_PORTFOLIO_NAME_KEY) + ": ");
  private final JTextField performanceOfPortfolioNameTextField = new JTextField(10);
  private final JLabel performanceOfPortfolioStartDateLabel = new JLabel(
          Constants.TEXT_VIEW_CONSTANTS.get(Constants.PROMPT_DATE_KEY) + ": ");
  private final JTextField performanceOfPortfolioStartDateTextField = new JTextField(10);
  private final JLabel performanceOfPortfolioEndDateLabel = new JLabel(
          Constants.TEXT_VIEW_CONSTANTS.get(Constants.PROMPT_DATE_KEY) + ": ");
  private final JTextField performanceOfPortfolioEndDateTextField = new JTextField(10);
  private final JButton performanceOfPortfolioSubmit = new JButton("Submit");


  public void addComponentToPane(Container pane) {

    JPanel comboBoxPane = new JPanel();

    String[] comboBoxItems = {CREATE_PANEL, EXAMINE_PANEL, VALUE_OF_PORTFOLIO_ON_DATE
            , MAKE_TRANSACTIONS, COST_BASIS, PERFORMANCE_OF_PORTFOLIO};
    JComboBox<String> cb = new JComboBox<>(comboBoxItems);
    cb.setEditable(false);
    cb.addItemListener(this);
    comboBoxPane.add(cb);

    // createPortfolio card
    JPanel card1 = new JPanel();
    card1.add(createPortfolioNameLabel);
    card1.add(createPortfolioNameTextField);
    card1.add(createPortfolioSubmit);

    // examinePortfolio card
    JPanel card2 = new JPanel();
    card2.setLayout(new BoxLayout(card2, BoxLayout.Y_AXIS));
    JPanel card2a = new JPanel();
    JPanel card2b = new JPanel();

    card2a.add(examinePortfolioNameLabel);
    card2a.add(examinePortfolioNameTextField);
    card2b.add(examinePortfolioDateLabel);
    card2b.add(examinePortfolioDateTextField);


    card2.add(card2a);
    card2.add(card2b);
    card2.add(examinePortfolioSubmit);


    // value of a portfolio
    JPanel card3 = new JPanel();
    card3.setLayout(new BoxLayout(card3, BoxLayout.Y_AXIS));
    JPanel card3a = new JPanel();
    JPanel card3b = new JPanel();

    card3a.add(valueOfPortfolioNameLabel);
    card3a.add(valueOfPortfolioNameTextField);
    card3b.add(valueOfPortfolioDateLabel);
    card3b.add(valueOfPortfolioDateTextField);

    card3.add(card3a);
    card3.add(card3b);
    card3.add(valueOfPortfolioSubmit);


    // transactions - not completed
    JPanel card4 = new JPanel();
    String[] transactionComboBoxItems = {BUY_STOCK, SELL_STOCK};
    JComboBox<String> transactionComboBox = new JComboBox<>(transactionComboBoxItems);
    card4.setLayout(new BoxLayout(card4, BoxLayout.Y_AXIS));
    card4.add(transactionComboBox);
    JPanel card4a = new JPanel();
    card4a.add(new JButton("dss"));
    JPanel card4b = new JPanel();
    card4b.add(new JTextField());
    card4.add(card4a);
    card4.add(card4b);


    // cost basis
    JPanel card5 = new JPanel();
    card5.setLayout(new BoxLayout(card5, BoxLayout.Y_AXIS));
    JPanel card5a = new JPanel();
    JPanel card5b = new JPanel();

    card5a.add(costBasisOfPortfolioNameLabel);
    card5a.add(costBasisOfPortfolioNameTextField);
    card5b.add(costBasisOfPortfolioDateLabel);
    card5b.add(costBasisOfPortfolioDateTextField);

    card5.add(card5a);
    card5.add(card5b);
    card5.add(costBasisOfPortfolioSubmit);

    // Performance of Portfolio
    JPanel card6 = new JPanel();
    card6.setLayout(new BoxLayout(card6, BoxLayout.Y_AXIS));
    JPanel card6a = new JPanel();
    JPanel card6b = new JPanel();
    JPanel card6c = new JPanel();

    card6a.add(performanceOfPortfolioNameLabel);
    card6a.add(performanceOfPortfolioNameTextField);
    card6b.add(performanceOfPortfolioStartDateLabel);
    card6b.add(performanceOfPortfolioStartDateTextField);
    card6c.add(performanceOfPortfolioEndDateLabel);
    card6c.add(performanceOfPortfolioEndDateTextField);

    card6.add(card6a);
    card6.add(card6b);
    card6.add(card6c);
    card6.add(performanceOfPortfolioSubmit);

    //Create the panel that contains the "cards".
    cards = new JPanel(new CardLayout());
    cards.add(card1, CREATE_PANEL);
    cards.add(card2, EXAMINE_PANEL);
    cards.add(card3, VALUE_OF_PORTFOLIO_ON_DATE);
    cards.add(card4, MAKE_TRANSACTIONS);
    cards.add(card5, COST_BASIS);
    cards.add(card6, PERFORMANCE_OF_PORTFOLIO);
//    cards.add(card7, APPLY_A_STRATEGY);


    pane.add(comboBoxPane, BorderLayout.PAGE_START);
    pane.add(cards, BoxLayout.Y_AXIS);
//    pane.add(buttonPanel, BorderLayout.SOUTH);
  }


  public JPortfolioView(int promptNumber) {
    //Create and set up the window.
    JFrame frame = new JFrame(Constants.
            MENU_TYPE.get(MenuItems.FLEXIBLE_PORTFOLIO.getValue())[0]);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Create and set up the content pane.
    this.addComponentToPane(frame.getContentPane());

    //Display the window.
    frame.setSize(500, 300);
    frame.setLocation(200, 200);
    frame.pack();
    frame.setVisible(true);
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public String getPortfolioNameInputString() {
    System.out.println(this.getContentPane().getComponent(1));
    return "dsd";
  }

  @Override
  public void clearInputString() {
    createPortfolioNameTextField.setText("");
    examinePortfolioNameTextField.setText("");
    examinePortfolioDateTextField.setText("");
    valueOfPortfolioNameTextField.setText("");
    valueOfPortfolioDateTextField.setText("");
    costBasisOfPortfolioNameTextField.setText("");
    costBasisOfPortfolioDateTextField.setText("");
    performanceOfPortfolioNameTextField.setText("");
    performanceOfPortfolioStartDateTextField.setText("");
    performanceOfPortfolioEndDateTextField.setText("");
  }

  @Override
  public void addFeatures(Features features) {
    createPortfolioSubmit.addActionListener(evt -> {
      try {
        features.createPortfolio(createPortfolioNameTextField.getText());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    examinePortfolioSubmit.addActionListener(evt -> {
      try {
        features.examinePortfolio(examinePortfolioNameTextField.getText(),
                examinePortfolioDateTextField.getText());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    valueOfPortfolioSubmit.addActionListener(evt -> {
      try {
        features.valueOfPortfolio(valueOfPortfolioNameTextField.getText(),
                valueOfPortfolioDateTextField.getText());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    // add transaction event

    costBasisOfPortfolioSubmit.addActionListener(evt -> {
      try {
        features.costBasisOfPortfolio(costBasisOfPortfolioNameTextField.getText(),
                costBasisOfPortfolioDateTextField.getText());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    // performance
    performanceOfPortfolioSubmit.addActionListener(evt -> {
      try {
        features.performanceOfPortfolio(performanceOfPortfolioNameTextField.getText(),
                performanceOfPortfolioStartDateTextField.getText(),
                performanceOfPortfolioEndDateTextField.getText());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    this.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(KeyEvent e) {
//      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//        features.createPortfolio(portfolioNameTextField.getText());
//      }
      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          System.out.println(e.getKeyCode());
          try {
            features.createPortfolio(createPortfolioNameTextField.getText());
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
//      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//        features.createPortfolio(portfolioNameTextField.getText());
//      }
      }
    });
  }

  @Override
  public void showString(String s) {
    JOptionPane.showMessageDialog(
            this.getParent()
            , s
            , ""
            , JOptionPane.INFORMATION_MESSAGE
            , new ImageIcon());
  }

  @Override
  public void showOptions(int selectedMenuItem) {

  }

  @Override
  public void showOptionError() {

  }

  @Override
  public void showPrompt(String key) {

  }

  @Override
  public void showPortfolio(Portfolio portfolio) {
    String portfolioName = portfolio.getName();
    List<Stock> stocks = portfolio.getStocks();
    if (stocks.size() == 0) {
      showString(String.format("%nThe portfolio %s has no stocks.%n", portfolioName));
      return;
    }

    Vector<Vector<String>> tableRows = new Vector<>();
    for (int i = 0; i < stocks.size(); i++) {
      Vector<String> row = new Vector<>();

      Stock stock = stocks.get(i);
      row.add(String.valueOf(i + 1));
      row.add(stock.getSymbol());
      row.add(String.valueOf(stock.getQuantity()));
      row.add(stock.getOperation().toString());

      tableRows.add(row);
    }

    Vector<String> columnNames = new Vector<>();
    columnNames.add("ID");
    columnNames.add("Ticker symbol");
    columnNames.add("Quantity");
    columnNames.add("Operation");

    JPanel compositionPanel = new JPanel(new BorderLayout(0, 10));

    JLabel msgLabel = new JLabel(
            String.format("Composition of the portfolio %s", portfolioName));

    final JTable table = new JTable(tableRows, columnNames);
    JScrollPane jScrollPane = new JScrollPane(table);
    compositionPanel.add(jScrollPane, BorderLayout.SOUTH);
    compositionPanel.add(msgLabel, BorderLayout.NORTH);
    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
    table.setFillsViewportHeight(true);
    JOptionPane.showMessageDialog(this.getParent(),
            compositionPanel,
            "",
            JOptionPane.INFORMATION_MESSAGE,
            new ImageIcon());
  }

  @Override
  public void showPortfolioValue(Pair<Portfolio, Double> portfolioValue) {

    Portfolio portfolio = portfolioValue.getKey();
    String portfolioName = portfolio.getName();
    List<Stock> stocks = portfolio.getStocks();

    if (stocks.size() == 0) {
      showString(String.format("The portfolio %s has no stocks.", portfolioName));
      return;
    }

    Vector<Vector<String>> tableRows = new Vector<>();
    for (int i = 0; i < stocks.size(); i++) {
      Vector<String> row = new Vector<>();

      Stock stock = stocks.get(i);
      row.add(String.valueOf(i + 1));
      row.add(stock.getSymbol());
      row.add(String.valueOf(stock.getQuantity()));
      row.add("$" + stock.getClose());
      if (portfolio.getPortfolioType() == PortfolioTypes.FLEXIBLE) {
        row.add(stock.getOperation().toString());
      }

      tableRows.add(row);
    }

    Vector<String> columnNames = new Vector<>();
    columnNames.add("ID");
    columnNames.add("Ticker symbol");
    columnNames.add("Quantity");
    columnNames.add("Closing price");
    columnNames.add("Operation");


    JPanel compositionPanel = new JPanel(new BorderLayout(0, 10));

    JLabel msgLabelNorth = new JLabel(
            String.format("Value of the portfolio %s", portfolioName));

    JLabel msgLabelSouth = new JLabel(
            String.format("Total value: $%.2f", portfolioValue.getValue()));

    final JTable table = new JTable(tableRows, columnNames);
    JScrollPane jScrollPane = new JScrollPane(table);
    compositionPanel.add(jScrollPane, BorderLayout.CENTER);
    compositionPanel.add(msgLabelNorth, BorderLayout.NORTH);
    compositionPanel.add(msgLabelSouth, BorderLayout.SOUTH);
    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
    table.setFillsViewportHeight(true);
    JOptionPane.showMessageDialog(this.getParent(),
            compositionPanel,
            "",
            JOptionPane.INFORMATION_MESSAGE,
            new ImageIcon());
  }

  @Override
  public void showPortfolioPerformance(String portfolioName, String fromDate, String toDate, List<PortfolioValue> portfolioValues) {

  }

  @Override
  public String getOption() {
    return null;
  }

  @Override
  public void setActionLister(ActionListener actionLister) {

  }


//  @Override
//  public String getOption() {
//    return input.getText();
//  }
//
//  @Override
//  public void setActionLister(ActionListener actionLister) {
//    enterButton.addActionListener(actionLister);
//  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    CardLayout cl = (CardLayout) (cards.getLayout());
    cl.show(cards, (String) e.getItem());
  }
}
