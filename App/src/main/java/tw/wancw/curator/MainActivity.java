package tw.wancw.curator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;

public class MainActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {

    private static final String STATE_SELECTED_NAVIGATION_SOURCE = "selected_navigate_source";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            actionBar.getThemedContext(),
            android.R.layout.simple_spinner_item, android.R.id.text1,
            new String[] { "正妹流", "一天一正妹" }
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionBar.setListNavigationCallbacks(adapter, this);
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_SOURCE)) {
            getSupportActionBar().setSelectedNavigationItem(
                savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_SOURCE));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_SOURCE,
            getSupportActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        Fragment fragment = new MeiZiCardsFragment();
        Bundle args = new Bundle();
            args.putInt(MeiZiCardsFragment.PARAM_SOURCE_TYPE,
        if (position == 0) {
                MeiZiCardsFragment.SOURCE_STREAM);
        } else {
            args.putInt(MeiZiCardsFragment.PARAM_SOURCE_TYPE,
                MeiZiCardsFragment.SOURCE_GIRL_OF_THE_DAY);
        }
        fragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, fragment)
            .commit();

        return true;
    }
}
