package tw.wancw.curator;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

import java.util.Collection;

import tw.wancw.curator.api.CuratorApi;
import tw.wancw.curator.api.MeiZiCard;
import tw.wancw.curator.api.MeiZiCardsResponseHandler;
import tw.wancw.curator.widget.MeiZiCardAdapter;
import tw.wancw.widget.ListViewOnScrollListenerBroadcaster;

public class MeiZiCardsFragment extends Fragment {

    public static final String PARAM_SOURCE_TYPE = "source_type";

    public static final int SOURCE_STREAM = 1;
    public static final int SOURCE_GIRL_OF_THE_DAY = 2;

    private static final CuratorApi api = new CuratorApi(BuildConfig.CURATOR_API_TOKEN);

    private ImageLoader loader;

    protected MeiZiCardAdapter adapter;
    protected GridView cardsView;
    protected View loadingIndicator;

    protected PaginatedLoader cardsLoader;

    public MeiZiCardsFragment() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meizi_cards, container, false);

        loadingIndicator = rootView.findViewById(R.id.loading_indicator);

        cardsView = (GridView) rootView.findViewById(R.id.cards);
        cardsView.setAdapter(adapter);
        cardsView.setOnScrollListener(new ListViewOnScrollListenerBroadcaster(
            new PauseOnScrollListener(loader, true, true),
            new LoadMoreTrigger()
        ));

        Bundle arguments = getArguments();
        int sourceType = arguments.getInt(PARAM_SOURCE_TYPE);
        if (sourceType == SOURCE_STREAM) {
            cardsLoader = new PaginatedStreamLoader();
        } else {
            cardsLoader = new PaginatedGirlOfTheDayLoader();
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateLayout();
        cardsLoader.loadNextPage();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateLayout();
    }

    private void updateLayout() {
        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            cardsView.setNumColumns(3);
        } else {
            cardsView.setNumColumns(2);
        }
    }

    private class LoadMoreTrigger implements AbsListView.OnScrollListener {

        @Override
        public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (visibleItemCount > 0 && absListView.getLastVisiblePosition() + 1 == totalItemCount) {
                cardsLoader.loadNextPage();
            }
        }

        @Override
        public void onScrollStateChanged(AbsListView absListView, int scrollState) {
            // Do nothing
        }
    }

    private abstract interface PaginatedLoader {
        public void loadNextPage();
    }

    private class PaginatedStreamLoader implements PaginatedLoader, MeiZiCardsResponseHandler {

        private int lastPage = 0;
        private boolean loading = false;
        private boolean noMoreData = false;

        @Override
        public void loadNextPage() {
            if (loading || noMoreData) {
                return;
            }

            loading = true;

            loadingIndicator.setVisibility(View.VISIBLE);

            lastPage = lastPage + 1;
            api.stream(lastPage, this);
        }

        @Override
        public void onSuccess(Collection<MeiZiCard> cards) {
            if (cards.size() > 0) {
                adapter.add(cards);
            } else {
                noMoreData = true;
            }

            loadingIndicator.setVisibility(View.GONE);

            loading = false;
        }

        @Override
        public void OnFailure(String message) {
            loadingIndicator.setVisibility(View.GONE);
            loading = false;
            Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_LONG).show();
        }
    }

    private class PaginatedGirlOfTheDayLoader implements PaginatedLoader, MeiZiCardsResponseHandler {

        private int lastPage = 0;
        private boolean loading = false;
        private boolean noMoreData = false;

        @Override
        public void loadNextPage() {
            if (loading || noMoreData) {
                return;
            }

            loading = true;

            loadingIndicator.setVisibility(View.VISIBLE);

            lastPage = lastPage + 1;
            api.girlOfTheDay(lastPage, this);
        }

        @Override
        public void onSuccess(Collection<MeiZiCard> cards) {
            if (cards.size() > 0) {
                adapter.add(cards);
            } else {
                noMoreData = true;
            }

            loadingIndicator.setVisibility(View.GONE);

            loading = false;
        }

        @Override
        public void OnFailure(String message) {
            loadingIndicator.setVisibility(View.GONE);
            loading = false;
            Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_LONG).show();
        }
    }
}
