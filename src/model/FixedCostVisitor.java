package model;

import constants.Constants;
import enums.Operations;
import enums.StrategyTypes;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import repository.IRepository;
import service.IStockService;
import utilities.DateUtils;
import utilities.Pair;
import utilities.StringUtils;

public class FixedCostVisitor<T> implements IPortfolioInvestmentStrategyVisitor<T> {

  @Override
  public T applyStrategy(FlexiblePortfolioModel portfolioModel,
      String portfolioName,
      InvestmentStrategy investmentStrategy) throws IOException {
    IStockService stockService = portfolioModel.getStockService();
    IRepository<Portfolio> portfolioRepository = portfolioModel.getRepository();

    if (investmentStrategy == null) {
      throw new IllegalArgumentException(Constants.INPUT_INVALID);
    }

    portfolioModel.validateInput(portfolioName);

    Portfolio portfolio = new Portfolio();
    portfolio.setName(portfolioName);

    String date = investmentStrategy.getStrategyStartDate();
    if (StringUtils.isNullOrWhiteSpace(date)) {
      date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
      investmentStrategy.setStrategyStartDate(date);
    }
    portfolioModel.validateDate(date);

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

    DecimalFormat df = new DecimalFormat("0.00");
    String strategyName = StringUtils.getRandomString(6);

    Map<String, Double> uniqueStockPairs = investmentStrategy.getStockWeightPairs().stream()
        .collect(Collectors.groupingBy(Pair::getKey, Collectors.summingDouble(Pair::getValue)));
    for (String symbol : uniqueStockPairs.keySet()) {
      double weight = uniqueStockPairs.get(symbol);
      double stockInvestment = strategyInvestment * (weight / 100);

      Stock stock = stockService.getStockOnDate(symbol, date);
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
          .setStrategyType(StrategyTypes.FIXED_AMOUNT)
          .setStrategyInvestment(strategyInvestment)
          .setWeight(weight)
          .setStrategyName(strategyName);

      portfolio.addStocks(new ArrayList<>(Collections.singletonList(newStock)));
      portfolioRepository.update(portfolio);
      portfolio.getStocks().clear();
    }

    return null;
  }
}
