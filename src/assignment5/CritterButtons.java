/* CRITTERS GUI CritterButtons.java
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



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CritterButtons {
	static ComboBox<String> critterSelection;
	static ComboBox<String> critterStatsSelection;
	static TextField stepCount = new TextField();
	static TextField seedCount = new TextField();
	static TextField makeCount = new TextField();
	static Label statsField = new Label();
	static Label errorMessageStep = new Label("Please Enter Valid Number");
	static Label errorMessageSeed = new Label("Please Enter Valid Number");
	static Label errorMessageMake = new Label("Please Enter Valid Number");
	static Slider runSpeed;
	static boolean animationRunning = false;
	static Button quitButton;
	static Button seedButton;
	static Button stepButton;
	static Button makeButton;
	static Button animationButton;
	static ArrayList<Node> items;
	private static Timer t;
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.

	// Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }
	
    /*
     * Sets up all buttons in the class
     * Adds all buttons which are to be disabled to items
     */
	public static void setUpButtons(){
		seedButton();
		stepButton();
		makeButton();
		animationButton();
		quitButton();
		createClassDropDown();
		setUpSlider();
		setUpErrorLabels();
		items = new ArrayList<Node>();
		items.add(seedButton);
		items.add(stepButton);
		items.add(makeButton);
		items.add(critterSelection);
		items.add(critterStatsSelection);
		items.add(runSpeed);
		items.add(makeCount);
		items.add(stepCount);
		items.add(seedCount);
	}
	
	/*
	 * Set up label properties
	 */
	public static void setUpErrorLabels(){
		errorMessageSeed.setTextFill(javafx.scene.paint.Color.RED);
		errorMessageSeed.setVisible(false);
		errorMessageSeed.setFont(new Font(12.0));
		errorMessageStep.setTextFill(javafx.scene.paint.Color.RED);
		errorMessageStep.setVisible(false);
		errorMessageStep.setFont(new Font(12.0));
		errorMessageMake.setTextFill(javafx.scene.paint.Color.RED);
		errorMessageMake.setVisible(false);
		errorMessageMake.setFont(new Font(12.0));
	}
	
	/*
	 * Sets up step button
	 */
	public static void stepButton(){
		Button btn = new Button();
		btn.setText("Step");
		btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	try{
            		for (int i = 0; i < Integer.parseInt(stepCount.getText()); i++){
                		Critter.worldTimeStep();
                        Critter.displayWorld();
                        errorMessageStep.setVisible(false);
                	}
                    displayStats();
            	} catch(Exception e){
            		//Create new textField for an error message
                    errorMessageStep.setVisible(true);
            	}
            }
        });
		stepButton = btn;
	}
	
	/*
	 * Sets up seed button
	 */
	public static void seedButton(){
		Button btn = new Button();
		btn.setText("Set Seed");
		btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	try{
                	Critter.setSeed(Integer.parseInt(seedCount.getText()));
                    errorMessageSeed.setVisible(false);
            	} catch(NumberFormatException e){
            		//Create new textField for an error message
                    errorMessageSeed.setVisible(true);

            	}
            }
        });
		seedButton = btn;
	}
	
	/*
	 * Creates a drop down menu full of all critters which could be created
	 * updates the global critterSelection ComboBox
	 */
	public static void createClassDropDown(){
		ObservableList<String> options;
		List<String> l = new ArrayList<String>();
		File current = new File("./src/assignment5");
		//create list of files you don't want to include in drop down
		List<String> dontInclude = new ArrayList<String>();
		dontInclude.add("InvalidCritterException.java");
		dontInclude.add("Critter.java");
		dontInclude.add("Params.java");
		dontInclude.add("Main.java");
		dontInclude.add("Header.java");
		dontInclude.add("CritterButtons.java");
		dontInclude.add("CountDown.java");

		//populate arraylist with options
		File[] files = current.listFiles();
		for (File f : files){
			//accounts for windows versus mac file paths
			String strippedName = f.toString().replace("\\", "/");
			strippedName = strippedName.replaceFirst("./src/assignment5/", "");

			if (!dontInclude.contains(strippedName)){
				strippedName = strippedName.replaceFirst(".java", "");
				l.add(strippedName);
			}
		}
		
		options = FXCollections.observableList(l);
		critterSelection = new ComboBox<String>(options);
		critterStatsSelection = new ComboBox<String>(options);
		//default both to algae
		critterSelection.setValue("Algae");
		critterStatsSelection.setValue("Algae");
	}
	
	/*
	 * Sets up start button
	 */
	public static Button startButton(Stage window){
		Button btn = new Button("CENTER");
		
		btn.setText("Start");
		btn.setLayoutX(326);
		btn.setLayoutY(100);
		btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.setMain(window);
            }
        });
		
		return btn;
	}
	
	/*
	 * Sets up make button
	 */
	public static void makeButton(){
		Button btn = new Button();
		btn.setText("Make Critter");
		btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                	for (int i = 0; i < Integer.parseInt(makeCount.getText()); i++){
                		Critter.makeCritter(critterSelection.getValue().toString());
                        errorMessageMake.setVisible(false);
                	}
				} catch (Exception e) {
                    errorMessageMake.setVisible(true);
				}
            }
        });
		makeButton = btn;
	}
	
	/*
	 * Sets up quit button
	 */
	public static void quitButton(){
		Button btn = new Button();
		btn.setText("Quit");
		btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
		quitButton = btn;
	}
	
	/*
	 * Sets up display stats button
	 */
	public static void displayStats() {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(b);
		PrintStream old = System.out;
		System.setOut(ps);
		String fullName = myPackage + "." + critterStatsSelection.getValue();
		try{
			List<Critter> crits = Critter.getInstances(critterStatsSelection.getValue());
			//use refelection to find correct method and execute for the given critter
			Class<?> cls = Class.forName(fullName);
			Class[] arg = new Class[1];
			arg[0] = List.class;
			Method method = cls.getMethod("runStats", arg);
			method.invoke(null, crits);
		} catch (Exception e){
			
		}
		
		System.out.flush();
		System.setOut(old);
		statsField.setText(b.toString());
	}
	
	/*
	 * Gets a node in the gridpane given its row and column in the pane
	 */
	public static StackPane getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
	    StackPane result = null;
	    ObservableList<Node> children = gridPane.getChildren();

	    for (Node node : children) {
	    	if (node instanceof StackPane){
	    		if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
		            result = (StackPane) node;
		            break;
		        }
	    	} 
	    }

	    return result;
	}
	
	/*
	 * Sets up animation speed slider
	 */
	public static void setUpSlider(){
		runSpeed = new Slider(1, 10, 1);
		runSpeed.setShowTickMarks(true);
		runSpeed.setShowTickLabels(true);
		runSpeed.setMajorTickUnit(1f);
		runSpeed.setBlockIncrement(0.1f);
	}
	
	/*
	 * Sets up play button
	 */
	public static void animationButton(){
		Image playImage = new Image("PlayButton.png");
		Image pauseImage = new Image("PauseButton.png");
		ImageView iv = new ImageView(playImage);
		iv.setFitHeight(10);
		iv.setFitWidth(10);
		ImageView iv2 = new ImageView(pauseImage);
		iv2.setFitHeight(10);
		iv2.setFitWidth(10);
		Button play = new Button("Play", iv);
		play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (!animationRunning){
            		//if the button was originally "PLAY"
            		animationRunning = true;
            		play.setGraphic(iv2);
            		play.setText("Pause");
            		animate();
            		disableAllItems();
            		} else {
            		//else the button was on "Pause"
            		animationRunning = false;
            		play.setGraphic(iv);
            		play.setText("Play");
            		enableAllItems();
            	}
            }
        });
        
		animationButton = play;
	}
	
	/*
	 * Disables all items in the items arraylist
	 */
	public static void disableAllItems(){
		for (Node b : items){
			b.disableProperty().set(true);
		}
	}
	
	/*
	 * Reenables all items in the items arraylist
	 */
	public static void enableAllItems(){
		for (Node b : items){
			b.disableProperty().set(false);
		}
	}
	
	/*
	 * Sets up timer to countdown task
	 */
	public static void animate(){
		t = new Timer(true);
        t.scheduleAtFixedRate(new CountDown(t), 0, (long) (1000 / runSpeed.getValue()));
	}
}
