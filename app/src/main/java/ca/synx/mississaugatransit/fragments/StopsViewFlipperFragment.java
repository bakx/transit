package ca.synx.mississaugatransit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ViewFlipper;

import ca.synx.mississaugatransit.app.R;
import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.models.Stop;

public class StopsViewFlipperFragment extends Fragment implements Animation.AnimationListener {

    private static View mView;
    private Route mRoute;
    private Stop mStop;

    private ViewFlipper mViewFlipper;
    private StopsFragment mStopsFragment;
    private StopTimesFragment mStopTimesFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView != null && mView.getParent() != null) {
            ((ViewGroup) mView.getParent()).removeView(mView);
        } else {
            mView = inflater.inflate(R.layout.stops_view_flipper, container, false);
        }

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();

        mViewFlipper = (ViewFlipper) mView.findViewById(R.id.stopsViewFlipperViewFlipper);
        mStopsFragment = (StopsFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.routesViewFlipperStopsFragment);
    }

    public void showPrevious() {
        mViewFlipper.setInAnimation(getActivity().getApplicationContext(), R.anim.right_in);
        mViewFlipper.setOutAnimation(getActivity().getApplicationContext(), R.anim.right_out);
        mViewFlipper.showPrevious();

        // Update menu and title items.
        updateActionBar();
    }

    public void showNext(final Stop stop) {
        mViewFlipper.setInAnimation(getActivity().getApplicationContext(), R.anim.left_in);
        mViewFlipper.setOutAnimation(getActivity().getApplicationContext(), R.anim.left_out);

        // Switch to next view.
        mViewFlipper.getOutAnimation().setAnimationListener(this);
        mViewFlipper.showNext();

        // Assign local variables for use in the animation end listeners.
        mStop = stop;

        // Update menu and title items.
        updateActionBar();
    }

    public void updateActionBar() {

        switch (mViewFlipper.getCurrentView().getId()) {
            case R.id.routesViewFlipperRoutesFragment:

                // Update title.
                getActivity().getActionBar().setTitle(getString(R.string.title_routes));

                mStopsFragment.fragmentHideMenu();
                mStopTimesFragment.fragmentHideMenu();

                break;

            case R.id.routesViewFlipperStopsFragment:

                // Update title.
                getActivity().getActionBar().setTitle(getString(R.string.title_stops));

                mStopsFragment.fragmentShowMenu();
                mStopTimesFragment.fragmentHideMenu();

                break;


            case R.id.routesViewFlipperStopTimesFragment:

                // Update title.
                getActivity().getActionBar().setTitle(getString(R.string.title_stop_times));

                mStopsFragment.fragmentHideMenu();
                mStopTimesFragment.fragmentShowMenu();

                break;
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
        animation.start();
    }

    @Override
    public void onAnimationEnd(Animation animation) {

        switch (mViewFlipper.getCurrentView().getId()) {
            case R.id.routesViewFlipperStopsFragment:
                mStopsFragment.fragmentLoaded();
                break;
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    public interface IRoutesViewFlipperFragment {
        void navigateBack();

        void navigateForward();

        void onRouteSelected(Route route, String routeDate);

        void onStopSelected(Route route, Stop stop, String routeDate);
    }
}