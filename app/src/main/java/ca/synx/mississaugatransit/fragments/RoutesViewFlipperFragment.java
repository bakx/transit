package ca.synx.mississaugatransit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import ca.synx.mississaugatransit.app.R;
import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.models.Stop;

public class RoutesViewFlipperFragment extends Fragment {

    private View mView;
    private ViewFlipper mViewFlipper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.routes_view_flipper, container, false);
        mViewFlipper = (ViewFlipper) mView.findViewById(R.id.routesViewFlipper);

        return mView;
    }

    public void showNext() {
        mViewFlipper.setInAnimation(getActivity().getApplicationContext(), R.anim.transition_in);
        mViewFlipper.setOutAnimation(getActivity().getApplicationContext(), R.anim.transition_out);
        mViewFlipper.showNext();
    }

    public void showPrevious() {
        mViewFlipper.showPrevious();
    }

    public interface IRoutesViewFlipperFragmentCallbacks {
        public void onStopSelected(Stop stop);

        public void onRouteSelected(Route route);


    }
}