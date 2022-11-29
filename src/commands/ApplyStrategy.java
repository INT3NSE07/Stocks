package commands;

import constants.Constants;
import enums.MenuItems;
import enums.StrategyTypes;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import model.IPortfolioFacadeModel;
import model.InvestmentStrategy;
import utilities.DateUtils;
import utilities.Pair;
import utilities.StringUtils;
import view.IPortfolioView;

/**
 * This class represents a command in the command design pattern. It handles the strategy
 * functionality of a portfolio.
 */
public class ApplyStrategy implements PortfolioCommand {

  private final IPortfolioFacadeModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;

  /**
   * Constructs a {@link ApplyStrategy} command object and initializes the model, view and reader
   * fields.
   *
   * @param model          the model instance which is used to perform the actual operations
   * @param view           the view which displays output to the end user
   * @param bufferedReader the input stream through which user input is taken
   */
  public ApplyStrategy(IPortfolioFacadeModel model, IPortfolioView view,
      BufferedReader bufferedReader) {
    this.model = model;
    this.view = view;
    this.bufferedReader = bufferedReader;
  }

  @Override
  public void execute() throws IOException {
    int selectedSubmenuItem = MenuItems.FLEXIBLE_PORTFOLIO.getValue();
    double commission = 0;
    this.view.showPrompt(Constants.PROMPT_COMMISSION_KEY);

    try {
      commission = Double.parseDouble(this.bufferedReader.readLine());
      if (commission < 0) {
        throw new IllegalArgumentException(String.format(Constants.NON_NEGATIVE, "Commission"));
      }
    } catch (NumberFormatException numberFormatException) {
      this.view.showString("Please enter a valid commission value in $");
      return;
    } catch (IllegalArgumentException e) {
      this.view.showString(e.getMessage());
      return;
    }

    while (selectedSubmenuItem != Constants.APPLY_STRATEGY_SUBMENU_EXIT_CODE) {
      this.view.showOptions(MenuItems.APPLY_STRATEGY.getValue());
      this.view.showPrompt(Constants.PROMPT_CHOICE);

      try {
        selectedSubmenuItem = Integer.parseInt(this.bufferedReader.readLine());

        if ((selectedSubmenuItem > Constants.APPLY_STRATEGY_SUBMENU_EXIT_CODE) && (
            selectedSubmenuItem > 0)) {
          this.view.showString(Constants.INVALID_OPTION);
        }
      } catch (NumberFormatException e) {
        this.view.showString(Constants.INVALID_OPTION);
        break;
      }

      applyStrategySubMenuItem(selectedSubmenuItem, commission);
    }
  }

  private void applyStrategySubMenuItem(int selectedSubmenuItem, double commission) {
    if (selectedSubmenuItem == Constants.APPLY_STRATEGY_SUBMENU_EXIT_CODE) {
      return;
    }

    switch (selectedSubmenuItem) {
      case 1: {
        try {
          InvestmentStrategy investmentStrategy;

          this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
          String portfolioName = this.bufferedReader.readLine();
          if (StringUtils.isNullOrWhiteSpace(portfolioName)) {
            this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
            return;
          }

          String marker = "e";
          List<Pair<String, Double>> stockWeightPairs = new ArrayList<>();
          this.view.showPrompt(Constants.PROMPT_STOCK_WEIGHT_ENTRY);
          while (!marker.equals("q")) {
            try {
              this.view.showPrompt(Constants.PROMPT_STOCK_SYMBOL_KEY);
              String symbol = this.bufferedReader.readLine();

              if (StringUtils.isNullOrWhiteSpace(symbol)) {
                this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
                return;
              }

              try {
                if (!this.model.isStockSymbolValid(symbol)) {
                  this.view.showString(
                      String.format(Constants.SYMBOL_FETCH_FAIL, symbol));
                  return;
                }
              } catch (IOException e) {
                this.view.showString(
                    String.format(Constants.SYMBOL_FETCH_FAIL, symbol));
                return;
              }

              this.view.showPrompt(Constants.PROMPT_WEIGHT);
              double weight = Double.parseDouble(this.bufferedReader.readLine());
              if (BigDecimal.valueOf(weight).scale() > 2) {
                throw new IllegalArgumentException(
                    "The maximum supported scale (precision) of stock weight is 2.");
              }
              if (weight <= 0) {
                throw new IllegalArgumentException(
                    "Weight of a stock must be positive and non-zero.");
              }

              stockWeightPairs.add(new Pair<>(symbol, weight));

              marker = this.bufferedReader.readLine();
            } catch (NumberFormatException numberFormatException) {
              this.view.showString("Please enter a valid stock weight");
              return;
            } catch (IllegalArgumentException e) {
              this.view.showString(e.getMessage());
              return;
            }
          }
          investmentStrategy = new InvestmentStrategy(stockWeightPairs);

          try {
            this.view.showPrompt(Constants.PROMPT_INVESTMENT);
            double investment = Double.parseDouble(this.bufferedReader.readLine());
            if (investment <= 0) {
              throw new IllegalArgumentException(
                  "Total investment must be positive and non-zero.");
            }
            investmentStrategy.setStrategyInvestment(investment);
          } catch (NumberFormatException numberFormatException) {
            this.view.showString(Constants.INPUT_INVALID);
            return;
          }

          this.view.showPrompt(Constants.PROMPT_START_DATE_KEY);
          String startDate = this.bufferedReader.readLine();
          if (StringUtils.isNullOrWhiteSpace(startDate)) {
            this.view.showString(Constants.DATE_INVALID);
            return;
          }
          if (!DateUtils.isDateWithinRange(startDate, Constants.DEFAULT_DATETIME_FORMAT)) {
            this.view.showString(Constants.DATE_INVALID);
            return;
          }
          investmentStrategy.setStrategyStartDate(startDate);

          investmentStrategy.setCommission(commission);
          investmentStrategy.setStrategyType(StrategyTypes.FIXED_AMOUNT);
          this.model.applyInvestmentStrategy(portfolioName, investmentStrategy);
          this.view.showString("Strategy successfully created");
        } catch (IllegalArgumentException | IOException e) {
          this.view.showString(e.getMessage());
        }
        break;
      }
      case 2: {
        try {
          InvestmentStrategy investmentStrategy;

          this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
          String portfolioName = this.bufferedReader.readLine();
          if (StringUtils.isNullOrWhiteSpace(portfolioName)) {
            this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
            return;
          }

          String marker = "e";
          List<Pair<String, Double>> stockWeightPairs = new ArrayList<>();
          this.view.showPrompt(Constants.PROMPT_STOCK_WEIGHT_ENTRY);
          while (!marker.equals("q")) {
            try {
              this.view.showPrompt(Constants.PROMPT_STOCK_SYMBOL_KEY);
              String symbol = this.bufferedReader.readLine();

              if (StringUtils.isNullOrWhiteSpace(symbol)) {
                this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
                return;
              }

              try {
                if (!this.model.isStockSymbolValid(symbol)) {
                  this.view.showString(
                      String.format(Constants.SYMBOL_FETCH_FAIL, symbol));
                  return;
                }
              } catch (IOException e) {
                this.view.showString(
                    String.format(Constants.SYMBOL_FETCH_FAIL, symbol));
                return;
              }

              this.view.showPrompt(Constants.PROMPT_WEIGHT);
              double weight = Double.parseDouble(this.bufferedReader.readLine());
              if (BigDecimal.valueOf(weight).scale() > 2) {
                throw new IllegalArgumentException(
                    "The maximum supported scale (precision) of stock weight is 2.");
              }
              if (weight <= 0) {
                throw new IllegalArgumentException(
                    "Weight of a stock must be positive and non-zero.");
              }

              stockWeightPairs.add(new Pair<>(symbol, weight));

              marker = this.bufferedReader.readLine();
            } catch (NumberFormatException numberFormatException) {
              this.view.showString("Please enter a valid stock weight");
              return;
            } catch (IllegalArgumentException e) {
              this.view.showString(e.getMessage());
              return;
            }
          }
          investmentStrategy = new InvestmentStrategy(stockWeightPairs);

          try {
            this.view.showPrompt(Constants.PROMPT_INVESTMENT);
            double investment = Double.parseDouble(this.bufferedReader.readLine());
            if (investment <= 0) {
              throw new IllegalArgumentException(
                  "Total investment must be positive and non-zero.");
            }
            investmentStrategy.setStrategyInvestment(investment);
          } catch (NumberFormatException numberFormatException) {
            this.view.showString(Constants.INPUT_INVALID);
            return;
          }

          this.view.showPrompt(Constants.PROMPT_START_DATE_KEY);
          String startDate = this.bufferedReader.readLine();
          if (StringUtils.isNullOrWhiteSpace(startDate)) {
            this.view.showString(Constants.DATE_INVALID);
            return;
          }
          if (!DateUtils.isDateWithinRange(startDate, Constants.DEFAULT_DATETIME_FORMAT)) {
            this.view.showString(Constants.DATE_INVALID);
            return;
          }
          investmentStrategy.setStrategyStartDate(startDate);

          this.view.showPrompt(Constants.PROMPT_END_DATE_KEY);
          String endDate = this.bufferedReader.readLine();
          if (StringUtils.isNullOrWhiteSpace(endDate)) {
            endDate = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
          }
          if (!DateUtils.isValidDate(endDate, Constants.DEFAULT_DATETIME_FORMAT)) {
            this.view.showString(Constants.DATE_INVALID);
            return;
          }
          investmentStrategy.setStrategyEndDate(endDate);

          try {
            this.view.showPrompt(Constants.PROMPT_PERIOD);
            int period = Integer.parseInt(this.bufferedReader.readLine());
            if (period <= 0) {
              throw new IllegalArgumentException(
                  "The frequency of investment must be positive and non-zero.");
            }
            investmentStrategy.setStrategyPeriod(period);
          } catch (NumberFormatException numberFormatException) {
            this.view.showString(Constants.INPUT_INVALID);
            return;
          }

          investmentStrategy.setCommission(commission);
          investmentStrategy.setStrategyType(StrategyTypes.DOLLAR_COST_AVERAGING);
          this.model.applyInvestmentStrategy(portfolioName, investmentStrategy);
          this.view.showString("Strategy successfully created");
        } catch (IllegalArgumentException | IOException e) {
          this.view.showString(e.getMessage());
        }
        break;
      }
      default:
        this.view.showOptionError();
        break;
    }
  }
}
