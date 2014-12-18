/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router;

import buoy.router.exceptions.InvalidHandlerException;
import buoy.router.utils.Argument;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author greg
 */
public class SimpleHandler implements Handler {

	private Class clazz;
	private Method method;
	private List<Constructor> arguments;
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

	public SimpleHandler(Class clazz, Method method) {
		this.clazz = clazz;
		this.method = method;
	}

	private void extractArguments() {
		Parameter[] parameters = this.method.getParameters();
		this.arguments = new ArrayList<>(parameters.length);
		for (Parameter parameter : parameters) {
			this.arguments.add(new Argument(parameter.getName(), parameter.getType()));
		}
	}

	@Override
	public void handleRequest() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
