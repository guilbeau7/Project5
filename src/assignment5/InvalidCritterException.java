/* CRITTERS GUI InvalidCritterException.java
 * EE422C Project 4b submission by
 * Josh Minor
 * jm78724
 * 16445
 * Andrew Guilbeau
 * abg926
 * 16460
 * Slip days used: <0>
 * Fall 2016
 */

package assignment5;

public class InvalidCritterException extends Exception {
	String offending_class;
	
	public InvalidCritterException(String critter_class_name) {
		offending_class = critter_class_name;
	}
	
	public String toString() {
		return "Invalid Critter Class: " + offending_class;
	}

}
