package tw.wancw.curator.api;

import java.util.Collection;

public interface MeiZiCardsResponseHandler {
    public void onSuccess(Collection<MeiZiCard> cards);
}
