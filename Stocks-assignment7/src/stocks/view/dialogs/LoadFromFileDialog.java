package stocks.view.dialogs;

import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import stocks.controller.actionlisteners.GoBackListener;

/**
 * This class represents the dialog created when a user tries to load a portfolio from a file.
 */
public class LoadFromFileDialog extends AbstractDialog {

  /**
   * This constructor creates a Load from file dialog that the user can interact with.
   * @param parent the JFrame parent of the application interface
   */
  public LoadFromFileDialog(JFrame parent) {
    super(parent);
  }

  @Override
  public JDialog createDialog() {
    JDialog dialog = new JDialog(parent);
    dialog.setSize(550, 200);
    dialog.setLocationRelativeTo(parent);
    JLabel nameLabel = new JLabel("Enter name of portfolio you want to create");
    nameLabel.setName("Name label");
    dialog.add(nameLabel);
    // Text field where user enters name
    JTextField nameField = new JTextField(10);
    nameField.setName("Portfolio Name");
    dialog.add(nameField);
    JFileChooser filePicker = new JFileChooser();
    filePicker.setName("File picker");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "CSV Files", "csv");
    filePicker.setFileFilter(filter);
    int retValue = filePicker.showOpenDialog(dialog);
    File ret = null;
    if (retValue == JFileChooser.APPROVE_OPTION) {
      ret = filePicker.getSelectedFile();
    }
    JLabel chosenFileLabel = new JLabel("File chosen for portfolio: ");
    chosenFileLabel.setName("File chosen label");
    dialog.add(chosenFileLabel);
    JButton chooseFile;
    assert ret != null;
    chooseFile = ret.exists() ? new JButton(ret.getAbsolutePath()) :
            new JButton("Please select a valid file");
    chooseFile.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "CSV Files", "csv");
        filePicker.setFileFilter(filter);
        int retValue = filePicker.showOpenDialog(dialog);
        File ret = null;
        if (retValue == JFileChooser.APPROVE_OPTION) {
          ret = filePicker.getSelectedFile();
        }
      }
    });
    chooseFile.setName("Choose file label");
    chooseFile.setBackground(Color.BLUE);
    chooseFile.setForeground(Color.BLUE);
    dialog.add(chooseFile);
    JButton submit = new JButton("Create portfolio");
    submit.setName("Submit");
    JLabel commissionAsk = new JLabel("Please enter commission paid on creating this portfolio.");
    commissionAsk.setName("Commission ask");
    dialog.add(commissionAsk);
    JTextField commissionAmount = new JTextField(5);
    commissionAmount.setName("Commission amount");
    dialog.add(commissionAmount);
    dialog.add(submit);
    JButton goBackButton = new JButton("Go back");
    goBackButton.addActionListener(new GoBackListener(dialog));
    dialog.add(goBackButton);
    dialog.setLayout(new FlowLayout());
    return dialog;
  }
}
