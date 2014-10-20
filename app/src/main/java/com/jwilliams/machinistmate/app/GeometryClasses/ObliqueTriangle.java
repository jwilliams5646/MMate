package com.jwilliams.machinistmate.app.GeometryClasses;

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

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public void setzPos(int zPos) {
        this.zPos = zPos;
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
        if(b < c || b < a || b <= a+c){
            return 2;
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
            return 1;
        }
        return 0;
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
        return 0;
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
        return 0;
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
        return 0;
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
        return 0;
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
        return 0;
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
        return 0;
    }

    public int calcFromCBZ(){
        if(c > b && getDegree(zPos,z) >90){
            return 3;
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
        return 0;
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
        return 0;
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
        return 0;
    }

    public int calcFromCYX(){
        z = 180 - (getDegree(xPos,x) + getDegree(yPos,y));
        if (z <= 0){
            return 1;
        }
        a = c* Math.sin(getRadian(xPos,x) / Math.sin(getRadian(yPos,y)));
        b = c* Math.sin(Math.toRadians(z) / Math.sin(getRadian(yPos,y)));
        if(zPos == 1){
            z = getRadian(zPos,z);
        }
        return 0;
    }

    public int calcFromCYZ(){
        x = 180 - (getDegree(yPos,y) + getDegree(zPos,z));
        if (x <= 0){
            return 1;
        }
        a = c* Math.sin(Math.toRadians(x)/ Math.sin(getRadian(yPos,y)));
        b = c* Math.sin(getRadian(zPos,z))/ Math.sin(getRadian(yPos,y));
        if(xPos == 1){
            x = getRadian(xPos,x);
        }
        return 0;
    }

    public int calcFromCXZ(){
        y = 180 - (getDegree(xPos,x) + getDegree(zPos,z));
        if (y<=0){
            return 1;
        }
        a = c* Math.sin(getRadian(xPos,x)/ Math.sin(Math.toRadians(y)));
        b = c* Math.sin(getRadian(zPos,z)/ Math.sin(Math.toRadians(y)));
        if(yPos == 1){
            y = getRadian(yPos,y);
        }
        return 0;
    }

    public int calcFromAYX(){
        z = 180 - (getDegree(xPos,x) + getDegree(yPos,y));
        if (z<=0){
            return 1;
        }
        c = a* Math.sin(getRadian(yPos,y))/ Math.sin(getRadian(xPos,x));
        b = a* Math.sin(Math.toRadians(z))/ Math.sin(getRadian(xPos,x));
        if(zPos == 1){
            z = getRadian(zPos,z);
        }
        return 0;
    }

    public int calcFromAYZ(){
        x = 180 - (getDegree(zPos,z) + getDegree(yPos,y));
        if (x<=0){
            return 1;
        }
        c = a* Math.sin(getRadian(yPos,y))/ Math.sin(Math.toRadians(x));
        b = a* Math.sin(getRadian(zPos,z))/ Math.sin(Math.toRadians(x));
        if(xPos == 1){
            x = getRadian(xPos,x);
        }
        return 0;
    }

    public int calcFromAXZ(){
        y = 180 - (getDegree(zPos,z) + getDegree(xPos,x));
        if (y<=0){
            return 1;
        }
        c = a* Math.sin(Math.toRadians(y))/ Math.sin(getRadian(xPos,x));
        b = a* Math.sin(getRadian(zPos,z))/ Math.sin(getRadian(xPos,x));
        if(yPos == 1){
            y = getRadian(yPos,y);
        }
        return 0;
    }

    public int calcFromBYX(){
        z = 180 - (getDegree(yPos,y) + getDegree(xPos,x));
        if (z<=0){
            return 1;
        }
        c = b* Math.sin(getRadian(yPos,y))/ Math.sin(Math.toRadians(z));
        a = b* Math.sin(getRadian(xPos,x))/ Math.sin(Math.toRadians(z));
        if(zPos == 1){
            z = getRadian(zPos,z);
        }
        return 0;
    }

    public int calcFromBYZ(){
        x = 180 - (getDegree(yPos,y) + getDegree(zPos,z));
        if (x<=0){
            return 1;
        }
        c = b* Math.sin(getRadian(yPos,y))/ Math.sin(getRadian(zPos,z));
        a = b* Math.sin(Math.toRadians(x))/ Math.sin(getRadian(zPos,z));
        if(xPos == 1){
            x = getRadian(xPos,x);
        }
        return 0;
    }

    public int calcFromBXZ(){
        y = 180 - (getDegree(xPos,x) + getDegree(zPos,z));
        if (x<=0){
            return 1;
        }
        c = b* Math.sin(Math.toRadians(y))/ Math.sin(getRadian(zPos,z));
        a = b* Math.sin(getRadian(xPos,x))/ Math.sin(getRadian(zPos,z));
        if(yPos == 1){
            y = getRadian(yPos,y);
        }
        return 0;
    }
}
