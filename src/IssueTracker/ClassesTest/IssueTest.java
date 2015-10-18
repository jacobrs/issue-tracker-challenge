package IssueTracker.ClassesTest;

import IssueTracker.Classes.Issue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IssueTest {

    Issue i1;
    Issue i2;

    @Before
    public void build(){
        i1 = new Issue();
        i2 = new Issue("Title");
    }

    @Test
    public void testConstuctor(){
        Assert.assertNotNull(i1);
        Assert.assertNotNull(i2);
        Assert.assertSame("Title", i2.title);
    }

}
