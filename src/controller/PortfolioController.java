package controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.IPortfolioModel;
import utilities.Pair;
import view.IPortfolioView;

/**
 *
 */
public class PortfolioController implements IPortfolioController {

  private final IPortfolioModel model;
  private final IPortfolioView view;
  private final InputStream in;

  /**
   * @param model
   * @param view
   * @param in
   */
  public PortfolioController(IPortfolioModel model, IPortfolioView view, InputStream in) {
    this.model = model;
    this.view = view;
    this.in = in;
  }

  public void run() {
    int selectedMenuItem = 0;

    while (true) {
      this.view.showOptions(selectedMenuItem);
      this.view.showSelectOption();

      Scanner sc = new Scanner(this.in);
      selectedMenuItem = sc.nextInt();

      switch (selectedMenuItem) {
        case 1: {
          view.promptPortfolioName();
          String portfolioName = sc.next();
          List<Pair<String, Double>> stockPairs = new ArrayList<>();

          int selectedSubmenuItem = 0;
          while (selectedSubmenuItem != 2) {
            this.view.showOptions(selectedMenuItem);
            this.view.showSelectOption();

            selectedSubmenuItem = sc.nextInt();
            switch (selectedSubmenuItem) {
              case 1:
                this.view.promptStockSymbol();
                String symbol = sc.next();

                this.view.promptStockQuantity();
                double quantity = sc.nextInt();

                stockPairs.add(new Pair<>(symbol, quantity));
                break;
              case 2:
                break;
              default:
                this.view.showOptionError();
                break;
            }
          }
          this.model.createPortfolio(portfolioName, stockPairs);
          this.view.showString(portfolioName + " portfolio was created.");

          selectedMenuItem = 0;
          break;
        }
        case 2: {
          this.view.promptPortfolioName();
          String portfolioName = sc.next();

          this.view.showPortfolio(this.model.readPortfolio(portfolioName));
        }
        break;
        case 3:
          this.view.promptPortfolioName();
          String portfolioName = sc.next();

          view.promptDate();
          String date = sc.next();

          this.model.getPortfolioValueOnDate(portfolioName, date);
          break;
        case 4:
          return;
        default:
          view.showOptionError();
          break;
      }
    }
  }
}
