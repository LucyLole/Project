
import Model.SongsView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import Model.DatabaseConnection;
import Model.*;
import static javafx.scene.layout.Priority.ALWAYS;

import java.util.ArrayList;
import java.util.Arrays;


public class Main extends Application {
    private int currentSong; //keeps track of the current selected song, based on the songID
    private int selectedSongID;
     //DatabaseConnection.DatabaseConnection("../MusicPlayerDatabase.db");
    private static Controller controller;
    private static DatabaseConnection database;

    private static TableView<SongsView> songsTable = new TableView<>();

    private void playWindow() {
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
        controller = new Controller(songsTable);
        selectedSongID = 0;
        database = new DatabaseConnection("MusicPlayerDatabase.db");

        //we stop the window from being resized as it would mess up the layout of elements
        libraryStage.setResizable(false);
        //we create a VBox called root, so we can stack the menu bar above the boarder pane!
        VBox root = new VBox();

        //borderRoot is our borderpane
        BorderPane borderRoot = new BorderPane();


        //we set the title of the window
        libraryStage.setTitle("Music Player");

        //we create a new scene where our borderpane is the main pane
        Scene mainScene = new Scene(root, 750, 700);


        //creating padding for my top elements
        Insets topPadding = new Insets(10,10,10,10);
        //we create a HBox for the top called topSection
        HBox topSection = new HBox(10);
        //setting the space between top elements
        HBox.setHgrow(topSection, ALWAYS);
        //creating the HBox to hold the search bar elements
        HBox rightTopSection = new HBox(10);
        //giving extra space to right section
        HBox.setHgrow(rightTopSection,ALWAYS);
        //giving my top elements thier padding values
        topSection.setPadding(topPadding);



        /*Table View Stuff*/
        songsTable.setPrefSize(665,590);
        Pane centerPane = new Pane();
        borderRoot.setCenter(centerPane);
        centerPane.getChildren().add(songsTable);

        //song number column
        TableColumn<SongsView, String> songsNumberColumn = new TableColumn<>("#");
        songsNumberColumn.setCellValueFactory(new PropertyValueFactory<>("SongID"));
        //setting the size of the column
        songsNumberColumn.prefWidthProperty().setValue(40);
        //adding name column to the tableview
        songsTable.getColumns().add(songsNumberColumn);


        //creating the name column
        TableColumn<SongsView, String> songsNameColumn = new TableColumn<>("Name");
        songsNameColumn.setCellValueFactory(new PropertyValueFactory<>("SongName"));
        //setting the size of the column
        songsNameColumn.prefWidthProperty().setValue(150);
        //adding number column to the tableview
        songsTable.getColumns().add(songsNameColumn);

        //creating the artist column
        TableColumn<SongsView, String> songsArtistColumn = new TableColumn<>("Artist");
        songsArtistColumn.setCellValueFactory(new PropertyValueFactory<>("ArtistName"));
        //setting the size of the column
        songsArtistColumn.prefWidthProperty().setValue(110);
        //adding artist column to the tableview
        songsTable.getColumns().add(songsArtistColumn);

        //creating the album column
        TableColumn<SongsView, String> songsAlbumColumn = new TableColumn<>("Album");
        songsAlbumColumn.setCellValueFactory(new PropertyValueFactory<>("AlbumName"));
        //setting the size of the column
        songsAlbumColumn.prefWidthProperty().setValue(110);
        //adding artist column to the tableview
        songsTable.getColumns().add(songsAlbumColumn);

        //creating the genre column
        TableColumn<SongsView, String> songsGenreColumn = new TableColumn<>("Genre");
        songsGenreColumn.setCellValueFactory(new PropertyValueFactory<>("SongGenre"));
        //setting the size of the column
        songsGenreColumn.prefWidthProperty().setValue(90);
        //adding artist column to the tableview
        songsTable.getColumns().add(songsGenreColumn);

        //creating the year column
        TableColumn<SongsView, String> songsYearColumn = new TableColumn<>("Year");
        songsYearColumn.setCellValueFactory(new PropertyValueFactory<>("SongYear"));
        //setting the size of the column
        songsYearColumn.prefWidthProperty().setValue(90);
        //adding artist column to the tableview
        songsTable.getColumns().add(songsYearColumn);

        //creating the length column
        TableColumn<SongsView, String> songsLengthColumn = new TableColumn<>("Length");
        songsLengthColumn.setCellValueFactory(new PropertyValueFactory<>("SongLength"));
        //setting the size of the column
        songsLengthColumn.prefWidthProperty().setValue(90);
        //adding artist column to the tableview
        songsTable.getColumns().add(songsLengthColumn);


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
        leftSection.setPrefWidth(200);
        //leftSection.setAlignment(Pos.BOTTOM_LEFT);
        //creating the vbox to hold the add, delete and edit buttons
        VBox ADEbuttons = new VBox(10);
        //ADEbuttons.setPadding(topPadding);

        /*left Section Buttons*/
        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button playButton = new Button("Play");
        playButton.setOnAction((ActionEvent ae) -> controller.updateTable(selectedSongID));

        //adding the buttons to the VBox
        VBox.setVgrow(ADEbuttons,ALWAYS);
        ADEbuttons.getChildren().addAll(addButton,editButton,deleteButton);
        leftSection.getChildren().addAll(ADEbuttons,playButton);
        leftSection.setPrefHeight(mainScene.getHeight() -100);

        /*Alignment and sizing for the buttons*/
        double leftWidth = 55;
        //applying the style and sizing to all buttons on the main screen
        for (Button b : Arrays.asList(addButton, deleteButton, searchButton, audioButton, videoButton,
            editButton, playButton)) {
            b.getStyleClass().add("library-buttons");
            b.setPrefWidth(leftWidth);
        }
        playButton.setPrefHeight(55);
        leftSection.setPadding(topPadding);
        leftSection.setMaxWidth(75);

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


        //This is testing adding albums

        Album TestAlb = new Album(8,8,"TestAlb",1999,"Classical",
                "/artwork/test.png");
        AlbumService.save(TestAlb, database);



        Songs TestSong = new Songs(0,8,8,"../songs/test.mp3","TestSong",7.13f,"Classical");
        SongsService.save(TestSong, database);



        Artist TestArtist = new Artist(8, "TestArtist");
        ArtistService.save(TestArtist, database);


        ArrayList<Album> testlist = new ArrayList<>();
        AlbumService.selectAll(testlist,database);

        Album test2 = AlbumService.selectById(2
                ,database);
        System.out.println(test2.toString());
        /*
        for (Album a: testlist) {
            System.out.println(a);
        }
        */
    }


    public static void main(String[] args) {
        launch(args);
    }
}

