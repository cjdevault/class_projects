/**
 * The model for John Conway's Game of Life. This class has all needed methods
 * as method stubs. The preceding comments for each are the specification for
 * the method explaining what each method does.
 *
 * @author Rick Mercer and CJ De Vault
 */
public class GameOfLife {

	// Use this data structure to represent the existence of cells.
	// A true value means a cell exists at that location.
	private boolean[][] society;

	/*-
	 * - Write the constructor so it takes two integer arguments to represent the
	 * number of rows and columns in the game of life. The constructor creates a
	 * society with no cells but space to store rows*cols cells.
	 *
	 * @param rows The height of the grid that shows the cells.
	 * @param cols The width of the grid that shows the cells.
	 * 
	 *             Precondition rows and cols are in the range of 5 through 50
	 */
	public GameOfLife(int rows, int cols) {
		// TODO: Complete this method
		society = new boolean[rows][cols];
	}

	/**
	 * Return the number of rows, which can be indexed from 0..numberOfRows()-1.
	 *
	 * @return The height of the society.
	 */
	public int numberOfRows() {
		// TODO: Complete this method
		return society.length;
	}

	/**
	 * The number of columns, which can be indexed from 0..numberOfColumns()-1.
	 *
	 * @return The height of the society.
	 */
	public int numberOfColumns() {
		// TODO: Complete this method
		// Ensure there is at least one row to avoid ArrayIndexOutOfBoundsException
		if (society.length > 0) {
			// Return the length of the first row to get the number of columns
			return society[0].length;
		} else {
			// If society has no rows, return 0 for the number of columns
			return 0;
		}
	}

	/**
	 * Place a new cell in the society.
	 * 
	 * @param row The row to grow the cell.
	 * 
	 * @param col The column to grow the cell.
	 *
	 *            Precondition: row and col are in range of the 2D array.
	 */
	public void growCellAt(int row, int col) {
		// TODO: Complete this method
		if (row >= 0 && row < numberOfRows() && col >= 0 && col < numberOfColumns()) {
			society[row][col] = true;
		} else {
			// Handle the case where row or col are out of bounds
			throw new IllegalArgumentException("Row or column out of bounds.");
		}
	}

	/*
	 * Return true if there is a cell at the given row and column. Return false if
	 * there is no cell at the specified location.
	 *
	 * @param row The row to check.
	 * 
	 * @param col The column to check.
	 * 
	 * @return True if there is a cell at the given row or false if none
	 *
	 * Precondition: row and col are in range.
	 */
	public boolean cellAt(int row, int col) {
		// TODO: Complete this method
		// Directly return boolean value at specified location
		return society[row][col];
	}

	/*
	 * Note this will not be tested for a grade. But you might find this useful
	 * while inplementing GameOfLife.
	 * 
	 * Return one big string of cells to represent the current state of the society
	 * of cells (see output below where '.' represents an empty space and 'O' is a
	 * live cell. There is no need to test toString. Simply use it to visually
	 * inspect if needed. Here is one sample output from toString:
	 *
	 * GameOfLife society = new GameOfLife(4, 14); society.growCellAt(1, 2);
	 * society.growCellAt(2, 3); society.growCellAt(3, 4);
	 * System.out.println(society.toString());
	 *
	 * @return A textual representation of this society of cells.
	 *
	 * Sample Output: .............. ..O........... ...O.......... ....O.........
	 */
	@Override
	public String toString() {
		// TODO: Complete this method. you need to send this object a
		// toString message to get 100% code coverage with EclEmma.
		// Do not write an assertion for this in an @Test.
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < society.length; row++) {
			for (int col = 0; col < society[row].length; col++) {
				if (society[row][col]) { // If there is a live cell at the current position
					sb.append('O');
				} else { // If the current position is empty
					sb.append('.');
				}
			}
			sb.append('\n'); // Append a newline character after each row
		}
		return sb.toString();
	}

	/*-
	 * The return values should always be in the range of 0 through 8.
	 *
	 * @return The number of neighbors around any cell using wrap around.
	 * 
	 *
	 * Count the neighbors around the given location. Use wraparound. A cell in row
	 * 0 has neighbors in the last row if a cell is in the same column, or the
	 * column to the left or right. In this example, cell 0,5 has two neighbors in
	 * the last row, cell 2,8 has four neighbors, cell 2,0 has four neighbors, cell
	 * 1,0 has three neighbors. The cell at 3,8 has 3 neighbors. 
	 *
	 * .....O..O
	 * O........
	 * O.......O
	 * O.......O
	 * ....O.O..
	 *
	 * Precondition: row and col are in range of the 2D array
	 *   
	 * @param row
	 * @param col
	 * @return how many neighbors are around the given location, alsways 0..8
	 */
	public int neighborCount(int row, int col) {
		// TODO: Complete this method
		int count = 0;
		int numRows = society.length;
		int numCols = society[0].length;

		// There are 8 possible directions for neighbors
		int[] directions = { -1, 0, 1 }; // Represents the row/column movement: left, stay, right/up, down

		for (int dRow : directions) {
			for (int dCol : directions) {
				// Skip the case where dRow and dCol are both 0, as that's the cell itself, not
				// a neighbor
				if (dRow == 0 && dCol == 0) {
					continue;
				}
				// Calculate the neighbor's position with wraparound
				int neighborRow = (row + dRow + numRows) % numRows;
				int neighborCol = (col + dCol + numCols) % numCols;

				// Check if the neighbor is alive and increment the count if so
				if (society[neighborRow][neighborCol]) {
					count++;
				}
			}
		}

		return count;
	}

	/*
	 * Update the state to represent the next society. Typically, some cells will
	 * die off while others are born.
	 */
	public void update() {
		// TODO: Complete this method
		int numRows = society.length;
		int numCols = society[0].length;
		boolean[][] nextGeneration = new boolean[numRows][numCols]; // Temp array for the next generation

		// Iterate over each cell to apply the rules of the Game of Life
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				int neighbors = neighborCount(row, col); // Get the number of live neighbors

				if (society[row][col]) { // Cell is alive
					// Cell lives on if it has 2 or 3 neighbors, dies otherwise
					nextGeneration[row][col] = (neighbors == 2 || neighbors == 3);
				} else { // Cell is dead
					// A dead cell with exactly 3 live neighbors becomes alive
					nextGeneration[row][col] = (neighbors == 3);
				}
			}
		}

		// Update the society to the next generation
		society = nextGeneration;
	}
}