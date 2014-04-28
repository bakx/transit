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
import ca.synx.mississaugatransit.interfaces.IStopItem;

public class StopTimeAdapter<T extends IStopItem> extends ArrayAdapter<T> {
    private Context mContext;
    private int mResourceId;
    private List<T> mList;

    public StopTimeAdapter(Context context, int resourceId, List<T> list) {
        super(context, resourceId, list);

        this.mContext = context;
        this.mResourceId = resourceId;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mResourceId, null);

            viewHolder.nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            viewHolder.stopTimeTextView = (TextView) view.findViewById(R.id.stopTimeTextView);
            viewHolder.nextStopTimeTextView = (TextView) view.findViewById(R.id.nextStopTimeTextView);
            viewHolder.locationTypeImageView = (ImageView) view.findViewById(R.id.locationTypeImageView);

            // Cache holder for performance reasons.
            view.setTag(viewHolder);
        } else {
            // Retrieve holder from Cache.
            viewHolder = (ViewHolder) view.getTag();
        }

        // Get object of list item.
        T t = getItem(position);

        // Update titles of the view item.
        viewHolder.stopTimeTextView.setText(t.getArrivalTime());
        viewHolder.nextStopTimeTextView.setText(t.getDepartureTime());
        //viewHolder.locationTypeImageView.setImageResource(GTFS.getLocationTypeImage(t..(), Theme.IconType.DARK));

        // Attach object T to view.
        view.setTag(R.id.object_iroute_ifilter, t);

        return view;
    }

    private static class ViewHolder {
        public TextView nameTextView;
        public TextView stopTimeTextView;
        public TextView nextStopTimeTextView;
        public ImageView locationTypeImageView;
    }
}