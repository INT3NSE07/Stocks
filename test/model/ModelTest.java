package model;

import static constants.Constants.INPUT_NULL_OR_EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import constants.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import org.junit.Test;
import repository.IRepository;
import service.IStockService;
import utilities.DateUtils;
import utilities.Pair;

/**
 * A JUnit test class for the {@link IPortfolioModel}s class.
 */
public class ModelTest {

  private static final String MOCK_SERVICE_GET_STOCK_MESSAGE =
      "MockService getStock() called";
  private static final String MOCK_SERVICE_GET_STOCK_ON_DATE_MESSAGE =
      "MockService getStockOnDate() called";
  private static final String MOCK_SERVICE_IS_STOCK_SYMBOL_VALID_MESSAGE =
      "MockService isStockSymbolValid() called";

  private static final String MOCK_REPO_CREATE_MESSAGE = "MockRepository create() called";
  private static final String MOCK_REPO_READ_MESSAGE = "MockRepository read() called";
  private static final String MOCK_REPO_UPDATE_MESSAGE = "MockRepository update() called";


  private static final String FOUND_A_MATCH = "ValidSymbol";

  @Test
  public void testCreatePortfolioNameNull() {
    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);
    try {
      List<Pair<String, Double>> stockPairs = new ArrayList<>();
      stockPairs.add(new Pair<>(FOUND_A_MATCH, Double.parseDouble("123")));
      model.createPortfolio(null, stockPairs);
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(INPUT_NULL_OR_EMPTY, illegalArgumentException.getMessage());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testCreatePortfolioNameEmpty() {
    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);
    try {
      List<Pair<String, Double>> stockPairs = new ArrayList<>();
      stockPairs.add(new Pair<>(FOUND_A_MATCH, Double.parseDouble("123")));

      model.createPortfolio("", stockPairs);
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(INPUT_NULL_OR_EMPTY, illegalArgumentException.getMessage());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
    public void testCreatePortfolioCreateCallingCreateRepository() {

    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>(FOUND_A_MATCH, Double.parseDouble("123")));

    try {
      model.createPortfolio(FOUND_A_MATCH, stockPairs);

      List<String> expected = new ArrayList<>(
          Arrays.asList(MOCK_REPO_CREATE_MESSAGE,
              MOCK_SERVICE_GET_STOCK_MESSAGE,
              MOCK_SERVICE_GET_STOCK_ON_DATE_MESSAGE,
              MOCK_SERVICE_IS_STOCK_SYMBOL_VALID_MESSAGE,
              MOCK_REPO_UPDATE_MESSAGE));

      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));
    } catch (IOException ioException) {
      fail(ioException.getMessage());
    }

  }

  @Test
  public void testCreatePortfolioAddCallingUpdateRepository() {

    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>(FOUND_A_MATCH, Double.parseDouble("123")));

    try {
      model.createPortfolio(FOUND_A_MATCH, stockPairs);
      List<String> expected = new ArrayList<>(
          Arrays.asList(MOCK_REPO_CREATE_MESSAGE,
              MOCK_SERVICE_GET_STOCK_MESSAGE,
              MOCK_SERVICE_GET_STOCK_ON_DATE_MESSAGE,
              MOCK_SERVICE_IS_STOCK_SYMBOL_VALID_MESSAGE,
              MOCK_REPO_UPDATE_MESSAGE));

      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));

    } catch (IOException ioException) {
      fail(ioException.getMessage());
    }
  }

  @Test
  public void testValidateSymbolNull() {
    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>(null, Double.parseDouble("123")));

    try {
      model.createPortfolio("notValidSymbol", stockPairs);
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(INPUT_NULL_OR_EMPTY,
          illegalArgumentException.getMessage());
    } catch (IOException ioException) {
      fail(ioException.getMessage());
    }
  }

  @Test
  public void testValidateSymbolEmpty() {
    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>("", Double.parseDouble("123")));

    try {
      model.createPortfolio(FOUND_A_MATCH, stockPairs);
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(INPUT_NULL_OR_EMPTY,
          illegalArgumentException.getMessage());
    } catch (IOException ioException) {
      fail(ioException.getMessage());
    }
  }

  @Test
  public void testValidateSymbolNotValid() {

    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>("sas", Double.parseDouble("123")));

    try {
      model.createPortfolio("sa", stockPairs);
      List<String> expected = new ArrayList<>(
          Arrays.asList(MOCK_REPO_CREATE_MESSAGE,
              MOCK_SERVICE_GET_STOCK_MESSAGE,
              MOCK_REPO_UPDATE_MESSAGE));
      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));
    } catch (IllegalArgumentException illegalArgumentException) {
      String date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
      assertEquals(String.format(
              "Error occurred while fetching stock data for symbol: sas on date: " + date),
          illegalArgumentException.getMessage());
    } catch (IOException ioException) {
      fail(ioException.getMessage());
    }
  }

  @Test
  public void testValidateQuantity0() {
    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>("sas", Double.parseDouble("0")));

    try {
      model.createPortfolio("notValidSymbol", stockPairs);

    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(Constants.QUANTITY_NON_NEGATIVE_AND_ZERO,
          illegalArgumentException.getMessage());
    } catch (IOException ioException) {
      fail(ioException.getMessage());
    }
  }

  @Test
  public void testValidateQuantityNegative() {

    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>("sas", Double.parseDouble("-1")));

    try {
      model.createPortfolio("notValidSymbol", stockPairs);

    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(Constants.QUANTITY_NON_NEGATIVE_AND_ZERO,
          illegalArgumentException.getMessage());
    } catch (IOException ioException) {
      fail(ioException.getMessage());
    }
  }

  @Test
  public void testValidateSymbolValidAndQuantity() {

    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>(FOUND_A_MATCH, Double.parseDouble("123")));

    try {
      model.createPortfolio(FOUND_A_MATCH, stockPairs);

      List<String> expected = new ArrayList<>(
          Arrays.asList(MOCK_REPO_CREATE_MESSAGE,
              MOCK_SERVICE_GET_STOCK_MESSAGE,
              MOCK_SERVICE_GET_STOCK_ON_DATE_MESSAGE,
              MOCK_SERVICE_IS_STOCK_SYMBOL_VALID_MESSAGE,
              MOCK_REPO_UPDATE_MESSAGE));
      assertEquals(Arrays.toString(mockLog.toArray()), Arrays.toString(expected.toArray()));

    } catch (IOException ioException) {
      fail(ioException.getMessage());
    }
  }

  @Test
  public void testReadPortfolioWithNullName() {

    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>(FOUND_A_MATCH, Double.parseDouble("123")));

    try {
      model.readPortfolio(null);
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(INPUT_NULL_OR_EMPTY, illegalArgumentException.getMessage());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadPortfolioWithEmptyName() {

    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>(FOUND_A_MATCH, Double.parseDouble("123")));

    try {
      model.readPortfolio("");
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(INPUT_NULL_OR_EMPTY, illegalArgumentException.getMessage());
    } catch (IOException e) {
      fail(e.getMessage());
    }

  }

  @Test
  public void testReadPortfolioNameNotfound() {

    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>(FOUND_A_MATCH, Double.parseDouble("123")));

    try {
      model.readPortfolio("sas");

    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(Constants.PORTFOLIO_DOES_NOT_EXIST, illegalArgumentException.getMessage());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadPortfolioName() {

    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>(FOUND_A_MATCH, Double.parseDouble("123")));

    try {
      // return static portfolio value from mocked repository read.
      Portfolio portfolio = model.readPortfolio(FOUND_A_MATCH);

      // assert sequence
      assertEquals("[" + MOCK_REPO_READ_MESSAGE + "]", Arrays.toString(mockLog.toArray()));

      // assert values
      assertEquals(portfolio.getName(), FOUND_A_MATCH);

    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetPortfolioValueOnDateWithNameNull() {
    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>(FOUND_A_MATCH, Double.parseDouble("123")));

    try {
      Pair<Portfolio, Double> pair = model.getPortfolioValueOnDate(null,
          DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT));

    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(INPUT_NULL_OR_EMPTY, illegalArgumentException.getMessage());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetPortfolioValueOnDateWithNameEmpty() {

    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>(FOUND_A_MATCH, Double.parseDouble("123")));

    try {
      Pair<Portfolio, Double> pair = model.getPortfolioValueOnDate("",
          DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT));

    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(INPUT_NULL_OR_EMPTY, illegalArgumentException.getMessage());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  // Mocked service and repository to provide with static data for unit testing purpose.
  @Test
  public void testGetPortfolioValueOnDate() {
    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>(FOUND_A_MATCH, Double.parseDouble("123")));

    try {
      Pair<Portfolio, Double> pair = model.getPortfolioValueOnDate(FOUND_A_MATCH,
          DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT));
      assertEquals(54756.0, pair.getValue(), 0);

    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetPortfolioValueOnDateWithDateNull() {

    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>(FOUND_A_MATCH, Double.parseDouble("123")));

    try {
      // return static portfolio value from mocked repository read.
      Portfolio portfolio = model.readPortfolio(FOUND_A_MATCH);
      Double value = model.getPortfolioValueOnDate(FOUND_A_MATCH, null).getValue();
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetPortfolioValueOnDateWithDateInvalid() {

    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService(mockLog);
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>(FOUND_A_MATCH, Double.parseDouble("123")));

    try {
      Portfolio portfolio = model.readPortfolio(FOUND_A_MATCH);
      Double value = model.getPortfolioValueOnDate(FOUND_A_MATCH, "2022/09/23").getValue();
    } catch (IOException ioException) {
      fail(ioException.getMessage());
    }
    catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(Constants.DATE_INVALID,illegalArgumentException.getMessage());
    }
  }

  static class MockService implements IStockService {

    private final List<String> log;

    public MockService(List<String> log) {
      this.log = log;
    }

    @Override
    public Stock getStock(String symbol, double quantity) {
      this.log.add(MOCK_SERVICE_GET_STOCK_MESSAGE);
      String date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
      return getStockOnDate(symbol, date);
    }

    @Override
    public Stock getStockOnDate(String symbol, String date) throws IllegalArgumentException {
      this.log.add(MOCK_SERVICE_GET_STOCK_ON_DATE_MESSAGE);
      if (!isStockSymbolValid(symbol.toUpperCase())) {
        throw new IllegalArgumentException(
            String.format("Error occurred while fetching stock data for symbol: %s on date: %s",
                symbol,
                date));
      }
      return Stock
          .StockBuilder
          .create()
          .setSymbol(FOUND_A_MATCH)
          .setQuantity(234)
          .setClose(234);
    }

    @Override
    public boolean isStockSymbolValid(String symbol) {
      this.log.add(MOCK_SERVICE_IS_STOCK_SYMBOL_VALID_MESSAGE);
      return symbol.equals(FOUND_A_MATCH.toUpperCase());
    }
  }

  static class MockRepository implements IRepository<Portfolio> {

    private final List<String> log;

    public MockRepository(List<String> log) {
      this.log = log;
    }

    @Override
    public Portfolio create(Portfolio item) {
      this.log.add(MOCK_REPO_CREATE_MESSAGE);
      return item;
    }

    @Override
    public Iterable<Portfolio> read(Predicate<Portfolio> predicate) {
      this.log.add(MOCK_REPO_READ_MESSAGE);
      Portfolio portfolio = new Portfolio();
      portfolio.setName(FOUND_A_MATCH);
      List<Stock> stocks = new ArrayList<>(
          Collections.singletonList(Stock
              .StockBuilder
              .create()
              .setSymbol(FOUND_A_MATCH)
              .setQuantity(234)
              .setClose(234)));
      portfolio.setStocks(stocks);
      List<Portfolio> portfolios = new ArrayList<>();
      if (predicate.test(portfolio)) {
        portfolios.add(portfolio);
      }
      if (portfolios.isEmpty()) {
        throw new IllegalArgumentException(Constants.PORTFOLIO_DOES_NOT_EXIST);
      }

      return portfolios;
    }

    @Override
    public Portfolio update(Portfolio item) {
      this.log.add(MOCK_REPO_UPDATE_MESSAGE);
      return item;
    }
  }


}
