/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router;

import static org.testng.Assert.fail;

/**
 *
 * @author Gregory
 */
public class TestInvocation implements Invocation {

	@Override
	public boolean hasKey(String key) {
		return key.equals("testInt") || key.equals("testInteger") || key.equals("testString");
	}

	@Override
	public String getValue(String key) {
		switch (key) {
			case "testInt":
				return "1";
			case "testInteger":
				return "2";
			case "testString":
				return "Test String";
		}

		fail("Should not have been here.");
		return "";
	}

}
