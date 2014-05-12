package ca.synx.mississaugatransit.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ca.synx.mississaugatransit.adapters.StopAdapter;
import ca.synx.mississaugatransit.app.R;
import ca.synx.mississaugatransit.interfaces.IFragment;
import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.models.Stop;
import ca.synx.mississaugatransit.tasks.RouteStopsTask;
import ca.synx.mississaugatransit.tasks.StopsTask;

public class StopsFragment extends Fragment implements IFragment, RouteStopsTask.IRouteStopsTask, StopsTask.IStopsTask, SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {

    private Route mRoute;
    private String mRouteDate;

    private View mView;
    private ProgressDialog mProgressDialog;
    private StopAdapter<Stop> mStopAdapter;
    private ImageView mPreviousImageView;
    private TextView mRouteStopNameTextView;
    private ListView mRouteStopListView;

    private SearchView mSearchView;
    private MenuItem mSearchMenuItem;
    private boolean routeView;

    private RoutesViewFlipperFragment.IRoutesViewFlipperFragment mRoutesViewFlipperFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make sure options menu is shown in action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_stops, container, false);

        mPreviousImageView = (ImageView) mView.findViewById(R.id.previousImageView);
        mRouteStopNameTextView = (TextView) mView.findViewById(R.id.routeStopNameTextView);
        mRouteStopListView = (ListView) mView.findViewById(R.id.routeStopListView);

        mPreviousImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRouteStopNameTextView.setText("");
                mStopAdapter.clear();
                mRoutesViewFlipperFragment.navigateBack();
            }
        });

        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.stops, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        mSearchMenuItem = menu.findItem(R.id.action_stops_search);

        // Set up search.
        mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof RoutesViewFlipperFragment.IRoutesViewFlipperFragment) {
            mRoutesViewFlipperFragment = (RoutesViewFlipperFragment.IRoutesViewFlipperFragment) activity;
            routeView = true;
        } else if (activity instanceof RoutesViewFlipperFragment.IRoutesViewFlipperFragment) {
            mRoutesViewFlipperFragment = (RoutesViewFlipperFragment.IRoutesViewFlipperFragment) activity;
            routeView = true;

        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement RoutesViewFlipperFragment.IRoutesViewFlipperFragment");
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        mStopAdapter.getFilter().filter(s);
        return false;
    }

    @Override
    public void onRouteStopsTaskComplete(List<Stop> stops) {
        mStopAdapter = new StopAdapter<Stop>(getActivity().getApplicationContext(), R.layout.item_stop, stops);
        mRouteStopListView.setAdapter(mStopAdapter);
        mRouteStopListView.setOnItemClickListener(this);
        mProgressDialog.cancel();
    }

    @Override
    public void onStopsTaskComplete(List<Stop> stops) {
        mStopAdapter = new StopAdapter<Stop>(getActivity().getApplicationContext(), R.layout.item_stop, stops);
        mRouteStopListView.setAdapter(mStopAdapter);
        mRouteStopListView.setOnItemClickListener(this);
        mProgressDialog.cancel();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Fetch object from view tag (Route does implement IStop and IFilter).
        Stop stop = (Stop) view.getTag(R.id.object_istop_ifilter);
        mRoutesViewFlipperFragment.onStopSelected(mRoute, stop, mRouteDate);
    }

    @Override
    public void fragmentLoaded(Object... params) {

        // Update internal references.
        mRoute = (Route) params[0];
        mRouteDate = (String) params[1];

        // Update text view that displays what date is being shown.
        mRouteStopNameTextView.setText(

                String.format(getString(R.string.action_displaying_stops),
                        mRoute.getRouteShortName() + " " + mRoute.getRouteLongName(),
                        mRoute.getRouteHeading(),
                        mRouteDate.substring(4, 6) + "/" +
                                mRouteDate.substring(6, 8) + "/" +
                                mRouteDate.substring(0, 4)
                )
        );

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(
                String.format(getString(R.string.action_loading_stops),
                        mRoute.getRouteShortName() + " " + mRoute.getRouteHeading(),
                        mRouteDate.substring(4, 6) + "/" +
                                mRouteDate.substring(6, 8) + "/" +
                                mRouteDate.substring(0, 4)
                )
        );
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);

        new RouteStopsTask(this).execute(mRoute, mRouteDate);
    }

    @Override
    public void fragmentHideMenu() {
        mSearchMenuItem.setVisible(false);
        mSearchView.setVisibility(View.GONE);
    }

    @Override
    public void fragmentShowMenu() {
        mSearchMenuItem.setVisible(true);
        mSearchView.setVisibility(View.VISIBLE);
    }
}