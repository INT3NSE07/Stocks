package repository;

import constants.CSVConstants;
import constants.Constants;
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
 *
 */
public class CSVPortfolioRepository implements IRepository<Portfolio> {

  private final IWriter<List<String>> writer;
  private final IReader<List<List<String>>> reader;

  private final String path;

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

    if (getFilePath(portFolioName).toFile().exists()) {
      throw new IllegalArgumentException(Constants.PORTFOLIO_EXISTS);
    }

    try (FileOutputStream fileOutputStream = new FileOutputStream(getFilePath(
        portfolio.getName()).toFile())) {
      this.writer.write(Arrays.asList(CSVConstants.STOCK_CSV_HEADERS), fileOutputStream);
    }

    return portfolio;
  }

  @Override
  public List<Portfolio> read(Predicate<Portfolio> predicate)
      throws IllegalArgumentException, IOException {
    List<Portfolio> portfolios = new ArrayList<>();
    Function<List<String>, Stock> mapper = MapperUtils.getUserPortfolioToStockMapper();

    List<Path> filteredPaths;
    try (Stream<Path> paths = Files.walk(Paths.get(this.path))) {
      filteredPaths = paths.filter(path -> {
        Portfolio portfolio = mapPathToPortfolio(path);

        return predicate.test(portfolio);
      }).collect(Collectors.toList());
    }

    for (Path path : filteredPaths) {
      Portfolio portfolio = mapPathToPortfolio(path);

      try (FileInputStream inputStream = new FileInputStream(path.toAbsolutePath().toString())) {
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
    Portfolio updatedPortfolio;

    Files.createDirectories(Paths.get(this.path));

    if (!getFilePath(portFolioName).toFile().exists()) {
      throw new IllegalArgumentException(Constants.PORTFOLIO_DOES_NOT_EXIST);
    }

    try (FileOutputStream fileOutputStream = new FileOutputStream(
        getFilePath(portfolio.getName()).toFile(), true)) {
      for (Stock stock : portfolio.getStocks()) {
        List<String> record = Arrays.asList(stock.getSymbol(),
            Double.toString(stock.getQuantity()), stock.getDate());
        this.writer.write(record, fileOutputStream);
      }
    }

    updatedPortfolio = this.read(x -> x.getName().equals(portFolioName)).get(0);

    return updatedPortfolio;
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
