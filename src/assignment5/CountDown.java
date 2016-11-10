/* CRITTERS GUI CountDown.java
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

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

/*
 * Class that counts down until animation is no longer running
 */
public class CountDown extends TimerTask{
	Timer parent;
	boolean isDone = true;
	int runtime = 5;
	
	public CountDown(Timer t){
		parent = t;
	}
	
    public void run() {
    	Platform.runLater(new Runnable() {
    		public void run() {
    			if (!CritterButtons.animationRunning){
            		parent.cancel();
            	} else {
            		Critter.worldTimeStep();
                    Critter.displayWorld();
                    CritterButtons.displayStats();
            	} 
    		}
    	});
    }
}
