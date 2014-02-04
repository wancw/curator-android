package tw.wancw.curator.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class CuratorApi {

    private static final String API_END_POINT = "http://curator.im/api/";

    private static final AsyncHttpClient client = new AsyncHttpClient();

    private final String token;

    public CuratorApi(String token) {
        this.token = token;
    }

    public void stream(final MeiZiCardsResponseHandler handler) {
        stream(1, handler);
    }

    public void stream(final int page, final MeiZiCardsResponseHandler handler) {
        final String path = "stream/";
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("page", String.valueOf(page));
        client.get(API_END_POINT + path, params, new StreamResponseHandler(handler));
    }

    public void girlOfTheDay(final MeiZiCardsResponseHandler handler) {
        stream(1, handler);
    }

    public void girlOfTheDay(final int page, final MeiZiCardsResponseHandler handler) {
        final String path = "girl_of_the_day/";
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("page", String.valueOf(page));
        client.get(API_END_POINT + path, params, new GirlOfTheDayResponseHandler(handler));
    }
}
