package stocks.controller;

import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;

import stocks.controller.actionlisteners.AddShareWhileCreatingPortfolioListener;
import stocks.controller.actionlisteners.AddShareWhileInvesting;
import stocks.controller.actionlisteners.GetCostBasisListener;
import stocks.controller.actionlisteners.GetValueListener;
import stocks.controller.actionlisteners.GoBackListener;
import stocks.controller.actionlisteners.PerformDollarCostAveragingListener;
import stocks.controller.actionlisteners.SaveToFileListener;
import stocks.controller.actionlisteners.SellStockListener;
import stocks.controller.actionlisteners.SubmitInvestmentListener;
import stocks.controller.actionlisteners.ViewCompositionListener;
import stocks.controller.actionlisteners.ViewPerformanceListener;
import stocks.model.PortfolioModel;
import stocks.portfolio.Portfolio;
import stocks.view.PortfolioUIView;

import static org.junit.Assert.assertEquals;

/**
 * Test class for PortfolioUIController class.
 */
public class PortfolioUIControllerTest {

  PortfolioUIController controller;
  StringBuilder log;
  PortfolioUIView view;
  JDialog dialog;
  List<String> mockValidTickers;
  Map<String, String> mockStocks;

  static class MockView implements PortfolioUIView {

    public StringBuilder logger;

    public MockView(StringBuilder log) {
      this.logger = log;
    }

    @Override
    public void addFeatures(Features features) {
      logger.append("Adding features to the view ");
    }

    @Override
    public JDialog createBuyDialog(List<String> portfolioNames) {
      logger.append("Creating buy dialog ");
      return new JDialog();
    }

    @Override
    public void createErrorDialog(String errorMessage, JDialog parent) {
      logger.append("Creating error dialog ");
    }

    @Override
    public void createInformationDialog(String informationMessage, JDialog parent) {
      logger.append("Creating information dialog");
    }

    @Override
    public JButton addShareButton(JDialog dialog) {
      logger.append("Getting add share button");
      return new JButton();
    }

    @Override
    public JTextField getTicker(JDialog dialog) {
      logger.append("Getting ticker ");
      return new JTextField();
    }

    @Override
    public JTextField getShares(JDialog dialog) {
      logger.append("Getting shares ");
      return new JTextField();
    }

    @Override
    public JDialog createPortfolioDialog() {
      logger.append("Creating portfolio dialog ");
      return new JDialog();
    }

    @Override
    public JTextField getPortfolioName(JDialog dialog) {
      logger.append("Getting portfolio names ");
      return new JTextField();
    }

    @Override
    public JScrollPane getStocksPane(JDialog dialog) {
      logger.append("Getting stocks pane ");
      return new JScrollPane();
    }

    @Override
    public JTable getStocksTable(JScrollPane stocksPane) {
      logger.append("Getting stocks table ");
      return new JTable();
    }

    @Override
    public JButton getSubmitButton(JDialog dialog) {
      logger.append("Getting submit button ");
      return new JButton();
    }

    @Override
    public JDialog createPortfolioNamesDialog(String[] portfolioNames) {
      logger.append("Creating portfolio names dialog ");
      return new JDialog();
    }

    @Override
    public JDialog loadFromFileDialog() {
      logger.append("Creating load from file dialog ");
      return new JDialog();
    }

    @Override
    public JButton getFileChosen(JDialog dialog) {
      logger.append("Getting file chosen button ");
      return new JButton();
    }

    @Override
    public JTextField commissionAmount(JDialog dialog) {
      logger.append("Getting commission amount ");
      return new JTextField();
    }

    @Override
    public JDialog viewComposition(List<String> names) {
      logger.append("Creating view composition dialog ");
      return new JDialog();
    }

    @Override
    public String getDate(JPanel datePanel) {
      logger.append("Getting date ");
      return "";
    }

    @Override
    public JDialog saveFileDialog(List<String> names) {
      logger.append("Creating save file dialog ");
      return new JDialog();
    }

    @Override
    public JDialog getValueDialog(List<String> names) {
      logger.append("Creating get value dialog ");
      return new JDialog();
    }

    @Override
    public JDialog createInvestDialog(List<String> names) {
      logger.append("Creating investment dialog ");
      return new JDialog();
    }

    @Override
    public JDialog sellStockDialog(List<String> names) {
      logger.append("Creating sell stock dialog ");
      return new JDialog();
    }

    @Override
    public JDialog costBasisDialog(List<String> names) {
      logger.append("Creating cost basis dialog ");
      return new JDialog();
    }

    @Override
    public JDialog performanceDialog(List<String> names, Map<LocalDate, Double> performance) {
      logger.append("Creating performance dialog ");
      return new JDialog();
    }

    @Override
    public JDialog createDollarCostAveragingDialogForExistingPortfolios(List<String> names) {
      logger.append("Creating DCA for existing portfolio dialog ");
      return new JDialog();
    }

    @Override
    public JDialog createPortfolioWithDCADialog() {
      logger.append("Creating DCA for new portfolio dialog ");
      return new JDialog();
    }

    @Override
    public JDialog rebalancePortfolioDialog(List<String> names) {
      logger.append("Rebalance portfolio dialog");
      return new JDialog();
    }
  }

  static class MockModel implements PortfolioModel {
    public StringBuilder log;
    public List<Portfolio> portfolioList;
    public List<String> nameList;

    /**
     * MockModel constructor class that sets the log values.
     *
     * @param log passed log.
     */
    public MockModel(StringBuilder log) {
      this.log = log;
      portfolioList = new ArrayList<>();
      nameList = new ArrayList<>();
    }

    @Override
    public void createInflexiblePortfolioFromFile(String name, String filePath) {
      log.append("Name of file: ").append(name).append(" path given: ").append(filePath);
    }

    @Override
    public String getValueOfPortfolio(String name, String date) throws RuntimeException {
      log.append("Finding value for ").append(name).append(" for date ").append(date);
      return "Value found";
    }

    @Override
    public Portfolio getPortfolio(String name) throws RuntimeException {
      log.append("Finding portfolio of name ").append(name);
      return null;
    }

    @Override
    public List<Portfolio> getPortfolioList() {
      return portfolioList;
    }

    @Override
    public List<String> getPortfolioNames() {
      return nameList;
    }

    @Override
    public void createInflexiblePortfolioManually(String name, Map<String, String> stocks) {
      log.append("Creating portfolio manually with name ").append(name);
    }

    @Override
    public void savePortfolioToFile(String name) {
      log.append("Portfolio to be saved that has name ").append(name);
    }

    @Override
    public void createFlexiblePortfolioFromFile(String name,
                                                String filePath,
                                                String dateOfCreation, Double commission) {
      log.append("Flexible portfolio name of file: ").append(name).append(" path given: ")
              .append(filePath);
    }

    @Override
    public void createFlexiblePortfolioManually(String name,
                                                Map<String, Map<String, String>> stocks) {
      log.append("Creating flexible portfolio manually with name").append(name);
    }

    @Override
    public void buyStock(String name, Integer shares, String ticker,
                         String date, Double commissionFee) {
      log.append("Buying stock");
    }

    @Override
    public void sellStock(String name, Integer shares, String ticker,
                          String date, Double commissionFee) {
      log.append("Selling stock");
    }

    @Override
    public String getCostBasis(String name, String date) {
      log.append("Getting cost basis");
      return "";
    }

    @Override
    public Map<LocalDate, Double> performanceOverTime(String name,
                                                      String timeStamp, String startDate,
                                                      String endDate) throws ParseException {
      log.append("Getting performance over time");
      return new HashMap<>();
    }

    @Override
    public Map<String, String> getComposition(String name, String date) {
      log.append("Getting composition");
      return new HashMap<>();
    }

    @Override
    public void investStocks(String name, Double totalInvestment,
                             Map<String, Double> map, String date) {
      log.append("Investing stocks");
    }

    @Override
    public void dollarCostAveraging(String name, Integer frequency,
                                    String startDate, String endDate, double totalInvestment,
                                    Map<String, Double> stocksPercent) {
      log.append("Performing dollar cost averaging");

    }

    @Override
    public void rebalancePortfolio(String name, Map<String, Double> stockWeights, String date)
        throws IllegalArgumentException {
      log.append("Rebalancing a portfolio");
    }
  }

  @Before
  public void setUp() {
    log = new StringBuilder();
    controller = new PortfolioUIController(new MockModel(log));
    view = new MockView(log);
    controller.setView(view);
    dialog = new JDialog();
    mockValidTickers = new ArrayList<>();
    mockStocks = new HashMap<>();
  }

  @Test
  public void testCreateFlexiblePortfolio() {
    controller.createNewFlexiblePortfolio();
    assertEquals("Adding features to the view " +
            "Creating portfolio dialog " +
            "Getting add share buttonGetting submit button ", log.toString());
  }

  @Test
  public void testBuyingStocks() {
    controller.buyStocks();
    assertEquals("Adding features to the view Creating buy " +
            "dialog Getting add share button", log.toString());
  }

  @Test
  public void testSellStocks() {
    controller.sellStocks();
    assertEquals("Adding features to the view Creating sell stock " +
            "dialog Getting add share button", log.toString());
  }

  @Test
  public void testGetCostBasis() {
    controller.getCostBasis();
    assertEquals("Adding features to the view Creating cost basis " +
            "dialog Getting submit button ", log.toString());
  }

  @Test
  public void testGetValue() {
    controller.getValue();
    assertEquals("Adding features to the view Creating get value dialog " +
            "Getting submit button ", log.toString());
  }

  @Test
  public void testSavePortfolio() {
    controller.savePortfolio();
    assertEquals("Adding features to the view Creating save file dialog" +
            " Getting submit button ", log.toString());
  }

  @Test
  public void testLoadPortfolio() {
    controller.loadPortfolio();
    assertEquals("Adding features to the view Creating load from file " +
            "dialog Getting submit button ", log.toString());
  }

  @Test
  public void testViewPortfolios() {
    controller.viewPortfolios();
    assertEquals("Adding features to the view Creating portfolio names dialog ",
            log.toString());
  }

  @Test
  public void testViewComposition() {
    controller.viewComposition();
    assertEquals("Adding features to the view Creating view composition " +
            "dialog Getting submit button ", log.toString());
  }

  @Test
  public void testInvestPortfolio() {
    controller.investPortfolio();
    assertEquals("Adding features to the view Creating investment dialog " +
            "Getting add share buttonGetting submit button ", log.toString());
  }

  @Test
  public void testDollarCostAveraging() {
    controller.dollarCostAveraging();
    controller.createWithDollarCostAveraging();
    assertEquals("Adding features to the view Creating DCA for existing portfolio dialog " +
            "Getting add share buttonGetting submit button Creating DCA for new portfolio dialog " +
            "Getting add share buttonGetting submit button ", log.toString());
  }

  @Test
  public void testPerformanceGraph() {
    controller.viewPerformance();
    assertEquals("Adding features to the view Creating performance dialog " +
            "Getting submit button ", log.toString());
  }

  @Test
  public void testAddShareWhileCreatingPortfolioListener() {
    new AddShareWhileCreatingPortfolioListener(new MockModel(log), view, dialog, mockStocks,
            mockValidTickers).actionPerformed(new ActionEvent(dialog, 0, "Test"));
    assertEquals("Adding features to the view Getting ticker Getting shares " +
            "Creating error dialog ", log.toString());
  }

  @Test
  public void testAddShareWhileInvestingListener() {
    new AddShareWhileInvesting(new MockModel(log), view, dialog, new HashMap<>(), mockValidTickers)
            .actionPerformed(new ActionEvent(dialog, 0 , "Test"));
    assertEquals("Adding features to the view Getting ticker Getting shares " +
            "Creating error dialog ", log.toString());
  }

  @Test
  public void testDCAListener() {
    new PerformDollarCostAveragingListener(view, new HashMap<>(), new MockModel(log), dialog)
            .actionPerformed(new ActionEvent(dialog, 0, "Test"));
    assertEquals("Adding features to the view Creating error dialog ", log.toString());
  }

  @Test
  public void testGoBackListener() {
    new GoBackListener(dialog).actionPerformed(new ActionEvent(dialog, 0, "test"));
    assertEquals("Adding features to the view ", log.toString());
  }

  @Test
  public void testGetValueListener() {
    new GetValueListener(view, dialog, new MockModel(log)).actionPerformed(new ActionEvent(dialog,
            0, "Test"));
    assertEquals("Adding features to the view Creating error dialog ", log.toString());
  }

  @Test
  public void testCostBasisListener() {
    new GetCostBasisListener(view, dialog, new MockModel(log)).actionPerformed(new ActionEvent(
            dialog, 0 , "Test"));
    assertEquals("Adding features to the view Creating error dialog ", log.toString());
  }

  @Test
  public void saveToFileListener() {
    new SaveToFileListener(dialog, view, new MockModel(log)).actionPerformed(new ActionEvent(
            dialog, 0, "Test"));
    assertEquals("Adding features to the view Creating error dialog ", log.toString());
  }

  @Test
  public void sellStockListener() {
    new SellStockListener(view, dialog, new MockModel(log)).actionPerformed(new ActionEvent(
            dialog, 0 , "Test"));
    assertEquals("Adding features to the view Getting ticker Getting " +
            "shares Creating error dialog Creating error dialog " +
            "Creating error dialog ", log.toString());
  }

  @Test
  public void submitInvestmentListener() {
    new SubmitInvestmentListener(view, new HashMap<>(), new MockModel(log), dialog).
            actionPerformed(new ActionEvent(dialog, 0, "Test"));
    assertEquals("Adding features to the view Creating error dialog ", log.toString());
  }

  @Test
  public void viewCompositionListener() {
    new ViewCompositionListener(view, dialog, new MockModel(log)).actionPerformed(new ActionEvent(
            dialog, 0, "Test"));
    assertEquals("Adding features to the view Getting stocks pane " +
            "Getting stocks table Creating error dialog ", log.toString());
  }

  @Test
  public void viewPerformanceListenerTest() {
    new ViewPerformanceListener(dialog, view, new MockModel(log)).actionPerformed(new ActionEvent(
            dialog, 0, "Test"));
    assertEquals("Adding features to the view Creating error dialog ", log.toString());
  }

}