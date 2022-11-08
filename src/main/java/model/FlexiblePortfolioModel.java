package model;

import java.util.List;
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
  public void buyStock(String portFolioName, List<Pair<String, Double>> stockPairs, String Date) {

  }

  @Override
  public double sellStock(String portfolioName, List<Pair<String, Double>> stockPair, String date) {
    return 0;
  }

  @Override
  public double calCommission() {
    return 0;
  }

  @Override
  public double costBasis() {
    return 0;
  }

  @Override
  public void getPerformanceOverView(Portfolio portfolio) {

  }
}
