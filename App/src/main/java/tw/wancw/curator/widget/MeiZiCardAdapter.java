package tw.wancw.curator.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tw.wancw.curator.R;
import tw.wancw.curator.api.MeiZiCard;

public class MeiZiCardAdapter extends BaseAdapter {

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
        MeiZiCardAdapter.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.view_meizicard, null);

            holder = new MeiZiCardAdapter.ViewHolder();
            holder.cardImage = (ImageView) view.findViewById(R.id.card_image);
            holder.cardCaption = (TextView) view.findViewById(R.id.card_caption);

            view.setTag(holder);
        } else {
            holder = (MeiZiCardAdapter.ViewHolder) view.getTag();
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

    public void add(Collection<MeiZiCard> cards) {
        this.cards.addAll(cards);
        notifyDataSetChanged();
    }

    private class ViewHolder {
        ImageView cardImage;
        TextView cardCaption;
    }
}
