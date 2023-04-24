package dodd;

import java.io.*;
import java.math.BigInteger;
import java.util.regex.Pattern;

public class Main {
	
	static final Pattern VALID_REGEX = Pattern.compile("^(0|-?[1-9][0-9]*|[A-Za-z][0-9A-Z_a-z]*)$");
	static final Pattern NUMBER_REGEX = Pattern.compile("^-?[0-9]+$");
	
	static boolean isValidString(String str) {
		return VALID_REGEX.matcher(str).matches();
	}
	
	static boolean isNumberString(String str) {
		return NUMBER_REGEX.matcher(str).matches();
	}
	
	static boolean insertBefore(String val, Item<String> item) {
		if (isNumberString(val) && isNumberString(item.value)) {
			return new BigInteger(val).compareTo(new BigInteger(item.value)) < 1;
		}
		else {
			return val.compareTo(item.value) < 1;
		}
	}
	
	static boolean valueEquals(Item<String> item, String val) {
		return item.value.equals(val);
	}
	
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		Item<String> start = null;
		
		boolean begin = true;
		String input = null;
		
		while (true) {
			if (!begin) {
				System.out.println();
			}
			else {
				begin = false;
			}
			
			System.out.println("Awaiting input...");
			try {
				input = reader.readLine();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
			if (input.isEmpty()) {
				System.out.println("\nProgram terminated!");
				start = Helpers.removeAll(start);
				return;
			}
			else if (input.charAt(0) == '~') {
				if (input.length() == 1) {
					System.out.println("\nDeleting list...");
					start = Helpers.removeAll(start);
				}
				else {
					input = input.substring(1);
					if (isValidString(input)) {
						System.out.println("\nRemoving item...");
						start = Helpers.removeItem(start, input, Main::valueEquals);
					}
					else {
						System.out.println("\nCould not parse input!");
					}
				}
			}
			else if (input.equals("l")) {
				System.out.println("\nLoop print...");
				Helpers.printLoop(start);
			}
			else if (input.equals("i")) {
				System.out.println("\nIterator print...");
				Helpers.printIterator(start);
			}
			else if (input.equals("a")) {
				System.out.println("\nArray print not implemented!");
			}
			else if (input.equals("r")) {
				System.out.println("\nRecursive print...");
				Helpers.printRecursive(start);
			}
			else if (input.equals("f")) {
				System.out.println("\nFold print...");
				Helpers.printFold(start);
			}
			else if (input.equals("b")) {
				System.out.println("\nFoldback print...");
				Helpers.printFoldback(start);
			}
			else if (isValidString(input)) {
				System.out.println("\nInserting item...");
				start = Helpers.insertItem(start, input, Main::insertBefore);
			}
			else {
				System.out.println("\nCould not parse input!");
			}
		}
	}
}
