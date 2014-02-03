package tw.wancw.curator;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

import org.apache.http.Header;

import java.util.Collection;

import tw.wancw.curator.api.CuratorApi;
import tw.wancw.curator.api.MeiZiCard;
import tw.wancw.curator.api.MeiZiCardsResponseHandler;
import tw.wancw.curator.widget.MeiZiCardAdapter;

public class StreamFragment extends Fragment {

    private static final CuratorApi api = new CuratorApi(BuildConfig.CURATOR_API_TOKEN);

    private MeiZiCardAdapter adapter;
    private ImageLoader loader;

    public StreamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .build();

        ImageLoaderConfiguration imageLoaderConfig = new ImageLoaderConfiguration.Builder(activity)
            .defaultDisplayImageOptions(displayImageOptions)
            .build();

        loader = ImageLoader.getInstance();
        loader.init(imageLoaderConfig);

        adapter = new MeiZiCardAdapter(activity, loader);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                update();
            }
        });
    }

    private void update() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stream, container, false);

        ListView cardsView = (ListView) rootView.findViewById(R.id.cards);
        cardsView.setAdapter(adapter);
        cardsView.setOnScrollListener(new PauseOnScrollListener(loader, true, true));

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        api.stream(new MeiZiCardsResponseHandler() {
            @Override
            public void onSuccess(Collection<MeiZiCard> cards) {
                Log.d("Curator/Stream", "cards loaded.");
                adapter.add(cards);
            }
        });
    }
}
