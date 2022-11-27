package model;

import enums.StrategyTypes;
import java.util.List;
import utilities.Pair;

public class InvestmentStrategy {

  private List<Pair<String, Double>> stockWeightPairs;
  private String strategyName;
  private StrategyTypes strategyType;
  private double strategyInvestment;
  private String strategyStartDate;
  private String strategyEndDate;
  private int strategyPeriod;
  private double commission;

  public InvestmentStrategy(List<Pair<String, Double>> stockWeightPairs) {
    this.stockWeightPairs = stockWeightPairs;
  }

  public String getStrategyName() {
    return strategyName;
  }

  public void setStrategyName(String strategyName) {
    this.strategyName = strategyName;
  }

  public StrategyTypes getStrategyType() {
    return strategyType;
  }

  public void setStrategyType(StrategyTypes strategyType) {
    this.strategyType = strategyType;
  }

  public double getStrategyInvestment() {
    return strategyInvestment;
  }

  public void setStrategyInvestment(double strategyInvestment) {
    this.strategyInvestment = strategyInvestment;
  }

  public String getStrategyStartDate() {
    return strategyStartDate;
  }

  public void setStrategyStartDate(String strategyStartDate) {
    this.strategyStartDate = strategyStartDate;
  }

  public String getStrategyEndDate() {
    return strategyEndDate;
  }

  public void setStrategyEndDate(String strategyEndDate) {
    this.strategyEndDate = strategyEndDate;
  }

  public int getStrategyPeriod() {
    return strategyPeriod;
  }

  public void setStrategyPeriod(int strategyPeriod) {
    this.strategyPeriod = strategyPeriod;
  }

  public List<Pair<String, Double>> getStockWeightPairs() {
    return stockWeightPairs;
  }

  public void setStockWeightPairs(
      List<Pair<String, Double>> stockWeightPairs) {
    this.stockWeightPairs = stockWeightPairs;
  }

  public double getCommission() {
    return commission;
  }

  public void setCommission(double commission) {
    this.commission = commission;
  }
}
