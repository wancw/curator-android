package tw.wancw.curator;

public class MeiZiCard {
    private final String caption;
    private final String imageUrl;

    MeiZiCard(String caption, String imageUrl) {
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
