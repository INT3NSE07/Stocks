package model;

import java.time.LocalDate;

/**
 * This class that holds the performance related metrics across a time frame.
 */
public class PortfolioValue {

  private final LocalDate fromDate;

  private final LocalDate toDate;

  private final double value;

  /**
   * Creates an instance of {@link PortfolioValue}.
   *
   * @param fromDate the date from which the performance is calculated
   * @param toDate   the date till which the performance is calculated
   * @param value    the calculated value
   */
  public PortfolioValue(LocalDate fromDate, LocalDate toDate, double value) {
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.value = value;
  }

  /**
   * Return the date from which the performance is calculated.
   *
   * @return the date from which the performance is calculated.
   */
  public LocalDate getFromDate() {
    return fromDate;
  }

  /**
   * Return the date till which the performance is calculated.
   *
   * @return the date till which the performance is calculated.
   */
  public LocalDate getToDate() {
    return toDate;
  }

  /**
   * Return the computed value.
   *
   * @return the computed value
   */
  public Double getValue() {
    return value;
  }

}
