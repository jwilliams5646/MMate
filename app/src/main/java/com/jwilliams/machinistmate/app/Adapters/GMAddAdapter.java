package com.jwilliams.machinistmate.app.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jwilliams.machinistmate.app.R;

import java.util.ArrayList;

/**
 * Created by John on 6/7/2014.
 */
public class GMAddAdapter extends BaseAdapter {

    private ArrayList<GMAddContent> _data;
    Context _c;

    public GMAddAdapter(ArrayList<GMAddContent> data, Activity c){
        _data = data;
        _c = c;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return _data.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return _data.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = convertView;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater)_c.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.gm_address_layout, null);
        }

        TextView code = (TextView)v.findViewById(R.id.add_code);
        TextView desc = (TextView)v.findViewById(R.id.add_desc);

        GMAddContent content = _data.get(position);
        code.setText(content.code);
        desc.setText(content.desc);

        return v;
    }
}
