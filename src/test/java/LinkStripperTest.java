

import org.testng.Assert;
import org.testng.annotations.Test;

import slurp.LinkStripper;


public class LinkStripperTest {

    @Test
    public void stripUrlTest() {
        LinkStripper testObject = new LinkStripper();
        String stripUrl = testObject.stripUrl("https://www.startnext.com/blog/Blog-Detailseite/b");
        Assert.assertEquals(stripUrl, "https://www.startnext.com");
    }

}
