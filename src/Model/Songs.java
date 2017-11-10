package Model;

class Songs {
    private int songID;
    private int artistID;
    private int albumID;
    private String songName;
    private float songLength;
    private String songGenre;
    private String filePath;

    public Songs(int songID, int artistID, int albumID, String filePath, String songName, float songLength, String songGenre) {
        this.songID = songID;
        this.artistID = artistID;
        this.albumID = albumID;
        this.songName = songName;
        this.songLength = songLength;
        this.songGenre = songGenre;
        this.filePath = filePath;
    }

    public int getArtistID() {
        return artistID;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getSongID() {
        return songID;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public float getSongLength() {
        return songLength;
    }

    public void setSongLength(float songLength) {
        this.songLength = songLength;
    }

    public String getSongGenre() {
        return songGenre;
    }

    public void setSongGenre(String songGenre) {
        this.songGenre = songGenre;
    }
}
