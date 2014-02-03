package tw.wancw.curator.api;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;

class StreamResponseHandler extends JsonHttpResponseHandler {

    private final MeiZiCardsResponseHandler handler;

    StreamResponseHandler(MeiZiCardsResponseHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("handler can't be null.");
        }

        this.handler = handler;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        Log.d("Curator/API/Stream", "success (object)");
        try {
            JSONArray results = response.getJSONArray("results");

            final int resultsLength = results.length();
            final MeiZiCard[] cards = new MeiZiCard[resultsLength];
            for (int i = 0; i < resultsLength; ++i) {
                JSONObject result = results.getJSONObject(i);
                cards[i] = new MeiZiCard(result.getString("name"), result.getString("image"));
            }

            handler.onSuccess(Collections.unmodifiableList(Arrays.asList(cards)));
        } catch (JSONException e) {
            // TODO call handler.onFailure();
        }
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        Log.d("Curator/API/Stream", "success (array)");
        // TODO call handler.onFailure();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
        Log.d("Curator/API/Stream", "failure (object)");
        // TODO call handler.onFailure();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONArray errorResponse) {
        Log.d("Curator/API/Stream", "failure (array)");
        // TODO call handler.onFailure();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, String errorResponse) {
        Log.d("Curator/API/Stream", "failure (string)");
        // TODO call handler.onFailure();
    }

}
