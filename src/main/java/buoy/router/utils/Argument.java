/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router.utils;

import buoy.router.Invocation;
import com.google.common.collect.ImmutableMap;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author greg
 */
public class Argument {

	/**
	 *
	 */
	public static ImmutableMap<Class, Class> primitives
			= ImmutableMap.<Class, Class>builder()
			.put(byte.class, Byte.class)
			.put(short.class, Short.class)
			.put(int.class, Integer.class)
			.put(long.class, Long.class)
			.put(float.class, Float.class)
			.put(double.class, Double.class)
			.put(boolean.class, Boolean.class)
			.put(char.class, Character.class)
			.build();

	private String name;
	private Constructor type;
	private static final Logger log = Logger.getLogger(Argument.class.getName());

	public Argument(String name, Class type) {
		this.name = name;
		Class usedType = type;
		if (Argument.primitives.containsKey(type)) {
			usedType = Argument.primitives.get(type);
		}
		try {
			this.type = usedType.getConstructor(String.class);
		} catch (NoSuchMethodException | SecurityException ex) {
			log.log(Level.SEVERE, "No usable string constructor found. Will use null for this value.", ex);
			this.type = null;
		}
	}

	public Object getType(Invocation invocation) {
		Object object = null;
		if (this.type != null && invocation.hasKey(this.name)) {
			try {
				object = this.type.newInstance(invocation.getValue(this.name));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
				log.log(Level.SEVERE, "Error intantiating argument from Invocation. Returning null.");
			}
		}

		return object;
	}
}
