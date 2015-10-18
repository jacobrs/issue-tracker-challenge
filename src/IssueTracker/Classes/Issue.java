package IssueTracker.Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Issues are used to keep for tickets which include
 * basic information on things that need to be done
 * and meta data related to that
 */

public class Issue implements Serializable{

    public String title;
    public String type;
    public String description;
    public String priority;
    public Integer timeEstimate;
    public String status;
    public Integer number;

    public Issue(){
        // default constructor remains empty
    }

    public Issue(String title){
        // create issue with title
        this.title = title;
    }
}