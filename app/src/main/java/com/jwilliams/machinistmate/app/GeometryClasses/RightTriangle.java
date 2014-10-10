package com.jwilliams.machinistmate.app.GeometryClasses;

/**
 * Created by John Williams
 * This class performs calculations for the Right Triangle Fragment.
 */
public class RightTriangle {
    double H;
    double O;
    double A;
    double x;
    double y;
    int xPos;
    int yPos;

    public void setH(double h) {
        this.H = h;
    }

    public void setO(double o) {
        this.O = o;
    }

    public void setA(double a) {
        this.A = a;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public double getArea() {
        return (O + A) / 2;
    }

    public double getPerimeter() {
        return H + O + A;
    }

    public double getH() {
        return H;
    }

    public double getO() {
        return O;
    }

    public double getA() {
        return A;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public void calcFromHO() {
        A = Math.sqrt(H * H - O * O);
        x = Math.toDegrees(Math.asin(O / H));
        y = 90 - x;
        if (xPos == 1) {
            x = Math.toRadians(x);
        }
        if (yPos == 1) {
            y = Math.toRadians(y);
        }
    }

    public void calcFromHA() {
        O = Math.sqrt(H * H - A * A);
        x = Math.toDegrees(Math.acos(A / H));
        y = 90 - x;
        if (xPos == 1) {
            x = Math.toRadians(x);
        }
        if (yPos == 1) {
            y = Math.toRadians(y);
        }
    }

    public void calcFromHX() {
        if (xPos == 1) {
            y = 90 - Math.toDegrees(x);
            O = H * Math.sin(x);
            A = H * Math.cos(x);
        } else {
            y = 90 - x;
            O = H * Math.sin(Math.toRadians(x));
            A = H * Math.cos(Math.toRadians(x));
        }
        if (yPos == 1) {
            y = Math.toRadians(y);
        }
    }

    public void calcFromHY() {
        if (yPos == 1) {
            x = 90 - Math.toDegrees(y);
        } else {
            x = 90 - y;
        }
        O = H * Math.sin(Math.toRadians(x));
        A = H * Math.cos(Math.toRadians(x));
        if (xPos == 1) {
            x = Math.toRadians(x);
        }
    }

    public void calcFromOA() {
        H = Math.sqrt(O * O + A * A);
        x = Math.toDegrees(Math.atan(O / A));
        y = 90 - x;
        if (xPos == 1) {
            x = Math.toRadians(x);
        }
        if (yPos == 1) {
            y = Math.toRadians(y);
        }
    }

    public void calcFromOX() {
        if (xPos == 1) {
            y = 90 - Math.toDegrees(x);
            A = O / Math.tan(x);
        } else {
            y = 90 - x;
            A = O / Math.tan(Math.toRadians(x));
        }
        H = Math.sqrt((A * A) + (O * O));
        if (yPos == 1) {
            y = Math.toRadians(y);
        }
    }

    public void calcFromOY() {
        if (yPos == 1) {
            x = 90 - Math.toDegrees(y);
            A = O / Math.tan(Math.toRadians(x));
        } else {
            x = 90 - y;
            A = O / Math.tan(Math.toRadians(x));
        }
        H = Math.sqrt((A * A) + (O * O));
        if (xPos == 1) {
            x = Math.toRadians(x);
        }
    }

    public void calcFromAX() {
        if (xPos == 1) {
            y = 90 - Math.toDegrees(x);
        } else {
            y = 90 - x;
        }
        H = A / Math.sin(Math.toRadians(y));
        O = H * Math.sin(Math.toRadians(x));
        if (yPos == 1) {
            y = Math.toRadians(y);
        }
    }

    public void calcFromAY() {
        if (yPos == 1) {
            x = 90 - Math.toDegrees(y);
            H = A / Math.sin(y);
        } else {
            x = 90 - y;
            H = A / Math.sin(Math.toRadians(y));
        }
        O = H * Math.sin(Math.toRadians(x));
        if (xPos == 1) {
            x = Math.toRadians(x);
        }

    }
}
