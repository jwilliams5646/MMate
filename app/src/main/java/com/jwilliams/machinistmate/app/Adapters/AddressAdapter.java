package com.jwilliams.machinistmate.app.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoTextView;
import com.jwilliams.machinistmate.app.R;

import java.util.ArrayList;

/**
 * Created by John Williams
 *
 * This adapter sets the CNC G and M Codes in the GMFragment
 */
public class AddressAdapter extends BaseAdapter {

    private ArrayList<GMAddContent> data;
    Context context;

    public AddressAdapter(ArrayList<GMAddContent> data, Activity context){
        this.data = data;
        this.context = context;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        AddressHolder addressHolder;
        if (convertView == null){
            LayoutInflater vi = (LayoutInflater)context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.gm_address_layout, null);
            addressHolder = new AddressHolder();

            addressHolder.code = (RobotoTextView)convertView.findViewById(R.id.add_code);
            addressHolder.desc = (RobotoTextView)convertView.findViewById(R.id.add_desc);
            convertView.setTag(addressHolder);
        }else{
            addressHolder = (AddressHolder)convertView.getTag();
        }

        GMAddContent content = data.get(position);
        addressHolder.code.setText(content.getCode());
        addressHolder.desc.setText(content.getDesc());

/*        Animation toList = AnimationUtils.loadAnimation(context, R.anim.add_to_list);
        convertView.startAnimation(toList);*/

        return convertView;
    }

    private class AddressHolder{
        RobotoTextView code;
        RobotoTextView desc;
    }
}
