package enums;

import constants.Constants;

public enum PortfolioTypes {
  INFLEXIBLE("INFLEXIBLE"),
  FLEXIBLE("FLEXIBLE");

  private final String portfolioType;

  PortfolioTypes(String portfolioType) {
    this.portfolioType = portfolioType;
  }

  public static PortfolioTypes getPortfolioTypeByValue(String type)
      throws IllegalArgumentException {
    for (PortfolioTypes portfolioType : PortfolioTypes.values()) {
      if (portfolioType.portfolioType.equals(type)) {
        return portfolioType;
      }
    }
    throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
  }

  public String getPortfolioType() {
    return this.portfolioType;
  }
}

