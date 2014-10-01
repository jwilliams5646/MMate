package com.jwilliams.machinistmate.app.GeometryClasses;

/**
 * Created by john.williams on 9/10/2014.
 */
public class Square {

    private double input;

    public Square(double input){
        this.input = input;
    }

    public double calcPerimeter() {
        return input*4;
    }

    public double calcSideByPerimeter() {
        return input/4;
    }

    public double calcSideByDiagonal() {
        return Math.sqrt(2)*(input/2);
    }

    public double calcSideByArea() {
        return Math.sqrt(input);
    }

    public double calcDiagonal() {
        return Math.sqrt(2)*input;
    }

    public double calcArea() {
        return input*input;
    }
}
