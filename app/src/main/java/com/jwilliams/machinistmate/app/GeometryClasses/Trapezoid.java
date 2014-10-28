package com.jwilliams.machinistmate.app.GeometryClasses;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jwilliams.machinistmate.app.Utility;

/**
 * Created by John Williams
 *
 */
public class Trapezoid {

    public void calcTrapezoid(EditText input1, EditText input2, EditText input3, EditText input4, TextView answer, int choice, Activity context){

        switch(choice){
            case 0:
                calcArea(input1,input2,input3,answer,context);
                break;
            case 1:
                calcBaseA(input1,input2,input3,answer,context);
                break;
            case 2:
                calcBaseB(input1,input2,input3,answer,context);
                break;
            case 3:
                calcSideC(input1,input2,input3,input4,answer,context);
                break;
            case 4:
                calcSideD(input1,input2,input3,input4,answer,context);
                break;
            case 5:
                calcHeight(input1,input2,input3,answer,context);
                break;
            case 6:
                calcMedian(input1,input2,answer,context);
                break;
            case 7:
                calcPerimter(input1,input2,input3,input4,answer,context);
                break;
            default:
                calcArea(input1,input2,input3,answer,context);
                break;
        }
    }

    private void calcPerimter(EditText input1, EditText input2, EditText input3, EditText input4, TextView answer, Activity context) {
        double a = 0.0;
        double b = 0.0;
        double c = 0.0;
        double d = 0.0;
        boolean check = true;

        try{
            a = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            b = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            c = Double.parseDouble(input3.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            d = Double.parseDouble(input4.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        if(check){
            answer.setText(Utility.formatOutput(a + b + c + d, getPrecision(context)));
        }else{
            Toast.makeText(context, "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcMedian(EditText input1, EditText input2,TextView answer, Activity context) {
        double a = 0.0;
        double b = 0.0;
        boolean check = true;

        try{
            a = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            b = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        if(check){
            answer.setText(Utility.formatOutput((a + b) / 2, getPrecision(context)));
        }else{
            Toast.makeText(context, "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcHeight(EditText input1, EditText input2, EditText input3, TextView answer, Activity context) {
        double a = 0.0;
        double b = 0.0;
        double A = 0.0;
        boolean check = true;

        try{
            a = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            b = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            A = Double.parseDouble(input3.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        if(check){
            answer.setText(Utility.formatOutput(2 * (A / (a + b)), getPrecision(context)));
        }else{
            Toast.makeText(context, "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcSideD(EditText input1, EditText input2, EditText input3, EditText input4, TextView answer, Activity context) {
        double a = 0.0;
        double b = 0.0;
        double c = 0.0;
        double p = 0.0;
        boolean check = true;

        try{
            a = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            b = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            c = Double.parseDouble(input3.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            p = Double.parseDouble(input4.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        if(check){
            answer.setText(Utility.formatOutput(p - a - b - c, getPrecision(context)));
        }else{
            Toast.makeText(context, "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcSideC(EditText input1, EditText input2, EditText input3, EditText input4, TextView answer, Activity context) {
        double a = 0.0;
        double b = 0.0;
        double d = 0.0;
        double p = 0.0;
        boolean check = true;

        try{
            a = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            b = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            d = Double.parseDouble(input3.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            p = Double.parseDouble(input4.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        if(check){
            answer.setText(Utility.formatOutput(p - a - b - d, getPrecision(context)));
        }else{
            Toast.makeText(context, "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcBaseB(EditText input1, EditText input2, EditText input3, TextView answer, Activity context) {
        double a = 0.0;
        double h = 0.0;
        double A = 0.0;
        boolean check = true;

        try{
            a = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            h = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            A = Double.parseDouble(input3.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        if(check){
            answer.setText(Utility.formatOutput(2 * (A / h) - a, getPrecision(context)));
        }else{
            Toast.makeText(context, "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcBaseA(EditText input1, EditText input2, EditText input3, TextView answer, Activity context) {
        double b = 0.0;
        double h = 0.0;
        double A = 0.0;
        boolean check = true;

        try{
            b = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            h = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            A = Double.parseDouble(input3.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        if(check){
            answer.setText(Utility.formatOutput(2 * (A / h) - b, getPrecision(context)));
        }else{
            Toast.makeText(context, "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcArea(EditText input1, EditText input2, EditText input3, TextView answer, Activity context) {
        double a = 0.0;
        double b = 0.0;
        double h = 0.0;
        boolean check = true;

        try{
            a = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            b = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        try{
            h = Double.parseDouble(input3.getText().toString());
        }catch(NumberFormatException e){
            check = false;
        }

        if(check){
            answer.setText(Utility.formatOutput(((a + b) / 2) * h, getPrecision(context)));
        }else{
            Toast.makeText(context, "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
        }
    }

    public int getPrecision(Activity context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return Integer.parseInt(sp.getString("pref_key_geometry_precision", "2"));
    }
}
