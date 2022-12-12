package stocks.controller.commands;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

public class RebalanceAPortfolio extends AbstractCommand {

  public RebalanceAPortfolio(PortfolioModel model, PortfolioView view, Scanner in) {
    super(model, view, in);
  }

  @Override
  public void process() throws ParseException {
    view.printMessage("\nEnter name for portfolio: ");
    String portfolioName = in.nextLine();
    if (!model.getPortfolioNames().contains(portfolioName)) {
      view.printMessage("Portfolio " + portfolioName + " does not exist.");
      return;
    }

    view.printMessage("\nEnter date in format YYYY-MM-DD: ");
    String date = in.nextLine();
    if (!isValidDate(date, DateTimeFormatter.ISO_LOCAL_DATE)) {
      view.printMessage("Date is invalid.");
      return;
    }

    view.printMessage(
        "\nPress press any key to assign equal investment weights to each stock in the portfolio. "
            + "To manually assign the weight press M");
    String investEqually = in.nextLine();

    Set<String> tickers = model.getComposition(portfolioName, date).keySet();
    Map<String, Double> stockWeights = new HashMap<>();
    for (String ticker : tickers) {
      double weight = 100.00 / tickers.size();

      if (investEqually.equalsIgnoreCase("q")) {
        view.printMessage(String.format("Enter investment weight for %s", ticker));

        while (true) {
          try {
            weight = Double.parseDouble(in.nextLine());
          } catch (NumberFormatException | NullPointerException e) {
            view.printMessage(
                String.format("Please enter a valid investment weight for %s.", ticker));
            continue;
          }

          break;
        }
      }

      stockWeights.put(ticker, weight);
    }

    model.rebalancePortfolio(portfolioName, stockWeights, date);
  }
}
