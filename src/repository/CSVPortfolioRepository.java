package repository;

import constants.CSVConstants;
import constants.Constants;
import enums.PortfolioTypes;
import enums.StrategyTypes;
import io.IReader;
import io.IWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.Portfolio;
import model.Stock;
import utilities.MapperUtils;
import utilities.StringUtils;

/**
 * A CSVPortfolio implementation of {@link IRepository}.
 * <ul>
 * <li>Creates CSV File for a {@link Portfolio}.</li>
 * <li>Reads File to Fetch {@link Portfolio}.</li>
 * <li>Updates a {@link Portfolio} object.</li>
 * </ul>
 */
public class CSVPortfolioRepository implements IRepository<Portfolio> {

  private final IWriter<List<String>> writer;
  private final IReader<List<List<String>>> reader;

  private final String path;

  /**
   * A constructor for {@link CSVPortfolioRepository} to initialize {@link IWriter}, {@link IReader}
   * , path to File Systems operations.
   *
   * @param reader {@link IReader} object of type List of (List of) Strings.
   * @param writer {@link IWriter} object of type List of (List of) Strings.
   * @param path   Path to the Files to perform file operations.
   */
  public CSVPortfolioRepository(IReader<List<List<String>>> reader,
      IWriter<List<String>> writer, String path) {
    this.reader = reader;
    this.writer = writer;
    this.path = path;
  }

  @Override
  public Portfolio create(Portfolio portfolio) throws IllegalArgumentException, IOException {
    String portFolioName = portfolio.getName();
    Files.createDirectories(Paths.get(this.path));

    Path portfolioMapping = getFilePath(Constants.PORTFOLIO_MAPPING_PATH);
    if (!portfolioMapping.toFile().exists()) {
      Files.createFile(portfolioMapping);
      try (FileOutputStream fileOutputStream = new FileOutputStream(portfolioMapping.toFile(),
          true)) {
        this.writer.write(Arrays.asList(CSVConstants.PORTFOLIO_CSV_HEADERS), fileOutputStream);
      }
    }

    if (getFilePath(portFolioName).toFile().exists()) {
      throw new IllegalArgumentException(Constants.PORTFOLIO_EXISTS);
    }

    try (FileOutputStream fileOutputStream = new FileOutputStream(getFilePath(
        portfolio.getName()).toFile(), true)) {
      if (portfolio.getPortfolioType() == PortfolioTypes.INFLEXIBLE) {
        this.writer.write(Arrays.asList(CSVConstants.INFLEXIBLE_PORTFOLIO_STOCK_CSV_HEADERS),
            fileOutputStream);
      } else if (portfolio.getPortfolioType() == PortfolioTypes.FLEXIBLE) {
        this.writer.write(Arrays.asList(CSVConstants.FLEXIBLE_PORTFOLIO_STOCK_CSV_HEADERS),
            fileOutputStream);
      }
    }

    try (FileOutputStream fileOutputStream = new FileOutputStream(portfolioMapping.toFile(),
        true)) {
      this.writer.write(Arrays.asList(portFolioName, portfolio.getPortfolioType().toString()),
          fileOutputStream);
    }

    return portfolio;
  }

  @Override
  public List<Portfolio> read(Predicate<Portfolio> predicate)
      throws IllegalArgumentException, IOException {
    List<Portfolio> portfolios = new ArrayList<>();
    Function<List<String>, Stock> mapper = MapperUtils.getUserPortfolioToStockMapper();
    Function<List<String>, Portfolio> portfolioMapper = MapperUtils.getCSVFileToPortfolioMapper();
    Path portfolioMapping = getFilePath(Constants.PORTFOLIO_MAPPING_PATH);

    List<Path> filteredPaths;
    try (Stream<Path> paths = Files.walk(Paths.get(this.path))) {
      filteredPaths = paths.filter(path -> {
        Portfolio portfolio = mapPathToPortfolio(path);

        return predicate.test(portfolio);
      }).collect(Collectors.toList());
    }

    for (Path path : filteredPaths) {
      Portfolio portfolio = mapPathToPortfolio(path);

      try (FileInputStream inputStream = new FileInputStream(path.toAbsolutePath().toString());
          FileInputStream portfolioInputStream = new FileInputStream(
              portfolioMapping.toAbsolutePath().toFile())) {
        this.reader.read(portfolioInputStream).forEach(record -> {
          Portfolio mappedPortfolio = portfolioMapper.apply(record);
          if (mappedPortfolio.getName().equals(portfolio.getName())) {
            portfolio.setPortfolioType(mappedPortfolio.getPortfolioType());
          }
        });

        if (portfolio.getPortfolioType() == null) {
          portfolio.setPortfolioType(PortfolioTypes.INFLEXIBLE);
        }

        this.reader.read(inputStream).forEach(record -> {
          Stock stock = mapper.apply(record);
          portfolio.getStocks().add(stock);
        });

        portfolios.add(portfolio);
      }
    }

    if (portfolios.isEmpty()) {
      throw new IllegalArgumentException(Constants.PORTFOLIO_DOES_NOT_EXIST);
    }

    return portfolios;
  }

  @Override
  public Portfolio update(Portfolio portfolio) throws IllegalArgumentException, IOException {
    String portFolioName = portfolio.getName();

    if (!getFilePath(portFolioName).toFile().exists()) {
      throw new IllegalArgumentException(Constants.PORTFOLIO_DOES_NOT_EXIST);
    }

    try (FileOutputStream fileOutputStream = new FileOutputStream(
        getFilePath(portfolio.getName()).toFile(), true)) {
      for (Stock stock : portfolio.getStocks()) {
        List<String> record = new ArrayList<>(Arrays.asList(stock.getSymbol(),
            Double.toString(stock.getQuantity()), stock.getDate()));

        // flexible portfolio
        if (stock.getOperation() != null) {
          record.add(stock.getOperation().toString());
          record.add(Double.toString(stock.getCommission()));
          record.add(Double.toString(stock.getWeight()));
          record.add(stock.getStrategyName());

          StrategyTypes strategyType = stock.getStrategyType();
          if (strategyType != null) {
            record.add(stock.getStrategyType().toString());
          }
          record.add(Double.toString(stock.getStrategyInvestment()));
          record.add(stock.getStrategyEndDate());
          record.add(Integer.toString(stock.getStrategyPeriod()));
        }
        this.writer.write(record, fileOutputStream);
      }
    }

    return portfolio;
  }

  private Path getFilePath(String name) {
    return Paths.get(this.path, name + CSVConstants.EXTENSION);
  }

  private Portfolio mapPathToPortfolio(Path path) {
    String fileName = StringUtils.getFileNameWithoutExtension(path.getFileName().toString());

    Portfolio portfolio = new Portfolio();
    portfolio.setName(fileName);

    return portfolio;
  }
}
