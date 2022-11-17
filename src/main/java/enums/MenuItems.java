package enums;

public enum MenuItems {
  TYPE_OF_PORTFOLIO(0),
  INFLEXIBLE_PORTFOLIO(1),
  FLEXIBLE_PORTFOLIO(2),
  CREATE_PORTFOLIO(3),
  CREATE_TRANSACTION(4);

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
