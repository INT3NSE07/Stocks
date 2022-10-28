package controller;

import java.io.InputStream;
import java.util.Scanner;

import model.IPortfolioModel;
import view.IPortfolioView;

public class PortfolioController implements IPortfolioController {
  private final IPortfolioModel model;
  private final IPortfolioView view;
  private final InputStream in;

//  private final Appendable out;

  public PortfolioController(IPortfolioModel model, IPortfolioView view, InputStream in) {
    this.model = model;
    this.view = view;
    this.in = in;
  }
  public void go(){
    int selectedMenuItem = 0;
    while(true) {
      this.view.displayHeader(selectedMenuItem);
      this.view.showMainOptions();
      this.view.showSelectOption();
      Scanner sc = new Scanner(this.in);
      selectedMenuItem = sc.nextInt();
      switch (selectedMenuItem) {
        case 1: int selectedPortfolioSubMenuItem = 0;
                while(selectedPortfolioSubMenuItem !=2) {
                  this.view.displayHeader(selectedMenuItem);
                  this.view.showSubMenuOptions(selectedMenuItem);
                  this.view.showSelectOption();
                  selectedPortfolioSubMenuItem = sc.nextInt();
                  switch (selectedPortfolioSubMenuItem) {
                    case 1: view.promptPortfolioName();
                            String newPortfolioName = sc.next();
                            view.promptPortfolioType();
                            int newPortfolioType = sc.nextInt();
                            model.createPortfolio(newPortfolioName);
                            selectedMenuItem = 0;
                            break;
                    case 2: selectedMenuItem = 0;
                            break;
                    default:view.showOptionError();
                            break;
                  }
                }

        case 2: this.model.addStock();
                break;
        case 3: this.model.getPortfolioValueOnDate();
                break;
        case 4: this.model.readPortfolio();
                break;
        case 5: return;
        default: view.showOptionError();
                 break;
      }
    }
  }

}
