# Running the Jar File
- Execute the jar by typing java -jar .\Stocks.jar


# Instructions to Run the Program:
- Below are the sequence of steps for the scenario:
You should also include detailed instructions on how to run your program to create a portfolio, purchase stocks of at least 3 different companies in that portfolio at different dates and then query the value and cost basis of that portfolio on two specific dates.


Enter the type of portfolio
1) Inflexible portfolio
2) Flexible portfolio
3) Exit
Enter your choice: 2

Portfolio Management Services
1) Create a portfolio
2) Examine a portfolio
3) Determine value of a portfolio on a certain date
4) Make a transactions in a portfolio
5) Calculate cost basis
6) Get performance of a portfolio over a period
7) Back
Enter your choice: 1
Enter portfolio name: retirement-flexible
The portfolio retirement-flexible has been created.

Portfolio Management Services
1) Create a portfolio
2) Examine a portfolio
3) Determine value of a portfolio on a certain date
4) Make a transactions in a portfolio
5) Calculate cost basis
6) Get performance of a portfolio over a period
7) Back
Enter your choice: 4
Please enter commission for this instance of transaction: 10

Transaction menu
1) Buy stocks
2) Sell stocks
3) Back
Enter your choice: 1
Enter portfolio name: retirement-flexible
Enter stock symbol: AAPL
Enter quantity: 100
Enter date in format YYYY-MM-DD (if no input is given, the default is current date): 
Stock AAPL bought successfully.

Transaction menu
1) Buy stocks
2) Sell stocks
3) Back
Enter your choice: 1
Enter portfolio name: retirement-flexible
Enter stock symbol: MSFT
Enter quantity: 35
Enter date in format YYYY-MM-DD (if no input is given, the default is current date): 2022-11-01
Stock MSFT bought successfully.

Transaction menu
1) Buy stocks
2) Sell stocks
3) Back
Enter your choice: 1
Enter portfolio name: retirement-flexible
Enter stock symbol: AMZN
Enter quantity: 10
Enter date in format YYYY-MM-DD (if no input is given, the default is current date): 2022-08-01
Stock AMZN bought successfully.

Transaction menu
1) Buy stocks
2) Sell stocks
3) Back
Enter your choice: 3

Portfolio Management Services
1) Create a portfolio
2) Examine a portfolio
3) Determine value of a portfolio on a certain date
4) Make a transactions in a portfolio
5) Calculate cost basis
6) Get performance of a portfolio over a period
7) Back
Enter your choice: 3
Enter portfolio name: retirement-flexible
Enter date in format YYYY-MM-DD (if no input is given, the default is current date): 2022-11-17

Value of the portfolio retirement-flexible
+----+---------------+----------+---------------+-----------+
| ID | Ticker symbol | Quantity | Closing price | Operation |
+----+---------------+----------+---------------+-----------+
| 1  | AAPL          | 100.0    | $150.72       | BUY       |
| 2  | MSFT          | 35.0     | $241.68       | BUY       |
| 3  | AMZN          | 10.0     | $94.85        | BUY       |
+----+---------------+----------+---------------+-----------+

Total value: $24479.30

Portfolio Management Services
1) Create a portfolio
2) Examine a portfolio
3) Determine value of a portfolio on a certain date
4) Make a transactions in a portfolio
5) Calculate cost basis
6) Get performance of a portfolio over a period
7) Back
Enter your choice: 3
Enter portfolio name: retirement-flexible
Enter date in format YYYY-MM-DD (if no input is given, the default is current date): 2022-11-15

Value of the portfolio retirement-flexible
+----+---------------+----------+---------------+-----------+
| ID | Ticker symbol | Quantity | Closing price | Operation |
+----+---------------+----------+---------------+-----------+
| 1  | MSFT          | 35.0     | $241.97       | BUY       |
| 2  | AMZN          | 10.0     | $98.94        | BUY       |
+----+---------------+----------+---------------+-----------+

Total value: $9458.35

Portfolio Management Services
1) Create a portfolio
2) Examine a portfolio
3) Determine value of a portfolio on a certain date
4) Make a transactions in a portfolio
5) Calculate cost basis
6) Get performance of a portfolio over a period
7) Back
Enter your choice: 5
Enter portfolio name: retirement-flexible
Enter date in format YYYY-MM-DD (if no input is given, the default is current date): 2022-11-17
Cost basis of portfolio retirement-flexible on is $24509.30

Portfolio Management Services
1) Create a portfolio
2) Examine a portfolio
3) Determine value of a portfolio on a certain date
4) Make a transactions in a portfolio
5) Calculate cost basis
6) Get performance of a portfolio over a period
7) Back
Enter your choice: 5
Enter portfolio name: retirement-flexible
Enter date in format YYYY-MM-DD (if no input is given, the default is current date): 2022-10-01
Cost basis of portfolio retirement-flexible on is $1140.00

Portfolio Management Services
1) Create a portfolio
2) Examine a portfolio
3) Determine value of a portfolio on a certain date
4) Make a transactions in a portfolio
5) Calculate cost basis
6) Get performance of a portfolio over a period
7) Back
Enter your choice: 7
Going back...

Enter the type of portfolio
1) Inflexible portfolio
2) Flexible portfolio
3) Exit
Enter your choice: 3
Exiting...