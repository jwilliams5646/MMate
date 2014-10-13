package com.jwilliams.machinistmate.app.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jwilliams.machinistmate.app.Adapters.DbHelper;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoButton;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoTextView;
import com.jwilliams.machinistmate.app.Formatter;
import com.jwilliams.machinistmate.app.R;

import java.io.IOException;

/**
 * Created by John Williams
 * Manages the LengthFragment Lifecycle
 */
public class LengthFragment extends Fragment {
    //Conversion Variables

    private static final String KEY_POSITION="position";
    private RobotoTextView answer;
    private RobotoTextView answerType;
    private RobotoTextView precisionView;
    private Spinner inputSpinner;
    private Spinner outputSpinner;
    private EditText input;
    private RobotoButton calcButton;
    private RobotoButton addButton;
    private RobotoButton minusButton;
    private LinearLayout answerLayout;
    private int inputPos;
    private int precision;
    private String output;
    private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private AdView adView;
    private AdRequest adRequest;

    public LengthFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.conversion_layout, container, false);
        setAd(rootView);
        setLayoutVariables(rootView);
        setSpinnerAdapter();
        setInputListener();
        setOutputListener();
        setCalcListener();
        setPrecisionListeners();

        return rootView;
    }

    private void setAd(View rootView){
        adView = (AdView)rootView.findViewById(R.id.rt_adView);
        adRequest = new AdRequest.Builder()
/*                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(TEST_DEVICE_ID)*/
                .build();
        adView.loadAd(adRequest);
    }

    private void setPrecisionListeners() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(precision < 6) {
                    precision++;
                    precisionView.setText(Integer.toString(precision));
                }else{
                    Toast.makeText(getActivity(), "Max precision reached.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (precision > 1) {
                    precision--;
                    precisionView.setText(Integer.toString(precision));
                } else {
                    Toast.makeText(getActivity(), "You can't go down any farther.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setCalcListener() {
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getCalculation().execute();
            }
        });
    }

    private void setOutputListener() {
        outputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setConversionType(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void setInputListener() {
        inputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                inputPos = i;
                setInputHint(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void setInputHint(int position) {
        switch (position) {
            case 0:
                input.setHint("in");
                break;
            case 1:
                input.setHint("ft");
                break;
            case 2:
                input.setHint("yd");
                break;
            case 3:
                input.setHint("mm");
                break;
            case 4:
                input.setHint("cm");
                break;
            case 5:
                input.setHint("m");
                break;
            default:
                input.setHint("in");
                break;
        }
    }

    private void setLayoutVariables(View rootView){
        answer = (RobotoTextView) rootView.findViewById(R.id.conv_answer);
        answerType = (RobotoTextView) rootView.findViewById(R.id.conv_answer_type);
        precisionView = (RobotoTextView) rootView.findViewById(R.id.conv_precision_view);
        inputSpinner = (Spinner) rootView.findViewById(R.id.conv_input_spinner);
        outputSpinner = (Spinner) rootView.findViewById(R.id.conv_output_spinner);
        input = (EditText) rootView.findViewById(R.id.conv_input);
        calcButton = (RobotoButton) rootView.findViewById(R.id.conv_calc_button);
        addButton = (RobotoButton) rootView.findViewById(R.id.conv_add_button);
        minusButton = (RobotoButton) rootView.findViewById(R.id.conv_minus_button);
        answerLayout = (LinearLayout) rootView.findViewById(R.id.conv_answer_layout);
        inputPos = 0;
        precision = 2;
        precisionView.setText(Integer.toString(precision));
    }

    private void setSpinnerAdapter(){
        ArrayAdapter<CharSequence> convAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.conv_length_array, R.layout.spinner_background);
        convAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
        inputSpinner.setAdapter(convAdapter);
        outputSpinner.setAdapter(convAdapter);
    }

    private void setConversionType(int position){
        switch (position) {
            case 0:
                answerType.setText("in");
                output = "inch";
                break;
            case 1:
                answerType.setText("ft");
                output = "feet";
                break;
            case 2:
                answerType.setText("yd");
                output = "yard";
                break;
            case 3:
                answerType.setText("mm");
                output = "millimeter";
                break;
            case 4:
                answerType.setText("cm");
                output = "centimeter";
                break;
            case 5:
                answerType.setText("m");
                output = "meter";
                break;
            default:
                answerType.setText("in");
                output = "inch";
                break;
        }
    }

    private void setDatabase(DbHelper myDbHelper){
        //instantiates the database and the
        try {
            myDbHelper.createDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openDb(DbHelper myDbHelper){
        try {
            myDbHelper.openDataBase();
        }catch(SQLException sqle){
            Log.d("Open Database", "SQL Exception");
            throw sqle;
        }
    }

    private class getCalculation extends AsyncTask {
        private double calcInput = 0.0;
        DbHelper myDbHelper;
        Cursor c;

        @Override
        protected void onPreExecute(){
            try {
                calcInput = Double.parseDouble(input.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(getActivity(), "Invalid Input", Toast.LENGTH_SHORT).show();
                cancel(true);
            }
        }

        @Override
        protected String doInBackground(Object[] params) {
            Log.d("DB Thread", "Starting work");
            myDbHelper = new DbHelper(getActivity());
            setDatabase(myDbHelper);
            openDb(myDbHelper);
            c = myDbHelper.getLengthConversionFactor(inputPos, output);
            c.moveToFirst();
            String result = Formatter.formatOutput(calcInput *
                    Double.parseDouble(c.getString(c.getColumnIndex(output))), precision);
            myDbHelper.close();
            Log.d("DB Thread", "Ending work");
            return result;
        }

        @Override
        protected void onPostExecute(Object result){
            answer.setText(result.toString());
            calcInput = 0;
            myDbHelper = null;
            c =  null;
            this.cancel(true);
        }
    }

    @Override
    public void onPause(){
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    /** Called before the activity is destroyed. */
    @Override
    public void onDestroy() {
        // Destroy the AdView.
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
