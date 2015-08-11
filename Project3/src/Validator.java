import java.util.Scanner;

public class Validator {
	public static String getString(Scanner sc, String prompt) {
		System.out.print(prompt);
		String s = sc.nextLine();
		// sc.nextLine();
		return s;
	}

	public static int getInt(Scanner sc, String prompt) {
		int i = 0;
		boolean isValid = false;
		while (isValid == false) {
			System.out.print(prompt);
			if (sc.hasNextInt()) {
				i = sc.nextInt();
				isValid = true;
			} else {
				System.out.println("Error! Invalid integer value.\nTry again.");
			}
			sc.nextLine();
		}
		return i;
	}

	public static double getDouble(Scanner sc, String prompt) {
		double d = 0;
		boolean isValid = false;
		while (isValid == false) {
			System.out.print(prompt);
			if (sc.hasNextDouble()) {
				d = sc.nextDouble();
				isValid = true;
			} else {
				System.out.println("Error! Invalid integer value.\nTry again.");
			}
			sc.nextLine(); 
		}
		return d;
	}

	public static int getInt(Scanner sc, String prompt, int min, int max) {
		int i = 0;
		boolean isValid = false;
		while (isValid == false) {
			i = getInt(sc, prompt);
			if (i < min) {
				System.out.println("Error! Number must not be less than " + min
						+ ".");
			} else if (i > max)
				System.out.println("Error! Number must not be greater than "
						+ max + ".");
			else {
				isValid = true;
			}
		}
		return i;
	}

	public static double getDouble(Scanner sc, String prompt, double min,
			double max) {
		double d = 0;
		boolean isValid = false;
		while (isValid == false) {
			d = getDouble(sc, prompt);
			if (d < min) {
				System.out.println("Error! Number must not be less than " + min
						+ ".");
			} else if (d > max) {
				System.out.println("Error! Number must not be greater than "
						+ max + ".");
			} else {
				isValid = true;
			}
		}
		return d;
	}
}