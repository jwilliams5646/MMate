package com.jwilliams.machinistmate.app.GeometryClasses;

import android.widget.EditText;

/**
 * Created by john.williams on 10/21/2014.
 */
public class Parallelogram {

    private double a;
    private double b;
    private double h;
    private double x;
    private double y;
    private double perimeter;
    private double area;

    public double getPerimeter() {
        return perimeter;
    }

    public double getArea() {
        return area;
    }

    public double getY() {
        return y;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getH() {
        return h;
    }

    public double getX() {
        return x;
    }

    public boolean calcY(EditText input1){
        try{
            y = 180 - Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    public boolean calcX(EditText input1){
        try{
            x = 180 - Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    public boolean calcPerimeter(EditText input1, EditText input2){
        try{
            a = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            return false;
        }

        try{
            b = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            return false;
        }
        perimeter = 2*(a+b);
        return true;
    }

    public boolean calcSide(EditText input1, EditText input2){
        try{
            perimeter = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            return false;
        }

        try{
            b = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            return false;
        }
        this.a = (perimeter/2)-b;
        return true;
    }

    public boolean calcHeight(EditText input1, EditText input2){
        try{
            a = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            return false;
        }

        try{
            b = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            return false;
        }
        this.h = a/b;
        return true;
    }

    public boolean calcBase(EditText input1, EditText input2){
        try{
            a = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            return false;
        }

        try{
            h = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            return false;
        }
        b = a/h;
        return true;
    }

    public boolean calcArea(EditText input1, EditText input2){
        try{
            b = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            return false;
        }

        try{
            h = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            return false;
        }
        area = b*h;
        return true;
    }
}
