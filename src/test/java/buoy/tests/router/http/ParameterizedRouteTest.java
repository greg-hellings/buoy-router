package buoy.tests.router.http;

import buoy.router.SimpleRoute;
import buoy.router.http.ParameterizedRoute;
import buoy.router.http.Route;
import buoy.router.http.Verb;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

/**
 * Created by Gregory on 4/22/2015.
 */
public class ParameterizedRouteTest {
    @Test
    public void testSimple() {
        ParameterizedRoute route = new ParameterizedRoute(Verb.GET, "/simple/path/route");
        assertTrue(route.equals(new SimpleRoute(Verb.GET, "/simple/path/route")));
        assertFalse(route.equals(new SimpleRoute(Verb.GET, "/simple/path/route/nope")));
        assertFalse(route.equals(new SimpleRoute(Verb.POST, "/simple/path/route")));
        assertTrue(route.equals(route));
    }

    @Test
    public void testWithParameter() {
        ParameterizedRoute route = new ParameterizedRoute(Verb.GET, "/articles/:id");
        Route testRoute = new Route(Verb.GET, "/articles/123");
        Route winRoute = new Route(Verb.GET, "/articles/:id");
        Map<String, String> values = route.getParameters(testRoute);
        Map<String, String> winValues = route.getParameters(winRoute);
        assertTrue(route.equals(testRoute));
        assertTrue(values.containsKey("id"));
        assertEquals(values.get("id"), "123");
        assertTrue(route.equals(winRoute));
        assertTrue(winValues.containsKey("id"));
        assertEquals(winValues.get("id"), ":id");
    }

    @Test
    public void testEscapedPath() {
        ParameterizedRoute route = new ParameterizedRoute(Verb.GET, "/articles/\\:id");
        Route passRoute = new Route(Verb.GET, "/articles/:id");
        Route failRoute = new Route(Verb.GET, "/articles/123");
        assertTrue(route.equals(passRoute));
        assertFalse(route.equals(failRoute));
        assertEquals(route.getParameters(passRoute).keySet().size(), 0);
    }

    @Test
    public void testAnyVerb() {
        ParameterizedRoute route = new ParameterizedRoute(Verb.ANY, "/articles/:id");
        ParameterizedRoute strictRoute = new ParameterizedRoute(Verb.POST, "/articles/:id");
        Route passRoute = new Route(Verb.GET, "/articles/123");
        Route permissiveRoute = new Route(Verb.ANY, "/articles/456");
        assertTrue(route.equals(passRoute));
        assertTrue(strictRoute.equals(permissiveRoute));
    }

    @Test
    public void testMissingParametersAreVoid() {
        ParameterizedRoute route = new ParameterizedRoute(Verb.GET, "/articles/:id");
        Route nullRoute = new Route(Verb.GET, "/articles");
        assertTrue(route.equals(nullRoute));
        assertNull(route.getParameters(nullRoute).get("id"));
    }

    @Test
    public void testInteriorParameter() {
        ParameterizedRoute route = new ParameterizedRoute(Verb.POST, "/articles/:id/missing");
        Route passRoute = new Route(Verb.POST, "/articles/345/missing");
        Route failRoute = new Route(Verb.POST, "/articles/missing");
        assertTrue(route.equals(passRoute));
        assertFalse(route.equals(failRoute));
        assertEquals(route.getParameters(passRoute).get("id"), "345");
    }

    @Test
    public void testDerp() {
        ParameterizedRoute route = new ParameterizedRoute(Verb.OPTIONS, "/articles/:id");
        assertFalse(route.equals("LOLOLOLOL"));
    }
}