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
import java.util.stream.Stream;
import model.Portfolio;
import model.Stock;
import utilities.MapperUtils;
import utilities.StringUtils;

public class CSVPortfolioRepository implements IRepository<Portfolio> {

  private final IWriter<List<String>> writer;
  private final IReader<List<List<String>>> reader;

  public CSVPortfolioRepository(IReader<List<List<String>>> reader,
      IWriter<List<String>> writer) {
    this.reader = reader;
    this.writer = writer;
  }

  @Override
  public Portfolio create(Portfolio portfolio) {
    String portFolioName = portfolio.getName();

    try {
      Files.createDirectories(Paths.get(Constants.DATA_DIR));

      if (getFilePath(portFolioName).toFile().exists()) {
        throw new IllegalArgumentException("A portfolio with this name does not exist.");
      }

      try (FileOutputStream fileOutputStream = new FileOutputStream(getFilePath(
          portfolio.getName()).toFile())) {
        this.writer.write(Arrays.asList(CSVConstants.STOCK_CSV_HEADERS), fileOutputStream);
      }
    } catch (IOException e) {
    }

    return portfolio;
  }

  @Override
  public List<Portfolio> read(Predicate<Portfolio> predicate) throws IOException {
    List<Portfolio> portfolios = new ArrayList<>();
    Function<List<String>, Stock> mapper = MapperUtils.getUserPortfolioToStockMapper();

    try (Stream<Path> paths = Files.walk(Paths.get(Constants.DATA_DIR))) {
      Stream<Path> filteredPaths = paths.filter(path -> {
        Portfolio portfolio = mapPathToPortfolio(path);

        return predicate.test(portfolio);
      });

      filteredPaths.forEach(path -> {
        Portfolio portfolio = mapPathToPortfolio(path);

        try (FileInputStream inputStream = new FileInputStream(path.toAbsolutePath().toString())) {
          this.reader.read(inputStream).forEach(record -> {
            Stock stock = mapper.apply(record);
            portfolio.getStocks().add(stock);
          });

          portfolios.add(portfolio);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      });
    }

    return portfolios;
  }

  @Override
  public Portfolio update(Portfolio portfolio) {
    String portFolioName = portfolio.getName();

    try {
      Files.createDirectories(Paths.get(Constants.DATA_DIR));

      if (!getFilePath(portFolioName).toFile().exists()) {
        throw new IllegalArgumentException("A portfolio with this name does not exist.");
      }

      try (FileOutputStream fileOutputStream = new FileOutputStream(
          getFilePath(portfolio.getName()).toFile(), true)) {
        for (Stock stock : portfolio.getStocks()) {
          List<String> record = Arrays.asList(stock.getSymbol(),
              Double.toString(stock.getQuantity()), stock.getDate());
          this.writer.write(record, fileOutputStream);
        }
      }
    } catch (IOException e) {
    }

    return portfolio;
  }

  private Path getFilePath(String name) throws IOException {
    return Paths.get(Constants.DATA_DIR, name + CSVConstants.EXTENSION);
  }

  private Portfolio mapPathToPortfolio(Path path) {
    String fileName = StringUtils.getFileNameWithoutExtension(path.getFileName().toString());

    Portfolio portfolio = new Portfolio();
    portfolio.setName(fileName);

    return portfolio;
  }
}
