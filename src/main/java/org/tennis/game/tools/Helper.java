package org.tennis.game.tools;

import java.util.Scanner;

public class Helper {

	// Generate a double
	public static double getRandomNumber() {
		double x = Math.random();
		return x;
	}

	// Read user entry
	@SuppressWarnings("resource")
	public static String readText(String message) {

		String entry = "";
		do {
			System.out.print(message);
			 entry = new Scanner(System.in).nextLine().toUpperCase();
		} while (entry.isEmpty());

		return entry;

	}

}
