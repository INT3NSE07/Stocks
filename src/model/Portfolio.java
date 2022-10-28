package model;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

  public Portfolio() {
    this.stocks = new ArrayList<>();
  }

  private String name;

  private List<Stock> stocks;

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
