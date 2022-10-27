import controller.IPortfolioController;
import controller.PortfolioController;
import model.IPortfolioModel;
import model.PortfolioModel;
import repository.CSVPortfolioRepository;
import view.IPortfolioView;
import view.PortfolioTextView;

public class PortfolioRunner {

  public static void main(String[] args) {
    IPortfolioModel model = new PortfolioModel(new CSVPortfolioRepository());
    IPortfolioView view = new PortfolioTextView(System.out);
    IPortfolioController controller = new PortfolioController(model, view, System.in);

    controller.go();
  }
}
