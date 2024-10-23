
/* This class models a real world PiggyBank to which pennies, 
 * nickels, dimes and quarters can be added
 * 
 * Programmer CJ De Vault 
*/

public class PiggyBank {
	// Instance variables to store the counts of each coin type
	private int pennies;
	private int nickels;
	private int dimes;
	private int quarters;

	// Constructor initializes the piggy bank with a specific number of coins
	public PiggyBank(int pennies, int nickels, int dimes, int quarters) {
		this.pennies = pennies;
		this.nickels = nickels;
		this.dimes = dimes;
		this.quarters = quarters;
	}

	// Method to calculate and return the total amount in the piggy bank
	public double getTotal() {
		return 0.01 * pennies + 0.05 * nickels + 0.10 * dimes + 0.25 * quarters;
	}

	// Methods to add coins to the piggy bank
	public void addPennies(int pennies) {
		this.pennies += pennies;
	}

	public void addNickels(int nickels) {
		this.nickels += nickels;
	}

	public void addDimes(int dimes) {
		this.dimes += dimes;
	}

	public void addQuarters(int quarters) {
		this.quarters += quarters;
	}
}