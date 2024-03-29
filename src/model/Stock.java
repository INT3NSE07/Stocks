package model;

import enums.Operations;
import enums.StrategyTypes;

/**
 * This class represents a stock and holds information related to it.
 */
public class Stock {

  private String symbol;
  private double quantity;
  private String date;
  private double open;
  private double high;
  private double low;
  private double close;
  private double volume;
  private Operations operation;
  private double commission;

  // properties to support portfolio strategy
  private double weight;
  private String strategyName;
  private StrategyTypes strategyType;
  private double strategyInvestment;
  private String strategyEndDate;
  private int strategyPeriod;

  private Stock() {
  }

  /**
   * Get the stock symbol.
   *
   * @return the stock symbol
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Get the stock symbol.
   *
   * @param symbol the stock symbol
   * @return the {@link Stock} object with the updated value
   */
  public Stock setSymbol(String symbol) {
    this.symbol = symbol.toUpperCase();

    return this;
  }

  /**
   * Get the stock open.
   *
   * @return the stock open
   */
  public double getOpen() {
    return open;
  }

  /**
   * Set the stock open.
   *
   * @param open the opening value of the stock
   * @return the {@link Stock} object with the updated value
   */
  public Stock setOpen(double open) {
    this.open = open;

    return this;
  }

  /**
   * Get the stock high.
   *
   * @return the stock high
   */
  public double getHigh() {
    return high;
  }

  /**
   * Set the stock high.
   *
   * @param high the high value of the stock
   * @return the {@link Stock} object with the updated value
   */
  public Stock setHigh(double high) {
    this.high = high;

    return this;
  }

  /**
   * Get the stock low.
   *
   * @return the stock low
   */
  public double getLow() {
    return low;
  }

  /**
   * Set the stock low.
   *
   * @param low the low value of the stock
   * @return the {@link Stock} object with the updated value
   */
  public Stock setLow(double low) {
    this.low = low;

    return this;
  }

  /**
   * Get the stock close.
   *
   * @return the stock symbol
   */
  public double getClose() {
    return close;
  }

  /**
   * Set the stock close.
   *
   * @param close the closing value of the stock
   * @return the {@link Stock} object with the updated value
   */
  public Stock setClose(double close) {
    this.close = close;

    return this;
  }

  /**
   * Get the stock volume.
   *
   * @return the stock volume
   */
  public double getVolume() {
    return volume;
  }

  /**
   * Set the stock volume.
   *
   * @param volume the volume of the stock being traded
   * @return the {@link Stock} object with the updated value
   */
  public Stock setVolume(double volume) {
    this.volume = volume;

    return this;
  }

  /**
   * Get the stock date.
   *
   * @return the stock symbol
   */
  public String getDate() {
    return date;
  }

  /**
   * Set the stock date.
   *
   * @param date the date on which this stock was bought
   * @return the {@link Stock} object with the updated value
   */
  public Stock setDate(String date) {
    this.date = date;

    return this;
  }

  /**
   * Get the stock quantity.
   *
   * @return the stock symbol
   */
  public double getQuantity() {
    return quantity;
  }

  /**
   * Set the stock quantity.
   *
   * @param quantity the quantity of this stock bought
   * @return the {@link Stock} object with the updated value
   */
  public Stock setQuantity(double quantity) {
    this.quantity = quantity;

    return this;
  }

  /**
   * Get the stock operation performed on it.
   *
   * @return the operation performed on it.
   */
  public Operations getOperation() {
    return operation;
  }

  /**
   * Set the stock operation performed on it.
   *
   * @param operation the operation that was performed on this stock.
   * @return the {@link Stock} object with the updated value
   */
  public Stock setOperation(Operations operation) {
    this.operation = operation;
    return this;
  }

  /**
   * Get the stock commission set tp perform operation on stock.
   *
   * @return the commission value performed on it.
   */
  public double getCommission() {
    return commission;
  }

  /**
   * Set the stock commission set tp perform operation on stock.
   *
   * @param commission the commission that should be set for the operation to be performed on this
   *                   stock.
   * @return the {@link Stock} object with the updated value
   */
  public Stock setCommission(double commission) {
    this.commission = commission;
    return this;
  }

  /**
   * Get the weight of the stock.
   *
   * @return the weight of the stock.
   */
  public double getWeight() {
    return weight;
  }

  /**
   * Set the weight of the stock.
   *
   * @param weight the weight of the stock.
   * @return the {@link Stock} object with the updated value
   */
  public Stock setWeight(double weight) {
    this.weight = weight;

    return this;
  }

  /**
   * Get the strategy name.
   *
   * @return the strategy name.
   */
  public String getStrategyName() {
    return strategyName;
  }

  /**
   * Set the strategy name.
   *
   * @param strategyName the strategy name.
   * @return the {@link Stock} object with the updated value
   */
  public Stock setStrategyName(String strategyName) {
    this.strategyName = strategyName;

    return this;
  }

  /**
   * Get the strategy type.
   *
   * @return the strategy type.
   */
  public StrategyTypes getStrategyType() {
    return strategyType;
  }

  /**
   * Set the strategy type.
   *
   * @param strategyType the strategy type.
   * @return the {@link Stock} object with the updated value
   */
  public Stock setStrategyType(StrategyTypes strategyType) {
    this.strategyType = strategyType;

    return this;
  }

  /**
   * Get the strategy investment.
   *
   * @return the strategy investment.
   */
  public double getStrategyInvestment() {
    return strategyInvestment;
  }

  /**
   * Set the strategy investment.
   *
   * @param strategyInvestment the strategy investment.
   * @return the {@link Stock} object with the updated value
   */
  public Stock setStrategyInvestment(double strategyInvestment) {
    this.strategyInvestment = strategyInvestment;

    return this;
  }

  /**
   * Get the strategy end date.
   *
   * @return the strategy end date.
   */
  public String getStrategyEndDate() {
    return strategyEndDate;
  }

  /**
   * Set the strategy end date.
   *
   * @param strategyEndDate the strategy end date.
   * @return the {@link Stock} object with the updated value
   */
  public Stock setStrategyEndDate(String strategyEndDate) {
    this.strategyEndDate = strategyEndDate;

    return this;
  }

  /**
   * Get the strategy period.
   *
   * @return the stock strategy period.
   */
  public int getStrategyPeriod() {
    return strategyPeriod;
  }

  /**
   * Set the strategy period.
   *
   * @param strategyPeriod the strategy period.
   * @return the {@link Stock} object with the updated value
   */
  public Stock setStrategyPeriod(int strategyPeriod) {
    this.strategyPeriod = strategyPeriod;

    return this;
  }


  /**
   * A Stock builder class to create new {@link Stock} objects.
   */
  public static class StockBuilder {

    public static Stock create() {
      return new Stock();
    }
  }
}
