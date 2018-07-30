import java.io.*;

class parser {
	public static void main(String[] args) {
		parser("(%%META ProgramId=\"6de9ceb4-3c92-4aed-95c1-d544eb659537\")");
		parser("(%%META PlateThickness=8 unit=mm)");
		parser("(%%META PlateSize=\"3000x1500\" Unit=\"mm\")");
		parser("(%%META Material=\"Mild Steel\")");
	}

	public static void parser(String input) {
		// Check the given input for basic format errors
		if (!input.substring(0, 8).equals("(%%META ") || input.charAt(input.length() - 1) != ')') {
			System.out.println("Error: Invalid format");
			return;
		}
		
		// Absolute index
		int index = 8;

		while (index < input.length() - 1) {
			StringBuilder key = new StringBuilder();
			StringBuilder value = new StringBuilder();			

			// Get key
			for (; index < input.length() - 1 && input.charAt(index) != '='; index++) {
				key.append(input.charAt(index));
			}

			// Skip whitespace
			index++;

			// Check if value is given inside the quotation marks
			if (index < input.length() - 1 && input.charAt(index) == '"' && input.charAt(index - 1) != '\\') {
				index++;
				// Get value - if it's given inside the quotation marks
				for (; input.charAt(index) != '"'; index++) {
					value.append(input.charAt(index));
				}
				index++;
			}	
			else {
				// Get value - if it's given without the quotation marks
				for (; index < input.length() - 1 && input.charAt(index) != ' '; index++) {
					value.append(input.charAt(index));
				}
			}
			
			// Skip whitespace if there's another <key,value> pair
			if (index < input.length() - 1) index++;

			System.out.printf("<%s,%s>\n", key, value);
		}

		System.out.printf("\n");
	}
}