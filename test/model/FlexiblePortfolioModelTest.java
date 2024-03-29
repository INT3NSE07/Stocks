package model;

import static org.junit.Assert.assertEquals;

import constants.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import utilities.Pair;

/**
 * A JUnit test class for the {@link IFlexiblePortfolioModel}s class.
 */
public class FlexiblePortfolioModelTest {

  private static final String MOCK_REPO_UPDATE_MESSAGE = "MockRepository update() called";
  private static final String FOUND_A_MATCH = "ValidSymbol";

  @Test
  public void testBuyStock() throws IOException {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);

    Pair<String, Double> stockPair = new Pair<>("VZ", Double.parseDouble("38.16"));

    model.buyStock("portfolioName", stockPair, "2022-10-10", Double.parseDouble("10"));

    List<String> expected = new ArrayList<>(
        List.of(
            MOCK_REPO_UPDATE_MESSAGE
        ));

    assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));

  }

  @Test
  public void testBuyStockNullOrInvalidPortfolioName() throws IOException {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);

    Pair<String, Double> stockPair = new Pair<>("VZ", Double.parseDouble("38.16"));

    try {
      model.buyStock("", stockPair, "2022-10-10", Double.parseDouble("10"));
    } catch (IllegalArgumentException illegalArgumentException) {

      assertEquals(Constants.INPUT_NULL_OR_EMPTY, illegalArgumentException.getMessage());
    }
  }

  @Test
  public void testBuyStockInvalidDate() throws IOException {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);

    Pair<String, Double> stockPair = new Pair<>("VZ", Double.parseDouble("38.16"));

    try {
      model.buyStock("portfolioName", stockPair, "09-10-2022", Double.parseDouble("10"));
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(Constants.DATE_INVALID, illegalArgumentException.getMessage());
    }

    try {
      model.buyStock("portfolioName", stockPair, "NotAValidFormat", Double.parseDouble("10"));
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(Constants.DATE_INVALID, illegalArgumentException.getMessage());
    }


  }

  @Test
  public void testBuyStockCommissionInvalid() throws IOException {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);

    Pair<String, Double> stockPair = new Pair<>("VZ", Double.parseDouble("38.7"));
    try {
      model.buyStock("portfolioName", stockPair, "2022-10-10", Double.parseDouble("-10"));
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(String.format(Constants.NON_NEGATIVE, "Commission"),
          illegalArgumentException.getMessage());
    }
  }

  @Test
  public void testSellStockInvalidStock() throws IOException {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);

    Pair<String, Double> stockPair = new Pair<>("VZ", Double.parseDouble("38.16"));

    try {
      model.sellStock(FOUND_A_MATCH, stockPair, "2022-10-10", Double.parseDouble("10"));
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals("The portfolio ValidSymbol does not contain 38.16 of VZ to "
          + "sell on date 2022-10-10.", illegalArgumentException.getMessage());
    }
  }

  @Test
  public void testSellStockInvalidOrNullPortfolioName() {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);

    Pair<String, Double> stockPair = new Pair<>("VZ", Double.parseDouble("38.16"));

    try {
      model.sellStock("", stockPair, "2022-10-10", Double.parseDouble("10"));
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(Constants.INPUT_NULL_OR_EMPTY, illegalArgumentException.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Test
  public void testSellStockInvalidDate() {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);

    Pair<String, Double> stockPair = new Pair<>("VZ", Double.parseDouble("38.16"));

    try {
      model.sellStock("portfolioName", stockPair, "NotValidFormat", Double.parseDouble("10"));
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(Constants.DATE_INVALID, illegalArgumentException.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      model.sellStock("portfolioName", stockPair, "20-2022-10", Double.parseDouble("10"));
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(Constants.DATE_INVALID, illegalArgumentException.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testSellStockInvalidQuantity() {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);

    Pair<String, Double> stockPair = new Pair<>(FOUND_A_MATCH.toUpperCase(),
        Double.parseDouble("-10"));

    try {
      model.sellStock(FOUND_A_MATCH, stockPair, "2022-10-19", Double.parseDouble("10"));
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals("Quantity of a stock cannot be negative or zero.",
          illegalArgumentException.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testSellStockQuantityCheck() {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);

    Pair<String, Double> stockPair = new Pair<>(FOUND_A_MATCH.toUpperCase(),
        Double.parseDouble("10"));

    try {
      model.sellStock(FOUND_A_MATCH, stockPair, "2022-10-15", Double.parseDouble("10"));
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals("The portfolio ValidSymbol does not contain 10.00 of "
          + "VALIDSYMBOL to sell on date 2022-10-15.", illegalArgumentException.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testSellStockChronology() {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);

    Pair<String, Double> stockPair = new Pair<>("AMZN",
        Double.parseDouble("3"));

    try {
      model.sellStock(FOUND_A_MATCH, stockPair, "2022-01-03", Double.parseDouble("10"));
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(
          "This transaction cannot be performed because selling 3.00 stocks of AMZN "
              + "on date 2022-01-03 in portfolio ValidSymbol invalidates a previous transaction. ",
          illegalArgumentException.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testGetCostBasisDateInvalid() throws IOException {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);

    try {
      model.getCostBasis(FOUND_A_MATCH, "ds");
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(Constants.DATE_INVALID, illegalArgumentException.getMessage());
    }
  }

  @Test
  public void testGetCostBasisPortfolioNameNullOrEmpty() {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);
    try {
      model.getCostBasis("", "2022-10-10");
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(Constants.INPUT_NULL_OR_EMPTY, illegalArgumentException.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testGetCostBasis() {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);
    try {
      double value = model.getCostBasis(FOUND_A_MATCH, "2022-10-10");
      assertEquals(3003.68, value, 0.01);
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(Constants.INPUT_NULL_OR_EMPTY, illegalArgumentException.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testFixedCostStrategyCreation() throws IOException {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);
    IPortfolioInvestmentStrategyVisitor<Void> visitor = new FixedCostVisitor<>(true);

    List<Pair<String, Double>> stocks = new ArrayList<>();
    stocks.add(new Pair<>("AAPL", 10.00));
    stocks.add(new Pair<>("AAPL", 20.00));
    stocks.add(new Pair<>("AMZN", 70.00));
    InvestmentStrategy investmentStrategy = new InvestmentStrategy(stocks);
    investmentStrategy.setStrategyStartDate("2022-10-10");
    investmentStrategy.setCommission(12.00);
    investmentStrategy.setStrategyInvestment(2000.00);

    model.acceptInvestmentStrategy(visitor, "fixed1",
        investmentStrategy);

    assertEquals(2, mockLog.stream().filter(x ->
        x.equals(MOCK_REPO_UPDATE_MESSAGE)).count());
  }

  @Test
  public void testDollarCostAveragingStrategyCreation() throws IOException {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);
    IPortfolioInvestmentStrategyVisitor<Void> visitor = new DollarCostAveragingVisitor<>(true);

    List<Pair<String, Double>> stocks = new ArrayList<>();
    stocks.add(new Pair<>("AAPL", 10.00));
    stocks.add(new Pair<>("AAPL", 20.00));
    stocks.add(new Pair<>("AMZN", 70.00));
    InvestmentStrategy investmentStrategy = new InvestmentStrategy(stocks);
    investmentStrategy.setCommission(12.00);
    investmentStrategy.setStrategyInvestment(2000.00);
    investmentStrategy.setStrategyStartDate("2022-11-01");
    investmentStrategy.setStrategyEndDate("2022-12-01");
    investmentStrategy.setStrategyPeriod(5);

    model.acceptInvestmentStrategy(visitor, "fixed1",
        investmentStrategy);

    assertEquals(10, mockLog.stream().filter(x ->
        x.equals(MOCK_REPO_UPDATE_MESSAGE)).count());
  }

  @Test
  public void testFixedCostStrategyCostBasis() throws IOException {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);

    double value = model.getCostBasis(FOUND_A_MATCH, "2022-11-10");
    assertEquals(8399.02, value, 0.01);
  }

  @Test
  public void testDollarCostAveragingStrategyCostBasis() throws IOException {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);

    double value = model.getCostBasis(FOUND_A_MATCH, "2022-11-20");
    assertEquals(9682.05, value, 0.01);
  }

  @Test
  public void testDollarCostAveragingStrategyCostBasisOnDifferentDates() throws IOException {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);

    double value = model.getCostBasis(FOUND_A_MATCH, "2022-10-01");
    assertEquals(1627.2, value, 0.01);

    value = model.getCostBasis(FOUND_A_MATCH, "2022-11-10");
    assertEquals(8399.02, value, 0.01);

    value = model.getCostBasis(FOUND_A_MATCH, "2022-11-20");
    assertEquals(9682.05, value, 0.01);
  }

  @Test
  public void testInvestmentStrategyCostBasisOnDifferentDates() throws IOException {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);

    double value = model.getCostBasis(FOUND_A_MATCH, "2022-11-01");
    assertEquals(5606.87, value, 0.01);

    value = model.getCostBasis(FOUND_A_MATCH, "2022-11-10");
    assertEquals(8399.02, value, 0.01);

    value = model.getCostBasis(FOUND_A_MATCH, "2022-11-18");
    assertEquals(9682.05, value, 0.01);

    value = model.getCostBasis(FOUND_A_MATCH, "2022-11-25");
    assertEquals(9682.05, value, 0.01);
  }

  @Test
  public void testInvestmentStrategyGetPortfolioValue() throws IOException {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);

    Pair<Portfolio, Double> value = model.getPortfolioValueOnDate(FOUND_A_MATCH, "2022-11-20");
    assertEquals(6116.21, value.getValue(), 0.01);
  }

  @Test
  public void testInvestmentStrategyGetPortfolioValueOnDifferentDays() throws IOException {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository, mockService);

    double value = model.getPortfolioValueOnDate(FOUND_A_MATCH, "2022-11-01").getValue();
    assertEquals(2101.03, value, 0.01);

    value = model.getPortfolioValueOnDate(FOUND_A_MATCH, "2022-11-10").getValue();
    assertEquals(4853.18, value, 0.01);

    value = model.getPortfolioValueOnDate(FOUND_A_MATCH, "2022-11-18").getValue();
    assertEquals(6116.21, value, 0.01);
  }
}