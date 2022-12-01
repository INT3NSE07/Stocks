package view;

import controller.IPortfolioFeatures;
import java.io.IOException;

public interface IGUIPortfolioView extends IPortfolioView {

  void addFeatures(IPortfolioFeatures features) throws IOException;

  void resetFocus();

  void clearInputString();

}
