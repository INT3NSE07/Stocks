import constants.Constants;
import controller.IPortfolioController;
import controller.PortfolioController;
import controller.PortfolioGUIController;
import io.CSVReader;
import io.CSVWriter;
import io.IReader;
import io.IWriter;
import java.io.IOException;
import java.util.List;
import model.FlexiblePortfolioModel;
import model.IFlexiblePortfolioModel;
import model.IPortfolioFacadeModel;
import model.IPortfolioModel;
import model.Portfolio;
import model.PortfolioFacadeModel;
import model.PortfolioModel;
import repository.CSVPortfolioRepository;
import repository.IRepository;
import service.AlphaVantageStockService;
import service.IStockService;
import view.IGUIPortfolioView;
import view.IPortfolioView;
import view.JPortfolioView;
import view.PortfolioTextView;

/**
 * This class contains the main method which is the entry point for the portfolio project to run.
 */
public class PortfolioRunner {

  /**
   * The entry point for the portfolio project.
   *
   * @param args the commandline arguments
   * @throws IOException when an unexpected I/O error is encountered
   */
  public static void main(String[] args) throws IOException {
    IReader<List<List<String>>> reader = new CSVReader();
    IWriter<List<String>> writer = new CSVWriter();

    IStockService stockService = AlphaVantageStockService.getInstance(reader);

    IRepository<Portfolio> repository = new CSVPortfolioRepository(reader, writer,
        Constants.DATA_DIR);

    IFlexiblePortfolioModel flexiblePortfolioModel = new FlexiblePortfolioModel(repository,
        stockService);
    IPortfolioModel inflexiblePortfolioModel = new PortfolioModel(repository, stockService);
    IPortfolioFacadeModel portfolioFacadeModel = new PortfolioFacadeModel(flexiblePortfolioModel,
        inflexiblePortfolioModel, repository);

    // supported views
    List<String> views = List.of("text", "gui");
    if (args.length == 0 || !views.contains(args[0])) {
      System.out.printf("Please enter a valid view to render. Valid views are: %s%n",
          String.join(", ", views));
      System.exit(1);
    }

    String viewOption = args[0];
    IPortfolioController controller;
    if (viewOption.equals("gui")) {
      IGUIPortfolioView view = new JPortfolioView();
      controller = new PortfolioGUIController(portfolioFacadeModel, view);
    } else {
      IPortfolioView view = new PortfolioTextView(System.out);
      controller = new PortfolioController(portfolioFacadeModel, view, System.in);
    }

    controller.run();
  }
}
