package service;

import java.net.URL;
import model.Stock;

public class AlphaVantageStockService implements IStockService {

  // TODO: move to config
  private final String apiKey = "7JWGAITR6OPG00LM";
  URL url = null;

  @Override
  public Stock getStock(String ticker) {
    return new Stock();
  }
}
