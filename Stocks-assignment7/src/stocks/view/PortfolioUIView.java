package stocks.view;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import stocks.controller.Features;

/**
 * This interface represents the view for a User Interface built on Java Swing. It has methods to
 * inform users of any errors, any information messages and to create dialogs for different tasks
 * that a user might be doing. It also has methods to get certain standard fields from different
 * dialogs for easier use by the controller.
 */
public interface PortfolioUIView {
  /**
   * Adds features that the UI supports to the view by getting them from the Features interface that
   * acts as the controller for this view.
   *
   * @param features the controller of this view
   */
  void addFeatures(Features features);

  /**
   * Create the dialog that allows a user to buy a stock for a flexible portfolio.
   *
   * @param portfolioNames the names of all the created portfolios
   * @return the dialog that the user interacts with
   */
  JDialog createBuyDialog(List<String> portfolioNames);

  /**
   * Creates an error message to inform user of what went wrong when they try to do something that
   * is not supported/pass an invalid input.
   *
   * @param errorMessage the message that informs the user of the error that occurred
   * @param parent       the dialog in which the error occurred
   */
  void createErrorDialog(String errorMessage, JDialog parent);

  /**
   * Creates an information dialog to inform the user if their action was successful or if
   * further input is required to complete the action.
   *
   * @param informationMessage the message to inform the user of action status
   * @param parent             the dialog from which the message is being generated
   */
  void createInformationDialog(String informationMessage, JDialog parent);

  /**
   * Find the button that allows users to add a share somewhere while performing input for an action
   * that they can do.
   *
   * @param dialog the dialog in which we want to access the button
   * @return the JButton instance of the "Add share" button in the dialog
   */
  JButton addShareButton(JDialog dialog);

  /**
   * Finds the text field where the user enters the ticker value while performing input for an
   * action that they can do.
   *
   * @param dialog the dialog in which we want to access the text field
   * @return the JTextField instance of the "Ticker" text field
   */
  JTextField getTicker(JDialog dialog);

  /**
   * Finds the text field where the user enters the number of shares while performing input for
   * an action that they can do.
   *
   * @param dialog the dialog in which we want to access the text field
   * @return the JTextField instance of the "Number of shares" text field
   */
  JTextField getShares(JDialog dialog);

  /**
   * Creates the dialog that allows a user to create a flexible portfolio manually.
   *
   * @return the dialog that the user interacts with
   */
  JDialog createPortfolioDialog();

  /**
   * Finds the text field where the user enters the name of the portfolio while performing input
   * for an action that they can do.
   *
   * @param dialog the dialog in which we want to access the text field
   * @return the JTextField instance of "Portfolio Names" text field
   */
  JTextField getPortfolioName(JDialog dialog);

  /**
   * Finds the scroll pane which shows the composition of a portfolio to the user for viewing.
   *
   * @param dialog the dialog in which we want to access the scroll pane
   * @return the JScrollPane instance of "Stocks Pane" pane
   */
  JScrollPane getStocksPane(JDialog dialog);

  /**
   * Find the table which shows the stocks to the user while they perform an action.
   *
   * @param stocksPane the scroll pane in which we want to access table
   * @return the JTable instance of the "Stocks" table
   */
  JTable getStocksTable(JScrollPane stocksPane);

  /**
   * Find the submit button which completes the action that the user is performing.
   *
   * @param dialog the dialog in which we want to access the submit button
   * @return the JButton instance of the "Submit" button
   */
  JButton getSubmitButton(JDialog dialog);

  /**
   * Creates the dialog that allows a user to view all the flexible portfolios created so far.
   *
   * @param portfolioNames an array with all the names of the portfolios created so far
   * @return the dialog that the user interacts with
   */
  JDialog createPortfolioNamesDialog(String[] portfolioNames);

  /**
   * Creates the dialog that allows a user to load a portfolio from a file.
   *
   * @return the dialog that the user interacts with
   */
  JDialog loadFromFileDialog();

  /**
   * Finds the file chosen button which allows the user to choose a file to load as portfolio.
   *
   * @param dialog the dialog in which we want ot access the button
   * @return the JButton instance of the "File chosen" button
   */
  JButton getFileChosen(JDialog dialog);

  /**
   * Finds the text field where the user enters the commission amount while they are performing
   * an action.
   *
   * @param dialog in which we are trying to access the text field
   * @return the JTextField instance of the "Commission amount" text field
   */
  JTextField commissionAmount(JDialog dialog);

  /**
   * Creates the dialog that allows the user to view the composition of a portfolio.
   *
   * @param names the list of all the current portfolios created
   * @return the dialog that the user interacts with
   */
  JDialog viewComposition(List<String> names);

  /**
   * Computes the date from the panel that allows the user to enter date.
   *
   * @param datePanel the panel which the user interacts with
   * @return the formatted string after user input to allow controller to perform actions with it
   */
  String getDate(JPanel datePanel);

  /**
   * Creates a dialog that lets the user save a portfolio outside the program.
   *
   * @param names the names of the current portfolios created
   * @return the dialog that the use can interact with
   */
  JDialog saveFileDialog(List<String> names);

  /**
   * Creates a dialog that lets the user query the value of a portfolio.
   *
   * @param names the names of the current portfolios created
   * @return the dialog that the user can interact with
   */
  JDialog getValueDialog(List<String> names);

  /**
   * Creates a dialog that lets the user invest a certain amount of money into a portfolio.
   *
   * @param names the names of the current portfolios created
   * @return the dialog that the user can interact with
   */
  JDialog createInvestDialog(List<String> names);

  /**
   * Creates a dialog that lets the user sell a certain amount of stocks from a portfolio.
   * @return the dialog that the user can interact with
   */
  JDialog sellStockDialog(List<String> names);

  /**
   * Creates a dialog that lets the user view the cost basis of their portfolio.
   * @param names names of the current portfolios created
   * @return the dialog the user can interact with
   */
  JDialog costBasisDialog(List<String> names);

  /**
   * Creates a dialog that lets the user view the performance of their portfolio over a period of
   * time as a bar graph.
   * @return the dialog that the user can interact with
   */
  JDialog performanceDialog(List<String> names, Map<LocalDate, Double> performance);

  /**
   * Creates a dialog that lets the user perform dollar cost averaging on an existing portfolio.
   * @param names names of the current portfolios created
   * @return the dialog that the user can interact with
   */
  JDialog createDollarCostAveragingDialogForExistingPortfolios(List<String> names);

  /**
   * Creates a dialog that lets users create a new portfolio with dollar cost averaging strategy.
   * @return the dialog that the user interacts with
   */
  JDialog createPortfolioWithDCADialog();

  JDialog rebalancePortfolioDialog(List<String> names);
}
