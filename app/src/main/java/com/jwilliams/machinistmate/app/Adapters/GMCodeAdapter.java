package com.jwilliams.machinistmate.app.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jwilliams.machinistmate.app.R;

import java.util.ArrayList;

/**
 * Created by John on 6/6/2014.
 */
public class GMCodeAdapter extends BaseAdapter {

    private ArrayList<GMCodeContent> _data = null;
    Context _c;

    public GMCodeAdapter(ArrayList<GMCodeContent> data, Activity c){
        _data = data;
        _c = c;
    }

    public int getCount() {
        return _data.size();
    }

    public Object getItem(int position) {
        return _data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d("WTF", Integer.toString(position));
        View v = convertView;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater)_c.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.gm_codes_layout, null);
        }

        TextView code = (TextView)v.findViewById(R.id.codes_code);
        TextView desc = (TextView)v.findViewById(R.id.codes_desc);
        TextView mill = (TextView)v.findViewById(R.id.codes_mill);
        TextView turn = (TextView)v.findViewById(R.id.codes_turn);

        GMCodeContent content = _data.get(position);
        Log.d("WTF", Integer.toString(position));
        code.setText(content.code);
        desc.setText(content.desc);
        mill.setText(content.mill);
        turn.setText(content.turn);

        return v;
    }
}
