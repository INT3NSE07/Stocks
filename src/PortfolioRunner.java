import controller.IPortfolioController;
import controller.PortfolioController;
import csv.ICSVReader;
import csv.PortfolioCSVReader;
import model.IPortfolioModel;
import model.Portfolio;
import model.PortfolioModel;
import repository.CSVPortfolioRepository;
import repository.IRepository;
import service.FileStockService;
import service.IStockService;
import view.IPortfolioView;
import view.PortfolioTextView;

public class PortfolioRunner {

  public static void main(String[] args) {
    ICSVReader reader = new PortfolioCSVReader();
    IStockService stockService = FileStockService.getInstance(reader);
    IRepository<Portfolio> repository = new CSVPortfolioRepository();

    IPortfolioModel model = new PortfolioModel(repository, stockService);
    IPortfolioView view = new PortfolioTextView(System.out);
    IPortfolioController controller = new PortfolioController(model, view, System.in);

    controller.run();
  }
}
