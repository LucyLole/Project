package Model;

public class Artist {
    private int artistID;
    private String artistName;

    public Artist(int artistID, String artistName) {
        this.artistID = artistID;
        this.artistName = artistName;
    }

    public int getArtistID() {
        return artistID;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
