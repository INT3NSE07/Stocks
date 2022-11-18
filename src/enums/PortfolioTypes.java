package enums;

import constants.Constants;

/**
 * This enum represents the types of portfolios which are supported by the system.
 */
public enum PortfolioTypes {
  INFLEXIBLE("INFLEXIBLE"),
  FLEXIBLE("FLEXIBLE");

  private final String portfolioType;

  PortfolioTypes(String portfolioType) {
    this.portfolioType = portfolioType;
  }

  /**
   * Return the enum constant associated with the type.
   *
   * @param type the type to be converted into an enum constant
   * @return the enum constant associated with the type
   * @throws IllegalArgumentException if the type is invalid
   */
  public static PortfolioTypes getPortfolioTypeByValue(String type)
      throws IllegalArgumentException {
    for (PortfolioTypes portfolioType : PortfolioTypes.values()) {
      if (portfolioType.portfolioType.equals(type)) {
        return portfolioType;
      }
    }
    throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
  }

  /**
   * Return the portfolio associated with the portfolio type.
   *
   * @return the portfolio associated with the portfolio type
   */
  public String getPortfolioType() {
    return this.portfolioType;
  }
}

