package service;

import model.Stock;

/**
 *
 */
public interface IStockService {

  /**
   *
   * @param symbol
   * @param quantity
   * @return
   */
  Stock getStock(String symbol, double quantity);

  /**
   *
   * @param symbol
   * @param date
   * @return
   */
  Stock getStockOnDate(String symbol, String date);
}
