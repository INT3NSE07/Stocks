package view;

import java.io.IOException;

import controller.IPortfolioFeatures;

public interface IGUIPortfolioView extends IPortfolioView {

  void addFeatures(IPortfolioFeatures features) throws IOException;

  void resetFocus();

  void clearInputString();

}
