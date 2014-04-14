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
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ca.synx.mississaugatransit.adapters.StopAdapter;
import ca.synx.mississaugatransit.app.R;
import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.models.Stop;
import ca.synx.mississaugatransit.tasks.RouteStopsTask;
import ca.synx.mississaugatransit.util.GTFS;

public class StopsFragment extends Fragment implements RouteStopsTask.IRouteStopsTask, SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {

    private Route mRoute;
    private View mView;

    private ProgressDialog mProgressDialog;

    private StopAdapter<Stop> mStopAdapter;
    private TextView mRouteStopNameTextView;
    private ListView mRouteStopListView;
    private String mRouteDate;

    private SearchView mSearchView;
    private MenuItem mSearchMenuItem;

    private RoutesViewFlipperFragment.IRoutesViewFlipperFragmentCallbacks mRoutesViewFlipperFragmentCallbacks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init variables.
        mRouteDate = GTFS.getServiceTimeStamp();

        // Make sure options menu is shown in action bar.
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_stops, container, false);
        mRouteStopNameTextView = (TextView) mView.findViewById(R.id.routeStopNameTextView);
        mRouteStopListView = (ListView) mView.findViewById(R.id.routeStopListView);

        return mView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        mSearchMenuItem = menu.findItem(R.id.action_search);

        // Set up search.
        mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.routes, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadStopData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof RoutesViewFlipperFragment.IRoutesViewFlipperFragmentCallbacks) {
            mRoutesViewFlipperFragmentCallbacks = (RoutesViewFlipperFragment.IRoutesViewFlipperFragmentCallbacks) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement RoutesViewFlipperFragment.IRoutesViewFlipperFragmentCallbacks");
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

    protected void loadStopData() {

        // Update text view that displays what date is being shown.
        mRouteStopNameTextView.setText(

                String.format(getString(R.string.action_displaying_routes),
                        mRouteDate.substring(4, 6) + "/" +
                                mRouteDate.substring(6, 8) + "/" +
                                mRouteDate.substring(0, 4)
                )
        );

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(
                String.format(getString(R.string.action_loading_routes),
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
    public void onRouteStopsTaskComplete(List<Stop> stops) {
        mStopAdapter = new StopAdapter<Stop>(getActivity().getApplicationContext(), R.layout.item_stop, stops);
        mRouteStopListView.setAdapter(mStopAdapter);
        mRouteStopListView.setOnItemClickListener(this);
        mProgressDialog.cancel();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Fetch object from view tag (Route does implement IStop and IFilter).
        Stop stop = (Stop) view.getTag(R.id.object_istop_ifilter);
        mRoutesViewFlipperFragmentCallbacks.onStopSelected(stop);
    }
}
