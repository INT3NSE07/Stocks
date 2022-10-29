import controller.IPortfolioController;
import controller.PortfolioController;
import model.IPortfolioModel;
import model.PortfolioModel;
import repository.CSVPortfolioRepository;
import service.AlphaVantageStockService;
import service.IStockService;
import view.IPortfolioView;
import view.PortfolioTextView;

public class PortfolioRunner {

  public static void main(String[] args) {
    IStockService service = AlphaVantageStockService.getInstance(null);
    IPortfolioModel model = new PortfolioModel(new CSVPortfolioRepository(), service);
    IPortfolioView view = new PortfolioTextView(System.out);
    IPortfolioController controller = new PortfolioController(model, view, System.in);

    controller.run();
  }
}
