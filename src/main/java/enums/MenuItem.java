package enums;

public enum MenuItem {
  PORTFOLIO_OPTIONS(0),
  INFLEXIBLE_PORTFOLIO_MAIN_MENU(1),
  FLEXIBLE_PORTFOLIO_MAIN_MENU(2),
  CREATE_PORTFOLIO_SUBMENU_ITEMS(3),
  TRANSACTIONS_SUBMENU(4);
  private final int menu;

  MenuItem(int menu) {
    this.menu = menu;
  }

  public static MenuItem getMenuByValue(int menu)
      throws IllegalArgumentException {
    for (MenuItem menuItem : MenuItem.values()) {
      if (menuItem.menu == menu) {
        return menuItem;
      }
    }
    throw new IllegalArgumentException("Invalid Menu number.");
  }

  public int getValue() {
    return this.menu;
  }

}
