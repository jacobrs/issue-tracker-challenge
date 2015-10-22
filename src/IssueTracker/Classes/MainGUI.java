package IssueTracker.Classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by benjaminbarault on 2015-10-22.
 */
public class MainGUI extends JFrame {
    private JPanel MainPanel;
    private JPanel LeftPanel;
    private JPanel RightPanel;
    private JPanel LeftHeader;
    private JPanel LeftFooter;
    private JPanel LeftContent;
    private JPanel LeftCEast;
    private JPanel LeftCWest;
    private JPanel LEastHeader;
    private JPanel RightHeader;
    private JPanel RightContent;
    private JPanel MiddlePanel;
    private JPanel MiddleHeader;
    private JPanel MiddleContent;
    private JPanel MiddleFooter;
    private JPanel MiddleCEast;
    private JPanel MiddleCWest;
    private JPanel MEastHeader;

    private JComboBox ddFilter;
    private JTextField txtProjectBottom;
    private JTextField txtIssueBottom;
    private JTextArea txtOutput;
    private JButton btnAddProject;
    private JButton btnDeleteProject;
    private JButton btnListIssues;
    private JButton btnAddIssue;
    private JButton btnDisplayIssue;
    private JButton btnDeleteIssue;
    private JButton btnSearch;
    private JList listProjects;
    private JList listIssues;
    private JPanel Filler1;
    private JPanel Filler2;
    private JPanel Filler3;
    private JPanel Filler4;
    private JPanel Filler5;
    private JPanel Filler6;
    private JPanel Filler7;
    private JPanel Filler8;
    private JPanel Filler9;
    private JPanel Filler10;
    private JPanel Filler11;
    private JPanel Filler12;

    private Project currListedProject;
    private LinkedList<Project> Projects = new LinkedList<>();
    private LinkedList<Issue> listOfIssues = new LinkedList<>();

    private DefaultListModel<String> projectsModel;
    private DefaultListModel<String> listsModel;

    public MainGUI(LinkedList<Project> p){
        final MainGUI temp = this;

        // Set the different filters
        DefaultComboBoxModel<String> dCModel = new DefaultComboBoxModel<>();
        dCModel.addElement("All");
        dCModel.addElement("Open");
        dCModel.addElement("In Progress");
        dCModel.addElement("In Review");
        dCModel.addElement("Closed");

        this.ddFilter.setModel(dCModel);


        // Populate all our projects
        this.Projects = p;
        this.projectsModel = new DefaultListModel<>();
        this.listsModel    = new DefaultListModel<>();

        // Add all to the list of projects
        // Add in the format: "Project Name"  |  "Total # issues"   |  "Estimated Time"
        for(Project project : p){
            String text = project.name + " | " + project.getOutstanding() + " | " + project.getEstimate();
            this.projectsModel.addElement(text);
        }

        this.listProjects.setModel(this.projectsModel);
        this.listIssues.setModel(this.listsModel);


        // Adding event listeners
        this.btnListIssues.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temp.listIssues();
            }
        });

        this.btnAddProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temp.addProject();
            }
        });

        this.btnDeleteProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temp.deleteProject();
            }
        });

        this.btnAddIssue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Finish the add issue functionality
            }
        });

        this.btnDeleteIssue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temp.deleteIssue();
            }
        });

        this.btnDisplayIssue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temp.displayIssue();
            }
        });

        this.btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temp.searchIssue();
            }
        });

        this.btnAddIssue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temp.promptAddIssue();
            }
        });
    }

    // Function that returns the currently selected project
    public Project getSelectedProject(){
        int selectedIndex = this.listProjects.getSelectedIndex();

        if(selectedIndex == -1){
            JOptionPane.showMessageDialog(null, "Select a project first");
            return null;
        }

        // Retrieve project from linked list and return it
        return Projects.get(selectedIndex);
    }

    public int getNumberOfIssues(){
        return currListedProject.getAllIssues().size();
    }

    private void listIssues(){
        // Get all the issues based off the filter and place them in the issues list
        String filter = ddFilter.getSelectedItem().toString();

        Project selectedProject = this.getSelectedProject();

        if(selectedProject != null) {
            currListedProject = selectedProject;
            LinkedList<Issue> issues = selectedProject.getIssues(filter);

            this.listIssues.removeAll();
            this.listsModel.clear();

            Iterator<Issue> ite = issues.iterator();
            while (ite.hasNext()) {
                Issue currObj = ite.next();

                // String should be formatted as:  "Issues Number"  |  "Issue Title"
                String text = currObj.number + " | " + currObj.title;
                this.listsModel.addElement(text);
            }

            this.listIssues.setModel(this.listsModel);
            this.listOfIssues = issues;
        }
    }

    private void addProject(){
        String projectName = this.txtProjectBottom.getText();

        // Add the project to our linked list and the list
        Project tmp = new Project(projectName);
        Projects.add(tmp);

        String text = projectName + " | " + tmp.getOutstanding() + " | " + tmp.getEstimate();
        this.projectsModel.addElement(text);
        this.listProjects.setModel(this.projectsModel);
    }

    private void deleteProject(){
        // Delete the selected project
        int selected = this.listProjects.getSelectedIndex();

        if(selected != -1) {
            // Delete the project from the LinkedList of Projects
            this.Projects.get(selected).destroyProject();
            this.Projects.remove(selected);

            this.listProjects.remove(selected);
            this.listIssues.removeAll();
            this.txtOutput.setText("");
        }else{
            JOptionPane.showMessageDialog(null, "Select a project first");
        }
    }

    private void promptAddIssue(){
        if(this.getSelectedProject() != null){
            PromptIssue newPrompt = new PromptIssue(MainGUI.this);
            newPrompt.setVisible(true);
        }
    }

    public void addIssue(Issue i){
        Project addTo;

        if(currListedProject != null) {
            addTo = currListedProject;
        }else{
            addTo = this.getSelectedProject();
        }

        if(addTo != null) {
            currListedProject.createTicket(i);

            this.listIssues();

            this.listProjects.setModel(this.projectsModel);

            this.projectsModel.clear();

            for (Project project : this.Projects) {
                String text = project.name + " | " + project.getOutstanding() + " | " + project.getEstimate();
                this.projectsModel.addElement(text);
            }

            this.listProjects.setModel(this.projectsModel);
        }
    }

    private void deleteIssue(){
        int issueIndex = this.listIssues.getSelectedIndex();

        if(issueIndex != -1) {
            if(this.currListedProject != null) {
                // Remove the specific issue from the LinkedList in the specific Project
                this.currListedProject.delete(this.listOfIssues.get(issueIndex).number);

                this.listsModel.remove(issueIndex);
                this.projectsModel.clear();

                for (Project project : this.Projects) {
                    String text = project.name + " | " + project.getOutstanding() + " | " + project.getEstimate();
                    this.projectsModel.addElement(text);
                }

                this.listIssues.setModel(this.listsModel);
                this.listProjects.setModel(this.projectsModel);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Select an issue first");
        }
    }

    private void searchIssue(){
        String searchTerm = this.txtIssueBottom.getText();

        if(currListedProject != null) {
            // Search for the specific issue here and display it in the issues list
            LinkedList<Issue> foundIssues = currListedProject.search(searchTerm);

            this.listIssues.removeAll();
            this.listsModel.clear();

            Iterator<Issue> ite = foundIssues.iterator();
            while (ite.hasNext()) {
                Issue currObj = ite.next();

                // String should be formatted as:  "Issues Number"  |  "Issue Title"
                String text = currObj.number + " | " + currObj.title;
                this.listsModel.addElement(text);
            }

            this.listIssues.updateUI();
            this.listOfIssues = foundIssues;
        }else{
            JOptionPane.showMessageDialog(null, "You must list issues first");
        }
    }

    private void displayIssue(){
        int selectedIndex = this.listIssues.getSelectedIndex();

        if(selectedIndex != -1) {
            Issue currSelectedIssue = this.listOfIssues.get(selectedIndex);

            String text = currSelectedIssue.title + "\n" +
                    currSelectedIssue.type + "\n" +
                    currSelectedIssue.description + "\n" +
                    currSelectedIssue.priority + "\n" +
                    currSelectedIssue.timeEstimate + "\n" +
                    currSelectedIssue.status;
            this.txtOutput.setText(text);
        }else{
            JOptionPane.showMessageDialog(null, "Selected an issue first");
        }
    }

    @Override
    public Container getContentPane() {
        return this.MainPanel;
    }
}
