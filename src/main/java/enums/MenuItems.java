package enums;

public enum MenuItems {
  PORTFOLIO_OPTIONS(0),
  INFLEXIBLE_PORTFOLIO_MAIN_MENU(1),
  FLEXIBLE_PORTFOLIO_MAIN_MENU(2),
  CREATE_PORTFOLIO_SUBMENU_ITEMS(3),
  TRANSACTIONS_SUBMENU(4);

  private final int menu;

  MenuItems(int menu) {
    this.menu = menu;
  }

  public static MenuItems getMenuByValue(int menu)
      throws IllegalArgumentException {
    for (MenuItems menuItem : MenuItems.values()) {
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
