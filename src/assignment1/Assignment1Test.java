/*
 *  ASSIGNMENT 1
 * 
 *	Do problem 1.1.30. Write a class RelativePrime containing a static method generateMatrix(). You may use the gcd code from p. 4 of the text. Your method should pass testRelativePrime() in the JUnit test class A1Test.
 *	Do problem 1.1.31. Write a class RandomConnections containing a static method run(). Sample output from my solution, when called with the arguments 17 and 0.25, is shown on the next page. Note that the specific lines drawn between dots will vary from one run to another. In A1Test, testRandomConnections() calls your method.
 * 	Do problem 1.1.33. Write a class Matrix containing the methods specified in the text. Tests for these methods are provided in A1Test.
 * 	Do problems 1.1.36 and 1.1.37. You have been given a class Shuffle containing the method shuffle() from p. 32. Add a second static method badShufﬂe() as described in problem 1.1.37. Add two more static methods testShufﬂe() and testBadShufﬂe() as described. A1Test has tests calling your methods. Also submit a mathematical proof that, with badShufﬂe(), “the resulting order is not equally likely to be one of the N! possibilities”.
 * 
 */

package assignment1;

import edu.princeton.cs.introcs.*;

import static org.junit.Assert.*;
import org.junit.Test;
import static java.util.Arrays.*;

public class Assignment1Test {

	/**
	 * Two doubles within EPSILON of each other are considered equal for these
	 * tests.
	 */
	public static final double EPSILON = 0.0000001;

	@Test
	public void testRelativePrime() {
		boolean[][] a = RelativePrime.generateMatrix(7);
		assertTrue(deepEquals(new boolean[][] {
				{ false, true, false, false, false, false, false },
				{ true, true, true, true, true, true, true },
				{ false, true, false, true, false, true, false },
				{ false, true, true, false, true, true, false },
				{ false, true, false, true, false, true, false },
				{ false, true, true, true, true, false, true },
				{ false, true, false, false, false, true, false } }, a));
	}

	@Test
	public void testRandomConnections() {
		// This is graphical and inspected visually
		RandomConnections.run(17, 0.25);
		StdDraw.show(2000); // Pause for 2 seconds
	}

	@Test
	public void testDot() {
		double[] x = { 1.0, 2.0, 3.0 };
		double[] y = { 1.0, 0.5, 2.5 };
		assertEquals(9.5, Matrix.dot(x, y), EPSILON);
	}

	@Test
	public void testMatrixMatrixMult() {
		double[][] a = { { 3, -1, 4 }, { 1, 0, 2 } };
		double[][] b = { { 1, 2 }, { 0, -3 }, { 2, 1 } };
		double[][] ab = { { 11, 13 }, { 5, 4 } };
		double[][] ba = { { 5, -1, 8 }, { -3, 0, -6 }, { 7, -2, 10 } };
		assertTrue(deepEquals(ab, Matrix.mult(a, b)));
		assertTrue(deepEquals(ba, Matrix.mult(b, a)));
	}

	@Test
	public void testTranspose() {
		double[][] a = { { 3, -1, 4 }, { 1, 0, 2 } };
		double[][] aT = { { 3, 1 }, { -1, 0 }, { 4, 2 } };
		assertTrue(deepEquals(aT, Matrix.transpose(a)));
	}

	@Test
	public void testMatrixVectorMult() {
		double[][] a = { { 3, -1, 4 }, { 1, 0, 2 } };
		double[] x = { 1.0, 2.0, 3.0 };
		double[] ax = { 13, 7 };
		assertTrue(java.util.Arrays.equals(ax, Matrix.mult(a, x)));
	}

	@Test
	public void testVectorMatrixMult() {
		double[] x = { 1.0, 2.0, 3.0 };
		double[][] b = { { 1, 2 }, { 0, -3 }, { 2, 1 } };
		double[] xb = { 7, -1 };
		assertTrue(java.util.Arrays.equals(xb, Matrix.mult(x, b)));
	}

	@Test
	public void testShuffle() {
		StdOut.println("Good shuffle:");
		Shuffle.testShuffle(10, 100000);
	}

	@Test
	public void testBadShuffle() {
		StdOut.println("Bad shuffle:");
		Shuffle.testBadShuffle(10, 100000);
	}

}
