package com.jwilliams.machinistmate.app.GeometryClasses;



/**
 * Created by john.williams on 9/10/2014.
 */
public class Circle {

    private double input;

    public Circle(double input){
        this.input = input;
    }

    public double calcRadCirc() {
        return input/(2* Math.PI);
    }

    public double calcRadArea() {
        return Math.sqrt(input / Math.PI);
    }

    public double calcRadDiam() {
        return input/2;
    }

    public double calcCircumference() {
        return 2* Math.PI*input;
    }

    public double calcArea() {
        return Math.PI* Math.pow(input, 2);
    }

    public double calcDiameter() {
        return 2*input;
    }
}
