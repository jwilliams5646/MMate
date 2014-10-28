package com.jwilliams.machinistmate.app.ConversionClass;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jwilliams.machinistmate.app.Adapters.DbHelper;
import com.jwilliams.machinistmate.app.Utility;

/**
 * Created by John Williams
 * This class handles the conversion Fragment database calls and calculations.
 */
public class Conversions extends AsyncTask<Void, Void, String> {

    private EditText input;
    private TextView answer;
    private double calcInput;
    private int conversionType;
    private int inputPos;
    private String output;
    private int precision;
    private Context context;
    private Cursor c;
    private DbHelper myDbHelper;

    public Conversions(EditText input, TextView answer, int conversionType, int inputPos, String output, int precision, Activity context) {
        this.input = input;
        this.answer = answer;
        this.conversionType = conversionType;
        this.inputPos = inputPos;
        this.output = output;
        this.precision = precision;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        try {
            calcInput = Double.parseDouble(input.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Invalid Input", Toast.LENGTH_SHORT).show();
            cancel(true);
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        myDbHelper = new DbHelper(context);
        myDbHelper.openDataBase();
        switch (conversionType) {
            case 0:
                c = myDbHelper.getLengthConversionFactor(inputPos, output);
                break;
            case 1:
                c = myDbHelper.getVolumeConversionFactor(inputPos, output);
                break;
        }
        Log.d("Precision", Integer.toString(precision));
        c.moveToFirst();
        Double calcResource = Double.parseDouble(c.getString(c.getColumnIndex(output)));
        Log.d("Conversion Factor", Double.toString(calcResource));
        String result = Utility.formatOutput(calcInput * calcResource, precision);
        myDbHelper.close();
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        answer.setText(result);
        myDbHelper = null;
        c = null;
        this.cancel(true);
    }
}
