package ca.synx.mississaugatransit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ca.synx.mississaugatransit.adapters.RouteAdapter;
import ca.synx.mississaugatransit.app.R;
import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.tasks.RoutesTask;

public class RoutesFragment extends Fragment implements RoutesTask.IRoutesTask {

    private View mView;

    private RouteAdapter<Route> mRoutesAdapter;
    private ListView mRoutesListView;

    public static RoutesFragment newInstance(int index) {
        RoutesFragment routesFragment = new RoutesFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        routesFragment.setArguments(args);
        return routesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_listview, container, false);
        mRoutesListView = (ListView) mView.findViewById(R.id.listView);

        new RoutesTask(this, null).execute();

        return mView;
    }

    @Override
    public void onRoutesTaskComplete(List<Route> routes) {
        mRoutesAdapter = new RouteAdapter<Route>(getActivity().getApplicationContext(), R.layout.item_route, routes);
        mRoutesListView.setAdapter(mRoutesAdapter);
        mRoutesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Get tag from clicked view.
                //   Route route = (Route) view.getTag(R.id.tag_id_2);

                // Create new intent.
                //  Intent intent = new Intent(mContext, StopsActivity.class);

                // Pass selected data.
                //  intent.putExtra("routeData", route);

                // Start the intent.
                //  startActivity(intent);
            }
        });

    }
}
