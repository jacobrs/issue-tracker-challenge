package IssueTracker.ClassesTest;

import IssueTracker.Classes.Issue;
import IssueTracker.Classes.Project;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

public class ProjectTest {

    Project p;

    @Before
    public void build(){
        p = new Project("Test");
    }

    @Test
    public void testConstructors(){
        Assert.assertSame("Test", p.name);
    }

    @Test
    public void testIssueManipulation(){
        LinkedList<Issue> issues = new LinkedList<Issue>();

        Issue i = new Issue("TestingIssue");
        i.description = "First testing issue";
        i.number = 1;
        i.priority = "High";
        i.status = "Open";
        i.type = "Task";
        i.timeEstimate = 15;

        p.createTicket(i);
        issues.add(i);

        Issue a = new Issue("TestingIssue2");
        a.description = "Second testing issue";
        a.number = 2;
        a.priority = "Critical";
        a.status = "Closed";
        a.type = "Bug";
        a.timeEstimate = 30;

        p.createTicket(a);
        issues.add(a);

        LinkedList<Issue> compSearch = new LinkedList<Issue>();
        compSearch.add(a);
        LinkedList<Issue> empty = new LinkedList<Issue>();

        Assert.assertEquals(1, p.getOutstanding());
        Assert.assertEquals("15.0", Double.toString(p.getEstimate()));
        Assert.assertEquals(2, p.getAllIssues().size());
        Assert.assertEquals(issues, p.getAllIssues());
        Assert.assertEquals(compSearch, p.search("2"));
        Assert.assertEquals(a, p.getIssue(2));
        Assert.assertEquals(true, p.delete(1));
        Assert.assertEquals(compSearch, p.getAllIssues());
        Assert.assertEquals(true, p.createTicket(i));
        Assert.assertNotEquals(compSearch, p.getAllIssues());
        Assert.assertEquals(true, p.destroyIssues());
        Assert.assertEquals(empty, p.getAllIssues());
        p.destroyProject();
    }

}
