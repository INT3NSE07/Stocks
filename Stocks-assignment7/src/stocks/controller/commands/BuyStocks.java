package stocks.controller.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import stocks.model.PortfolioModel;
import stocks.portfolio.PortfolioImpl;
import stocks.view.PortfolioView;

/**
 * This portfolio represents the command to buy stocks of a portfolio.
 */
public class BuyStocks extends AbstractCommand {

  /**
   * Creates a BuyStocks type object that works with the model, view and input scanner
   * provided to the PortfolioController it is being used by.
   *
   * @param model the model that the PortfolioController uses
   * @param view  the view that the PortfolioController uses to show messages to the user
   * @param in    the input scanner that the PortfolioController uses to get user input
   */
  public BuyStocks(PortfolioModel model, PortfolioView view, Scanner in) {
    super(model, view, in);
  }

  @Override
  public void process() {
    view.printMessage("\nEnter name of portfolio you want to buy stocks for: ");
    String name = in.nextLine();
    if (!model.getPortfolioNames().contains(name)) {
      view.printMessage("Portfolio of name " + name + " does not exist");
      return;
    }
    if (model.getPortfolio(name) instanceof PortfolioImpl) {
      view.printMessage("Cannot buy stocks for an inflexible portfolio.");
      return;
    }
    List<String> validTickers = new ArrayList<>();
    view.printMessage("Enter ticker of stock you wish to purchase");
    String ticker = in.nextLine();
    try {
      InputStream stream = getClass().getResourceAsStream("validTickers.csv");
      InputStreamReader reader = new InputStreamReader(stream);
      BufferedReader br = new BufferedReader(reader);
      for (String line; (line = br.readLine()) != null; ) {
        validTickers.add(line);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    if (validTickers.contains(ticker)) {
      view.printMessage("Enter number of shares you want to purchase: ");
    }
    String shares;
    try {
      shares = in.nextLine();
      int sharesNum = Integer.parseInt(shares);
      if (sharesNum <= 0) {
        view.printMessage("Number of shares must be positive");
        return;
      }
    } catch (NumberFormatException e) {
      view.printMessage("Number of shares must be an integer.");
      return;
    }
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String date = dtf.format(LocalDate.now());
    view.printMessage("Enter is the commission fee paid for buying this: ");
    double commissionFee;
    try {
      commissionFee = Double.parseDouble(in.nextLine());
    } catch (NumberFormatException e) {
      view.printMessage("Number of shares must be an number.");
      return;
    }
    try {
      model.buyStock(name, Integer.valueOf(shares), ticker, date, commissionFee);
    } catch (IOException | RuntimeException e) {
      view.printMessage(e.getMessage());
    }
  }
}
