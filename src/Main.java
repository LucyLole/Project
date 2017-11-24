import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import Model.DatabaseConnection;

import static javafx.scene.layout.Priority.ALWAYS;

public class Main extends Application {
    public static DatabaseConnection Database;
    //DatabaseConnection.DatabaseConnection("../MusicPlayerDatabase.db");

    @Override
    public void start(Stage primaryStage) throws Exception{
        Database = new DatabaseConnection("../MusicPlayerDataBase.db");

        //borderRoot is our borderpane
        BorderPane borderRoot = new BorderPane();

        //we create a VBox called root, so we can stack the menu bar above the boarder pane!
        VBox root = new VBox();

        //we set the title of the window
        primaryStage.setTitle("Music Player");

        //we create a new scene where our boarderpane is the main pane
        Scene mainScene = new Scene(root, 750, 700);

        /* top box things */

        //we create a HBox for the top called topSection
        HBox topSection = new HBox(10);
        topSection.setHgrow(topSection, ALWAYS);
        HBox rightTopSection = new HBox(10);
        rightTopSection.setHgrow(rightTopSection,ALWAYS);

        //we set topSection to be the top in our boarderPane

        //we add the stylesheet to the scene
        mainScene.getStylesheets().add("stylesheet.css");
        //we create a new menuBar called topMenu
        MenuBar topMenu = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem fileItem1 = new MenuItem("Open");
        fileMenu.getItems().addAll(fileItem1);
        topMenu.getMenus().addAll(fileMenu);
        //we add the menu bar to topSection
        root.getChildren().add(topMenu);

        //buttons
        Button audioButton = new Button("Audio");
        //audioButton.getStyleClass().add("top_button");
        Button videoButton = new Button("Video");
        //videoButton.getStyleClass().add("top_button");

        Button searchButton =  new Button("Search");

        /*Alignment for the top section buttons*/
        topSection.setAlignment(Pos.TOP_CENTER);
        audioButton.setAlignment(Pos.CENTER_RIGHT);
        videoButton.setAlignment(Pos.CENTER_RIGHT);
        //adding to the scene
        topSection.getChildren().add(searchButton);
        rightTopSection.getChildren().add(audioButton);
        rightTopSection.getChildren().add(videoButton);

        topSection.getChildren().add(rightTopSection);
        rightTopSection.setAlignment(Pos.TOP_RIGHT);
        root.getChildren().add(borderRoot);
        borderRoot.setTop(topSection);
        //initialising
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

