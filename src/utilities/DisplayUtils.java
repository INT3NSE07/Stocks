package utilities;

import java.awt.BorderLayout;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;

/**
 * A utility class that contains display related helpers.
 */
public final class DisplayUtils {

  private DisplayUtils() {
  }

  /**
   * This class represents a text-based table generator.
   */
  public static final class TextTableGenerator {

    private static final String HORIZONTAL_SEP = "-";
    private static final String VERTICAL_SEP = "|";
    private static final String JOIN_SEP = "+";
    private final List<List<String>> rows;
    private final List<String> headers;
    private final PrintStream out;


    /**
     * Creates and instance {@link TextTableGenerator} class.
     *
     * @param out the output stream to which the table is printed
     */
    public TextTableGenerator(PrintStream out) {
      this.headers = new ArrayList<>();
      this.rows = new ArrayList<>();
      this.out = out;
    }

    public void addHeader(String header) {
      this.headers.add(header);
    }

    public void addRow(List<String> cells) {
      rows.add(cells);
    }

    /**
     * Generates and prints the table.
     */
    public void printTable() {
      int[] cellWidths = new int[headers.size()];

      for (List<String> row : rows) {
        for (int i = 0; i < row.size(); i++) {
          cellWidths[i] = Math.max(headers.get(i).length(), row.get(i).length());
        }
      }

      printLine(cellWidths);
      printRow(headers, cellWidths);
      printLine(cellWidths);

      for (List<String> row : rows) {
        printRow(row, cellWidths);
      }
      printLine(cellWidths);
    }

    private void printLine(int[] cellWidths) {
      for (int i = 0; i < cellWidths.length; i++) {
        String line = String.join("", Collections.nCopies(cellWidths[i]
            + VERTICAL_SEP.length() + 1, HORIZONTAL_SEP));
        this.out.print(JOIN_SEP + line + (i == cellWidths.length - 1 ? JOIN_SEP : ""));
      }

      this.out.println();
    }

    private void printRow(List<String> row, int[] maxWidths) {
      for (int i = 0; i < row.size(); i++) {
        String value = row.get(i);

        String word = i == row.size() - 1 ? VERTICAL_SEP : "";
        this.out.printf("%s %-" + maxWidths[i] + "s %s", VERTICAL_SEP, value, word);
      }

      this.out.println();
    }
  }

  /**
   * This class represents a barchart and contains methods which render a barchart.
   */
  public static class BarChart {

    public CategoryDataset dataset;

    /**
     * Creates a new instance of a {@link BarChart}
     *
     * @param title the frame title.
     */
    public BarChart(String title, String heading, CategoryDataset dataset, double maxVal) {
//      super(title);
      JFrame f = new JFrame(title);
      f.setTitle(title);
      f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      f.setLayout(new BorderLayout(0, 5));
      this.dataset = dataset;
      final JFreeChart chart = createChart(dataset, heading, maxVal);

      // add the chart to a panel...
      final ChartPanel chartPanel = new ChartPanel(chart);
      f.add(chartPanel, BorderLayout.CENTER);
      chartPanel.setMouseWheelEnabled(true);
//      chartPanel.setHorizontalAxisTrace(true);
//      chartPanel.setVerticalAxisTrace(true);
      f.pack();
      f.setLocationRelativeTo(null);
      f.setVisible(true);
//      chartPanel.setPreferredSize(new java.awt.Dimension(700, 270));
//      setContentPane(chartPanel);
    }

    /**
     * Creates a sample chart.
     *
     * @param dataset the dataset.
     * @return The chart.
     */
    public static JFreeChart createChart(CategoryDataset dataset, String heading, double rangeMax) {
      final JFreeChart chart = ChartFactory.createBarChart(
          heading,                      // chart title
          "Timeline",                 // domain axis label
          "Value in $",                // range axis label
          dataset,                    // data
          PlotOrientation.HORIZONTAL, // orientation
          false,                       // include legend
          true,
          false
      );

      // set the background color for the chart...
      //chart.setBackgroundPaint(Color.lightGray);

      // get a reference to the plot for further customisation...
      final CategoryPlot plot = chart.getCategoryPlot();
      plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

      // change the auto tick unit selection to integer units only...
      final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
      rangeAxis.setRange(0.0, rangeMax);
      rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

      return chart;
    }
  }

}
