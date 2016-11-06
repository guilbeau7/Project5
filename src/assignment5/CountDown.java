package assignment5;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

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
