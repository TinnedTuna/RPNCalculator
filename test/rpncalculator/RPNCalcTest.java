/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rpncalculator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tuna
 */
public class RPNCalcTest {

    public RPNCalcTest() {
    }
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of run method, of class RPNCalc.
     */
/*    @Test
    public void run() {
        System.out.println("run");
        String input = "";
        RPNCalc instance = new RPNCalc();
        Integer expResult = null;
        Integer result = instance.run(input);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("It shits itself on the empty string.");
    }*/
    
    /**
     * Test simply putting a value onto the stack
     */
    @Test
    public void pushToStack() {
        RPNCalc testInst = new RPNCalc();
        String input = "1";
        Integer expectedOut = 1;
        Integer out = testInst.run(input);
        assertEquals(expectedOut, out);
    }
    
    /**
     * Test summing 2 numbers
     */
    @Test
    public void testSum() {
        RPNCalc testInst = new RPNCalc();
        String input = "22+";
        Integer expectedOut = 4;
        Integer out = testInst.run(input);
        assertEquals(expectedOut, out);
    }
    
    /**
     * Test subtracting 2 numbers
     */
    @Test
    public void testSub() {
        RPNCalc testInst = new RPNCalc();
        String input = "22-";
        Integer expectedOut = 0;
        Integer out = testInst.run(input);
        assertEquals(expectedOut, out);
    }
    
    /**
     * Test mixed addition and subtraction 2 numbers
     */
    @Test
    public void testSubAdd() {
        RPNCalc testInst = new RPNCalc();
        String input = "22-2+";
        Integer expectedOut = 2;
        Integer out = testInst.run(input);
        assertEquals(expectedOut, out);
    }
    
    /**
     * Test multiplying some numbers
     */
    @Test
    public void testMul() {
        RPNCalc testInst = new RPNCalc();
        String input = "222**";
        Integer expectedOut = 8;
        Integer out = testInst.run(input);
        assertEquals(expectedOut, out);
    }
    

    /**
     * Test that looping works as expected. Fairly important to turing
     * completeness
     */
    @Test
    public void testLoop() {
        RPNCalc testInst = new RPNCalc();
        String input = "2[1-]";
        Integer expectedOut = 0;
        Integer out = testInst.run(input);
        assertEquals(expectedOut, out);
    }
    
    
    /**
     * Test a nested loop.
     */
    @Test
    public void testNestedLoop() {
        RPNCalc testInst = new RPNCalc();
        String input = "2[1-2[1-]]";
        Integer expectedOut = 0;
        Integer out = testInst.run(input);
        assertEquals(expectedOut, out);
    }
    
    /**
     * Test that basic duplication and clearing works.
     */
    @Test
    public void testDupClr () {
        RPNCalc testInst = new RPNCalc();
        String input = "2$^";
        Integer expectedOut = 2;
        Integer out = testInst.run(input);
        assertEquals(expectedOut, out);
    }
    
    /**
     * Test that swapping works.
     */
    @Test
    public void testSwp() {
        RPNCalc testInst = new RPNCalc();
        String input = "23&";
        Integer expectedOut = 2;
        Integer out = testInst.run(input);
        assertEquals(expectedOut, out);
    }
    
    /**
     * Test that clearing works
     */
    @Test
    public void testClr () {
        RPNCalc testInst = new RPNCalc();
        String input = "02^";
        Integer expectedOut = 0;
        Integer out = testInst.run(input);
        assertEquals(expectedOut, out);
    }
    
    /**
     * Test All (well, almost).
     */
    @Test
    public void testComplexLoop() {
        RPNCalc testInst = new RPNCalc();
        String input = "23[&$*&1-]^"; // 2^2^2^2 I think...
        Integer expectedOut = 256;
        Integer out = testInst.run(input);
        assertEquals(expectedOut, out);
    }
    
    /**
     * Test ld and str work as expected
     */
    @Test
    public void testLdStr() {
        RPNCalc testInst = new RPNCalc();
        String input = "01,23[&$*&1-]^."; // 2^2^2^2 I think... Then load 0
        Integer expectedOut = 0;
        Integer out = testInst.run(input);
        assertEquals(expectedOut, out);
    }
    
    /**
     * Test summation from 0 to 10. This needs to be more rigorous.
     */
    @Test
    public void testRealInput () {
        RPNCalc testInst = new RPNCalc();
        String input = "52*52*1+*"; // n ( n + 1) / 2 is result
        Integer expectedOut = 110;
        Integer out = testInst.run(input);
        assertEquals(expectedOut, out);
    }
    
    /**
     * Test repeated runs on the same calculator.
     */
    @Test
    public void testRepeated() {
        RPNCalc testInst = new RPNCalc();
        Integer expectedOut = 65536;
        String input = "23[&$*&1-]^";
        testInst.run(input);
        testInst.reset();
        input = "24[&$*&1-]^";
        testInst.run(input);
        testInst.reset();
        Integer out = testInst.run(input);
        testInst.reset();
        assertEquals(expectedOut, out);
    }
    
    /**
     * Test the skip-if-not-zero command.
     */
    @Test
    public void testIffZero() {
        RPNCalc testInst = new RPNCalc();
        Integer expectedOut = 0;
        String input = "220?-+";
        Integer out = testInst.run(input);
        testInst.reset();
        assertEquals(expectedOut, out);
    }
    
    /**
     * Test the skip-if-not-zero command.
     */
    @Test
    public void testIffNotZero() {
        RPNCalc testInst = new RPNCalc();
        Integer expectedOut = 4;
        String input = "221?-+";
        Integer out = testInst.run(input);
        testInst.reset();
        assertEquals(expectedOut, out);
    }
}