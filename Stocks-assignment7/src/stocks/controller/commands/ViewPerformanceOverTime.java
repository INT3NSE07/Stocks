package stocks.controller.commands;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

/**
 * This class represents a command to view performance of a portfolio over time.
 */
public class ViewPerformanceOverTime extends AbstractCommand {

  /**
   * Creates a PortfolioCommand type object that works with the model, view and input scanner
   * provided to the PortfolioController it is being used by.
   *
   * @param model the model that the PortfolioController uses
   * @param view  the view that the PortfolioController uses to show messages to the user
   * @param in    the input scanner that the PortfolioController uses to get user input
   */
  public ViewPerformanceOverTime(PortfolioModel model,
                                 PortfolioView view, Scanner in) {
    super(model, view, in);
  }

  @Override
  public void process() throws ParseException {
    view.printMessage("\nEnter name for portfolio: ");
    String name = in.nextLine();
    if (!model.getPortfolioNames().contains(name)) {
      view.printMessage("Portfolio of name " + name + " does not exist.");
      return;
    }
    view.printMessage("Enter the timestamp type you want to view performance in: 1 - Daily, " +
            "2 - Monthly, 3 - Yearly");
    String timestamp = in.nextLine();
    if (!timestamp.equals("1") && !timestamp.equals("2") && !timestamp.equals("3")) {
      view.printMessage("Please enter a valid time stamp.");
    }
    view.printMessage("Enter the start year of range: ");
    String startyear = in.nextLine();
    view.printMessage("Enter the start month of range:  "
            + "(1-January...12-December): ");
    String startmonth = in.nextLine();
    if (startmonth.length() < 2) {
      startmonth = '0' + startmonth;
    }
    view.printMessage("Enter the start day of range: ");
    String startday = in.nextLine();
    if (startday.length() < 2) {
      startday = '0' + startday;
    }
    String startDate = startyear + "-" + startmonth + "-" + startday;
    view.printMessage("Enter the end year of range: ");
    String year = in.nextLine();
    view.printMessage("Enter the end month of range  "
            + "(1-January...12-December): ");
    String month = in.nextLine();
    if (month.length() < 2) {
      month = '0' + month;
    }
    view.printMessage("Enter the end day of range: ");
    String day = in.nextLine();
    if (day.length() < 2) {
      day = '0' + day;
    }
    String endDate = year + "-" + month + "-" + day;
    Map<LocalDate, Double> map = new HashMap<>();
    if (checkValidDate(startDate) && checkValidDate(endDate)) {
      try {
        switch (timestamp) {
          case "1":
            map = model.performanceOverTime(name, "Daily", startDate, endDate);
            break;
          case "2":
            String[] temp = startDate.split("-");
            temp[2] = "29";
            startDate = String.join("-", temp);
            map = model.performanceOverTime(name, "Monthly", startDate, endDate);
            break;
          case "3":
            String[] temp2 = startDate.split("-");
            temp2[1] = "12";
            temp2[2] = "29";
            startDate = String.join("-", temp2);
            map = model.performanceOverTime(name, "Yearly", startDate, endDate);
            break;
          default:
            view.printMessage("Please select a correct timestamp.");
        }
      } catch (RuntimeException e) {
        view.printMessage(e.getMessage());
        return;
      }
    } else {
      view.printMessage("Invalid date entered. Please try again");
    }
    try {
      Double maxValue = Collections.max(map.values());
      int scale = (int) Math.floor(maxValue / 50);
      view.printMessage("Performance of portfolio " + name + " from "
              + startDate + " to " + endDate);
      view.printMessage(" ");
      StringBuilder x;
      List<LocalDate> dateSet = new ArrayList<>(map.keySet());
      Collections.sort(dateSet);
      for (LocalDate date : dateSet) {
        int numberOfStars = (int) (map.get(date) / scale);
        x = new StringBuilder().append(date.toString()).append(": ");
        while (numberOfStars > 0) {
          x.append("*");
          numberOfStars--;
        }
        view.printMessage(x.toString());
      }
      view.printMessage(" ");
      view.printMessage("Scale: $" + scale);
    } catch (Exception e) {
      view.printMessage(e.getMessage());
    }
  }
}
