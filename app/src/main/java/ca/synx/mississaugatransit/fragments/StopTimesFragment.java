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
import ca.synx.mississaugatransit.tasks.UpcomingStopTimesTask;

public class StopTimesFragment extends Fragment implements IFragment, StopTimesTask.IStopTimesTask, UpcomingStopTimesTask.IUpcomingStopTimesTask {

    private Route mRoute;
    private Stop mStop;
    private String mRouteDate;

    private View mView;
    private ProgressDialog mProgressDialog;
    private StopTimeAdapter<StopTime> mStopTimeAdapter;
    private StopTimeAdapter<StopTime> mUpcomingStopTimeAdapter;
    private ImageView mPreviousImageView;
    private TextView mStopTimeHeaderTextView;

    private GridView mStopTimesUpcomingStopsListView;
    private GridView mStopTimesAllStopsListView;


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
        mStopTimeHeaderTextView = (TextView) mView.findViewById(R.id.stopTimeHeaderTextView);

        mStopTimesUpcomingStopsListView = (GridView) mView.findViewById(R.id.stopTimesUpcomingStopsListView);
        mStopTimesAllStopsListView = (GridView) mView.findViewById(R.id.stopTimesAllStopsListView);

        mPreviousImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStopTimeHeaderTextView.setText("");
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
        mStopTimesAllStopsListView.setAdapter(mStopTimeAdapter);
        mProgressDialog.cancel();

        // Calculate upcoming stop times.
        new UpcomingStopTimesTask(getActivity().getApplicationContext(), this, 3).execute(stopTimes);
    }

    @Override
    public void onUpcomingStopTimesTaskComplete(List<StopTime> stopTimes) {
        mStop.setNextStopTimes(stopTimes);
        mUpcomingStopTimeAdapter = new StopTimeAdapter<StopTime>(getActivity().getApplicationContext(), R.layout.item_stop_time, stopTimes);
        mStopTimesUpcomingStopsListView.setAdapter(mUpcomingStopTimeAdapter);
    }

    @Override
    public void fragmentLoaded(Object... params) {

        // Update internal references.
        mRoute = (Route) params[0];
        mStop = (Stop) params[1];
        mRouteDate = (String) params[2];

        // Update text view that displays what date is being shown.
        mStopTimeHeaderTextView.setText(

                String.format(getString(R.string.action_displaying_stoptimes),
                        mRoute.getRouteShortName() + " " + mRoute.getRouteLongName() + " " + mRoute.getRouteHeading(),
                        mRouteDate.substring(4, 6) + "/" +
                                mRouteDate.substring(6, 8) + "/" +
                                mRouteDate.substring(0, 4),
                        mStop.getStopName()
                )
        );

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(
                String.format(getString(R.string.action_loading_stoptimes),
                        mRoute.getRouteShortName() + " " + mRoute.getRouteHeading(),
                        mRouteDate.substring(4, 6) + "/" +
                                mRouteDate.substring(6, 8) + "/" +
                                mRouteDate.substring(0, 4),
                        mStop.getStopName()
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