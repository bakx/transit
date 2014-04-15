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

public class RoutesViewFlipperFragment extends Fragment implements Animation.AnimationListener {

    private Route mRoute;
    private String mRouteDate;

    private View mView;
    private ViewFlipper mViewFlipper;

    private RoutesFragment mRouteFragment;
    private StopsFragment mStopFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.routes_view_flipper, container, false);
        mViewFlipper = (ViewFlipper) mView.findViewById(R.id.routesViewFlipper);

        mRouteFragment = (RoutesFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.routeFragment);
        mStopFragment = (StopsFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.stopFragment);

        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getActivity().getSupportFragmentManager().beginTransaction().remove(mRouteFragment).commit();
        getActivity().getSupportFragmentManager().beginTransaction().remove(mStopFragment).commit();
    }

    public void showPrevious() {
        mViewFlipper.setInAnimation(getActivity().getApplicationContext(), R.anim.right_in);
        mViewFlipper.setOutAnimation(getActivity().getApplicationContext(), R.anim.right_out);
        mViewFlipper.showPrevious();

        // Update menu and title items.
        updateActionBar();
    }

    public void showNext(final Route route, final String routeDate) {
        mViewFlipper.setInAnimation(getActivity().getApplicationContext(), R.anim.left_in);
        mViewFlipper.setOutAnimation(getActivity().getApplicationContext(), R.anim.left_out);

        // Switch to next view.
        mViewFlipper.showNext();

        //
        mViewFlipper.getOutAnimation().setAnimationListener(this);


        // Assign local variables for use in the animation end listeners.
        mRoute = route;
        mRouteDate = routeDate;

        // Update menu and title items.
        updateActionBar();
    }


    public void updateActionBar() {

        switch (mViewFlipper.getCurrentView().getId()) {
            case R.id.routeFragment:

                // Update title.
                getActivity().getActionBar().setTitle(getString(R.string.title_routes));

                mRouteFragment.fragmentShowMenu();
                mStopFragment.fragmentHideMenu();

                break;

            case R.id.stopFragment:

                // Update title.
                getActivity().getActionBar().setTitle(getString(R.string.title_stops));

                mRouteFragment.fragmentHideMenu();
                mStopFragment.fragmentShowMenu();

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
            case R.id.routeFragment:
                mRouteFragment.fragmentLoaded();
                break;

            case R.id.stopFragment:
                mStopFragment.fragmentLoaded(mRoute, mRouteDate);
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