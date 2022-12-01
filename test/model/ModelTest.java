package model;

import static constants.Constants.INPUT_NULL_OR_EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import constants.Constants;
import enums.Operations;
import enums.StrategyTypes;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
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
              MOCK_SERVICE_GET_STOCK_ON_DATE_MESSAGE,
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
              MOCK_SERVICE_GET_STOCK_ON_DATE_MESSAGE,
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
              MOCK_SERVICE_GET_STOCK_ON_DATE_MESSAGE,
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
    // Stock data on Nov 17
    stockPairs.add(new Pair<>("AAPL", Double.parseDouble("150.72")));
    stockPairs.add(new Pair<>("AMZN", Double.parseDouble("94.85")));
    stockPairs.add(new Pair<>("MSFT", Double.parseDouble("241.68")));
    stockPairs.add(new Pair<>("GOOG", Double.parseDouble("98.50")));
    stockPairs.add(new Pair<>("VZ", Double.parseDouble("38.16")));

    try {
      model.createPortfolio(FOUND_A_MATCH, stockPairs);

      List<String> expected = new ArrayList<>(
          Arrays.asList(MOCK_REPO_CREATE_MESSAGE,
              MOCK_SERVICE_GET_STOCK_ON_DATE_MESSAGE,
              MOCK_REPO_UPDATE_MESSAGE,
              MOCK_SERVICE_GET_STOCK_ON_DATE_MESSAGE,
              MOCK_REPO_UPDATE_MESSAGE,
              MOCK_SERVICE_GET_STOCK_ON_DATE_MESSAGE,
              MOCK_REPO_UPDATE_MESSAGE,
              MOCK_SERVICE_GET_STOCK_ON_DATE_MESSAGE,
              MOCK_REPO_UPDATE_MESSAGE,
              MOCK_SERVICE_GET_STOCK_ON_DATE_MESSAGE,
              MOCK_REPO_UPDATE_MESSAGE
          ));

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

    stockPairs.add(new Pair<>("AAPL", Double.parseDouble("100")));
    try {
      Pair<Portfolio, Double> pair = model.getPortfolioValueOnDate(FOUND_A_MATCH,
          DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT));
      assertEquals(12747.89, pair.getValue(), 0.01);

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
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(Constants.DATE_INVALID, illegalArgumentException.getMessage());
    }
  }

  static class MockService implements IStockService {

    private final List<String> log;

    private final List<String> nonTradingDays;

    public MockService(List<String> log) {
      this.log = log;
      this.nonTradingDays = List.of(
          "2022-01-17",
          "2022-02-21",
          "2022-04-15",
          "2022-05-30",
          "2022-06-20",
          "2022-07-04",
          "2022-09-05",
          "2022-11-24",
          "2022-12-26"
      );
    }

    @Override
    public List<Stock> getHistoricalStockData(String symbol)
        throws IllegalArgumentException, IOException {
      return null;
    }

    @Override
    public Stock getStockOnDate(String symbol, String date) throws IllegalArgumentException {
      this.log.add(MOCK_SERVICE_GET_STOCK_ON_DATE_MESSAGE);
      return Stock
          .StockBuilder
          .create()
          .setSymbol("AAPL")
          .setQuantity(234)
          .setClose(Double.parseDouble("150.72"));
    }

    @Override
    public boolean isTradingDay(String date) throws IllegalArgumentException, IOException {
      LocalDate localDate = LocalDate.parse(date);
      int day = localDate.get(ChronoField.DAY_OF_WEEK);
      return !nonTradingDays.contains(date) && (day != 6 && day != 7);
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
          List.of(
              Stock
                  .StockBuilder
                  .create()
                  .setSymbol(FOUND_A_MATCH.toUpperCase())
                  .setOperation(Operations.BUY)
                  .setQuantity(9)
                  .setClose(234)
                  .setCommission(20)
                  .setDate("2022-10-10"),
              Stock
                  .StockBuilder
                  .create()
                  .setSymbol("VZ")
                  .setOperation(Operations.SELL)
                  .setQuantity(7)
                  .setCommission(10)
                  .setDate("2022-10-17"),
              Stock
                  .StockBuilder
                  .create()
                  .setSymbol("AMZN")
                  .setOperation(Operations.BUY)
                  .setQuantity(10)
                  .setCommission(30)
                  .setDate("2022-01-01"),
              Stock
                  .StockBuilder
                  .create()
                  .setSymbol("AMZN")
                  .setOperation(Operations.SELL)
                  .setQuantity(8)
                  .setCommission(90)
                  .setDate("2022-01-10"),
              Stock
                  .StockBuilder
                  .create()
                  .setSymbol("AAPL")
                  .setQuantity(3.31)
                  .setDate("2022-11-01")
                  .setOperation(Operations.BUY)
                  .setCommission(10.00)
                  .setStrategyType(StrategyTypes.DOLLAR_COST_AVERAGING)
                  .setStrategyInvestment(1000)
                  .setStrategyEndDate("2022-11-20")
                  .setWeight(50)
                  .setStrategyName("asdad")
                  .setStrategyPeriod(5),
              Stock
                  .StockBuilder
                  .create()
                  .setSymbol("AMZN")
                  .setQuantity(5.16)
                  .setDate("2022-11-01")
                  .setOperation(Operations.BUY)
                  .setCommission(10.00)
                  .setStrategyType(StrategyTypes.DOLLAR_COST_AVERAGING)
                  .setStrategyInvestment(1000)
                  .setStrategyEndDate("2022-11-20")
                  .setWeight(50)
                  .setStrategyName("asdad")
                  .setStrategyPeriod(5),
              Stock
                  .StockBuilder
                  .create()
                  .setSymbol("AAPL")
                  .setQuantity(3.58)
                  .setDate("2022-11-08")
                  .setOperation(Operations.BUY)
                  .setCommission(10.00)
                  .setStrategyType(StrategyTypes.DOLLAR_COST_AVERAGING)
                  .setStrategyInvestment(1000)
                  .setStrategyEndDate("2022-11-20")
                  .setWeight(50)
                  .setStrategyName("asdad")
                  .setStrategyPeriod(5),
              Stock
                  .StockBuilder
                  .create()
                  .setSymbol("AMZN")
                  .setQuantity(5.55)
                  .setDate("2022-11-08")
                  .setOperation(Operations.BUY)
                  .setCommission(10.00)
                  .setStrategyType(StrategyTypes.DOLLAR_COST_AVERAGING)
                  .setStrategyInvestment(1000)
                  .setStrategyEndDate("2022-11-20")
                  .setWeight(50)
                  .setStrategyName("asdad")
                  .setStrategyPeriod(5),
              Stock
                  .StockBuilder
                  .create()
                  .setSymbol("AAPL")
                  .setQuantity(3.33)
                  .setDate("2022-11-15")
                  .setOperation(Operations.BUY)
                  .setCommission(10.00)
                  .setStrategyType(StrategyTypes.DOLLAR_COST_AVERAGING)
                  .setStrategyInvestment(1000)
                  .setStrategyEndDate("2022-11-20")
                  .setWeight(50)
                  .setStrategyName("asdad")
                  .setStrategyPeriod(5),
              Stock
                  .StockBuilder
                  .create()
                  .setSymbol("AMZN")
                  .setQuantity(5.05)
                  .setDate("2022-11-15")
                  .setOperation(Operations.BUY)
                  .setCommission(10.00)
                  .setStrategyType(StrategyTypes.DOLLAR_COST_AVERAGING)
                  .setStrategyInvestment(1000)
                  .setStrategyEndDate("2022-11-20")
                  .setWeight(50)
                  .setStrategyName("asdad")
                  .setStrategyPeriod(5),
              Stock
                  .StockBuilder
                  .create()
                  .setSymbol("AAPL")
                  .setQuantity(3.31)
                  .setDate("2022-11-01")
                  .setOperation(Operations.BUY)
                  .setCommission(10.00)
                  .setStrategyType(StrategyTypes.FIXED_AMOUNT)
                  .setStrategyInvestment(1000)
                  .setWeight(50)
                  .setStrategyName("wewe"),
              Stock
                  .StockBuilder
                  .create()
                  .setSymbol("AMZN")
                  .setQuantity(5.16)
                  .setDate("2022-11-01")
                  .setOperation(Operations.BUY)
                  .setCommission(10.00)
                  .setStrategyType(StrategyTypes.FIXED_AMOUNT)
                  .setStrategyInvestment(1000)
                  .setWeight(50)
                  .setStrategyName("wewe"),
              Stock
                  .StockBuilder
                  .create()
                  .setSymbol("AAPL")
                  .setQuantity(3.58)
                  .setDate("2022-11-08")
                  .setOperation(Operations.BUY)
                  .setCommission(10.00)
                  .setStrategyType(StrategyTypes.FIXED_AMOUNT)
                  .setStrategyInvestment(1000)
                  .setWeight(50)
                  .setStrategyName("wewe"),
              Stock
                  .StockBuilder
                  .create()
                  .setSymbol("AMZN")
                  .setQuantity(5.55)
                  .setDate("2022-11-08")
                  .setOperation(Operations.BUY)
                  .setCommission(10.00)
                  .setStrategyType(StrategyTypes.FIXED_AMOUNT)
                  .setStrategyInvestment(1000)
                  .setWeight(50)
                  .setStrategyName("wewe")
          )
      );
      //  8 sell
      Stock s = Stock
          .StockBuilder
          .create()
          .setSymbol("VZ")
          .setOperation(Operations.SELL)
          .setQuantity(7)
          .setDate("2022-10-17");
      stocks.add(s);
      portfolio.addStocks(stocks);
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
