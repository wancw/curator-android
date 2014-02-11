package tw.wancw.curator.api;

public class MeiZiCard {
    private final String caption;
    private final MeiZiImage image;
    private final MeiZiImage thumbnail;

    public MeiZiCard(String caption, MeiZiImage image, MeiZiImage thumbnail) {
        this.caption = caption;
        this.image = image;
        this.thumbnail = thumbnail;
    }

    public String getCaption() {
        return caption;
    }

    public MeiZiImage getImage() {
        return image;
    }

    public MeiZiImage getThumbnail() {
        return thumbnail;
    }
}
