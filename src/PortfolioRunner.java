import constants.CSVConstants;
import constants.Constants;
import controller.IPortfolioController;
import controller.PortfolioController;
import io.CSVReader;
import io.CSVWriter;
import io.IReader;
import io.IWriter;
import java.util.List;
import model.IPortfolioModel;
import model.Portfolio;
import model.PortfolioModel;
import repository.CSVPortfolioRepository;
import repository.IRepository;
import service.FileStockService;
import service.IStockService;
import view.IPortfolioView;
import view.PortfolioTextView;

/**
 * This class contains the main method which is the entry point for the portfolio project to run.
 */
public class PortfolioRunner {

  /**
   * The entry point for the portfolio project
   *
   * @param args the commandline arguments
   */
  public static void main(String[] args) {
    IReader<List<List<String>>> reader = new CSVReader();
    IWriter<List<String>> writer = new CSVWriter();

    IStockService stockService = FileStockService.getInstance(reader,
        Constants.STOCK_DATA_PATH + CSVConstants.EXTENSION);

    IRepository<Portfolio> repository = new CSVPortfolioRepository(reader, writer,
        Constants.DATA_DIR);

    IPortfolioModel model = new PortfolioModel(repository, stockService);
    IPortfolioView view = new PortfolioTextView(System.out);
    IPortfolioController controller = new PortfolioController(model, view, System.in);

    controller.run();
  }
}
