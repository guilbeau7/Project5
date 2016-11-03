package assignment5;

import java.awt.Insets;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Main extends Application{

	static GridPane grid = new GridPane();
	static GridPane controller = new GridPane();
	Stage critterDisplay = new Stage();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Critter Controller");
		critterDisplay.setTitle("Critter World");
		grid.setGridLinesVisible(true);
		
		FlowPane flow = new FlowPane();
	    flow.setVgap(4);
	    flow.setHgap(4);
	    flow.setPrefWrapLength(170); // preferred width allows for two columns
	    flow.setStyle("-fx-background-color: DAE6F3;");
	    Circle c = new Circle(50);
	    c.setFill(javafx.scene.paint.Color.RED);
	    flow.getChildren().add(c);
	    controller.add(flow, 110, 0);
	    
		primaryStage.setScene(new Scene(controller, 500, 500));
		createWorld(grid);
		critterDisplay.setScene(new Scene(grid));
		critterDisplay.show();
		primaryStage.show();
	}
	
	/**
	 * Create the visual grid for the critter world using params given
	 * @param world
	 */
	public static void createWorld(GridPane world){
		for (int i = 0; i < Params.world_height; i++){
			for (int j = 0; j < Params.world_width; j++){
				StackPane spot = new StackPane();
				spot.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.BLACK, CornerRadii.EMPTY, null)));
				Rectangle s = new Rectangle(7.5, 7.5);
				s.setStroke(javafx.scene.paint.Color.BLACK);
				s.setStrokeWidth(0.5);
				s.setFill(javafx.scene.paint.Color.WHITE);
				spot.getChildren().add(s);
				world.add(spot, j, i);
			}
		}
	}

}

