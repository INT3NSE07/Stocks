package view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import constants.Constants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.Test;
import model.Portfolio;
import model.PortfolioValue;
import model.Stock;
import utilities.Pair;

/**
 * A JUnit test class for the {@link PortfolioTextView}s class.
 */
public class PortfolioTextViewTest {

  private static final String alphabetString =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz";

  @Test
  public void showStringEmpty() {
    OutputStream outputStream = new ByteArrayOutputStream();
    IPortfolioView view = new PortfolioTextView(new PrintStream(outputStream));
    int ranInt = ThreadLocalRandom.current().nextInt(0, 10000);
    String randString = "";
    view.showString(randString);
    assertEquals(randString + System.lineSeparator(), outputStream.toString());
  }

  @Test
  public void showStringNull() {
    OutputStream outputStream = new ByteArrayOutputStream();
    IPortfolioView view = new PortfolioTextView(new PrintStream(outputStream));
    int ranInt = ThreadLocalRandom.current().nextInt(0, 10000);
    String randString = null;
    view.showString(randString);
    assertEquals(randString + System.lineSeparator(), outputStream.toString());
  }

  @Test
  public void showString() {
    OutputStream outputStream = new ByteArrayOutputStream();
    IPortfolioView view = new PortfolioTextView(new PrintStream(outputStream));
    int ranInt = ThreadLocalRandom.current().nextInt(0, 10000);
    String randString = getRandomString(ranInt);
    view.showString(randString);
    assertEquals(randString + System.lineSeparator(), outputStream.toString());
  }

  @Test
  public void showOptionsInMenu() {
    for (int i = 0; i < Constants.MENU_TYPE.size(); i++) {
      OutputStream outputStream = new ByteArrayOutputStream();
      IPortfolioView view = new PortfolioTextView(new PrintStream(outputStream));
      view.showOptions(i);
      String expected = null;
      if (i == 0) {
        expected = System.lineSeparator()
            + "Enter the type of portfolio" + System.lineSeparator()
            + "1) Inflexible portfolio" + System.lineSeparator()
            + "2) Flexible portfolio" + System.lineSeparator()
            + "3) Exit" + System.lineSeparator();
      } else if (i == 1) {
        expected = System.lineSeparator()
            + "Portfolio Management Services" + System.lineSeparator()
            + "1) Create a portfolio" + System.lineSeparator()
            + "2) Examine a portfolio" + System.lineSeparator()
            + "3) Determine value of a portfolio on a certain date" + System.lineSeparator()
            + "4) Back" + System.lineSeparator();
      } else if (i == 2) {
        expected = System.lineSeparator()
            + "Portfolio Management Services" + System.lineSeparator()
            + "1) Create a portfolio" + System.lineSeparator()
            + "2) Examine a portfolio" + System.lineSeparator()
            + "3) Determine value of a portfolio on a certain date" + System.lineSeparator()
            + "4) Make a transactions in a portfolio" + System.lineSeparator()
            + "5) Calculate cost basis" + System.lineSeparator()
            + "6) Get performance of a portfolio over a period" + System.lineSeparator()
            + "7) Back" + System.lineSeparator();
      } else if (i == 3) {
        expected = System.lineSeparator()
            + "Create portfolio menu" + System.lineSeparator()
            + "1) Add a stock to this portfolio" + System.lineSeparator()
            + "2) Back" + System.lineSeparator();
      } else if (i == 4) {
        expected = System.lineSeparator()
            + "Transaction menu" + System.lineSeparator()
            + "1) Buy stocks" + System.lineSeparator()
            + "2) Sell stocks" + System.lineSeparator()
            + "3) Back" + System.lineSeparator();
      }
      assertEquals(expected, outputStream.toString());
    }
  }

  @Test(expected = NullPointerException.class)
  public void showOptionsOutOfMenu() {
    int randInt = ThreadLocalRandom.current().nextInt(5, 10000);

    try (OutputStream outputStream = new ByteArrayOutputStream()) {
      IPortfolioView view = new PortfolioTextView(new PrintStream(outputStream));
      view.showOptions(randInt);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void showOptionError() {
    OutputStream outputStream = new ByteArrayOutputStream();
    IPortfolioView view = new PortfolioTextView(new PrintStream(outputStream));

    view.showString(Constants.INVALID_OPTION);

    String expected = Constants.INVALID_OPTION + System.lineSeparator();
    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void showPromptInListOfPrompts() {

    for (String key :
        Constants.TEXT_VIEW_CONSTANTS.keySet()) {
      OutputStream outputStream = new ByteArrayOutputStream();
      IPortfolioView view = new PortfolioTextView(new PrintStream(outputStream));
      view.showPrompt(key);
      assertEquals(Constants.TEXT_VIEW_CONSTANTS.get(key) + ": ", outputStream.toString());
    }
  }

  @Test
  public void showPromptOutListOfPrompts() {

    OutputStream outputStream = new ByteArrayOutputStream();
    IPortfolioView view = new PortfolioTextView(new PrintStream(outputStream));
    int ranInt = ThreadLocalRandom.current().nextInt(5, 10000);

    view.showPrompt(String.valueOf(ranInt));
    assertEquals(null + ": ", outputStream.toString());
  }

  @Test
  public void showPortfolio() {

    OutputStream outputStream = new ByteArrayOutputStream();
    IPortfolioView view = new PortfolioTextView(new PrintStream(outputStream));
    int ranInt = ThreadLocalRandom.current().nextInt(5, 10000);

    Portfolio portfolio = new Portfolio();
    portfolio.setName("ez");
    Stock amzn = Stock.StockBuilder.create().setSymbol("AMZN").setQuantity(234);
    Stock msft = Stock.StockBuilder.create().setSymbol("MSFT").setQuantity(234);
    List<Stock> stocks = new ArrayList<>();
    stocks.add(amzn);
    stocks.add(msft);
    portfolio.addStocks(stocks);
    view.showPortfolio(portfolio);
    String expected =
        System.lineSeparator() + "Composition of the portfolio ez"
            + System.lineSeparator()
            + "+----+---------------+----------+" + System.lineSeparator()
            + "| ID | Ticker symbol | Quantity |" + System.lineSeparator()
            + "+----+---------------+----------+" + System.lineSeparator()
            + "| 1  | AMZN          | 234.0    |" + System.lineSeparator()
            + "| 2  | MSFT          | 234.0    |" + System.lineSeparator()
            + "+----+---------------+----------+" + System.lineSeparator();
    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void showPortfolioValue() {

    OutputStream outputStream = new ByteArrayOutputStream();
    IPortfolioView view = new PortfolioTextView(new PrintStream(outputStream));

    Portfolio portfolio = new Portfolio();
    portfolio.setName("ez");
    Stock amzn = Stock.StockBuilder.create().setSymbol("AMZN").setQuantity(234).setClose(123);
    Stock msft = Stock.StockBuilder.create().setSymbol("MSFT").setQuantity(234).setClose(321);
    List<Stock> stocks = new ArrayList<>();
    stocks.add(amzn);
    stocks.add(msft);
    portfolio.addStocks(stocks);
    view.showPortfolioValue(new Pair<>(portfolio, 103896.0));
    String expected =
        System.lineSeparator() + "Value of the portfolio ez" + System.lineSeparator()
            + "+----+---------------+----------+---------------+" + System.lineSeparator()
            + "| ID | Ticker symbol | Quantity | Closing price |" + System.lineSeparator()
            + "+----+---------------+----------+---------------+" + System.lineSeparator()
            + "| 1  | AMZN          | 234.0    | $123.0        |" + System.lineSeparator()
            + "| 2  | MSFT          | 234.0    | $321.0        |" + System.lineSeparator()
            + "+----+---------------+----------+---------------+" + System.lineSeparator()
            + System.lineSeparator()
            + "Total value: $103896.00" + System.lineSeparator();
    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void showPortfolioPerformanceTest() {
    OutputStream outputStream = new ByteArrayOutputStream();
    IPortfolioView view = new PortfolioTextView(new PrintStream(outputStream));
    view.showPortfolioPerformance(
        "Investment_Portfolio",
        "2022-01-01",
        "2022-01-07",
        new ArrayList<PortfolioValue>(
            List.of(
                new PortfolioValue(LocalDate.parse("2022-01-01"), LocalDate.parse("2022-01-07"),
                    9667.9),
                new PortfolioValue(LocalDate.parse("2022-01-10"), LocalDate.parse("2022-01-27"),
                    909357.9),
                new PortfolioValue(LocalDate.parse("2022-01-25"), LocalDate.parse("2022-02-07"),
                    56267.9)
            )
        )
    );
    String expected = "Performance of portfolio Investment_Portfolio from 2022-01-01 to 2022-01-07"
        + System.lineSeparator()
        + System.lineSeparator()
        + "2022-01-01 - 2022-01-07:  *" + System.lineSeparator()
        + "2022-01-10 - 2022-01-27:  **************************************************"
        + System.lineSeparator()
        + "2022-01-25 - 2022-02-07:  ***" + System.lineSeparator()
        + System.lineSeparator()
        + "Scale:  * = $18187.16" + System.lineSeparator();

    assertEquals(expected, outputStream.toString());
  }

  @Test
  public void showPortfolioPerformanceTestExceptionCases() {
    OutputStream outputStream = new ByteArrayOutputStream();
    IPortfolioView view = new PortfolioTextView(new PrintStream(outputStream));

    try {
      // invalid date formats
      view.showPortfolioPerformance("Investment_Portfolio", "random", "random", new ArrayList<>());
    } catch (NoSuchElementException noSuchElementException) {
      assertEquals("No value present", noSuchElementException.getMessage());
    }

    // null dates
    try {
      // invalid date formats
      view.showPortfolioPerformance("Investment_Portfolio", null, null, new ArrayList<>());
    } catch (NoSuchElementException noSuchElementException) {
      assertEquals("No value present", noSuchElementException.getMessage());
    }

    // empty date strings
    try {
      // invalid date formats
      view.showPortfolioPerformance("Investment_Portfolio", "", "", new ArrayList<>());
    } catch (NoSuchElementException noSuchElementException) {
      assertEquals("No value present", noSuchElementException.getMessage());
    }
  }

  @Test
  public void showOptionErrorTest() {
    OutputStream outputStream = new ByteArrayOutputStream();
    IPortfolioView view = new PortfolioTextView(new PrintStream(outputStream));

    view.showOptionError();

    assertEquals(Constants.INVALID_OPTION
        + System.lineSeparator()
        + System.lineSeparator(), outputStream.toString());


  }

  private String getRandomString(int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int index = (int) (alphabetString.length() * Math.random());
      sb.append(alphabetString.charAt(index));
    }

    return sb.toString();
  }
}