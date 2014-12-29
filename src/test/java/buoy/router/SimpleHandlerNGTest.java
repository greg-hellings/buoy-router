/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router;

import buoy.router.exceptions.InvalidHandlerException;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;
import org.testng.annotations.Test;

/**
 *
 * @author Gregory
 */
public class SimpleHandlerNGTest {

	public SimpleHandlerNGTest() {
	}

	@Test
	public void testStringClass() throws InvalidHandlerException {
		SimpleHandler<TestController> simpleHandler = new SimpleHandler<>(TestController.class.getCanonicalName(), "noArgumentMethod");
		TestController controller = mock(TestController.class);
		simpleHandler.handleRequest(controller, new TestInvocation());
		verify(controller, times(1)).noArgumentMethod();
	}

	@Test(expectedExceptions = {InvalidHandlerException.class})
	public void testNoClassExists() throws InvalidHandlerException {
		SimpleHandler<TestController> simpleHandler = new SimpleHandler<>("com.nothing.NoSuchClass", "noSuchArgument");
	}

	@Test(expectedExceptions = {InvalidHandlerException.class})
	public void testNoMethodExists() throws InvalidHandlerException {
		SimpleHandler<TestController> simpleHandler = new SimpleHandler<>(TestController.class.getCanonicalName(), "invalidMethodName");
	}

	@Test
	public void testWithClassType() throws InvalidHandlerException, NoSuchMethodException {
		SimpleHandler<TestController> simpleHandler = new SimpleHandler<>(TestController.class, TestController.class.getMethod("noArgumentMethod"));
	}

	@Test
	public void testWithNoDefaultConstructorClass() throws InvalidHandlerException, NoSuchMethodException {
		SimpleHandler<TestControllerNoDefaultConstructor> simpleHandler = new SimpleHandler<>(TestControllerNoDefaultConstructor.class, TestControllerNoDefaultConstructor.class.getMethod("instanceMethod"));
	}

	@Test
	public void testInstantiateClass() throws InvalidHandlerException {
		SimpleHandler<TestController> simpleHandler = new SimpleHandler<>(TestController.class.getCanonicalName(), "stringArgumentMethod");
		simpleHandler.handleRequest(new TestInvocation());
	}

	@Test
	public void testMethodInvokedProperly() throws InvalidHandlerException {
		SimpleHandler<TestController> simpleHandler = new SimpleHandler<>(TestController.class.getCanonicalName(), "stringArgumentMethod");
		TestController testController = mock(TestController.class);
		simpleHandler.handleRequest(testController, new TestInvocation());
		verify(testController, times(1)).stringArgumentMethod("Test String");
	}

	@Test
	public void testMethodWithBadArgument() throws InvalidHandlerException {
		SimpleHandler<TestController> simpleHandler = new SimpleHandler<>(TestController.class.getCanonicalName(), "badArgument");
	}
}
