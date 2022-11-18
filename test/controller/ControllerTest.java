package controller;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

import constants.Constants;
import enums.PortfolioTypes;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import model.IPortfolioFacadeModel;
import model.Portfolio;
import model.PortfolioValue;
import org.junit.Test;
import utilities.Pair;
import view.IPortfolioView;

/**
 * A JUnit test class for the {@link controller.IPortfolioController}s class.
 */
public class ControllerTest {

  private static final String MOCK_VIEW_SHOW_OPTIONS =
      "MockView showOptions() called with option %s";
  private static final String MOCK_VIEW_SHOW_OPTION_ERROR =
      "MockView showOptionError() called";
  private static final String MOCK_VIEW_SHOW_STRING =
      "MockView showString() called with String %s";
  private static final String MOCK_VIEW_SHOW_PROMPT =
      "MockView showPrompt() called with Key %s";
  private static final String MOCK_MODEL_SET_PORTFOLIO_TYPE =
      "MockModel setPortfolioType() called with type %s";

  private static final String MOCK_MODEL_READ_PORTFOLIO =
      "MockModel readPortfolio() called with Portfolio Name: %s";
  private static final String MOCK_MODEL_GET_PORTFOLIO_TYPE =
      "MockModel getPortfolioType() called";

  @Test
  public void testInitialState() {

    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    int initialSelectedMenuItem = 0;
    int exitCode = Constants.PORTFOLIO_OPTIONS_EXIT_CODE;
    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        Integer.toString(exitCode).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);
      controller.run();
      List<String> expected = new ArrayList<>(
          Arrays.asList(
              String.format(MOCK_VIEW_SHOW_OPTIONS, initialSelectedMenuItem),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)
          ));
      assertEquals(Arrays.toString(expected.toArray()), mockLog.toString());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testInvalidOptionType() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    String[] inputs = {
        "kfnd",
        String.valueOf(Constants.PORTFOLIO_OPTIONS_EXIT_CODE)
    };

    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        String.join(System.lineSeparator(), inputs).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);
      controller.run();
      List<String> expected = new ArrayList<>(
          Arrays.asList(
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.INVALID_OPTION),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)
          ));
      assertEquals(Arrays.toString(expected.toArray()), mockLog.toString());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testRandomInvalidOption() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    int count = ThreadLocalRandom.current().nextInt(4, 10);
    String[] inputs = new String[count + 1];
    for (int i = 0; i < count; i++) {
      inputs[i] = ThreadLocalRandom.current().nextInt(4, 10000) + System.lineSeparator();
    }
    inputs[count] = String.valueOf(Constants.PORTFOLIO_OPTIONS_EXIT_CODE);

    try (ByteArrayInputStream bais = new ByteArrayInputStream(String.join("", inputs).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);
      controller.run();

      List<String> filteredMockLog = mockLog.stream()
          .filter(x -> x.equals(MOCK_VIEW_SHOW_OPTION_ERROR)).collect(Collectors.toList());
      assertEquals(inputs.length - 1, filteredMockLog.size());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testOption1InflexiblePortfolio() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    String[] inputs = {
        "1",
        String.valueOf(Constants.INFLEXIBLE_EXIT_CODE),
        String.valueOf(Constants.PORTFOLIO_OPTIONS_EXIT_CODE)
    };

    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        String.join(System.lineSeparator(), inputs).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);
      controller.run();

      // expected sequence.
      List<String> expected = new ArrayList<>(
          Arrays.asList(
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_MODEL_SET_PORTFOLIO_TYPE, PortfolioTypes.INFLEXIBLE.toString()),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 1),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.GOING_BACK_STATUS),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)
          ));
      assertEquals(Arrays.toString(expected.toArray()), mockLog.toString());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testOption2flexiblePortfolio() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    String[] inputs = {
        "2",
        String.valueOf(Constants.FLEXIBLE_EXIT_CODE),
        String.valueOf(Constants.PORTFOLIO_OPTIONS_EXIT_CODE)
    };

    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        String.join(System.lineSeparator(), inputs).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);
      controller.run();

      // expected sequence.
      List<String> expected = new ArrayList<>(
          Arrays.asList(
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_MODEL_SET_PORTFOLIO_TYPE, PortfolioTypes.FLEXIBLE),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 2),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.GOING_BACK_STATUS),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)
          ));
      assertEquals(Arrays.toString(expected.toArray()), mockLog.toString());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }


  static class MockModel implements IPortfolioFacadeModel {

    private final List<String> log;

    public MockModel(List<String> log) {
      this.log = log;
    }

    @Override
    public void createPortfolio(String portfolioName, List<Pair<String, Double>> stockPairs)
        throws IllegalArgumentException, IOException {

    }

    @Override
    public Portfolio readPortfolio(String portfolioName, String date)
        throws IllegalArgumentException, IOException {
      return null;
    }

    @Override
    public Pair<Portfolio, Double> getPortfolioValueOnDate(String portfolioName, String date)
        throws IllegalArgumentException, IOException {
      return null;
    }

    @Override
    public void buyStock(String portfolioName, Pair<String, Double> stockPair, String date,
        double commission) throws IOException {
    }

    @Override
    public void sellStock(String portfolioName, Pair<String, Double> stockPair, String date,
        double commission) throws IOException {

    }

    @Override
    public double getCostBasis(String portfolioName, String date) throws IOException {
      return 0;
    }

    @Override
    public List<PortfolioValue> getPerformanceOverview(String portfolioName, String fromDate,
        String toDate) throws IOException {
      return null;
    }

    @Override
    public boolean isStockSymbolValid(String symbol) throws IOException, IllegalArgumentException {
      return false;
    }

    @Override
    public PortfolioTypes getPortfolioType() {
      this.log.add(MOCK_MODEL_GET_PORTFOLIO_TYPE);
      return null;
    }

    @Override
    public void setPortfolioType(PortfolioTypes portfolioType) {
      this.log.add(String.format(MOCK_MODEL_SET_PORTFOLIO_TYPE, portfolioType));
    }
  }

  static class MockView implements IPortfolioView {

    private final List<String> log;

    public MockView(List<String> log) {
      this.log = log;
    }

    @Override
    public void showString(String s) {
      this.log.add(String.format(MOCK_VIEW_SHOW_STRING, s));
    }

    @Override
    public void showOptions(int selectedMenuItem) {
      this.log.add(String.format(MOCK_VIEW_SHOW_OPTIONS, selectedMenuItem));
    }

    @Override
    public void showOptionError() {
      this.log.add(MOCK_VIEW_SHOW_OPTION_ERROR);
    }

    @Override
    public void showPrompt(String key) {
      this.log.add(String.format(MOCK_VIEW_SHOW_PROMPT, key));
    }

    @Override
    public void showPortfolio(Portfolio portfolio) {

    }

    @Override
    public void showPortfolioValue(Pair<Portfolio, Double> portfolioValue) {

    }

    @Override
    public void showPortfolioPerformance(String portfolioName, String fromDate, String toDate,
        List<PortfolioValue> portfolioValues) {

    }

  }
}
