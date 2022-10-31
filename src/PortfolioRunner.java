import controller.IPortfolioController;
import controller.PortfolioController;
import io.CSVReaderImpl;
import io.CSVWriterImpl;
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

public class PortfolioRunner {

  public static void main(String[] args) {
    IReader<List<List<String>>> reader = new CSVReaderImpl();
    IWriter<List<String>> writer = new CSVWriterImpl();

    IStockService stockService = FileStockService.getInstance(reader);

    IRepository<Portfolio> repository = new CSVPortfolioRepository(reader, writer);

    IPortfolioModel model = new PortfolioModel(repository, stockService);
    IPortfolioView view = new PortfolioTextView(System.out);
    IPortfolioController controller = new PortfolioController(model, view, System.in);

    controller.run();
  }
}
