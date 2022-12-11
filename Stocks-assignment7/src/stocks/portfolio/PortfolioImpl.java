package stocks.portfolio;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * This class implements the Portfolio interface as an Inflexible portfolio which does not allow
 * any changes to be made to it after creation.
 */
public class PortfolioImpl extends AbstractPortfolio {
  private final Map<String, String> stocks;

  /**
   * This constructor creates a new Portfolio with a given name from a file which contains tickers
   * for different companies along with the number of shares for that company.
   *
   * @param name     the name of the Portfolio
   * @param filePath the path of the file which contains information about tickers and the number
   *                 of shares per company
   * @throws IllegalArgumentException when the file path given does not have a .csv or .xml file
   * @throws RuntimeException         when a file is not found at the given path
   */
  public PortfolioImpl(String name, String filePath) throws IllegalArgumentException,
          RuntimeException {
    super(name);
    String fileType = filePath.substring(filePath.length() - 3);
    switch (fileType) {
      case "csv":
        this.stocks = parseCSV(filePath);
        this.removeInvalidTickers();
        this.removeFractionalShares();
        break;
      case "xml":
        this.stocks = parseXML(filePath);
        this.removeInvalidTickers();
        this.removeFractionalShares();
        break;
      default:
        throw new IllegalArgumentException("The filetype is not supported.");
    }
  }

  /**
   * This constructor creates a new Portfolio with a given name containing stocks stored as
   * (Ticker, Number of Shares) key-value pairs in a Map.
   *
   * @param name   name of the portfolio being created
   * @param stocks the Map containing all the stocks
   */
  public PortfolioImpl(String name, Map<String, String> stocks) {
    super(name);
    this.stocks = stocks;
    this.removeFractionalShares();
    this.removeInvalidTickers();
  }

  /**
   * Gets all the stocks contained in this portfolio.
   */
  @Override
  public Map<String, String> viewStocks(String date) {
    return stocks;
  }

  /**
   * Calculates the total number of shares in this portfolio.
   *
   * @return total number of shares in this portfolio
   */
  @Override
  public double getNumberOfShares(String date) {
    double total = 0;
    for (Map.Entry<String, String> mapEntry : stocks.entrySet()) {
      total += Double.parseDouble(mapEntry.getValue());
    }
    return total;
  }

  @Override
  public void buyStocks(Integer shares, String ticker, String date, Double commsionFee) {
    throw new RuntimeException("Cannot buy stocks for an Inflexible portfolio.");
  }

  @Override
  public void sellStocks(Integer shares, String ticker, String date, Double commsionFee) {
    throw new RuntimeException("Cannot sell stocks for an Inflexible portfolio.");
  }

  @Override
  public Double costBasis(String date) {
    throw new RuntimeException("Cannot get cost basis for an Inflexible portfolio.");
  }

  @Override
  public Map<LocalDate, Double> portfolioPerformance(String timeStamp,
                                                     String startDate, String endDate) {
    throw new RuntimeException("Please use getValue for the date you want to see performance for");
  }

  @Override
  public void investPortfolio(double totalInvestment, Map<String, Double> stocksPercent,
                              String date) {
    throw new RuntimeException("This method is not supported for an inflexible portfolio!");

  }

  @Override
  public void dollarCostAverage(Integer frequency, String startDate, String endDate,
                                double totalInvestment, Map<String, Double> stocksPercent) {
    throw new RuntimeException("This method is not supported for an inflexible portfolio.");
  }

  @Override
  public void savePortfolio() {
    String currentPath = Paths.get("").toAbsolutePath().toString();
    File savedFile = new File(currentPath, this.getName() + ".csv");
    BufferedWriter writer = null;
    try {
      writer = new BufferedWriter(new FileWriter(savedFile));
      writer.write("ticker,shares");
      writer.newLine();
      for (Map.Entry<String, String> mapEntry : stocks.entrySet()) {
        writer.write(mapEntry.getKey() + "," + mapEntry.getValue());
        writer.newLine();
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Helper method to parse a CSV portfolio file used to create a PortfolioImpl object.
   *
   * @param filePath path of the CSV file which contains the portfolio information
   * @return a list of Pairs containing the ticker and the number of shares for that company
   */
  private Map<String, String> parseCSV(String filePath) throws RuntimeException {
    Map<String, String> stocks = new HashMap<>();
    File f = new File(filePath);
    if (!f.exists()) {
      throw new RuntimeException("File does not exist.");
    }
    try {
      BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
      String row = csvReader.readLine();
      if (!row.split(",")[0].equalsIgnoreCase("ticker")
              || !row.split(",")[1].equalsIgnoreCase("Shares")) {
        throw new RuntimeException("Invalid file format. CSV files must have 2 headers with "
                + "name Ticker and Shares.");
      }
      while (row != null) {
        String ticker = row.split(",")[0];
        String numberOfShares = row.split(",")[1];
        if (!stocks.containsKey(ticker)) {
          if (!ticker.equalsIgnoreCase("ticker")) {
            stocks.put(ticker, numberOfShares);
          }
        } else {
          stocks.put(ticker, String.valueOf(Double.parseDouble(stocks.get(ticker)) +
                  Double.parseDouble(numberOfShares)));
        }
        row = csvReader.readLine();
      }
      csvReader.close();
    } catch (IOException e) {
      throw new RuntimeException("Something went wrong. Please retry.");
    }
    return stocks;
  }

  /**
   * Helper method to get stocks from an XML portfolio file used to create a PortfolioImpl object
   * using the XMLHelper class to parse.
   *
   * @param filePath path of the XML file which contains the portfolio information
   * @return a list of Pairs containing the ticker and the number of shares for that company
   */
  private Map<String, String> parseXML(String filePath) {
    Map<String, String> stocks;
    File f = new File(filePath);
    if (!f.exists()) {
      throw new RuntimeException("File does not exist.");
    }
    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    try {
      SAXParser saxParser = saxParserFactory.newSAXParser();
      XMLHandler handler = new XMLHandler();
      saxParser.parse(new File(filePath), handler);
      stocks = handler.getStocks();
    } catch (ParserConfigurationException | IOException | SAXException e) {
      throw new RuntimeException(e);
    }
    return stocks;
  }

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

  protected void removeFractionalShares() {
    for (Map.Entry<String, String> mapEntry : stocks.entrySet()) {
      Double newValue = Math.floor(Double.parseDouble(mapEntry.getValue()));
      mapEntry.setValue(String.valueOf(newValue.intValue()));
    }
  }

  /**
   * Handler class for parsing XML portfolio files.
   */
  static class XMLHandler extends DefaultHandler {

    static class Pair {
      String ticker;
      String numberOfShares;

      private void setTicker(String x) {
        this.ticker = x;
      }

      private void setNumberOfShares(String x) {
        this.numberOfShares = x;
      }
    }

    private Map<String, String> stocks;
    StringBuilder data = new StringBuilder();
    Pair p;

    /**
     * Returns list of stocks after they have been parsed by the handler.
     *
     * @return list of pairs of stocks from XML file
     */
    public Map<String, String> getStocks() {
      return stocks;
    }

    /**
     * Creates a new list when the document starts.
     */
    @Override
    public void startDocument() {
      stocks = new HashMap<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

      data.setLength(0);

      if (qName.equalsIgnoreCase("stock")) {
        p = new Pair();
      }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {


      if (qName.equalsIgnoreCase("ticker")) {
        p.setTicker(data.toString());
      }

      if (qName.equalsIgnoreCase("shares")) {
        p.setNumberOfShares(data.toString());
      }

      if (qName.equalsIgnoreCase("stock")) {
        stocks.put(p.ticker, p.numberOfShares);
      }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
      data.append(new String(ch, start, length));
    }

  }

}
