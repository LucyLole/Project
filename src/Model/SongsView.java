package Model;

import javafx.beans.property.SimpleStringProperty;

public class SongsView {
    private int SongID;
    private final SimpleStringProperty SongName;
    private final SimpleStringProperty ArtistName;
    private final SimpleStringProperty AlbumName;
    private final SimpleStringProperty SongGenre;
    private int SongYear;
    private String SongLength;

    public SongsView(int SongID, String SongName, String ArtistName, String AlbumName, String SongGenre, int SongYear, String SongLength) {
        this.SongID = SongID;
        this.SongName = new SimpleStringProperty(SongName);
        this.ArtistName = new SimpleStringProperty(ArtistName);
        this.AlbumName = new SimpleStringProperty(AlbumName);
        this.SongGenre = new SimpleStringProperty(SongGenre);
        this.SongYear = SongYear;
        this.SongLength = SongLength;
    }

    public int getSongID() {
        return SongID;
    }

    public String getSongName() {
        return SongName.get();
    }

    public String getArtistName() {
        return ArtistName.get();
    }

    public String getAlbumName() {
        return AlbumName.get();
    }

    public String getSongGenre() {
        return SongGenre.get();
    }

    public int getSongYear() {
        return SongYear;
    }

    public String getSongLength() {
        return SongLength;
    }
}
