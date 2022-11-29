package enums;

public enum StrategyTypes {
  FIXED_AMOUNT("FIXED_AMOUNT"),
  DOLLAR_COST_AVERAGING("DOLLAR_COST_AVERAGING");

  private final String strategyType;

  StrategyTypes(String strategyType) {
    this.strategyType = strategyType;
  }

  /**
   * Return the enum constant associated with the type.
   *
   * @param type the type to be converted into an enum constant
   * @return the enum constant associated with the type
   * @throws IllegalArgumentException if the type is invalid
   */
  public static StrategyTypes getStrategyTypeByValue(String type) {
    for (StrategyTypes strategyType : StrategyTypes.values()) {
      if (strategyType.strategyType.equals(type)) {
        return strategyType;
      }
    }

    return null;
  }

  /**
   * Return the strategy associated with the strategy type.
   *
   * @return the strategy associated with the strategy type
   */
  public String getStrategyType() {
    return this.strategyType;
  }
}
