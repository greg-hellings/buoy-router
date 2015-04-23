/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.tests.router;

import buoy.router.http.Route;
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
		Route<Verb> route = new Route<>(Verb.GET, "testpath");
		assertEquals(route.getVerb(), Verb.GET, "Verb is not stored and retrieved.");
		assertEquals(route.getPath(), "testpath", "Path is not stored and retrieved.");
	}

	@Test
	public void identity() {
		Route<Verb> route = new Route<>(Verb.POST, "some/path");
		assertTrue(route.equals(route), "An object should equal itself.");
	}

	@Test
	public void composition() {
		Route<Verb> route = new Route<>(Verb.DELETE, "some/other/path");
		Route<Verb> otherRoute = new Route(Verb.DELETE, "some/other/path");
		assertTrue(route.equals(otherRoute), "The route should equal another identically constructed route.");
	}

	@Test
	public void differentType() {
		Route<Verb> route = new Route<>(Verb.OPTIONS, "immaterial");
		assertFalse(route.equals("Not a route"), "Code should identify different types as not routes.");
	}

	@Test
	public void differentVerbs() {
		Route<Verb> route = new Route<>(Verb.GET, "/some/route");
		Route<Verb> otherRoute = new Route<>(Verb.POST, "/some/route");
		assertFalse(route.equals(otherRoute));
	}

	@Test
	public void differentRoutes() {
		Route<Verb> route = new Route<>(Verb.GET, "/some/route");
		Route<Verb> differentRoute = new Route<>(Verb.GET, "/different/route");
		assertFalse(route.equals(differentRoute));
	}

	@Test
	public void nullPath() {
		Route<Verb> route = new Route<>(Verb.PUT, null);
		Route<Verb> otherRoute = new Route<>(Verb.PUT, null);
		assertTrue(route.equals(otherRoute));
	}

	@Test
	public void oneNullPath() {
		Route<Verb> route = new Route<>(Verb.PUT, null);
		Route<Verb> otherRoute = new Route<>(Verb.PUT, "/not/null/path");
		assertFalse(route.equals(otherRoute));
	}
}
