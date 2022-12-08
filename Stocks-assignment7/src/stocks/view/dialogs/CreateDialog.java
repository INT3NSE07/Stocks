package stocks.view.dialogs;

import javax.swing.JDialog;

/**
 * This interface represents the process of Creating a dialog that a user interacts with while
 * performing actions supported by the applications.
 */
public interface CreateDialog {

  /**
   * Create the JDialog instance of the required dialog to allow the user to perform an action with
   * all the required components for the action.
   * @return the dialog that the user can interact with
   */
  JDialog createDialog();
}
