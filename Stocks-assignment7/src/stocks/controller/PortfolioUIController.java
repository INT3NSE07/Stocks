package stocks.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JButton;

import stocks.controller.actionlisteners.AddShareWhileCreatingPortfolioListener;
import stocks.controller.actionlisteners.AddShareWhileInvesting;
import stocks.controller.actionlisteners.BuyActionListener;
import stocks.controller.actionlisteners.CreatePortfolioFromFileListener;
import stocks.controller.actionlisteners.CreatePortfolioManuallyListener;
import stocks.controller.actionlisteners.GetCostBasisListener;
import stocks.controller.actionlisteners.GetValueListener;
import stocks.controller.actionlisteners.PerformDollarCostAveragingListener;
import stocks.controller.actionlisteners.SaveToFileListener;
import stocks.controller.actionlisteners.SellStockListener;
import stocks.controller.actionlisteners.SubmitInvestmentListener;
import stocks.controller.actionlisteners.ViewCompositionListener;
import stocks.controller.actionlisteners.ViewPerformanceListener;
import stocks.controller.actionlisteners.RebalanceAPortfolio;
import stocks.model.PortfolioModel;
import stocks.view.PortfolioUIView;

/**
 * Portfolio UI Controller class to implement all the features supported in ths stage.
 */
public class PortfolioUIController implements Features {

  private PortfolioUIView view;
  private final PortfolioModel model;
  private final List<String> validTickers;

  /**
   * Constructor that takes in a model to get access to the level where the methods are called
   * when needed.
   *
   * @param model passed model.
   */
  public PortfolioUIController(PortfolioModel model) {
    this.model = model;
    String filePath = "validTickers.csv";
    try {
      InputStream inputStream = getClass().getResourceAsStream(filePath);
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
      BufferedReader br = new BufferedReader(inputStreamReader);
      validTickers = new ArrayList<>();
      for (String line; (line = br.readLine()) != null; ) {
        validTickers.add(line);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void setView(PortfolioUIView view) {
    this.view = view;
    view.addFeatures(this);
  }

  @Override
  public void createNewFlexiblePortfolio() {
    Map<String, String> stocks = new HashMap<>();
    JDialog createPortfolioDialog = view.createPortfolioDialog();
    JButton addShareButton = view.addShareButton(createPortfolioDialog);
    addShareButton.addActionListener(new AddShareWhileCreatingPortfolioListener(
            model, view, createPortfolioDialog, stocks, validTickers));
    JButton submitButton = view.getSubmitButton(createPortfolioDialog);

    submitButton.addActionListener(new CreatePortfolioManuallyListener(view, stocks,
            model, createPortfolioDialog));
    createPortfolioDialog.pack();
    createPortfolioDialog.setVisible(true);
  }

  @Override
  public void buyStocks() {
    JDialog buyDialog = view.createBuyDialog(model.getPortfolioNames());
    buyDialog.setVisible(true);
    JButton addShareButton = view.addShareButton(buyDialog);
    addShareButton.addActionListener(new BuyActionListener(view, buyDialog, model, validTickers));
  }

  @Override
  public void sellStocks() {
    JDialog sellDialog = view.sellStockDialog(model.getPortfolioNames());
    sellDialog.setVisible(true);
    JButton addShareButton = view.addShareButton(sellDialog);
    addShareButton.addActionListener(new SellStockListener(view, sellDialog, model));
  }

  @Override
  public void getCostBasis() {
    JDialog getCostBasisDialog = view.costBasisDialog(model.getPortfolioNames());
    getCostBasisDialog.setVisible(true);
    JButton getCostBasisButton = view.getSubmitButton(getCostBasisDialog);
    getCostBasisButton.addActionListener(new GetCostBasisListener(view, getCostBasisDialog, model));
  }


  @Override
  public void getValue() {
    JDialog valueDialog = view.getValueDialog(model.getPortfolioNames());
    valueDialog.setVisible(true);
    JButton getValueButton = view.getSubmitButton(valueDialog);
    getValueButton.addActionListener(new GetValueListener(view, valueDialog, model));
  }

  @Override
  public void savePortfolio() {
    JDialog saveDialog = view.saveFileDialog(model.getPortfolioNames());
    saveDialog.setVisible(true);
    JButton saveFileButton = view.getSubmitButton(saveDialog);
    saveFileButton.addActionListener(new SaveToFileListener(saveDialog, view, model));
  }

  @Override
  public void loadPortfolio() {
    JDialog loadDialog = view.loadFromFileDialog();
    JButton submitButton = view.getSubmitButton(loadDialog);
    submitButton.addActionListener(new CreatePortfolioFromFileListener(view, loadDialog, model));
    loadDialog.setVisible(true);
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public void viewPortfolios() {
    int n = model.getPortfolioList().size();
    String[] names = new String[n];
    for (int i = 0; i < n; i++) {
      names[i] = model.getPortfolioNames().get(i);
    }
    JDialog viewDialog = view.createPortfolioNamesDialog(names);
    viewDialog.setVisible(true);
  }

  @Override
  public void viewComposition() {
    JDialog compositionDialog = view.viewComposition(model.getPortfolioNames());
    JButton submit = view.getSubmitButton(compositionDialog);
    submit.addActionListener(new ViewCompositionListener(view, compositionDialog, model));
    compositionDialog.setVisible(true);
  }

  @Override
  public void investPortfolio() {
    Map<String, Double> breakdownMap = new HashMap<>();
    JDialog investDialog = view.createInvestDialog(model.getPortfolioNames());
    JButton addTickerButton = view.addShareButton(investDialog);
    JButton submitButton = view.getSubmitButton(investDialog);
    addTickerButton.addActionListener(new AddShareWhileInvesting(model, view,
            investDialog, breakdownMap, validTickers));
    submitButton.addActionListener(new SubmitInvestmentListener(view,
            breakdownMap, model, investDialog));
    investDialog.setVisible(true);
  }

  @Override
  public void dollarCostAveraging() {
    Map<String, Double> breakdownMap = new HashMap<>();
    JDialog dcaDialog = view.createDollarCostAveragingDialogForExistingPortfolios(model
            .getPortfolioNames());
    JButton addTickerButton = view.addShareButton(dcaDialog);
    addTickerButton.addActionListener(new AddShareWhileInvesting(model, view, dcaDialog,
            breakdownMap, validTickers));
    JButton submitButton = view.getSubmitButton(dcaDialog);
    submitButton.addActionListener(new PerformDollarCostAveragingListener(view, breakdownMap,
            model, dcaDialog));
    dcaDialog.setVisible(true);
  }

  @Override
  public void createWithDollarCostAveraging() {
    Map<String, Double> breakdownMap = new HashMap<>();
    JDialog dcaDialog = view.createPortfolioWithDCADialog();
    JButton addTickerButton = view.addShareButton(dcaDialog);
    addTickerButton.addActionListener(new AddShareWhileInvesting(model, view,
            dcaDialog, breakdownMap, validTickers));
    JButton submitButton = view.getSubmitButton(dcaDialog);
    submitButton.addActionListener(new PerformDollarCostAveragingListener(view, breakdownMap,
            model, dcaDialog));
    dcaDialog.setVisible(true);
  }

  @Override
  public void viewPerformance() {
    JDialog performanceDialog = view.performanceDialog(model.getPortfolioNames(), new HashMap<>());
    JButton submitButton = view.getSubmitButton(performanceDialog);
    submitButton.addActionListener(new ViewPerformanceListener(performanceDialog, view, model));
    performanceDialog.setVisible(true);
  }

  @Override
  public void reBalancePortfolio() {
    JDialog reBalancePortfolioDialog = view.rebalancePortfolioDialog(model.getPortfolioNames());
    JButton submitButton = view.getSubmitButton(reBalancePortfolioDialog);
    submitButton.addActionListener(new RebalanceAPortfolio(reBalancePortfolioDialog, view, model));
    reBalancePortfolioDialog.setVisible(true);
  }
}
