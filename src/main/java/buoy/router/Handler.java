/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router;

import buoy.router.exceptions.HandlerExecutionException;
import buoy.router.exceptions.HandlerInstantiationException;

/**
 *
 * @author greg
 */
public interface Handler<T> {

	void handleRequest(T t, Invocation invocation) throws HandlerExecutionException;

	void handleRequest(Invocation invocation) throws HandlerInstantiationException, HandlerExecutionException;
}
