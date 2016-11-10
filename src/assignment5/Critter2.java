/* CRITTERS Critter2.java
 * EE422C Project 5 submission by
 * Andrew Guilbeau
 * abg926
 * 16460
 * Josh Minor
 * jm78724
 * 16445
 * Slip days used: 0
 * Fall 2016
 */
package assignment5;

import assignment5.Critter.CritterShape;

/*
 * Super Critter
 * This critter is different from other critters by its doTimeStep and fight methods. During the doTimeStep method,
 * this critter produces 5 offspring all in the same spot, so that in the next timestep, they will all fight eachother,
 * leading to one critter emerging with a lot of health. This critter reproduces with as long as its health is above the 
 * Critter minimum reproduction health. It runs during each time step, in a direction specified which is at a perpendicular 
 * to the previous direction. Specifically, it adds 90 degrees to the previous movement direction each time.
 * This critter only fights if it is fighting either an Algae or another of its kind, else it runs away.
 */
public class Critter2 extends Critter {
	
	private int myDir = 0;
	
	public Critter2(){
		myDir = Critter.getRandomInt(8);
	}
	
	@Override
	public void doTimeStep () {
		
		for (int i = 0; i < 5; i++){
			Critter2 m = new Critter2();
			reproduce(m, myDir);
		}
		
		myDir = (myDir + 2) % 8; // change direction each step
	}
	
	@Override
	/**
	 * Tries to run away if if its not fighting another of its kind.
	 * Else it fights
	 */
	public boolean fight(String opp) {
		if (opp.equals("@") || opp.equals(toString())){
			run(myDir);
			return false;
		} else {
			return true;
		}
	}
	
	public String toString() {
		return "2";
	}
	
	public CritterShape viewShape() { return CritterShape.CIRCLE; }
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.YELLOW; }

}
