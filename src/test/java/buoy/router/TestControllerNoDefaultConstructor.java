/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router;

/**
 *
 * @author Gregory
 */
public class TestControllerNoDefaultConstructor {

	public TestControllerNoDefaultConstructor(String dummy) {

	}

	public TestControllerNoDefaultConstructor() throws Exception {
		throw new Exception("I Shouldn't Be Here");
	}

	public static void staticMethod() {

	}

	public void instanceMethod() {

	}
}
