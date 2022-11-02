package view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import constants.Constants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.Test;

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
    for (int i = 0; i < Constants.MAIN_MENU_ITEMS.length; i++) {
      OutputStream outputStream = new ByteArrayOutputStream();
      IPortfolioView view = new PortfolioTextView(new PrintStream(outputStream));
      view.showOptions(i);
      String expected;
      if (i == 0) {
        expected = System.lineSeparator() +
            "Portfolio Management Services" + System.lineSeparator()
            + "1) Create a portfolio" + System.lineSeparator()
            + "2) Examine a portfolio" + System.lineSeparator()
            + "3) Determine value of a portfolio on a certain date" + System.lineSeparator()
            + "4) Exit" + System.lineSeparator();
      } else if (i == 1) {
        expected = System.lineSeparator() +
            "Create a portfolio" + System.lineSeparator()
            + "1) Add a stock to this portfolio" + System.lineSeparator()
            + "2) Back" + System.lineSeparator();
      } else {
        expected = System.lineSeparator() + Constants.MAIN_MENU_ITEMS[i] + System.lineSeparator();
      }
      assertEquals(expected, outputStream.toString());
    }
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
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

  private String getRandomString(int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int index = (int) (alphabetString.length() * Math.random());
      sb.append(alphabetString.charAt(index));
    }

    return sb.toString();
  }
}