/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.tests.router;

import buoy.router.http.HTTPRoute;
import buoy.router.http.Verb;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author greg
 */
public class TestSimpleRoute {

	public TestSimpleRoute() {
	}

	// TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	@Test
	public void sanityCheck() {
		HTTPRoute<Verb> route = new HTTPRoute<>(Verb.GET, "testpath");
		assertEquals(route.getVerb(), Verb.GET, "Verb is not stored and retrieved.");
		assertEquals(route.getPath(), "testpath", "Path is not stored and retrieved.");
	}

	@Test
	public void identity() {
		HTTPRoute<Verb> route = new HTTPRoute<>(Verb.POST, "some/path");
		assertTrue(route.equals(route), "An object should equal itself.");
	}

	@Test
	public void composition() {
		HTTPRoute<Verb> route = new HTTPRoute<>(Verb.DELETE, "some/other/path");
		HTTPRoute<Verb> otherRoute = new HTTPRoute(Verb.DELETE, "some/other/path");
		assertTrue(route.equals(otherRoute), "The route should equal another identically constructed route.");
	}

	@Test
	public void differentType() {
		HTTPRoute<Verb> route = new HTTPRoute<>(Verb.OPTIONS, "immaterial");
		assertFalse(route.equals("Not a route"), "Code should identify different types as not routes.");
	}

	@Test
	public void differentVerbs() {
		HTTPRoute<Verb> route = new HTTPRoute<>(Verb.GET, "/some/route");
		HTTPRoute<Verb> otherRoute = new HTTPRoute<>(Verb.POST, "/some/route");
		assertFalse(route.equals(otherRoute));
	}

	@Test
	public void differentRoutes() {
		HTTPRoute<Verb> route = new HTTPRoute<>(Verb.GET, "/some/route");
		HTTPRoute<Verb> differentRoute = new HTTPRoute<>(Verb.GET, "/different/route");
		assertFalse(route.equals(differentRoute));
	}

	@Test
	public void nullPath() {
		HTTPRoute<Verb> route = new HTTPRoute<>(Verb.PUT, null);
		HTTPRoute<Verb> otherRoute = new HTTPRoute<>(Verb.PUT, null);
		assertTrue(route.equals(otherRoute));
	}

	@Test
	public void oneNullPath() {
		HTTPRoute<Verb> route = new HTTPRoute<>(Verb.PUT, null);
		HTTPRoute<Verb> otherRoute = new HTTPRoute<>(Verb.PUT, "/not/null/path");
		assertFalse(route.equals(otherRoute));
	}
}
