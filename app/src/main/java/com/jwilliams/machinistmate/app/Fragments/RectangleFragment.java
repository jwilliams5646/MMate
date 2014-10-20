package com.jwilliams.machinistmate.app.Fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoButton;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoTextView;
import com.jwilliams.machinistmate.app.Formatter;
import com.jwilliams.machinistmate.app.GeometryClasses.Rectangle;
import com.jwilliams.machinistmate.app.R;

/**
 * Created by John on 5/27/2014.
 */
public class RectangleFragment extends Fragment {

    private static final String KEY_POSITION = "position";
    private LinearLayout inputLayout1;
    private LinearLayout inputLayout2;
    private LinearLayout inputLayout3;
    private Spinner answerChoice;
    private Spinner inputChoice;
    private RobotoTextView answer;
    private RobotoTextView inputView1;
    private RobotoTextView inputView2;
    private RobotoTextView inputView3;
    private EditText input1;
    private EditText input2;
    private EditText input3;
    private RobotoButton calcButton;
    private int answerPos;
    private int inputPos;
    private int precision;
    private ArrayAdapter<CharSequence> answerAdapter;
    private ArrayAdapter<CharSequence> inputAdapter;
    private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private AdView adView;
    private AdRequest adRequest;
    private Rectangle rectangle;
    private double inputValue1;
    private double inputValue2;
    private double inputValue3;
    private SharedPreferences sp;

    public RectangleFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rectangle_geometry, container, false);
        setAd(rootView);
        initializeLayout(rootView);
        setInitialLayout();
        setAnwerChoiceAdapter();
        setAnswerChoiceListener();
        setInputChoiceAdapter();
        setInputListener();
        setCalcListener();
        return rootView;
    }

    private void setAd(View rootView){
        adView = (AdView)rootView.findViewById(R.id.rect_adView);
        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(TEST_DEVICE_ID)
                .build();
        adView.loadAd(adRequest);
    }

    private void setCalcListener() {
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rectangle = new Rectangle();
                precision = Integer.parseInt(sp.getString("pref_key_geometry_precision", "2"));
                switch(answerPos){
                    case 0:
                        calcArea();
                        break;
                    case 1:
                        calcDiagonal();
                        break;
                    case 2:
                        switch(inputPos) {
                            case 0:
                                calcWidthByArea();
                                break;
                            case 1:
                                calcWidthByDiagonal();
                                break;
                            case 2:
                                calcWidthByPerimeter();
                                break;
                        }
                        break;
                    case 3:
                        switch(inputPos){
                            case 0:
                                calcLengthByArea();
                                break;
                            case 1:
                                calcLengthByDiagonal();
                                break;
                            case 2:
                                calcLengthByPerimeter();
                                break;
                        }
                        break;
                    case 4:
                        calcPerimeter();
                        break;
                }
            }
        });
    }

    private void calcPerimeter() {
        if(input1Valid() && input2Valid()){
            rectangle.setLength(inputValue1);
            rectangle.setWidth(inputValue2);
            answer.setText(Formatter.formatOutput(rectangle.calcPerimeter(),precision));
        }else{
            Toast.makeText(getActivity(), "One or more inputs are invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcLengthByPerimeter() {
        if(input2Valid() && input3Valid()){
            rectangle.setPerimeter(inputValue2);
            rectangle.setWidth(inputValue3);
            answer.setText(Formatter.formatOutput(rectangle.calcLengthByPerimeter(),precision));
        }else{
            Toast.makeText(getActivity(), "One or more inputs are invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcLengthByDiagonal() {
        if(input2Valid() && input3Valid()){
            rectangle.setDiagonal(inputValue2);
            rectangle.setWidth(inputValue3);
            answer.setText(Formatter.formatOutput(rectangle.calcLengthByDiagonal(),precision));
        }else{
            Toast.makeText(getActivity(), "One or more inputs are invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcLengthByArea() {
        if(input2Valid() && input3Valid()){
            rectangle.setArea(inputValue2);
            rectangle.setWidth(inputValue3);
            answer.setText(Formatter.formatOutput(rectangle.calcLengthByArea(),precision));
        }else{
            Toast.makeText(getActivity(), "One or more inputs are invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcWidthByPerimeter() {
        if(input2Valid() && input3Valid()){
            rectangle.setPerimeter(inputValue2);
            rectangle.setLength(inputValue3);
            answer.setText(Formatter.formatOutput(rectangle.calcWidthByPerimeter(),precision));
        }else{
            Toast.makeText(getActivity(), "One or more inputs are invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcWidthByDiagonal() {
        if(input2Valid() && input3Valid()){
            rectangle.setDiagonal(inputValue2);
            rectangle.setLength(inputValue3);
            answer.setText(Formatter.formatOutput(rectangle.calcWidthByDiagonal(),precision));
        }else{
            Toast.makeText(getActivity(), "One or more inputs are invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcWidthByArea() {
        if(input2Valid() && input3Valid()){
            rectangle.setArea(inputValue2);
            rectangle.setLength(inputValue3);
            answer.setText(Formatter.formatOutput(rectangle.calcWidthByArea(),precision));
        }else{
            Toast.makeText(getActivity(), "One or more inputs are invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcDiagonal() {
        if(input1Valid() && input2Valid()){
            rectangle.setLength(inputValue1);
            rectangle.setWidth(inputValue2);
            answer.setText(Formatter.formatOutput(rectangle.calcDiagonal(),precision));
        }else{
            Toast.makeText(getActivity(), "One or more inputs are invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcArea() {
        if(input1Valid() && input2Valid()){
            rectangle.setLength(inputValue1);
            rectangle.setWidth(inputValue2);
            answer.setText(Formatter.formatOutput(rectangle.calcArea(),precision));
        }else{
            Toast.makeText(getActivity(), "One or more inputs are invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean input1Valid(){
        try {
            inputValue1 = Double.parseDouble(input1.getText().toString());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean input2Valid(){
        try {
            inputValue2 = Double.parseDouble(input2.getText().toString());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean input3Valid(){
        try {
            inputValue3 = Double.parseDouble(input3.getText().toString());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void setInputListener() {
        inputChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                inputPos = i;
                if(answerPos ==2) {
                    switch (i) {
                        case 0:
                            inputView2.setText("Area");
                            inputView3.setText("Length (L)");
                            inputLayout2.setVisibility(View.VISIBLE);
                            inputLayout3.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            inputView2.setText("Diagonal");
                            inputView3.setText("Length (L)");
                            inputLayout2.setVisibility(View.VISIBLE);
                            inputLayout3.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            inputView2.setText("Perimeter");
                            inputView3.setText("Length (L)");
                            inputLayout2.setVisibility(View.VISIBLE);
                            inputLayout3.setVisibility(View.VISIBLE);
                            break;
                    }
                }
                if(answerPos == 3){
                    switch (i) {
                        case 0:
                            inputView2.setText("Area");
                            inputView3.setText("Width (W)");
                            inputLayout2.setVisibility(View.VISIBLE);
                            inputLayout3.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            inputView2.setText("Diagonal");
                            inputView3.setText("Width (W)");
                            inputLayout2.setVisibility(View.VISIBLE);
                            inputLayout3.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            inputView2.setText("Perimeter");
                            inputView3.setText("Width (W)");
                            inputLayout2.setVisibility(View.VISIBLE);
                            inputLayout3.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setInputChoiceAdapter() {
        inputAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.rect_input_array, R.layout.spinner_background);
        inputAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
        inputChoice.setAdapter(inputAdapter);

    }

    private void setAnswerChoiceListener() {
        answerChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                answerPos = i;
                inputChoice.setSelection(0);
                resetLayout();
                switch (i) {
                    case 0:
                        setInitialLayout();
                        break;
                    case 1:
                        setInitialLayout();
                        break;
                    case 2:
                        inputChoice.setVisibility(View.VISIBLE);
                        inputView2.setText("Area");
                        inputView3.setText("Length (L)");
                        inputLayout2.setVisibility(View.VISIBLE);
                        inputLayout3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        inputChoice.setVisibility(View.VISIBLE);
                        inputView2.setText("Area");
                        inputView3.setText("Width (W)");
                        inputLayout2.setVisibility(View.VISIBLE);
                        inputLayout3.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        setInitialLayout();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void setAnwerChoiceAdapter() {
        answerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.rect_calc_array, R.layout.spinner_background);
        answerAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
        answerChoice.setAdapter(answerAdapter);
    }

    private void initializeLayout(View rootView) {
        inputLayout1 = (LinearLayout)rootView.findViewById(R.id.rectangle_input_layout1);
        inputLayout2 = (LinearLayout)rootView.findViewById(R.id.rectangle_input_layout2);
        inputLayout3 = (LinearLayout)rootView.findViewById(R.id.rectangle_input_layout3);
        answerChoice = (Spinner)rootView.findViewById(R.id.rectangle_answer_choice);
        inputChoice = (Spinner)rootView.findViewById(R.id.rectangle_input_choice);
        answer = (RobotoTextView)rootView.findViewById(R.id.rectangle_answer);
        inputView1 = (RobotoTextView)rootView.findViewById(R.id.rectangle_input_view1);
        inputView2 = (RobotoTextView)rootView.findViewById(R.id.rectangle_input_view2);
        inputView3 = (RobotoTextView)rootView.findViewById(R.id.rectangle_input_view3);
        input1 = (EditText)rootView.findViewById(R.id.rectangle_input1);
        input2 = (EditText)rootView.findViewById(R.id.rectangle_input2);
        input3 = (EditText)rootView.findViewById(R.id.rectangle_input3);
        calcButton = (RobotoButton)rootView.findViewById(R.id.rect_calc);
        answerPos = 0;
        inputPos = 0;
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    private void resetLayout() {
        inputLayout1.setVisibility(View.INVISIBLE);
        inputLayout2.setVisibility(View.INVISIBLE);
        inputLayout3.setVisibility(View.INVISIBLE);
        inputChoice.setVisibility(View.INVISIBLE);
        answer.setText("");
        inputView1.setText("");
        inputView2.setText("");
        inputView3.setText("");
        input1.setText("");
        input2.setText("");
        input3.setText("");
        answerPos = 0;
        inputPos = 0;

    }

    private void setInitialLayout() {
        inputView1.setText("Length (L)");
        inputView2.setText("Width (W)");
        inputLayout1.setVisibility(View.VISIBLE);
        inputLayout2.setVisibility(View.VISIBLE);
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

    @Override
    public void onDestroy() {
        // Destroy the AdView.
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
