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

    public void stream(MeiZiCardsResponseHandler handler) {
        final String path = "stream/";
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("page", 1);
        client.get(API_END_POINT + path, params, new StreamResponseHandler(handler));
    }
}
