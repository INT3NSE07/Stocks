package view;

import constants.Constants;
import model.Portfolio;
import model.Stock;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortfolioTextView implements IPortfolioView {

  private final PrintStream out;

  public PortfolioTextView(PrintStream out) {
    this.out = out;
  }

  @Override
  public void showString(String s) {
    out.println(s);
  }

  @Override
  public void showOptions(int selectedMenuItem) {
    // print the UI

//    out.println("Menu: ");
//    out.println("E: Enter a string");
//    out.println("Q: Quit the program");
//    out.print("Enter your choice: ");

    this.out.println(System.lineSeparator() + Constants.MAIN_MENU_ITEMS[selectedMenuItem]);

    switch (selectedMenuItem) {
      case 0:
        for (int i = 1; i < Constants.MAIN_MENU_ITEMS.length; i++) {
          this.out.println(i + ") " + Constants.MAIN_MENU_ITEMS[i]);
        }
        break;
      case 1:
        for (int i = 0; i < Constants.CREATE_PORTFOLIO_SUBMENU_ITEMS.length; i++) {
          this.out.println((i + 1) + ") " + Constants.CREATE_PORTFOLIO_SUBMENU_ITEMS[i]);
        }
        break;
      default:
        break;
    }
  }

  @Override
  public void showStringEntry() {
    out.print("\nEnter the string to be echoed: ");
  }

  @Override
  public void showOptionError() {
    this.out.println("Invalid option. Please try again." + System.lineSeparator());
  }

  @Override
  public void showSelectOption() {
    this.out.print("Enter your choice: ");
  }

  @Override
  public void showMainOptions() {
//    this.displayHeader(selectedMenuItem);
////
////    for (int i = 1; i < Constants.MAIN_MENU_ITEMS.length; i++) {
////      this.out.println(i + ") " + Constants.MAIN_MENU_ITEMS[i]);
////    }
  }

  @Override
  public void displayHeader(int menuItemNumber) {
    //this.out.println(Constants.MAIN_MENU_ITEMS[menuItemNumber]);
  }

  @Override
  public String showOutputStream() {
    return this.out.toString();
  }

  @Override
  public void showSubMenuOptions(int selectedMenuItem) {
    if (selectedMenuItem == 1) {
      this.out.println("1) Add Portfolio Name and Type\n2) Add Stocks\n3) Back");
    }
  }

  @Override
  public void promptPortfolioName() {
    this.out.print("Enter portfolio name: ");
  }

  @Override
  public void promptStockSymbol() {
    this.out.print("Enter ticker symbol: ");
  }

  @Override
  public void promptDate() {
    this.out.print("Enter date: ");
  }

  @Override
  public void showPortfolio(Portfolio readPortfolio) {
    this.out.println(readPortfolio.getName());
    TableGenerator tableGenerator = new TableGenerator();
    List<String> headersList = new ArrayList<>();
    headersList.add("ID");
    headersList.add("Ticker Symbol");
    headersList.add("No of Stocks");
    List<List<String>> rowsList = new ArrayList<>();
    for (int i = 0; i < readPortfolio.getStocks().size(); i++) {
      List<String> row = new ArrayList<>();
      row.add(String.valueOf(i+1));
      for (Stock stock: readPortfolio.getStocks()) {
        row.add(stock.getSymbol());
        row.add(String.valueOf(stock.getQuantity()));
      }
      rowsList.add(row);
    }
//    this.out.println(rowsList.get(0));
    this.out.println(tableGenerator.generateTable(headersList,rowsList));
  }

  @Override
  public void promptStockQuantity() {
    this.out.print("Enter quantity: ");
  }


  @Override
  public void promptPortfolioType() {
    this.out.println("Enter Portfolio Type: ");
  }

  public static class TableGenerator {

    private final int PADDING_SIZE = 2;

    public String generateTable(List<String> headersList, List<List<String>> rowsList, int... overRiddenHeaderHeight) {
      StringBuilder stringBuilder = new StringBuilder();

      int rowHeight = overRiddenHeaderHeight.length > 0 ? overRiddenHeaderHeight[0] : 1;

      Map<Integer, Integer> columnMaxWidthMapping = getMaximumWidthTable(headersList, rowsList);

      String NEW_LINE = System.lineSeparator();
      stringBuilder.append(NEW_LINE);
      stringBuilder.append(NEW_LINE);
      createRowLine(stringBuilder, headersList.size(), columnMaxWidthMapping);
      stringBuilder.append(NEW_LINE);


      for (int headerIndex = 0; headerIndex < headersList.size(); headerIndex++) {
        fillCell(stringBuilder, headersList.get(headerIndex), headerIndex, columnMaxWidthMapping);
      }

      stringBuilder.append(NEW_LINE);

      createRowLine(stringBuilder, headersList.size(), columnMaxWidthMapping);


      for (List<String> row : rowsList) {

        stringBuilder.append(NEW_LINE.repeat(Math.max(0, rowHeight)));

        for (int cellIndex = 0; cellIndex < row.size(); cellIndex++) {
          fillCell(stringBuilder, row.get(cellIndex), cellIndex, columnMaxWidthMapping);
        }

      }

      stringBuilder.append(NEW_LINE);
      createRowLine(stringBuilder, headersList.size(), columnMaxWidthMapping);
      stringBuilder.append(NEW_LINE);
      stringBuilder.append(NEW_LINE);

      return stringBuilder.toString();
    }

    private void fillSpace(StringBuilder stringBuilder, int length) {
      stringBuilder.append(" ".repeat(Math.max(0, length)));
    }

    private void createRowLine(StringBuilder stringBuilder, int headersListSize, Map<Integer, Integer> columnMaxWidthMapping) {
      for (int i = 0; i < headersListSize; i++) {
        String TABLE_JOINT_SYMBOL = "+";
        if (i == 0) {
          stringBuilder.append(TABLE_JOINT_SYMBOL);
        }

        String TABLE_H_SPLIT_SYMBOL = "-";
        stringBuilder.append(String.valueOf(TABLE_H_SPLIT_SYMBOL).repeat(Math.max(0, columnMaxWidthMapping.get(i) + PADDING_SIZE * 2)));
        stringBuilder.append(TABLE_JOINT_SYMBOL);
      }
    }


    private Map<Integer, Integer> getMaximumWidthTable(List<String> headersList, List<List<String>> rowsList) {
      Map<Integer, Integer> columnMaxWidthMapping = new HashMap<>();

      for (int columnIndex = 0; columnIndex < headersList.size(); columnIndex++) {
        columnMaxWidthMapping.put(columnIndex, 0);
      }

      for (int columnIndex = 0; columnIndex < headersList.size(); columnIndex++) {

        if (headersList.get(columnIndex).length() > columnMaxWidthMapping.get(columnIndex)) {
          columnMaxWidthMapping.put(columnIndex, headersList.get(columnIndex).length());
        }
      }


      for (List<String> row : rowsList) {

        for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {

          if (row.get(columnIndex).length() > columnMaxWidthMapping.get(columnIndex)) {
            columnMaxWidthMapping.put(columnIndex, row.get(columnIndex).length());
          }
        }
      }

      for (int columnIndex = 0; columnIndex < headersList.size(); columnIndex++) {

        if (columnMaxWidthMapping.get(columnIndex) % 2 != 0) {
          columnMaxWidthMapping.put(columnIndex, columnMaxWidthMapping.get(columnIndex) + 1);
        }
      }


      return columnMaxWidthMapping;
    }

    private int getOptimumCellPadding(int cellIndex, int datalength, Map<Integer, Integer> columnMaxWidthMapping, int cellPaddingSize) {
      if (datalength % 2 != 0) {
        datalength++;
      }

      if (datalength < columnMaxWidthMapping.get(cellIndex)) {
        cellPaddingSize = cellPaddingSize + (columnMaxWidthMapping.get(cellIndex) - datalength) / 2;
      }

      return cellPaddingSize;
    }

    private void fillCell(StringBuilder stringBuilder, String cell, int cellIndex, Map<Integer, Integer> columnMaxWidthMapping) {

      int cellPaddingSize = getOptimumCellPadding(cellIndex, cell.length(), columnMaxWidthMapping, PADDING_SIZE);

      String TABLE_V_SPLIT_SYMBOL = "|";
      if (cellIndex == 0) {
        stringBuilder.append(TABLE_V_SPLIT_SYMBOL);
      }

      fillSpace(stringBuilder, cellPaddingSize);
      stringBuilder.append(cell);
      if (cell.length() % 2 != 0) {
        stringBuilder.append(" ");
      }

      fillSpace(stringBuilder, cellPaddingSize);

      stringBuilder.append(TABLE_V_SPLIT_SYMBOL);

    }
  }

}
