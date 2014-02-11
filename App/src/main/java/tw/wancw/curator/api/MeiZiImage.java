package tw.wancw.curator.api;

public class MeiZiImage {
    private final String url;
    private final int width;
    private final int height;

    public MeiZiImage(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isPortrait() {
        return height > width;
    }
}
