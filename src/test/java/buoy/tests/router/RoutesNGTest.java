/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.tests.router;

import buoy.router.*;
import buoy.router.exceptions.InvalidHandlerDefinitionException;
import buoy.router.http.Route;
import buoy.router.http.Verb;
import buoy.router.utils.RouteReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Gregory
 */
public class RoutesNGTest {

	public static class TestRouteReader implements RouteReader {

		public static final List<Pair<buoy.router.Route, Handler>> list;

		static {
			list = new ArrayList<>();
			Class<TestController> c = TestController.class;
			Method m = null;
			try {
				m = c.getMethod("publicInstanceMethod");
			} catch (NoSuchMethodException | SecurityException ex) {
				Logger.getLogger(RoutesNGTest.class.getName()).log(Level.SEVERE, null, ex);
			}
			try {
				list.add(new Pair<>(new Route<>(Verb.GET, "/first/path"), new SimpleHandler<>(c, m)));
				list.add(new Pair<>(new Route<>(Verb.GET, "/second/path"), new SimpleHandler<>(c, m)));
				list.add(new Pair<>(new Route<>(Verb.OPTIONS, "/first/path"), new SimpleHandler<>(c, m)));
			} catch (InvalidHandlerDefinitionException exception) {
				// oops
			}
			list.iterator();
		}

		@Override
		public Iterator<Pair<buoy.router.Route, Handler>> iterator() {
			return list.iterator();
		}

	}

	private static TestRouteReader testRouteReader = new TestRouteReader();
	private SimpleRoutes simpleRoutes;

	public RoutesNGTest() {
	}

	@BeforeMethod
	public void setUpMethod() throws Exception {
		this.simpleRoutes = new SimpleRoutes(RoutesNGTest.testRouteReader);
	}

	/**
	 * Test of addRoute method, of class SimpleRoutes.
	 */
	@Test
	public void testAddRoute() throws InvalidHandlerDefinitionException {
		SimpleHandler<TestController> simpleHandler = new SimpleHandler<>(TestController.class.getCanonicalName(), "stringArgumentMethod");
		this.simpleRoutes.addRoute(new Route<>(Verb.PUT, "/added/route"), simpleHandler);
		assertTrue(simpleHandler == this.simpleRoutes.getHandlerForRoute(new Route<>(Verb.PUT, "/added/route")));
	}

	/**
	 * Test of getHandlerForRoute method, of class SimpleRoutes.
	 */
	@Test
	public void testGetHandlerForRoute() {
		assertTrue(
				this.simpleRoutes.getHandlerForRoute(new Route<>(Verb.GET, "/first/path"))
				== TestRouteReader.list.get(0).getValue()
		);
	}

	/**
	 * Test of clearRoutes method, of class SimpleRoutes.
	 */
	@Test
	public void testClearRoutes() {
		this.simpleRoutes.clearRoutes();
		assertTrue(this.simpleRoutes.getHandlerForRoute(new Route<>(Verb.GET, "/first/path")) == null);
	}

	@Test
	public void testConstructorLol() {
		SimpleRoutes routes = new SimpleRoutes();
		assertTrue(routes instanceof SimpleRoutes);
	}

}
