/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router.utils;

import buoy.router.TestInvocation;
import java.lang.reflect.InvocationTargetException;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;
import org.testng.annotations.Test;

/**
 *
 * @author Gregory
 */
public class ArgumentNGTest {

	public ArgumentNGTest() {
	}

	/**
	 * Test of getType method, of class Argument.
	 */
	@Test
	public void testGetType() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Argument argument = new Argument("testString", GetTypeSuccessClass.class);
		GetTypeSuccessClass answer = (GetTypeSuccessClass) argument.getType(new TestInvocation());
		assertEquals(answer.word, "Test String");
	}

	@Test
	public void testGetTypePrimitive() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Argument argument = new Argument("testInt", int.class);
		Integer response = (Integer) argument.getType(new TestInvocation());
		assertEquals(response, new Integer(1));
	}

	@Test
	public void testGetTypeBuiltIn() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Argument argument = new Argument("testInteger", Integer.class);
		Integer response = (Integer) argument.getType(new TestInvocation());
		assertEquals(response, new Integer(2));
	}

	@Test
	public void testArgumentHasPrivateConstructorGivesNull() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Argument argument = new Argument("testString", GetTypeNoSuchMethodClass.class);
		GetTypeNoSuchMethodClass response = (GetTypeNoSuchMethodClass) argument.getType(new TestInvocation());
		assertNull(response);
	}

	@Test
	public void testAgumentReturnsNull() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Argument argument = new Argument("testInteger", GetTypeFailureClass.class);
		GetTypeFailureClass response = (GetTypeFailureClass) argument.getType(new TestInvocation());
		assertNull(response);
	}

	@Test
	public void missingArgument() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Argument argument = new Argument("nonexistent", GetTypeSuccessClass.class);
		GetTypeSuccessClass response = (GetTypeSuccessClass) argument.getType(new TestInvocation());
		assertNull(response);
	}

	/**
	 * A test class that should succeed
	 */
	public static class GetTypeSuccessClass {

		public String word;

		public GetTypeSuccessClass() {
			fail("Should not have been here in " + GetTypeSuccessClass.class.getName());
		}

		public GetTypeSuccessClass(String word) {
			this.word = word;
		}
	}

	/**
	 * A test class that should fail with a security exception
	 */
	public static class GetTypeNoSuchMethodClass {

		private GetTypeNoSuchMethodClass(String word) {
		}
	}

	/**
	 * A test class that should fail with a no such method exception
	 */
	public static class GetTypeFailureClass {

		public GetTypeFailureClass(String dummy) throws Exception {
			throw new Exception("Test exception.");
		}
	}
}
