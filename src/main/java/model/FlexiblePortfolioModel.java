package model;

import constants.Constants;
import enums.Operations;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
  public Pair<Portfolio, Double> getPortfolioValueOnDate(String portFolioName, String date)
      throws IllegalArgumentException, IOException {
    this.validateInput(portFolioName);

    if (StringUtils.isNullOrWhiteSpace(date)) {
      date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }
    this.validateDate(date);

    Portfolio portfolio = this.readPortfolio(portFolioName);
    double value = 0;

    // filter the earliest date present in the portfolio
    // if given date < earliest data - show error

    for (Stock stock : portfolio.getStocks()) {
      stock.setClose(this.stockService.getStockOnDate(stock.getSymbol(), date)
          .getClose());

      switch (stock.getOperation()) {
        case BUY:
          value += (stock.getQuantity() * stock.getClose());
          break;
        case SELL:
          value -= (stock.getQuantity() * stock.getClose());
          break;
      }
    }

    return new Pair<>(portfolio, value);
  }

  @Override
  public void buyStock(String portfolioName, Pair<String, Double> stockPair, String date,
      double commission)
      throws IOException, IllegalArgumentException {
    super.validateInput(portfolioName);
    super.validateInput(stockPair.getKey());

    super.isStockSymbolValid(stockPair.getKey());

    if (stockPair.getValue() <= 0) {
      throw new IllegalArgumentException(Constants.QUANTITY_NON_NEGATIVE_AND_ZERO);
    }

    // cannot buy before IPO - comes from api - throws illegal arg catches up top in controller.
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
  public void sellStock(String portfolioName, Pair<String, Double> stockPair, String date,
      double commission)
      throws IOException, IllegalArgumentException {
    super.validateInput(portfolioName);
    super.validateInput(stockPair.getKey());

    super.isStockSymbolValid(stockPair.getKey());

    if (stockPair.getValue() <= 0) {
      throw new IllegalArgumentException(Constants.QUANTITY_NON_NEGATIVE_AND_ZERO);
    }

    if (StringUtils.isNullOrWhiteSpace(date)) {
      date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }

    super.validateDate(date);
    // select only flexible csv - not her in upper levels
    // 1) check for chronology and
    // 2) is quantity sufficiency to sell
    Portfolio validPortfolio = super.readPortfolio(portfolioName);
    List<Stock> transactions = null;

    String finalDate = date;
    transactions = validPortfolio.getStocks().stream().filter(x -> x.getSymbol().equals(stockPair.getKey())
            && LocalDate.parse(x.getDate()).isBefore(LocalDate.parse(finalDate))
            && x.getOperation().equals(Operations.BUY)
            ).collect(Collectors.toList());

    if (transactions.size() == 0) {
      throw new IllegalArgumentException("No Purchases of Stock: " + stockPair.getKey() + "before date: " + date);
    }


    List<Stock> Alltransactions = validPortfolio.getStocks().stream().filter(x -> x.getSymbol().equals(stockPair.getKey())
            && LocalDate.parse(x.getDate()).isBefore(LocalDate.parse(finalDate))
    ).collect(Collectors.toList());

    final int[] quantity = {0};
    Alltransactions.forEach(
            stock -> {
//              int quantity = 0;
              if (stock.getOperation().equals(Operations.BUY)) {
                quantity[0] += stock.getQuantity();
              }
              if (stock.getOperation().equals(Operations.SELL)) {
                quantity[0] -= stock.getQuantity();
              }
            }
    );
    if (quantity[0] < stockPair.getValue()) {
      throw new IllegalArgumentException("Not enough sufficient quantity of Stocks to sell");
    }

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
          value += (stock.getClose() * stock.getQuantity()) + stock.getCommission();
          break;
        case SELL:
          value += stock.getCommission();
          break;
      }
    }

    return new Pair<>(portfolio, value);
  }

  @Override
  public void getPerformanceOverview(String portfolioName, String fromDate, String toDate) {
    super.validateInput(portfolioName);

    super.validateDate(fromDate);
    if (StringUtils.isNullOrWhiteSpace(toDate)) {
      toDate = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }
    super.validateDate(toDate);

    LocalDate from = LocalDate.parse(fromDate, Constants.DEFAULT_DATETIME_FORMAT);
    LocalDate to = LocalDate.parse(toDate, Constants.DEFAULT_DATETIME_FORMAT);
    long days = ChronoUnit.DAYS.between(from, to);
    int splits = Constants.BAR_CHART_MAX_LINES;

    if (days < Constants.BAR_CHART_MIN_LINES) {
      throw new IllegalArgumentException();
    } else if (days <= Constants.BAR_CHART_MAX_LINES) {
      splits = (int) days;
    }
    //Pair<Pair<LocalDate, LocalDate>, Double>

    List<Long> splitDays = getSplitDays(days, splits);
    // readportfolio
    // distinct symbols, getStock(symbol from, to)


    var x = 10;

  }

  private List<Long> getSplitDays(long days, int splits) {
    List<Long> splitDays = new ArrayList<>();

    if (days % splits == 0) {
      for (int i = 0; i < splits; i++) {
        splitDays.add(days / splits);
      }
    } else {
      long maxDivisibleNumber = splits - (days % splits);
      long nonDivisibleNumber = days / splits;
      for (int i = 0; i < splits; i++) {
        if (i >= maxDivisibleNumber) {
          splitDays.add(Long.sum(nonDivisibleNumber, 1));
        } else {
          splitDays.add(nonDivisibleNumber);
        }
      }
    }

    return splitDays;
  }
}
