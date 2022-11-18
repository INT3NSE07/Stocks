package enums;

/**
 * This enum represents the types of menu items.
 */
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

  /**
   * Return the enum constant associated with the menu number.
   *
   * @param menu the menu number to be converted into an enum constant
   * @return the enum constant associated with the menu number
   * @throws IllegalArgumentException if the menu number is invalid
   */
  public static MenuItems getMenuByValue(int menu)
      throws IllegalArgumentException {
    for (MenuItems menuItem : MenuItems.values()) {
      if (menuItem.menu == menu) {
        return menuItem;
      }
    }
    throw new IllegalArgumentException("Invalid menu number.");
  }

  /**
   * Return the menu number associated with the menu item.
   *
   * @return the menu number associated with the menu item
   */
  public int getValue() {
    return this.menu;
  }
}
