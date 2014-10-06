package com.jwilliams.machinistmate.app.GeometryClasses;

/**
 * Created by john.williams on 10/6/2014.
 */
public class RightTriangle {
    double H;
    double O;
    double A;
    double x;
    double y;
    double area;
    double perimeter;

    public void setH(double h){
       this.H = h;
    }

    public void setO(double o){
        this.O = o;
    }

    public void setA(double a){
        this.A = a;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void calcFromHO(double h, double o){
        this.H = h;
        this.O = o;
        A = Math.sqrt(h * h - o * o);
        x = Math.toDegrees(Math.asin(o / h));
        y= 90 - x;
        setArea();
        setPerimeter();
    }

    public void calcFromHA(double h, double a){
        this.H = h;
        this.A = a;
        O = Math.sqrt(h * h - a * a);
        x = Math.toDegrees(Math.acos(a / h));
        y = 90 - x;
        setArea();
        setPerimeter();
    }

    private void setArea(){
        area = (O+A)/2;
    }

    private void setPerimeter(){
        perimeter = H+O+A;
    }

    public double getX(double y){
        return y - 90;
    }

    public double getY(double x){
        return x - 90;
    }
}
