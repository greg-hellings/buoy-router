/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router;

/**
 *
 * @author greg
 */
public interface Routes {
    void addRoute(Route route, Handler handler);
    Handler getHandlerForRoute(Route route);
    void clearRoutes();
}
