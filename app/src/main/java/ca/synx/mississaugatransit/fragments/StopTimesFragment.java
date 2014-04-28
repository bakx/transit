package ca.synx.mississaugatransit.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ca.synx.mississaugatransit.adapters.StopTimeAdapter;
import ca.synx.mississaugatransit.app.R;
import ca.synx.mississaugatransit.interfaces.IFragment;
import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.models.Stop;
import ca.synx.mississaugatransit.models.StopTime;
import ca.synx.mississaugatransit.tasks.StopTimesTask;

public class StopTimesFragment extends Fragment implements IFragment, StopTimesTask.IStopTimesTask {

    private Route mRoute;
    private Stop mStop;
    private String mRouteDate;

    private View mView;
    private ProgressDialog mProgressDialog;
    private StopTimeAdapter<StopTime> mStopTimeAdapter;
    private ImageView mPreviousImageView;
    private TextView mRouteStopNameTextView;
    private GridView mRouteStopTimesListView;

    private RoutesViewFlipperFragment.IRoutesViewFlipperFragment mRoutesViewFlipperFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make sure options menu is shown in action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_stop_times, container, false);

        mPreviousImageView = (ImageView) mView.findViewById(R.id.previousImageView);
        mRouteStopNameTextView = (TextView) mView.findViewById(R.id.routeStopNameTextView);
        mRouteStopTimesListView = (GridView) mView.findViewById(R.id.routeStopTimesListView);

        mPreviousImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRouteStopNameTextView.setText("");
                mStopTimeAdapter.clear();
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
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement RoutesViewFlipperFragment.IRoutesViewFlipperFragment");
        }
    }

    @Override
    public void onStopTimesTaskComplete(List<StopTime> stopTimes) {
        mStop.setStopTimes(stopTimes);
        mStopTimeAdapter = new StopTimeAdapter<StopTime>(getActivity().getApplicationContext(), R.layout.item_stop_time, stopTimes);
        mRouteStopTimesListView.setAdapter(mStopTimeAdapter);
        mProgressDialog.cancel();
    }

    @Override
    public void fragmentLoaded(Object... params) {

        // Update internal references.
        mRoute = (Route) params[0];
        mStop = (Stop) params[1];
        mRouteDate = (String) params[2];

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

        new StopTimesTask(this).execute(mStop, mRouteDate);
    }

    @Override
    public void fragmentHideMenu() {
    }

    @Override
    public void fragmentShowMenu() {
    }

}