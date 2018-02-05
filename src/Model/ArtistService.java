package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ArtistService {

    public static void selectAll(List<Artist> targetList, DatabaseConnection database) {

        //writing the SQl query to be executed, we want to select everything from the table Artist
        PreparedStatement statement = database.newStatement("SELECT * FROM Artist ORDER BY ArtistID");

        try {
            //as long as there is our query isnt empty, perform this code
            if (statement != null){
                //let a new var results be whatever we retrive from the db with our query
                ResultSet results = database.executeQuery(statement);

                //if there is another result in the result set, perform this code
                if (results.next()) {
                    //add the result to the array list in the form of our artist class
                    targetList.add(new Artist(results.getInt("ArtistID"),results.getString("ArtistName")));
                }
            }

        //if our query is for some reason rejected, throw this message so I can easily debug
        } catch (SQLException resultsException) {
            System.out.println("Database select all error: "+ resultsException.getMessage());
        }
    }

    public static Artist selectById(int ArtistID, DatabaseConnection database) {
        //set the result to be null at first
        Artist result = null;

        //creating a statement that will find everything on the row that matches the ID we supply
        PreparedStatement statement = database.newStatement("SELECT * FROM Artist WHERE ArtistID = ?");

        try {
            //as long as our query contains something
            if (statement != null) {

                //set the first (and only) var in our query to be the artistID that we passed into the the method
                statement.setInt(1, ArtistID);
                //set the results to be whatever the query finds
                ResultSet results = database.executeQuery(statement);

                //as long as there are results:
                if (results != null) {
                    //the result is equal to a new instance of our artist class
                    result = new Artist(results.getInt("ArtistID"), results.getString("ArtistName"));
                }
            }
            //catch if something goes wrong
        } catch (SQLException resultsException) {
            System.out.println("Database select by id error: " + resultsException.getMessage());
        }

        //return the result from our selection
        return result;
    }

    public static void deleteById(int ArtistID, DatabaseConnection database) {

        //delete the row with the matching artistID that was passed in
        PreparedStatement statement = database.newStatement("DELETE FROM Artist WHERE ArtistID = ?");

        try {
            if (statement != null) {
                //set the first (and only variable in the query to be artistID
                statement.setInt(1, ArtistID);
                //execcute the update of the table, removing the row
                database.executeUpdate(statement);
            }
        } catch (SQLException resultsException) {
            System.out.println("Database deletion error: "+ resultsException.getMessage());
        }
    }

    public static void save(Artist itemToSave, DatabaseConnection database) {
        //create a variable that will hold an existing item if it exists
        Artist existingItem = null;
        //if this is not the first entry in the database, having id 0, then the existing item is equal
        // to the results of a query based on the id given
        if (itemToSave.getArtistID() != 0) existingItem = selectById(itemToSave.getArtistID(), database);

        try {
            //however if there is nothing with that id, then we can insert these values rather than updating
            if (existingItem ==null ) {
                //insert into the appropriate columns of the database
                PreparedStatement statement = database.newStatement("INSERT INTO Artist (ArtistName) VALUES (?)");
                //setting the vars for the query to be from our object
                statement.setString(1, itemToSave.getArtistName());
                //execute the save
                database.executeUpdate(statement);

            }
            else {
                //if we already have something in that row, then we can just update the appropriate values
                PreparedStatement statement = database.newStatement("UPDATE Artist SET ArtistName = ? WHERE ArtistID = ?");
                statement.setString(1,itemToSave.getArtistName());
                statement.setInt(2, itemToSave.getArtistID());
                database.executeUpdate(statement);
            }
        } catch (SQLException resultsException) {
            System.out.println("Database saving error: " + resultsException.getMessage());
        }
    }


    public static int getArtistIdFromName(String ArtistName, DatabaseConnection database) {
        int id;
        id = 0;
        PreparedStatement statement = database.newStatement("SELECT ArtistID from Artist WHERE ArtistName = ?");
        try {
            if (statement != null) {
                statement.setString(1, ArtistName);
                ResultSet result  = database.executeQuery(statement);
                id = result.getInt("ArtistID");
            }

        } catch (SQLException resultsException) {
            System.out.println("Database name from ID error: " + resultsException.getMessage());
        }
        return id;
    }

}
