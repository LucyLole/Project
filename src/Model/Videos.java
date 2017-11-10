package Model;

class Videos {
    private int videoID;
    private String videoName;
    private float videoLength;
    private String videoFilePath;


    public Videos(int videoID, String videoName, float videoLength, String videoFilePath) {
        this.videoID = videoID;
        this.videoName = videoName;
        this.videoLength = videoLength;
        this.videoFilePath = videoFilePath;
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

    public String getVideoFilePath() {
        return videoFilePath;
    }

    public void setVideoFilePath(String videoFilePath) {
        this.videoFilePath = videoFilePath;
    }
}