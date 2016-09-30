package hw0;

import java.util.Random;

/**
 * RandomHello selects a random greeting to display to the user.
 */
public class RandomHello {

    /**
     * Uses a RandomHello object to print
     * a random greeting to the console.
     */
    public static void main(String[] argv) {
        RandomHello randomHello = new RandomHello();
        System.out.println(randomHello.getGreeting());
    }

    /**
     * @return a random greeting from a list of five different greetings.
     */
    public String getGreeting() {
        
    	String greeting1 = "Hello, World";
    	String greeting2 = "Hello";
    	String greeting3 = "Good Morning";
    	String greeting4 = "Good Afternoon";
    	String greeting5 = "Gooday mate";
    	
    	String[] greetings = {greeting1, greeting2, greeting3, greeting4, greeting5};
    	
    	Random random = new Random();
    	int index = random.nextInt(greetings.length);
    	
    	return greetings[index];
    }
}