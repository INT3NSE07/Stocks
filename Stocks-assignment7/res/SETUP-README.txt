The Stocks Program Setup:

Third Party libraries we are using: JFreeChart.
This is licensed under the GNU Lesser General Public License.
Verification Links: https://www.jfree.org/jfreechart/
https://github.com/jfree/jfreechart/tree/v1.5.2

To Set-up the program in IntelliJ the following steps can be performed:
1. Navigate to File > Project Structure
2. Under Project Settings select Libraries and press on the ‘+’ icon.
3. Select Java and point to the jfreechart-1.5.3.jar file included in the submission zip file.
4. The JFreeChart should show up in the External Libraries in IntelliJ while the project is open.
5. Run the program.



Steps to run the program:
1. Navigate to where the Jar Stocks.jar file is saved.
   And make sure that the jfreechart-1.5.3.jar file to be in the same directory that the Stocks.jar
   file is in.
2. Run the file using the commands:
   For GUI  view   -> java -jar Stocks.jar
   For text view   -> java -jar Stocks.jar text

3. If the GUI view selected the popout will be visible to make any selections, per the attached
   screenshot "GUI_SCREENSHOT".

   <Options offered on the GUI view >:
           1. "Create a new flexible portfolio"
           2. "Load a flexible portfolio from file "
           3. "Create a portfolio with dollar cost averaging"
           4. "Buy stocks"
           5. "Invest in a Portfolio"
           6. "Sell stocks"
           7. "Dollar Cost Averaging on an existing portfolio"
           8. "Get value of a portfolio"
           9. "Get cost basis"
           10. "View composition of a portfolio"
           11. "View performance of a portfolio over time"
           12. "View Current Portfolios"
           13. "Save a portfolio to file"

   <If the text view is selected the Menu should be printed on the command line as follows>:

       Menu:
       1 Load portfolio from a file
       2 Create portfolio manually
       3 Check value of a portfolio
       4 View composition of a portfolio
       5 Save portfolio to a file
       6 View all portfolio names
       7 Create flexible portfolio manually
       8 Load flexible portfolio from file
       9 Buy stock for a flexible portfolio
       10 Sell stock for a flexible portfolio
       11 View cost basis of a flexible portfolio
       12 View performance of portfolio over time
       Q Exit the program
       Enter your choice to perform action:





<<PREVIOUS SETUP>>
1. Navigate to where the Jar Stocks.jar file is saved.
2. Run the file using the command: java -jar Stocks.jar
3. The Menu should be printed on the command line as follows:
THE NEW SETUP:
Menu:
1 Load portfolio from a file
2 Create portfolio manually
3 Check value of a portfolio
4 View composition of a portfolio
5 Save portfolio to a file
6 View all portfolio names
7 Create flexible portfolio manually
8 Load flexible portfolio from file
9 Buy stock for a flexible portfolio
10 Sell stock for a flexible portfolio
11 View cost basis of a flexible portfolio
12 View performance of portfolio over time
Q Exit the program


4.
Menu:
1 Load portfolio from a file
2 Create portfolio manually
3 Check value of a portfolio
4 View composition of a portfolio
5 Save portfolio to a file
6 View all portfolio names
7 Create flexible portfolio manually
8 Load flexible portfolio from file
9 Buy stock for a flexible portfolio
10 Sell stock for a flexible portfolio
11 View cost basis of a flexible portfolio
12 View performance of portfolio over time
Q Exit the program
Enter your choice to perform action: 9

Enter name of portfolio you want to buy stocks for:
test
Enter ticker of stock you wish to purchase
AAPL
Enter number of shares you want to purchase:
200
Enter is the commission fee paid for buying this:
50.0
{AAPL={2022-11-17=210}}




Menu:
1 Load portfolio from a file
2 Create portfolio manually
3 Check value of a portfolio
4 View composition of a portfolio
5 Save portfolio to a file
6 View all portfolio names
7 Create flexible portfolio manually
8 Load flexible portfolio from file
9 Buy stock for a flexible portfolio
10 Sell stock for a flexible portfolio
11 View cost basis of a flexible portfolio
12 View performance of portfolio over time
Q Exit the program
Enter your choice to perform action: 9

Enter name of portfolio you want to buy stocks for:
test
Enter ticker of stock you wish to purchase
BBC
Enter number of shares you want to purchase:
100
Enter is the commission fee paid for buying this:
40.9
{AAPL={2022-11-17=210}, BBC={2022-11-17=100}}

Menu:
  1 Load portfolio from a file
  2 Create portfolio manually
  3 Check value of a portfolio
  4 View composition of a portfolio
  5 Save portfolio to a file
  6 View all portfolio names
  7 Create flexible portfolio manually
  8 Load flexible portfolio from file
  9 Buy stock for a flexible portfolio
  10 Sell stock for a flexible portfolio
  11 View cost basis of a flexible portfolio
  12 View performance of portfolio over time
  Q Exit the program
  Enter your choice to perform action: 9

  Enter name of portfolio you want to buy stocks for:
  test
  Enter ticker of stock you wish to purchase
  FRSH
  Enter number of shares you want to purchase:
  300
  Enter is the commission fee paid for buying this:
  50.9
  {FRSH={2022-11-17=300}, AAPL={2022-11-17=210}, BBC={2022-11-17=100}}


  Menu:
  1 Load portfolio from a file
  2 Create portfolio manually
  3 Check value of a portfolio
  4 View composition of a portfolio
  5 Save portfolio to a file
  6 View all portfolio names
  7 Create flexible portfolio manually
  8 Load flexible portfolio from file
  9 Buy stock for a flexible portfolio
  10 Sell stock for a flexible portfolio
  11 View cost basis of a flexible portfolio
  12 View performance of portfolio over time
  Q Exit the program
  Enter your choice to perform action: 11

  Enter name for portfolio:
  test
  Enter the year to check value of portfolio for:
  2022
  Enter the month to check value of portfolio for (1-January...12-December):
  11
  Enter the day to check value of portfolio for:
  17
  $36699.48


    Menu:
    1 Load portfolio from a file
    2 Create portfolio manually
    3 Check value of a portfolio
    4 View composition of a portfolio
    5 Save portfolio to a file
    6 View all portfolio names
    7 Create flexible portfolio manually
    8 Load flexible portfolio from file
    9 Buy stock for a flexible portfolio
    10 Sell stock for a flexible portfolio
    11 View cost basis of a flexible portfolio
    12 View performance of portfolio over time
    Q Exit the program
    Enter your choice to perform action: 11

    Enter name for portfolio:
    test
    Enter the year to check value of portfolio for:
    2022
    Enter the month to check value of portfolio for (1-January...12-December):
    11
    Enter the day to check value of portfolio for:
    16
    $0.0





OLD SET UP:
Menu:
 1. Load portfolio from a file
 2. Create portfolio manually
 3. Check value of portfolio
 4. View composition of a portfolio
 5. Save portfolio to a file
 6. View all portfolio names
 Q. Exit the program
 Enter your choice to perform action:



4. Based on the user's preference select 1 for file or 2 for manual Portfolio entry
For testing purposes as mention in the Assignment, select manual entry:
 Enter your choice to perform action 2
 Enter name for portfolio:Test
 Enter number of stocks in this portfolio: 3
 Enter ticker of company to buy shares of: AAPL
 Enter number of shares of company to buy: 100
 Enter ticker of company to buy shares of: JIVE
 Enter number of shares of company to buy: 200
 Enter ticker of company to buy shares of: SCON
 Enter number of shares of company to buy: 30
 Portfolio with name Test created.

 Menu:
 1. Load portfolio from a file
 2. Create portfolio manually
 3. Check value of portfolio
 4. View composition of a portfolio
 5. Save portfolio to a file
 6. View all portfolio names
 Q. Exit the program
 Enter your choice to perform action:2

 Enter name for portfolio:Test2
 Enter number of stocks in this portfolio: 2
 Enter ticker of company to buy shares of: WLB
 Enter number of shares of company to buy: 50
 Enter ticker of company to buy shares of: BBC
 Enter number of shares of company to buy: 300
 Portfolio with name Test2 created.

 Menu:
 1. Load portfolio from a file
 2. Create portfolio manually
 3. Check value of portfolio
 4. View composition of a portfolio
 5. Save portfolio to a file
 6. View all portfolio names
 Q. Exit the program
 Enter your choice to perform action: 3

 Enter name for portfolio: Test
 Enter the year to check value of portfolio for: 2022
 Enter the month to check value of portfolio for (1-January...12-December): 3
 Enter the day to check value of portfolio for: 22
 Checking value of stock on 2022-03-22Value of portfolio is: 16882.0

 Menu:
 1. Load portfolio from a file
 2. Create portfolio manually
 3. Check value of portfolio
 4. View composition of a portfolio
 5. Save portfolio to a file
 6. View all portfolio names
 Q. Exit the program
 Enter your choice to perform action: 3

 Enter name for portfolio: Test2
 Enter the year to check value of portfolio for: 2016
 Enter the month to check value of portfolio for (1-January...12-December): 6
 Enter the day to check value of portfolio for: 20
 Checking value of stock on 2016-06-20Value of portfolio is: 6086.5

 Menu:
 1. Load portfolio from a file
 2. Create portfolio manually
 3. Check value of portfolio
 4. View composition of a portfolio
 5. Save portfolio to a file
 6. View all portfolio names
 Q. Exit the program
 Enter your choice to perform action: Q

 Exits the program



