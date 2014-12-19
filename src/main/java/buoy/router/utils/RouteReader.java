/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router.utils;

import buoy.router.Handler;
import buoy.router.Route;
import javafx.util.Pair;

/**
 *
 * @author greg
 */
public interface RouteReader extends Iterable<Pair<Route, Handler>> {
}
