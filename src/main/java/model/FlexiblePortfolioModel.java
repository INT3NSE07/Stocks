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
  public Portfolio readPortfolio(String portfolioName, String date)
      throws IllegalArgumentException, IOException {
    if (StringUtils.isNullOrWhiteSpace(date)) {
      date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }
    this.validateDate(date);

    Portfolio portfolio = super.readPortfolio(portfolioName);

    String finalDate = date;
    List<Stock> filteredStocks = portfolio.getStocks().stream()
        .filter(x -> LocalDate.parse(x.getDate()).compareTo(LocalDate.parse(finalDate)) <= 0)
        .collect(
            Collectors.toList());
    portfolio.setStocks(filteredStocks);

    return portfolio;
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

    LocalDate portfolioCreationDate = portfolio.getStocks().stream()
        .map(x -> LocalDate.parse(x.getDate()))
        .min(LocalDate::compareTo).orElse(null);
    if (portfolioCreationDate != null && LocalDate.parse(date).isBefore(portfolioCreationDate)) {
      throw new IllegalArgumentException(
          String.format("\nValue of the portfolio %s on %s is %.2f", portfolio.getName(), date,
              value));
    }

    String finalDate = date;
    List<Stock> filteredStocks = portfolio.getStocks().stream()
        .filter(x -> LocalDate.parse(x.getDate()).compareTo(LocalDate.parse(finalDate)) <= 0)
        .collect(
            Collectors.toList());
    portfolio.setStocks(filteredStocks);

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

    if (stockPair.getValue() <= 0) {
      throw new IllegalArgumentException(Constants.QUANTITY_NON_NEGATIVE_AND_ZERO);
    }

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

    if (stockPair.getValue() <= 0) {
      throw new IllegalArgumentException(Constants.QUANTITY_NON_NEGATIVE_AND_ZERO);
    }

    if (StringUtils.isNullOrWhiteSpace(date)) {
      date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }
    super.validateDate(date);

    Portfolio validPortfolio = super.readPortfolio(portfolioName);

    String symbol = stockPair.getKey();
    double quantity = stockPair.getValue();
    List<Stock> allTransactions = validPortfolio.getStocks().stream()
        .filter(x -> x.getSymbol().equals(symbol)).collect(Collectors.toList());

    String finalDate = date;
    List<Stock> transactionsBeforeDate = allTransactions.stream()
        .filter(x -> LocalDate.parse(x.getDate()).compareTo(LocalDate.parse(finalDate)) <= 0)
        .collect(Collectors.toList());

    if (allTransactions.size() == 0 && transactionsBeforeDate.size() == 0) {
      throw new IllegalArgumentException(
          String.format("No purchases of stock %s before date %s found.", symbol, date));
    }

    double buyQuantity =
        transactionsBeforeDate.stream().filter(x -> x.getOperation() == Operations.BUY)
            .mapToDouble(Stock::getQuantity).sum();
    double sellQuantity =
        transactionsBeforeDate.stream().filter(x -> x.getOperation() == Operations.SELL)
            .mapToDouble(Stock::getQuantity).sum();
    double availableQuantity = buyQuantity - sellQuantity;
    if (availableQuantity < quantity) {
      throw new IllegalArgumentException(
          String.format(
              "The portfolio %s does not contain %.2f of %s to sell on date %s.",
              portfolioName, quantity, symbol, date));
    }

    double totalBuyQuantity =
        allTransactions.stream().filter(x -> x.getOperation() == Operations.BUY)
            .mapToDouble(Stock::getQuantity).sum();
    double totalSellQuantity =
        allTransactions.stream().filter(x -> x.getOperation() == Operations.SELL)
            .mapToDouble(Stock::getQuantity).sum();
    double totalAvailableQuantity = totalBuyQuantity - totalSellQuantity;
    if (totalAvailableQuantity < quantity) {
      throw new IllegalArgumentException(
          String.format(
              "This transaction cannot be performed because selling %.2f stocks of %s "
                  + "on date %s in portfolio %s invalidates a previous transaction. ",
              quantity, symbol, date, portfolioName));
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
  public double getCostBasis(String portfolioName, String date)
      throws IOException {
    super.validateInput(portfolioName);

    if (StringUtils.isNullOrWhiteSpace(date)) {
      date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }
    super.validateDate(date);

    Iterable<Portfolio> portfolios = super.portfolioRepository.read(
        x -> x.getName().equals(portfolioName));
    Portfolio portfolio = portfolios.iterator().next();

    Map<String, Double> stockValueMap = new HashMap<>();
    List<String> uniqueStockSymbols = portfolio.getStocks().stream().map(Stock::getSymbol)
        .distinct()
        .collect(Collectors.toList());
    for (String symbol : uniqueStockSymbols) {
      stockValueMap.put(symbol, this.stockService.getStockOnDate(symbol, date)
          .getClose());
    }

    double value = 0;
    for (Stock stock : portfolio.getStocks()) {
      stock.setClose(stockValueMap.get(stock.getSymbol()));

      switch (stock.getOperation()) {
        case BUY:
          value += (stock.getClose() * stock.getQuantity()) + stock.getCommission();
          break;
        case SELL:
          value += stock.getCommission();
          break;
      }
    }

    return value;
  }

  @Override
  public List<PortfolioValue> getPerformanceOverview(String portfolioName, String fromDate,
      String toDate)
      throws IOException {
    super.validateInput(portfolioName);

    if (StringUtils.isNullOrWhiteSpace(fromDate)) {
      throw new IllegalArgumentException(Constants.DATE_INVALID);
    }
    super.validateDate(fromDate);

    if (StringUtils.isNullOrWhiteSpace(toDate)) {
      toDate = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }
    super.validateDate(toDate);

    LocalDate from = LocalDate.parse(fromDate, Constants.DEFAULT_DATETIME_FORMAT);
    LocalDate to = LocalDate.parse(toDate, Constants.DEFAULT_DATETIME_FORMAT);
    if (to.compareTo(from) < 0) {
      throw new IllegalArgumentException(Constants.DATE_INVALID);
    }

    long days = ChronoUnit.DAYS.between(from, to) + 1;
    int splits = Constants.BAR_CHART_MAX_LINES;

    if (days < Constants.BAR_CHART_MIN_LINES) {
      throw new IllegalArgumentException(Constants.BAR_CHART_MIN_DAYS_INPUT);
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
