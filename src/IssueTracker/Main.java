package IssueTracker;

import IssueTracker.Classes.Issue;
import IssueTracker.Classes.MainGUI;
import IssueTracker.Classes.Project;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main{

    public static boolean isJacob = false;

    public static String PATH = "/Users/benjaminbarault/Desktop/Java Projects/Test Folders/Data";
    public static LinkedList<Project> projects = new LinkedList<>();
    public static void main(String args[]) throws FileNotFoundException{

        if(isJacob){
            Main.PATH = "/Users/jacob/Documents/Downloaded Tools/Tryout 3/Data";
        }
        
        File projectsFolder = new File(PATH);
        boolean launch = true;

        if(projectsFolder.exists() && projectsFolder.listFiles() != null) {
            //no inspection Constant Conditions
            for (File project : projectsFolder.listFiles()) {
                Project p = new Project(project.getName());
                if (project.isDirectory()) {
                    //no inspection Constant Conditions
                    for (File issue : project.listFiles()) {
                        Issue i = readIssue(issue);
                        p.createTicket(i);
                    }
                    projects.add(p);
                }
            }
        }else{
            launch = projectsFolder.mkdirs();
        }

        if(launch) {
            JFrame frame = new JFrame("MainGUI");
            frame.setContentPane(new MainGUI(projects).getContentPane());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setResizable(false);
            frame.setVisible(true);
        }
    }

    public static Issue readIssue(File issue) throws FileNotFoundException{
        Scanner read;
        Issue i = new Issue();
        String issueNumber = issue.getName().substring(5);
        issueNumber = issueNumber.replaceFirst(".txt", "");
        read= new Scanner(issueNumber);
        i.number = read.nextInt();
        
        read = new Scanner(issue);
        String line;
        line = read.nextLine();
        i.title = line.substring(7);
        line = read.nextLine();
        i.type = line.substring(6);
        line = read.nextLine();
        i.description = line.substring(13);
        line = read.nextLine();
        i.priority = line.substring(10);
        line = read.nextLine();
        Scanner s = new Scanner(line);
        
        s.next();s.next();
        i.timeEstimate = s.nextInt();
        line = read.nextLine();
        i.status = line.substring(8);
        return i;
    }

}
