package stocks.controller.actionlisteners;

import java.awt.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioUIView;
import stocks.view.dialogs.BarChart;

/**
 * Class listener that waits for the "ViewPerformance" button to be pressed on.
 * That's the button that the user clicks the get the performance on an existing portfolio.
 * And validates all the user input before telling the model to action.
 */
public class ViewPerformanceListener implements ActionListener {

  private JDialog performanceDialog;
  private final PortfolioUIView view;
  private final PortfolioModel model;

  /**
   * Constructor that sets the values of the passed view, dialog and model.
   *
   * @param performanceDialog The passed Dialog.
   * @param view              The passed view.
   * @param model             The passed model.
   */
  public ViewPerformanceListener(JDialog performanceDialog, PortfolioUIView view,
                                 PortfolioModel model) {
    this.performanceDialog = performanceDialog;
    this.view = view;
    this.model = model;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JComboBox<String> nameList = null;
    for (Component c : performanceDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("Portfolio Names")) {
        nameList = (JComboBox<String>) c;
      }
    }
    if (nameList == null || nameList.getSelectedItem() == null) {
      view.createErrorDialog("Please select a portfolio", performanceDialog);
      return;
    }
    String name = nameList.getSelectedItem().toString();
    String startDate = LocalDate.now().toString();
    String endDate = LocalDate.now().toString();
    String timeStamp = "";
    JComboBox<String> timeStampList = null;
    for (Component c : performanceDialog.getContentPane().getComponents()) {
      if (c.getName() != null && c.getName().equals("Date panel")) {
        JPanel datePanel = (JPanel) c;
        startDate = view.getDate(datePanel);
        try {
          LocalDate.parse(startDate);
        } catch (DateTimeException w) {
          view.createErrorDialog("Please enter a valid date.", performanceDialog);
          return;
        }
      }
      if (c.getName() != null && c.getName().equals("Date panel end")) {
        JPanel datePanel = (JPanel) c;
        endDate = view.getDate(datePanel);
        try {
          LocalDate.parse(endDate);
        } catch (DateTimeException w) {
          view.createErrorDialog("Please enter a valid date.", performanceDialog);
          return;
        }
      }
      if (c.getName() != null && c.getName().equals("Time stamp")) {
        timeStampList = (JComboBox<String>) c;
        timeStamp = timeStampList.getSelectedItem().toString();
      }
    }
    Map<LocalDate, Double> map = new HashMap<>();
    try {
      map = model.performanceOverTime(name, timeStamp, startDate, endDate);
    } catch (Exception ex) {
      view.createErrorDialog(ex.getMessage(), performanceDialog);
      return;
    }
    view.createInformationDialog("Please wait while we create the performance " +
            "chart for you.", performanceDialog);
    BarChart chart = new BarChart("Performance Chart", map);
    chart.pack();
    chart.setDefaultCloseOperation(chart.DISPOSE_ON_CLOSE);
    chart.setVisible(true);
  }
}
