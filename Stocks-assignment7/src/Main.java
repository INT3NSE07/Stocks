import java.text.ParseException;

import stocks.controller.PortfolioController;
import stocks.controller.PortfolioControllerImpl;
import stocks.controller.PortfolioUIController;
import stocks.model.PortfolioModel;
import stocks.model.PortfolioModelImpl;
import stocks.view.PortfolioJFrameViewImpl;
import stocks.view.PortfolioUIView;
import stocks.view.PortfolioView;
import stocks.view.PortfolioViewImpl;

/**
 * This class holds the main function for the program.
 */
public class Main {

  /**
   * Main function called to start the program, define the model, view and controller and pass
   * the control to the controller.
   *
   * @param args arguments passed while running the program
   */
  public static void main(String[] args) throws ParseException {
    PortfolioModel model = new PortfolioModelImpl();
    if (args.length > 0 && args[0].equalsIgnoreCase("Text")) {
      PortfolioView view = new PortfolioViewImpl(System.out);
      PortfolioController controller = new PortfolioControllerImpl(System.in, view, model);
      controller.start();
    } else {
      PortfolioUIView uIView = new PortfolioJFrameViewImpl("Portfolio Assignment");
      PortfolioUIController uIController = new PortfolioUIController(model);
      uIController.setView(uIView);
    }
  }
}
