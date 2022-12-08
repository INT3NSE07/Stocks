For this project we have Package stocks, META-INF <Jar File>, and Test folder.


Under the stocks Package:
    1. The MVC Sub-Packages < Model, View, and Controller>
    2. Under the Controller --> The commands Package, The actionlisteners Package and features
    interface and implementation.
    3. Portfolio Interface / PortfolioImpl / FlexiblePortfolio / AbstractPortfolio classes
    4. Main
    5. Test folder <resources, stocks folders>

Design Changes <ASSIGNMENT 6>:
    1. We created a new interface for the GUI, so that we could implement the GUI separated from the
     text view.
    2. We created a new interface Features, which acted as our new controller for the GUI view which
     is all implemented on the PortfolioUIController.
    3. We added the new methods for the new functionalities on the Portfolio interface to support
     investment and Dollar Cost AVerging and added these to our model.
    4. Added the Bar chart on the UI view.

Design Changes <ASSIGNMENT 5>:

    1. Controller the design changed from a switch case into the command design patter with a
    package called commands that has an interface called PortfolioCommand which is implemented in
    an Abstract Command class, and that abstract class is inherited by every command object.
    2. Changed the controller from a switch case to have in a map that holds all the commands and the
     keys that can be used by the user to execute.
    3. In the Portfolio Interface; we change some signatures to include a date parameter
    accommodate the date parameter, which is disregarded for the tradition inflexible portfolio.
    4. On the Portfolio Interface; we added methods to accommodate new features for the Flexible
    Portfolio, and all were defined on the FlexiblePortfolio class. Thus, for those new methods they
     are handled in a way that when the user attempts to call them on the traditional one that
     are informed that they are operations only for Flexible Portfolio.
    4. Created an Abstract portfolio to avoid code redundancy, that has methods to call the API which
     can be extended for multiple APIs as well.
    5. For Portfolio Model we added new methods to accommodate those in Flexible Portfolio and changed
     some method signatures to accommodate the date parameter, which is disregard for the tradition
     inflexible portfolio.

Controller sub package:

    - Since Controller the design changed from a switch case into the command design patter with a
    package called commands that has an interface called PortfolioCommand which is implemented in
    an Abstract Command class, and that abstract class is inherited by every command object.
    - It contains both the PortfolioController interface and PortfolioControllerImpl classes.
    - On this level we defined the start method, which loops through the user's inputs until the
    user passed "Q" as an option.
    - And based on the case passed, it calls the appropriate helper private methods.
    - Since the Start method is the one needed to start the program, it's implemented as public.
    and the rest are defined as private as they are only used when called within the method.
    - Takes the user input, here it decides if it should be passed to the model or the view.
    - The Features interface where all the GUI methods are stored and implemented on the
    PortfolioUIController.

        <Commands> Package:
        - This has an interface called PortfolioCommand which is implemented in
         an Abstract Command class, and that abstract class is inherited by every command object.
        - This is where we have all available commands which are then called in the controller

        <Actionlisteners> Package:
         - This is where we implement all the action listener classes based on the functionality it's
          used for, as in for all the buttons that perform action.




Model sub package:
    - It contains both the PortfolioModel interface and PortfolioModelImpl classes.
    - On this level we have access to the PortfolioImpl class where we call the method based on what
     we are trying to do on each method.
    - We maintain a list of all portfolios created in a session.
    - Here we call the PortfolioImpl constructor to create a portfolio object using the user's input.


View sub package:
    - On this level the view prints the composition, shows messages that the controller decides
    and shows all the options to the user to select from.


    <Dialogs> Subpackage:
    - This is where the all the different dialogs supported are implemented.
    - All the dialogs that would all the users to perform any actions.

PortfolioImpl:
    - On this level we have all implementation of the supported options for the user.
    - It stores the stocks in a map, with keys of tickers and values number of shares so that it
    doesn't store the same stock multiple times.
    - Using the constructor, it detects the input file type and calls the corresponding parser based
     on the file extensions.
    - It supports viewStocks, where it returns the stocks stored in a portfolio.
    - getNumberOfShares, it returns the number of shares within a portfolio.
    - getValue of a stock, uses an API call for the ticker provided to get the value of it using the
     date passed.
    - removeInvalidTickers, if the ticker is not on the NASDAQ list it doesn't get added to the
    portfolio.
    - removeFractionalShares, to avoid adding fractional shares, here it rounds it down to the
    nearest integer.

FlexiblePortfolio:
    - On this level we handle storing Flexible portfolio.
    - On this level we have all implementation of the supported options for the user.
    - It stores the stocks in a map, with keys of tickers and values as a map of dates as a key and
    number of shares as value.
    - Every time a user buy or sell it updates the main map of stocks.
    - Using the constructor, it detects the input file type and calls the corresponding parser based
     on the file extensions.
    - It supports viewStocks, where it returns the stocks stored in a portfolio.
    - getNumberOfShares, it returns the number of shares within a portfolio.
    - getValue of a stock, uses an API call for the ticker provided to get the value of it using the
     date passed.
    - removeInvalidTickers, if the ticker is not on the NASDAQ list it doesn't get added to the
    portfolio.
    - removeFractionalShares, to avoid adding fractional shares, here it rounds it down to the
    nearest integer.
    - It supports buyStock which will allow the user to add a stock if it exists already
     it will create a new entry to the inner map with the most recent date and the most recent
     cumulative number of stocks assumes that the date purchased is the day the user enters this.
    - It supports sellStock which allows the user to sell a stock on a specific date and enters a
    new entry to the inner map that has the new number of stocks if that exists.
    - It has the CostBasis map that stores the total cost basis on all entries of the portfolio, and
     gets called by the kep which is the date.
    - Supports getting the value of a portfolio on a given date.
    - Supports having a graph for performance on the data of the portfolio.
    - Supports the ability that the user can invest on an existing portfolio, which allows
    fractional shares in this case.
    - Supports the ability that the user can also call the Dollar Cost averaging on a existing
    Portfolio or create a new one from start to end. Which allows fractional shares to be bought.



