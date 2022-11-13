package model;

import constants.Constants;
import enums.Operations;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import repository.IRepository;
import service.IStockService;
import utilities.DateUtils;
import utilities.Pair;
import utilities.StringUtils;

public class FlexiblePortfolioModel extends PortfolioModel implements IFlexiblePortfolioModel {

  /**
   * Constructs a {@link PortfolioModel} object.
   *
   * @param portfolioRepository the portfolio repository which is used the write the data to the
   *                            actual datastore
   * @param stockService        the stock service which is used to fetch the stocks
   */
  public FlexiblePortfolioModel(IRepository<Portfolio> portfolioRepository,
      IStockService stockService) {
    super(portfolioRepository, stockService);
  }

  @Override
  public void buyStock(String portfolioName, Pair<String, Double> stockPair, String date, Double commission)
      throws IOException, IllegalArgumentException {
    super.validateInput(portfolioName);
    super.validateInput(stockPair.getKey());

    super.isStockSymbolValid(stockPair.getKey());

    if (stockPair.getValue() <= 0) {
      throw new IllegalArgumentException(Constants.QUANTITY_NON_NEGATIVE_AND_ZERO);
    }

    // cannot buy before IPO - comes from api
    if (StringUtils.isNullOrWhiteSpace(date)) {
      date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }
    super.validateDate(date);

    Portfolio portfolio = new Portfolio();
    portfolio.setName(portfolioName);

    Stock stock = Stock
        .StockBuilder
        .create()
        .setSymbol(stockPair.getKey())
        .setQuantity(stockPair.getValue())
        .setDate(date)
        .setOperation(Operations.BUY)
        .setCommission(commission);
    portfolio.setStocks(new ArrayList<>(Collections.singletonList(stock)));

    super.portfolioRepository.update(portfolio);
  }

  @Override
  public void sellStock(String portfolioName, Pair<String, Double> stockPair, String date, Double commission)
      throws IOException, IllegalArgumentException {
    super.validateInput(portfolioName);
    super.validateInput(stockPair.getKey());

    super.isStockSymbolValid(stockPair.getKey());

    if (stockPair.getValue() <= 0) {
      throw new IllegalArgumentException(Constants.QUANTITY_NON_NEGATIVE_AND_ZERO);
    }

    // 1)check for chronology and 2) is quantity sufficiency to sell
    if (StringUtils.isNullOrWhiteSpace(date)) {
      date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }
    super.validateDate(date);

    Portfolio portfolio = new Portfolio();
    portfolio.setName(portfolioName);

    Stock stock = Stock
        .StockBuilder
        .create()
        .setSymbol(stockPair.getKey())
        .setQuantity(stockPair.getValue())
        .setDate(date)
        .setOperation(Operations.SELL)
        .setCommission(commission);
    portfolio.setStocks(new ArrayList<>(Collections.singletonList(stock)));

    super.portfolioRepository.update(portfolio);
  }

  @Override
  public Pair<Portfolio, Double> getCostBasis(String portfolioName, String date)
      throws IOException {
    super.validateInput(portfolioName);

    if (StringUtils.isNullOrWhiteSpace(date)) {
      date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }
    super.validateDate(date);

    Iterable<Portfolio> portfolios = super.portfolioRepository.read(
        x -> x.getName().equals(portfolioName));
    Portfolio portfolio = portfolios.iterator().next();

    double value = 0;
    for (Stock stock : portfolio.getStocks()) {
      stock.setClose(this.stockService.getStockOnDate(stock.getSymbol(), date)
          .getClose());

      switch (stock.getOperation()) {
        case BUY:
          // change high to commission fees
          value += (stock.getClose() * stock.getQuantity()) + stock.getCommission();
        case SELL:
          value += stock.getCommission();
      }
    }

    return new Pair<>(portfolio, value);
  }

  @Override
  public void getPerformanceOverView(Portfolio portfolio) {

  }
}
