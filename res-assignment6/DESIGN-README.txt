This project has been implemented in a generic way and leverages multiple design patterns.

Assignment 6 design changes:
Visitor design pattern:
  Implemented visitor design pattern for incoporating investment startagies into the system. As investment stratgies are portfolio agnostic, segregating them would help make it easier if a new portifolio type or a new investment strategy is introduced in the future. This pattern was implmented through the IPortfolioInvestmentStrategyVisitor interface which has only one method applyStrategy(). This interface is implemented by FixedCostVisitor and DollarCostAveragingVisitor which are the two types of investment startagies currently supported. If a new investment strategy is introduced in the future, it just has to implement this interface. Similarly if a new type of portifolio is introduced, an overload can be added to this interface which takes a parameter of the new portfolio type. A method acceptInvestmentStrategy has been added to IFlexiblePortfolioModel to mark itself as visitable.

Assignment 5 design:
Facade design pattern:
   As there may be multiple types of portfolio (flexible, inflexible), a PortfolioFacade has been implemented which has all the required methods by different types of portfolios. There are checks in place which prevent an inflexible portfolio from calling methods specific to a flexible portfolio. The PortfolioFacade is responsible for mapping the methods called by the controller to the corresponding model implementation.

Command design pattern:
  The Command pattern has been implemented in the controller. All the commands have been neatly segregated into their own classes which makes it easier to scale in the future.

Model interface design:
  The original IPortfolioModel interface has not been changed. A new IFlexiblePortfolioModel extends this interface and adds the methods needed for new functionality. It also overrides a few methods in IPortfolioModel

AlphaVantage service:
  To query stock data, the program utilizes AlphaVantage's REST endpoints. The responses are cached in-memory to prevent frequent API calls 

Assignment 4:
1) IWriter<T> is a generic interface for writing any format of data to an OutputStream. Currently, this interface has been implemented by CSVWriter which writes the provided data in CSV format. In the future, the IWriter interface can be implemented to write any other formats of data. Ex: IWriter can be implemented by JsonWriter, XMLWriter, etc.
2) IReader<T> is a generic interface for reading any format of data from an InputStream. Currently, this interface has been implemented by CSVReader which reads and parses data in CSV format. In the future, the IReader interface can be implemented to read any other formats of data. Ex: IReader can be implemented by JsonReader, XMLReader, etc.
3) IRepository<T> is a generic implementation of the Repository pattern. It can perform CRUD operations on an item. Data persistence is handled without the need to know any details about where the data is actually persisted. Currently it is implemented by CSVPortfolioRepository which handles the creation, read, update of the portfolio entity.
4). IStockService represents a stock service which is used to fetch stock data from various sources. It is implemented by AbstractStockService which has two abstract methods namely getInputStream() and getResponseToStockMapper(). The getInputStream() delegates the responsibility of returning the stream of data from whichever source is used by the child classes. As the data stream may have different formats, the child classes implement the getResponseToStockMapper() method and provide a response -> stock mapper. In the future, if there are other implementations which read stock data from other sources like a database, they just need to implement these two methods. Currently, IStockService has two concrete implementations. FileStockService loads stock data from a file in the filesystem and AlphaVantageStockService loads stock data by invoking the AlphaVantage REST API. To prevent multiple instantiations of the service, a singleton pattern has been implemented. 
5). As the Stock model has a large number of properties, we implemented the builder design pattern
6). The IPortfolioModel implements all the actual functionality of the system. All the methods in the model accept the portFolioName as an argument. The Portfolio object is returned to the caller by readPortfolio() and getPortfolioValueOnDate(). Although this Portfolio object can be mutated by the caller, it does not change the internal state of the model.
7) The PortfolioRunner is the entry point of the system. Due to the generic design and the dependency on interfaces, various components of the system can be switched easily. For instance, consider the following scenarios which may arise in the future:
Scenario 1: Switch the format of the data being stored to JSON. The only changes which would be required are:
  Implement JSONWriter by implementing the IWriter interface
  Implement JSONReader by implementing the IReader interface
  Implement JSONPortfolioRepository to handle CRUD operations of the portfolio entity in JSON format
  In the PortfolioRunner's main() method, switch the CSVWriter with JSONWriter and CSVReader with JSONReader

Scenario 2: Fetch data dynamically from AlphaVantage's API. The only changes which would be required are:
  Change FileStockService.getInstance() to AlphaVantageStockService.getInstance()