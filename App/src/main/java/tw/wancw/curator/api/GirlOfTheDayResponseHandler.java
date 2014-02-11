package tw.wancw.curator.api;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;

class GirlOfTheDayResponseHandler extends JsonHttpResponseHandler {

    private final MeiZiCardsResponseHandler handler;

    GirlOfTheDayResponseHandler(MeiZiCardsResponseHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("handler can't be null.");
        }

        this.handler = handler;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        Log.d("Curator/API/GirlOfTheDay", "success (object)");
        try {
            JSONArray results = response.getJSONArray("results");

            final int resultsLength = results.length();
            final MeiZiCard[] cards = new MeiZiCard[resultsLength];
            for (int i = 0; i < resultsLength; ++i) {
                JSONObject result = results.getJSONObject(i);
                MeiZiImage image = new MeiZiImage(result.getString("image"),
                    result.getInt("width"), result.getInt("height"));
                MeiZiImage thumbnail = new MeiZiImage(result.getString("thumbnail"),
                    result.getInt("thumbnail_width"), result.getInt("thumbnail_height"));
                cards[i] = new MeiZiCard(result.getString("date") + ' ' + result.getString("name"),
                    image, thumbnail);
            }

            handler.onSuccess(Collections.unmodifiableList(Arrays.asList(cards)));
        } catch (JSONException e) {
            handler.OnFailure(e.getMessage());
        }
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        Log.d("Curator/API/GirlOfTheDay", "success (array)");

        handler.OnFailure("Invalid response");
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
        Log.d("Curator/API/GirlOfTheDay", "failure (object)\n" +
            "Status Code: " + statusCode + "\n" +
            "Exception: " + e
        );

        handler.OnFailure(e.getMessage());
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONArray errorResponse) {
        Log.d("Curator/API/GirlOfTheDay", "failure (array)" +
            "Status Code: " + statusCode + "\n" +
            "Exception: " + e
        );

        handler.OnFailure(e.getMessage());
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, String errorResponse) {
        Log.d("Curator/API/GirlOfTheDay", "failure (string)" +
            "Status Code: " + statusCode + "\n" +
            "Exception: " + e
        );

        handler.OnFailure(e.getMessage());
    }

}
