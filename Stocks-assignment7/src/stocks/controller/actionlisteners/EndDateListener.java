package stocks.controller.actionlisteners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

/**
 * Class listener that waits for the "EndDate" button to be pressed.
 * That's the button that the user clicks to be checked if an end date is entered or not.
 * And based on that it will allow the user to enter the end date details if checked.
 */
public class EndDateListener implements ActionListener {

  private JPanel datePanel;

  /**
   * The listener implementation constructor to be created by passing the panel.
   * @param datePanel the passed panel.
   */
  public EndDateListener(JPanel datePanel) {
    this.datePanel = datePanel;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    datePanel.setEnabled(!datePanel.isEnabled());
    for (Component c : datePanel.getComponents()) {
      c.setEnabled(!c.isEnabled());
    }
  }

}
