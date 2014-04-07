package ca.synx.mississaugatransit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ca.synx.mississaugatransit.app.R;
import ca.synx.mississaugatransit.models.Route;

public class RouteAdapter<T extends Route> extends ArrayAdapter<T> {
    private Context mContext;
    private int mResourceId;
    private List<T> mList;

    public RouteAdapter(Context context, int resourceId, List<T> list) {
        super(context, resourceId, list);

        this.mContext = context;
        this.mResourceId = resourceId;
        this.mList = list;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mResourceId, null);

            viewHolder.numberTextView = (TextView) view.findViewById(R.id.numberTextView);
            viewHolder.nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            viewHolder.headingTextView = (TextView) view.findViewById(R.id.headingTextView);
            viewHolder.nextImageView = (ImageView) view.findViewById(R.id.nextImageView);

            // Cache holder for performance reasons.
            view.setTag(viewHolder);
        } else {
            // Retrieve holder from Cache.
            viewHolder = (ViewHolder) view.getTag();
        }

        // Get object of list item.
        T t = mList.get(position);

        // Update titles of the view item.
        viewHolder.numberTextView.setText(t.getRouteNumber());
        viewHolder.nameTextView.setText(t.getRouteName());
        viewHolder.headingTextView.setText(t.getRouteHeading());
        viewHolder.nextImageView.setImageResource(t.getListItemImageResource());

        return view;
    }

    private static class ViewHolder {
        public TextView numberTextView;
        public TextView nameTextView;
        public TextView headingTextView;
        public ImageView nextImageView;
    }
}