/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router;

import buoy.router.exceptions.InvalidHandlerException;
import buoy.router.utils.Argument;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author greg
 */
public class SimpleHandler<T> implements Handler<T> {

	private Constructor<T> constructor;
	private Method method;
	private List<Argument> arguments;
	private static final Logger log = Logger.getLogger(SimpleHandler.class.getName());

	public SimpleHandler(String classname, String methodname) throws InvalidHandlerException {
		Class<T> clazz = null;
		try {
			clazz = (Class<T>) Class.forName(classname);
			this.constructor = this.extractConstructor(clazz);
		} catch (ClassNotFoundException exception) {
			log.log(Level.SEVERE, "Unable to locate " + classname);
			throw new InvalidHandlerException("No classname " + classname + " found");
		}

		for (Method method : clazz.getMethods()) {
			if (method.getName().equals(methodname)) {
				this.method = method;
			}
		}

		if (this.method == null) {
			throw new InvalidHandlerException("No method of " + methodname + " found on class " + classname);
		}

		this.extractArguments();
	}

	public SimpleHandler(Class<T> clazz, Method method) {
		this.constructor = this.extractConstructor(clazz);
		this.method = method;

		this.extractArguments();
	}

	private Constructor<T> extractConstructor(Class<T> clazz) {
		try {
			return clazz.getConstructor();
		} catch (NoSuchMethodException ex) {
			log.log(Level.SEVERE, "It appears there is no default constructor. Assuming method is static.");
		} catch (SecurityException ex) {
			log.log(Level.SEVERE, "No accessible default constructor. Assuming method is static.");
		}
		return null;
	}

	private void extractArguments() {
		Parameter[] parameters = this.method.getParameters();
		this.arguments = new ArrayList<>(parameters.length);
		for (Parameter parameter : parameters) {
			try {
				this.arguments.add(new Argument(parameter.getName(), parameter.getType()));
			} catch (NoSuchMethodException ex) {
				log.log(Level.SEVERE, "Unable to identify string-based constructor for type " + parameter.getType().getName());
				this.arguments.add(null);
			}
		}
	}

	@Override
	public void handleRequest(Invocation invocation) {
		// Try to instantiate the class we're invoking on
		T subject = null;
		if (!Modifier.isStatic(this.method.getModifiers())) {
			if (this.constructor == null) {
				log.log(Level.SEVERE, "It appears the class constructor was private or no default constructor exists. Attempting to recover by invoking method as a static.");
			} else {
				try {
					subject = this.constructor.newInstance();
				} catch (IllegalAccessException |
						InstantiationException |
						IllegalArgumentException |
						InvocationTargetException exception) {
					log.log(Level.SEVERE, "An error occured trying to instantiate this handler.");
				}
			}
		}
		this.handleRequest(subject, invocation);
	}

	@Override
	public void handleRequest(T t, Invocation invocation
	) {
		// Convert values from invocation to argument list
		Object[] arguments = new Object[this.arguments.size()];
		int argCounter = 0;
		for (Argument argument : this.arguments) {
			try {
				arguments[argCounter] = argument.getType(invocation);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NullPointerException ex) {
				log.log(Level.SEVERE, "Problem instantiating object. Replacing with null.");
				arguments[argCounter] = null;
			}
			argCounter++;
		}
		// Try to invoke the method
		try {
			this.method.invoke(t, arguments);
		} catch (IllegalAccessException ex) {
			log.log(Level.SEVERE, "It appears the specified method is not public. Please ensure it is accessible and try again.");
		} catch (IllegalArgumentException ex) {
			log.log(Level.SEVERE, "Unable to call the specified method, as we were unable to construct its necessary arguments.");
		} catch (InvocationTargetException ex) {
			log.log(Level.SEVERE, "Method reported the following exception.");
		}
	}
}
