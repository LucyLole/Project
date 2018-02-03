
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
import static javafx.scene.layout.Priority.ALWAYS;
import java.io.IOException;



public class Main extends Application {
    private int currentSong; //keeps track of the current selected song, based on the songID
     //DatabaseConnection.DatabaseConnection("../MusicPlayerDatabase.db");

    public void playWindow() {
        Stage playbackWindow = new Stage();
        playbackWindow.setTitle("Playback Window");
        VBox playbackRoot = new VBox();
        Scene playbackScene = new Scene(playbackRoot,650,600);

        ImageView albumArt = new ImageView(/* This needs to be retreiving albumart filepath from DB using current song as ID*/);

        playbackWindow.setScene(playbackScene);
        playbackWindow.show();
    }

    @Override
    public void start(Stage libraryStage) throws Exception {
        DatabaseConnection Database = Controller.ConnectToDB();

        //we stop the window from being resized as it would mess up the layout of elements
        libraryStage.setResizable(false);
        //we create a VBox called root, so we can stack the menu bar above the boarder pane!
        VBox root = new VBox();

        //borderRoot is our borderpane
        BorderPane borderRoot = new BorderPane();


        //we set the title of the window
        libraryStage.setTitle("Music Player");

        //we create a new scene where our boarderpane is the main pane
        Scene mainScene = new Scene(root, 750, 700);


        //creating padding for my top elements
        Insets topPadding = new Insets(10,10,10,10);
        //we create a HBox for the top called topSection
        HBox topSection = new HBox(10);
        //setting the space between top elements
        HBox.setHgrow(topSection, ALWAYS);
        //creating the hbox to hold the search bar elements
        HBox rightTopSection = new HBox(10);
        //giving extra space to right section
        HBox.setHgrow(rightTopSection,ALWAYS);
        //giving my top elements thier padding values
        topSection.setPadding(topPadding);


        //we add the stylesheet to the scene
        mainScene.getStylesheets().add("stylesheet.css");
        //we create a new menuBar called topMenu and add its options
        MenuBar topMenu = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem fileItem1 = new MenuItem("Open");
        fileMenu.getItems().addAll(fileItem1);
        topMenu.getMenus().addAll(fileMenu);
        //we add the menu bar to topSection
        root.getChildren().add(topMenu);

        /*Text Field*/
        //we create the text field element for our search bar
        TextField SearchField = new TextField();

        /*buttons*/
        //adding our top section buttons
        Button audioButton = new Button("Audio");
        Button videoButton = new Button("Video");
        Button searchButton =  new Button("Search");
        //giving the audio button its event to open the play window
        audioButton.setOnAction((ActionEvent ae) -> playWindow());

        /*Left Section*/
        //creating the left vbox
        VBox leftSection = new VBox(10);
        leftSection.setAlignment(Pos.TOP_CENTER);
        //creating the vbox to hold the add, delete and edit buttons
        VBox ADEbuttons = new VBox(10);

        /*left Section Buttons*/
        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button playButton = new Button("Play");

        //adding the buttons to the
        VBox.setVgrow(ADEbuttons,ALWAYS);
        ADEbuttons.getChildren().addAll(addButton,editButton,deleteButton);
        leftSection.getChildren().addAll(ADEbuttons,playButton);
        leftSection.setPrefHeight(mainScene.getHeight() -100);

        /*Alignment for the top section buttons*/
        playButton.setAlignment(Pos.BOTTOM_CENTER);
        topSection.setAlignment(Pos.TOP_CENTER);
        audioButton.setAlignment(Pos.CENTER_RIGHT);
        videoButton.setAlignment(Pos.CENTER_RIGHT);

        //adding to the top boxes
        topSection.getChildren().add(searchButton);
        topSection.getChildren().add(SearchField);
        rightTopSection.getChildren().add(audioButton);
        rightTopSection.getChildren().add(videoButton);
        topSection.getChildren().add(rightTopSection);
        rightTopSection.setAlignment(Pos.TOP_RIGHT);
        //adding to the root
        root.getChildren().add(borderRoot);
        //setting the border pane sections
        borderRoot.setTop(topSection);
        borderRoot.setLeft(leftSection);

        /*Initialising*/

        //settintg the stage to the current scene and showing
        libraryStage.setScene(mainScene);
        libraryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

