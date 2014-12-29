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
		if (key.equals("testInt") || key.equals("testInteger") || key.equals("testString")) {
			return true;
		}
		return false;
	}

	@Override
	public String getValue(String key) {
		if (key.equals("testInt")) {
			return "1";
		} else if (key.equals("testInteger")) {
			return "2";
		} else if (key.equals("testString")) {
			return "Test String";
		}

		fail("Should not have been here.");
		return "";
	}

}
