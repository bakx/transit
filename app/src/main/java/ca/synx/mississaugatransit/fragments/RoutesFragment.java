package ca.synx.mississaugatransit.fragments;

import android.app.Activity;
import android.app.Dialog;
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
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ca.synx.mississaugatransit.adapters.RouteAdapter;
import ca.synx.mississaugatransit.app.R;
import ca.synx.mississaugatransit.interfaces.IFragment;
import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.tasks.RoutesTask;
import ca.synx.mississaugatransit.util.GTFS;

public class RoutesFragment extends Fragment implements IFragment, RoutesTask.IRoutesTask, CalendarView.OnDateChangeListener, SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {

    private View mView;

    private ProgressDialog mProgressDialog;
    private Dialog mCalendarDialog;

    private RouteAdapter<Route> mRoutesAdapter;
    private TextView mRouteDateTextView;
    private ListView mRoutesListView;
    private String mRouteDate;

    private SearchView mSearchView;
    private MenuItem mSearchMenuItem;
    private MenuItem mCalendarMenuItem;

    private RoutesViewFlipperFragment.IRoutesViewFlipperFragment mRoutesViewFlipperFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init variables.
        mRouteDate = GTFS.getServiceTimeStamp();

        // Make sure options menu is shown in action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_routes, container, false);
        mRouteDateTextView = (TextView) mView.findViewById(R.id.routeDateTextView);
        mRoutesListView = (ListView) mView.findViewById(R.id.routeListView);

        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.routes, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        mSearchMenuItem = menu.findItem(R.id.action_routes_search);
        mCalendarMenuItem = menu.findItem(R.id.action_routes_calendar_time);

        // Set up search.
        mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_routes_calendar_time) {

            if (mCalendarDialog != null)
                mCalendarDialog.show();
            else {
                mCalendarDialog = new Dialog(getActivity());
                mCalendarDialog.setContentView(R.layout.calendar_view);
                mCalendarDialog.setTitle(getString(R.string.action_change_route_date));

                CalendarView calendarView = (CalendarView) mCalendarDialog.findViewById(R.id.calendarView);
                calendarView.setOnDateChangeListener(this);

                mCalendarDialog.show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadRouteData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof RoutesViewFlipperFragment.IRoutesViewFlipperFragment) {
            mRoutesViewFlipperFragment = (RoutesViewFlipperFragment.IRoutesViewFlipperFragment) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement RoutesViewFlipperFragment.IRoutesViewFlipperFragment");
        }
    }

    @Override
    public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {

        // Update mRouteDate to contain the value of the selected date.
        mRouteDate = String.valueOf(year) +
                String.format("%02d", month + 1) +
                String.format("%02d", day);

        // Cancel out dialog.
        mCalendarDialog.cancel();

        // Load Routes.
        loadRouteData();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        mRoutesAdapter.getFilter().filter(s);
        return false;
    }

    protected void loadRouteData() {

        // Update text view that displays what date is being shown.
        mRouteDateTextView.setText(
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

        new RoutesTask(this).execute(mRouteDate);
    }

    @Override
    public void onRoutesTaskComplete(List<Route> routes) {
        mRoutesAdapter = new RouteAdapter<Route>(getActivity().getApplicationContext(), R.layout.item_route, routes);
        mRoutesListView.setAdapter(mRoutesAdapter);
        mRoutesListView.setOnItemClickListener(this);
        mProgressDialog.cancel();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Fetch object from view tag (Route does implement IRoute and IFilter).
        Route route = (Route) view.getTag(R.id.object_iroute_ifilter);
        mRoutesViewFlipperFragment.onRouteSelected(route, mRouteDate);
    }

    @Override
    public void fragmentLoaded(Object... params) {
    }

    @Override
    public void fragmentHideMenu() {
        mSearchMenuItem.setVisible(false);
        mCalendarMenuItem.setVisible(false);
        mSearchView.setVisibility(View.GONE);

    }

    @Override
    public void fragmentShowMenu() {
        mSearchMenuItem.setVisible(true);
        mCalendarMenuItem.setVisible(true);
        mSearchView.setVisibility(0);

    }
}