/**
 * This is part of HW0: Environment Setup and Java Introduction.
 */
package hw0;

/**
 * This is a simple object that has a volume.
 */
// You may not make Ball implement the Comparable interface.
public class Ball {

    private double volume; //not used if return statement is not volume

    /**
     * Constructor that creates a new ball object with the specified volume.
     * @param volume Volume of the new object.
     */
    public Ball(double volume) {
        this.volume = volume; //change from volume to this.volume in order to refer to the class volume
    }
    

    /**
     * Returns the volume of the Ball.
     * @return the volume of the Ball.
    */
    public double getVolume() {
    	
        return volume; //change from return 0 to return volume
    }

}


