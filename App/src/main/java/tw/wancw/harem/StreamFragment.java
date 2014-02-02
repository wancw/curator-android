package tw.wancw.harem;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StreamFragment extends Fragment {

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
        (new LoadMeiZiTask(adapter)).execute();
    }
}

class MeiZiCardAdapter extends BaseAdapter {

    private final Context context;
    private final ImageLoader loader;
    private final List<MeiZiCard> cards;

    public MeiZiCardAdapter(Context context, ImageLoader loader) {
        this.context = context;
        this.loader = loader;
        this.cards = new ArrayList<MeiZiCard>();
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int i) {
        return cards.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.view_meizicard, null);

            holder = new ViewHolder();
            holder.cardImage = (ImageView) view.findViewById(R.id.card_image);
            holder.cardCaption = (TextView) view.findViewById(R.id.card_caption);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        MeiZiCard card = (MeiZiCard) getItem(i);

        holder.cardImage.setVisibility(View.INVISIBLE);
        loader.cancelDisplayTask(holder.cardImage);
        loader.displayImage(card.getImageUrl(), holder.cardImage, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                view.setVisibility(View.VISIBLE);
            }
        });

        holder.cardCaption.setText(card.getCaption());

        return view;
    }

    public void add(List<MeiZiCard> cards) {
        this.cards.addAll(cards);
        notifyDataSetChanged();
    }

    private class ViewHolder {
        ImageView cardImage;
        TextView cardCaption;
    }
}

class MeiZiCard {
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

class LoadMeiZiTask extends AsyncTask<String, Void, MeiZiCard[]> {

    private final MeiZiCardAdapter adapter;

    public LoadMeiZiTask(MeiZiCardAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected MeiZiCard[] doInBackground(String... strings) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        return new MeiZiCard[] {
            new MeiZiCard("小悠 / Judy", "http://t.curator.im/9sTJDaUMqqnWG6acMajyP-R1OGE=/500x0/media.curator.im/images/132434493631215/171637146377616_1460298_171637146377616_834083471_n.jpg"),
            new MeiZiCard("Shakira Chung", "http://t.curator.im/TSy40R1QIu_0RsLgYOq9Nd0u210=/500x0/media.curator.im/images/1408849209352845/1422670297970736_1502594_1422670297970736_932427694_o.jpg"),
            new MeiZiCard("Shakira Chung", "http://t.curator.im/HDkT9EgmuLdvvlzuB1FLZrrqgHk=/500x0/media.curator.im/images/1408849209352845/1422670211304078_1412314_1422670211304078_481140092_o.jpg"),
            new MeiZiCard("Shakira Chung", "http://t.curator.im/doE9zSqwqybuGR__zN67i0Xr46o=/500x0/media.curator.im/images/1408849209352845/1422567154647717_1538798_1422567154647717_525836477_n.jpg"),
        };
    }

    @Override
    protected void onPostExecute(MeiZiCard[] cards) {
        adapter.add(Arrays.asList(cards));
    }
}