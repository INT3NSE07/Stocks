package service;

import java.io.IOException;
import model.Stock;

public interface IStockService {

  Stock getStock(String symbol, double quantity) throws IllegalArgumentException, IOException;

  Stock getStockOnDate(String symbol, String date) throws IllegalArgumentException, IOException;

  boolean isStockSymbolValid(String symbol) throws IOException;
}
