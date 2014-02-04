package tw.wancw.widget;


import android.widget.AbsListView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ListViewOnScrollListenerBroadcaster implements AbsListView.OnScrollListener {
    private final Set<AbsListView.OnScrollListener> listeners =
        new HashSet<AbsListView.OnScrollListener>();

    public ListViewOnScrollListenerBroadcaster(AbsListView.OnScrollListener ... listeners) {
        this.listeners.addAll(Arrays.asList(listeners));
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        for (AbsListView.OnScrollListener listener : listeners) {
            listener.onScrollStateChanged(absListView, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        for (AbsListView.OnScrollListener listener : listeners) {
            listener.onScroll(absListView, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }
}