package ca.synx.mississaugatransit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.synx.mississaugatransit.app.R;
import ca.synx.mississaugatransit.interfaces.IFilter;
import ca.synx.mississaugatransit.interfaces.IStop;

public class StopAdapter<T extends IStop & IFilter> extends ArrayAdapter<T> {
    private Context mContext;
    private int mResourceId;
    private List<T> mList;
    private List<T> mFilteredList;
    private Filter mFilter;

    public StopAdapter(Context context, int resourceId, List<T> list) {
        super(context, resourceId, list);

        this.mContext = context;
        this.mResourceId = resourceId;
        this.mList = list;
        this.mFilteredList = list;
    }

    public Filter getFilter() {
        if (mFilter == null)
            mFilter = new RouteFilter();

        return mFilter;
    }

    @Override
    public int getCount() {
        return mFilteredList.size();
    }

    @Override
    public T getItem(int position) {
        return mFilteredList.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mResourceId, null);

            viewHolder.nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            viewHolder.idTextView = (TextView) view.findViewById(R.id.idTextView);
            viewHolder.nextImageView = (ImageView) view.findViewById(R.id.nextImageView);

            // Cache holder for performance reasons.
            view.setTag(viewHolder);
        } else {
            // Retrieve holder from Cache.
            viewHolder = (ViewHolder) view.getTag();
        }

        // Get object of list item.
        T t = getItem(position);

        // Update titles of the view item.
        viewHolder.nameTextView.setText(t.getStopName());
        viewHolder.idTextView.setText(t.getStopId());
        viewHolder.nextImageView.setImageResource(t.getListItemImageResource());

        // Attach object T to view.
        view.setTag(R.id.object_iroute_ifilter, t);

        return view;
    }

    private static class ViewHolder {
        public TextView nameTextView;
        public TextView idTextView;
        public ImageView nextImageView;
    }

    private class RouteFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            FilterResults filterResults = new FilterResults();

            if (charSequence == null || charSequence.length() == 0) {
                filterResults.values = mList;
                filterResults.count = mList.size();
            } else {
                List<T> filteredList = new ArrayList<T>();

                for (T t : mList) {
                    if (t.getFilterData().toUpperCase()
                            .contains(charSequence.toString().toUpperCase())
                            ) {

                        filteredList.add(t);
                    }
                }

                filterResults.values = filteredList;
                filterResults.count = filteredList.size();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mFilteredList = (ArrayList<T>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}