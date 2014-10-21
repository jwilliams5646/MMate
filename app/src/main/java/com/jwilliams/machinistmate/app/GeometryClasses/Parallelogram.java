package com.jwilliams.machinistmate.app.GeometryClasses;

import android.widget.EditText;
import android.widget.Toast;

import com.jwilliams.machinistmate.app.Formatter;

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

    public void setY(double y) {
        this.y = y;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setX(double x) {
        this.x = x;
    }

    public boolean calcY(EditText input1){
        try{
            this.y = 180 - Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    public boolean calcX(EditText input1){
        try{
            this.x = 180 - Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    public boolean calcPerimeter(EditText input1, EditText input2){
        double a;
        double b;
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
        this.perimeter = 2*(a+b);
        return true;
    }

    public boolean calcSide(EditText input1, EditText input2){
        double p;
        double b;
        try{
            p = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            return false;
        }

        try{
            b = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            return false;
        }
        this.a = (p/2)-b;
        return true;
    }

    public boolean calcHeight(EditText input1, EditText input2){
        double a;
        double b;
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

}
