Steps to run the program

JAR:
1. Navigate to where the jar Stocks.jar file is saved.
   And make sure that the jfreechart-1.5.3.jar file to be in the same directory that the Stocks.jar
   file is in.
2. Run the file using the commands:
   For GUI  view   -> java -jar Stocks.jar
   For text view   -> java -jar Stocks.jar Text

To set-up the program in IntelliJ the following steps can be performed:
1. Navigate to File > Project Structure
2. Under Project Settings select Libraries and press on the ‘+’ icon.
3. Select Java and point to the jfreechart-1.5.3.jar file included in the submission zip file.
4. The JFreeChart should show up in the External Libraries in IntelliJ while the project is open.
5. Run the program.

Functionality implemented
1. Support for rebalancing an existing portfolio has been successfully implemented both in text and GUI views
2. Wrote unit tests for the rebalancing implementation
2. Please note the following issues with the providers code

Issues in provider code:
1. Getting a value of a stock on holiday/non-trading day is not working. Similarly even on a trading day if AlphaVantage hasn't refreshed the stocks file, the getValue method returns 0. Consider the date 2022-12-13. Although this is a trading day, AlphaVantage may not have refreshed their stocks file due to which the API still returns 2022-12-12 as the latest available date. The provider was NOT able to fix these issue
2. Buying and selling a stock on a specific date, buying and selling fractional shares has NOT been implemented. The provider was NOT able to fix this issue
3. Initially the model only supported integer stock values. The provider was able to fix this issue
4. 31 tests (not related to rebalancing) fail due to file paths being hardcoded in the tests

Changes
1. Added an interface IPortfolioVisitor which represents any operations which can be performed on a portfolio
2. Implemented the visitor interface in RebalancePortfolioVisitor which encapsulates all the logic specific to rebalancing a portfolio
3. To make a portfolio visitable, added an accept() method to Portfolio.java
4. Implement the accept() method in the FlexiblePortfolio class which passes the required data to the visitor
5. Add a method rebalancePortfolio() to PortfolioModel
6. In the PortfolioModelImpl, implement the rebalancePortfolio() method by creating the visitor object and visit the corresponding flexible portfolio
7. For the text interface, added a RebalancePortfolio command
8. Similarly for GUI view, added a RebalancePortfolioDialog which handles creation of the UI elements. In the controller, added a RebalancePortfolio actionlistener which listens, processes the inputs and finally sends it to the model
9. In PortfolioUIController, added rebalancePortfolio() which creates the dialog and attaches an actionlistener to it
10. In PortfolioControllerImpl, added option 13 to the knownCommands map
