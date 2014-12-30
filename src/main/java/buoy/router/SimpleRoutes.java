/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router;

import buoy.router.utils.RouteReader;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 * A very simple hash map of routes to handlers, just to serve the simple cases
 * where route lookup is relatively straightforward.
 *
 * @author greg
 */
public class SimpleRoutes implements Routes {

	private final List<Pair<Route, Handler>> routes = new ArrayList<>();

	public SimpleRoutes() {
	}

	public SimpleRoutes(RouteReader reader) {
		for (Pair<Route, Handler> pair : reader) {
			this.addRoute(pair.getKey(), pair.getValue());
		}
	}

	@Override
	public final void addRoute(Route route, Handler handler) {
		this.routes.add(new Pair<>(route, handler));
	}

	@Override
	public Handler getHandlerForRoute(Route route) {
		for (Pair<Route, Handler> pair : this.routes) {
			if (pair.getKey().equals(route)) {
				return pair.getValue();
			}
		}
		return null;
	}

	@Override
	public void clearRoutes() {
		this.routes.clear();
	}

}
