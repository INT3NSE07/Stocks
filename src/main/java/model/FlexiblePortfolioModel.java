package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import constants.Constants;
import enums.Operation;
import repository.IRepository;
import service.IStockService;
import utilities.Pair;

public class FlexiblePortfolioModel extends PortfolioModel implements IFlexiblePortfolioModel  {

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
  public void buyStock(String portfolioName, Pair<String, Double> stockPair, String date) throws IOException {
    super.validateInput(portfolioName);
    super.validateInput(stockPair.getKey());
    super.isStockSymbolValid(stockPair.getKey());
    if(stockPair.getValue() < 0) {
      throw new IllegalArgumentException(Constants.QUANTITY_NON_NEGATIVE_AND_ZERO);
    }
    // cannot buy future stocks
    super.validateDate(date);

    Portfolio portfolio= new Portfolio();
    portfolio.setName(portfolioName);
    Stock stock = Stock
                  .StockBuilder
                  .create()
                  .setSymbol(stockPair.getKey())
                  .setQuantity(stockPair.getValue())
                  .setDate(date)
                  .setOperation(Operation.operations.BUY);
    portfolio.setStocks(new ArrayList<>(Collections.singletonList(stock)));
    super.portfolioRepository.update(portfolio);
  }

  @Override
  public void sellStock(String portfolioName, Pair<String, Double> stockPair, String date) throws IOException {
    super.validateInput(portfolioName);
    super.validateInput(stockPair.getKey());
    super.isStockSymbolValid(stockPair.getKey());
    if(stockPair.getValue() < 0) {
      throw new IllegalArgumentException(Constants.QUANTITY_NON_NEGATIVE_AND_ZERO);
    }
    super.validateDate(date);
    // 1)check for chronology and 2) is quantity sufficiency to sell

    Portfolio portfolio= new Portfolio();
    portfolio.setName(portfolioName);
    Stock stock = Stock
            .StockBuilder
            .create()
            .setSymbol(stockPair.getKey())
            .setQuantity(stockPair.getValue())
            .setDate(date);
    portfolio.setStocks(new ArrayList<>(Collections.singletonList(stock)));
    super.portfolioRepository.update(portfolio);

  }

  @Override
  public double calCommission() {
    // Ask for commission rate per transaction
    // well then what should be constant in commission fee.
    return 0;
  }

  @Override
  public double costBasis(Portfolio portfolio) {
    // costBasis = value of stocks bought + calCommission for only these bought transactions.
     super.validateInput(portfolio.getName());
//    Iterable<Portfolio> portfolios = super.portfolioRepository
//             .read(x -> x.getName().getOperation().equals("Buy"));

    return 0;
  }

  @Override
  public void getPerformanceOverView(Portfolio portfolio) {

  }
}
