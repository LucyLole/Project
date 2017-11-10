package Model;

class Videos {
    private int videoID;
    private String videoName;
    private float videoLength;

    public Videos(int videoID, String videoName, float videoLength) {
        this.videoID = videoID;
        this.videoName = videoName;
        this.videoLength = videoLength;
    }

    public int getVideoID() {
        return videoID;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public float getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(float videoLength) {
        this.videoLength = videoLength;
    }
}
