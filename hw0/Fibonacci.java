	/**
	 * This is part of HW0: Environment Setup and Java Introduction.
	 */
	package hw0;
	
	/**
	 * Fibonacci calculates the <var>n</var>th term in the Fibonacci sequence.
	 *
	 * The first two terms of the Fibonacci sequence are both 1,
	 * and each subsequent term is the sum of the previous two terms.
	 *
	 * @author mbolin
	 */
	public class Fibonacci {
	
	    /**
	     * Calculates the desired term in the Fibonacci sequence.
	     *
	     * @param n the index of the desired term; the first index of the sequence is 0
	     * @return the <var>n</var>th term in the Fibonacci sequence
	     * @throws IllegalArgumentException if <code>n</code> is not a nonnegative number
	     */
		
		// testThrowsillegalArgumentException: Threw exception for 0 even though it's nonneg
		// testBaseCase: 0 is negative n>=0
		// testInductiveCase: getFibTerm(2) expected 2 but was 1
		
	    public int getFibTerm(int n) {
	        // 0 is being thrown as negative when it is not, change from n <= 0 to n < 0
	    	if (n < 0) {
	            throw new IllegalArgumentException(n + " is negative");
	        // 2 is expected not 1 , change from n <= 2 to n < 2    
	        } else if (n < 2) {
	            return 1;
	        } else {
	            return getFibTerm(n - 1) + getFibTerm(n - 2); //change operation from - to +
	        }
	    }
	    
	}
