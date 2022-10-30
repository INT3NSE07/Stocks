package controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import model.IPortfolioModel;
import view.IPortfolioView;

public class PortfolioController implements IPortfolioController {

  private final IPortfolioModel model;
  private final IPortfolioView view;
  private final InputStream in;

  public PortfolioController(IPortfolioModel model, IPortfolioView view, InputStream in) {
    this.model = model;
    this.view = view;
    this.in = in;
  }

//  public void run() {
//    int selectedMenuItem = 0;
//
//    while (true) {
//      this.view.displayHeader(selectedMenuItem);
//      this.view.showMainOptions();
//      this.view.showSelectOption();
//      Scanner sc = new Scanner(this.in);
//      selectedMenuItem = sc.nextInt();
//      switch (selectedMenuItem) {
//        case 1:
//          int selectedPortfolioSubMenuItem = 0;
//          while (selectedPortfolioSubMenuItem != 2) {
//            this.view.displayHeader(selectedMenuItem);
//            this.view.showSubMenuOptions(selectedMenuItem);
//            this.view.showSelectOption();
//            selectedPortfolioSubMenuItem = sc.nextInt();
//            switch (selectedPortfolioSubMenuItem) {
//              case 1:
//                view.promptPortfolioName();
//                String newPortfolioName = sc.next();
//                view.showString(
//                    model.createPortfolio(newPortfolioName)
//                        .getName()
//                        + " portfolio was created.");
//                selectedMenuItem = 0;
//                break;
//              case 2:
//                //this.model.addStock();
//                selectedMenuItem = 0;
//                break;
//              default:
//                view.showOptionError();
//                break;
//            }
//          }
//        case 2:
////          this.model.getValueOfPortfolio();
//          break;
//        case 3:
////          this.model.readPortfolio();
//          break;
//        case 4:
//          return;
//        default:
//          view.showOptionError();
//          break;
//      }
//    }
//  }

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
          Map<String, Double> stockSymbolQuantityMap = new HashMap<>();

          int selectedSubmenuItem = 0;
          while (selectedSubmenuItem != 2) {
            this.view.showOptions(selectedMenuItem);
            this.view.showSelectOption();

            selectedSubmenuItem = sc.nextInt();
            switch (selectedSubmenuItem) {
              case 1:
                view.promptStockSymbol();
                String symbol = sc.next();

                view.promptStockQuantity();
                double quantity = sc.nextInt();

                stockSymbolQuantityMap.put(symbol, quantity);
                break;
              case 2:
                break;
              default:
                view.showOptionError();
                break;
            }
          }
          model.createPortfolio(portfolioName, stockSymbolQuantityMap);
          view.showString(portfolioName + " portfolio was created.");

          selectedMenuItem = 0;
          break;
        }
        case 2: {
          view.promptPortfolioName();
          String portfolioName = sc.next();

          this.model.readPortfolio(portfolioName);
        }
        break;
        case 3:
          view.promptPortfolioName();
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
