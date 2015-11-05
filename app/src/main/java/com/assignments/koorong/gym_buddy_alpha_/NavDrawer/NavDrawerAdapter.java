package com.assignments.koorong.gym_buddy_alpha_.NavDrawer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.assignments.koorong.gym_buddy_alpha_.R;

import java.util.ArrayList;

/**
 * Created by Peter on 04/11/2015.
 */
public class NavDrawerAdapter extends ArrayAdapter<NavDrawerItem>{
    private Context context;
    private final int layouResourceId;
    private ArrayList<NavDrawerItem> data = null;

    public NavDrawerAdapter(Context context, int layoutResourceId, ArrayList<NavDrawerItem> data){
        super(context, layoutResourceId, data);
        this.context = context;
        this.layouResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layouResourceId, parent, false);
        }

        ImageView itemIcon = (ImageView)convertView.findViewById(R.id.navItemIcon);
        TextView itemHeading = (TextView)convertView.findViewById(R.id.navItemHeading);
        TextView itemSub = (TextView)convertView.findViewById(R.id.navItemSub);

        NavDrawerItem choice = data.get(position);

        itemIcon.setImageResource(choice.icon);
        itemHeading.setText(choice.heading);
        itemSub.setText(choice.subheading);


        return convertView;
    }
}
