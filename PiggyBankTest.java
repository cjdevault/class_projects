
/*
 * This is a complete unit test for class PiggyBank.
 * 
 * Programmer Rick Mercer
 */

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PiggyBankTest {

	@Test
	void testConstructorAndTotal() {
		// Constructor take these four arguments: pennies, nickels, dimes, quarters
		PiggyBank one = new PiggyBank(0, 0, 0, 0);
		assertEquals(0.0, one.getTotal(), 0.01);

		PiggyBank two = new PiggyBank(1, 0, 0, 0);
		assertEquals(0.01, two.getTotal(), 0.01);

		PiggyBank three = new PiggyBank(0, 1, 0, 0);
		assertEquals(0.05, three.getTotal(), 0.01);

		PiggyBank four = new PiggyBank(0, 0, 1, 0);
		assertEquals(0.10, four.getTotal(), 0.01);

		PiggyBank five = new PiggyBank(0, 0, 0, 1);
		assertEquals(0.25, five.getTotal(), 0.01);

		PiggyBank six = new PiggyBank(2, 2, 2, 2);
		assertEquals(0.82, six.getTotal(), 0.01);
	}

	@Test
	void testTheAddPennies() {
		PiggyBank one = new PiggyBank(0, 0, 0, 0);
		one.addPennies(0);
		assertEquals(0.00, one.getTotal(), 0.01);

		one.addPennies(1);
		assertEquals(0.01, one.getTotal(), 0.01);

		one.addPennies(6);
		assertEquals(0.07, one.getTotal(), 0.01);
	}

	@Test
	void testTheAddNickels() {
		PiggyBank one = new PiggyBank(0, 0, 0, 0);
		one.addNickels(1);
		assertEquals(0.05, one.getTotal(), 0.01);

		one.addNickels(6);
		assertEquals(0.35, one.getTotal(), 0.01);
	}

	@Test
	void testTheAddDimes() {
		PiggyBank one = new PiggyBank(0, 0, 0, 0);
		one.addDimes(1);
		assertEquals(0.10, one.getTotal(), 0.01);

		one.addDimes(4);
		assertEquals(0.50, one.getTotal(), 0.01);
	}

	@Test
	void testTheAddQuarters() {
		PiggyBank one = new PiggyBank(0, 0, 0, 0);
		one.addQuarters(1);
		assertEquals(0.25, one.getTotal(), 0.01);

		one.addQuarters(3);
		assertEquals(1.00, one.getTotal(), 0.01);
	}

}
