/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.tests.router;

/**
 *
 * @author greg
 */
public class TestControllerCausesException {

	public TestControllerCausesException() throws Exception {
		throw new Exception("This should happen.");
	}

	public void publicInstanceMethod() {

	}
}
