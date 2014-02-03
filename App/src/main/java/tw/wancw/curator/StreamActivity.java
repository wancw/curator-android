package tw.wancw.curator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class StreamActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new StreamFragment())
                    .commit();
        }
    }

}
