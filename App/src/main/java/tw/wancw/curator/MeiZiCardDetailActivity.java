package tw.wancw.curator;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.FileCountLimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class MeiZiCardDetailActivity extends Activity {

    public static final String PARAM_TITLE = "title";
    public static final String PARAM_IMAGE_URL = "image_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActionBar();

        setContentView(R.layout.activity_meizi_card_detail);

        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .build();

        File cacheDir = StorageUtils.getCacheDirectory(this);
        ImageLoaderConfiguration imageLoaderConfig = new ImageLoaderConfiguration.Builder(this)
            .defaultDisplayImageOptions(displayImageOptions)
            .discCache(new FileCountLimitedDiscCache(cacheDir, 250))
            .build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(imageLoaderConfig);

        String title= getIntent().getExtras().getString(PARAM_TITLE);
        setTitle(title);

        String imageUrl = getIntent().getExtras().getString(PARAM_IMAGE_URL);
        ImageView imageView = (ImageView) findViewById(R.id.card_image);
        imageLoader.displayImage(imageUrl, imageView);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setUpActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish(); // NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
