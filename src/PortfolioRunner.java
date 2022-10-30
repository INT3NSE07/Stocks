import controller.IPortfolioController;
import controller.PortfolioController;
import csv.PortfolioCSVReader;
import model.IPortfolioModel;
import model.PortfolioModel;
import repository.CSVPortfolioRepository;
import service.AlphaVantageStockService;
import service.FileStockService;
import service.IStockService;
import view.IPortfolioView;
import view.PortfolioTextView;

public class PortfolioRunner {

  public static void main(String[] args) {
    IStockService service = FileStockService.getInstance(new PortfolioCSVReader());
    IPortfolioModel model = new PortfolioModel(new CSVPortfolioRepository(), service);
    IPortfolioView view = new PortfolioTextView(System.out);
    IPortfolioController controller = new PortfolioController(model, view, System.in);

    controller.run();
  }
}
