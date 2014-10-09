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
    int xPos;
    int yPos;

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

    public void setXPos(int xPos){
        this.xPos = xPos;
    }

    public void setYPos(int yPos){
        this.yPos = yPos;
    }

    public double getArea(){
        return (O+A)/2;
    }

    public double setPerimeter(){
        return H+O+A;
    }

    public double getRadians(double value){
        return Math.toRadians(value);
    }

    public double getDegrees(double value){
        return Math.toDegrees(value);
    }

    public double getY(){
        return y;
    }

    public void calcFromHO(){
        A = Math.sqrt(H * H - O * O);
        x = Math.toDegrees(Math.asin(O / H));
        y = 90 - x;
        if(xPos == 1){
            x = getRadians(x);
        }
        if(yPos == 1){
            y = getRadians(y);
        }
    }

    public void calcFromHA(){
        O = Math.sqrt(H * H - A * A);
        x = Math.toDegrees(Math.acos(A / H));
        y = 90 - x;
        if(xPos == 1){
            x = getRadians(x);
        }
        if(yPos == 1){
            y = getRadians(y);
        }
    }

    public void calcFromHX(){
        if(xPos == 1){
            y = 90 - Math.toDegrees(x);
            O = H* Math.sin(x);
            A = H* Math.cos(x);
        }else {
            y = 90 - x;
            O = H* Math.sin(Math.toRadians(x));
            A = H* Math.cos(Math.toRadians(x));
        }
        if(yPos == 1){
            y = getRadians(y);
        }
    }

    public void calcFromHY(){
        if(yPos == 1){
            x = 90 - Math.toDegrees(y);
        }else{
            x = 90 - y;
        }
        O = H* Math.sin(Math.toRadians(x));
        A = H* Math.cos(Math.toRadians(x));
        if(xPos == 1){
            x = getRadians(x);
        }
    }

    public void calcFromOA(){
        H = Math.sqrt(O * O + A * A);
        x = Math.toDegrees(Math.atan(O / A));
        y = 90 - x;
        if(xPos == 1){
            x = getRadians(x);
        }
        if(yPos == 1){
            y = getRadians(y);
        }
    }

    public void calcFromOX(){
        if(xPos == 1){
            y = 90 - Math.toDegrees(x);
            H = A/ Math.sin(Math.toRadians(y));
            A = H* Math.cos(x);
        }else{
            y = 90 - x;
            H = A/ Math.sin(Math.toRadians(y));
            A = H* Math.cos(Math.toRadians(x));
        }
        if(yPos == 1){
            y = getRadians(y);
        }
    }

    public void calcFromOY(){
        if(yPos == 1){
            x = 90 - Math.toDegrees(y);
            H = A/ Math.sin(y);
        }else{
            x = 90 - y;
            H = A/ Math.sin(Math.toRadians(y));
        }
        A = H* Math.cos(Math.toRadians(x));
        if(xPos == 1){
            x = getRadians(x);
        }
    }

    public void calcFromAX(){
        if(xPos == 1){
            y = 90 - Math.toDegrees(x);
        }else{
            y = 90 - x;
        }
        H = A/ Math.sin(Math.toRadians(y));
        O = H* Math.sin(Math.toRadians(x));
        if(yPos == 1){
            y = getRadians(y);
        }
    }
}
