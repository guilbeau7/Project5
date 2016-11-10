/* CRITTERS Critter4.java
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
 * Venus Flytrap
 * Similar to algae, this critter acts like a plant most of the time, but a quarter of the time it will
 * choose to fight a critter it encounters.
 * Also, it a unique species that can actually move itself, and as such it will always try to run away from an
 * encounter, before randomly determining whether it will fight or not. It also changes its 
 * direction with every time step call, ensuring that it will walk all around the map.
 * Also, the flytrap will not fight a member of it's own species, so during fights against other flytraps
 * it will act like algae, and a winner will be chosen arbitrarily
 */
public class Critter4 extends Critter {
	
	private int myDir = 0;
	
	@Override
	public void doTimeStep () {
		
	}
	
	@Override
	/**
	 * Always tries to run away first, will choose to fight 1/4th of the time 
	 */
	public boolean fight(String opp) {
		run(myDir);
		if(opp.equals("4")){
			return false;
		}
		if(Critter.getRandomInt(2) == 0){
			return true;
		}
		return false;
	}
	
	public String toString() {
		return "4";
	}

	public CritterShape viewShape() { return CritterShape.TRIANGLE; }
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.BLUE; }
}
