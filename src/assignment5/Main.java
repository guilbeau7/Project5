package assignment5;

import javafx.geometry.HPos;
import javafx.geometry.Insets;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
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

public class Main extends Application{

	static AnchorPane root = new AnchorPane();
	static GridPane grid = new GridPane();
	static Stage critterDisplay = new Stage();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Critter Controller");
		critterDisplay.setTitle("Critter World");
	    setIntro(primaryStage);
		createWorld(grid);
		root.getChildren().add(grid);
		critterDisplay.setScene(new Scene(root, 600, 600));
		primaryStage.show();
		critterDisplay.show();
	}
	
	public static void setIntro(Stage window){
		Image image = new Image("CritterIntro.png");
		ImageView iv = new ImageView();
		iv.setImage(image);
		iv.setSmooth(true);
		
		Group root = new Group();
		root.getChildren().add(iv);
		root.getChildren().add(CritterButtons.startButton(window));
		
		Scene s = new Scene(root, 500, 500);
		window.setScene(s);
		iv.fitWidthProperty().bind(window.widthProperty());
		iv.fitHeightProperty().bind(window.heightProperty());
	}
	
	/**
	 * Create the visual grid for the critter world using params given
	 * @param world
	 */
	public static void createWorld(GridPane world){
		// this binding will find out which parameter is smaller: height or width
	    NumberBinding rectsAreaSize = Bindings.min(root.heightProperty(), root.widthProperty());
	    
		for (int i = 0; i < Params.world_width; i++){
			for (int j = 0; j < Params.world_height; j++){
				StackPane spot = new StackPane();
				spot.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
				Rectangle s = new Rectangle();
				s.setStroke(javafx.scene.paint.Color.BLACK);
				s.strokeWidthProperty().bind(s.heightProperty().multiply(0.009));;
				s.setFill(javafx.scene.paint.Color.WHITE);
				
				//position rects (this depends on pane size as well)
	            s.xProperty().bind(rectsAreaSize.multiply(i).divide(Params.world_width + 1));
	            s.yProperty().bind(rectsAreaSize.multiply(j).divide(Params.world_height + 1));

	            //bind rectangle size to pane size 
	            s.heightProperty().bind(rectsAreaSize.divide(Params.world_width + 1));
	            s.widthProperty().bind(s.heightProperty());
				spot.getChildren().add(s);
				world.add(spot, i, j);
			}
		}
	}
	
	public static void setMain(Stage window){
		CritterButtons.stepCount.setPromptText("Enter Number of Steps");
		CritterButtons.seedCount.setPromptText("Enter Seed Number");
		CritterButtons.makeCount.setPromptText("Enter Number of Critters to Make");
		
		VBox vbox = new VBox();
		vbox.getChildren().add(new Text("Control Panel"));
		vbox.getChildren().add(CritterButtons.seedCount);
		vbox.getChildren().add(CritterButtons.seedButton());
		vbox.getChildren().add(CritterButtons.stepCount);
		vbox.getChildren().add(CritterButtons.stepButton());
		CritterButtons.createClassDropDown();
		vbox.getChildren().add(CritterButtons.critterSelection);
		vbox.getChildren().add(CritterButtons.makeCount);
		vbox.getChildren().add(CritterButtons.createCritterButton());
		vbox.getChildren().add(CritterButtons.critterStatsSelection);
		vbox.getChildren().add(CritterButtons.statsField);
		CritterButtons.statsField.setDisable(true);
		vbox.getChildren().add(CritterButtons.quitButton());
		Scene s = new Scene(vbox, 500, 500);
		window.setScene(s);
	}

	
}

