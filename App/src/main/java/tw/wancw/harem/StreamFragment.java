package tw.wancw.harem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StreamFragment extends Fragment {

    public StreamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stream, container, false);

//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
//        ImageLoader.getInstance().init(config);

//        final String uri = "http://media.curator.im/images/425748887508373/549666548449939_1462967_549666548449939_824904591_n.jpg";
//        ImageView view = (ImageView) rootView.findViewById(R.id.image);
//        ImageLoader.getInstance().displayImage(uri, view,
//                new SimpleImageLoadingListener() {
//                    @Override
//                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                        Log.i("test", "OK: " + imageUri);
//                    }
//                    @Override
//                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                        Log.i("test", "Failed: " + imageUri + ": " + failReason);
//                    }
//                });

        return rootView;
    }

}
