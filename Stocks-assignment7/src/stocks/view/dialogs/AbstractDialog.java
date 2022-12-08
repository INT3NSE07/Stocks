package stocks.view.dialogs;

import javax.swing.JFrame;

/**
 * This abstract class implements the CreateDialog interface.
 */
abstract class AbstractDialog implements CreateDialog {

  protected JFrame parent;

  /**
   * A super constructor for all the classes that inherit this Abstract class. Each of them has
   * a parent JFrame but might have different other parameters.
   * @param parent the JFrame that the dialog is being created by
   */
  protected AbstractDialog(JFrame parent) {
    this.parent = parent;
  }

}
