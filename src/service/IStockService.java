package service;

import model.Stock;

public interface IStockService {

  Stock getStock(String ticker);
}
