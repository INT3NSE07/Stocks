import controller.IPortfolioController;
import controller.PortfolioController;
import model.IPortfolioModel;
import model.PortfolioModel;
import view.IPortfolioView;
import view.PortfolioTextView;

public class PortfolioRunner {

  public static void main(String[] args) {
    IPortfolioModel model = new PortfolioModel();
    IPortfolioView view = new PortfolioTextView(System.out);
    IPortfolioController controller = new PortfolioController(model, System.in, view);

    //controller.go();
  }
}
