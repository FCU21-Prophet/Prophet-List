package team.prophet.a3104.prophet_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 2016/6/19.
 */
public class PhListAdapter extends ArrayAdapter<PhList>
{
    Context context = null;
    public PhListAdapter(Context context, ArrayList<PhList> item)
    {
        super(context,0,item);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout itemlayout = null;

        if(convertView == null)
        {
            itemlayout = (LinearLayout)inflater.inflate(R.layout.list_view,null);
        }
        else
        {
            itemlayout = (LinearLayout)convertView;
        }
        PhList item = getItem(position);

        TextView title = (TextView)itemlayout.findViewById(R.id.show_title);
        title.setText(item.getListTitle().toString());

        TextView tag = (TextView)itemlayout.findViewById(R.id.show_tag);
        tag.setText(item.getTag().toString());

        TextView date = (TextView)itemlayout.findViewById(R.id.show_date);
        date.setText(item.getDate().toString());

        TextView time = (TextView)itemlayout.findViewById(R.id.show_time);
        time.setText(item.getTime().toString());

        TextView content = (TextView)itemlayout.findViewById(R.id.show_content);
        content.setText(item.getListContent().toString());


        return itemlayout;

    }
}
