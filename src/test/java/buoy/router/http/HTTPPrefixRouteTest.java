package buoy.router.http;

import buoy.router.SimpleRoute;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by Gregory on 4/21/2015.
 */
public class HTTPPrefixRouteTest {

    @Test
    public void testDifferentVerbs() {
        assertFalse(
                (new HTTPPrefixRoute(Verb.GET, "samestring")).equals(new SimpleRoute<>(Verb.POST, "samestrhing"))
        );
    }

    @Test
    public void testDifferentStrings() {
        assertFalse(
                (new HTTPPrefixRoute(Verb.GET, "samestring")).equals(new SimpleRoute<Verb>(Verb.GET, "otherstring"))
        );
    }

    @Test
    public void testNotRoute() {
        assertFalse(
                (new HTTPPrefixRoute(Verb.GET, "string")).equals("string")
        );
    }

    @Test
    public void testActualPrefix() {
        assertTrue(
                (new HTTPPrefixRoute(Verb.GET, "string")).equals(new SimpleRoute<>(Verb.GET, "stringwithmoredata"))
        );
    }

    @Test
    public void testPrefixWithAnyVerb() {
        assertTrue(
                (new HTTPPrefixRoute(Verb.ANY, "string")).equals(new SimpleRoute<>(Verb.DELETE, "stringwithmoredata"))
        );
    }
}