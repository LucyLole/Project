import Model.DatabaseConnection;
import Model.*;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import java.util.ArrayList;


public class Controller {

    //These are the essential variables for our table view in the main library window
    private TableView<SongsView> songsTable;

    private DatabaseConnection database;
    private ArrayList<SongsView> allSongsView = new ArrayList<>();

    //This is the constructor class for our controller, which will take the table view from the main library window
    public Controller(TableView<SongsView> songsTable) {
        System.out.println("Initialising Controller...");

        this.songsTable = songsTable;

        database = new DatabaseConnection("MusicPlayerDatabase.db");
        updateTable(0);
    }

    public void updateTable(int selectedSongID) {
        allSongsView.clear();
        SongsService.selectForTable(allSongsView, database);

        songsTable.setItems(FXCollections.observableList(allSongsView));

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

}
