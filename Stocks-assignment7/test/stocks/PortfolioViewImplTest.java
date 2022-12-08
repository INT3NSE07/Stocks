package stocks;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import stocks.portfolio.Portfolio;
import stocks.portfolio.PortfolioImpl;
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the view of the program.
 */
public class PortfolioViewImplTest {

  PortfolioView view;
  ByteArrayOutputStream outStream;

  @Before
  public void setUp() {
    outStream = new ByteArrayOutputStream();
    view = new PortfolioViewImpl(new PrintStream(outStream));
  }

  @Test
  public void testPrintMessage() {
    String message = "Test message";
    view.printMessage(message);
    assertEquals(message + "\n", outStream.toString());
  }

  @Test
  public void testPrintComposition() {
    Portfolio p = new PortfolioImpl("Test CSV Portfolio", "/Users/arinjay/Desktop/"
            + "Fall 22/CS 5010/group/Stocks/test/resources/portfolio.csv");
    view.printComposition(p, "12-12-2022");
    String expected = "MSFT 200\n" +
            "GOOGL 400\n" +
            "AAPL 140\n";
    assertEquals(expected, outStream.toString());
  }

  @Test
  public void testPrintOptions() {
    String expected = "\n" +
            "Menu: \n" +
            "1. Load portfolio from a file\n" +
            "2. Create portfolio manually\n" +
            "3. Check value of portfolio\n" +
            "4. View composition of a portfolio\n" +
            "5. Save portfolio to a file\n" +
            "6. View all portfolio names\n" +
            "7. Load flexible portfolio from a file\n" +
            "8. Create flexible portfolio manually\n" +
            "9. Buy stocks for a flexible portfolio\n" +
            "10. Sell stocks from a flexible portfolio\n" +
            "11. View performance of a flexible portfolio over time\n" +
            "Q. Exit the program\n" +
            "Enter your choice to perform action: ";
    view.showOptions();
    assertEquals(expected, outStream.toString());
  }

}