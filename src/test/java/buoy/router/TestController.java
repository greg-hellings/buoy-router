/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router;

import java.util.List;

/**
 *
 * @author Gregory
 */
public class TestController {

	public static class Incrementer {

		private int counter = 0;

		public void increment() {
			this.counter++;
		}
	}

	public static Incrementer incrementer = new Incrementer();

	public TestController() {

	}

	public void noArgumentMethod() {
		// Pass
	}

	public void stringArgumentMethod(String testString) {
		// Pass
	}

	public void badArgument(List<Integer> list) {

	}

	public static void staticMethod() {
		incrementer.increment();
	}
}
