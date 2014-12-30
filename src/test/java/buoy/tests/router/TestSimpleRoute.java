/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.tests.router;

import buoy.router.SimpleRoute;
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
		SimpleRoute<Verb> route = new SimpleRoute<>(Verb.GET, "testpath");
		assertEquals(route.getVerb(), Verb.GET, "Verb is not stored and retrieved.");
		assertEquals(route.getPath(), "testpath", "Path is not stored and retrieved.");
	}

	@Test
	public void identity() {
		SimpleRoute<Verb> route = new SimpleRoute<>(Verb.POST, "some/path");
		assertTrue(route.equals(route), "An object should equal itself.");
	}

	@Test
	public void composition() {
		SimpleRoute<Verb> route = new SimpleRoute<>(Verb.DELETE, "some/other/path");
		SimpleRoute<Verb> otherRoute = new SimpleRoute(Verb.DELETE, "some/other/path");
		assertTrue(route.equals(otherRoute), "The route should equal another identically constructed route.");
	}

	@Test
	public void differentType() {
		SimpleRoute<Verb> route = new SimpleRoute<>(Verb.OPTIONS, "immaterial");
		assertFalse(route.equals("Not a route"), "Code should identify different types as not routes.");
	}

	@Test
	public void differentVerbs() {
		SimpleRoute<Verb> route = new SimpleRoute<>(Verb.GET, "/some/route");
		SimpleRoute<Verb> otherRoute = new SimpleRoute<>(Verb.POST, "/some/route");
		assertFalse(route.equals(otherRoute));
	}

	@Test
	public void differentRoutes() {
		SimpleRoute<Verb> route = new SimpleRoute<>(Verb.GET, "/some/route");
		SimpleRoute<Verb> differentRoute = new SimpleRoute<>(Verb.GET, "/different/route");
		assertFalse(route.equals(differentRoute));
	}

	@Test
	public void nullPath() {
		SimpleRoute<Verb> route = new SimpleRoute<>(Verb.PUT, null);
		SimpleRoute<Verb> otherRoute = new SimpleRoute<>(Verb.PUT, null);
		assertTrue(route.equals(otherRoute));
	}

	@Test
	public void oneNullPath() {
		SimpleRoute<Verb> route = new SimpleRoute<>(Verb.PUT, null);
		SimpleRoute<Verb> otherRoute = new SimpleRoute<>(Verb.PUT, "/not/null/path");
		assertFalse(route.equals(otherRoute));
	}
}
