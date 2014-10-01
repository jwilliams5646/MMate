package com.jwilliams.machinistmate.app.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoButton;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoTextView;
import com.jwilliams.machinistmate.app.Formatter;
import com.jwilliams.machinistmate.app.R;

/**
 * Created by john.williams on 5/19/2014.
 */
public class RightTriangleFragment extends Fragment {

    private static final String KEY_POSITION="position";
    private EditText sideH;
    private EditText sideO;
    private EditText sideA;
    private EditText angleX;
    private EditText angleY;
    private RobotoTextView areaAnswer;
    private RobotoTextView perimeterAnswer;
    private RobotoTextView precisionView;
    private Spinner angleXSpinner;
    private Spinner angleYSpinner;
    private RobotoButton addButton;
    private RobotoButton minusButton;
    private RobotoButton calcButton;
    private RobotoButton clearButton;
    private RobotoButton questionButton;
    private int xPos;
    private int yPos;
    private int precision;
    private ArrayAdapter<CharSequence> adapter;
    private View rootView;
    private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private AdView adView;
    private AdRequest adRequest;


    public RightTriangleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.right_triangle, container, false);
        setAd(rootView);
        initializeLayout(rootView);
        setSpinnerAdapters();
        setSpinnerXListener();
        setSpinnerYListener();
        setClearButtonListener();
        setQuestionButtonListener();
        setCalcButtonListener();
        setPrecisionListeners();
        return rootView;
    }

    private void setAd(View rootView){
        adView = (AdView)rootView.findViewById(R.id.rt_adView);
        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(TEST_DEVICE_ID)
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
                    Toast.makeText(getActivity().getApplicationContext(), "Max precision reached.", Toast.LENGTH_SHORT).show();
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

    private void setCalcButtonListener() {
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double h = 0.0;
                double o = 0.0;
                double a = 0.0;
                double x = 0.0;
                double y = 0.0;
                double area = 0.0;
                double p = 0.0;
                boolean ch = false;
                boolean co = false;
                boolean ca = false;
                boolean cx = false;
                boolean cy = false;
                int count = 0;

                try{
                    h = Double.parseDouble(sideH.getText().toString());
                }catch(NumberFormatException e){
                    ch = true;
                    count++;
                }
                try{
                    o = Double.parseDouble(sideO.getText().toString());
                }catch(NumberFormatException e){
                    co = true;
                    count++;
                }
                try{
                    a = Double.parseDouble(sideA.getText().toString());
                }catch(NumberFormatException e){
                    ca = true;
                    count++;
                }
                try{
                    x = Double.parseDouble(angleX.getText().toString());
                }catch(NumberFormatException e){
                    cx = true;
                    count++;
                }
                try{
                    y = Double.parseDouble(angleY.getText().toString());
                }catch(NumberFormatException e){
                    cy = true;
                    count++;
                }
                if(count > 4){
                    Toast.makeText(getActivity(), "Input at least 2 values", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(count < 2){
                    Toast.makeText(getActivity(), "Input only 2 values", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!ch && !co && ca && cx && cy){
                    ss_ho(h, o, a, x, y, p, area);
                }
                else if(!ch && co && !ca && cx && cy){
                    ss_ha(h, o, a, x, y, p, area);
                }
                else if(!ch && co && ca && !cx && cy){
                    sa_hx(h, o, a, x, y, p, area);
                }
                else if(!ch && co && ca && cx && !cy){
                    sa_hy(h, o, a, x, y, p, area);
                }
                else if(ch && !co && !ca && cx && cy){
                    ss_oa(h, o, a, x, y, p, area);
                }
                else if(ch && !co && ca && !cx && cy){
                    sa_ox(h, o, a, x, y, p, area);
                }
                else if(ch && !co && ca && cx && !cy){
                    sa_oy(h, o, a, x, y, p, area);
                }
                else if(ch && co && !ca && !cx && cy){
                    sa_ax(h, o, a, x, y, p, area);
                }
                else if(ch && co && !ca && cx && !cy){
                    sa_ay(h, o, a, x, y, p, area);
                }
            }

            private void sa_ay(double h, double o, double a, double x, double y, double p, double area) {
                if(y < 1 || y >= 90){
                    setYToast();
                    return;
                }
                if(yPos == 1){
                    y = Math.toDegrees(y);
                }
                x = 90 - y;
                h = a/ Math.sin(Math.toRadians(y));
                o = h* Math.sin(Math.toRadians(x));
                sideH.setText(Formatter.formatOutput(h, precision));
                sideO.setText(Formatter.formatOutput(o, precision));
                setAngleX(x);
                setAreaPeri(h, o, a);
            }

            private void sa_ax(double h, double o, double a, double x, double y, double p, double area) {
                if(x < 1 || x >= 90){
                    setXToast();
                    return;
                }
                if(xPos == 1){
                    x = Math.toDegrees(x);
                }
                y = 90 - x;
                h = a/ Math.sin(Math.toRadians(y));
                o = h* Math.sin(Math.toRadians(x));
                sideH.setText(Formatter.formatOutput(h, precision));
                sideO.setText(Formatter.formatOutput(o, precision));
                setAngleY(y);
                setAreaPeri(h, o, a);
            }

            private void sa_oy(double h, double o, double a, double x, double y, double p, double area) {
                if(y < 1 || y >= 90){
                    setYToast();
                    return;
                }
                if(yPos == 1){
                    y = Math.toDegrees(y);
                }
                x = 90 - y;
                h = a/ Math.sin(Math.toRadians(y));
                a = h* Math.cos(Math.toRadians(x));
                sideH.setText(Formatter.formatOutput(h, precision));
                sideA.setText(Formatter.formatOutput(a, precision));
                setAngleX(x);
                setAreaPeri(h, o, a);

            }

            private void sa_ox(double h, double o, double a, double x, double y, double p, double area) {
                if(x < 1 || x >= 90){
                    setXToast();
                    return;
                }
                if(xPos == 1){
                    x = Math.toDegrees(x);
                }
                y = 90 - x;
                h = a/ Math.sin(Math.toRadians(y));
                a = h* Math.cos(Math.toRadians(x));
                sideH.setText(Formatter.formatOutput(h, precision));
                sideA.setText(Formatter.formatOutput(a, precision));
                setAngleY(y);
                setAreaPeri(h, o, a);
            }

            private void ss_oa(double h, double o, double a, double x, double y, double p, double area) {
                h = Math.sqrt(o * o + a * a);
                x = Math.toDegrees(Math.atan(o / a));
                y = 90 - x;
                sideH.setText(Formatter.formatOutput(h, precision));
                setAngleX(x);
                setAngleY(y);
                setAreaPeri(h, o, a);
            }

            private void sa_hy(double h, double o, double a, double x, double y, double p, double area) {
                if(yPos == 1){
                    y = Math.toDegrees(x);
                }
                x = 90 - y;
                o = h* Math.sin(Math.toRadians(x));
                a = h* Math.cos(Math.toRadians(x));
                sideO.setText(Formatter.formatOutput(o, precision));
                sideA.setText(Formatter.formatOutput(a, precision));
                setAngleX(x);
                setAreaPeri(h, o, a);
            }

            private void sa_hx(double h, double o, double a, double x, double y, double p, double area) {
                if(x < 1 || x >= 90){
                    setXToast();
                    return;
                }
                if(xPos == 1){
                    x = Math.toDegrees(x);
                }
                y = 90 - x;
                o = h* Math.sin(Math.toRadians(x));
                a = h* Math.cos(Math.toRadians(x));
                sideO.setText(Formatter.formatOutput(o, precision));
                sideA.setText(Formatter.formatOutput(a, precision));
                setAngleY(y);
                setAreaPeri(h,o,a);
            }

            private void ss_ha(double h, double o, double a, double x, double y, double p, double area) {
                if(h<a) {
                    Toast.makeText(getActivity(), "Side H must always be longer than side A.", Toast.LENGTH_SHORT).show();
                    return;
                }
                o = Math.sqrt(h * h - a * a);
                x = Math.toDegrees(Math.acos(a / h));
                y = 90 - x;
                sideO.setText(Formatter.formatOutput(o, precision));
                setAngleX(x);
                setAngleY(y);
                setAreaPeri(h, o, a);
            }

            private void ss_ho(double h, double o, double a, double x, double y, double p, double area) {
                a = Math.sqrt(h * h - o * o);
                x = Math.toDegrees(Math.asin(o / h));
                y = 90 - x;
                sideA.setText(Formatter.formatOutput(a, precision));
                setAngleX(x);
                setAngleY(y);
                setAreaPeri(h, o, a);
            }

            public void setAngleX(double x){
                if(xPos == 0){
                    angleX.setText(Formatter.formatOutput(x, precision));
                }else{
                    angleX.setText(Formatter.formatOutput(Math.toRadians(x), precision));
                }
            }

            public void setAngleY(double y){
                if(yPos == 0){
                    angleY.setText(Formatter.formatOutput(y, precision));
                }else{
                    angleY.setText(Formatter.formatOutput(Math.toRadians(y), precision));
                }
            }

            private void setAreaPeri(double h, double o, double a) {
                areaAnswer.setText(Formatter.formatOutput(getArea(o, a), precision));
                perimeterAnswer.setText(Formatter.formatOutput(getPeri(h, o, a), precision));
            }

            private double getArea(double o, double a){
                return (o+a)/2;
            }

            private double getPeri(double h, double o, double a){
                return h+o+a;
            }

            private void setYToast() {
                Toast.makeText(getActivity(), "Angle y can't be greater than 90 or less than 1", Toast.LENGTH_SHORT).show();
                return;
            }

            private void setXToast() {
                Toast.makeText(getActivity(), "Angle x can't be greater than 90 or less than 1", Toast.LENGTH_SHORT).show();
                return;
            }

        });
    }

    private void setQuestionButtonListener() {
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(getActivity());

                // Set GUI of login screen
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.info_dialog);
                //Set dialog text
                final RobotoTextView dialog = (RobotoTextView)d.findViewById(R.id.dialog);
                dialog.setText("Input at least 2 values (angles only will not work).\n" +
                        "Side H must always be longer than side A.\n" +
                        "These calculations use the law of sine, cosine and/or tangent.");
                // Make dialog box visible.
                d.show();
            }});
    }

    private void setClearButtonListener() {
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
    }

    private void setSpinnerYListener() {
        angleYSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int i, long id) {
                yPos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}});
    }

    private void setSpinnerXListener() {
        angleXSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int i, long id) {
                xPos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}});
    }

    private void initializeLayout(View rootView) {
        sideH = (EditText)rootView.findViewById(R.id.rt_h_input);
        sideO = (EditText)rootView.findViewById(R.id.rt_o_input);
        sideA = (EditText)rootView.findViewById(R.id.rt_a_input);
        angleX = (EditText)rootView.findViewById(R.id.rt_x_input);
        angleY = (EditText)rootView.findViewById(R.id.rt_y_input);
        areaAnswer = (RobotoTextView)rootView.findViewById(R.id.rt_area_answer);
        perimeterAnswer = (RobotoTextView)rootView.findViewById(R.id.rt_perimeter_answer);
        precisionView = (RobotoTextView)rootView.findViewById(R.id.rt_precision_view);
        angleXSpinner = (Spinner)rootView.findViewById(R.id.rt_x_spinner);
        angleYSpinner = (Spinner)rootView.findViewById(R.id.rt_y_spinner);
        calcButton = (RobotoButton)rootView.findViewById(R.id.rt_calc_button);
        addButton = (RobotoButton)rootView.findViewById(R.id.rt_add_button);
        minusButton = (RobotoButton)rootView.findViewById(R.id.rt_minus_button);
        clearButton = (RobotoButton)rootView.findViewById(R.id.rt_clear_button);
        questionButton = (RobotoButton)rootView.findViewById(R.id.rt_question_button);
        xPos = 0;
        yPos = 0;
        precision = 2;
        precisionView.setText(Integer.toString(precision));
    }

    private void clear(){
        sideH.setText("");
        sideO.setText("");
        sideA.setText("");
        angleX.setText("");
        angleY.setText("");
        areaAnswer.setText("");
        perimeterAnswer.setText("");
    }

    private void setSpinnerAdapters() {
        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.degree_rad_array, R.layout.spinner_background);
        adapter.setDropDownViewResource(R.layout.spinner_drop_down);
        angleXSpinner.setAdapter(adapter);
        angleYSpinner.setAdapter(adapter);
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
