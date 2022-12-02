package model;

import constants.Constants;
import enums.Operations;
import enums.StrategyTypes;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import repository.IRepository;
import service.IStockService;
import utilities.DateUtils;
import utilities.Pair;
import utilities.StringUtils;

/**
 * Apply the dollar cost averaging investment strategy to a portfolio.
 *
 * @param <T> the return type of the strategy
 */
public class DollarCostAveragingVisitor<T> implements IPortfolioInvestmentStrategyVisitor<T> {

  private IStockService stockService;
  private final boolean shouldCreateStrategy;

  public DollarCostAveragingVisitor(boolean shouldCreateStrategy) {
    this.shouldCreateStrategy = shouldCreateStrategy;
  }

  @Override
  public T applyStrategy(FlexiblePortfolioModel portfolioModel,
      String portfolioName,
      InvestmentStrategy investmentStrategy) throws IOException {
    this.stockService = portfolioModel.getStockService();
    IRepository<Portfolio> portfolioRepository = portfolioModel.getRepository();

    if (investmentStrategy == null) {
      throw new IllegalArgumentException(Constants.INPUT_INVALID);
    }

    portfolioModel.validateInput(portfolioName);

    Portfolio portfolio = new Portfolio();
    portfolio.setName(portfolioName);

    String startDate = investmentStrategy.getStrategyStartDate();
    portfolioModel.validateDate(startDate);

    String endDate = investmentStrategy.getStrategyEndDate();
    if (StringUtils.isNullOrWhiteSpace(endDate)) {
      endDate = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }
    if (!DateUtils.isValidDate(endDate, Constants.DEFAULT_DATETIME_FORMAT)) {
      throw new IllegalArgumentException(Constants.DATE_INVALID);
    }

    LocalDate currentDate = LocalDate.parse(
        DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT));
    LocalDate from = LocalDate.parse(startDate, Constants.DEFAULT_DATETIME_FORMAT);
    LocalDate to = LocalDate.parse(endDate, Constants.DEFAULT_DATETIME_FORMAT);

    if (!this.stockService.isTradingDay(from.format(Constants.DEFAULT_DATETIME_FORMAT))) {
      from = getClosestTradingDay(from, 1);
    }

    if (to.compareTo(currentDate) <= 0 && !this.stockService.isTradingDay(
        to.format(Constants.DEFAULT_DATETIME_FORMAT))) {
      to = getClosestTradingDay(to, 1);
    }

    if (to.compareTo(from) < 0) {
      throw new IllegalArgumentException(Constants.DATE_INVALID);
    }

    double commission = investmentStrategy.getCommission();
    if (commission < 0) {
      throw new IllegalArgumentException(String.format(Constants.NON_NEGATIVE, "Commission"));
    }

    double strategyInvestment = investmentStrategy.getStrategyInvestment();
    if (strategyInvestment < 0) {
      throw new IllegalArgumentException(String.format(Constants.NON_NEGATIVE, "Total investment"));
    }

    double percentage = investmentStrategy.getStockWeightPairs()
        .stream().mapToDouble(Pair::getValue).sum();
    if (percentage != 100) {
      throw new IllegalArgumentException(Constants.WEIGHT_INVALID);
    }

    int period = investmentStrategy.getStrategyPeriod();
    if (period < 0) {
      throw new IllegalArgumentException(String.format(Constants.NON_NEGATIVE, "Period"));
    }

    DecimalFormat df = new DecimalFormat("0.00");

    String strategyName;
    if (investmentStrategy.getStrategyName() != null) {
      strategyName = investmentStrategy.getStrategyName();
    } else {
      strategyName = StringUtils.getRandomString(6);
    }

    if (this.shouldCreateStrategy) {
      portfolioModel.createPortfolio(portfolioName, new ArrayList<>());
    }

    Map<String, Double> uniqueStockPairs = investmentStrategy.getStockWeightPairs().stream()
        .collect(Collectors.groupingBy(Pair::getKey, Collectors.summingDouble(Pair::getValue)));

    List<String> dates = new ArrayList<>();
    dates.add(from.format(Constants.DEFAULT_DATETIME_FORMAT));
    while (from.compareTo(to) <= 0) {
      try {
        from = getClosestTradingDay(from, period);
      } catch (IllegalArgumentException e) {
        break;
      }

      if (!from.isAfter(to)) {
        dates.add(from.format(Constants.DEFAULT_DATETIME_FORMAT));
      }
    }

    for (String symbol : uniqueStockPairs.keySet()) {
      double weight = uniqueStockPairs.get(symbol);
      double stockInvestment = strategyInvestment * (weight / 100);

      for (String date : dates) {
        Stock stock = this.stockService.getStockOnDate(symbol, date);
        double stockQuantity = Double.parseDouble(
            df.format(stockInvestment / stock.getClose()));

        Stock newStock = Stock
            .StockBuilder
            .create()
            .setSymbol(symbol)
            .setQuantity(stockQuantity)
            .setDate(date)
            .setOperation(Operations.BUY)
            .setCommission(commission)
            .setStrategyType(StrategyTypes.DOLLAR_COST_AVERAGING)
            .setStrategyInvestment(strategyInvestment)
            .setStrategyEndDate(endDate)
            .setWeight(weight)
            .setStrategyName(strategyName)
            .setStrategyPeriod(period);

        portfolio.addStocks(new ArrayList<>(Collections.singletonList(newStock)));
        portfolioRepository.update(portfolio);
        portfolio.getStocks().clear();
      }
    }

    return null;
  }

  private LocalDate getClosestTradingDay(LocalDate date, int period)
      throws IOException, IllegalArgumentException {
    int count = 0;
    while (count < period) {
      date = date.plusDays(1);
      if (this.stockService.isTradingDay(date.format(Constants.DEFAULT_DATETIME_FORMAT))) {
        count++;
      }
    }

    return date;
  }
}
