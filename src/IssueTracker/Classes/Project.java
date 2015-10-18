package IssueTracker.Classes;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Projects Store collections of issues and are the base element of the program
 */
public class Project implements Serializable{

    public String name;
    public LinkedList<Issue> issues = new LinkedList<Issue>();

    public Project(String title){
        name = title;
    }

    private LinkedList<Issue> getOustandingIssues(){
        LinkedList<Issue> r = new LinkedList<Issue>();
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
        LinkedList<Issue> r = new LinkedList<Issue>();
        for(Issue i : issues){
            if(!i.status.toLowerCase().equals(status))
                r.add(i);
        }
        return r;
    }

    public LinkedList<Issue> search(String term){
        LinkedList<Issue> r = new LinkedList<Issue>();
        for(Issue i : issues){
            if(i.title.toLowerCase().contains(term))
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
        /*
        String issueFile = Main.PATH + "/" + this.name + "/Issue#" + issues.get(k).number;
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
                System.err.println(x);
            }
        } catch (Exception a) {
            a.printStackTrace();
        }*/

        issues.remove(k);
        return true;
    }

    public boolean createTicket(Issue i){
        int size = issues.size();
        issues.add(i);
        return size < issues.size();
    }

    public boolean destroyIssues(){

        /*
        for(Issue i : issues) {

            String issueFile = Main.PATH + "/" + this.name + "/Issue#" + i.number;
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
                    System.err.println(x);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        */

        issues.clear();
        return issues.isEmpty();
    }

    public void destroyProject(){
        destroyIssues();
        /*
        try {
            URI projPath = new URI(Main.PATH + "/");
            Path issuePath = FileSystems.getDefault().getPath(projPath + "/" + this.name);
            try {
                Files.delete(issuePath);
            } catch (NoSuchFileException x) {
                System.err.format("%s: no such" + " file or directory%n", issuePath);
            } catch (DirectoryNotEmptyException x) {
                System.err.format("%s not empty%n", issuePath);
            } catch (IOException x) {
                // File permission problems are caught here.
                System.err.println(x);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        */
    }
}

