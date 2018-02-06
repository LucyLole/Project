package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SongsService {
    public static void selectAll(List<Songs> targetList, DatabaseConnection database) {
        PreparedStatement statement = database.newStatement("SELECT * FROM Songs ORDER BY SongID");

        try {
            if (statement != null) {

                ResultSet results = database.executeQuery(statement);

                if (results != null) {
                    while (results.next()) {
                        targetList.add(new Songs(
                                results.getInt("SongID"),
                                results.getInt("ArtistID"),
                                results.getInt("AlbumID"),
                                results.getString("FilePath"),
                                results.getString("SongName"),
                                results.getString("SongLength"),
                                results.getString("SongGenre")
                        ));
                    }
                }
            }
        } catch (SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }

    public static void selectForTable(List<SongsView> targetList, DatabaseConnection database) {
        //thiss method will bring everything together to be put into our table view on the main window
        PreparedStatement statement = database.newStatement(
                "SELECT Songs.SongID As 'id', Songs.SongName As 'sName', Artist.ArtistName As 'aName', " +
                        " Albums.AlbumName As 'a2Name', Songs.SongGenre As 'genre', Albums.ReleaseYear As 'year', Songs.SongLength As 'length' FROM Songs" +
                        " LEFT OUTER JOIN Artist ON Songs.ArtistID = Artist.ArtistID" +
                        " LEFT OUTER JOIN Albums ON Songs.AlbumID = Albums.AlbumID" +
                        " ORDER BY Songs.SongName"
        );

        try {
            if (statement != null) {

                ResultSet results = database.executeQuery(statement);

                if (results != null) {
                    while (results.next()) {
                        targetList.add(new SongsView(
                                results.getInt("id"),
                                results.getString("sName"),
                                results.getString("aName"),
                                results.getString("a2Name"),
                                results.getString("genre"),
                                results.getInt("year"),
                                results.getString("length")
                        ));
                    }
                }
            }
        } catch (SQLException resultsException) {
            System.out.println("Database select all error (select for table): " + resultsException.getMessage());
        }



    }

    public static Songs selectById(int SongID, DatabaseConnection database) {

        Songs result = null;

        PreparedStatement statement = database.newStatement("SELECT * FROM Songs WHERE SongID = ?");

        try {
            if (statement != null) {
                statement.setInt(1, SongID);
                ResultSet results = database.executeQuery(statement);

                if (results != null) {
                    while (results.next()) {
                        result = new Songs(
                                results.getInt("SongID"),
                                results.getInt("ArtistID"),
                                results.getInt("AlbumID"),
                                results.getString("FilePath"),
                                results.getString("SongName"),
                                results.getString("SongLength"),
                                results.getString("SongGenre")
                        );
                    }
                }

            }

        } catch (SQLException resultsException) {
            System.out.println("Database select by id error: " + resultsException.getMessage());
        }

        return result;
    }

    public static void save(Songs itemToSave, DatabaseConnection database) {
        Songs existingItem = null;
        if (itemToSave.getSongID() != 0) existingItem = selectById(itemToSave.getSongID(), database);

        try {
            if (existingItem == null) {
                PreparedStatement statement = database.newStatement("INSERT INTO Songs (ArtistID," +
                        "AlbumID, FilePath, SongName, SongLength, SongGenre) VALUES (?,?,?,?,?,?)");
                statement.setInt(1, itemToSave.getArtistID());
                statement.setInt(2, itemToSave.getAlbumID());
                statement.setString(3, itemToSave.getFilePath());
                statement.setString(4, itemToSave.getSongName());
                statement.setString(5, itemToSave.getSongLength());
                statement.setString(6, itemToSave.getSongGenre());
                database.executeUpdate(statement);
            }
            else {
                PreparedStatement statement = database.newStatement("UPDATE Songs SET ArtistID = ?, AlbumID = ?, FilePath = ?," +
                        "SongName = ?, SongLength = ?, SongGenre = ? WHERE SongID = ?");
                statement.setInt(1, itemToSave.getArtistID());
                statement.setInt(2, itemToSave.getAlbumID());
                statement.setString(3, itemToSave.getFilePath());
                statement.setString(4, itemToSave.getSongName());
                statement.setString(5, itemToSave.getSongLength());
                statement.setInt(5, itemToSave.getSongID());
                database.executeUpdate(statement);
                statement.close();
            }
        } catch (SQLException resultsException) {
            System.out.println("Database saving error: " + resultsException.getMessage());
        }
    }

    public static void deleteById(Songs itemToDelete, DatabaseConnection database) {
        PreparedStatement statement = database.newStatement("DELETE FROM Songs WHERE SongID = ?");

        try {
            if (statement != null) {
                statement.setInt(1,itemToDelete.getSongID());
                database.executeUpdate(statement);
            }
        } catch (SQLException resultsException) {
            System.out.println("Database deletion error: "+ resultsException.getMessage());
        }
    }

    public static String getFilePath(SongsView song, DatabaseConnection database) {
        PreparedStatement statement = database.newStatement("SELECT FilePath FROM Songs WHERE SongID = ?");
        String result =null;
        try {
            if (statement != null) {
                statement.setInt(1,song.getSongID());
                ResultSet resultRaw = database.executeQuery(statement);

                if (resultRaw != null) {
                    result = resultRaw.getString("FilePath");
                }
            }
        } catch (SQLException resultsException) {
            System.out.println("Database filepath get error: "+ resultsException.getMessage());
        }
        return result;

    }



}
