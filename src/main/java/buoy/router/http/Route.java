/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router.http;

/**
 * A simple route which requires nothing other than a verb and a path to define itself.
 *
 * @author greg
 */
public class Route<T extends Enum> implements buoy.router.Route<T> {

	private T verb;
	private String path;

	public Route(T verb, String path) {
		this.verb = verb;
		this.path = path;
	}

	@Override
	public T getVerb() {
		return this.verb;
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof buoy.router.Route)) {
			return false;
		}
		buoy.router.Route route = (buoy.router.Route) other;
		return this.getVerb() == route.getVerb()
				&& (this.getPath() == null ? route.getPath() == null : this.getPath().equals(route.getPath()));
	}
}
