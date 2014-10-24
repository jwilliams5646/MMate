package com.jwilliams.machinistmate.app.SpeedsandFeedsClasses;

import android.widget.EditText;

/**
 * Created by john.williams on 9/11/2014.
 */
public class Feeds {

    private int speed;
    private double fpt;
    private int numTeeth;
    private double feed;

    public double getFeedRate(){
        return feed;
    }

    public boolean calcFeed(EditText speedInput, EditText fptInput, EditText ntInput){
        try {
            speed = (Integer.parseInt(speedInput.getText().toString()));
        } catch (NumberFormatException e) {
            return false;
        }
        try {
            fpt = (Double.parseDouble(fptInput.getText().toString()));
        } catch (NumberFormatException e) {
            return false;
        }
        try {
            numTeeth = (Integer.parseInt(ntInput.getText().toString()));
        } catch (NumberFormatException e) {
            return false;
        }

        feed = speed * fpt * numTeeth;
        return true;
    }
}
