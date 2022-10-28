package repository;

import constants.Constants;
import csv.IPortfolioCSVWriter;
import csv.PortfolioCSVWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import model.Portfolio;

public class CSVPortfolioRepository implements IRepository<Portfolio> {

  private final IPortfolioCSVWriter writer;

  public CSVPortfolioRepository() {
    this.writer = new PortfolioCSVWriter();
  }

  @Override
  public Portfolio create(Portfolio item) {
    try (FileOutputStream fileOutputStream = new FileOutputStream(
            Paths.get(Constants.DATA_DIR, item.getName()).toFile())) {
      return this.writer.writeRecords(item, fileOutputStream);
    } catch (IOException e) {

    }

    return item;
  }

  @Override
  public List<Portfolio> read(Predicate<Portfolio> predicate) {
    return null;
  }

  @Override
  public Portfolio update(Portfolio item) {
    try (FileOutputStream fileOutputStream = new FileOutputStream(
            Paths.get(Constants.DATA_DIR, item.getName()).toFile(), true)) {
      return this.writer.writeRecords(item, fileOutputStream);
    } catch (IOException e) {

    }

    return null;
  }
}
