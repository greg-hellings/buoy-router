/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buoy.router.utils;

import buoy.router.Invocation;
import java.lang.reflect.InvocationTargetException;
import static org.testng.Assert.*;
import org.testng.annotations.ExpectedExceptions;
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
        GetTypeSuccessClass answer = (GetTypeSuccessClass) argument.getType(new InvocationTest());
        assertEquals(answer.word, "Test String");
    }
    
    @Test
    public void testGetTypePrimitive() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Argument argument = new Argument("testInt", int.class);
        Integer response = (Integer) argument.getType(new InvocationTest());
        assertEquals(response, new Integer(1));
    }
    
    @Test
    public void testGetTypeBuiltIn() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Argument argument = new Argument("testInteger", Integer.class);
        Integer response = (Integer) argument.getType(new InvocationTest());
        assertEquals(response, new Integer(2));
    }
    
    @Test(expectedExceptions = {NoSuchMethodException.class})
    public void testArgumentThrowsSecurityException() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Argument argument = new Argument("testString", GetTypeNoSuchMethodClass.class);
        GetTypeNoSuchMethodClass response = (GetTypeNoSuchMethodClass) argument.getType(new InvocationTest());
        fail("Exception should have happened already.");
    }
    
    @Test(expectedExceptions = {InvocationTargetException.class})
    public void testAgumentThrowsException() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Argument argument = new Argument("testInteger", GetTypeFailureClass.class);
        GetTypeFailureClass response = (GetTypeFailureClass) argument.getType(new InvocationTest());
        fail("Exception should have happened already.");
    }
    
    private class InvocationTest implements Invocation {

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
