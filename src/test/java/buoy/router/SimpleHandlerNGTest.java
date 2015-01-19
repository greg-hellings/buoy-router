/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router;

import buoy.router.exceptions.HandlerExecutionException;
import buoy.router.exceptions.HandlerInstantiationException;
import buoy.router.exceptions.InvalidHandlerDefinitionException;
import java.lang.reflect.Method;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.testng.annotations.Test;

/**
 *
 * @author Gregory
 */
public class SimpleHandlerNGTest {

	public SimpleHandlerNGTest() {
	}

	@Test
	public void testStringClass() throws InvalidHandlerDefinitionException, HandlerExecutionException {
		SimpleHandler<TestController> simpleHandler = new SimpleHandler<>(TestController.class.getCanonicalName(), "publicInstanceMethod");
		TestController controller = mock(TestController.class);
		simpleHandler.handleRequest(controller, new TestInvocation());
		verify(controller, times(1)).publicInstanceMethod();
	}

	@Test
	public void testWithClassType() throws InvalidHandlerDefinitionException, NoSuchMethodException {
		SimpleHandler<TestController> simpleHandler = new SimpleHandler<>(TestController.class, TestController.class.getMethod("publicInstanceMethod"));
	}

	@Test(expectedExceptions = {InvalidHandlerDefinitionException.class})
	public void testNoClassExists() throws InvalidHandlerDefinitionException {
		SimpleHandler<TestController> simpleHandler = new SimpleHandler<>("com.nothing.NoSuchClass", "noSuchArgument");
	}

	@Test(expectedExceptions = {InvalidHandlerDefinitionException.class})
	public void testNoMethodExists() throws InvalidHandlerDefinitionException {
		SimpleHandler<TestController> simpleHandler = new SimpleHandler<>(TestController.class.getCanonicalName(), "invalidMethodName");
	}

	/*
	 * These three tests cover trying to use an instance method on a class that the Handler cannot instantiate because there
	 * is no default constructor
	 */
	@Test(expectedExceptions = {InvalidHandlerDefinitionException.class})
	public void testWithNoDefaultConstructorPublicInstanceMethodClass() throws InvalidHandlerDefinitionException, NoSuchMethodException {
		SimpleHandler<TestControllerNoDefaultConstructor> simpleHandler = new SimpleHandler<>(TestControllerNoDefaultConstructor.class, TestControllerNoDefaultConstructor.class.getMethod("publicInstanceMethod"));
	}

	@Test(expectedExceptions = {InvalidHandlerDefinitionException.class})
	public void testWithNoDefaultConstructorPrivateInstanceMethodClass() throws InvalidHandlerDefinitionException {
		SimpleHandler<TestControllerNoDefaultConstructor> simpleHandler = new SimpleHandler<>(TestControllerNoDefaultConstructor.class.getCanonicalName(), "privateInstanceMethod");
	}

	@Test(expectedExceptions = {InvalidHandlerDefinitionException.class})
	public void testWithNoDefaultConstructorProtectedInstanceMethodClass() throws InvalidHandlerDefinitionException, NoSuchMethodException {
		Method method = TestControllerNoDefaultConstructor.class.getDeclaredMethod("protectedInstanceMethod");
		SimpleHandler<TestControllerNoDefaultConstructor> simpleHandler = new SimpleHandler<>(TestControllerNoDefaultConstructor.class, method);
	}

	/*
	 * These three tests look at static methods on a class that the Handler cannot instantiate because there is no
	 * default constructor
	 */
	@Test
	public void testWithNoDefaultConstructorPublicStaticMethod() throws InvalidHandlerDefinitionException, HandlerInstantiationException, HandlerExecutionException {
		SimpleHandler<TestControllerNoDefaultConstructor> simpleHandler = new SimpleHandler<>(TestControllerNoDefaultConstructor.class.getCanonicalName(), "publicStaticMethod");
		simpleHandler.handleRequest(new TestInvocation());
	}

	@Test(expectedExceptions = {InvalidHandlerDefinitionException.class})
	public void testWithNoDefaultConstructorProtectedStaticMethod() throws InvalidHandlerDefinitionException {
		SimpleHandler<TestControllerNoDefaultConstructor> simpleHandler = new SimpleHandler<>(TestControllerNoDefaultConstructor.class.getCanonicalName(), "protectedStaticMethod");
	}

	@Test(expectedExceptions = {InvalidHandlerDefinitionException.class})
	public void testWithNoDefaultConstructorPrivateStaticMethod() throws InvalidHandlerDefinitionException {
		SimpleHandler<TestControllerNoDefaultConstructor> simpleHandler = new SimpleHandler<>(TestControllerNoDefaultConstructor.class.getCanonicalName(), "privateStaticMethod");
	}

	/*
	 * These two tests look at methods when the constructor is protected
	 */
	@Test(expectedExceptions = {InvalidHandlerDefinitionException.class})
	public void testWithProtectedConstructorPublicInstanceMethod() throws InvalidHandlerDefinitionException {
		SimpleHandler<TestControllerProtectedConstructor> simpleHandler = new SimpleHandler<>(TestControllerProtectedConstructor.class.getCanonicalName(), "publicInstanceMethod");
	}

	@Test
	public void testWithProtectedConstructorPublicStaticMethod() throws InvalidHandlerDefinitionException, HandlerInstantiationException, HandlerExecutionException {
		SimpleHandler<TestControllerProtectedConstructor> simpleHandler = new SimpleHandler<>(TestControllerProtectedConstructor.class.getCanonicalName(), "publicStaticMethod");
		simpleHandler.handleRequest(new TestInvocation());
	}

	/*
	 * These two tests looks at methods when the constructor is private
	 */
	@Test(expectedExceptions = {InvalidHandlerDefinitionException.class})
	public void testWithPrivateConstructorPublicInstanceMethod() throws InvalidHandlerDefinitionException {
		SimpleHandler<TestControllerPrivateConstructor> simpleHandler = new SimpleHandler<>(TestControllerPrivateConstructor.class.getCanonicalName(), "publicInstanceMethod");
	}

	@Test
	public void testWithPrivateConstructorPublicStaticMethod() throws InvalidHandlerDefinitionException, HandlerInstantiationException, HandlerExecutionException {
		SimpleHandler<TestControllerPrivateConstructor> simpleHandler = new SimpleHandler<>(TestControllerPrivateConstructor.class.getCanonicalName(), "publicStaticMethod");
		simpleHandler.handleRequest(new TestInvocation());
	}

	/*
	 * The simple test case, with the handler instantiating the object.
	 */
	@Test
	public void testInstantiateClass() throws InvalidHandlerDefinitionException, HandlerInstantiationException, HandlerExecutionException {
		SimpleHandler<TestController> simpleHandler = new SimpleHandler<>(TestController.class.getCanonicalName(), "stringArgumentMethod");
		simpleHandler.handleRequest(new TestInvocation());
	}

	/*
	 * Test the instantiation of the objects themselves and their valid parameters
	 */
	@Test
	public void testMethodInvokedProperly() throws InvalidHandlerDefinitionException, HandlerExecutionException {
		SimpleHandler<TestController> simpleHandler = new SimpleHandler<>(TestController.class.getCanonicalName(), "stringArgumentMethod");
		TestController testController = mock(TestController.class);
		simpleHandler.handleRequest(testController, new TestInvocation());
		verify(testController, times(1)).stringArgumentMethod("Test String");
	}

	/*
	 * Test that a method that has an argument the handler cannot understand at present.
	 */
	@Test
	public void testMethodWithBadArgument() throws InvalidHandlerDefinitionException, HandlerExecutionException {
		SimpleHandler<TestController> simpleHandler = new SimpleHandler<>(TestController.class.getCanonicalName(), "badArgument");
		TestController testController = mock(TestController.class);
		simpleHandler.handleRequest(testController, new TestInvocation());
		verify(testController, times(1)).badArgument(null);
	}

	/*
	 * Test that the calling of a static method actually happens.
	 */
	@Test
	public void testStaticMethod() throws InvalidHandlerDefinitionException, HandlerInstantiationException, HandlerExecutionException {
		SimpleHandler<TestController> simpleHandler = new SimpleHandler<>(TestController.class.getCanonicalName(), "publicStaticMethod");
		TestController.Incrementer incrementer = mock(TestController.Incrementer.class);
		TestController.incrementer = incrementer;
		simpleHandler.handleRequest(new TestInvocation());
		verify(incrementer, times(1)).increment();
	}

	@Test(expectedExceptions = {InvalidHandlerDefinitionException.class})
	public void testNoDefaultConstructorAndInvalidMethod() throws InvalidHandlerDefinitionException, HandlerInstantiationException, HandlerExecutionException {
		SimpleHandler<TestControllerNoDefaultConstructor> simpleHandler = new SimpleHandler<>(TestControllerNoDefaultConstructor.class.getCanonicalName(), "notAMethod");
		simpleHandler.handleRequest(new TestInvocation());
	}

	@Test(expectedExceptions = {InvalidHandlerDefinitionException.class})
	public void testPrivateConstructor() throws InvalidHandlerDefinitionException, HandlerInstantiationException, HandlerExecutionException {
		SimpleHandler<TestControllerPrivateConstructor> simpleHandler = new SimpleHandler<>(TestControllerPrivateConstructor.class.getCanonicalName(), "publicInstanceMethod");
		simpleHandler.handleRequest(new TestInvocation());
	}

	@Test(expectedExceptions = {HandlerInstantiationException.class})
	public void testControllerConstructorThrowsException() throws NoSuchMethodException, InvalidHandlerDefinitionException, HandlerInstantiationException, HandlerExecutionException {
		SimpleHandler<TestControllerCausesException> simpleHandler = new SimpleHandler<>(TestControllerCausesException.class.getCanonicalName(), "publicInstanceMethod");
		simpleHandler.handleRequest(new TestInvocation());
	}

	@Test(expectedExceptions = {HandlerExecutionException.class})
	public void testControllerMethodThrowsException() throws InvalidHandlerDefinitionException, HandlerExecutionException, HandlerInstantiationException {
		SimpleHandler<TestController> simpleHandler = new SimpleHandler<>(TestController.class.getCanonicalName(), "publicThrowsBadException");
		simpleHandler.handleRequest(new TestInvocation());
	}
}
