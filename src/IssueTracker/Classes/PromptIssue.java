package IssueTracker.Classes;

import javax.swing.*;
import java.awt.event.*;

public class PromptIssue extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel contentFooter;
    private JPanel contentHeader;
    private JComboBox ddPriority;
    private JComboBox ddType;
    private JComboBox ddStatus;
    private JTextField txtDescription;
    private JTextField txtTimeEstimate;
    private JTextField txtTitle;

    private MainGUI parent;

    public PromptIssue(MainGUI parent) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();

        // Set the different filters
        DefaultComboBoxModel<String> dTModel = new DefaultComboBoxModel<>();
        dTModel.addElement("Task");
        dTModel.addElement("Bug");
        dTModel.addElement("New Feature");

        this.ddType.setModel(dTModel);

        // Set the different filters
        DefaultComboBoxModel<String> dSModel = new DefaultComboBoxModel<>();
        dSModel.addElement("Open");
        dSModel.addElement("In Progress");
        dSModel.addElement("In Review");
        dSModel.addElement("Closed");

        this.ddStatus.setModel(dSModel);

        DefaultComboBoxModel<String> dPModel = new DefaultComboBoxModel<>();
        dPModel.addElement("Critical");
        dPModel.addElement("High");
        dPModel.addElement("Normal");
        dPModel.addElement("Low");

        this.ddPriority.setModel(dPModel);

        this.parent = parent;
    }

    private void onOK() {
        // Add the issue
        String title = this.txtTitle.getText();
        String desc  = this.txtDescription.getText();
        String time  = this.txtTimeEstimate.getText();

        // Make sure nothing is empty
        if(title.equals("") || desc.equals("") || time.equals("")){
            JOptionPane.showMessageDialog(null, "Please fill out the necessary fields");
            return;
        }

        int intTime;

        try {
            intTime = Integer.parseInt(time);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Time Estimate must be a integer");
            return;
        }

        Issue addIssue = new Issue();
        addIssue.title        = title;
        addIssue.description  = desc;
        addIssue.timeEstimate = intTime;
        addIssue.type     = this.ddType.getSelectedItem().toString();
        addIssue.priority = this.ddPriority.getSelectedItem().toString();
        addIssue.status   = this.ddStatus.getSelectedItem().toString();

        addIssue.number   = parent.getNumberOfIssues() + 1;

        parent.addIssue(addIssue);

        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
