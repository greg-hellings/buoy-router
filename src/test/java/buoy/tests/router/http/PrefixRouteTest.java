package buoy.tests.router.http;

import buoy.router.SimpleRoute;
import buoy.router.http.PrefixRoute;
import buoy.router.http.Verb;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by Gregory on 4/21/2015.
 */
public class PrefixRouteTest {

    @Test
    public void testDifferentVerbs() {
        assertFalse(
                (new PrefixRoute(Verb.GET, "samestring")).equals(new SimpleRoute<>(Verb.POST, "samestrhing"))
        );
    }

    @Test
    public void testDifferentStrings() {
        assertFalse(
                (new PrefixRoute(Verb.GET, "samestring")).equals(new SimpleRoute<Verb>(Verb.GET, "otherstring"))
        );
    }

    @Test
    public void testNotRoute() {
        assertFalse(
                (new PrefixRoute(Verb.GET, "string")).equals("string")
        );
    }

    @Test
    public void testActualPrefix() {
        assertTrue(
                (new PrefixRoute(Verb.GET, "string")).equals(new SimpleRoute<>(Verb.GET, "stringwithmoredata"))
        );
    }

    @Test
    public void testPrefixWithAnyVerb() {
        assertTrue(
                (new PrefixRoute(Verb.ANY, "string")).equals(new SimpleRoute<>(Verb.DELETE, "stringwithmoredata"))
        );
    }
}