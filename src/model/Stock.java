package model;

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
   * @param symbol
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
   * @param open
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
   * @param high
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
   * @param low
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
   * @param close
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
   * @param volume
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
   * @param date
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
   * @param quantity
   * @return the {@link Stock} object with the updated value
   */
  public Stock setQuantity(double quantity) {
    this.quantity = quantity;

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
