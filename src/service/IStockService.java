package service;

import model.Stock;

public interface IStockService {

  Stock getStock(String symbol, double quantity);

  Stock getStockOnDate(String symbol, String date);
}
