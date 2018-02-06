import Model.DatabaseConnection;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TableView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.mpatric.mp3agic.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Optional;


public class Controller {

    //These are the essential variables for our table view in the main library window
    private TableView<SongsView> songsTable;
    private ArrayList<SongsView> allSongsView = new ArrayList<>();

    private FileChooser fileChooser = new FileChooser();
    private DirectoryChooser directoryChooser = new DirectoryChooser();

    private Stage stage;

    private ArrayList<String> mp3Paths = new ArrayList();

    private DatabaseConnection database;

    public String searchText;

    //This is the constructor class for our controller, which will take the table view from the main library window
    public Controller(TableView<SongsView> songsTable,Stage stage) {
        System.out.println("Initialising Controller...");

        this.songsTable = songsTable;

        database = Main.database;
        updateTable(0,"");
    }

    public void updateTable(int selectedSongID, String newValue) {
        //clear the tableView
        allSongsView.clear();

        searchText = newValue;
        //get the song list from the join query
        SongsService.selectForTable(allSongsView, database);

        //make the list int an observable list
        final ObservableList<SongsView> data
                = FXCollections.observableArrayList(allSongsView);

        //make it into a filtered list
        FilteredList<SongsView> filteredSongs = new FilteredList(data, p -> true);

        filteredSongs.setPredicate(SongsView -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            if (SongsView.getSongName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (SongsView.getAlbumName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (SongsView.getArtistName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            return false;
                });

        SortedList<SongsView> sortedSongs = new SortedList<>(filteredSongs);

        sortedSongs.comparatorProperty().bind(songsTable.comparatorProperty());


        //set the items to be our filtered list
        songsTable.setItems(sortedSongs);


        if (selectedSongID != 0) {
            for (int n = 0; n < songsTable.getItems().size(); n++) {
                if (songsTable.getItems().get(n).getSongID() == selectedSongID) {
                    songsTable.getSelectionModel().select(n);
                    songsTable.getFocusModel().focus(n);
                    songsTable.scrollTo(n);
                    break;
                }
            }
        }
    }

    public void playButtonPressed() {
        if (Main.songPlayer != null) {
            if (Main.songPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                Main.songPlayer.pause();
            }
            else if (Main.songPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                Main.songPlayer.play();
            }
        }
    }



    public void addNewFilePressed() throws Exception {
        fileChooser.setTitle("Add Song File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("mp3","*.mp3"));


        File newFile = fileChooser.showOpenDialog(stage);
        addFile(newFile);
        updateTable(songsTable.getSelectionModel().getSelectedIndex(),"");


    }

    public void addFile(File newFile) throws Exception {

        if (newFile != null) {

            String filePath = newFile.getCanonicalPath();
            String name = newFile.getName().replace(".mp3", "");
            Mp3File mp3Data = new Mp3File(filePath);
            ID3v2 mp3DataTag = mp3Data.getId3v2Tag();
            String artistName = mp3DataTag.getArtist();
            String albumName = mp3DataTag.getAlbum();
            String length = String.format("%02d:%02d", mp3Data.getLengthInSeconds() / 60, mp3Data.getLengthInSeconds() % 60);
            String genre = mp3DataTag.getGenreDescription();
            int year = Integer.parseInt(mp3DataTag.getYear());

            int ArtistID = ArtistService.getArtistIdFromName(artistName, database);


            if (ArtistID == 0) {
                Artist newArtist = new Artist(0, artistName);
                ArtistService.save(newArtist, database);
                ArtistID = ArtistService.getArtistIdFromName(artistName, database);
            }

            int AlbumID = AlbumService.getAlbumIdFromName(albumName, database);
            if (AlbumID == 0) {
                Album newAlbum = new Album(0, ArtistID, albumName, year, genre, "");
                AlbumService.save(newAlbum, database);
                AlbumID = AlbumService.getAlbumIdFromName(albumName, database);
            }


            Songs newSong = new Songs(0, ArtistID, AlbumID, filePath, name, length, genre);
            SongsService.save(newSong, database);
        }
    }




    public void searchFolder() throws Exception {
        mp3Paths.clear();

        directoryChooser.setTitle("Add Album");

        File startDirectory = directoryChooser.showDialog(stage);
        getMp3s(startDirectory);

        for (String m : mp3Paths) {
            File f = new File(m);
            addFile(f);
        }

        updateTable(songsTable.getSelectionModel().getSelectedIndex(),"");



    }

    void getMp3s(File f) {
        File[] files;
        if (f.isDirectory() && (files = f.listFiles()) != null) {
            for (File file : files) {
                getMp3s(file);
            }
        }
        else {
            String path = f.getPath();
            if (path.endsWith((".mp3"))) {
                mp3Paths.add(f.getPath());
            }
        }

    }



    public void newAlbumArt(SongsView selectedItem) throws Exception {
        fileChooser.setTitle("Add Album Art");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg","*.jpg"),new FileChooser.ExtensionFilter("png","*.png"));

        File newFile = fileChooser.showOpenDialog(stage);
        if (newFile != null) {
            String artworkFilePath = newFile.getCanonicalPath();
            int AlbumID = AlbumService.getAlbumIdFromName(selectedItem.getAlbumName(),database);

            AlbumService.setAlbumArtowkrFromID(AlbumID,artworkFilePath,database);
        }
    }


    public void addButtonPressed() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Select Add Type");
        alert.setHeaderText("Please select what to add");
        ButtonType buttonTypeSingleFile = new ButtonType("Add File");
        ButtonType buttonTypeAlbum = new ButtonType("Add Album");
        ButtonType buttonTypeAlbumArt = new ButtonType("Add Album Artwork");

        alert.getButtonTypes().removeAll(ButtonType.OK,ButtonType.CANCEL);
        alert.getButtonTypes().addAll(buttonTypeSingleFile,buttonTypeAlbum,buttonTypeAlbumArt);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeSingleFile) {
            addNewFilePressed();
        }
        else if (result.get() == buttonTypeAlbum) {
            searchFolder();
        }
        else if (result.get() == buttonTypeAlbumArt) {
            if (songsTable.getSelectionModel().getSelectedItem() != null) {
                SongsView selectedItem = songsTable.getSelectionModel().getSelectedItem();
                newAlbumArt(selectedItem);
            }
            else {
                Alert noSelected = new Alert(Alert.AlertType.ERROR,"No Selection");
                noSelected.setContentText("Please select a song first");
                noSelected.showAndWait();
            }
        }
    }



    public void onDeltePressed() {
        if (songsTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this file?", ButtonType.YES,ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                SongsView selectedItem = songsTable.getSelectionModel().getSelectedItem();
                SongsService.deleteById(SongsService.selectById(selectedItem.getSongID(), database), database);
                updateTable(songsTable.getSelectionModel().getSelectedIndex() + 1, searchText);
            }

        }

    }

}
