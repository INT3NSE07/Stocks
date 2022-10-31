package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Portfolio {

  private String name;
  private List<Stock> stocks;

  /**
   *
   */
  public Portfolio() {
    this.stocks = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Stock> getStocks() {
    return stocks;
  }

  public void setStocks(List<Stock> stocks) {
    this.stocks = stocks;
  }
}
