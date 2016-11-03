package assignment5;

import java.awt.Insets;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Main extends Application{

	static GridPane grid = new GridPane();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Critter Controller");
		grid.setGridLinesVisible(true);
		
		FlowPane flow = new FlowPane();
	    flow.setVgap(4);
	    flow.setHgap(4);
	    flow.setPrefWrapLength(170); // preferred width allows for two columns
	    flow.setStyle("-fx-background-color: DAE6F3;");
	    Circle c = new Circle(50);
	    c.setFill(javafx.scene.paint.Color.RED);
	    flow.getChildren().add(c);
	    grid.add(flow, 0, 0);
	    
		primaryStage.setScene(new Scene(grid, 1200, 500));
		primaryStage.show();
	}

}

