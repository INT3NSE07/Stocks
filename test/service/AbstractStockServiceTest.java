package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import constants.Constants;
import java.io.IOException;
import model.Stock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import utilities.DateUtils;

/**
 * A JUnit test class for the {@link AbstractStockService}s class.
 */
public abstract class AbstractStockServiceTest {

  protected final String currentDate;
  @Rule
  public TemporaryFolder tmpFolder = new TemporaryFolder();

  public AbstractStockServiceTest() {
    currentDate = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
  }

  protected abstract IStockService createStockService() throws IOException;

  protected abstract Stock getStock();

  protected abstract Stock getStockOnDate();

  @Test
  public void testGetStockOnDate() {
    try {
      IStockService stockService = createStockService();
      Stock stock = getStockOnDate();
      String symbol = "AAPL";
      String date = "2022-10-05";

      Stock result = stockService.getStockOnDate(symbol, date);

      assertEquals(stock.getSymbol(), result.getSymbol());
      assertEquals(stock.getDate(), result.getDate());
      assertEquals(stock.getOpen(), result.getOpen(), 0);
      assertEquals(stock.getHigh(), result.getHigh(), 0);
      assertEquals(stock.getLow(), result.getLow(), 0);
      assertEquals(stock.getClose(), result.getClose(), 0);
      assertEquals(stock.getVolume(), result.getVolume(), 0);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetStockOnDateInvalidSymbol() {
    try {
      IStockService stockService = createStockService();
      String symbol = "aawww";
      String date = "2022-10-05";

      try {
        Stock result = stockService.getStockOnDate(symbol, date);
      } catch (IllegalArgumentException e) {
        assertEquals(
            String.format(Constants.NO_STOCK_DATA_FOUND, symbol.toUpperCase(), date),
            e.getMessage());
      }
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }
}
