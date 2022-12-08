package stocks.view.dialogs;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.Dimension;
import java.time.LocalDate;
import java.util.Map;

import javax.swing.JFrame;

/**
 * This class represents a BarChart that shows performance of a portfolio. The usage
 * link is https://www.jfree.org/jfreechart/.
 */
public class BarChart extends JFrame {

  /**
   * Creates a new bar chart instance using JFreeChart library.
   *
   * @param title the frame title.
   */
  public BarChart(String title, Map<LocalDate, Double> map) {
    super(title);
    final CategoryDataset dataset = createDataset(map);
    final JFreeChart chart = createChart(dataset);
    final ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(1000, 1000));
    setContentPane(chartPanel);
  }

  private CategoryDataset createDataset(Map<LocalDate, Double> performance) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for (Map.Entry<LocalDate, Double> entry : performance.entrySet()) {
      dataset.addValue(entry.getValue(), "", entry.getKey());
    }
    return dataset;
  }

  private JFreeChart createChart(CategoryDataset dataset) {
    JFreeChart chart = ChartFactory.createBarChart(
            "Performance Chart",
            "Date",
            "Value",
            dataset,
            PlotOrientation.HORIZONTAL,
            false,
            true,
            false
    );
    return chart;
  }

}
