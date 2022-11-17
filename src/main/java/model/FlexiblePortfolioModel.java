package model;

import constants.Constants;
import enums.Operations;
import enums.PortfolioTypes;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import repository.IRepository;
import service.IStockService;
import utilities.DateUtils;
import utilities.Helpers;
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
  public void createPortfolio(String portfolioName, List<Pair<String, Double>> stockPairs)
      throws IllegalArgumentException, IOException {
    this.validateInput(portfolioName);

    Portfolio portfolio = new Portfolio();
    portfolio.setName(portfolioName);
    portfolio.setPortfolioType(PortfolioTypes.FLEXIBLE);

    portfolioRepository.create(portfolio);
  }

  @Override
  public Pair<Portfolio, Double> getPortfolioValueOnDate(String portfolioName, String date)
      throws IllegalArgumentException, IOException {
    this.validateInput(portfolioName);

    if (StringUtils.isNullOrWhiteSpace(date)) {
      date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }
    this.validateDate(date);

    Portfolio portfolio = this.readPortfolio(portfolioName);
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

    if(!super.isStockSymbolValid(stockPair.getKey())) {
      throw new IllegalArgumentException(String.format(Constants.SYMBOL_FETCH_FAIL, stockPair.getKey()));
    }

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
    transactions = validPortfolio.getStocks().stream()
        .filter(x -> x.getSymbol().equals(stockPair.getKey())
            && LocalDate.parse(x.getDate()).isBefore(LocalDate.parse(finalDate))
            && x.getOperation().equals(Operations.BUY)
        ).collect(Collectors.toList());

    if (transactions.size() == 0) {
      throw new IllegalArgumentException(
          "No Purchases of Stock: " + stockPair.getKey() + "before date: " + date);
    }

    List<Stock> Alltransactions = validPortfolio.getStocks().stream()
        .filter(x -> x.getSymbol().equals(stockPair.getKey())
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
  public List<PortfolioValue> getPerformanceOverview(String portfolioName, String fromDate,
      String toDate)
      throws IOException {
    super.validateInput(portfolioName);

    super.validateDate(fromDate);
    if (StringUtils.isNullOrWhiteSpace(toDate)) {
      toDate = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }
    super.validateDate(toDate);

    LocalDate from = LocalDate.parse(fromDate, Constants.DEFAULT_DATETIME_FORMAT);
    LocalDate to = LocalDate.parse(toDate, Constants.DEFAULT_DATETIME_FORMAT);
    long days = ChronoUnit.DAYS.between(from, to) + 1;
    int splits = Constants.BAR_CHART_MAX_LINES;

    if (days < Constants.BAR_CHART_MIN_LINES) {
      throw new IllegalArgumentException();
    } else if (days <= Constants.BAR_CHART_MAX_LINES) {
      splits = (int) days;
    }

    List<PortfolioValue> portfolioValues = new ArrayList<>();

    List<Long> splitDays = Helpers.splitValue(days, splits);
    List<Pair<String, String>> dateRanges = new ArrayList<>();
    dateRanges.add(new Pair<>(fromDate, LocalDate.parse(fromDate).plusDays(splitDays.get(0))
        .format(Constants.DEFAULT_DATETIME_FORMAT)));

    for (int i = 1; i < splitDays.size(); i++) {
      String prevDate = dateRanges.get(i - 1).getValue();
      String startDate = LocalDate.parse(prevDate).plusDays(1)
          .format(Constants.DEFAULT_DATETIME_FORMAT);

      dateRanges.add(new Pair<>(startDate, LocalDate.parse(startDate).plusDays(splitDays.get(i))
          .format(Constants.DEFAULT_DATETIME_FORMAT)));
    }

    Portfolio portfolio = this.portfolioRepository.read(
        x -> x.getName().equals(portfolioName)).iterator().next();
    List<Stock> stocks = portfolio.getStocks();
    List<String> symbols = stocks.stream().map(Stock::getSymbol).distinct()
        .collect(Collectors.toList());

    Map<String, List<Stock>> stockData = new HashMap<>();
    for (String symbol : symbols) {
      stockData.put(symbol, this.stockService.getHistoricalStockData(symbol));
    }

    for (Pair<String, String> dateRange : dateRanges) {
      LocalDate startDate = LocalDate.parse(dateRange.getKey(), Constants.DEFAULT_DATETIME_FORMAT);
      LocalDate endDate = LocalDate.parse(dateRange.getValue(),
          Constants.DEFAULT_DATETIME_FORMAT);
      List<Stock> stocksBetweenRange = stocks.stream()
          .filter(x -> endDate.compareTo(LocalDate.parse(x.getDate())) >= 0)
          .collect(Collectors.toList());

      Map<String, Double> stockQuantityMap = new HashMap<>();
      for (Stock stock : stocksBetweenRange) {
        String symbol = stock.getSymbol();
        switch (stock.getOperation()) {
          case BUY:
            if (stockQuantityMap.containsKey(symbol)) {
              stockQuantityMap.put(symbol, stockQuantityMap.get(symbol) + stock.getQuantity());
            } else {
              stockQuantityMap.put(symbol, stock.getQuantity());
            }
            break;
          case SELL:
            stockQuantityMap.put(symbol, stockQuantityMap.get(symbol) - stock.getQuantity());
            break;
        }
      }

      double value = 0;
      for (String stockSymbol : stockQuantityMap.keySet()) {
        Stock stockOnEndDate = stockData.get(stockSymbol).stream()
            .filter(x -> x.getDate().equals(endDate.format(Constants.DEFAULT_DATETIME_FORMAT)))
            .findFirst().orElse(null);
        if (stockOnEndDate == null) {
          stockOnEndDate = stockData.get(stockSymbol).stream()
              .filter(x -> LocalDate.parse(x.getDate())
                  .isBefore(LocalDate.parse(endDate.format(Constants.DEFAULT_DATETIME_FORMAT))))
              .findFirst().orElse(null);
        }

        if (stockOnEndDate != null) {
          value += stockOnEndDate.getClose() * stockQuantityMap.get(stockSymbol);
        }
      }

      portfolioValues.add(new PortfolioValue(startDate, endDate, value));
    }

    return portfolioValues;
  }
}
