package buoy.tests.router;

import buoy.router.PrefixRoute;
import buoy.router.SimpleRoute;
import buoy.router.http.Verb;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by Gregory on 4/21/2015.
 */
public class PrefixRouteTest {
    public enum NotVerb {
        ONE,
        TWO
    }

    private PrefixRoute<Verb> route;

    @BeforeMethod
    public void setUp() throws Exception {
        this.route = new PrefixRoute<>(Verb.ANY, "/this/is/a");
    }

    @Test
    public void testNotRoute() throws Exception {
        assertFalse(this.route.equals("nope"));
    }

    @Test
    public void testDifferentVerbs() throws Exception {
        assertFalse(this.route.equals(new SimpleRoute<>(Verb.GET, "/this/is/a")));
    }

    @Test
    public void testDifferentVerbTypes() throws Exception {
        assertFalse(this.route.equals(new SimpleRoute<>(NotVerb.ONE, "/this/is/a")));
    }

    @Test
    public void testDifferentPaths() throws Exception {
        assertFalse(this.route.equals(new SimpleRoute<>(Verb.ANY, "/this/is/not")));
    }

    @Test
    public void testSameValues() throws Exception {
        assertTrue(this.route.equals(new SimpleRoute<>(this.route.getVerb(), this.route.getPath())));
    }

    @Test
    public void testActualPrefix() throws Exception {
        assertTrue(this.route.equals(new SimpleRoute<>(this.route.getVerb(), this.route.getPath() + "morethingshere")));
    }

    @Test
    public void testIdentity() throws Exception {
        assertTrue(this.route.equals(this.route));
    }
}