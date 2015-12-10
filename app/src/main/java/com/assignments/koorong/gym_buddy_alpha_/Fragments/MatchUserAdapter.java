package com.assignments.koorong.gym_buddy_alpha_.Fragments;

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
import com.assignments.koorong.gym_buddy_alpha_.User;

import java.util.ArrayList;

/**
 * Created by Peter on 04/11/2015.
 */
public class MatchUserAdapter extends ArrayAdapter<User>{
    private Context context;
    private final int layoutResourceId;
    private ArrayList<User> data = null;

    public MatchUserAdapter(Context context, int layoutResourceId, ArrayList<User> data){
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.txtName);
        TextView email = (TextView)convertView.findViewById(R.id.txtEmail);
        TextView num = (TextView)convertView.findViewById(R.id.txtMatchNum);

        User user = data.get(position);
        String uName = user.getName();

        name.setText(uName);
        email.setText(user.getEmail());
        //num.setText("1");
        num.setText(Integer.toString(data.get(position).getexerciseType()));



        return convertView;
    }
}
