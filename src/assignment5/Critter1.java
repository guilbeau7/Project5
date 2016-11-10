/* CRITTERS Critter1.java
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

import java.util.*;

import assignment5.Critter.CritterShape;

/**
 * Macho Critter
 * This critter will behave differently than all the other critters in the sense that it
 * is very strong, and is very willing to fight, but only if it knows that it has a good chance of 
 * winning, for it fears being embarrassed by losing a fight. This critter will only fight if it has at least 
 * 80% of its starting health. It does not move during doTimeStep because it is not in very good aerobic shape.
 * When it flees a potential fight, it will just walk away in the direction it came from, 
 * and not run, because running would be more embarrassing. It will also reproduce only if it has double 
 * the starting health of a normal critter, so its offspring can be strong just like it.
 * It will also produce more offspring on average, being a random number between 0 and 3.
 * All the babies are placed on top of each other, meaning they all will scatter the first world time step they are in.
 *
 */
public class Critter1 extends Critter {

	private int dir;
	
	public Critter1() {
		dir = Critter.getRandomInt(8);
	}
	
	@Override
	public void doTimeStep() {
		walk(dir);
		if (getEnergy() > (Params.start_energy * 2)) {
			int random = Critter.getRandomInt(3);
			for (int i = 0; i < random; i++){
				Critter1 m = new Critter1();
				reproduce(m, dir);
			}
		}
		//set new direction
		dir = Critter.getRandomInt(8);
	}

	@Override
	public boolean fight(String opponent) {
		if (getEnergy() > (int)(Params.start_energy * 0.8)) return true;
		else {
			if (this.look((dir + 4) % 8, false) == null){
				walk((dir + 4) % 8); //walks back in the opposite direction
			}
			return false;
		}
		
	}
	
	public String toString() {
		return "1";
	}
	
	public void test (List<Critter> l) {
		
	}
	
	public CritterShape viewShape() { return CritterShape.TRIANGLE; }
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.RED; }
}
