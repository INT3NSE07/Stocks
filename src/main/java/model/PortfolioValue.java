package model;

import java.time.LocalDate;

public class PortfolioValue {

  private LocalDate fromDate;

  private LocalDate toDate;

  private double value;

  public PortfolioValue() {

  }

  public PortfolioValue(LocalDate fromDate, LocalDate toDate, double value) {
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.value = value;
  }

  public LocalDate getFromDate() {
    return fromDate;
  }

  void setFromDate(LocalDate fromDate) {
    this.fromDate = fromDate;
  }

  public LocalDate getToDate() {
    return toDate;
  }

  void setToDate(LocalDate toDate) {
    this.toDate = toDate;
  }

  public Double getValue() {
    return value;
  }

  void setValue(Double value) {
    this.value = value;
  }
}
