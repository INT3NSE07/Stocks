package model;

/**
 * A Stock class that holds the Symbol, quantity, date, open, close, high, low, volume
 *                  and operations that can be performed on the Stock object.
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

  public String getSymbol() {
    return symbol;
  }

  public Stock setSymbol(String symbol) {
    this.symbol = symbol.toUpperCase();

    return this;
  }

  public double getOpen() {
    return open;
  }

  public Stock setOpen(double open) {
    this.open = open;

    return this;
  }

  public double getHigh() {
    return high;
  }

  public Stock setHigh(double high) {
    this.high = high;

    return this;
  }

  public double getLow() {
    return low;
  }

  public Stock setLow(double low) {
    this.low = low;

    return this;
  }

  public double getClose() {
    return close;
  }

  public Stock setClose(double close) {
    this.close = close;

    return this;
  }

  public double getVolume() {
    return volume;
  }

  public Stock setVolume(double volume) {
    this.volume = volume;

    return this;
  }

  public String getDate() {
    return date;
  }

  public Stock setDate(String date) {
    this.date = date;

    return this;
  }

  public double getQuantity() {
    return quantity;
  }

  public Stock setQuantity(double quantity) {
    this.quantity = quantity;

    return this;
  }

  /**
   * A Stock Builder Class to Create new Stock objects.
   */
  public static class StockBuilder {

    public static Stock create() {
      return new Stock();
    }
  }
}
