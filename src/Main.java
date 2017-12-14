import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import Model.DatabaseConnection;

import java.io.IOException;

import static javafx.scene.layout.Priority.ALWAYS;

public class Main extends Application {
    public static DatabaseConnection Database;
    //DatabaseConnection.DatabaseConnection("../MusicPlayerDatabase.db");

    public void playWindow() {
        Stage playbackWindow = new Stage();
        playbackWindow.setTitle("Playback Window");
        VBox playbackRoot = new VBox();
        Scene playbackScene = new Scene(playbackRoot,650,600);

        ImageView albumArt = new ImageView(currentSong.getArtwork);


        playbackWindow.setScene(playbackScene);
        playbackWindow.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Database = new DatabaseConnection("../MusicPlayerDataBase.db");

        //we create a VBox called root, so we can stack the menu bar above the boarder pane!
        VBox root = new VBox();

        //borderRoot is our borderpane
        BorderPane borderRoot = new BorderPane();


        //we set the title of the window
        primaryStage.setTitle("Music Player");

        //we create a new scene where our boarderpane is the main pane
        Scene mainScene = new Scene(root, 750, 700);

        Insets topPadding = new Insets(10,10,10,10);
        //we create a HBox for the top called topSection
        HBox topSection = new HBox(10);
        HBox.setHgrow(topSection, ALWAYS);
        HBox rightTopSection = new HBox(10);
        HBox.setHgrow(rightTopSection,ALWAYS);
        topSection.setPadding(topPadding);
        StackPane leftStackPane = new StackPane();

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

        /*Text Field*/
        TextField SearchField = new TextField();

        /*buttons*/
        Button audioButton = new Button("Audio");
        //audioButton.getStyleClass().add("top_button");
        Button videoButton = new Button("Video");
        //videoButton.getStyleClass().add("top_button");
        Button searchButton =  new Button("Search");

        audioButton.setOnAction((ActionEvent ae) -> playWindow());

        /*Left Stuff*/
        VBox leftSection = new VBox(10);
        leftSection.setAlignment(Pos.TOP_CENTER);
        VBox botLeftSection = new VBox(10);
        VBox.setVgrow(botLeftSection,ALWAYS);
        botLeftSection.setAlignment(Pos.BOTTOM_CENTER);

        /*leftButtons*/
        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        //leftSection.getChildren().addAll(addButton,editButton,deleteButton);

        Button playButton = new Button("Play");

        //adding the buttons to the top vbox
        //botLeftSection.getChildren().addAll(playButton);
        VBox.setVgrow(leftSection,ALWAYS);
        leftStackPane.getChildren().addAll(addButton,editButton,deleteButton,playButton);
        leftStackPane.maxHeight(leftStackPane.getMaxHeight());
        leftStackPane.setAlignment(playButton,Pos.BOTTOM_CENTER);



        leftSection.getChildren().add(botLeftSection);

        /*Alignment for the top section buttons*/
        playButton.setAlignment(Pos.BOTTOM_CENTER);
        topSection.setAlignment(Pos.TOP_CENTER);
        audioButton.setAlignment(Pos.CENTER_RIGHT);
        videoButton.setAlignment(Pos.CENTER_RIGHT);
        //adding to the scene
        topSection.getChildren().add(searchButton);
        topSection.getChildren().add(SearchField);
        rightTopSection.getChildren().add(audioButton);
        rightTopSection.getChildren().add(videoButton);

        topSection.getChildren().add(rightTopSection);
        rightTopSection.setAlignment(Pos.TOP_RIGHT);
        root.getChildren().add(borderRoot);

        borderRoot.setTop(topSection);
        borderRoot.setLeft(leftStackPane);
        //initialising
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

