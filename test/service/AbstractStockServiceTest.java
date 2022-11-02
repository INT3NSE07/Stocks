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

public abstract class AbstractStockServiceTest {

  @Rule
  public TemporaryFolder tmpFolder = new TemporaryFolder();

  protected final String currentDate;
  private String tmpFileName;

  public AbstractStockServiceTest() {
    currentDate = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
  }

  protected abstract IStockService createStockService(String tmpFileName) throws IOException;

  protected abstract Stock getStock();

  protected abstract Stock getStockOnDate();

  @Test
  public void testGetStock() {
    try {
      this.tmpFileName = tmpFolder.newFile().toString();

      IStockService stockService = createStockService(this.tmpFileName);
      Stock stock = getStock();
      String symbol = "AAPL";
      double quantity = 123.0;

      Stock result = stockService.getStock(symbol, quantity);

      assertEquals(stock.getSymbol(), result.getSymbol());
      assertEquals(quantity, result.getQuantity(), 0);
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
  public void testGetStockInvalidSymbol() {
    try {
      this.tmpFileName = tmpFolder.newFile().toString();

      IStockService stockService = createStockService(this.tmpFileName);
      String symbol = "aawww";
      double quantity = 123.0;

      try {
        Stock result = stockService.getStock(symbol, quantity);
      } catch (IllegalArgumentException e) {
        assertEquals(
            String.format(Constants.NO_STOCK_DATA_FOUND, symbol.toUpperCase(), this.currentDate),
            e.getMessage());
      }
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testGetStockOnDate() {
    try {
      IStockService stockService = createStockService(this.tmpFileName);
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
      IStockService stockService = createStockService(this.tmpFileName);
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

  @Test
  public void testIsStockSymbolValid() {
    try {
      IStockService stockService = createStockService(this.tmpFileName);
      String symbol = "AAPL";

      assertTrue(stockService.isStockSymbolValid(symbol));
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testIsStockSymbolValidInvalidSymbol() {
    try {
      IStockService stockService = createStockService(this.tmpFileName);
      String symbol = "asdas";

      assertFalse(stockService.isStockSymbolValid(symbol));
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }
}
