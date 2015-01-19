/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router;

import buoy.router.exceptions.HandlerExecutionException;
import buoy.router.exceptions.HandlerInstantiationException;
import buoy.router.exceptions.InvalidHandlerDefinitionException;
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
	private boolean isStatic;

	public SimpleHandler(String classname, String methodname) throws InvalidHandlerDefinitionException {
		// Find the class that we'll be working with
		Class<T> clazz = null;
		try {
			clazz = (Class<T>) Class.forName(classname);
		} catch (ClassNotFoundException exception) {
			log.log(Level.SEVERE, "Unable to locate " + classname);
			throw new InvalidHandlerDefinitionException("No classname " + classname + " found");
		}
		// Find the method, if one exists
		for (Method method : clazz.getMethods()) {
			if (method.getName().equals(methodname)) {
				this.method = method;
			}
		}
		this.checkMethod();
		if (!this.isStatic) {
			// Extract its constructor
			this.constructor = this.extractConstructor(clazz);
			this.checkConstructor();
		}

		this.extractArguments();
	}

	public SimpleHandler(Class<T> clazz, Method method) throws InvalidHandlerDefinitionException {
		this.method = method;
		this.checkMethod();
		if (!this.isStatic) {
			this.constructor = this.extractConstructor(clazz);
			this.checkConstructor();
		}

		this.extractArguments();
	}

	/**
	 * Verifies that the selected method is able to be called.
	 *
	 * Checks to be sure that the user-provided method is one which is callable. In order to be callable it must 1)
	 * Exist 2) Be public
	 *
	 * @throws InvalidHandlerDefinitionException
	 */
	private void checkMethod() throws InvalidHandlerDefinitionException {
		if (this.method == null) {
			throw new InvalidHandlerDefinitionException(("No such method found on class."));
		}
		int modifiers = this.method.getModifiers();
		if (!Modifier.isPublic(modifiers)) {
			throw new InvalidHandlerDefinitionException("Method " + this.method.getName() + " is not public. Unable to proceed.");
		}
		this.isStatic = Modifier.isStatic(modifiers);
	}

	/**
	 * Verifies that the class can be used as a handler.
	 *
	 * Checks that the user-provided class is one which can be used. In order to be used it must either 1) have a
	 * non-private, non-protected default constructor OR 2) be referencing a static method
	 *
	 * @throws InvalidHandlerDefinitionException
	 */
	private void checkConstructor() throws InvalidHandlerDefinitionException {
		if (this.constructor == null) {
			throw new InvalidHandlerDefinitionException("No dfeault constructor found, therefore method needs to be static.");
		}
	}

	/**
	 * Fetches the default constructor for the provided clazz.
	 *
	 * Really just a convenience method because there are several exceptions which need to be caught on the constructor
	 * which we don't really want child methods to be burdened with over and over.
	 *
	 * @param clazz
	 * @return
	 */
	private Constructor<T> extractConstructor(Class<T> clazz) {
		try {
			return clazz.getConstructor();
		} catch (NoSuchMethodException | SecurityException ex) {
			log.log(Level.SEVERE, "It appears there is no accessible default constructor. Proceeding in case method is static. Please check that constructor is not private or protected.");
		}
		return null;
	}

	/**
	 * Retrieves the arguments for the method, and crafts a way for those arguments to be distilled from the invocation
	 * object received later on.
	 */
	private void extractArguments() {
		Parameter[] parameters = this.method.getParameters();
		this.arguments = new ArrayList<>(parameters.length);
		for (Parameter parameter : parameters) {
			this.arguments.add(new Argument(parameter.getName(), parameter.getType()));
		}
	}

	@Override
	public void handleRequest(Invocation invocation) throws HandlerInstantiationException, HandlerExecutionException {
		// Try to instantiate the class we're invoking on
		T subject = null;
		if (!this.isStatic) {
			try {
				subject = this.constructor.newInstance();
			} catch (IllegalAccessException |
					InstantiationException |
					IllegalArgumentException |
					InvocationTargetException exception) {
				log.log(Level.SEVERE, "An error occured trying to instantiate this handler.");
				throw new HandlerInstantiationException("An error occurred while instantiating the handling class.");
			}
		}
		this.handleRequest(subject, invocation);
	}

	@Override
	public void handleRequest(T t, Invocation invocation) throws HandlerExecutionException {
		// Convert values from invocation to argument list
		Object[] arguments = new Object[this.arguments.size()];
		int argCounter = 0;
		for (Argument argument : this.arguments) {
			arguments[argCounter++] = argument.getType(invocation);
		}
		// Try to invoke the method
		try {
			this.method.invoke(t, arguments);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			log.log(Level.SEVERE, "There was an exception while executing your method.");
			throw new HandlerExecutionException("There was an exception while executing your method.", ex);
		}
	}
}
