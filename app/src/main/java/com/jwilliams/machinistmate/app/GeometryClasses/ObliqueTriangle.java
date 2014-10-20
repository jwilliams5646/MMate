package com.jwilliams.machinistmate.app.GeometryClasses;

import android.widget.Toast;

/**
 * Created by John on 10/17/2014.
 */
public class ObliqueTriangle {

    private double a;
    private double b;
    private double c;
    private double x;
    private double y;
    private double z;
    private double area;
    private double perimeter;
    private double height;

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(double perimeter) {
        this.perimeter = perimeter;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void calcFromABC(){
        x = Math.toDegrees(Math.acos((c * c + b * b - a * a) / (2 * b * c)));
        y = Math.toDegrees(Math.acos((a * a + b * b - c * c) / (2 * a * b)));
        z = 180 - (x + y);
            //setAngleX(x);
            //setAngleY(y);
            //setAngleZ(z);
            //setAreaPeriHeight(a, b, c);
    }

    public boolean isXNaN(){
        String x = Double.toString(this.x);
        return x.equals("NaN");
    }

    public boolean isYNaN(){
        String y = Double.toString(this.y);
        return y.equals("NaN");
    }

    public boolean isZNaN(){
        String z = Double.toString(this.z);
        return z.equals("NaN");
    }
}
