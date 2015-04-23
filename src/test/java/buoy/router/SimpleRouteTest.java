package buoy.router;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by Gregory on 4/21/2015.
 */
public class SimpleRouteTest {
    public enum Something {
        A,
        B
    }

    private SimpleRoute<Something> route;

    @BeforeTest
    public void setUp() {
        this.route = new SimpleRoute<>(Something.A, "astring");
    }

    @Test
    public void testIdentity() throws Exception {
        assertTrue(this.route.equals(this.route));
    }

    @Test
    public void testNotRoute() throws Exception {
        assertFalse(this.route.equals(""));
    }

    @Test
    public void testDifferentVerb() throws Exception {
        assertFalse(this.route.equals(new SimpleRoute<>(Something.B, "astring")));
    }

    @Test
    public void testDifferentStrings() throws Exception {
        assertFalse(this.route.equals(new SimpleRoute<>(Something.A, "notstring")));
    }

    @Test
    public void testSameValues() throws Exception {
        assertTrue(this.route.equals(new SimpleRoute<>(Something.A, "astring")));
    }
}