# Stocks

Stocks is a project the that is initially developed to create a portfolio management system.
Features for this project at this iteration are:
1) Creating a Portfolio 
    - we can create a human-readable csv file for the purchased stocks that automatically persists in the File System. 
    - we can add multiple stocks and its quantity accordingly as there is sub menu to add stocks.
    - a same stock can be added multiple times with different quantity would be appended automatically to the current stock data.
    - a portfolio with null stocks does not create a portfolio file in the file system as there need to be stocks in a portfolio
2) Examine Portfolio
    - displays the ID, Ticker/Stock symbol, No. of Stocks in the portfolio in a table formatted view.
3) Get Value of Portfolio
   - calculates the value of the portfolio for a given date
     - if date is not given then the software computes to the current date 
       - if current date is found in the fetch details of list of stocks that close value would be taken into consideration to calculate portfolio value.
       - else last available close value of the stock from the list is fetched to calculate portfolio value.
    - else if date is given tries to fetch close values for that particular date and computes the portfolio value.

# Future Improvements
1) Currently, our system is designed to develop keeping in mind to support only stocks in S&P 500. In Future We would like to extend the support for all stocks.
2) did not add testing for view.showPortfolio, view.showPortfolioValue