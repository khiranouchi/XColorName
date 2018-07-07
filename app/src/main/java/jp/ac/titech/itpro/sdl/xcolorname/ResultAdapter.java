package jp.ac.titech.itpro.sdl.xcolorname;

import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Array adapter used in {@linkplain EditActivity}.
 */
public class ResultAdapter extends ArrayAdapter<ResultViewItem> {
    private final static String TAG = "ResultAdapter";

    private int mResource;
    private List<ResultViewItem> mItems;
    private LayoutInflater mInflater;

    public ResultAdapter(Context context, int resource, List<ResultViewItem> items){
        super(context, resource, items);
        mResource = resource;
        mItems = items;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.d(TAG, "getView");

        View view;
        if(convertView != null){
            view = convertView;
        }else{
            view = mInflater.inflate(mResource, null);
        }

        ResultViewItem item = mItems.get(position);

        View colorView = view.findViewById(R.id.color_view);
        TextView colorName = view.findViewById(R.id.color_name);
        TextView colorRgb = view.findViewById(R.id.color_rgb);

        colorView.setBackgroundColor(item.getColor());
        colorName.setText(item.getColorName());
        colorRgb.setText(item.getColorRgb());

        return view;
    }
}
