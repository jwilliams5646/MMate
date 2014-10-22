package com.jwilliams.machinistmate.app.GeometryClasses;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by John Williams
 * ObliqueTriangle object calculates for ObliqueTriangleFragment.
 */
public class ObliqueTriangle {

    private double a;
    private double b;
    private double c;
    private double x;
    private double y;
    private double z;
    private int xPos;
    private int yPos;
    private int zPos;
    private int count;
    private Context maContext;

    public boolean calcObliqueTriangle(EditText a, EditText b, EditText c,
                                       EditText x, EditText y, EditText z,
                                       int xPos, int yPos, int zPos, Activity context){

        if(!isEmpty(a)){
            this.a = Double.parseDouble(a.getText().toString());
            count++;
        }

        if(!isEmpty(b)){
            this.b = Double.parseDouble(b.getText().toString());
            count++;
        }

        if(!isEmpty(c)){
            this.c = Double.parseDouble(c.getText().toString());
            count++;
        }

        if(!isEmpty(x)){
            this.x = Double.parseDouble(x.getText().toString());
            count++;
        }

        if(!isEmpty(y)){
            this.y = Double.parseDouble(y.getText().toString());
            count++;
        }

        if(!isEmpty(z)){
            this.z = Double.parseDouble(z.getText().toString());
            count++;
        }

        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.maContext = context;

        return process(a,b,c,x,y,z);
    }

    public boolean process(EditText a, EditText b, EditText c,
                           EditText x, EditText y, EditText z){
        if(count > 3){
            error(0);
            return false;
        }

        if(count < 3){
            error(1);
            return false;
        }

        if(!isEmpty(x) && !isEmpty(y) && !isEmpty(z)){
            error(2);
            return false;
        }

        if(this.x >= 90 || this.y >= 90){
            error(3);
            return false;
        }

        int errorCode = 0;
        //a-b-c -> x-y-z
        if (!isEmpty(a) && !isEmpty(b) && !isEmpty(c)){
            errorCode = calcFromABC();
        }
        //a-c-z -> x-y-b
        else if (!isEmpty(c) && !isEmpty(a) && !isEmpty(z)){
            errorCode = calcFromCAZ();
        }
        //c-b-x -> a-y-z
        else if (!isEmpty(c) && !isEmpty(b) && !isEmpty(x)){
            errorCode = calcFromCBX();
        }
        //a-b-y -> c-x-z
        else if (!isEmpty(a) && !isEmpty(b) && !isEmpty(y)){
            errorCode = calcFromABY();
        }
        //c-a-y -> b-x-z
        else if (!isEmpty(c) && !isEmpty(a) && !isEmpty(y)){
            errorCode = calcFromCAY();
        }
        //c-a-x -> b-y-z
        else if (!isEmpty(c) && !isEmpty(a) && !isEmpty(x)){
            errorCode = calcFromCAX();
        }
        //c-b-y -> a-x-z
        else if (!isEmpty(c) && !isEmpty(b) && !isEmpty(y)){
            errorCode = calcFromCBY();
        }
        //c-b-z -> a-x-y
        else if (!isEmpty(c) && !isEmpty(b) && !isEmpty(z)){
            errorCode = calcFromCBZ();
        }
        //a-b-x -> c-y-z
        else if (!isEmpty(a) && !isEmpty(b) && !isEmpty(x)){
            errorCode = calcFromABX();
        }
        //a-b-z -> c-x-y
        else if (!isEmpty(a) && !isEmpty(b) && !isEmpty(z)){
            errorCode = calcFromABZ();
        }
        //c-y-x -> a-b-z
        else if (!isEmpty(c) && !isEmpty(y) && !isEmpty(x)){
            errorCode = calcFromCYX();
        }

        else if (!isEmpty(c) && !isEmpty(y) && !isEmpty(z)){
            errorCode = calcFromCYZ();
        }

        else if (!isEmpty(c) && !isEmpty(x) && !isEmpty(z)){
            errorCode = calcFromCXZ();
        }

        else if (!isEmpty(a) && !isEmpty(y) && !isEmpty(x)){
            errorCode = calcFromAYX();
        }

        else if (!isEmpty(a) && !isEmpty(y) && !isEmpty(z)){
            errorCode = calcFromAYZ();
        }

        else if (!isEmpty(a) && !isEmpty(x) && !isEmpty(z)){
            errorCode = calcFromAXZ();
        }

        else if (!isEmpty(b) && !isEmpty(y) && !isEmpty(x)){
            errorCode = calcFromBYX();
        }

        else if (!isEmpty(b) && !isEmpty(y) && !isEmpty(z)){
            errorCode = calcFromBYZ();
        }

        else if (!isEmpty(b) && !isEmpty(x) && !isEmpty(z)){
            errorCode = calcFromBXZ();
        }
        if(errorCode == -1) {
            return true;
        }else{
            error(errorCode);
            return false;
        }
    }

    public void error(int errorCode){
        switch(errorCode){
            case 0:
                Toast.makeText(maContext, "You need at least 3 values to proceed", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(maContext, "Input a maximum of 3 values", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(maContext, "Triangle can not be solved with only angles.", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(maContext, "Only angle (z) can be 90 degrees or greater", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(maContext, "This is not a triangle", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(maContext, "Side(b) is the base of the triangle and must be the longest side and greater than the sum of Side(a) and Side(c).", Toast.LENGTH_SHORT).show();
                break;
            case 6:
                Toast.makeText(maContext, "Side b cannot be shorter than side c if angle (z) is greater than 90 degrees", Toast.LENGTH_SHORT).show();
        }
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getArea() {
        double x = (a + b + c)/2;
        return Math.sqrt(x * (x - a) * (x - b) * (x - c));
    }

    public double getPerimeter() {
        return a+b+c;
    }

    public double getHeight() {
        return a/(getArea()/b);
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

    public double getRadian(int pos, double angle){
        if(pos == 0){
            return Math.toRadians(angle);
        }else{
           return angle;
        }
    }

    public double getDegree(int pos, double angle){
        if(pos == 1){
            return Math.toDegrees(angle);
        }else{
            return angle;
        }
    }

    public int calcFromABC(){
        if(b < c || b < a){
            return 5;
        }
        x = Math.toDegrees(Math.acos((c * c + b * b - a * a) / (2 * b * c)));
        y = Math.toDegrees(Math.acos((a * a + b * b - c * c) / (2 * a * b)));
        z = 180 - (x + y);
        if(xPos == 1){
            x = getRadian(xPos,x);
        }
        if(yPos == 1){
            y = getRadian(yPos,y);
        }
        if(zPos == 1){
            z = getRadian(zPos,z);
        }
        if(isXNaN() || isYNaN() || isZNaN()) {
            return 4;
        }
        return -1;
    }

    public int calcFromCAZ(){
        b = Math.sqrt(c * c + a * a - 2 * c * a * (Math.cos(getRadian(zPos,z))));
        if (c < a) {  // only one angle can be > 90 deg
            y = Math.toDegrees(Math.asin(c * Math.sin(getRadian(zPos,z)) / b));
            x = 180 - (y + getDegree(zPos,z));
        } else {
            x = Math.toDegrees(Math.asin(a * Math.sin(getRadian(zPos,z)) / b));
            y = 180 - (x + getDegree(zPos,z));
        }
        if(xPos == 1){
            x = getRadian(xPos,x);
        }
        if(yPos == 1){
            y = getRadian(yPos,y);
        }
        return -1;
    }

    public int calcFromCBX(){
        a = Math.sqrt(c * c + b * b - 2 * c * b * Math.cos(getRadian(xPos,x)));
        // only one angle can be > 90 deg
        if (c < b) {
            y = Math.toDegrees(Math.asin(c * Math.sin(getRadian(xPos,x)) / a));
            z = 180 - (getDegree(xPos,x) + y);
        } else {
            z = Math.toDegrees(Math.asin(b * Math.sin(getRadian(xPos,x)) / a));
            y = 180 - (getDegree(xPos,x) + z);
        }
        if(yPos == 1){
            y = getRadian(yPos,y);
        }
        if(zPos == 1){
            z = getRadian(zPos,z);
        }
        return -1;
    }

    public int calcFromABY(){
        c = Math.sqrt(a * a + b * b - 2 * a * b * Math.cos(getRadian(yPos,y)));
        // only one angle can be > 90 deg
        if (a < b) {
            x = Math.toDegrees(Math.asin(a * Math.sin(getRadian(yPos,y)) / c));
            z = 180 - (x + getDegree(yPos,y));
        } else {
            z = Math.toDegrees(Math.asin(b * Math.sin(getRadian(yPos,y)) / c));
            x = 180 - (z + getDegree(yPos,y));
        }
        if(xPos == 1){
            x = getRadian(xPos,x);
        }
        if(zPos == 1){
            z = getRadian(zPos,z);
        }
        return -1;
    }

    public int calcFromCAY(){
        x = Math.toDegrees(Math.asin(a * Math.sin(getRadian(yPos,y)) / c));
        z = 180.0 - (getDegree(yPos,y) + x);
        b = Math.sqrt(c * c + a * a - 2 * c * a * Math.cos(Math.toRadians(z)));
        if(xPos == 1){
            x = getRadian(xPos,x);
        }
        if(zPos == 1){
            z = getRadian(zPos,z);
        }
        return -1;
    }

    public int calcFromCAX(){
        y = Math.toDegrees(Math.asin(c * Math.sin(getRadian(xPos,x)) / a));
        z = 180.0 - (y + getDegree(xPos,x));
        b = Math.sqrt(c * c + a * a - 2 * c * a * Math.cos(Math.toRadians(z)));
        if(yPos == 1){
            y = getRadian(yPos,y);
        }
        if(zPos == 1){
            z = getRadian(zPos,z);
        }
        return -1;
    }

    public int calcFromCBY(){
        z = Math.toDegrees(Math.asin(b * Math.sin(getRadian(yPos,y)) / c));
        x = 180.0 - (getDegree(yPos,y) + z);
        a = Math.sqrt(c * c + b * b - 2 * c * b * Math.cos(Math.toRadians(x)));
        if(xPos == 1){
            x = getRadian(xPos,x);
        }
        if(zPos == 1){
            z = getRadian(zPos,z);
        }
        return -1;
    }

    public int calcFromCBZ(){
        if(c > b && getDegree(zPos,z) >90){
            return 6;
        }
        y = Math.toDegrees(Math.asin(c * Math.sin(getRadian(zPos,z)) / b));
        x = 180.0 - (y + getDegree(zPos,z));
        a = Math.sqrt(c * c + b * b - 2 * c * b * Math.cos(Math.toRadians(x)));
        if(xPos == 1){
            x = getRadian(xPos,x);
        }
        if(yPos == 1){
            y = getRadian(yPos,y);
        }
        return -1;
    }

    public int calcFromABX(){
        z = Math.toDegrees(Math.asin(b * Math.sin(getRadian(xPos,x)) / a));
        y = 180.0 - (getDegree(xPos,x) + z);
        c = Math.sqrt(a * a + b * b - 2 * a * b * Math.cos(Math.toRadians(y)));
        if(yPos == 1){
            y = getRadian(yPos,y);
        }
        if(zPos == 1){
            z = getRadian(zPos,z);
        }
        return -1;
    }

    public int calcFromABZ(){
        x = Math.toDegrees(Math.asin(a * Math.sin(getRadian(zPos,z)) / b));
        y = 180.0 - (getDegree(zPos,z) + x);
        c = Math.sqrt(a * a + b * b - 2 * a * b * Math.cos(Math.toRadians(y)));
        if(xPos == 1){
            x = getRadian(xPos,x);
        }
        if(yPos == 1){
            y = getRadian(yPos,y);
        }
        return -1;
    }

    public int calcFromCYX(){
        z = 180 - (getDegree(xPos,x) + getDegree(yPos,y));
        if (z <= 0){
            return 4;
        }
        a = c* Math.sin(getRadian(xPos,x) / Math.sin(getRadian(yPos,y)));
        b = c* Math.sin(Math.toRadians(z) / Math.sin(getRadian(yPos,y)));
        if(zPos == 1){
            z = getRadian(zPos,z);
        }
        return -1;
    }

    public int calcFromCYZ(){
        x = 180 - (getDegree(yPos,y) + getDegree(zPos,z));
        if (x <= 0){
            return 4;
        }
        a = c* Math.sin(Math.toRadians(x)/ Math.sin(getRadian(yPos,y)));
        b = c* Math.sin(getRadian(zPos,z))/ Math.sin(getRadian(yPos,y));
        if(xPos == 1){
            x = getRadian(xPos,x);
        }
        return -1;
    }

    public int calcFromCXZ(){
        y = 180 - (getDegree(xPos,x) + getDegree(zPos,z));
        if (y<=0){
            return 4;
        }
        a = c* Math.sin(getRadian(xPos,x)/ Math.sin(Math.toRadians(y)));
        b = c* Math.sin(getRadian(zPos,z)/ Math.sin(Math.toRadians(y)));
        if(yPos == 1){
            y = getRadian(yPos,y);
        }
        return -1;
    }

    public int calcFromAYX(){
        z = 180 - (getDegree(xPos,x) + getDegree(yPos,y));
        if (z<=0){
            return 4;
        }
        c = a* Math.sin(getRadian(yPos,y))/ Math.sin(getRadian(xPos,x));
        b = a* Math.sin(Math.toRadians(z))/ Math.sin(getRadian(xPos,x));
        if(zPos == 1){
            z = getRadian(zPos,z);
        }
        return -1;
    }

    public int calcFromAYZ(){
        x = 180 - (getDegree(zPos,z) + getDegree(yPos,y));
        if (x<=0){
            return 4;
        }
        c = a* Math.sin(getRadian(yPos,y))/ Math.sin(Math.toRadians(x));
        b = a* Math.sin(getRadian(zPos,z))/ Math.sin(Math.toRadians(x));
        if(xPos == 1){
            x = getRadian(xPos,x);
        }
        return -1;
    }

    public int calcFromAXZ(){
        y = 180 - (getDegree(zPos,z) + getDegree(xPos,x));
        if (y<=0){
            return 4;
        }
        c = a* Math.sin(Math.toRadians(y))/ Math.sin(getRadian(xPos,x));
        b = a* Math.sin(getRadian(zPos,z))/ Math.sin(getRadian(xPos,x));
        if(yPos == 1){
            y = getRadian(yPos,y);
        }
        return -1;
    }

    public int calcFromBYX(){
        z = 180 - (getDegree(yPos,y) + getDegree(xPos,x));
        if (z<=0){
            return 4;
        }
        c = b* Math.sin(getRadian(yPos,y))/ Math.sin(Math.toRadians(z));
        a = b* Math.sin(getRadian(xPos,x))/ Math.sin(Math.toRadians(z));
        if(zPos == 1){
            z = getRadian(zPos,z);
        }
        return -1;
    }

    public int calcFromBYZ(){
        x = 180 - (getDegree(yPos,y) + getDegree(zPos,z));
        if (x<=0){
            return 4;
        }
        c = b* Math.sin(getRadian(yPos,y))/ Math.sin(getRadian(zPos,z));
        a = b* Math.sin(Math.toRadians(x))/ Math.sin(getRadian(zPos,z));
        if(xPos == 1){
            x = getRadian(xPos,x);
        }
        return -1;
    }

    public int calcFromBXZ(){
        y = 180 - (getDegree(xPos,x) + getDegree(zPos,z));
        if (x<=0){
            return 4;
        }
        c = b* Math.sin(Math.toRadians(y))/ Math.sin(getRadian(zPos,z));
        a = b* Math.sin(getRadian(xPos,x))/ Math.sin(getRadian(zPos,z));
        if(yPos == 1){
            y = getRadian(yPos,y);
        }
        return -1;
    }

    public boolean isEmpty(EditText et){
        try{
            double check = Double.parseDouble(et.getText().toString());
        }catch(NumberFormatException e){
            return true;
        }
        return false;
    }
}
