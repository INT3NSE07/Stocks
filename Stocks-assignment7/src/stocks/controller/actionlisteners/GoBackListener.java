package stocks.controller.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

/**
 * Class listener that waits for the "GoBack" button to be pressed in.
 * That's the button that the user clicks the GoBack button in all Dialogs.
 * And validates all the user input before telling the model to action.
 */
public class GoBackListener implements ActionListener {

  private JDialog dialog;

  /**
   * Constructor that creates a listener object, passing the below.
   *
   * @param dialog The specific dialog.
   */
  public GoBackListener(JDialog dialog) {
    this.dialog = dialog;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    dialog.dispose();
  }
}
