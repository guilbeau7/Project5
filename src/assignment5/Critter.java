package assignment5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;




public abstract class Critter {
	
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private static HashSet<String> occupied = new HashSet<String>();
	private static HashSet<String> prevOccupied = new HashSet<String>();
	private static HashMap<String, ArrayList<Critter>> prevWorld = new HashMap<String, ArrayList<Critter>>();
	private boolean hasMoved;
	private boolean fighting;

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	protected String look(int direction, boolean steps) {
		int nextX = this.x_coord;
		int nextY = this.y_coord;
		int check = 1;
		if( steps == true){
			check = 2;
		}
		switch(direction){
		case 1: nextX += check;nextY += check;
				break;
		case 2: nextY += check;
				break;
		case 3: nextX -= check;nextY += check;
				break;
		case 4: nextX -= check;
				break;
		case 5: nextX -= check; nextY -= check;
				break;
		case 6: nextY -= check;
				break;
		case 7: nextX += check; nextY -= check;
				break;
		default: nextX += check;
				break;
		
		} 
		if (nextX >= Params.world_width){
			nextX %= Params.world_width;
		}
		if (nextY >= Params.world_height){
			nextY %= Params.world_height;
		}
		if(nextX < 0){
			nextX += Params.world_width;
		}
		if(nextY < 0){
			nextY += Params.world_height;
		}
		
		String nextPos = Integer.toString(nextX) + " " + Integer.toString(nextY);
		String crit = null;
		if(prevWorld.containsKey(nextPos)){
			crit = prevWorld.get(nextPos).get(0).toString();
		}
		return crit;
	}
	
	/* rest is unchanged from Project 4 */
	
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	/**
	 * Moves a given critter a fixed number of steps.
	 * Should be used with different parameters for walk and run method
	 * @param steps
	 * @param direction
	 */
	private final void move(int steps, int direction){
		//Critters only move if they haven't during a given time step
		if (!this.hasMoved){
			int nextX = this.x_coord;
			int nextY = this.y_coord;
			switch(direction){
			case 1: nextX += steps;nextY += steps;
					break;
			case 2: nextY += steps;
					break;
			case 3: nextX -= steps;nextY += steps;
					break;
			case 4: nextX -= steps;
					break;
			case 5: nextX -= steps; nextY -= steps;
					break;
			case 6: nextY -= steps;
					break;
			case 7: nextX += steps; nextY -= steps;
					break;
			default: nextX += steps;
					break;
			
			} 
			if (nextX >= Params.world_width){
				nextX %= Params.world_width;
			}
			if (nextY >= Params.world_height){
				nextY %= Params.world_height;
			}
			if(nextX < 0){
				nextX += Params.world_width;
			}
			if(nextY < 0){
				nextY += Params.world_height;
			}
			
			//check if attempted move position is valid
			String nextPos = Integer.toString(nextX) + " " + Integer.toString(nextY);
			//if Critter tries to move while fighting must check next spot
			if (fighting){
				if (!occupied.contains(nextPos)){
					this.x_coord = nextX;
					this.y_coord = nextY;
				}
			} else { //move blindly if else
				this.x_coord = nextX;
				this.y_coord = nextY;
			}
		}
		this.hasMoved = true;
	}
	
	protected final void walk(int direction) {
		move(1, direction);
		this.energy -= Params.walk_energy_cost;
	}
	
	protected final void run(int direction) {
		move(2, direction);
		this.energy -= Params.run_energy_cost;
	}
	
	/**
	 * adds a critter into the population born from an already existing critter.
	 * Only runs if parent critter has enough energy to do so
	 * @param offspring
	 * @param direction
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if (this.energy < Params.min_reproduce_energy){
			return;
		} else {
			//Set parameters of new baby
			offspring.energy = (int) Math.floor(this.energy / 2);
			this.energy = (int) Math.ceil(this.energy / 2);
			//put critter in adjacent spot to parent specified in direction
			offspring.x_coord = this.x_coord;
			offspring.y_coord = this.y_coord;
			offspring.move(1, direction);
			offspring.hasMoved = false;
			babies.add(offspring);
		}
	}
	
	/**
	 * Gets a random valid location in critter world to place critter
	 * @return the x coordinate at index 0 and y at index 1
	 */
	private static ArrayList<Integer> getRandomLocation() {
		ArrayList<Integer> locations = new ArrayList<Integer>(2);
		locations.add(getRandomInt(Params.world_width));
		locations.add(getRandomInt(Params.world_height));
		return locations;
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	
	/**
	 * Simulates one time step for every Critter in the population
	 */
	public static void worldTimeStep() {
		for (Critter c : population){
			c.doTimeStep();
		}
		
		//Resolve conflicts
		resolveOverlap();
		prevOccupied.clear();
		prevOccupied = occupied;
		occupied.clear();
		
		//remove dead critters from collection after updating energy
		ArrayList<Critter> dead = new ArrayList<Critter>();
		for (Critter c : population){
			c.energy -= Params.rest_energy_cost;
			if (c.energy <= 0){
				dead.add(c);
			}
		}
		population.removeAll(dead);
		
		//add new Algae into the population
		Algae a;
		for (int i = 0; i < Params.refresh_algae_count; i++){
			a = new Algae();
			a.setEnergy(Params.start_energy);
			ArrayList<Integer> positions = getRandomLocation();
			a.setX_coord(positions.get(0)); 
			a.setY_coord(positions.get(1));
			population.add(a);
		}
		
		//add babies from step into the world
		for (Critter c : babies){
			population.add(c);
		}
		babies.clear(); //reset babies for next time interval
		
		for(Critter crit : population){
			crit.hasMoved = false;
		}
	}
	
	/**
	 * Generates a map representation of the world with arrayLists of critters for each position
	 * @return the hashMap representation of the world
	 */
	private static HashMap<String, ArrayList<Critter>> generateWorldMap(){
		HashMap<String, ArrayList<Critter>> gridBuckets = new HashMap<String, ArrayList<Critter>>();
		String loc;
		for (Critter crit : population){
			loc = ((Integer)crit.x_coord).toString() + " " + ((Integer)crit.y_coord).toString();
			occupied.add(loc);
			if (!gridBuckets.containsKey(loc)){
				ArrayList<Critter> critList = new ArrayList<Critter>();
				critList.add(crit);
				gridBuckets.put(loc, critList);
			} else {
				gridBuckets.get(loc).add(crit);
			}
		}
		return gridBuckets;
	}
	
	private static void resolveOverlap(){
		//Create a map storing all the critters at a given location
		HashMap<String, ArrayList<Critter>> world = generateWorldMap();		
		//iterate through map and resolve conflicts at each position
		Set<String> keys = world.keySet();
		for (String key : keys){
			//if there are more than 2 critters at a given location
			ArrayList<Critter> current = world.get(key);
			while (current.size() > 1){
				Critter A = current.get(0);
				Critter B = current.get(1);
				int toRemove = battle(A, B);
				if (toRemove >= 0){
					current.remove(toRemove);
				} else {//one or both critters moved into a valid spot
					if (A.hasMoved){
						current.remove(A);
					}
					if (B.hasMoved){
						current.remove(B);
					}
				}
			}
		}
		prevWorld = world;
	}
	
	/**
	 * A fight occured between two critters. One must be removed unless they fled during the fight
	 * @param A Critter
	 * @param B Critter
	 * @return integer representing the index of the loser, -1 if there was no result
	 */
	private static int battle(Critter A, Critter B){
		A.fighting = true;
		B.fighting = true;
		boolean resultA = A.fight(B.toString());
		boolean resultB = B.fight(A.toString());
		A.fighting = false;
		B.fighting = false;
		int powerA = 0;
		int powerB = 0;
		if (A.x_coord == B.x_coord && A.y_coord == B.y_coord && A.energy > 0 && B.energy > 0){
			if(resultA){
				powerA = Critter.getRandomInt(A.energy + 1);
			}
			if(resultB){
				powerB = Critter.getRandomInt(B.energy + 1);
			}
			if(powerA >= powerB){
				A.energy += Math.floor(B.energy/2);
				population.remove(B);
				return 1;
			}
			else{
				B.energy += Math.floor(A.energy/2);
				population.remove(A);
				return 0;
			}
		}
		else {
			if (A.energy <= 0){
				population.remove(A);
			}
			if (B.energy <= 0){
				population.remove(B);
			}
		}
		//if they are not in the same spot anymore must return -1
		return -1;
	}
	
	/**
	 * Prints out 2D representation of the population with a border around it
	 */
	public static void displayWorld() {
		int width = Params.world_width + 2;
		int height = Params.world_height + 2;
		String[][] world = new String[height][width];
		
		//add border to world
		for (int row =  0; row < height; row++){
			for (int col = 0; col < width; col++){
				//handle borders of grid
				if (row == 0 || row == height - 1){ //if you are on the top or bottom row need to handle corners
					if (col == 0 || col == width - 1){
						world[row][col] = "+";
					} else {
						world[row][col] = "-";
					}
				} 
				else if (col == 0 || col == width - 1) { //if you are on first or last col put pipe
						world[row][col] = "|";
				} else {
					world[row][col] = " "; //fill rest of spaces with " "
				}
			}
		}
		
		//add in critter data
		for (Critter c : population) {
			world[c.y_coord + 1][c.x_coord + 1] = c.toString();
			
		}
		
		//print result
		for (int row =  0; row < height; row++){
			for (int col = 0; col < width; col++){
				if (col == width - 1){
					System.out.println(world[row][col]);
				} else {
					System.out.print(world[row][col]);
				}
			}
		}
	}
	
	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		//if critter_class does not exist or is not valid subclass of critter throw exception
		try {
			String fullName = myPackage + "." + critter_class_name;
			Class<?> cls = Class.forName(fullName);
			Object obj = cls.newInstance();
			if (!(obj instanceof Critter)){
				throw new InvalidCritterException(critter_class_name);
			} else {
				Critter crit = Critter.class.cast(obj);	//cast object to Critter
				//set starting value of critter before putting it in the world
				crit.energy = Params.start_energy;
				ArrayList<Integer> positions = getRandomLocation();
				crit.x_coord = positions.get(0);
				crit.y_coord = positions.get(1);
				crit.hasMoved = false;
				crit.fighting = false;
				population.add(crit);
			}
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		} catch (InstantiationException e) {
			throw new InvalidCritterException(critter_class_name);
		} catch (IllegalAccessException e) {
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try {
			//check if name is valid and if critters in pop of proper type
			String fullName = myPackage + "." + critter_class_name;
			Class<?> cls = Class.forName(fullName);
			Object o = cls.newInstance();
			if (!(o instanceof Critter)){
				throw new InvalidCritterException(critter_class_name);
			} else {
				for (Critter c : population){
					if (o.getClass().isInstance(c)) {
						result.add(c);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		} catch (InstantiationException e) {
			throw new InvalidCritterException(critter_class_name);
		} catch (IllegalAccessException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		return result;
	}
	
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure thath the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctup update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}
	
	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
	}
	
	
}
