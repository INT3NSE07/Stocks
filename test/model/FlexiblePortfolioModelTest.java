package model;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import constants.Constants;
import utilities.Pair;

import static org.junit.Assert.*;

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
            Arrays.asList(
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
      assertEquals(Constants.COMMISSION_NON_NEGATIVE, illegalArgumentException.getMessage());
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
      assertEquals("The portfolio ValidSymbol does not contain 38.16 of VZ to " +
              "sell on date 2022-10-10.", illegalArgumentException.getMessage());
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
      assertEquals("Quantity of a stock cannot be negative or zero.", illegalArgumentException.getMessage());
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
      assertEquals("The portfolio ValidSymbol does not contain 10.00 of " +
              "VALIDSYMBOL to sell on date 2022-10-15.", illegalArgumentException.getMessage());
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

    Pair<String, Double> stockPair = new Pair<>(FOUND_A_MATCH.toUpperCase(),
            Double.parseDouble("2"));

    try {
      model.sellStock(FOUND_A_MATCH, stockPair, "2022-10-15", Double.parseDouble("10"));
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals("The portfolio ValidSymbol does not contain 10.00 of " +
              "VALIDSYMBOL to sell on date 2022-10-15.", illegalArgumentException.getMessage());
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

}