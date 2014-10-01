package com.jwilliams.machinistmate.app.SpeedsandFeedsClasses;

/**
 * Created by john.williams on 9/11/2014.
 */
public class Speeds {

    private double surface;
    private double diameter;
    private boolean isStandard;

    public Speeds(boolean isStandard){
        this.isStandard = isStandard;
    }

    public void setSurface(double surface){
        this.surface = surface;
    }

    public void setDiameter(double diameter){
        this.diameter = diameter;
    }

    public double getSpeed(){
        if (isStandard) {
            return (surface * 3.82) / diameter;
        } else {
            return (surface * 320) / diameter;
        }
    }
}
