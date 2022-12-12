package stocks;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import stocks.controller.PortfolioController;
import stocks.controller.PortfolioControllerImpl;
import stocks.model.PortfolioModel;
import stocks.model.PortfolioModelImpl;
import stocks.portfolio.Portfolio;
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class used to test the controller.
 */
public class PortfolioControllerImplTest {

  static class MockModel implements PortfolioModel {
    public StringBuilder log;
    public List<Portfolio> portfolioList;
    public List<String> nameList;

    /**
     * MockModel constructor class that sets the loo values.
     *
     * @param log passed log.
     */
    public MockModel(StringBuilder log) {
      this.log = log;
      portfolioList = new ArrayList<>();
      nameList = new ArrayList<>();
    }

    @Override
    public void createInflexiblePortfolioFromFile(String name, String filePath) {
      log.append("Name of file: ").append(name).append(" path given: ").append(filePath);
    }

    @Override
    public String getValueOfPortfolio(String name, String date) throws RuntimeException {
      log.append("Finding value for ").append(name).append(" for date ").append(date);
      return "Value found";
    }

    @Override
    public Portfolio getPortfolio(String name) throws RuntimeException {
      log.append("Finding portfolio of name ").append(name);
      return null;
    }

    @Override
    public List<Portfolio> getPortfolioList() {
      return portfolioList;
    }

    @Override
    public List<String> getPortfolioNames() {
      return nameList;
    }

    @Override
    public void createInflexiblePortfolioManually(String name, Map<String, String> stocks) {
      log.append("Creating portfolio manually with name ").append(name);
    }

    @Override
    public void savePortfolioToFile(String name) {
      log.append("Portfolio to be saved that has name ").append(name);
    }

    @Override
    public void createFlexiblePortfolioFromFile(String name,
                                                String filePath,
                                                String dateOfCreation, Double commission) {
      log.append("Flexible portfolio name of file: ").append(name).append(" path given: ")
              .append(filePath);
    }

    @Override
    public void createFlexiblePortfolioManually(String name,
                                                Map<String, Map<String, String>> stocks) {
      log.append("Creating flexible portfolio manually with name").append(name);
    }

    @Override
    public void buyStock(String name, Integer shares, String ticker,
                         String date, Double commissionFee) {
      log.append("Buying stock");
    }

    @Override
    public void sellStock(String name, Integer shares, String ticker,
                          String date, Double commissionFee) {
      log.append("Selling stock");
    }

    @Override
    public String getCostBasis(String name, String date) {
      log.append("Getting cost basis");
      return "";
    }

    @Override
    public Map<LocalDate, Double> performanceOverTime(String name,
                                                      String timeStamp, String startDate,
                                                      String endDate) throws ParseException {
      log.append("Getting performance over time");
      return new HashMap<>();
    }

    @Override
    public Map<String, String> getComposition(String name, String date) {
      log.append("Getting composition");
      return new HashMap<>();
    }

    @Override
    public void investStocks(String name, Double totalInvestment,
                             Map<String, Double> map, String date) {
      log.append("Investing stocks");
    }

    @Override
    public void dollarCostAveraging(String name, Integer frequency,
                                    String startDate, String endDate, double totalInvestment,
                                    Map<String, Double> stocksPercent) {
      log.append("Performing dollar cost averaging");

    }

    @Override
    public void rebalancePortfolio(String name, Map<String, Double> stockWeights, String date)
        throws IllegalArgumentException {
      log.append("Rebalancing a portfolio");
    }
  }

  PortfolioModel model;
  PortfolioView view;
  ByteArrayOutputStream outStream;

  @Before
  public void setUp() {
    outStream = new ByteArrayOutputStream();
    view = new PortfolioViewImpl(new PrintStream(outStream));
    model = new PortfolioModelImpl();
  }

  @Test
  public void testInvalidMenuInput() {
    ByteArrayInputStream inStream = new ByteArrayInputStream("F".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Invalid option. Please try again\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    assertEquals(expected, outStream.toString());
  }

  @Test
  public void testCreatePortfolioFromFile() {
    ByteArrayInputStream inStream = new ByteArrayInputStream(("1\nTest " +
            "\ntest/resources/portfolio.csv")
            .getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Enter path for portfolio: " +
            "Portfolio with name Test  created.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    assertEquals(expected, outStream.toString());
  }

  @Test
  public void testCreatePortfolioManually() {
    ByteArrayInputStream inStream = new ByteArrayInputStream("2\nTest\n1\nGOOGL\n200".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Enter number of stocks in this portfolio: " +
            "Enter ticker of company to buy shares of: " +
            "Enter number of shares of company to buy: Portfolio with name Test created.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    assertEquals(expected, outStream.toString());
  }

  @Test
  public void testCheckPortfolioValueWhenPortfolioDoesNotExist() {
    ByteArrayInputStream inStream = new ByteArrayInputStream("3\nTest".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Portfolio of name Test does not exist.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    assertEquals(expected, outStream.toString());
  }

  @Test
  public void testCheckPortfolioCompositionWhenPortfolioDoesNotExist() {
    ByteArrayInputStream inStream = new ByteArrayInputStream("4\nTest".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Portfolio of name Test does not exist.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    assertEquals(expected, outStream.toString());
  }

  @Test
  public void testSaveFileWhenPortfolioDoesNotExist() {
    ByteArrayInputStream inStream = new ByteArrayInputStream("5\nTest".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Portfolio with given name does not exist\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    assertEquals(expected, outStream.toString());
  }

  @Test
  public void testPrintAllPortfoliosWhenNoPortfoliosExist() {
    ByteArrayInputStream inStream = new ByteArrayInputStream("6".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\nMenu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: No portfolios have been created yet.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    assertEquals(expected, outStream.toString());
  }

  @Test
  public void testCreatePortfolioManuallyWithError() {
    ByteArrayInputStream inStream = new ByteArrayInputStream("2\nTest\nasd".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Enter number of stocks in this portfolio: " +
            "Please enter a valid integer.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    assertEquals(expected, outStream.toString());
  }

  @Test
  public void testCreatePortfolioWithErrorInNumberOfShares() {
    ByteArrayInputStream inStream = new ByteArrayInputStream("2\nTest\n1\nGOOGL\nASD".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Enter number of stocks in this portfolio: " +
            "Enter ticker of company to buy shares of: Enter number of shares of company to buy: " +
            "Number of shares must be an integer.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    assertEquals(expected, outStream.toString());
  }

  @Test
  public void testCreatePortfolioFromFileWithInvalidPath() {
    ByteArrayInputStream inStream = new ByteArrayInputStream(("1\nTest " +
            "\n/Users/arinjay/Desktop/portfolio.json")
            .getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Enter path for portfolio: File not found at path.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    assertEquals(expected, outStream.toString());
    assertEquals(0, model.getPortfolioList().size());
  }

  @Test
  public void testCreatePortfolioFromInvalidFileFormat() {
    ByteArrayInputStream inStream = new ByteArrayInputStream(("1\nTest " +
            "\n/Users/arinjay/Desktop/invalidPortfolio.csv")
            .getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Enter path for portfolio: File not found at path.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    assertEquals(expected, outStream.toString());
    assertEquals(0, model.getPortfolioList().size());
  }

  @Test
  public void testCreatePortfolioWithNameTaken() {
    ByteArrayInputStream inStream = new ByteArrayInputStream(("1\nTest" +
            "\ntest/resources/portfolio.csv\n2\nTest\n2")
            .getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Enter path for portfolio: " +
            "Portfolio with name Test created.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Portfolio with this name exists already. " +
            "PLease try again with a different name.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: ";
    assertEquals(expected, outStream.toString());
  }

  @Test
  public void testPortfolioComposition() {
    ByteArrayInputStream inStream = new ByteArrayInputStream(("1\nTest" +
            "\ntest/resources/portfolio.csv\n4\nTest")
            .getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Enter path for portfolio: " +
            "Portfolio with name Test created.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: MSFT 200\n" +
            "GOOGL 400\n" +
            "AAPL 140\n" +
            "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    assertEquals(expected, outStream.toString());
  }

  @Test
  public void testPortfolioValue() {
    ByteArrayInputStream inStream = new ByteArrayInputStream(("1\nTest" +
            "\ntest/resources/portfolio.csv\n3\nTest\n2020\n3\n13")
            .getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Enter path for portfolio: " +
            "Portfolio with name Test created.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Enter the year to check value of portfolio for: " +
            "Enter the month to check value of portfolio for (1-January...12-December): " +
            "Enter the day to check value of portfolio for: Checking value of stock on 2020-03-13" +
            "Value of portfolio is: 556389.8\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    assertEquals(expected, outStream.toString());
  }

  @Test
  public void testPortfolioValueInvalidDate() {
    ByteArrayInputStream inStream = new ByteArrayInputStream(("1\nTest" +
            "\ntest/resources/portfolio.csv\n3\nTest\n202o\na\n20")
            .getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Enter path for portfolio: " +
            "Portfolio with name Test created.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Enter the year to check value of portfolio for: " +
            "Enter the month to check value of portfolio for (1-January...12-December): " +
            "Enter the day to check value of portfolio for: Invalid date entered. " +
            "Please try again.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    assertEquals(expected, outStream.toString());
  }

  @Test
  public void testSaveToFile() {
    ByteArrayInputStream inStream = new ByteArrayInputStream(("1\nTest" +
            "\ntest/resources/portfolio.csv\n5\nTest")
            .getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\nMenu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Enter path for portfolio: " +
            "Portfolio with name Test created.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: File saved with name Test.csv\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    assertEquals(expected, outStream.toString());
    String currentPath = Paths.get("").toAbsolutePath().toString();
    File savedFile = new File(currentPath, "Test.csv");
    assertTrue(savedFile.exists());
  }

  @Test
  public void testPrintAllPortfoliosWhenNoPortfolioExists() {
    ByteArrayInputStream inStream = new ByteArrayInputStream(("6")
            .getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: No portfolios have been created yet.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    assertEquals(expected, outStream.toString());
  }

  @Test
  public void printAllPortfolios() {
    ByteArrayInputStream inStream = new ByteArrayInputStream(("1\nTest" +
            "\ntest/resources/portfolio.csv\n6")
            .getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, model);
    try {
      controller.start();
    } catch (NoSuchElementException | ParseException ignored) {

    }
    String expected = "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: \n" +
            "Enter name for portfolio: Enter path for portfolio: " +
            "Portfolio with name Test created.\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: Test\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    assertEquals(expected, outStream.toString());
  }

  @Test
  public void mockModelTestLoadFromFile() throws ParseException {
    StringBuilder log = new StringBuilder();
    PortfolioModel mockModel = new MockModel(log);
    ByteArrayInputStream inStream = new ByteArrayInputStream(("1\nTest" +
            "\ntest/resources/portfolio.csv\nQ")
            .getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, mockModel);
    controller.start();
    assertEquals("Name of file: Test path given: " +
            "test/resources/portfolio.csv", log.toString());
    assertEquals("\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: Enter name for portfolio: \n" +
            "Enter path for portfolio: \n" +
            "Portfolio with name Test created.\n" +
            "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ", outStream.toString());
  }

  @Test
  public void mockModelCreateManually() throws ParseException {
    StringBuilder log = new StringBuilder();
    PortfolioModel mockModel = new MockModel(log);
    ByteArrayInputStream inStream = new ByteArrayInputStream(("2\nTest\n1\nGOOGL\n200\nQ")
            .getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, mockModel);
    controller.start();
    assertEquals("Creating portfolio manually with name Test", log.toString());
    assertEquals("\n" +
            "Menu: \n" +
            "1 Load portfolio from a file\n" +
            "2 Create portfolio manually\n" +
            "3 Check value of a portfolio\n" +
            "4 View composition of a portfolio\n" +
            "5 Save portfolio to a file\n" +
            "6 View all portfolio names\n" +
            "7 Create flexible portfolio manually\n" +
            "8 Load flexible portfolio from file\n" +
            "9 Buy stock for a flexible portfolio\n" +
            "10 Sell stock for a flexible portfolio\n" +
            "11 View cost basis of a flexible portfolio\n" +
            "12 View performance of portfolio over time\n" +
            "Q Exit the program\n" +
            "Enter your choice to perform action: Enter name for portfolio: \n" +
            "Enter path for portfolio: \n" +
            "Enter the year of first purchase in the portfolio: \n" +
            "Enter the month of first purchase (1-January...12-December): \n" +
            "Enter the day of first purchase in portfolio : \n" +
            "Date entered is: 2020-10-21\n" +
            "Enter commission fee paid on transactions: \n" +
            "Portfolio with name Test created.\n" +
            "\n" +
            "Menu: \n" +
            "1 Load portfolio from a file\n" +
            "2 Create portfolio manually\n" +
            "3 Check value of a portfolio\n" +
            "4 View composition of a portfolio\n" +
            "5 Save portfolio to a file\n" +
            "6 View all portfolio names\n" +
            "7 Create flexible portfolio manually\n" +
            "8 Load flexible portfolio from file\n" +
            "9 Buy stock for a flexible portfolio\n" +
            "10 Sell stock for a flexible portfolio\n" +
            "11 View cost basis of a flexible portfolio\n" +
            "12 View performance of portfolio over time\n" +
            "Q Exit the program\n" +
            "Enter your choice to perform action: Please enter a valid command.\n" +
            "\n" +
            "Menu: \n" +
            "1 Load portfolio from a file\n" +
            "2 Create portfolio manually\n" +
            "3 Check value of a portfolio\n" +
            "4 View composition of a portfolio\n" +
            "5 Save portfolio to a file\n" +
            "6 View all portfolio names\n" +
            "7 Create flexible portfolio manually\n" +
            "8 Load flexible portfolio from file\n" +
            "9 Buy stock for a flexible portfolio\n" +
            "10 Sell stock for a flexible portfolio\n" +
            "11 View cost basis of a flexible portfolio\n" +
            "12 View performance of portfolio over time\n" +
            "Q Exit the program\n" +
            "Enter your choice to perform action:", outStream.toString());
  }

  @Test
  public void mockModelCreateFlexibleFromFile() throws ParseException {
    StringBuilder log = new StringBuilder();
    PortfolioModel mockModel = new MockModel(log);
    ByteArrayInputStream inStream = new ByteArrayInputStream(("8\nTest" +
            "\n/test/resources/flexible.portfolio\n2020\n10\n21\n10\nQ")
            .getBytes());
    PortfolioController controller = new PortfolioControllerImpl(inStream, view, mockModel);
    controller.start();
    assertEquals("Flexible portfolio name of file: Test path given: " +
            "/test/resources/flexible.portfolio", log.toString());
    assertEquals("\n" +
            "Menu: \n" +
            "1 Load portfolio from a file\n" +
            "2 Create portfolio manually\n" +
            "3 Check value of a portfolio\n" +
            "4 View composition of a portfolio\n" +
            "5 Save portfolio to a file\n" +
            "6 View all portfolio names\n" +
            "7 Create flexible portfolio manually\n" +
            "8 Load flexible portfolio from file\n" +
            "9 Buy stock for a flexible portfolio\n" +
            "10 Sell stock for a flexible portfolio\n" +
            "11 View cost basis of a flexible portfolio\n" +
            "12 View performance of portfolio over time\n" +
            "Q Exit the program\n" +
            "Enter your choice to perform action: Enter name for portfolio: \n" +
            "Enter path for portfolio: \n" +
            "Enter the year of first purchase in the portfolio: \n" +
            "Enter the month of first purchase (1-January...12-December): \n" +
            "Enter the day of first purchase in portfolio : \n" +
            "Date entered is: 2020-10-21\n" +
            "Enter commission fee paid on transactions: \n" +
            "Portfolio with name Test created.\n" +
            "\n" +
            "Menu: \n" +
            "1 Load portfolio from a file\n" +
            "2 Create portfolio manually\n" +
            "3 Check value of a portfolio\n" +
            "4 View composition of a portfolio\n" +
            "5 Save portfolio to a file\n" +
            "6 View all portfolio names\n" +
            "7 Create flexible portfolio manually\n" +
            "8 Load flexible portfolio from file\n" +
            "9 Buy stock for a flexible portfolio\n" +
            "10 Sell stock for a flexible portfolio\n" +
            "11 View cost basis of a flexible portfolio\n" +
            "12 View performance of portfolio over time\n" +
            "Q Exit the program\n" +
            "Enter your choice to perform action: Please enter a valid command.\n" +
            "\n" +
            "Menu: \n" +
            "1 Load portfolio from a file\n" +
            "2 Create portfolio manually\n" +
            "3 Check value of a portfolio\n" +
            "4 View composition of a portfolio\n" +
            "5 Save portfolio to a file\n" +
            "6 View all portfolio names\n" +
            "7 Create flexible portfolio manually\n" +
            "8 Load flexible portfolio from file\n" +
            "9 Buy stock for a flexible portfolio\n" +
            "10 Sell stock for a flexible portfolio\n" +
            "11 View cost basis of a flexible portfolio\n" +
            "12 View performance of portfolio over time\n" +
            "Q Exit the program\n" +
            "Enter your choice to perform action: ", outStream.toString());
  }

}