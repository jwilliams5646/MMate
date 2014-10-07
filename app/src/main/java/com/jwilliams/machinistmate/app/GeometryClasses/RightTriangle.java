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

    private void setArea(){
        area = (O+A)/2;
    }

    private void setPerimeter(){
        perimeter = H+O+A;
    }

    public double getRadians(double value){
        return Math.toRadians(value);
    }

    public double getDegrees(double value){
        return Math.toDegrees(value);
    }

    public double getY(double x){
        return x - 90;
    }

    public void calcFromHO(double h, double o, int xPos, int yPos){
        this.H = h;
        this.O = o;
        this.A = Math.sqrt(h * h - o * o);
        x = Math.toDegrees(Math.asin(o / h));
        y = 90 - x;
        if(xPos == 1){
            x = getRadians(x);
        }
        if(yPos == 1){
            y = getRadians(y);
        }
        setArea();
        setPerimeter();
    }

    public void calcFromHA(double h, double a, int xPos, int yPos){
        this.H = h;
        this.A = a;
        O = Math.sqrt(h * h - a * a);
        x = Math.toDegrees(Math.acos(a / h));
        y = 90 - x;
        if(xPos == 1){
            x = getRadians(x);
        }
        if(yPos == 1){
            y = getRadians(y);
        }
        setArea();
        setPerimeter();
    }

    public void calcFromHX(double h, double x, int xPos, int yPos){
        if(xPos == 1){
            x = Math.toDegrees(x);
        }
        this.y = 90 - x;
        if(xPos == 1){
            this.x = getRadians(x);
        }
        if(yPos == 1){
            this.y = getRadians(y);
        }
        O = h* Math.sin(Math.toRadians(x));
        A = h* Math.cos(Math.toRadians(x));
        setArea();
        setPerimeter();
    }

    public void calcFromHY(double h, double x, int xPos, int yPos){
        if(xPos == 1){
            x = Math.toDegrees(x);
        }
        this.y = 90 - x;
        if(xPos == 1){
            this.x = getRadians(x);
        }
        if(yPos == 1){
            this.y = getRadians(y);
        }
        O = h* Math.sin(Math.toRadians(x));
        A = h* Math.cos(Math.toRadians(x));
        setArea();
        setPerimeter();
    }
}
