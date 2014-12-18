/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router;

import buoy.router.exceptions.InvalidHandlerException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author greg
 */
public class SimpleHandler implements Handler {

	private Class clazz;
	private Method method;
	private static Logger log = Logger.getLogger(SimpleHandler.class.getName());

	public SimpleHandler(String classname, String methodname) throws InvalidHandlerException {
		try {
			this.clazz = Class.forName(classname);
		} catch (ClassNotFoundException exception) {
			log.log(Level.SEVERE, "Unable to locate " + classname);
			throw new InvalidHandlerException("No classname " + classname + " found");
		}

		Method[] methods = this.clazz.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodname)) {
				this.method = method;
			}
		}

		if (this.method == null) {
			throw new InvalidHandlerException("No method of " + methodname + " found on class " + classname);
		}
	}
}
