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

  @Test
  public void createPortfolio() {
  }

  @Test
  public void readPortfolio() {
  }

  @Test
  public void getPortfolioValueOnDate() {
  }

  @Test
  public void testBuyStock() throws IOException {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository,mockService);

    Pair<String, Double> stockPair = new Pair<>("VZ", Double.parseDouble("38.16"));

    model.buyStock("portfolioName",stockPair,"2022-10-10",Double.parseDouble("10"));

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
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository,mockService);

    Pair<String, Double> stockPair = new Pair<>("VZ", Double.parseDouble("38.16"));

    try {
      model.buyStock("", stockPair, "2022-10-10", Double.parseDouble("10"));
    }
    catch (IllegalArgumentException illegalArgumentException) {

      assertEquals(Constants.INPUT_NULL_OR_EMPTY,illegalArgumentException.getMessage());
    }
  }


  @Test
  public void testBuyStockInvalidDate() throws IOException {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository,mockService);

    Pair<String, Double> stockPair = new Pair<>("VZ", Double.parseDouble("38.16"));

    try {
      model.buyStock("portfolioName", stockPair, "NotValidFormat", Double.parseDouble("10"));
    }
    catch (IllegalArgumentException illegalArgumentException) {
      assertEquals(Constants.DATE_INVALID, illegalArgumentException.getMessage());
    }
  }

  @Test
  public void testSellStock() {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository,mockService);

//    model.buyStock();
  }

  @Test
  public void testSellStockInvalidOrNullPortfolioName() {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository,mockService);

//    model.buyStock();
  }

  @Test
  public void testSellStockInvalidDate() {
    List<String> mockLog = new ArrayList<>();

    ModelTest.MockRepository mockRepository = new ModelTest.MockRepository(mockLog);
    ModelTest.MockService mockService = new ModelTest.MockService(mockLog);
    IFlexiblePortfolioModel model = new FlexiblePortfolioModel(mockRepository,mockService);

//    model.buyStock();
  }

  @Test
  public void testGetCostBasis() {

  }

  @Test
  public void testGetPerformanceOverview() {
  }
}