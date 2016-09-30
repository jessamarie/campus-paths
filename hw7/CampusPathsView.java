package hw7;

import java.util.Scanner;

/**
 *  CampusPathsView sets up the scanner for user input and 
 *  program output
 */

public class CampusPathsView {
	
	
	/*
	 * This class is not an ADT
	 */
	
	
	private static Scanner reader;
	
	/**
	 * printMenu prints the menu
	 */
	public void printMenu() {
		System.out.println("Menu:\n" + "\tm : See Menu\n"
				+ "\tr : Find Route\n" + "\tb : Lists All Buildings\n"
				+ "\tq : Quit");
	}

	/**
	 * prompt prints the string which asks for input
	 * 
	 * @param str the string to print
	 */
	public void prompt(String str) {
		System.out.print(str);
	}

	/**
	 * getInput reads the user input and return it
	 * 
	 * @return the user input
	 */
	public String getStringInput() {
		return reader.nextLine();
	}

	/**
	 * isInteger checks if the input is an integer
	 * 
	 * @param the input to be evaluated
	 * @return true if the user input is an integer
	 */
	public boolean isInteger(String e) {
		
		try {
			Integer.parseInt(e);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
		
	}

	/**
	 * print prints a string with next line
	 * 
	 * @param str the string to print
	 */
	public void print(String str) {
		System.out.println(str);
	}

	/**
	 * close the scanner
	 */
	public void close() {
		reader.close();
	}
	
	/**
	 * starts the scanner
	 */
	public void open() {
		reader = new Scanner(System.in);
	}
}
