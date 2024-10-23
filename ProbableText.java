/*
 * CSC210 Software Development 
 * This java project utilizes a hashmap assigning key-value pairs with ngrams 
 * and possible followers to generate probabilistic text.  
 * @author CJ De Vault
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class ProbableText {
	private HashMap<String, ArrayList<Character>> ngramMap = new HashMap<>();
	private Random random = new Random();
	private StringBuilder bookText = new StringBuilder();
	private int ngramSize;

	// Constructor loads the text and builds the n-gram map.
	public ProbableText(String fileName, int ngramSize) {
		this.ngramSize = ngramSize;
		loadBookText(fileName); // Load the book's text from a file.
		// Only build the n-gram map if the text is longer than the n-gram size.
		if (bookText.length() > ngramSize) {
			buildNgramMap();
		}
	}

	// Builds a map where each n-gram is a key and its value is a list of characters
	// that follow it in the text.
	private void buildNgramMap() {
		for (int i = 0; i <= bookText.length() - ngramSize; i++) {
			// Extract an n-gram and the character that immediately follows it.
			String ngram = bookText.substring(i, i + ngramSize);
			char nextChar;
			if (i + ngramSize < bookText.length()) {
				nextChar = bookText.charAt(i + ngramSize);
			} else {
				nextChar = ' ';
			}
			// Check if the ngramMap already contains the n-gram key.
			if (ngramMap.containsKey(ngram)) {
				ngramMap.get(ngram).add(nextChar);
			} else {
				// If not, create a new ArrayList, add nextChar to it, and put it in the map.
				ArrayList<Character> followers = new ArrayList<>();
				followers.add(nextChar);
				ngramMap.put(ngram, followers);
			}
		}
	}

	// Reads the book's text from a file and appends it to the StringBuilder.
	private void loadBookText(String fileName) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(fileName));
			while (scanner.hasNextLine()) {
				// Read all file input into one StringBuilder using append()
				bookText.append(scanner.nextLine()).append(' ');
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	// Generates random text of a specified length using the n-gram model.
	public String printRandom(int length) {
		// Check if the book's text is shorter than the desired n-gram size.
		if (bookText.length() < ngramSize)
			return "Book text is too short.";

		// Start the generated text with a randomly selected n-gram.
		String currentNgram = pickRandomNgram();
		StringBuilder generatedText = new StringBuilder(currentNgram);

		// Loop to build the rest of the text, one character at a time until reaching
		// the desired length.
		for (int i = ngramSize; i < length; i++) {
			// Retrieve the list of possible next characters for the current n-gram.
			ArrayList<Character> possibleNextChars = ngramMap.get(currentNgram);
			// If there are no possible next characters, end the loop early.
			if (possibleNextChars == null || possibleNextChars.isEmpty())
				break;

			// Select a random next character from the list of possible characters.
			char nextChar = possibleNextChars.get(random.nextInt(possibleNextChars.size()));
			// Append this character to the generated text.
			generatedText.append(nextChar);
			// Update the current n-gram by removing its first character and appending the
			// new character.
			currentNgram = currentNgram.substring(1) + nextChar;
		}

		// After generating the text, format it to meet the line length requirement and
		// return.
		return formatGeneratedText(generatedText.toString());
	}

	// Helper method selects and returns a random n-gram from the map.
	private String pickRandomNgram() {
		Object[] ngrams = ngramMap.keySet().toArray();
		return (String) ngrams[random.nextInt(ngrams.length)];
	}

	// Formats the generated text so that each line has up to 60 characters and
	// doesn't break words.
	private String formatGeneratedText(String text) {
		String[] words = text.split(" ");
		StringBuilder formattedText = new StringBuilder();
		int lineLength = 0;

		for (String word : words) {
			if (lineLength + word.length() > 60) {
				formattedText.append("\n"); // Start a new line if adding the word exceeds 60 characters.
				lineLength = 0;
			}
			if (lineLength > 0) {
				formattedText.append(" "); // Add a space before the word if it's not the start of a new line.
				lineLength++;
			}
			formattedText.append(word); // Add the word to the current line.
			lineLength += word.length();
		}

		return formattedText.toString();
	}

	// Getters for testing
	public StringBuilder getContent() {
		return bookText;
	}

	public HashMap<String, ArrayList<Character>> getAllNgrams() {
		return ngramMap;
	}

	public Random getRandom() {
		return random;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter file name: ");
		String fileName = in.nextLine();
		System.out.println();
		System.out.println("Enter size of ngram: ");
		int ngramSize = in.nextInt();
		System.out.println();
		ProbableText rw = new ProbableText(fileName, ngramSize);
		System.out.println("Enter number of characters to generate: ");
		int textSize = in.nextInt();
		System.out.println();
		System.out.println(rw.printRandom(textSize));
		in.close();
	}

}
