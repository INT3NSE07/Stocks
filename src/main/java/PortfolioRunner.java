import constants.Constants;
import controller.IPortfolioController;
import controller.PortfolioController;
import io.CSVReader;
import io.CSVWriter;
import io.IReader;
import io.IWriter;
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
import view.IPortfolioView;
import view.PortfolioTextView;

/**
 * This class contains the main method which is the entry point for the portfolio project to run.
 */
public class PortfolioRunner {

  /**
   * The entry point for the portfolio project.
   *
   * @param args the commandline arguments
   */
  public static void main(String[] args) {
    IReader<List<List<String>>> reader = new CSVReader();
    IWriter<List<String>> writer = new CSVWriter();

    IStockService stockService = AlphaVantageStockService.getInstance(reader);

    IRepository<Portfolio> repository = new CSVPortfolioRepository(reader, writer,
        Constants.DATA_DIR);

    IPortfolioView view = new PortfolioTextView(System.out);

    IFlexiblePortfolioModel flexiblePortfolioModel = new FlexiblePortfolioModel(repository,
        stockService);
    IPortfolioModel inflexiblePortfolioModel = new PortfolioModel(repository, stockService);
    IPortfolioFacadeModel portfolioFacadeModel = new PortfolioFacadeModel(flexiblePortfolioModel,
        inflexiblePortfolioModel, repository);
    IPortfolioController controller = new PortfolioController(portfolioFacadeModel, view,
        System.in);

    controller.run();
  }
}
