package Model;

class Album {
    private int albumID;
    private int artistID;
    private String albumName;
    private int releaseYear;
    private String albumGenre;

    public Album(int albumID, int artistID, String albumName, int releaseYear, String albumGenre) {
        this.albumID = albumID;
        this.artistID = artistID;
        this.albumName = albumName;
        this.releaseYear = releaseYear;
        this.albumGenre = albumGenre;
    }

    public int getAlbumID() {
        return albumID;
    }

    public int getArtistID() {
        return artistID;
    }

    public String getAlbumName() {
        return albumName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getAlbumGenre() {
        return albumGenre;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setAlbumGenre(String albumGenre) {
        this.albumGenre = albumGenre;
    }

}
