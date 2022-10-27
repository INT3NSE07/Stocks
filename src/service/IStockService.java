package service;

import java.util.Date;
import model.Stock;

public interface IStockService {

  Stock getStock(String symbol);

  Stock getStockOnDate(String symbol, String date);
}
