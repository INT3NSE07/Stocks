package model;

import enums.PortfolioType;
import java.util.ArrayList;
import java.util.List;

public class Portfolio {

  public Portfolio() {
    this.stocks = new ArrayList<>();
  }

  private String name;

  private PortfolioType portfolioType;

  private List<Stock> stocks;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PortfolioType getPortfolioType() {
    return portfolioType;
  }

  public void setPortfolioType(PortfolioType portfolioType) {
    this.portfolioType = portfolioType;
  }

  public List<Stock> getStocks() {
    return stocks;
  }

  public void setStocks(List<Stock> stocks) {
    this.stocks = stocks;
  }
}
