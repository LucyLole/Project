import Model.DatabaseConnection;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.mpatric.mp3agic.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Controller {

    //These are the essential variables for our table view in the main library window
    private TableView<SongsView> songsTable;
    private ArrayList<SongsView> allSongsView = new ArrayList<>();

    private FileChooser fileChooser = new FileChooser();

    private Stage stage;



    private DatabaseConnection database;

    public String searchText;

    //This is the constructor class for our controller, which will take the table view from the main library window
    public Controller(TableView<SongsView> songsTable,Stage stage) {
        System.out.println("Initialising Controller...");

        this.songsTable = songsTable;

        database = new DatabaseConnection("MusicPlayerDatabase.db");
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


        //set the items to be our filtered list
        songsTable.setItems(filteredSongs);


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

    public void addButtonPressed() throws IOException,UnsupportedTagException,InvalidDataException {
        fileChooser.setTitle("Add Song File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("mp3","*.mp3"));

        Mp3File mp3file = new Mp3File("SomeMp3File.mp3");

        File newFile = fileChooser.showOpenDialog(stage);
        if (newFile != null) {
            String filePath  = newFile.getCanonicalPath();
            String name = newFile.getName();

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
