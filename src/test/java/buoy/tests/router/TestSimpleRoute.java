/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.tests.router;

import buoy.router.SimpleRoute;
import buoy.router.http.Verb;
import org.testng.Assert;
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
	public static void sanityCheck() {
		SimpleRoute<Verb> route = new SimpleRoute<>(Verb.GET, "testpath");
		Assert.assertEquals(route.getVerb(), Verb.GET, "Verb is not stored and retrieved.");
		Assert.assertEquals(route.getPath(), "testpath", "Path is not stored and retrieved.");
	}

	@Test
	public static void identity() {
		SimpleRoute<Verb> route = new SimpleRoute<>(Verb.POST, "some/path");
		Assert.assertTrue(route.equals(route), "An object should equal itself.");
	}

	@Test
	public static void composition() {
		SimpleRoute<Verb> route = new SimpleRoute<>(Verb.DELETE, "some/other/path");
		SimpleRoute<Verb> otherRoute = new SimpleRoute(Verb.DELETE, "some/other/path");
		Assert.assertTrue(route.equals(otherRoute), "The route should equal another identically constructed route.");
	}

	@Test
	public static void differentType() {
		SimpleRoute<Verb> route = new SimpleRoute<>(Verb.OPTIONS, "immaterial");
		Assert.assertFalse(route.equals("Not a route"), "Code should identify different types as not routes.");
	}

	@org.testng.annotations.BeforeClass
	public static void setUpClass() throws Exception {
	}

	@org.testng.annotations.AfterClass
	public static void tearDownClass() throws Exception {
	}

	@org.testng.annotations.BeforeMethod
	public void setUpMethod() throws Exception {
	}

	@org.testng.annotations.AfterMethod
	public void tearDownMethod() throws Exception {
	}
}
