# Code critique

### Design critique
- All methods required by inflexible and flexible portfolios have been added to the same PortfolioModel interface
- Similarly, the Portfolio interface represents both the inflexible and flexible portfolios. Due to this, methods not required by the inflexible portfolios still need to be implemented (bait and switch)
- The GUI view does not extend the PortfolioView interface. This may require the consumer of this interface to make changes to their code

### Implementation critique
- The overall implementation of the program was done well especially the controller and view
- Due to hardcoding of the API key inside the getCostOfStock() method, all unit tests are hitting the actual API. Moving this logic to a service would enable this API call to be mocked

### Documentation critique
- The setup readme documented all the steps required to run the project so we had no issues in running the code
- Similarly, the design readme was clear and we could understand the design and some implementation details through it
- Although the README was written well, it was a bit misleading because a few functionalities were documented to be working which were not

### Design/code strengths
- The controller design was well thought out and was the responsibility of handling inputs was cleanly segregated into command objects for text UI and actionlisteners for GUI. This would make it very easy to add new functionality without modifying existing logic
- The GUI view implementation was done well with each dialog in the system being in its own class. This made it easy to add the rebalancing functionality

### Design/code limitations
- Fetching stock data on weekends/non-trading days has not been implemented properly
- Although the program has AlphaVantage support, the list of supported tickers is static and is loaded when the program starts
- Dates are being hardcoded and will only work for years between 2015 and 2022
- Buying and selling a stock on a specific date has not been implemented

### Suggestions
- Move hardcoded strings to a Constants class to bring consistency
- Date validation is being performed manually by splitting a string which represents a date and only works for years between 2015 and 2022 as mentioned previously. Consider using LocalDate
- Split interfaces for inflexible and flexible portfolios
- Add the exceptions being thrown by a method to the method signature and avoid throwing RuntimeException

### Requested changes
- Add support for fractional shares in the model (FIXED by provider)
  - We required this fix to properly implement portfolio rebalancing. Rebalancing may produce fractional shares. If this fraction is discarded, the value of portfolio before and after rebalancing will differ
- Add support for buying/selling stocks on a particular date along with support to buy/sell fractional shares (NOT FIXED by provider)
  - While rebalancing, we need to buy under-represented shares. Conversely, over-represented shares need to be sold. Having working implementations of these would have greatly simplified the rebalancing logic
- Support for fetching value of a portfolio on a holiday (NOT FIXED by provider)