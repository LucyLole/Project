package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AlbumService {

    public static void selectAll(List<Album> targetList, DatabaseConnection database) {
        PreparedStatement statement = database.newStatement("SELECT * FROM Albums ORDER BY AlbumID");

        try {
            if (statement != null) {

                ResultSet results = database.executeQuery(statement);

                if (results != null) {
                    while (results.next()) {
                        targetList.add(new Album(
                                results.getInt("AlbumID"),
                                results.getInt("ArtistID"),
                                results.getString("AlbumName"),
                                results.getInt("ReleaseYear"),
                                results.getString("Genre"),
                                results.getString("ArtworkFilePath")
                        ));
                    }
                }
            }
        } catch (SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }

    public static Album selectById(int AlbumID, DatabaseConnection database) {

        Album result = null;

        PreparedStatement selectStatement = database.newStatement("SELECT AlbumID, ArtistID, AlbumName, ReleaseYear, Genre, ArtworkFilePath FROM Albums WHERE AlbumID = ?");

        try {
            if (selectStatement != null) {
                selectStatement.setInt(1, AlbumID);
                ResultSet results = database.executeQuery(selectStatement);

                if (results != null) {
                    result = new Album(
                            results.getInt("AlbumID"),
                            results.getInt("ArtistID"),
                            results.getString("AlbumName"),
                            results.getInt("ReleaseYear"),
                            results.getString("Genre"),
                            results.getString("ArtworkFilePath"));
                }

            }

        } catch (SQLException resultsException) {
            System.out.println("Database select by id error: " + resultsException.getMessage());
        }

        return result;
    }

    public static void save(Album itemToSave, DatabaseConnection database) {

        Album existingItem = null;
        if (itemToSave.getAlbumID() != 0) existingItem = selectById(itemToSave.getAlbumID(), database);

        try {
            if (existingItem == null) {
                PreparedStatement statement = database.newStatement("INSERT INTO Albums (ArtistID," +
                        "AlbumName, ReleaseYear, Genre, ArtworkFilePath) VALUES (?,?,?,?,?)");
                statement.setInt(1, itemToSave.getArtistID());
                statement.setString(2, itemToSave.getAlbumName());
                statement.setInt(3, itemToSave.getReleaseYear());
                statement.setString(4, itemToSave.getAlbumGenre());
                statement.setString(5, itemToSave.getArtworkFilePath());
                database.executeUpdate(statement);
            }
            else {
                PreparedStatement statement = database.newStatement("UPDATE Albums SET (ArtistID, AlbumName, ReleaseYear," +
                        "Genre, ArtworkFilePath) = (?,?,?,?,?) WHERE AlbumID = ?");
                statement.setInt(1,itemToSave.getArtistID());
                statement.setString(2, itemToSave.getAlbumName());
                statement.setInt(3, itemToSave.getReleaseYear());
                statement.setString(4, itemToSave.getAlbumGenre());
                statement.setString(5, itemToSave.getArtworkFilePath());
                statement.setInt(6, itemToSave.getAlbumID());
                database.executeUpdate(statement);
            }
        } catch (SQLException resultsException) {
            System.out.println("Database saving error: " + resultsException.getMessage());
        }
    }

    public static void deleteById(Album itemToDelete, DatabaseConnection database) {
        PreparedStatement statement = database.newStatement("DELETE FROM Albums WHERE AlbumID = ?");

        try {
            if (statement != null) {
                statement.setInt(1,itemToDelete.getAlbumID());
                database.executeUpdate(statement);
            }
        } catch (SQLException resultsException) {
            System.out.println("Database deletion error: "+ resultsException.getMessage());
        }
    }



}
