package com.jwilliams.machinistmate.app.Fragments;

import android.app.Fragment;
import android.os.Bundle;
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
import com.jwilliams.machinistmate.app.R;

/**
 * Created by John on 5/12/2014.
 */
public class TrapezoidFragment extends Fragment {

    private static final String KEY_POSITION="position";
    private Spinner trapChoice;
    private LinearLayout input1Layout;
    private LinearLayout input2Layout;
    private LinearLayout input3Layout;
    private LinearLayout input4Layout;
    private RobotoTextView input1View;
    private RobotoTextView input2View;
    private RobotoTextView input3View;
    private RobotoTextView input4View;
    private RobotoTextView answer;
    private RobotoTextView precisionView;
    private EditText input1;
    private EditText input2;
    private EditText input3;
    private EditText input4;
    private RobotoButton calcButton;
    private RobotoButton addButton;
    private RobotoButton minusButton;
    private boolean check;
    private int choice;
    private int precision;
    private ArrayAdapter<CharSequence> trapAdapter;
    private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private AdView adView;
    private AdRequest adRequest;


    public TrapezoidFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trapezoid_detail, container, false);
        setAd(rootView);
        initializeLayout(rootView);
        setSpinnerAdapter();
        setSpinnerListener();
        setCalcListener();
        setPrecisionListeners();
        return rootView;
    }

    private void setAd(View rootView){
        adView = (AdView)rootView.findViewById(R.id.trap_adView);
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
                switch(choice){
                    case 0:
                        calcArea();
                        break;
                    case 1:
                        calcBaseA();
                        break;
                    case 2:
                        calcBaseB();
                        break;
                    case 3:
                        calcSideC();
                        break;
                    case 4:
                        calcSideD();
                        break;
                    case 5:
                        calcHeight();
                        break;
                    case 6:
                        calcMedian();
                        break;
                    case 7:
                        calcPerimter();
                        break;
                    default:
                        calcArea();
                        break;
                }
            }
        });
    }

    private void calcPerimter() {
        double a = 0.0;
        double b = 0.0;
        double c = 0.0;
        double d = 0.0;

        try{
            a = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            b = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            c = Double.parseDouble(input3.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            d = Double.parseDouble(input4.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        if(!check){
            answer.setText(Formatter.formatOutput(a + b + c + d, precision));
        }else{
            Toast.makeText(getActivity(), "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcMedian() {
        double a = 0.0;
        double b = 0.0;

        try{
            a = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            b = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        if(!check){
            answer.setText(Formatter.formatOutput((a + b) / 2, precision));
        }else{
            Toast.makeText(getActivity(), "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcHeight() {
        double a = 0.0;
        double b = 0.0;
        double A = 0.0;

        try{
            a = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            b = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            A = Double.parseDouble(input3.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        if(!check){
            answer.setText(Formatter.formatOutput(2 * (A / (a + b)), precision));
        }else{
            Toast.makeText(getActivity(), "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcSideD() {
        double a = 0.0;
        double b = 0.0;
        double c = 0.0;
        double p = 0.0;

        try{
            a = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            b = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            c = Double.parseDouble(input3.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            p = Double.parseDouble(input4.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        if(!check){
            answer.setText(Formatter.formatOutput(p - a - b - c, precision));
        }else{
            Toast.makeText(getActivity(), "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcSideC() {
        double a = 0.0;
        double b = 0.0;
        double d = 0.0;
        double p = 0.0;

        try{
            a = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            b = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            d = Double.parseDouble(input3.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            p = Double.parseDouble(input4.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        if(!check){
            answer.setText(Formatter.formatOutput(p - a - b - d, precision));
        }else{
            Toast.makeText(getActivity(), "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcBaseB() {
        double a = 0.0;
        double h = 0.0;
        double A = 0.0;

        try{
            a = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            h = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            A = Double.parseDouble(input3.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        if(!check){
            answer.setText(Formatter.formatOutput(2 * (A / h) - a, precision));
        }else{
            Toast.makeText(getActivity(), "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcBaseA() {
        double b = 0.0;
        double h = 0.0;
        double A = 0.0;

        try{
            b = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            h = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            A = Double.parseDouble(input3.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        if(!check){
            answer.setText(Formatter.formatOutput(2 * (A / h) - b, precision));
        }else{
            Toast.makeText(getActivity(), "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calcArea() {
        double a = 0.0;
        double b = 0.0;
        double h = 0.0;

        try{
            a = Double.parseDouble(input1.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            b = Double.parseDouble(input2.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        try{
            h = Double.parseDouble(input3.getText().toString());
        }catch(NumberFormatException e){
            check = true;
        }

        if(!check){
            answer.setText(Formatter.formatOutput(((a + b) / 2) * h, precision));
        }else{
            Toast.makeText(getActivity(), "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void setSpinnerListener() {
        AdapterView.OnItemSelectedListener trapSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choice = i;
                switch(i){
                    case 0:
                        clearLayouts();
                        setInitialLayout();
                        set3Layouts();
                        break;
                    case 1:
                        clearLayouts();
                        input1View.setText("Base (b)");
                        input2View.setText("Height (h)");
                        input3View.setText("Area");
                        set3Layouts();
                        break;
                    case 2:
                        clearLayouts();
                        input1View.setText("Base (a)");
                        input2View.setText("Height (h)");
                        input3View.setText("Area");
                        set3Layouts();
                        break;
                    case 3:
                        clearLayouts();
                        input1View.setText("Base (a)");
                        input2View.setText("Base (b)");
                        input3View.setText("Side (d)");
                        input4View.setText("Perimeter");
                        set4Layouts();
                        break;
                    case 4:
                        clearLayouts();
                        input1View.setText("Base (a)");
                        input2View.setText("Base (b)");
                        input3View.setText("Side (c)");
                        input4View.setText("Perimeter");
                        set4Layouts();
                        break;
                    case 5:
                        clearLayouts();
                        input1View.setText("Base (a)");
                        input2View.setText("Base (b)");
                        input3View.setText("Area");
                        set3Layouts();
                        break;
                    case 6:
                        clearLayouts();
                        input1View.setText("Base (a)");
                        input2View.setText("Base (b)");
                        input1Layout.setVisibility(View.VISIBLE);
                        input2Layout.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        clearLayouts();
                        input1View.setText("Base (a)");
                        input2View.setText("Base (b)");
                        input3View.setText("Side (c)");
                        input4View.setText("Side (d)");
                        set4Layouts();
                        break;
                    default:
                        clearLayouts();
                        setInitialLayout();
                        set3Layouts();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        trapChoice.setOnItemSelectedListener(trapSelectedListener);
    }

    private void setSpinnerAdapter() {
        trapAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.trap_calc_array, R.layout.spinner_background);
        trapAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
        trapChoice.setAdapter(trapAdapter);
    }

    private void clearLayouts(){
        input1Layout.setVisibility(View.INVISIBLE);
        input2Layout.setVisibility(View.INVISIBLE);
        input3Layout.setVisibility(View.INVISIBLE);
        input4Layout.setVisibility(View.INVISIBLE);
    }

    private void set3Layouts(){
        input1Layout.setVisibility(View.VISIBLE);
        input2Layout.setVisibility(View.VISIBLE);
        input3Layout.setVisibility(View.VISIBLE);
        input4Layout.setVisibility(View.INVISIBLE);
    }

    private void set4Layouts(){
        input1Layout.setVisibility(View.VISIBLE);
        input2Layout.setVisibility(View.VISIBLE);
        input3Layout.setVisibility(View.VISIBLE);
        input4Layout.setVisibility(View.VISIBLE);
    }

    private void initializeLayout(View rootView) {
        trapChoice = (Spinner)rootView.findViewById(R.id.trap_spinner);
        input1Layout =(LinearLayout)rootView.findViewById(R.id.trap_input1_layout);
        input2Layout =(LinearLayout)rootView.findViewById(R.id.trap_input2_layout);
        input3Layout =(LinearLayout)rootView.findViewById(R.id.trap_input3_layout);
        input4Layout =(LinearLayout)rootView.findViewById(R.id.trap_input4_layout);
        input1View = (RobotoTextView)rootView.findViewById(R.id.trap_input1_view);
        input2View = (RobotoTextView)rootView.findViewById(R.id.trap_input2_view);
        input3View = (RobotoTextView)rootView.findViewById(R.id.trap_input3_view);
        input4View = (RobotoTextView)rootView.findViewById(R.id.trap_input4_view);
        answer = (RobotoTextView)rootView.findViewById(R.id.trap_answer);
        precisionView = (RobotoTextView)rootView.findViewById(R.id.trap_precision_view);
        input1 = (EditText)rootView.findViewById(R.id.trap_input1);
        input2 = (EditText)rootView.findViewById(R.id.trap_input2);
        input3 = (EditText)rootView.findViewById(R.id.trap_input3);
        input4 = (EditText)rootView.findViewById(R.id.trap_input4);
        calcButton = (RobotoButton)rootView.findViewById(R.id.trap_calc_button);
        addButton = (RobotoButton)rootView.findViewById(R.id.trap_add_button);
        minusButton = (RobotoButton)rootView.findViewById(R.id.trap_minus_button);
        check = false;
        choice = 0;
        precision = 2;
        precisionView.setText(Integer.toString(precision));
        setInitialLayout();
    }

    private void setInitialLayout() {
        input1View.setText("Base (a)");
        input2View.setText("Base (b)");
        input3View.setText("Height (h)");
    }

    public static TrapezoidFragment newInstance(int position) {
        TrapezoidFragment frag=new TrapezoidFragment();
        Bundle args=new Bundle();

        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return(frag);
    }

/*    static String getTitle(Context ctxt, int position) {
        return(String.format(ctxt.getString(R.string.trapezoid), position + 1));
    }*/

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
