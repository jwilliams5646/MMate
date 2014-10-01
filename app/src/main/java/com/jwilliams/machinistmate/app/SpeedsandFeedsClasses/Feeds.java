package com.jwilliams.machinistmate.app.SpeedsandFeedsClasses;

/**
 * Created by john.williams on 9/11/2014.
 */
public class Feeds {

    private int speed = 0;
    private double fpt = 0.0;
    private int numTeeth = 0;

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public void setFpt(double fpt){
        this.fpt = fpt;
    }

    public void setNumTeeth(int numTeeth){
        this.numTeeth = numTeeth;
    }

    public double getFeedRate(){
        return speed * fpt * numTeeth;
    }
}
