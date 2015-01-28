package com.jwilliams.machinistmate.app.GeometryClasses;

/**
 * Created by john.williams on 9/11/2014.
 */
public class Rectangle {
    private double area;
    private double diagonal;
    private double width;
    private double length;
    private double perimeter;

    public void setArea(double area){
        this.area = area;
    }

    public void setDiagonal(double diagonal){
        this.diagonal = diagonal;
    }

    public void setWidth(double width){
        this.width = width;
    }

    public void setLength(double length){
        this.length = length;
    }

    public void setPerimeter(double perimeter){
        this.perimeter = perimeter;
    }

    public double calcPerimeter() {
        return 2*(length+width);
    }

    public double calcLengthByPerimeter() {
        return perimeter/2-width;
    }

    public double calcLengthByDiagonal() {
        return Math.sqrt(diagonal*diagonal - width*width);
    }

    public double calcLengthByArea() {
        return area/width;
    }

    public double calcWidthByPerimeter() {
        return perimeter/2-length;
    }

    public double calcWidthByDiagonal() {
        return Math.sqrt(diagonal*diagonal - length*length);
    }

    public double calcWidthByArea() {
        return area/length;
    }

    public double calcDiagonal() {
        return Math.sqrt(length*length + width*width);
    }

    public double calcArea() {
        return length*width;
    }
}
