package repository;

import constants.CSVConstants;
import constants.Constants;
import csv.ICSVWriter;
import csv.PortfolioCSVWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.Portfolio;
import utilities.StringUtils;

public class CSVPortfolioRepository implements IRepository<Portfolio> {

  private final ICSVWriter<Portfolio> writer;

  public CSVPortfolioRepository() {
    this.writer = new PortfolioCSVWriter();
  }

  @Override
  public Portfolio create(Portfolio portfolio) {
    String portFolioName = portfolio.getName();

    try {
      Files.createDirectories(Paths.get(Constants.DATA_DIR));

      if (getFilePath(portFolioName).toFile().exists()) {
        throw new IllegalArgumentException("A portfolio with this name does not exist.");
      }

      try (FileOutputStream fileOutputStream = new FileOutputStream(
          getFilePath(portfolio.getName()).toFile())) {
        this.writer.writeHeader(portfolio, fileOutputStream);
      }
    } catch (IOException e) {
    }

    return portfolio;
  }

  @Override
  public List<Portfolio> read(Predicate<Portfolio> predicate) throws IOException {
    try (Stream<Path> paths = Files.walk(Paths.get(Constants.DATA_DIR))) {
      return paths.filter(path -> {
        Portfolio portfolio = mapPathToPortfolio(path);

        return predicate.test(portfolio);
      }).findFirst().map(this::mapPathToPortfolio).stream().collect(Collectors.toList());
    }
  }

  private Portfolio mapPathToPortfolio(Path path) {
    String fileName = StringUtils.getFileNameWithoutExtension(path.getFileName().toString());

    Portfolio portfolio = new Portfolio();
    portfolio.setName(fileName);

    return portfolio;
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
        this.writer.writeRecords(portfolio, fileOutputStream);
      }
    } catch (IOException e) {
    }

    return portfolio;
  }

  private Path getFilePath(String name) throws IOException {
    return Paths.get(Constants.DATA_DIR, name + CSVConstants.EXTENSION);
  }
}
