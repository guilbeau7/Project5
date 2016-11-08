package assignment5;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{

	static AnchorPane root = new AnchorPane();
	static GridPane grid = new GridPane();
	static Stage Controller = new Stage();
	static Stage critterDisplay = new Stage();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Controller.setTitle("Critter Controller");
		critterDisplay.setTitle("Critter World");
	    setIntro(Controller);
		createWorld(grid);
		root.getChildren().add(grid);
		critterDisplay.setScene(new Scene(root, 600, 600));
		critterDisplay.sizeToScene();
		Controller.setAlwaysOnTop(true);
		Controller.show();
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
		window.setResizable(false);
		iv.fitWidthProperty().bind(window.widthProperty());
		iv.fitHeightProperty().bind(window.heightProperty());
	}
	
	/**
	 * Create the visual grid for the critter world using params given
	 * @param world
	 */
	public static void createWorld(GridPane world){
		//find out which parameter is smaller: height or width
	    NumberBinding rectsAreaSize = Bindings.min(root.heightProperty(), root.widthProperty());

	    
		for (int i = 0; i < Params.world_width; i++){
			for (int j = 0; j < Params.world_height; j++){
				StackPane spot = new StackPane();
				spot.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
				Rectangle s = new Rectangle();
				s.setStroke(javafx.scene.paint.Color.BLACK);
				s.strokeWidthProperty().bind(s.heightProperty().multiply(0.009));;
				s.setFill(javafx.scene.paint.Color.WHITE);
				
				//position rectangles
	            s.xProperty().bind(rectsAreaSize.multiply(i).divide(Params.world_width + 1));
	            s.yProperty().bind(rectsAreaSize.multiply(j).divide(Params.world_height + 1));

	            //bind rectangle size to pane size
	            if (Params.world_width > Params.world_height){
	            	s.widthProperty().bind(rectsAreaSize.divide(Params.world_width + 1));
		            s.heightProperty().bind(s.widthProperty());
	            } else {
	            	s.heightProperty().bind(rectsAreaSize.divide(Params.world_height + 1));
	            	s.widthProperty().bind(s.heightProperty());
	            }
	            
				spot.getChildren().add(s);
				world.add(spot, i, j);
			}
		}
	}
	
	public static void setMain(Stage window){
		CritterButtons.stepCount.setPromptText("Enter Number of Steps");
		CritterButtons.seedCount.setPromptText("Enter Seed Number");
		CritterButtons.makeCount.setPromptText("Enter Number of Critters to Make");
		
		CritterButtons.setUpButtons();
		
		BorderPane border = new BorderPane();
		StackPane top = new StackPane();
		border.setTop(top);
		Label title = new Label("Control Panel");
		title.setFont(new Font("Algerian", 32));
		top.getChildren().add(title);
		top.setAlignment(Pos.CENTER);
		
		VBox vbox = new VBox();
		border.setCenter(vbox);
		
		VBox seedSection = new VBox(10);
		Label seedTitle = new Label("Set Seed Number:");
		seedTitle.setFont(new Font(18));
		seedSection.getChildren().add(seedTitle);
		seedSection.setPadding(new Insets(10,20,10,20));
		HBox seed = new HBox(10);
		seed.getChildren().add(CritterButtons.seedCount);
		seed.getChildren().add(CritterButtons.seedButton);
		seedSection.getChildren().add(seed);
		vbox.getChildren().add(seedSection);
		
		VBox stepSection = new VBox(10);
		Label stepTitle = new Label("Set Step Number:");
		stepTitle.setFont(new Font(18));
		stepSection.getChildren().add(stepTitle);
		stepSection.setPadding(new Insets(10,20,10,20));
		HBox step = new HBox(10);
		step.getChildren().add(CritterButtons.stepCount);
		step.getChildren().add(CritterButtons.stepButton);
		stepSection.getChildren().add(step);
		vbox.getChildren().add(stepSection);
		
		VBox makeSection = new VBox(10);
		Label makeTitle = new Label("Set Make Number:");
		makeTitle.setFont(new Font(18));
		makeSection.getChildren().add(makeTitle);
		makeSection.setPadding(new Insets(10,20,10,20));
		HBox make = new HBox(10);
		make.getChildren().add(CritterButtons.makeCount);
		make.getChildren().add(CritterButtons.critterSelection);
		make.getChildren().add(CritterButtons.makeButton);
		makeSection.getChildren().add(make);
		vbox.getChildren().add(makeSection);
		
		HBox stats = new HBox(10);
		Label instructions = new Label("Choose a critter to observe:");
		instructions.setFont(new Font(18));
		stats.getChildren().add(instructions);
		stats.getChildren().add(CritterButtons.critterStatsSelection);
		stats.setPadding(new Insets(10,20,10,20));
		vbox.getChildren().add(stats);
		
		VBox statField = new VBox();
		Label st = new Label("Stats:");
		statField.getChildren().add(st);
		CritterButtons.statsField.setWrapText(true);
		CritterButtons.statsField.setText("Stats Displayed Here");
		statField.getChildren().add(CritterButtons.statsField);
		statField.setPadding(new Insets(10,20,25,20));
		vbox.getChildren().add(statField);
		
		VBox animationSection = new VBox();
		Label aTitle = new Label("Set Animation:");
		aTitle.setFont(new Font(18));
		aTitle.setAlignment(Pos.CENTER);
		animationSection.getChildren().add(aTitle);
		animationSection.setPadding(new Insets(10,20,10,20));
		HBox speed = new HBox(30);
		Label setSpeed = new Label("Set Speed:");
		speed.getChildren().add(setSpeed);
		speed.getChildren().add(CritterButtons.runSpeed);
		speed.setPadding(new Insets(10,20,10,20));
		speed.setAlignment(Pos.CENTER);
		animationSection.getChildren().add(speed);
		
		
		HBox run = new HBox(10);
		run.getChildren().add(CritterButtons.animationButton);
		run.setPadding(new Insets(10,20,10,20));
		run.setAlignment(Pos.CENTER);
		animationSection.getChildren().add(run);
		animationSection.setAlignment(Pos.CENTER);
		vbox.getChildren().add(animationSection);
		
		HBox quit = new HBox(10);
		quit.getChildren().add(CritterButtons.quitButton);
		quit.setPadding(new Insets(10,20,10,20));
		quit.setAlignment(Pos.CENTER);
		vbox.getChildren().add(quit);
		
		
		Scene s = new Scene(border,500,590);
		window.setScene(s);
		
		/*
		VBox vbox = new VBox();
		vbox.getChildren().add(new Text("Control Panel"));
		vbox.getChildren().add(CritterButtons.seedCount);
		vbox.getChildren().add(CritterButtons.seedButton);
		vbox.getChildren().add(CritterButtons.stepCount);
		vbox.getChildren().add(CritterButtons.stepButton);
		vbox.getChildren().add(CritterButtons.critterSelection);
		vbox.getChildren().add(CritterButtons.makeCount);
		vbox.getChildren().add(CritterButtons.makeButton);
		vbox.getChildren().add(CritterButtons.critterStatsSelection);
		vbox.getChildren().add(CritterButtons.statsField);
		vbox.getChildren().add(CritterButtons.runSpeed);
		vbox.getChildren().add(CritterButtons.animationButton);
		vbox.getChildren().add(CritterButtons.quitButton);
		Scene s = new Scene(vbox, 500, 500);
		window.setScene(s);
		*/
	}
}

