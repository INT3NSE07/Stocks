Stocks 
  A program that allows you to create two types of portfolios,
  along with two types of views <Text, GUI>:

  1. GUI View for Flexible Portfolio:
                For the GUI view, as mentioned on the assignment we only support inflexible
                portfolio type. Once called a popout screen opens ups with multiple options to
                select from. It allows a user to use all previously supported functionality on the
                Flexible portfolio using distinct dialogs for each selection. This type of
                portfolio can be created either manually, by passing a file to it or by
                calling buyStocks. It allows the user to make changes to an existing portfolio.
                Our program supports ".CSV" file extension when creating a file using a file.
                When a user has multiple transactions on a specific ticker, it's stored in a way that
                the date of the Transactions (Sell or Buy) is always record and the one that has the
                latest date of that ticker has the most up-to-date values.


                Functionality Supported:
                         - BuyStocks allows the user to purchase a specific Stock with a specific
                         number of shares on a specific date, given a commission fee.


                         - SellStock allows the user to sell a specific Stock with a specific
                         number of shares on a specific date, given a commission fee.

                         - A cost basis function that the total amount of money invested in a portfolio
                         by a specific date.

                         - A method that calculates value of a portfolio on a specific date (
                         to be exact, the end of that day).

                         - A method that allows the user to invest a fixed amount of money on various
                         tickers with a specific percentage of the total for each stock.

                         - A method that allows the user to invest a set amount for set period of time
                         by either providing the date or it not we assume the end date is day the
                         method was called.

                         - A graph method that allows the user to view a performance graph summarize
                          the performance of a portfolio over a specific time frame on a bar chart.


  2. Default Portfolio (InFlexible) Text View:
          This type of portfolio can be created either manually or by passing a file to it.
          Our program supports both ".CSV" and ".XML" files.

          File Extensions Supported:
                  - For .CSV file extensions, the file format must have "ticker, shares" columns,
                  as it will extract the Tickers and shares from each row to add to the portfolio.


                  - For .XML file extensions, it should have the following format:
                      <portfolio>
                      <stock>
                          <ticker>Ticker_Name_EnteredHere</ticker>
                          <shares>Shares_Number_EnteredHere</shares>
                      </stock>
                      </portfolio>


                  - All Tickers passed weather manually or through a file are checked against tickers that
                  are provided by NASDAQ which we have under the package "validTickers.csv". If not on the
                  list, they just simply don't get added to the Portfolio when created.

  3. Flexible Portfolio Text View:
            This type of portfolio can be created either manually, by passing a file to it or by
            calling buyStocks. It allows the user to make changes to an existing portfolio.
            Our program supports ".CSV" file extension when creating a file using a file.
            When a user had multiple transactions on a specific ticker, it's stored in a way that
            the date of the Transactions (Sell or Buy) is always record and the one that has the
            latest date of that ticker has the most up-to-date values.


            File Extension Supported:
                    - For .CSV file extensions, the file format must have "ticker, date, shares"
                    columns, as it will extract the Tickers, date of the transaction(Sell or Buy)
                    and the shares from each row to add to the portfolio.


                    - All Tickers passed weather manually or through a file are checked against tickers that
                    are provided by NASDAQ which we have under the package "validTickers.csv". If not on the
                    list, they just simply don't get added to the Portfolio when created, and it
                    prints a message to the user informing them that the ticker is invalid and
                    wasn't added.

            Functionality Supported:

                    - BuyStocks allows the user to purchase a specific Stock with a specific
                    number of shares on a specific date, given a commission fee.


                    - SellStock allows the user to sell a specific Stock with a specific
                    number of shares on a specific date, given a commission fee.

                    - A cost basis function that the total amount of money invested in a portfolio
                    by a specific date.

                    - A method that calculates value of a portfolio on a specific date (
                    to be exact, the end of that day).



  Features:


   The Program offers the below options in the drop-down for the GUI view:
   For any of the options, if selected by mistake the user can simply click on the "Go Back" button.
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


               Option "Create a new flexible portfolio":
                    - The dialog have the user enter the name of the Portfolio, number of stocks,
                      the Ticker and number of shares respectively. Then the user can click on the
                      "Add to Portfolio Button" for every entry. Once finished the user can click on
                       Create Portfolio.
                    - If the entered Ticker is not in the NASDAQ list, the program will send a
                      message to the user that the ticker passed is not supported with NASDAQ.
                    - The number of shares must be a whole positive number, otherwise teh user will
                      a warning the appropriate message.


                Option "Load a flexible portfolio from file ":
                    - When this is selected it will prompt the user to select the .CVS file they
                    want to load, and the file must have three columns <Ticker, Date, Shares>.
                    - Else it will send a warning message to the user with the appropriate message
                    to allow the user to make another selection for the file or click on go back.
                    -Thus it allows a user to create one or more Flexible portfolios with shares of
                    one or more stock along the dates of purchase or sell of these stocks.


                Option "Create a portfolio with dollar cost averaging":
                    - When this is selected, the user is allowed to create a portfolio manually and
                      set a plan for investment.
                    - This allows creating "start-to-finish" dollar-cost averaging as a single
                      operation.
                    - This will allow the user to select the number of days in between each
                      investment made with the same values, for example monthly will be the number
                      of the month with a calendar check depending on the month it will add the
                      appropriate number of days to satisfy the frequency.

                Option "Buy stocks":
                   - Allows the user to purchase a stock, and add it to an existing portfolio
                   - It will let the user select the Portfolio from teh drop down, and then enter
                     all the info of the Ticker, number of shares and Commission fee.
                   - And if the user purchased a stock using an existing ticker,
                     it will update the value of shares on the one with the most recent date.
                   - The number of shares must be a positive whole integer number, else it prompt a
                     message to user with the approperiate details.




                Option "Invest in a Portfolio":
                 - Prints all Portfolios created by the user so far.
                 - If no Portfolios there it throws an error "No portfolios have been created yet.".



                Option "Sell stocks":

                    - Allows the user to sell a stock from an existing portfolio.
                    - It then checks if the user has enough shares of that ticker or
                    if it even exists in the portfolio.
                    - If the Ticker exists and there's enough shares, it will have an entry on the
                    portfolio with the most up-to-date number of shares of that stock. Even if it's
                    zero to have a record of the sell and have the most updated number of available
                    shares of that stock.
                     - It prompt the user to enter the name of the portfolio it wants to sell from
                     it.
                     - Then the user enters the ticker that they want to sell, the number of
                     shares, year, month, day and the commission fee respectively.
                     - If the ticker doesn't exit it will return the below:
                     No available stocks from this ticker!


               Option "Dollar Cost Averaging on an existing portfolio":

                    - This allows creating  dollar-cost averaging on an existing Portfolio.
                    - This will allow the user to select the number of days in between each
                      investment made with the same values, for example monthly will be the number
                      of the month with a calendar check depending on the month it will add the
                      appropriate number of days to satisfy the frequency.




                Option "Get value of a portfolio":
                - Allows the user to get the value of a portfolio on a specific day.



                Option "Get cost basis":
                - This will return the cost basis for the whole portfolio on the specific date
                passed as a cumulative for all commission fees and buys that happened till that date
                 passed.



                Option "View composition of a portfolio":
                - Shows the user teh composition of the selected portfolio for a partivulat day.


                Option "View Current Portfolios":
                - Shows the user a list of all portfolio.


                Option "Save a portfolio to file":
                - Allows the user to save the portfolio into a file and it shows the path after its
                saved.




    The Program offers the below options for text view:

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



1. When the program is called through a JAR file it prompts a few options to the user to pick from:


   Option 1 "Load portfolio from a file":
   - If this option is selected, it will prompt the user to enter the portfolio name, then the user
   is prompted to enter the file name given the location of the file.
   - If successful it will prompt "Portfolio with name %Portfolio_Name created."
   - Else it will throw an exception if it wasn't able to successfully create the portfolio:
                1. If the format is not as expected, as explained above under the File Extensions Supported section it will throw the below message:
                "Invalid File Format".

                2. If the file type is not supported, it will throw the following message: "The filetype is not supported".

                3. If the program catches an IOException, it will throw the following message: "File
                 does not exist.".

                4. If the Portfolio name already exists it will throw "Portfolio with this name
                exists already. Please try again with a different name.".

   - Thus it allows a user to create one or more portfolios with shares of one or more stock.
   - And once created, shares cannot be added or removed from the portfolio.



    Option 2 "Create portfolio manually":
    - The program will ask the user to enter the name of the Portfolio.
    - The program will ask the user to enter the number of stocks they would like to pass through.
    - Then the program will proceed by asking for the Ticker and number of shares respectively
    - Once we reach the count passed for the number of the stocks, it will stop and return the
    message that the portfolio has been created.
    - If the number of shares passed is not numerical, it throws an error and ask the user to start
    over.
    - If the ticker passed is not included on the Nasdaq list is not added when the Portfolio is
    created.


    Option 3 "Check value of portfolio":
    - When called on a Flexible Portfolio it will return the value of a portfolio.
    - On this option the user is able to determine the total value of a portfolio on a certain date.
    - It first prompts the user to enter the Portfolio Name.
    - If the Portfolio name doesn't exist, the program errors out if the Portfolio name passed does
    not exist "Portfolio of name %NamePassed does not exist. "
    - Else, it prompts the user to enter year, month and day respectively.
        * If the date entered is invalid or falls under a holiday or it's the current day before 4
        pm it throws an error that no data was found for that day.
        * Else it prints the value of the total shares in the Portfolio for the closing price of the
         date provided

    Option 4 "View composition of a portfolio":
    - Under this option the program allows a user to examine the composition of a portfolio.
    - It simply asks for a Portfolio name and if that portfolio existed, it prints the values
    (Ticker, Shares).
    - Else if the portfolio name passed doesn't exist, it throws an error "Portfolio of name
    %pName_Passed does not exist.".

    Option 5 "Save portfolio to a file":
    - Persist a portfolio so that it can be saved and loaded (i.e. save to and retrieve from files).
    - It prompts the user to enter the portfolio name, and it saves the portfolio into a .CSV in the same
    folder where the jar file is saved as portfolioName.csv
    with the columns: ticker, shares for traditional Portfolio
    - When called on a Flexible Portfolio, it will do the same but with the columns: ticker,
    date.
    - If the portfolio doesn't exist it will throw an error with "Portfolio with given name does not
     exist".


    Option 6 "View all portfolio names":
     - Prints all Portfolios created by the user so far.
     - If no Portfolios there it throws an error "No portfolios have been created yet.".



    Option 7 "Create flexible portfolio manually":
    - The program will ask the user to enter the name of the Portfolio.
    - The program will ask the user to enter the number of stocks they would like to pass through.
    - Then the program will proceed by asking for the Ticker and number of shares respectively
    - Once we reach the count passed for the number of the stocks, it will stop and return the
    message that the portfolio has been created.
    - The program will automatically use the day the user enters their stocks manually to be the
    date of purchase when it saved into a flexible portfolio.
    - If the number of shares passed is not numerical, it throws an error and ask the user to start
    over.
    - If the ticker passed is not included on the Nasdaq list is not added when the Portfolio is
    created.


    Option 8 "Load flexible portfolio from file":
   - If this option is selected, it will prompt the user to enter the portfolio name, then the user
   is prompted to enter the file name given the location of the file.
   - If successful it will prompt "Portfolio with name %Portfolio_Name created."
   - Else it will throw an exception if it wasn't able to successfully create the portfolio:
                1. If the format is not as expected, as explained above under the File Extensions Supported section it will throw the below message:
                "Invalid File Format".

                2. If the file type is not supported, it will throw the following message: "The filetype is not supported".

                3. If the program catches an IOException, it will throw the following message: "File
                 does not exist.".

                4. If the Portfolio name already exists it will throw "Portfolio with this name
                exists already. Please try again with a different name.".

   - Thus it allows a user to create one or more Flexible portfolios with shares of one or more
   stock along the dates of purchase or sell of these stocks.



    Option 9 "Buy stock for a flexible portfolio:
    - Allows the user to purchase a stock, and add it to an existing portfolio
    - And if the user purchased a stock using an existing ticker, it will update the value of shares
     on the one with the most recent date.
     - It prompt the user to enter the name of the portfolio it wants to add to it
     - Then asks the name of the ticker that the user wants to buy, the number of shares and the
     commission fee respectively.
     - It will then return the map of stocks on that same date in the below form as an example:
     {AAPL={2022-11-17=255}, BBC={2022-11-17=200}}



    Option 10 "Sell stock for a flexible portfolio":
    - Allows the user to sell a stock from an existing portfolio.
    - It then checks if the user has enough shares of that ticker or if it even exists in the
    portfolio.
    - If the Ticker exists and there's enough shares, it will have an entry on the portfolio with
    the most up-to-date number of shares of that stock. Even if it's zero to have a record of the
    sell and have the most updated number of available shares of that stock.
     - It prompt the user to enter the name of the portfolio it wants to sell from it
     - Then asks the name of the ticker that the user wants to sell, the number of shares, year,
     month, day and the commission fee respectively.
     - If the ticker doesn't exit it will return the below:
     No available stocks from this ticker!
     - If the sale went through it will return something similar to the below:
     Selling 10 shares of AAPL on  2022-11-17



    Option 11 "View cost basis of a flexible portfolio":
    - This will return the cost basis for the whole portfolio on the specific date passed as a
    cumulative for all commission fees and buys that happened till that date passed.


    Option 12 "View performance of portfolio over time":
    - This feature will allow the users to set daily, monthly, yearly to pick from.
    - Once the user selects their pick the year, month and day for start and end date respectively.



    Option Q "Exit the Program":
    -Once that is selected the user will exit the program.







