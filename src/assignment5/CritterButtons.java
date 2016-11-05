package assignment5;

import javafx.geometry.HPos;
import javafx.geometry.Insets;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CritterButtons {
	static ComboBox<String> critterSelection;
	static ComboBox<String> critterStatsSelection;
	static TextField stepCount = new TextField();
	static TextField seedCount = new TextField();
	static TextField makeCount = new TextField();
	static TextField statsField = new TextField();
	
	public static Button stepButton(){
		Button btn = new Button();
		btn.setText("Step");
		btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	try{
            		for (int i = 0; i < Integer.parseInt(stepCount.getText()); i++){
                		Critter.worldTimeStep();
                        Critter.displayWorld();
                	}
                    //displayStats();
            	} catch(NumberFormatException e){
            		//Create new textField for an error message
            	}
            }
        });
		return btn;
	}
	
	
	public static Button seedButton(){
		Button btn = new Button();
		btn.setText("Set Seed");
		btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	try{
                	Critter.setSeed(Integer.parseInt(seedCount.getText()));
            	} catch(NumberFormatException e){
            		//Create new textField for an error message
            	}
            }
        });
		return btn;
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
		
		//populate arraylist with options
		File[] files = current.listFiles();
		for (File f : files){
			String strippedName = f.toString().replaceFirst("./src/assignment5/", "");
			if (!dontInclude.contains(strippedName)){
				strippedName = strippedName.replaceFirst(".java", "");
				l.add(strippedName);
			}
		}
		
		options = FXCollections.observableList(l);
		
		critterSelection = new ComboBox<String>(options);
		critterStatsSelection = new ComboBox<String>(options);
		critterSelection.setValue("Algae");
		critterStatsSelection.setValue("Algae");
	}
	
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
	
	public static Button createCritterButton(){
		Button btn = new Button();
		btn.setText("Make Critter");
		btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                	for (int i = 0; i < Integer.parseInt(makeCount.getText()); i++){
                		Critter.makeCritter(critterSelection.getValue().toString());
                	}
				} catch (InvalidCritterException e) {
					e.printStackTrace();
				}
            }
        });
		return btn;
	}
	
	public static Button quitButton(){
		Button btn = new Button();
		btn.setText("Quit");
		btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
		return btn;
	}
	
	public static void displayStats(){
		try{
			Scanner s = new Scanner(System.in);
			List<Critter> crits = Critter.getInstances(critterStatsSelection.getValue());
			Critter.runStats(crits);
			while(s.hasNext()){
				statsField.setText(s.nextLine());
			}
			s.close();
		} catch(Exception e){
			//Handle Exception with textMessage
		}
	}
	
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

}
