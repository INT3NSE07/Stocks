package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import constants.Constants;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import model.IFlexiblePortfolioModel;
import model.Portfolio;
import org.junit.Test;
import utilities.Pair;
import view.IPortfolioView;

/**
 * A JUnit test class for the {@link IPortfolioController}s class.
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
  private static final String MOCK_VIEW_SHOW_PORTFOLIO =
      "MockView showPortfolio() called with portfolio Name: %s";
  private static final String MOCK_VIEW_SHOW_PORTFOLIO_VALUE =
      "MockView showPortfolioValue() called with portfolio Name: %s, Value: %.2f";

  private static final String MOCK_MODEL_CREATE_PORTFOLIO =
      "MockModel createPortfolio() called with Portfolio Name: %s, List of Stocks: "
          + System.lineSeparator();
  private static final String MOCK_MODEL_READ_PORTFOLIO =
      "MockModel readPortfolio() called with Portfolio Name: %s";
  private static final String MOCK_MODEL_GET_PORTFOLIO_ON_DATE =
      "MockModel getPortfolioValueOnDate() called with Portfolio Name: %s, Date: %s";
  private static final String MOCK_MODEL_IS_SYMBOL_VALID =
      "MockModel isStockSymbolValid() called with Symbol %s";
  private static final String FOUND_A_MATCH = "SymbolValid";
  private static final Double PORTFOLIO_VALUE = 23455.0;
  private static final String FOUND_A_MATCH1 = "SymbolValid1";

  @Test
  public void testInitialState() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    int initialSelectedMenuItem = 0;
    int selectedMenuItem = 4;
    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        Integer.toString(selectedMenuItem).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);
      controller.run();
    } catch (IOException e) {
      fail(e.getMessage());
    }

    assertEquals(String.format(MOCK_VIEW_SHOW_OPTIONS, initialSelectedMenuItem), mockLog.get(0));
  }

  @Test
  public void testExit() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    int initialSelectedMenuItem = 0;
    int selectedMenuItem = 4;
    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        Integer.toString(selectedMenuItem).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);

      controller.run();
    } catch (IOException e) {
      fail(e.getMessage());
    }

    assertEquals(String.format(MOCK_VIEW_SHOW_OPTIONS, initialSelectedMenuItem), mockLog.get(0));
  }

  @Test
  public void testRandomInvalidInput() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    int count = ThreadLocalRandom.current().nextInt(5, 10);
    String[] inputs = new String[count + 1];
    for (int i = 0; i < count; i++) {
      inputs[i] = ThreadLocalRandom.current().nextInt(5, 10000) + System.lineSeparator();
    }
    inputs[count] = "4";

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
  public void testOption2NullOREmptyPortfolioName() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    String[] inputs = {
        "2",
        "",
        "4"
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
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_PORTFOLIO_NAME"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.INPUT_NULL_OR_EMPTY),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)
          ));
      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testOption2ValidInput() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    String[] inputs = {
        "2",
        FOUND_A_MATCH,
        "4"
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
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_PORTFOLIO_NAME"),
              String.format(MOCK_MODEL_READ_PORTFOLIO, FOUND_A_MATCH),
              String.format(MOCK_VIEW_SHOW_PORTFOLIO, FOUND_A_MATCH),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)
          ));
      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testOption2PortfolioFetchFailed() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    String[] inputs = {
        "2",
        "PortfolioName",
        "4"
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
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_PORTFOLIO_NAME"),
              String.format(MOCK_MODEL_READ_PORTFOLIO, "PortfolioName"),
              String.format(MOCK_VIEW_SHOW_STRING,
                  String.format(Constants.PORTFOLIO_FETCH_FAIL, "PortfolioName")),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)
          ));
      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testOption3NullOREmptyPortfolioName() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    String[] inputs = {
        "3",
        "",
        "4"
    };

    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        String.join(System.lineSeparator(), inputs).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);
      controller.run();

      List<String> expected = new ArrayList<>(
          Arrays.asList(
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_PORTFOLIO_NAME"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.INPUT_NULL_OR_EMPTY),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)
          ));
      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testOption3NullOREmptyPortfolioDate() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    // accepts null is what tested here.
    String[] inputs = {
        "3",
        FOUND_A_MATCH,
        "",
        "4"
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
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_PORTFOLIO_NAME"),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_DATE"),
              String.format(MOCK_MODEL_GET_PORTFOLIO_ON_DATE, FOUND_A_MATCH, ""),
              String.format(MOCK_VIEW_SHOW_PORTFOLIO_VALUE, null, PORTFOLIO_VALUE),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)
          ));
      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testOption3PortfolioFetchFailed() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    String[] inputs = {
        "3",
        "PortfolioName",
        "",
        "4"
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
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_PORTFOLIO_NAME"),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_DATE"),
              String.format(MOCK_MODEL_GET_PORTFOLIO_ON_DATE, "PortfolioName", ""),
              String.format(MOCK_VIEW_SHOW_STRING,
                  String.format(Constants.PORTFOLIO_FETCH_FAIL, "PortfolioName")),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)
          ));
      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testOption3PortfolioValidInput() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    String[] inputs = {
        "3",
        FOUND_A_MATCH,
        "2022-02-14",
        "4"
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
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_PORTFOLIO_NAME"),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_DATE"),
              String.format(MOCK_MODEL_GET_PORTFOLIO_ON_DATE, FOUND_A_MATCH, "2022-02-14"),
              String.format(MOCK_VIEW_SHOW_PORTFOLIO_VALUE, null, 2 * PORTFOLIO_VALUE),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)
          ));
      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testOption1NullOREmptyPortfolioName() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    String[] inputs = {
        "1",
        "",
        "4"
    };

    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        String.join(System.lineSeparator(), inputs).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);
      controller.run();

      List<String> expected = new ArrayList<>(
          Arrays.asList(
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_PORTFOLIO_NAME"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.INPUT_NULL_OR_EMPTY),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)

          ));
      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testOption1SubmenuOption2And0Stocks() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    String[] inputs = {
        "1",
        FOUND_A_MATCH,
        "2",
        "4"
    };

    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        String.join(System.lineSeparator(), inputs).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);
      controller.run();

      List<String> expected = new ArrayList<>(
          Arrays.asList(
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_PORTFOLIO_NAME"),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 1),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_MODEL_CREATE_PORTFOLIO, FOUND_A_MATCH),
              String.format(MOCK_VIEW_SHOW_STRING,
                  "The portfolio " + FOUND_A_MATCH + " has been created."),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)
          ));
      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testOption1SubmenuOption1NullOREmptyStockName() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    String[] inputs = {
        "1",
        FOUND_A_MATCH,
        "1",
        "",
        "2",
        "4"
    };

    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        String.join(System.lineSeparator(), inputs).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);
      controller.run();

      List<String> expected = new ArrayList<>(
          Arrays.asList(
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_PORTFOLIO_NAME_KEY),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 1),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_STOCK_SYMBOL_KEY),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.INPUT_NULL_OR_EMPTY),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 1),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_MODEL_CREATE_PORTFOLIO, FOUND_A_MATCH),
              String.format(MOCK_VIEW_SHOW_STRING,
                  "The portfolio " + FOUND_A_MATCH + " has been created."),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)
          ));
      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testOption1SubmenuOption1ValidStockName() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    String[] inputs = {
        "1",
        FOUND_A_MATCH,
        "1",
        FOUND_A_MATCH,
        "213",
        "2",
        "4"
    };

    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        String.join(System.lineSeparator(), inputs).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);
      controller.run();

      List<String> expected = new ArrayList<>(
          Arrays.asList(
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_PORTFOLIO_NAME_KEY),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 1),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_STOCK_SYMBOL_KEY),
              String.format(MOCK_MODEL_IS_SYMBOL_VALID, FOUND_A_MATCH),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_QUANTITY_KEY),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 1),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_MODEL_CREATE_PORTFOLIO, FOUND_A_MATCH),
              String.format(FOUND_A_MATCH + " " + "213.0"),
              String.format(MOCK_VIEW_SHOW_STRING,
                  "The portfolio " + FOUND_A_MATCH + " has been created."),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)
          ));
      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testOption1SubmenuOption1InValidStockName() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    String[] inputs = {
        "1",
        FOUND_A_MATCH,
        "1",
        "InValidSymbol",
        "2",
        "4"
    };

    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        String.join(System.lineSeparator(), inputs).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);
      controller.run();

      List<String> expected = new ArrayList<>(
          Arrays.asList(
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_PORTFOLIO_NAME_KEY),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 1),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_STOCK_SYMBOL_KEY),
              String.format(MOCK_MODEL_IS_SYMBOL_VALID, "InValidSymbol"),
              String.format(MOCK_VIEW_SHOW_STRING,
                  String.format(Constants.SYMBOL_FETCH_FAIL, "InValidSymbol")),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 1),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_MODEL_CREATE_PORTFOLIO, FOUND_A_MATCH),
              String.format(MOCK_VIEW_SHOW_STRING,
                  "The portfolio " + FOUND_A_MATCH + " has been created."),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)));
      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testOption1SubmenuOption1MultipleStockName() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    String[] inputs = {
        "1",
        FOUND_A_MATCH,
        "1",
        FOUND_A_MATCH,
        "231",
        "1",
        FOUND_A_MATCH1,
        "123",
        "2",
        "4"
    };

    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        String.join(System.lineSeparator(), inputs).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);
      controller.run();

      List<String> expected = new ArrayList<>(
          Arrays.asList(
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_PORTFOLIO_NAME_KEY),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 1),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_STOCK_SYMBOL_KEY),
              String.format(MOCK_MODEL_IS_SYMBOL_VALID, FOUND_A_MATCH),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_QUANTITY_KEY),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 1),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_STOCK_SYMBOL_KEY),
              String.format(MOCK_MODEL_IS_SYMBOL_VALID, FOUND_A_MATCH1),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_QUANTITY_KEY),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 1),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_MODEL_CREATE_PORTFOLIO, FOUND_A_MATCH),
              String.format(FOUND_A_MATCH + " " + "231.0"),
              String.format(FOUND_A_MATCH1 + " " + "123.0"),
              String.format(MOCK_VIEW_SHOW_STRING,
                  "The portfolio " + FOUND_A_MATCH + " has been created."),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)
          ));
      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));
    } catch (IOException e) {
      fail(e.getMessage());
    }

  }

  @Test
  public void testOption1SubmenuOption1ValidQuantity() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    String[] inputs = {
        "1",
        FOUND_A_MATCH,
        "1",
        FOUND_A_MATCH,
        "231",
        "2",
        "4"
    };

    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        String.join(System.lineSeparator(), inputs).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);
      controller.run();

      List<String> expected = new ArrayList<>(
          Arrays.asList(
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_PORTFOLIO_NAME_KEY),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 1),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_STOCK_SYMBOL_KEY),
              String.format(MOCK_MODEL_IS_SYMBOL_VALID, FOUND_A_MATCH),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_QUANTITY_KEY),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 1),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_MODEL_CREATE_PORTFOLIO, FOUND_A_MATCH),
              String.format(FOUND_A_MATCH + " " + "231.0"),
              String.format(MOCK_VIEW_SHOW_STRING,
                  "The portfolio " + FOUND_A_MATCH + " has been created."),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)
          ));
      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));
    } catch (IOException e) {
      fail(e.getMessage());
    }

  }

  @Test
  public void testOption1SubmenuOption1InValidQuantity() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    String[] inputs = {
        "1",
        FOUND_A_MATCH,
        "1",
        FOUND_A_MATCH,
        "231.4",
        "2",
        "4"
    };

    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        String.join(System.lineSeparator(), inputs).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);
      controller.run();

      List<String> expected = new ArrayList<>(
          Arrays.asList(
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_PORTFOLIO_NAME_KEY),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 1),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_STOCK_SYMBOL_KEY),
              String.format(MOCK_MODEL_IS_SYMBOL_VALID, FOUND_A_MATCH),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_QUANTITY_KEY),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.QUANTITY_MUST_BE_A_WHOLE_NUMBER),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 1),
              String.format(MOCK_VIEW_SHOW_PROMPT, Constants.PROMPT_CHOICE),
              String.format(MOCK_MODEL_CREATE_PORTFOLIO, FOUND_A_MATCH),
              String.format(MOCK_VIEW_SHOW_STRING,
                  "The portfolio " + FOUND_A_MATCH + " has been created."),
              String.format(MOCK_VIEW_SHOW_OPTIONS, 0),
              String.format(MOCK_VIEW_SHOW_PROMPT, "PROMPT_CHOICE"),
              String.format(MOCK_VIEW_SHOW_STRING, Constants.EXITING_STATUS)
          ));
      assertEquals(Arrays.toString(expected.toArray()), Arrays.toString(mockLog.toArray()));
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  static class MockModel implements IFlexiblePortfolioModel {

    private final List<String> log;

    public MockModel(List<String> log) {
      this.log = log;
    }

    @Override
    public void createPortfolio(String portFolioName, List<Pair<String, Double>> stockPairs)
        throws IllegalArgumentException {
      this.log.add(String.format(MOCK_MODEL_CREATE_PORTFOLIO, portFolioName));
      for (Pair<String, Double> stockPair :
          stockPairs) {
        this.log.add(stockPair.getKey() + " " + stockPair.getValue());
      }
    }

    @Override
    public void buyStock(String portFolioName, Pair<String, Double> stockPair, String Date, double commission) {

    }

    @Override
    public void sellStock(String portfolioName, Pair<String, Double> stockPair, String date, double commission) {
    }

    @Override
    public Pair<Portfolio, Double> getCostBasis(String portfolioName, String date) throws IOException {
      return null;
    }

    @Override
    public void getPerformanceOverview(Portfolio portfolio) {

    }

    @Override
    public Portfolio readPortfolio(String portFolioName) throws IllegalArgumentException {
      this.log.add(String.format(MOCK_MODEL_READ_PORTFOLIO, portFolioName));
      if (!portFolioName.equals(FOUND_A_MATCH)) {
        throw new IllegalArgumentException(
            String.format(Constants.PORTFOLIO_FETCH_FAIL, portFolioName));
      }
      Portfolio portfolio = new Portfolio();
      portfolio.setName(FOUND_A_MATCH);
      return portfolio;
    }

    @Override
    public Pair<Portfolio, Double> getPortfolioValueOnDate(String portFolioName, String date)
        throws IllegalArgumentException {
      this.log.add(String.format(MOCK_MODEL_GET_PORTFOLIO_ON_DATE, portFolioName, date));
      if (!portFolioName.equals(FOUND_A_MATCH)) {
        throw new IllegalArgumentException(
            String.format(Constants.PORTFOLIO_FETCH_FAIL, portFolioName));
      }
      if (date.equals("2022-02-14")) {
        return new Pair<>(new Portfolio(), 2 * PORTFOLIO_VALUE);
      }
      return new Pair<>(new Portfolio(), PORTFOLIO_VALUE);
    }

    @Override
    public boolean isStockSymbolValid(String symbol) {
      this.log.add(String.format(MOCK_MODEL_IS_SYMBOL_VALID, symbol));
      return symbol.equals(FOUND_A_MATCH) || symbol.equals(FOUND_A_MATCH1);
    }

    @Override
    public void addStock(String portFolioName, List<Pair<String, Double>> stockPairs)
        throws IllegalArgumentException, IOException {

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
      this.log.add(String.format(MOCK_VIEW_SHOW_PORTFOLIO, portfolio.getName()));
    }

    @Override
    public void showPortfolioValue(Pair<Portfolio, Double> portfolioValue) {
      this.log.add(String.format(MOCK_VIEW_SHOW_PORTFOLIO_VALUE, portfolioValue.getKey().getName(),
          portfolioValue.getValue()));
    }
  }
}


