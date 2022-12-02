# Stocks

Stocks is a portfolio management system.
The features that have been implemented and tested in the Assignment 6 are:
1) Added support for Fixed cost and Dollar cost averaging investment strategies
2) Investment strategies can be applied while creating portfolios or can be applied to existing portfolios
3) Implemented a GUI using Java swing which shows all operations that can be performed on a flexible portfolio through a user friendly GUI
4) Portfolio performance can be visualized through a graphical barchart


The features that have been implemented and tested in the Assignment 5 are:
1) Allow a user to create one or more flexible portfolios. A specific number of shares of a stock can be purchased on a specific date which are then added them to the portfolio
2) Allow a user to sell a specific number of shares of a specific stock on a specified date from their portfolio
3) Calculate the cost basis of a portfolio on a specific date. This includes brokers commission during both purchase and sell transactions
4) Allow a user to determine the value of their portfolio on a specific date
5) The user can enter the commission fee for each transaction. It is fixed per transaction
6) The program has been integrated with the AlphaVantage Web API through which stock data is queried
7) Portfolio performance can be visualized through a barchart across a range of dates


The features that have been implemented and tested in the Assignment 4 are:
1) Allow a user to create one or more portfolios with shares of one or more stock. Once created, shares
cannot be added or removed from the portfolio
    - the files used to save and retrieve the portfolios is in a human-readable format (CSV)
    - we can add multiple stocks and its quantity accordingly as there is sub menu to add stocks
    - a stock can be added multiple times with different quantity would be appended automatically to the current stock data
2) Examine the composition of a portfolio
    - displays the ID, Ticker/Stock symbol, No. of Stocks in the portfolio in a table formatted view.
3) Determine the total value of a portfolio on a certain date
   - calculates the value of the portfolio for a given date
     - if date is not given then the software considers the current date 
       - if stock data for the current date is not found the latest available data is considered
       - else last available close value of the stock from the list is fetched to calculate portfolio value.