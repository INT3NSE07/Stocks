package stocks.portfolio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class implements the Portfolio interface as a flexible portfolio which does allow users to
 * purchase for it or sell stocks that already exist in it after creation.
 */
public class FlexiblePortfolio extends AbstractPortfolio {

  private Map<String, Map<String, String>> stocks;
  private double costBasis;
  private Map<String, Double> costBasisMap;
  private final String date;

  /**
   * This constructor creates a new  Flexible Portfolio with a given name from a file which contains
   * tickers for different companies along with the number of shares for that company.
   *
   * @param name     the name of the Portfolio
   * @param filePath the path of the file which contains information about tickers and the number of
   *                 shares per company
   * @throws IllegalArgumentException when the file path given does not have a .csv or .xml file
   * @throws RuntimeException         when a file is not found at the given path
   */
  public FlexiblePortfolio(String name, String filePath, String date, Double commission)
      throws IllegalArgumentException,
      RuntimeException, IOException {
    super(name);
    String fileType = filePath.substring(filePath.length() - 3);
    this.costBasisMap = new HashMap<>();
    this.date = date;
    switch (fileType) {
      case "csv":
        this.stocks = parseCSV(filePath);
        this.removeInvalidTickers();
        //this.removeFractionalShares();
        calculateCostBasis(commission);
        break;
      case "xml":
        throw new IllegalArgumentException("The filetype xml is not supported.");
      default:
        throw new IllegalArgumentException("The filetype is not supported.");
    }
  }

  /**
   * This constructor creates a new  Flexible Portfolio with a given name containing stocks stored
   * as (Ticker, Number of Shares) key-value pairs in a Map.
   *
   * @param name   name of the portfolio being created
   * @param stocks the Map containing all the stocks
   */
  public FlexiblePortfolio(String name, Map<String, Map<String, String>> stocks) {
    super(name);
    this.stocks = stocks;
    //this.removeFractionalShares();
    this.removeInvalidTickers();
    this.costBasis = 0;
    this.costBasisMap = new HashMap<String, Double>();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    this.date = dtf.format(LocalDate.now()).toString();
    calculateCostBasis(10.0);
  }

  @Override
  public Map<String, String> viewStocks(String date) {
    Map<String, String> todaysComposition = new HashMap<>();
    for (Map.Entry<String, Map<String, String>> mapEntry : stocks.entrySet()) {
      Map<String, String> innerMap = mapEntry.getValue();
      List<LocalDate> sortedDates = sortDates(mapEntry.getKey());
      LocalDate lDate = LocalDate.parse(date);
      for (int i = sortedDates.size() - 1; i >= 0; i--) {
        if (sortedDates.get(i).isBefore(lDate) || sortedDates.get(i).isEqual(lDate)) {
          todaysComposition.put(mapEntry.getKey(), innerMap.get(sortedDates.get(i).toString()));
          break;
        }
      }
    }
    return todaysComposition;
  }

  @Override
  protected void removeInvalidTickers() {
    List<String> validTickers;
    String filePath = "validTickers.csv";
    try {
      InputStream inputStream = getClass().getResourceAsStream(filePath);
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
      BufferedReader br = new BufferedReader(inputStreamReader);
      validTickers = new ArrayList<>();
      for (String line; (line = br.readLine()) != null; ) {
        validTickers.add(line);
      }
      stocks.keySet().removeIf(key -> (!validTickers.contains(key)));
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  protected void removeFractionalShares() {
    for (Map.Entry<String, Map<String, String>> mapEntry : stocks.entrySet()) {
      Map<String, String> innerMap = mapEntry.getValue();
      for (Map.Entry<String, String> entry : innerMap.entrySet()) {
        Double newValue = Math.floor(Double.parseDouble(entry.getValue()));
        entry.setValue(String.valueOf(newValue.intValue()));
      }
    }
  }

  @Override
  public void savePortfolio() {
    String currentPath = Paths.get("").toAbsolutePath().toString();
    File savedFile = new File(currentPath, this.getName() + ".csv");
    BufferedWriter writer = null;
    try {
      writer = new BufferedWriter(new FileWriter(savedFile));
      writer.write("ticker,date,shares");
      writer.newLine();
      for (Map.Entry<String, Map<String, String>> mapEntry : stocks.entrySet()) {
        Map<String, String> innerMap = mapEntry.getValue();
        for (Map.Entry<String, String> entry : innerMap.entrySet()) {
          writer.write(mapEntry.getKey() + "," + entry.getKey() + "," + entry.getValue());
          writer.newLine();
        }
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Helper method to use when we have a portfolio entry of a file .csv type.
   *
   * @param filePath where the file saved.
   * @return returns the map of stocks passed within the file.
   * @throws RuntimeException to avoid exceptions.
   */
  public Map<String, Map<String, String>> parseCSV(String filePath) throws RuntimeException {
    Map<String, Map<String, String>> stocks = new HashMap<>();
    File f = new File(filePath);
    if (!f.exists()) {
      throw new RuntimeException("File does not exist.");
    }
    Double cost = 0.0;
    try {
      BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
      String row = csvReader.readLine();
      if (!row.split(",")[0].equalsIgnoreCase("ticker")
          || !row.split(",")[1].equalsIgnoreCase("date")
          || !row.split(",")[2].equalsIgnoreCase("Shares")) {
        throw new RuntimeException("Invalid file format. CSV files must have 3 headers with "
            + "name Ticker, date and shares.");
      }
      while (row != null) {
        String ticker = row.split(",")[0];
        String numberOfShares = row.split(",")[2];
        String date = row.split(",")[1];
        if (!stocks.containsKey(ticker)) {
          if (!ticker.equalsIgnoreCase("ticker")) {
            stocks.put(ticker, new HashMap<>());
            stocks.get(ticker).put(date, numberOfShares);
          }
        } else {
          stocks.get(ticker).put(date, numberOfShares);
        }
        row = csvReader.readLine();
      }
      csvReader.close();
    } catch (IOException e) {
      throw new RuntimeException("Something went wrong. Please retry.");
    }
    return stocks;
  }

  @Override
  public double getNumberOfShares(String date) {
    double numShares = 0;
    for (String val : viewStocks(date).values()) {
      numShares += Double.parseDouble(val);
    }
    return numShares;
  }

  @Override
  public void buyStocks(Integer shares, String ticker, String date, Double commissionFee)
      throws IOException {

    costBasis += commissionFee;

    costBasis += getCostOfStock(ticker, date) * shares;
    costBasisMap.put(date, costBasis);
    Map<String, String> p = new HashMap<>();
    if (stocks.containsKey(ticker)) {
      Map<String, String> existingTicker = stocks.get(ticker);
      for (Map.Entry<String, String> mapEntry : existingTicker.entrySet()) {
        Integer newSharesNum = Integer.parseInt(mapEntry.getValue());
        shares += newSharesNum;
      }
      existingTicker.put(date, shares.toString());

    } else {
      p.put(date, shares.toString());
      this.stocks.put(ticker, p);
    }
  }

  @Override
  public void sellStocks(Integer shares, String ticker, String date, Double commissionFee)
      throws ParseException, RuntimeException {
    if (stocks.containsKey(ticker)) {
      costBasis += commissionFee;
      costBasisMap.put(date, costBasis);
      Map<String, String> existingTicker = stocks.get(ticker);
      List<LocalDate> datesSorted = sortDates(ticker);
      String sharesAvailble = existingTicker.
          get(datesSorted.get((datesSorted.size() - 1)).toString());
      if (shares <= Double.parseDouble(sharesAvailble)) {
        Double finalShares = Double.parseDouble(sharesAvailble) - shares;
        existingTicker.put(date, finalShares.toString());
        this.stocks.put(ticker, existingTicker);
      } else {
        throw new RuntimeException("No available shares to sell this amount!");
      }
    } else {
      throw new RuntimeException("No available stocks from this ticker!");
    }
  }


  private List<LocalDate> sortDates(String ticker) {
    Map<String, String> existingTicker = stocks.get(ticker);
    Set<String> dateSet = existingTicker.keySet();
    List<LocalDate> listDates = new ArrayList<LocalDate>();
    for (String d : dateSet) {
      LocalDate parsedDate = LocalDate.parse(d);
      listDates.add(parsedDate);
    }
    Collections.sort(listDates);
    return listDates;
  }

  @Override
  public Double costBasis(String date) {
    if (costBasisMap.containsKey(date)) {
      return costBasisMap.get(date);
    }
    Set<String> dateSet = costBasisMap.keySet();
    List<LocalDate> listDates = new ArrayList<>();
    for (String d : dateSet) {
      LocalDate parsedDate = LocalDate.parse(d);
      listDates.add(parsedDate);
    }
    LocalDate lDate = LocalDate.parse(date);
    Collections.sort(listDates);
    int i = 0;
    while (i < listDates.size() - 1) {
      if (listDates.get(i).isBefore(lDate)) {
        i++;
      } else {
        break;
      }
    }
    return costBasisMap.get(listDates.get(i).toString());
  }

  @Override
  public Map<LocalDate, Double> portfolioPerformance(String timeStamp,
      String startDate, String endDate)
      throws ParseException {
    LocalDate start = LocalDate.parse(startDate);
    LocalDate end = LocalDate.parse(endDate);
    Period diff = Period.between(start, end);
    Map<LocalDate, Double> values = new HashMap<>();
    LocalDate date = start;
    switch (timeStamp) {
      case "Daily":
        if (diff.getMonths() > 1 || diff.getYears() > 0) {
          throw new RuntimeException("Choose a bigger time stamp.");
        }
        while (date.isBefore(end)) {
          values.put(date, this.getValue(date.toString()));
          date = date.plusDays(1);
        }
        return values;
      case "Monthly":
        if ((diff.getYears() * 12) + diff.getMonths() > 30) {
          throw new RuntimeException("Please choose a bigger time stamp.");
        }
        values = new HashMap<>();
        date = start;
        while (date.isBefore(end)) {
          values.put(date, this.getValue(date.toString()));
          date = date.plusMonths(1);
        }
        return values;
      case "Yearly":
        if (diff.getYears() > 30) {
          throw new RuntimeException("Please try again with a shorter range");
        }
        values = new HashMap<>();
        date = start;
        while (date.isBefore(end)) {
          values.put(date, this.getValue(date.toString()));
          date = date.plusYears(1);
        }
        return values;
      default:
        throw new RuntimeException("Please enter a valid time stamp type");
    }
  }

  @Override
  public void investPortfolio(double totalInvestment, Map<String, Double> stocksPercent,
      String date) {
    for (String ticker : stocksPercent.keySet()) {
      Double investValue = totalInvestment * (stocksPercent.get(ticker) / 100.0);
      Double cost = getCostOfStock(ticker, date);
      if (cost <= 0) {
        throw new RuntimeException("The passed date is a holiday!");
      }
      Double sharesNumber = investValue / cost;

      if (this.stocks.containsKey(ticker)) {
        Map<String, String> existingTicker = stocks.get(ticker);
        for (Map.Entry<String, String> mapEntry : existingTicker.entrySet()) {
          Double oldSharesNum = Double.parseDouble(mapEntry.getValue());
          Double totalShares = oldSharesNum + sharesNumber;
          mapEntry.setValue(totalShares.toString());
          this.stocks.put(ticker, existingTicker);
        }
      } else {
        Map<String, String> newMap = new HashMap<>();
        newMap.put(date, (sharesNumber).toString());
        this.stocks.put(ticker, newMap);
      }
    }
  }

  @Override
  public void dollarCostAverage(Integer frequency, String startDate, String endDate,
      double totalInvestment, Map<String, Double> stocksPercent) {
    LocalDate start = LocalDate.parse(startDate);
    LocalDate end = LocalDate.parse(endDate);
    while (start.isBefore(end) || start.isEqual(end)) {
      try {
        this.investPortfolio(totalInvestment, stocksPercent, start.toString());
        start = start.plusDays(frequency);
      } catch (RuntimeException e) {
        start = start.plusDays(1);
      }

    }
  }

  @Override
  public <T> T accept(IPortfolioVisitor<T> visitor, String portfolioName,
      Map<String, Double> stockWeights, String date) {
    return visitor.apply(this, portfolioName, stockWeights, date, stocks);
  }

  private void calculateCostBasis(Double commission) {
    for (String ticker : stocks.keySet()) {
      List<LocalDate> sorted = sortDates(ticker);
      Double previousShares = 0.0;
      for (int i = 0; i < sorted.size(); i++) {
        LocalDate date = sorted.get(i);
        if (costBasisMap.containsKey(date.toString())) {
          if (previousShares > Double.valueOf(stocks.get(ticker).get(date.toString()))) {
            Double x = costBasisMap.get(date.toString());
            costBasisMap.put(date.toString(), x + commission);
          } else {
            Double x = costBasisMap.get(date.toString());
            x += commission;
            x += (Double.valueOf(stocks.get(ticker).get(date.toString())) - previousShares)
                * getCostOfStock(ticker, date.toString());
            costBasisMap.put(date.toString(), x);
          }
        } else {
          if (previousShares > Double.valueOf(stocks.get(ticker).get(date.toString()))) {
            LocalDate previousDate;
            if (i > 0) {
              previousDate = sorted.get(i - 1);
              Double value = costBasisMap.get(previousDate.toString());
              costBasisMap.put(date.toString(), value + commission);
            } else {
              costBasisMap.put(date.toString(), commission);
            }
          } else {
            costBasisMap.put(date.toString(), (Double.valueOf(stocks.get(ticker)
                .get(date.toString())) - previousShares)
                * getCostOfStock(ticker, date.toString()) + commission);
          }
        }
        previousShares = Double.valueOf(stocks.get(ticker).get(date.toString()));
      }
    }
  }


}
