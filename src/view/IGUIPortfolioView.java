package view;

import java.awt.event.ActionListener;
import java.io.IOException;

import controller.Features;

public interface IGUIPortfolioView extends IPortfolioView {

  void addFeatures(Features features) throws IOException;

  String getOption();

  void resetFocus();

  String getPortfolioNameInputString();

  void clearInputString();

}
