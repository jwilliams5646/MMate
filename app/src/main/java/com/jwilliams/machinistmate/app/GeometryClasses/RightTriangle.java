package com.jwilliams.machinistmate.app.GeometryClasses;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.jwilliams.machinistmate.app.Utility;

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
    Utility u;
    private Context context;

    public double getH() {
        return H;
    }

    public double getO() {
        return O;
    }

    public double getA() {
        return A;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getArea() {
        return (O + A) / 2;
    }

    public double getPerimeter() {
        return H + O + A;
    }

    public boolean calcRightTriangle(EditText H, EditText O, EditText A, EditText X, EditText Y, int xPos, int yPos, Activity context ){
        u = new Utility();
        int count = 0;

        if(!u.isEmpty(H)){
            this.H = Double.parseDouble(H.getText().toString());
            count++;
        }

        if(!u.isEmpty(O)){
            this.O = Double.parseDouble(O.getText().toString());
            count++;
        }

        if(!u.isEmpty(A)){
            this.A = Double.parseDouble(A.getText().toString());
            count++;
        }

        if(!u.isEmpty(X)){
            this.x = Double.parseDouble(X.getText().toString());
            count++;
        }

        if(!u.isEmpty(Y)){
            this.y = Double.parseDouble(Y.getText().toString());
            count++;
        }

        this.xPos = xPos;
        this.yPos = yPos;
        this.context = context;

        return process(H,O,A,X,Y,count);
    }

    private boolean process(EditText H, EditText O, EditText A, EditText X, EditText Y, int count) {

        if (count > 4 || count < 2) {
            Toast.makeText(context, "You must enter 2 values, and they cannot both be angles.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!u.isEmpty(X) && !u.isEmpty(Y)) {
            Toast.makeText(context, "You must enter 2 values, and they cannot both be angles.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!u.isEmpty(X)) {
            if (x < 1 || x >= 90) {
                Toast.makeText(context, "Angle x can't be greater than 90 or less than 1", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (!u.isEmpty(Y)) {
            if (y < 1 || y >= 90) {
                Toast.makeText(context, "Angle y can't be greater than 90 or less than 1", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (this.H < this.A && this.H != 0) {
            Toast.makeText(context, "Side H must always be longer than side A.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!u.isEmpty(H) && !u.isEmpty(O)) {
            calcFromHO();
        } else if (!u.isEmpty(H) && !u.isEmpty(A)) {
            calcFromHA();
        } else if (!u.isEmpty(H) && !u.isEmpty(X)) {
            calcFromHX();
        } else if (!u.isEmpty(H) && !u.isEmpty(Y)) {
            calcFromHY();
        } else if (!u.isEmpty(O) && !u.isEmpty(A)) {
            calcFromOA();
        } else if (!u.isEmpty(O) && !u.isEmpty(X)) {
            calcFromOX();
        } else if (!u.isEmpty(O) && !u.isEmpty(Y)) {
            calcFromOY();
        } else if (!u.isEmpty(A) && !u.isEmpty(X)) {
            calcFromAX();
        } else if (!u.isEmpty(A) && !u.isEmpty(Y)) {
            calcFromAY();
        }
        return true;
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
