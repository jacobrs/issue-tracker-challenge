package IssueTracker.Classes;

import IssueTracker.Main;

import java.io.*;
import java.nio.file.*;
import java.util.LinkedList;

/**
 * Projects Store collections of issues and are the base element of the program
 */
public class Project implements Serializable{

    public String name;
    public LinkedList<Issue> issues = new LinkedList<>();

    public Project(String title){
        name = title;
        this.writeProjectToDisk();
    }

    private LinkedList<Issue> getOustandingIssues(){
        LinkedList<Issue> r = new LinkedList<>();
        for(Issue i : issues){
            if(!i.status.toLowerCase().equals("closed"))
                r.add(i);
        }
        return r;
    }

    public int getOutstanding(){
        return getOustandingIssues().size();
    }

    public double getEstimate(){
        double counter = 0;
        for(Issue i : getOustandingIssues()){
            counter += i.timeEstimate;
        }
        return counter;
    }

    public LinkedList<Issue> getIssues(){
        return getOustandingIssues();
    }

    public LinkedList<Issue> getAllIssues(){
        return issues;
    }

    public LinkedList<Issue> getIssues(String status){
        LinkedList<Issue> r = new LinkedList<>();
        for(Issue i : issues){
            if(i.status.toLowerCase().equals(status.toLowerCase()) || status.toLowerCase().equals("all"))
                r.add(i);
        }
        return r;
    }

    public LinkedList<Issue> search(String term){
        LinkedList<Issue> r = new LinkedList<>();
        for(Issue i : issues){
            if(i.title.toLowerCase().contains(term.toLowerCase()))
                r.add(i);
        }
        return r;
    }

    public Issue getIssue(int e){
        for(Issue i : issues){
            if(i.number == e)
                return i;
        }
        return null;
    }

    public boolean delete(int e){
        int k = 0;
        for(Issue i : issues){
            if(i.number == e)
                break;
            k++;
        }
        if(k > issues.size() - 1){
            return false;
        }

        String issueFile = Main.PATH + "/" + this.name + "/Issue" + issues.get(k).number + ".txt";
        try {
            Path issuePath = FileSystems.getDefault().getPath(issueFile);
            try {
                Files.delete(issuePath);
            } catch (NoSuchFileException x) {
                System.err.format("%s: no such" + " file or directory%n", issuePath);
            } catch (DirectoryNotEmptyException x) {
                System.err.format("%s not empty%n", issuePath);
            } catch (IOException x) {
                // File permission problems are caught here.
                System.err.println(x.toString());
            }
        } catch (Exception a) {
            a.printStackTrace();
        }

        issues.remove(k);
        return true;
    }

    public boolean createTicket(Issue i){
        int size = issues.size();
        issues.add(i);

        Path issuePath = FileSystems.getDefault().getPath(Main.PATH + "/" + this.name + "/Issue" + i.number + ".txt");
        try {
            Files.createFile(issuePath);
        } catch (FileAlreadyExistsException e) {
            System.out.print(e.toString());
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x.toString());
        }

        try {
            PrintWriter writer = new PrintWriter(issuePath.toString(), "UTF-8");
            writer.println("Title: " + i.title);
            writer.println("Type: " + i.type);
            writer.println("Description: " + i.description);
            writer.println("Priority: " + i.priority);
            writer.println("Time Estimate: " + i.timeEstimate);
            writer.println("Status: " + i.status);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return size < issues.size();
    }

    public boolean destroyIssues(){


        for(Issue i : issues) {

            String issueFile = Main.PATH + "/" + this.name + "/Issue" + i.number  + ".txt";
            try {
                Path issuePath = FileSystems.getDefault().getPath(issueFile);
                try {
                    Files.delete(issuePath);
                } catch (NoSuchFileException x) {
                    System.err.format("%s: no such" + " file or directory%n", issuePath);
                } catch (DirectoryNotEmptyException x) {
                    System.err.format("%s not empty%n", issuePath);
                } catch (IOException x) {
                    // File permission problems are caught here.
                    System.err.println(x.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        issues.clear();
        return issues.isEmpty();
    }

    public void destroyProject(){
        destroyIssues();

        Path issuePath = FileSystems.getDefault().getPath(Main.PATH + "/" + this.name);
        try {
            Files.delete(issuePath);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", issuePath);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", issuePath);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x.toString());
        }
    }

    public void writeProjectToDisk(){
        Path issuePath = FileSystems.getDefault().getPath(Main.PATH + "/" + this.name);
        try {
            Files.createDirectory(issuePath);
        } catch (FileAlreadyExistsException e) {
            System.out.print(e.toString());
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x.toString());
        }
    }
}

