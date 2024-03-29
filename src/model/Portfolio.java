package model;

import enums.PortfolioTypes;
import java.util.ArrayList;
import java.util.List;

/**
 * A Portfolio class that holds the Name, List of Stocks and operations that can be performed on the
 * Portfolio object.
 */
public class Portfolio {

  private final List<Stock> stocks;
  private String name;
  private PortfolioTypes portfolioType;

  /**
   * A {@link Portfolio} object constructor that assigns the List of Stocks to new Empty List.
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

  public void addStocks(List<Stock> stocks) {
    this.stocks.addAll(stocks);
  }

  public PortfolioTypes getPortfolioType() {
    return portfolioType;
  }

  public void setPortfolioType(PortfolioTypes portfolioType) {
    this.portfolioType = portfolioType;
  }
}
