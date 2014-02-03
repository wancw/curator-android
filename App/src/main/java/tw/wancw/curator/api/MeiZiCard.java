package tw.wancw.curator.api;

public class MeiZiCard {
    private final String caption;
    private final String imageUrl;

    public MeiZiCard(String caption, String imageUrl) {
        this.caption = caption;
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
