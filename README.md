# Stocks

Stocks is a portfolio management system.
The features that have been implemented and tested in the current iteration are:
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

# Future Improvements
1) Currently, our system only supports stocks in S&P 500 list. In the future we would like to extend it to include all stocks.
2) A user can enter any number of stocks (upto Integer.INT_MAX), In the future, based on the market cap of a company, a validation can be added
3) The controller can be refactored to use the Command design pattern