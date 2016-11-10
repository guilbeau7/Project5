/* CRITTERS Critter3.java
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
/*
 * Praying mantis
 * These creatures are purely carnivorous, but their small brain means they try to eat anything
 * they come into contact with. 
 * Because they are carnivorous, if they come into contact with algae they will still eat it,
 * but doing so will cause them to become sick and run themselves to death unless they get better
 * The only way for them to to become healthy again is to cosume a non-algae critter.
 * As they are prone to quicker death from algae, they have adapted over time to reproduce two offspring
 * at a time, in an attempt to maintain their population. 
 */
public class Critter3 extends Critter {

	private boolean sick = false;
	
	@Override
	public void doTimeStep() {
		int direction = Critter.getRandomInt(8);
		walk(direction);	
		if(sick && this.look(direction, true) == "@"){
			run(direction);
		}
		
		if(getEnergy() > 110 && !sick){
			Critter3 child1 = new Critter3();
			Critter3 child2 = new Critter3();
			reproduce(child1, Critter.getRandomInt(8));
			reproduce(child2, Critter.getRandomInt(8));
		}
	}

	@Override
	public boolean fight(String opponent) {
		if(opponent.equals("@")){
			sick = true;
		}
		else if(sick){
			sick = false;
		}
		return true;
	}
	
	public String toString() {
		return "3";
	}
	
	public CritterShape viewShape() { return CritterShape.STAR; }
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.PURPLE; }
	
}
