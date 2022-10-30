package repository;

import constants.CSVConstants;
import constants.Constants;
import io.IReader;
import io.IWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import model.Portfolio;
import model.Stock;
import utilities.StringUtils;

public class CSVPortfolioRepository implements IRepository<Portfolio> {

  private final IWriter<Portfolio> writer;
  private final IReader<Portfolio, String, Stock> reader;

  public CSVPortfolioRepository(IReader<Portfolio, String, Stock> reader,
      IWriter<Portfolio> writer) {
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
          portfolio.getName()).toFile());
          ByteArrayOutputStream bos = new ByteArrayOutputStream();
          ObjectOutputStream oos = new ObjectOutputStream(
              bos)) {
        oos.writeObject(portfolio);
        oos.flush();
        this.writer.write(bos.toByteArray(), fileOutputStream);
      }
    } catch (IOException e) {
    }

    return portfolio;
  }

  @Override
  public List<Portfolio> read(Predicate<Portfolio> predicate) throws IOException {
    try (Stream<Path> paths = Files.walk(Paths.get(Constants.DATA_DIR))) {
      Stream<Path> filteredPaths = paths.filter(path -> {
        Portfolio portfolio = mapPathToPortfolio(path);

        return predicate.test(portfolio);
      });

      filteredPaths.forEach(path -> {
        try (FileInputStream inputStream = new FileInputStream(path.toAbsolutePath().toString())) {
          this.reader.read(inputStream);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      });
    }

    return null;
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

  private Portfolio mapPathToPortfolio(Path path) {
    String fileName = StringUtils.getFileNameWithoutExtension(path.getFileName().toString());

    Portfolio portfolio = new Portfolio();
    portfolio.setName(fileName);

    return portfolio;
  }
}
