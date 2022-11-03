# Running the Jar File
- Double-clicking the jar file to open the terminal, where the below operations are performed.


# Instructions to Run the Program:
- Below are the sequence of steps for the scenario:
"to create a portfolio with 3 different stocks, a second portfolio with 2 different stocks and query their value on a specific date."


Portfolio Management Services
1) Create a portfolio
2) Examine a portfolio
3) Determine value of a portfolio on a certain date
4) Exit
Enter your choice: 1
Enter portfolio name: A Retirement Portfolio

Create a portfolio
1) Add a stock to this portfolio
2) Back
Enter your choice: 1
Enter stock symbol (the stock symbol must belong to the list of stocks in S&P 500): AAPL
Enter quantity: 2345

Create a portfolio
1) Add a stock to this portfolio
2) Back
Enter your choice: 1
Enter stock symbol (the stock symbol must belong to the list of stocks in S&P 500): AMZN
Enter quantity: 987

Create a portfolio
1) Add a stock to this portfolio
2) Back
Enter your choice: 1
Enter stock symbol (the stock symbol must belong to the list of stocks in S&P 500): MSFT
Enter quantity: 156

Create a portfolio
1) Add a stock to this portfolio
2) Back
Enter your choice: 2
The portfolio A Retirement Portfolio has been created.

Portfolio Management Services
1) Create a portfolio
2) Examine a portfolio
3) Determine value of a portfolio on a certain date
4) Exit
Enter your choice: 1
Enter portfolio name:  A College-Savings Portfolio

Create a portfolio
1) Add a stock to this portfolio
2) Back
Enter your choice: 1
Enter stock symbol (the stock symbol must belong to the list of stocks in S&P 500): AMZN
Enter quantity: 2345

Create a portfolio
1) Add a stock to this portfolio
2) Back
Enter your choice: 1
Enter stock symbol (the stock symbol must belong to the list of stocks in S&P 500): AAPL
Enter quantity: 566

Create a portfolio
1) Add a stock to this portfolio
2) Back
Enter your choice: 2
The portfolio  A College-Savings Portfolio has been created.

Portfolio Management Services
1) Create a portfolio
2) Examine a portfolio
3) Determine value of a portfolio on a certain date
4) Exit
Enter your choice: 3
Enter portfolio name:  A College-Savings Portfolio
Enter date with format(YYYY-MM-DD) (if no input is given, the default is current date): 2022-11-02

Value of the portfolio  A College-Savings Portfolio on 2022-11-02
+----+---------------+----------+---------------+
| ID | Ticker symbol | Quantity | Closing price |
+----+---------------+----------+---------------+
| 1  | AAPL          | 566.0    | 145.03        |
| 2  | AMZN          | 2345.0   | 92.12         |
+----+---------------+----------+---------------+

Total value: $298108.38

Portfolio Management Services
1) Create a portfolio
2) Examine a portfolio
3) Determine value of a portfolio on a certain date
4) Exit
Enter your choice: 3
Enter portfolio name: A Retirement Portfolio
Enter date with format(YYYY-MM-DD) (if no input is given, the default is current date): 2022-11-02

Value of the portfolio A Retirement Portfolio on 2022-11-02
+----+---------------+----------+---------------+
| ID | Ticker symbol | Quantity | Closing price |
+----+---------------+----------+---------------+
| 1  | MSFT          | 156.0    | 220.1         |
| 2  | AAPL          | 2345.0   | 145.03        |
| 3  | AMZN          | 987.0    | 92.12         |
+----+---------------+----------+---------------+

Total value: $465353.39

Portfolio Management Services
1) Create a portfolio
2) Examine a portfolio
3) Determine value of a portfolio on a certain date
4) Exit
Enter your choice: 4
Exiting...

# List of Stocks that supports the program
- List of stocks in S&P 500 like AMZN(Amazon), AAPL(Apple), MSFT(Microsoft), etc..
- We have support for stock values till dates 2022-11-02 any date after this will fetch the latest day value in the stock.



