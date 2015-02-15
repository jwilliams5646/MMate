package com.jwilliams.machinistmate.app.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoTextView;
import com.jwilliams.machinistmate.app.R;

import java.util.ArrayList;

/**
 * Created by John Williams
 *
 * This adapter sets the Address Codes in the GMFragment
 */
public class CodeAdapter extends BaseAdapter {

    private ArrayList<GMCodeContent> data = null;
    Context context;

    public CodeAdapter(ArrayList<GMCodeContent> data, Activity context){
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
        CodeHolder codeHolder;
        if (convertView == null){
            LayoutInflater vi = (LayoutInflater)context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.gm_codes_layout, null);
            codeHolder = new CodeHolder();
            codeHolder.code = (RobotoTextView)convertView.findViewById(R.id.codes_code);
            codeHolder.desc = (RobotoTextView)convertView.findViewById(R.id.codes_desc);
            codeHolder.mill = (RobotoTextView)convertView.findViewById(R.id.codes_mill);
            convertView.setTag(codeHolder);
        }else{
            codeHolder = (CodeHolder)convertView.getTag();
        }

        GMCodeContent content = data.get(position);
        codeHolder.code.setText(content.getCode());
        codeHolder.desc.setText(content.getDesc());
        if(content.getMill() != null && content.getTurn() != null){
            codeHolder.mill.setText("both");
        }
        if(content.getMill() != null && content.getTurn() == null){
            codeHolder.mill.setText(content.getMill());
        }
        if(content.getMill() == null && content.getTurn() != null){
            codeHolder.mill.setText(content.getTurn());
        }


/*        Animation toList = AnimationUtils.loadAnimation(context, R.anim.add_to_list);
        convertView.startAnimation(toList);*/
        return convertView;
    }
    private class CodeHolder{
        RobotoTextView code;
        RobotoTextView desc;
        RobotoTextView mill;
    }
}
