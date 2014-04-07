package ca.synx.mississaugatransit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.List;

import ca.synx.mississaugatransit.app.R;
import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.tasks.RoutesTask;

public class MapsFragment extends SupportMapFragment implements RoutesTask.IRoutesTask {
    private SupportMapFragment mSupportMapFragment;
    private GoogleMap mGoogleMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        mSupportMapFragment = this;
        mGoogleMap = mSupportMapFragment.getMap();
    }

    @Override
    public void onResume() {
        super.onResume();

        mSupportMapFragment = this;

        if (mGoogleMap == null) {
            mGoogleMap = mSupportMapFragment.getMap();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onRoutesTaskComplete(List<Route> routes) {

    }
}
