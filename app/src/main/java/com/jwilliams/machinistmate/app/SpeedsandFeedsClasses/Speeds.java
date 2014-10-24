package com.jwilliams.machinistmate.app.SpeedsandFeedsClasses;

import android.widget.EditText;

/**
 * Created by john.williams on 9/11/2014.
 */
public class Speeds {

    private double surface;
    private double diameter;
    private double speed;

    public double getSpeed(){
        return speed;
    }

    public boolean calcSpeed(EditText surfaceInput, EditText diameterInput, boolean isStandard){
        try {
            surface = (Double.parseDouble(surfaceInput.getText().toString()));
        } catch (NumberFormatException e) {
            surfaceInput.setHint("Invalid");
            return false;
        }

        try {
            diameter = (Double.parseDouble(diameterInput.getText().toString()));
        } catch (NumberFormatException e) {
            diameterInput.setHint("Invalid");
            return false;
        }

        if (isStandard) {
            speed = (surface * 3.82) / diameter;
        } else {
            speed = (surface * 320) / diameter;
        }

        return true;
    }
}
