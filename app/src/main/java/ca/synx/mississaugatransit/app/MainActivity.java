package ca.synx.mississaugatransit.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.synx.mississaugatransit.fragments.MapsFragment;
import ca.synx.mississaugatransit.fragments.NavigationDrawerFragment;
import ca.synx.mississaugatransit.fragments.RoutesViewFlipperFragment;
import ca.synx.mississaugatransit.handlers.DatabaseHandler;
import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.models.Stop;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, RoutesViewFlipperFragment.IRoutesViewFlipperFragmentCallbacks {

    private DatabaseHandler mDatabaseHandler;

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private RoutesViewFlipperFragment mRoutesViewFlipperFragment;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make sure the DB handler gets initialized.
        mDatabaseHandler = new DatabaseHandler(this);

        // Get a reference to the navigation drawer.
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Update title of Action Bar.
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment fragment = GetFragment(position + 1);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_favorites);
                break;
            case 2:
                mTitle = getString(R.string.title_routes);
                break;
            case 3:
                mTitle = getString(R.string.title_stops);
                break;
            case 4:
                mTitle = getString(R.string.title_map);
                break;
        }
    }

    protected Fragment GetFragment(int position) {

        // Update title.
        onSectionAttached(position);

        // Get Fragment.
        switch (position) {
            case 1:
                return PlaceholderFragment.newInstance(position);
            case 2:
                if (mRoutesViewFlipperFragment == null)
                    mRoutesViewFlipperFragment = new RoutesViewFlipperFragment();
                return mRoutesViewFlipperFragment;
            case 3:
                return PlaceholderFragment.newInstance(position);
            case 4:
                return MapsFragment.newInstance();
        }

        return null;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public void onRouteSelected(Route route) {
        mRoutesViewFlipperFragment.showNext();
    }

    @Override
    public void onStopSelected(Stop stop) {
        mRoutesViewFlipperFragment.showNext();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            try {
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            } catch (Exception e) {
            }

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
