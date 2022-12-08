package stocks.view.dialogs;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

/**
 * This class represents a JPanel which has drop down menus of day, month and year for a user to
 * select a date while they are performing an action.
 */
public class DatePanel {

  /**
   * This constructor creates the JPanel with all the required components for the user to interact
   * with while performing an action.
   * @return the JPanel users can select dates in
   */
  public JPanel datePanel() {
    JPanel date = new JPanel();
    date.setName("Date panel");
    date.setSize(200, 300);
    JLabel dayLabel = new JLabel("Choose day");
    dayLabel.setName("Day label");
    dayLabel.setHorizontalAlignment(JTextField.CENTER);
    date.add(dayLabel);
    JComboBox<Integer> day = new JComboBox<>();
    day.setName("Day list");
    for (int i = 1; i <= 31; i++) {
      day.addItem(i);
    }
    date.add(day);
    JLabel monthLabel = new JLabel("Choose month");
    monthLabel.setName("Month label");
    monthLabel.setHorizontalAlignment(JTextField.CENTER);
    date.add(monthLabel);
    JComboBox<String> months = new JComboBox<>(new String[] {"January" , "February", "March",
        "April", "May", "June", "July", "August", "September", "October",
        "November", "December"});
    months.setName("Month list");
    date.add(months);
    JLabel yearLabel = new JLabel("Choose year");
    yearLabel.setHorizontalAlignment(JTextField.CENTER);
    yearLabel.setName("Year label");
    date.add(yearLabel);
    JComboBox<Integer> year = new JComboBox<>();
    for (int i = 2015; i <= 2022; i++) {
      year.addItem(i);
    }
    year.setName("Year list");
    date.add(year);
    date.setLayout(new GridLayout(0,1));
    return date;
  }

}
