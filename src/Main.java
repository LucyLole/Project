
import Model.SongsView;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import Model.DatabaseConnection;
import Model.*;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.util.StringConverter;

import static javafx.scene.layout.Priority.ALWAYS;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;


public class Main extends Application {
    private int currentSong; //keeps track of the current selected song, based on the songID

    private boolean playWindowShowing = false;

    private static Controller controller;
    private Stage playbackWindow = new Stage();
    public static DatabaseConnection database;

    public static MediaPlayer songPlayer = null;
    public SongsView selectedSong;


    private static TableView<SongsView> songsTable = new TableView<>();

    public void playSong() {
        selectedSong = songsTable.getSelectionModel().getSelectedItem();
        if (selectedSong != null) {
            File songFile = new File(SongsService.getFilePath(selectedSong, database));
            if (songFile.isFile()) {
                Media songMedia = new Media(songFile.toURI().toString());
                songPlayer = new MediaPlayer(songMedia);
                songPlayer.setVolume(0.50);
                songPlayer.play();
            } else {
                System.out.println("File error.");
            }
        }
    }

    @Override
    public void start(Stage libraryStage) throws IOException {
        database = new DatabaseConnection("MusicPlayerDatabase.db");

        controller = new Controller(songsTable, libraryStage);





        //Loading our images
        ImageView playIcon = new ImageView(new Image(getClass().getResourceAsStream("/Images/playIcon.png")));
        ImageView pauseIcon = new ImageView(new Image(getClass().getResourceAsStream("/Images/pauseIcon.png")));
        ImageView forwardsIcon = new ImageView(new Image(getClass().getResourceAsStream("/Images/forwardIcon.png")));
        ImageView backwardsIcon = new ImageView(new Image(getClass().getResourceAsStream("/Images/backwardsIcon.png")));
        for (ImageView i : Arrays.asList(playIcon,pauseIcon,forwardsIcon,backwardsIcon)) {
            i.setFitHeight(45);
            i.setFitWidth(45);
        }


        //we stop the window from being resized as it would mess up the layout of elements
        libraryStage.setResizable(false);
        libraryStage.getIcons().add(new Image("/Images/playIcon.png"));
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





        //Table View Stuff
        songsTable.setPrefSize(665,590);
        Pane centerPane = new Pane();
        borderRoot.setCenter(centerPane);
        centerPane.getChildren().add(songsTable);

        /*
        //song number column/*
        TableColumn<SongsView, String> songsNumberColumn = new TableColumn<>("#");
        songsNumberColumn.setCellValueFactory(new PropertyValueFactory<>("SongID"));
        songsNumberColumn.setResizable(false);
        //setting the size of the column
        songsNumberColumn.prefWidthProperty().setValue(40);
        //adding name column to the tableview
        songsTable.getColumns().add(songsNumberColumn);
        */

        //creating the name column
        TableColumn<SongsView, String> songsNameColumn = new TableColumn<>("Name");
        songsNameColumn.setCellValueFactory(new PropertyValueFactory<>("SongName"));
        songsNameColumn.setResizable(false);
        //setting the size of the column
        songsNameColumn.prefWidthProperty().setValue(150);
        //adding number column to the tableview
        songsTable.getColumns().add(songsNameColumn);

        //creating the artist column
        TableColumn<SongsView, String> songsArtistColumn = new TableColumn<>("Artist");
        songsArtistColumn.setCellValueFactory(new PropertyValueFactory<>("ArtistName"));
        songsArtistColumn.setResizable(false);
        //setting the size of the column
        songsArtistColumn.prefWidthProperty().setValue(110);
        //adding artist column to the tableview
        songsTable.getColumns().add(songsArtistColumn);

        //creating the album column
        TableColumn<SongsView, String> songsAlbumColumn = new TableColumn<>("Album");
        songsAlbumColumn.setCellValueFactory(new PropertyValueFactory<>("AlbumName"));
        songsAlbumColumn.setResizable(false);
        //setting the size of the column
        songsAlbumColumn.prefWidthProperty().setValue(110);
        //adding artist column to the tableview
        songsTable.getColumns().add(songsAlbumColumn);

        //creating the genre column
        TableColumn<SongsView, String> songsGenreColumn = new TableColumn<>("Genre");
        songsGenreColumn.setCellValueFactory(new PropertyValueFactory<>("SongGenre"));
        songsGenreColumn.setResizable(false);
        //setting the size of the column
        songsGenreColumn.prefWidthProperty().setValue(90);
        //adding artist column to the tableview
        songsTable.getColumns().add(songsGenreColumn);

        //creating the year column
        TableColumn<SongsView, String> songsYearColumn = new TableColumn<>("Year");
        songsYearColumn.setCellValueFactory(new PropertyValueFactory<>("SongYear"));
        songsYearColumn.setResizable(false);
        //setting the size of the column
        songsYearColumn.prefWidthProperty().setValue(90);
        //adding artist column to the tableview
        songsTable.getColumns().add(songsYearColumn);

        //creating the length column
        TableColumn<SongsView, String> songsLengthColumn = new TableColumn<>("Length");
        songsLengthColumn.setCellValueFactory(new PropertyValueFactory<>("SongLength"));
        songsLengthColumn.setResizable(false);;
        //setting the size of the column
        songsLengthColumn.prefWidthProperty().setValue(90);
        //adding artist column to the tableview
        songsTable.getColumns().add(songsLengthColumn);

        songsTable.getSortOrder().addAll(songsTable.getColumns());






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
        TextField searchField = new TextField();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            controller.updateTable(0,newValue);
                });


        /*buttons*/
        //adding our top section buttons
        Button audioButton = new Button("Audio");
        Button videoButton = new Button("Video");
        Button searchButton =  new Button("Search");
        //giving the audio button its event to open the play window

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
        Button playButton = new Button();
        playButton.setGraphic(playIcon);
        deleteButton.setOnAction((ActionEvent ae) -> controller.onDeltePressed());

        audioButton.setOnAction((ActionEvent ae) -> controller.updateTable(0,searchField.getText()));

        playButton.setOnAction((ActionEvent ae) -> {

            playSong();
            playbackWindow.close();
            playWindow();
        });

        addButton.setOnAction((ActionEvent ae) -> {
            try {
                controller.addButtonPressed();
            } catch (Exception io) {
                System.out.println("File open error :" + io.getMessage());
            }
                });

        searchButton.setDisable(true);

        editButton.setOnAction((ActionEvent ae) -> {
            SongsView selection = songsTable.getSelectionModel().getSelectedItem();
            if (selection != null) {
                editWindow(selection);
            }

        });

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
        topSection.getChildren().add(searchField);
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

    public void editWindow(SongsView selectedSong) {
        Stage editWindow = new Stage();
        GridPane root = new GridPane();
        VBox editBox = new VBox(10);
        Scene editScene = new Scene(editBox,450,400);


        editScene.getStylesheets().add("stylesheet.css");

        String startSongName = selectedSong.getSongName();
        String startArtistName = selectedSong.getArtistName();
        String startAlbumName = selectedSong.getAlbumName();
        String startGenre = selectedSong.getSongGenre();
        int startSongYear = selectedSong.getSongYear();

        Button acceptButton = new Button ("Accept");
        acceptButton.getStyleClass().add("library-buttons");


        Label nameLabel = new Label("Name :");
        Label artistLabel = new Label("Artist :");
        Label albumLabel = new Label("Album :");
        Label genreLabel = new Label("Genre :");
        Label yearLabel = new Label("Year :");

        TextField nameField = new TextField();
        nameField.textProperty().setValue(startSongName);
        TextField artistField = new TextField();
        artistField.textProperty().setValue(startArtistName);
        TextField albumField = new TextField();
        albumField.textProperty().setValue(startAlbumName);
        TextField genreField = new TextField();
        genreField.textProperty().setValue(startGenre);
        TextField yearField = new TextField();
        yearField.textProperty().setValue(String.valueOf(startSongYear));

        acceptButton.setOnAction((ActionEvent ae) -> {

            editWindow.hide();


            try {
                    Integer.parseInt(yearField.getText());
            } catch (NumberFormatException e) {
                Alert noSelected = new Alert(Alert.AlertType.ERROR,"Invalid Input");
                noSelected.setContentText("Year entered was not a number ");
                noSelected.showAndWait();
                return;
            }

            String SongName = startSongName;
            if (!startSongName.equals(nameField.getText())) {
                SongName = nameField.getText();
            }

            String ArtistName = startArtistName;
            if (!ArtistName.equals(artistField.getText())) {
                ArtistName = artistField.getText();
            }

            String AlbumName = startAlbumName;
            if (!startAlbumName.equals(albumField.getText())) {
                AlbumName = albumField.getText();
            }

            String SongGenre = startGenre;
            if (!startGenre.equals(genreField.getText())) {
                SongGenre = genreField.getText();
            }

            int SongYear = startSongYear;
            if (startSongYear != Integer.valueOf(yearField.getText())){
                SongYear = Integer.valueOf(yearField.getText());
            }

            int ArtistID = ArtistService.getArtistIdFromName(ArtistName,database);
            if (ArtistID == 0) {
                Artist newArtist = new Artist(0,ArtistName);
                ArtistService.save(newArtist,database);


            }

            int AlbumID = AlbumService.getAlbumIdFromName(AlbumName,database);
            if (AlbumID == 0) {
                Album newAlbum = new Album(0,ArtistID,AlbumName,SongYear,SongGenre,"");
                AlbumService.save(newAlbum,database);
                AlbumID = AlbumService.getAlbumIdFromName(AlbumName,database);
            }



            ArtistID = ArtistService.getArtistIdFromName(ArtistName,database);

            Songs songToSave = new Songs(0,ArtistID,AlbumID,SongsService.getFilePath(selectedSong,database),SongName,selectedSong.getSongLength(),SongGenre);
            Songs songToRemove = new Songs(selectedSong.getSongID(),ArtistID,AlbumID,SongsService.getFilePath(selectedSong,database),SongName,selectedSong.getSongLength(),SongGenre);
            SongsService.save(songToSave,database);
            SongsService.deleteById(songToRemove,database);
            controller.updateTable(selectedSong.getSongID(),"");

            editWindow.close();

        });

        GridPane.setConstraints(nameLabel,0,0);
        GridPane.setConstraints(artistLabel,0,1);
        GridPane.setConstraints(albumLabel,0,2);
        GridPane.setConstraints(genreLabel,0,3);
        GridPane.setConstraints(yearLabel,0,4);

        GridPane.setConstraints(nameField,1,0);
        GridPane.setConstraints(artistField,1,1);
        GridPane.setConstraints(albumField,1,2);
        GridPane.setConstraints(genreField,1,3);
        GridPane.setConstraints(yearField,1,4);

        editWindow.setTitle("Editing: " +startSongName);

        root.getChildren().addAll(nameLabel,nameField,artistField,artistLabel,
                genreField,genreLabel,yearField,yearLabel,albumField,albumLabel);
        root.setAlignment(Pos.CENTER);

        editBox.setAlignment(Pos.CENTER);

        editBox.getChildren().addAll(root,acceptButton);

        root.setHgap(10);
        root.setVgap(10);

        editWindow.setResizable(false);
        editWindow.setScene(editScene);
        editWindow.show();




    }





    public void playWindow() {

        final DecimalFormat df = new DecimalFormat("#.##");
        //Stage playbackWindow = new Stage();

        ImageView playIcon = new ImageView(new Image(getClass().getResourceAsStream("/Images/playIcon.png")));
        ImageView pauseIcon = new ImageView(new Image(getClass().getResourceAsStream("/Images/pauseIcon.png")));
        ImageView forwardsIcon = new ImageView(new Image(getClass().getResourceAsStream("/Images/forwardIcon.png")));
        ImageView backwardsIcon = new ImageView(new Image(getClass().getResourceAsStream("/Images/backwardsIcon.png")));
        for (ImageView i : Arrays.asList(playIcon, pauseIcon, forwardsIcon, backwardsIcon)) {
            i.setFitHeight(45);
            i.setFitWidth(45);
        }

        playbackWindow.setTitle("Playback Window");
        VBox playbackRoot = new VBox(10);
        Scene playbackScene = new Scene(playbackRoot, 650, 600);
        playbackScene.getStylesheets().add("stylesheet.css");
        ImageView albumArt = new ImageView("/Images/defaultArt.jpg");


        HBox nowPlaying  = new HBox(10);
        HBox controlsBox  = new HBox(10);
        Button back  = new Button();
        back.setGraphic(backwardsIcon);
        Button playPause  = new Button();
        Label currenttimeLabel = new Label("0.00/0.00");
        playPause.setGraphic(pauseIcon);
        Button forward  = new Button();
        forward.setGraphic(forwardsIcon);
        Label playingInfo = new Label("Now Playing: ");
        Slider volume = new Slider();
        Slider progress = new Slider();


        if (selectedSong != null && AlbumService.getAlbumArtFromName(selectedSong.getAlbumName(), database) != null) {
            albumArt = new ImageView(new Image (new File(AlbumService.getAlbumArtFromName(selectedSong.getAlbumName(), database)).toURI().toString()));
        }
        if (selectedSong != null) {
            playingInfo.setText("Now Playing: \n    "+selectedSong.getSongName()+"\n        "+selectedSong.getArtistName()+"\n            "+selectedSong.getAlbumName());
        }

        albumArt.setFitHeight(400);
        albumArt.setFitWidth(400);


        volume.setShowTickLabels(true);
        volume.setShowTickMarks(true);
        volume.setValue(0.5);
        volume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (songPlayer != null) {
                    songPlayer.setVolume(volume.getValue() / 100);
                    volume.setValue(songPlayer.getVolume()*100);
                }
            }
        });




        if (songPlayer != null) {
            songPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    progress.setValue(newValue.toSeconds());
                    progress.setMax(songPlayer.getTotalDuration().toSeconds());
                    String currentTime = String.format("%02d:%02d", ((long) songPlayer.getCurrentTime().toSeconds()) / 60, ((long) songPlayer.getCurrentTime().toSeconds()) % 60);
                    String totalTime = String.format("%02d:%02d", ((long) songPlayer.getTotalDuration().toSeconds()) / 60, ((long) songPlayer.getTotalDuration().toSeconds()) % 60);
                    currenttimeLabel.setText(currentTime+"/"+totalTime);
                }
            });

            progress.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    songPlayer.pause();
                }
            });

            progress.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    songPlayer.seek(Duration.seconds(progress.getValue()));
                    songPlayer.play();
                }
            });
        }




        playPause.setOnAction((ActionEvent ae) -> {
            controller.playButtonPressed();
            if (songPlayer != null && songPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                playPause.setGraphic(playIcon);
            }
            else {
                playPause.setGraphic(pauseIcon);
            }
        });

        forward.setOnAction((ActionEvent ae) -> {
           if (songsTable.getSelectionModel().getSelectedItem() != null) {
               songsTable.getSelectionModel().selectBelowCell();
               if (songPlayer != null) {
                   songPlayer.stop();
               }
               playSong();
               playbackWindow.close();
               playWindow();
           }

        });

        back.setOnAction((ActionEvent ae) -> {
            if (songsTable.getSelectionModel().getSelectedItem() != null) {
                songsTable.getSelectionModel().selectAboveCell();
                if (songPlayer != null) {
                    songPlayer.stop();
                }
                playSong();
                playbackWindow.close();
                playWindow();
            }

        });


        playbackWindow.getIcons().add(new Image("/Images/playIcon.png"));

        for (Button b : Arrays.asList(back,playPause,forward)) {
            b.getStyleClass().add("library-buttons");
        }

        nowPlaying.getChildren().addAll(playingInfo,volume);
        controlsBox.getChildren().addAll(back,playPause,forward);

        playbackRoot.getChildren().addAll(albumArt,nowPlaying,currenttimeLabel,progress,controlsBox);
        playbackRoot.setAlignment(Pos.CENTER);
        controlsBox.setAlignment(Pos.CENTER);
        nowPlaying.setAlignment(Pos.CENTER);
        playbackWindow.setResizable(false);

        playbackWindow.setScene(playbackScene);
        playbackWindow.show();
        playWindowShowing = true;

        playbackWindow.setOnCloseRequest((WindowEvent we) -> playWindowShowing = false);




    }


    public static void main(String[] args) {
        launch(args);
    }
}

