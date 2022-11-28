import constants.Constants;
import controller.IPortfolioController;
import controller.PortfolioController;
import controller.PortfolioGUIController;
import io.CSVReader;
import io.CSVWriter;
import io.IReader;
import io.IWriter;
import io.GenericWriter;

import java.io.ByteArrayInputStream;
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
   */
  public static void main(String[] args) throws IOException {
    IReader<List<List<String>>> reader = new CSVReader();
    IWriter<List<String>> writer = new GenericWriter(new CSVWriter());

    IStockService stockService = AlphaVantageStockService.getInstance(reader);

    IRepository<Portfolio> repository = new CSVPortfolioRepository(reader, writer,
            Constants.DATA_DIR);
    IFlexiblePortfolioModel flexiblePortfolioModel = new FlexiblePortfolioModel(repository,
            stockService);
    IPortfolioModel inflexiblePortfolioModel = new PortfolioModel(repository, stockService);
    IPortfolioFacadeModel portfolioFacadeModel = new PortfolioFacadeModel(flexiblePortfolioModel,
            inflexiblePortfolioModel, repository);

    if (args.length == 0) {
      System.out.println("Please enter valid view to render");
      System.exit(0);
    } else if (args[0].equals("gui")) {
      IGUIPortfolioView view = new JPortfolioView(2);
      PortfolioGUIController controller = new PortfolioGUIController(portfolioFacadeModel,
              view, new ByteArrayInputStream(new byte[]{}));
    } else if (args[0].equals("text-ui")) {
      IPortfolioView view = new PortfolioTextView(System.out);
      IPortfolioController controller = new PortfolioController(portfolioFacadeModel, view,
              System.in);
      controller.run();
    } else {
      System.out.println("Please enter valid view to render");
      System.exit(0);
    }
  }
}
