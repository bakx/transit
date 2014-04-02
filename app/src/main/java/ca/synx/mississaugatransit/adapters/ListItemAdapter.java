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
import ca.synx.mississaugatransit.interfaces.IListItem;


public class ListItemAdapter<T extends IListItem> extends ArrayAdapter<T> {

    private Context mContext;
    private int mResourceId;
    private List<T> mList;

    public ListItemAdapter(Context context, int resourceId, List<T> list) {
        super(context, resourceId, list);

        this.mContext = context;
        this.mResourceId = resourceId;
        this.mList = list;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View v = view;
        Holder holder = new Holder();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(mResourceId, null);

            holder.mText = (TextView) v.findViewById(R.id.textView);
            holder.mImage = (ImageView) v.findViewById(R.id.imageView);

            // Cache holder for performance reasons.
            v.setTag(holder);
        } else {
            // Retrieve holder from Cache.
            holder = (Holder) v.getTag();
        }

        // Get position of list item.
        T t = mList.get(position);

        // Update titles of the view item.
        holder.mText.setText(t.getText());
        holder.mImage.setImageResource(t.getImageResource());

        return v;
    }

    private static class Holder {
        public TextView mText;
        public ImageView mImage;
    }
}