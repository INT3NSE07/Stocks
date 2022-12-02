package view;

import static constants.Constants.PROMPT_STOCK_SYMBOL_KEY;
import static constants.Constants.PROMPT_WEIGHT;
import static utilities.StringUtils.isNullOrWhiteSpace;

import constants.Constants;
import controller.IPortfolioFeatures;
import enums.MenuItems;
import enums.PortfolioTypes;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.Portfolio;
import model.PortfolioValue;
import model.Stock;
import org.jfree.data.category.DefaultCategoryDataset;
import utilities.DisplayUtils.BarChart;
import utilities.Pair;

/**
 * This class represents the GUI portfolio view. It uses java swing to render a UI through which the
 * user is able to perform all operations on a portfolio.
 */
public class JPortfolioView extends JFrame implements IGUIPortfolioView, ItemListener {

  private static final String APPLY_A_STRATEGY = "Apply a Strategy";
  private static final String CREATE_PANEL = Constants
      .MENU_TYPE.get(MenuItems.FLEXIBLE_PORTFOLIO.getValue())[1];
  private static final String EXAMINE_PANEL = Constants
      .MENU_TYPE.get(MenuItems.FLEXIBLE_PORTFOLIO.getValue())[2];
  private static final String VALUE_OF_PORTFOLIO_ON_DATE = Constants
      .MENU_TYPE.get(MenuItems.FLEXIBLE_PORTFOLIO.getValue())[3];
  private static final String MAKE_TRANSACTIONS = Constants
      .MENU_TYPE.get(MenuItems.FLEXIBLE_PORTFOLIO.getValue())[4];
  private static final String COST_BASIS = Constants
      .MENU_TYPE.get(MenuItems.FLEXIBLE_PORTFOLIO.getValue())[5];
  private static final String PERFORMANCE_OF_PORTFOLIO = Constants
      .MENU_TYPE.get(MenuItems.FLEXIBLE_PORTFOLIO.getValue())[6];
  private static final String BUY_STOCK = Constants
      .MENU_TYPE.get(MenuItems.CREATE_TRANSACTION.getValue())[1];
  private static final String SELL_STOCK = Constants
      .MENU_TYPE.get(MenuItems.CREATE_TRANSACTION.getValue())[2];

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
  private final JLabel transactionCommissionLabel = new JLabel(Constants.TEXT_VIEW_CONSTANTS
      .get(Constants.PROMPT_COMMISSION_KEY) + ": ");
  private final JTextField transactionCommissionTextField = new JTextField(10);
  //  JPanel card4bForTransactionCombobox = new JPanel();
  private final JButton transactionCommissionSubmit = new JButton("Proceed");
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
      Constants.TEXT_VIEW_CONSTANTS.get(Constants.PROMPT_START_DATE_KEY) + ": ");
  private final JTextField performanceOfPortfolioStartDateTextField = new JTextField(10);
  private final JLabel performanceOfPortfolioEndDateLabel = new JLabel(
      Constants.TEXT_VIEW_CONSTANTS.get(Constants.PROMPT_DATE_KEY) + ": ");
  private final JTextField performanceOfPortfolioEndDateTextField = new JTextField(10);
  private final JButton performanceOfPortfolioSubmit = new JButton("Submit");
  private final JLabel transactionPortfolioNameLabel = new JLabel(
      Constants.TEXT_VIEW_CONSTANTS.get(Constants.PROMPT_PORTFOLIO_NAME_KEY) + ": ");
  private final JTextField transactionPortfolioNameTextField = new JTextField(10);
  JPanel cards; //a panel that uses CardLayout
  JComboBox<String> cb = null;
  private JComboBox<String> transactionComboBox = null;

  /**
   * A {@link JPortfolioView} constructor to initialize the parent frame.
   */
  public JPortfolioView() {
    //Create and set up the window.
    JFrame frame = new JFrame(Constants.MENU_TYPE.get(MenuItems.FLEXIBLE_PORTFOLIO.getValue())[0]);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Create and set up the content pane.
    this.addComponentToPane(frame.getContentPane());

    //Display the window.
    frame.setSize(500, 300);
    frame.setLocationRelativeTo(null);
    frame.pack();
    frame.setVisible(true);
  }

  private void addComponentToPane(Container pane) {
    JPanel comboBoxPane = new JPanel();

    String[] comboBoxItems = {CREATE_PANEL, EXAMINE_PANEL, VALUE_OF_PORTFOLIO_ON_DATE,
        MAKE_TRANSACTIONS, COST_BASIS, PERFORMANCE_OF_PORTFOLIO};
    cb = new JComboBox<>(comboBoxItems);
    cb.setEditable(false);
    cb.addItemListener(this);
    comboBoxPane.add(cb);

    //Create the panel that contains the "cards".
    cards = new JPanel(new CardLayout());
    cards.add(getCreateCardPanel(), CREATE_PANEL);
    cards.add(getExamineCardPanel(), EXAMINE_PANEL);
    cards.add(getValueOfPortfolioCardPanel(), VALUE_OF_PORTFOLIO_ON_DATE);
    cards.add(getTransactionCardPanel(), MAKE_TRANSACTIONS);
    cards.add(getCostBasisCardPanel(), COST_BASIS);
    cards.add(getPerformanceOfPortfolioCardPanel(), PERFORMANCE_OF_PORTFOLIO);

    pane.add(comboBoxPane, BorderLayout.PAGE_START);
    pane.add(cards, BoxLayout.Y_AXIS);
  }

  private JPanel getPerformanceOfPortfolioCardPanel() {
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
    return card6;
  }

  private JPanel getCostBasisCardPanel() {
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
    return card5;
  }

  private JPanel getTransactionCardPanel() {
    // transactions - not completed
    JPanel card4 = new JPanel();
    card4.setLayout(new BoxLayout(card4, BoxLayout.Y_AXIS));

    // Prompt for commission fee
    JPanel card4a = new JPanel();
    card4a.add(transactionCommissionLabel);
    card4a.add(transactionCommissionTextField);

    // Prompt for portfolio name panel
    JPanel card4b = new JPanel();
    card4b.add(transactionPortfolioNameLabel);
    card4b.add(transactionPortfolioNameTextField);

    // combo box
    JPanel card4c = new JPanel();
    String[] transactionComboBoxItems = {BUY_STOCK, SELL_STOCK, APPLY_A_STRATEGY};
    transactionComboBox = new JComboBox<>(transactionComboBoxItems);
    card4c.add(transactionComboBox);

    // Prompt for commission fee
    JPanel card4d = new JPanel();
    card4d.add(transactionCommissionSubmit);

    // summing aa sub cards
    card4.add(card4a);
    card4.add(card4b);
    card4.add(card4c);
    card4.add(card4d);
    return card4;
  }

  private JPanel getValueOfPortfolioCardPanel() {
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
    return card3;
  }

  private JPanel getExamineCardPanel() {
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
    return card2;
  }

  private JPanel getCreateCardPanel() {
    JPanel card1 = new JPanel();
    card1.add(createPortfolioNameLabel);
    card1.add(createPortfolioNameTextField);
    card1.add(createPortfolioSubmit);
    return card1;
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void clearInputString() {
    createPortfolioNameTextField.setText("");
    examinePortfolioNameTextField.setText("");
    examinePortfolioDateTextField.setText("");
    valueOfPortfolioNameTextField.setText("");
    valueOfPortfolioDateTextField.setText("");
    transactionCommissionTextField.setText("");
    transactionPortfolioNameTextField.setText("");
    costBasisOfPortfolioNameTextField.setText("");
    costBasisOfPortfolioDateTextField.setText("");
    performanceOfPortfolioNameTextField.setText("");
    performanceOfPortfolioStartDateTextField.setText("");
    performanceOfPortfolioEndDateTextField.setText("");
  }

  @Override
  public void addFeatures(IPortfolioFeatures features) {

    createPortfolioSubmit.addActionListener(evt -> {

      try {
        if (!isNullOrWhiteSpace(createPortfolioNameTextField.getText())) {
          int optionValue = JOptionPane.showConfirmDialog(
              this.getParent(),
              "Would you like to apply strategy on this portfolio",
              "Confirm",
              JOptionPane.YES_NO_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              new ImageIcon()
          );

          if (optionValue == 0) { // success path
            buildStrategyPane(features);
          } else {
            features.createPortfolio(createPortfolioNameTextField.getText());
          }
        } else {
          features.createPortfolio(createPortfolioNameTextField.getText());
        }

      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    examinePortfolioSubmit.addActionListener(evt -> {
      try {
        if (isNullOrWhiteSpace(examinePortfolioNameTextField.getText())) {
          showString("Portfolio name cannot be null.");
          return;
        }
        features.examinePortfolio(examinePortfolioNameTextField.getText(),
            examinePortfolioDateTextField.getText());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    valueOfPortfolioSubmit.addActionListener(evt -> {
      try {
        if (isNullOrWhiteSpace(valueOfPortfolioNameTextField.getText())) {
          showString("Portfolio name cannot be null.");
          return;
        }
        features.valueOfPortfolio(valueOfPortfolioNameTextField.getText(),
            valueOfPortfolioDateTextField.getText());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    // add transaction event
    transactionCommissionSubmit.addActionListener(evt -> {

      if (isNullOrWhiteSpace(transactionCommissionTextField.getText())) {
        showString("Commission Fee cannot be Empty.");
        return;
      }

      if (isNullOrWhiteSpace(transactionPortfolioNameTextField.getText())) {
        showString("Portfolio name cannot be Empty.");
        return;
      }

      String commissionFee = transactionCommissionTextField.getText();

      JPanel transactionsPopup = new JPanel();
      transactionsPopup.setLayout(new BoxLayout(transactionsPopup, BoxLayout.Y_AXIS));

      if (transactionComboBox.getSelectedIndex() == 2) {
        buildStrategyPane(features);
      } else {

        // ticker symbol
        JPanel symbolPanel = new JPanel();
        JLabel symbolNameLabel = new JLabel(
            Constants.TEXT_VIEW_CONSTANTS.get(PROMPT_STOCK_SYMBOL_KEY) + ": ");
        JTextField symbolNameTextField = new JTextField(10);
        symbolPanel.add(symbolNameLabel);
        symbolPanel.add(symbolNameTextField);

        //quantity
        JPanel quantityPanel = new JPanel();
        JLabel quantityLabel = new JLabel(
            Constants.TEXT_VIEW_CONSTANTS.get(Constants.PROMPT_QUANTITY_KEY) + ": ");
        JTextField quantityTextField = new JTextField(10);
        quantityPanel.add(quantityLabel);
        quantityPanel.add(quantityTextField);

        // date
        JPanel datePanel = new JPanel();
        JLabel dateLabel = new JLabel(
            Constants.TEXT_VIEW_CONSTANTS.get(Constants.PROMPT_DATE_KEY) + ": ");
        JTextField dateTextField = new JTextField(10);
        datePanel.add(dateLabel);
        datePanel.add(dateTextField);

        transactionsPopup.add(symbolPanel);
        transactionsPopup.add(quantityPanel);
        transactionsPopup.add(datePanel);

        JOptionPane.showMessageDialog(
            getParent(),
            transactionsPopup,
            "",
            JOptionPane.INFORMATION_MESSAGE,
            new ImageIcon()
        );

        try {

          if (isNullOrWhiteSpace(quantityTextField.getText())) {
            showString("Quantity cannot be Empty.");
            return;
          }
          if (isNullOrWhiteSpace(symbolNameTextField.getText())) {
            showString("Ticker Symbol cannot be Empty.");
            return;
          }
          features.createTransaction(commissionFee,
              transactionPortfolioNameTextField.getText(),
              symbolNameTextField.getText(),
              quantityTextField.getText(),
              dateTextField.getText(),
              String.valueOf(transactionComboBox.getSelectedIndex() + 1)
          );
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    });

    costBasisOfPortfolioSubmit.addActionListener(evt -> {
      try {
        if (isNullOrWhiteSpace(costBasisOfPortfolioNameTextField.getText())) {
          showString("Portfolio name cannot be null.");
          return;
        }
        features.costBasisOfPortfolio(costBasisOfPortfolioNameTextField.getText(),
            costBasisOfPortfolioDateTextField.getText());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    // performance
    performanceOfPortfolioSubmit.addActionListener(evt -> {
      try {
        if (isNullOrWhiteSpace(performanceOfPortfolioNameTextField.getText())) {
          showString("Portfolio name cannot be null.");
          return;
        }
        if (isNullOrWhiteSpace(performanceOfPortfolioStartDateTextField.getText())) {
          showString("Start date cannot be null.");
          return;
        }
        features.performanceOfPortfolio(performanceOfPortfolioNameTextField.getText(),
            performanceOfPortfolioStartDateTextField.getText(),
            performanceOfPortfolioEndDateTextField.getText());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  private void buildStrategyPane(IPortfolioFeatures features) {
    JFrame f = new JFrame();
    Box center = Box.createVerticalBox();
    JScrollPane jScrollPane = new JScrollPane(center);
    JPanel strategyCards = new JPanel(new CardLayout());
    JPanel fixedStrategyPanel = new JPanel();
    fixedStrategyPanel.setLayout(new BoxLayout(fixedStrategyPanel, BoxLayout.Y_AXIS));
    JPanel dollarCostStrategyPanel = new JPanel();
    dollarCostStrategyPanel.setLayout(new BoxLayout(dollarCostStrategyPanel, BoxLayout.Y_AXIS));

    JPanel strategyComboBoxPanel = new JPanel();
    String[] strategyComboBoxItems = {Constants
        .MENU_TYPE.get(MenuItems.APPLY_STRATEGY.getValue())[1],
        Constants.MENU_TYPE.get(MenuItems.APPLY_STRATEGY.getValue())[2]};
    JComboBox<String> strategyComboBox = new JComboBox<>(strategyComboBoxItems);
    strategyComboBox.setEditable(false);
    strategyComboBoxPanel.add(strategyComboBox);
    strategyComboBox.addItemListener(e -> {
      CardLayout cl = (CardLayout) (strategyCards.getLayout());
      cl.show(strategyCards, (String) e.getItem());
    });

    //Common components
    JPanel commissionPanel = new JPanel();
    JLabel commissionLabel = new JLabel(Constants.TEXT_VIEW_CONSTANTS
        .get(Constants.PROMPT_COMMISSION_KEY) + ": ");
    JTextField commissionTextField = new JTextField(10);
    commissionPanel.add(commissionLabel);
    commissionPanel.add(commissionTextField);

    // disable visibility while on create card.
    if (cb.getSelectedIndex() == 3) {
      commissionPanel.setVisible(false);
      commissionTextField.setText(transactionCommissionTextField.getText());
    }

    JPanel investmentPanel = new JPanel();
    JLabel investmentLabel = new JLabel(Constants.TEXT_VIEW_CONSTANTS
        .get(Constants.PROMPT_INVESTMENT) + ": ");
    JTextField investmentTextField = new JTextField(10);
    investmentPanel.add(investmentLabel);
    investmentPanel.add(investmentTextField);

    // fixedStrategyPanel
    JPanel fixedStrategyDatePanel = new JPanel();
    JLabel fixedStrategyDateLabel = new JLabel(
        "Please Enter Date in Format (YYYY-MM-DD): ");
    JTextField fixedStrategyDateTextField = new JTextField(10);
    fixedStrategyDatePanel.add(fixedStrategyDateLabel);
    fixedStrategyDatePanel.add(fixedStrategyDateTextField);

    fixedStrategyPanel.add(fixedStrategyDatePanel);

    // dollarCostStrategyPanel
    JPanel dollarCostStrategyStartDatePanel = new JPanel();
    JLabel dollarCostStrategyStartDateLabel = new JLabel(Constants.TEXT_VIEW_CONSTANTS
        .get(Constants.PROMPT_START_DATE_KEY) + ": ");
    JTextField dollarCostStrategyStartDateTextField = new JTextField(10);
    dollarCostStrategyStartDatePanel.add(dollarCostStrategyStartDateLabel);
    dollarCostStrategyStartDatePanel.add(dollarCostStrategyStartDateTextField);

    JPanel dollarCostStrategyEndDatePanel = new JPanel();
    JLabel dollarCostStrategyEndDateLabel = new JLabel(Constants.TEXT_VIEW_CONSTANTS
        .get(Constants.PROMPT_END_DATE_KEY) + ": ");
    JTextField dollarCostStrategyEndDateTextField = new JTextField(10);
    dollarCostStrategyEndDatePanel.add(dollarCostStrategyEndDateLabel);
    dollarCostStrategyEndDatePanel.add(dollarCostStrategyEndDateTextField);

    JPanel dollarCostStrategyPeriodPanel = new JPanel();
    JLabel dollarCostStrategyPeriodLabel = new JLabel(Constants.TEXT_VIEW_CONSTANTS
        .get(Constants.PROMPT_PERIOD) + ": ");
    JTextField dollarCostStrategyPeriodTextField = new JTextField(10);
    dollarCostStrategyPeriodPanel.add(dollarCostStrategyPeriodLabel);
    dollarCostStrategyPeriodPanel.add(dollarCostStrategyPeriodTextField);

    dollarCostStrategyPanel.add(dollarCostStrategyStartDatePanel);
    dollarCostStrategyPanel.add(dollarCostStrategyEndDatePanel);
    dollarCostStrategyPanel.add(dollarCostStrategyPeriodPanel);

    // Button for strategy panel
    JPanel commonComponentsInStrategyPanel = new JPanel();
    JButton addStockWeightsButton = new JButton("Add Stocks & Weights");
    JButton createStrategyConfirmButton = new JButton("Confirm");
    JLabel defaultStockWeightPairSymbolLabel = new JLabel(
        Constants.TEXT_VIEW_CONSTANTS.get(PROMPT_STOCK_SYMBOL_KEY) + ": ");
    JTextField defaultStockWeightPairSymbolTextField = new JTextField(10);
    JLabel defaultStockWeightPairWeightLabel = new JLabel(
        Constants.TEXT_VIEW_CONSTANTS.get(PROMPT_WEIGHT) + ": ");
    JTextField defaultStockWeightPairWeightTextField = new JTextField(10);

    List<JLabel> symbolsLabel = new ArrayList<>(
        Collections.singletonList(defaultStockWeightPairSymbolLabel));
    List<JTextField> symbolsTextField = new ArrayList<>(
        Collections.singletonList(defaultStockWeightPairSymbolTextField));

    List<JLabel> weightsLabel = new ArrayList<>(
        Collections.singletonList(defaultStockWeightPairWeightLabel));
    List<JTextField> weightsTextField = new ArrayList<>(
        Collections.singletonList(defaultStockWeightPairWeightTextField));

    commonComponentsInStrategyPanel.setLayout(new BoxLayout(
        commonComponentsInStrategyPanel, BoxLayout.Y_AXIS));

    JPanel buttonsPanel = new JPanel();
    buttonsPanel.add(addStockWeightsButton);
    buttonsPanel.add(createStrategyConfirmButton);

    JPanel defaultStockWeightPairPanel = new JPanel();
    defaultStockWeightPairPanel.add(defaultStockWeightPairSymbolLabel);
    defaultStockWeightPairPanel.add(defaultStockWeightPairSymbolTextField);
    defaultStockWeightPairPanel.add(defaultStockWeightPairWeightLabel);
    defaultStockWeightPairPanel.add(defaultStockWeightPairWeightTextField);

    commonComponentsInStrategyPanel.add(defaultStockWeightPairPanel);
    commonComponentsInStrategyPanel.add(commissionPanel);
    commonComponentsInStrategyPanel.add(investmentPanel);
    commonComponentsInStrategyPanel.add(buttonsPanel);

    addStockWeightsButton.addActionListener(e -> {
      JPanel stockWeightPanel = new JPanel();

      JLabel symbolLabel = new JLabel(
          Constants.TEXT_VIEW_CONSTANTS.get(PROMPT_STOCK_SYMBOL_KEY) + ": ");
      JTextField symbolTextField = new JTextField(10);
      JLabel weightLabel = new JLabel(
          Constants.TEXT_VIEW_CONSTANTS.get(PROMPT_WEIGHT) + ": ");
      JTextField weightTextField = new JTextField(10);

      stockWeightPanel.add(symbolLabel);
      stockWeightPanel.add(symbolTextField);
      stockWeightPanel.add(weightLabel);
      stockWeightPanel.add(weightTextField);

      symbolsLabel.add(symbolLabel);
      symbolsTextField.add(symbolTextField);
      weightsLabel.add(weightLabel);
      weightsTextField.add(weightTextField);

      if (strategyComboBox.getSelectedIndex() == 0) {
        fixedStrategyPanel.add(stockWeightPanel);
        f.validate();
        f.repaint();
      } else if (strategyComboBox.getSelectedIndex() == 1) {
        dollarCostStrategyPanel.add(stockWeightPanel);
        f.validate();
        f.repaint();
      }
    });
    createStrategyConfirmButton.addActionListener(cevt -> {
      String portfolioName = null;
      if (cb.getSelectedIndex() == 0) {
        portfolioName = createPortfolioNameTextField.getText();
      } else {
        portfolioName = transactionPortfolioNameTextField.getText();
      }
      if (strategyComboBox.getSelectedIndex() == 0) {
        // fixed strategy
        List<Pair<String, String>> stockWeightList = new ArrayList<>();

        for (int i = 0; i < symbolsTextField.size(); i++) {
          Pair<String, String> stringDoublePair = new Pair<>(
              symbolsTextField.get(i).getText(),
              weightsTextField.get(i).getText());
          stockWeightList.add(stringDoublePair);
        }
        try {
          features.applyStrategy(
              commissionTextField.getText(),
              strategyComboBox.getSelectedIndex(),
              portfolioName,
              stockWeightList,
              investmentTextField.getText(),
              fixedStrategyDateTextField.getText(),
              null,
              0,
              cb.getSelectedIndex() == 0 ? MenuItems.CREATE_PORTFOLIO : MenuItems.CREATE_TRANSACTION
          );
          f.dispose();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      } else if (strategyComboBox.getSelectedIndex() == 1) {
        // dollar cost averaging
        List<Pair<String, String>> stockWeightList = new ArrayList<>();

        for (int i = 0; i < symbolsTextField.size(); i++) {
          Pair<String, String> stringDoublePair = new Pair<>(
              symbolsTextField.get(i).getText(),
              weightsTextField.get(i).getText());
          stockWeightList.add(stringDoublePair);
        }
        try {
          features.applyStrategy(
              commissionTextField.getText(),
              strategyComboBox.getSelectedIndex(),
              portfolioName,
              stockWeightList,
              investmentTextField.getText(),
              dollarCostStrategyStartDateTextField.getText(),
              dollarCostStrategyEndDateTextField.getText(),
              Integer.parseInt(dollarCostStrategyPeriodTextField.getText()),
              cb.getSelectedIndex() == 0 ? MenuItems.CREATE_PORTFOLIO : MenuItems.CREATE_TRANSACTION
          );
          f.dispose();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }

    });

    strategyCards.add(fixedStrategyPanel, Constants
        .MENU_TYPE.get(MenuItems.APPLY_STRATEGY.getValue())[1]);

    strategyCards.add(dollarCostStrategyPanel, Constants
        .MENU_TYPE.get(MenuItems.APPLY_STRATEGY.getValue())[2]);

    center.add(strategyComboBoxPanel);
    center.add(strategyCards, BoxLayout.Y_AXIS);

    f.getContentPane().add(jScrollPane);
    f.getContentPane().add(commonComponentsInStrategyPanel, BorderLayout.SOUTH);
    f.setSize(500, 300);
    f.setLocationRelativeTo(null);
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    f.setVisible(true);
    f.pack();
  }

  @Override
  public void showString(String s) {
    JOptionPane.showMessageDialog(
        this.getParent(),
        s,
        "",
        JOptionPane.INFORMATION_MESSAGE,
        new ImageIcon());
  }

  @Override
  public void showOptions(int selectedMenuItem) {
    // No Implementation required.
  }

  @Override
  public void showOptionError() {
    // No Implementation required.
  }

  @Override
  public void showPrompt(String key) {
    // No Implementation required.
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
    JOptionPane.showMessageDialog(
        this.getParent(),
        compositionPanel,
        "Examine Portfolio",
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
        "Value of Portfolio",
        JOptionPane.INFORMATION_MESSAGE,
        new ImageIcon());
  }

  @Override
  public void showPortfolioPerformance(String portfolioName,
      String fromDate,
      String toDate,
      List<PortfolioValue> portfolioValues) {

    String heading = String.format("Performance of portfolio %s from %s to %s",
        portfolioName,
        fromDate,
        toDate);

    double maxValue = portfolioValues.stream().max(Comparator.comparing(PortfolioValue::getValue))
        .get().getValue();

    DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
    for (PortfolioValue portfolioValue : portfolioValues) {
      defaultCategoryDataset.addValue(portfolioValue.getValue(), "",
          String.format("%s - %s:  ",
              portfolioValue.getFromDate(), portfolioValue.getToDate()));
    }

    new BarChart("Portfolio Performance", heading, defaultCategoryDataset, maxValue);
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    CardLayout cl = (CardLayout) (cards.getLayout());
    cl.show(cards, (String) e.getItem());
  }
}
