import Model.DatabaseConnection;

public class Controller {


    public static DatabaseConnection ConnectToDB() {
        DatabaseConnection ConnectedDB = new DatabaseConnection("../MusicPlayerDataBase.db");
        return ConnectedDB;
    }
}
