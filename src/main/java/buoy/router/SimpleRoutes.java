/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router;

import buoy.router.utils.RouteReader;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;

/**
 * A very simple hash map of routes to handlers, just to serve the simple cases where route lookup is relatively
 * straightforward.
 *
 * @author greg
 */
public class SimpleRoutes implements Routes {

	private final Map<Route, Handler> routes = new HashMap<>();

	public SimpleRoutes() {
	}

	public SimpleRoutes(RouteReader reader) {
		for (Pair<Route, Handler> pair : reader) {
			this.addRoute(pair.getKey(), pair.getValue());
		}
	}

	@Override
	public void addRoute(Route route, Handler handler) {
		this.routes.put(route, handler);
	}

	@Override
	public Handler getHandlerForRoute(Route route) {
		Handler handler = null;
		if (routes.containsKey(route)) {
			handler = routes.get(route);
		}
		return handler;
	}

	@Override
	public void clearRoutes() {
		this.routes.clear();
	}

}
