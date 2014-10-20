package com.jwilliams.machinistmate.app.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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
import com.jwilliams.machinistmate.app.GeometryClasses.ObliqueTriangle;
import com.jwilliams.machinistmate.app.R;

import java.text.DecimalFormat;

/**
 * Created by John on 5/12/2014.
 */
public class ObliqueTriangleFragment extends Fragment {

    private EditText sideAInput;
    private EditText sideBInput;
    private EditText sideCInput;
    private EditText angleXInput;
    private EditText angleYInput;
    private EditText angleZInput;
    private RobotoTextView areaAnswer;
    private RobotoTextView heightAnswer;
    private RobotoTextView perimeterAnswer;
    private Spinner angleXSpinner;
    private Spinner angleYSpinner;
    private Spinner angleZSpinner;
    private RobotoButton calcButton;
    private RobotoButton clearButton;
    private RobotoButton questionButton;
    private int spinnerX;
    private int spinnerY;
    private int spinnerZ;
    private int precision;
    private ArrayAdapter<CharSequence> angleAdapter;
    private View rootView;
    private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private AdView adView;
    private AdRequest adRequest;
    //private static final String AD_UNIT_ID = "ca-app-pub-6986976933268044/5924552212";

    public ObliqueTriangleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.oblique_triangle_detail, container, false);
        setAd(rootView);
        initializeLayout(rootView);
        return rootView;
    }

    private void setAd(View rootView){
        adView = (AdView)rootView.findViewById(R.id.oblique_adView);
        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(TEST_DEVICE_ID)
                .build();
        adView.loadAd(adRequest);
    }

    private void initializeLayout(View rootView) {
        sideAInput = (EditText)rootView.findViewById(R.id.oblique_a_input);
        sideBInput = (EditText)rootView.findViewById(R.id.oblique_b_input);
        sideCInput = (EditText)rootView.findViewById(R.id.oblique_c_input);
        angleXInput = (EditText)rootView.findViewById(R.id.oblique_x_input);
        angleYInput = (EditText)rootView.findViewById(R.id.oblique_y_input);
        angleZInput = (EditText)rootView.findViewById(R.id.oblique_z_input);
        areaAnswer = (RobotoTextView)rootView.findViewById(R.id.oblique_area_answer);
        heightAnswer = (RobotoTextView)rootView.findViewById(R.id.oblique_height_answer);
        perimeterAnswer = (RobotoTextView)rootView.findViewById(R.id.oblique_perimeter_answer);
        angleXSpinner = (Spinner)rootView.findViewById(R.id.oblique_x_spinner);
        angleYSpinner = (Spinner)rootView.findViewById(R.id.oblique_y_spinner);
        angleZSpinner = (Spinner)rootView.findViewById(R.id.oblique_z_spinner);
        calcButton = (RobotoButton)rootView.findViewById(R.id.oblique_calc_button);
        clearButton = (RobotoButton)rootView.findViewById(R.id.oblique_clear_button);
        questionButton = (RobotoButton)rootView.findViewById(R.id.ot_question_button);
        spinnerX = 0;
        spinnerY = 0;
        spinnerZ = 0;
        setAngleAdapters();
        setAngleXListener();
        setAngleYListener();
        setAngleZListener();
        setClearListener();
        setCalcButtonListener();
        setQuestionButtonListener();
        precision = 2;
    }

    private void setQuestionButtonListener() {
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(getActivity());

                // Set GUI of login screen
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.info_dialog);
                final RobotoTextView dialog = (RobotoTextView)d.findViewById(R.id.dialog);
                dialog.setText("Input at least 3 values (angles only will not work).\n" +
                        "Only angle (z) can be 90 degrees or greater.\n" +
                        "These calculations use the law of sine, cosine and/or tangent.");
                // Make dialog box visible.
                d.show();
            }
        });
    }

    private void setCalcButtonListener() {
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObliqueTriangle ot = new ObliqueTriangle();
                double a = 0.0;
                double b = 0.0;
                double c = 0.0;
                double x = 0.0;
                double y = 0.0;
                double z = 0.0;
                boolean ca = true;
                boolean cb = true;
                boolean cc = true;
                boolean cx = true;
                boolean cy = true;
                boolean cz = true;
                int count = 0;
                //DecimalFormat df = new DecimalFormat("##.####");

                try {
                    a = Double.parseDouble(sideAInput.getText().toString());
                    ot.setA(Double.parseDouble(sideAInput.getText().toString()));
                } catch (NumberFormatException e) {
                    ca=false;
                    count++;
                    Log.d("Side (a) has no value", Integer.toString(count));
                }

                try {
                    b = Double.parseDouble(sideBInput.getText().toString());
                    ot.setB(Double.parseDouble(sideBInput.getText().toString()));
                } catch (NumberFormatException e) {
                    cb=false;
                    count++;
                    Log.d("Side (b) has no value", Integer.toString(count));
                }

                try {
                    c = Double.parseDouble(sideCInput.getText().toString());
                    ot.setC(Double.parseDouble(sideCInput.getText().toString()));
                } catch (NumberFormatException e) {
                    cc=false;
                    count++;
                    Log.d("Side (c) has no value", Integer.toString(count));
                }

                try {
                    x = Double.parseDouble(angleXInput.getText().toString());
                    ot.setX(Double.parseDouble(angleXInput.getText().toString()));
                } catch (NumberFormatException e) {
                    cx=false;
                    count++;
                    Log.d("Angle (x) has no value", Integer.toString(count));
                }

                try {
                    y = Double.parseDouble(angleYInput.getText().toString());
                    ot.setY(Double.parseDouble(angleYInput.getText().toString()));
                } catch (NumberFormatException e) {
                    cy=false;
                    count++;
                    Log.d("Angle (y) has no value", Integer.toString(count));
                }

                try {
                    z = Double.parseDouble(angleZInput.getText().toString());
                    ot.setZ(Double.parseDouble(angleZInput.getText().toString()));
                } catch (NumberFormatException e) {
                    cz=false;
                    count++;
                    Log.d("Angle (z) has no value", Integer.toString(count));
                }

                if(count > 3){
                    Toast.makeText(getActivity(), "You need at least 3 values to proceed", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(count < 3){
                    Toast.makeText(getActivity(), "Input a maximum of 3 values", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(cx && cy && cz){
                    Toast.makeText(getActivity(), "Triangle can not be solved with only angles.  This is the triangle equivalent of dividing by zero.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(x >= 90 || y >= 90){
                    Toast.makeText(getActivity(), "Only angle (z) can be 90 degrees or greater", Toast.LENGTH_SHORT).show();
                    return;
                }

                //a-b-c -> x-y-z
                if (cc && ca && cb){
                    if(b < c || b < a){
                        Toast.makeText(getActivity(), "Side (b) is the base of the triangle and must be the longest side.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ot.calcFromABC();
                    if(ot.isXNaN() || ot.isYNaN() || ot.isZNaN()){
                        Toast.makeText(getActivity(), "This is not a triangle.", Toast.LENGTH_SHORT).show();
                        return;
                    //sss_abc(a, b, c, x, y ,z);
                }
                //a-c-z -> x-y-b
                else if (cc && ca && !cb && !cy && !cx && cz){
                    sas_xyb(a, b, c, x, y ,z);       // side-angle-side
                }
                //c-b-x -> a-y-z
                else if (cc && !ca && cb && !cy && cx && !cz){
                    sas_ayz(a, b, c, x, y ,z);       // side-angle-side
                }
                //a-b-y -> c-x-z
                else if (!cc && ca && cb && cy && !cx && !cz){
                    sas_cxz(a, b, c, x, y ,z);      // side-angle-side
                }
                //c-a-y -> b-x-z
                else if (cc && ca && !cb && cy && !cx && !cz){
                    ssa_bxz(a, b, c, x, y ,z);       // side-side-angle
                }
                //c-a-x -> b-y-z
                else if (cc && ca && !cb && !cy && cx && !cz){
                    ssa_byz(a, b, c, x, y ,z);       // side-side-angle
                }

                else if (cc && !ca && cb && cy && !cx && !cz){
                    ssa_axz(a, b, c, x, y ,z);       // side-side-angle
                }

                else if (cc && !ca && cb && !cy && !cx && cz){
                    ssa_ayx(a, b, c, x, y ,z);       // side-side-angle
                }

                else if (!cc && ca && cb && !cy && cx && !cz){
                    ssa_cyz(a, b, c, x, y ,z);      // side-side-angle
                }

                else if (!cc && ca && cb && !cy && !cx && cz){
                    ssa_cyx(a, b, c, x, y ,z);       // side-side-angle
                }

                else if (cc && !ca && !cb && cy && cx && !cz){
                    asa_abz(a, b, c, x, y ,z);       // angle-side-angle
                }

                else if (cc && !ca && !cb && cy && !cx && cz){
                    asa_abx(a, b, c, x, y ,z);       // angle-side-angle
                }

                else if (cc && !ca && !cb && !cy && cx && cz){
                    asa_aby(a, b, c, x, y ,z);      // angle-side-angle
                }

                else if (!cc && ca && !cb && cy && cx && !cz){
                    asa_cbz(a, b, c, x, y ,z);       // angle-side-angle
                }

                else if (!cc && ca && !cb && cy && !cx && cz){
                    asa_cbx(a, b, c, x, y ,z);       // angle-side-angle
                }

                else if (!cc && ca && !cb && !cy && cx && cz){
                    asa_cby(a, b, c, x, y ,z);       // angle-side-angle
                }

                else if (!cc && !ca && cb && cy && cx && !cz){
                    asa_caz(a, b, c, x, y ,z);      // angle-side-angle
                }

                else if (!cc && !ca && cb && cy && !cx && cz){
                    asa_cax(a, b, c, x, y ,z);       // angle-side-angle
                }

                else if (!cc && !ca && cb && !cy && cx && cz){
                    asa_cay(a, b, c, x, y ,z);       // angle-side-angle
                }




            }

            private void asa_cay(double a, double b, double c, double x, double y, double z) {
                if (spinnerX == 0) {
                    x = Math.toRadians(x);
                }
                if (spinnerZ == 0) {
                    z = Math.toRadians(z);
                }
                y = 180 - (Math.toDegrees(x) + Math.toDegrees(z));
                if (x<=0){
                    Toast.makeText(getActivity(), "This is not a triangle", Toast.LENGTH_SHORT).show();
                    return;
                }
                c = b* Math.sin(y)/ Math.sin(z);
                a = b* Math.sin(x)/ Math.sin(z);
                setAreaPeriHeight(a,b,c);
                sideCInput.setText(Formatter.formatOutput(c, precision));
                sideAInput.setText(Formatter.formatOutput(a, precision));
                setAngleY(y);
            }

            private void asa_cax(double a, double b, double c, double x, double y, double z) {
                if (spinnerY == 0) {
                    y = Math.toRadians(y);
                }
                if (spinnerZ == 0) {
                    z = Math.toRadians(z);
                }
                x = 180 - (Math.toDegrees(y) + Math.toDegrees(z));
                if (x<=0){
                    Toast.makeText(getActivity(), "This is not a triangle", Toast.LENGTH_SHORT).show();
                    return;
                }
                c = b* Math.sin(y)/ Math.sin(z);
                a = b* Math.sin(x)/ Math.sin(z);
                setAreaPeriHeight(a,b,c);
                sideCInput.setText(Formatter.formatOutput(c, precision));
                sideAInput.setText(Formatter.formatOutput(a, precision));
                setAngleX(x);
            }

            private void asa_caz(double a, double b, double c, double x, double y, double z) {
                if (spinnerY == 0) {
                    y = Math.toRadians(y);
                }
                if (spinnerX == 0) {
                    x = Math.toRadians(x);
                }
                z = 180 - (Math.toDegrees(y) + Math.toDegrees(x));
                if (z<=0){
                    Toast.makeText(getActivity(), "This is not a triangle", Toast.LENGTH_SHORT).show();
                    return;
                }
                c = b* Math.sin(y)/ Math.sin(z);
                a = b* Math.sin(x)/ Math.sin(z);
                setAreaPeriHeight(a,b,c);
                sideCInput.setText(Formatter.formatOutput(c, precision));
                sideAInput.setText(Formatter.formatOutput(a, precision));
                setAngleZ(z);
            }

            private void asa_cby(double a, double b, double c, double x, double y, double z) {
                if (spinnerZ == 0) {
                    z = Math.toRadians(z);
                }
                if (spinnerX == 0) {
                    x = Math.toRadians(x);
                }
                y = 180 - (Math.toDegrees(z) + Math.toDegrees(x));
                if (y<=0){
                    Toast.makeText(getActivity(), "This is not a triangle", Toast.LENGTH_SHORT).show();
                    return;
                }
                c = a* Math.sin(y)/ Math.sin(x);
                b = a* Math.sin(z)/ Math.sin(x);
                setAreaPeriHeight(a,b,c);
                sideCInput.setText(Formatter.formatOutput(c, precision));
                sideBInput.setText(Formatter.formatOutput(b, precision));
                setAngleY(y);
            }

            private void asa_cbx(double a, double b, double c, double x, double y, double z) {
                if (spinnerZ == 0) {
                    z = Math.toRadians(z);
                }
                if (spinnerY == 0) {
                    y = Math.toRadians(y);
                }
                x = 180 - (Math.toDegrees(z) + Math.toDegrees(y));
                if (x<=0){
                    Toast.makeText(getActivity(), "This is not a triangle", Toast.LENGTH_SHORT).show();
                    return;
                }
                c = a* Math.sin(y)/ Math.sin(x);
                b = a* Math.sin(z)/ Math.sin(x);
                setAreaPeriHeight(a,b,c);
                sideCInput.setText(Formatter.formatOutput(c, precision));
                sideBInput.setText(Formatter.formatOutput(b, precision));
                setAngleX(x);
            }

            private void asa_cbz(double a, double b, double c, double x, double y, double z) {
                if (spinnerX == 0) {
                    x = Math.toRadians(x);
                }
                if (spinnerY == 0) {
                    y = Math.toRadians(y);
                }
                z = 180 - (Math.toDegrees(x) + Math.toDegrees(y));
                if (z<=0){
                    Toast.makeText(getActivity(), "This is not a triangle", Toast.LENGTH_SHORT).show();
                    return;
                }
                c = a* Math.sin(y)/ Math.sin(x);
                b = a* Math.sin(z)/ Math.sin(x);
                setAreaPeriHeight(a,b,c);
                sideCInput.setText(Formatter.formatOutput(c, precision));
                sideBInput.setText(Formatter.formatOutput(b, precision));
                setAngleZ(z);
            }

            private void asa_aby(double a, double b, double c, double x, double y, double z) {
                if (spinnerX == 0) {
                    x = Math.toRadians(x);
                }
                if (spinnerZ == 0) {
                    z = Math.toRadians(z);
                }
                y = 180 - (Math.toDegrees(x) + Math.toDegrees(z));
                if (y<=0){
                    Toast.makeText(getActivity(), "This is not a triangle", Toast.LENGTH_SHORT).show();
                    return;
                }
                a = c* Math.sin(x)/ Math.sin(y);
                b = c* Math.sin(z)/ Math.sin(y);
                setAreaPeriHeight(a,b,c);
                sideAInput.setText(Formatter.formatOutput(a, precision));
                sideBInput.setText(Formatter.formatOutput(b, precision));
                setAngleY(y);
            }

            private void asa_abx(double a, double b, double c, double x, double y, double z) {
                if (spinnerY == 0) {
                    y = Math.toRadians(y);
                }
                if (spinnerZ == 0) {
                    z = Math.toRadians(z);
                }
                x = 180 - (Math.toDegrees(y) + Math.toDegrees(z));
                if (x <= 0){
                    Toast.makeText(getActivity(), "This is not a triangle", Toast.LENGTH_SHORT).show();
                    return;
                }
                a = c* Math.sin(x)/ Math.sin(y);
                b = c* Math.sin(z)/ Math.sin(y);
                setAreaPeriHeight(a,b,c);
                sideAInput.setText(Formatter.formatOutput(a, precision));
                sideBInput.setText(Formatter.formatOutput(b, precision));
                setAngleX(x);
            }

            private void asa_abz(double a, double b, double c, double x, double y, double z) {
                if(spinnerY == 1){
                    y = Math.toDegrees(y);
                }
                if(spinnerX == 1){
                    x = Math.toDegrees(x);
                }
                z = 180 - (y + x);
                if (z <= 0){
                    Toast.makeText(getActivity(), "This is not a triangle", Toast.LENGTH_SHORT).show();
                    return;
                }
                a = c* Math.sin(Math.toRadians(x) / Math.sin(Math.toRadians(y)));
                b = c* Math.sin(Math.toRadians(z) / Math.sin(Math.toRadians(y)));
                setAreaPeriHeight(a,b,c);
                sideAInput.setText(Formatter.formatOutput(a, precision));
                sideBInput.setText(Formatter.formatOutput(b, precision));
                setAngleZ(z);
            }

            private void ssa_cyx(double a, double b, double c, double x, double y, double z) {
                if(spinnerZ == 0){
                    z = Math.toRadians(z);
                }
                x = Math.toDegrees(Math.asin(a * Math.sin(z) / b));
                y = 180.0 - (Math.toDegrees(z) + x);
                c = Math.sqrt(a * a + b * b - 2 * a * b * Math.cos(y));
                setAreaPeriHeight(a,b,c);
                sideCInput.setText(Formatter.formatOutput(c, precision));
                setAngleY(y);
                setAngleX(x);
            }

            private void ssa_cyz(double a, double b, double c, double x, double y, double z) {
                if(spinnerX == 0){
                    x = Math.toRadians(x);
                }
                z = Math.toDegrees(Math.asin(b * Math.sin(x) / a));
                y = 180.0 - (Math.toDegrees(x) + z);
                c = Math.sqrt(a * a + b * b - 2 * a * b * Math.cos(Math.toRadians(y)));
                setAreaPeriHeight(a,b,c);
                sideCInput.setText(Formatter.formatOutput(c, precision));
                setAngleY(y);
                setAngleZ(z);
            }

            private void ssa_ayx(double a, double b, double c, double x, double y, double z) {
                if(c > b && z >90){
                    Toast.makeText(getActivity(), "Side b cannot be shorter than side c if angle (z) is greater than 90 degrees", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(spinnerZ == 0){
                    z = Math.toRadians(z);
                }
                y = Math.toDegrees(Math.asin(c * Math.sin(z) / b));
                x = 180.0 - (y + Math.toDegrees(z));
                a = Math.sqrt(c * c + b * b - 2 * c * b * Math.cos(Math.toRadians(x)));
                setAreaPeriHeight(a,b,c);
                sideAInput.setText(Formatter.formatOutput(a, precision));
                setAngleY(y);
                setAngleX(x);
            }

            private void ssa_axz(double a, double b, double c, double x, double y, double z) {
                if(spinnerY == 0){
                    y = Math.toRadians(y);
                }
                z = Math.toDegrees(Math.asin(b * Math.sin(y) / c));
                x = 180.0 - (Math.toDegrees(y) + z);
                a = Math.sqrt(c * c + b * b - 2 * c * b * Math.cos(Math.toRadians(x)));
                setAreaPeriHeight(a,b,c);
                sideAInput.setText(Formatter.formatOutput(a, precision));
                setAngleX(x);
                setAngleZ(z);
            }

            private void ssa_byz(double a, double b, double c, double x, double y, double z) {
                if(spinnerX == 0){
                    x = Math.toRadians(x);
                }
                y = Math.toDegrees(Math.asin(c * Math.sin(x) / a));
                z = 180.0 - (y + Math.toDegrees(x));
                b = Math.sqrt(c * c + a * a - 2 * c * a * Math.cos(Math.toRadians(z)));
                setAreaPeriHeight(a,b,c);
                sideBInput.setText(Formatter.formatOutput(b, precision));
                setAngleY(y);
                setAngleZ(z);
            }

            private void ssa_bxz(double a, double b, double c, double x, double y, double z) {
                if(spinnerY == 0){
                    y = Math.toRadians(y);
                }
                x = Math.toDegrees(Math.asin(a * Math.sin(y) / c));
                z = 180.0 - (Math.toDegrees(y) + x);
                b = Math.sqrt(c * c + a * a - 2 * c * a * Math.cos(Math.toRadians(z)));
                setAreaPeriHeight(a,b,c);
                sideBInput.setText(Formatter.formatOutput(b, precision));
                setAngleX(x);
                setAngleZ(z);
            }

            private void sas_cxz(double a, double b, double c, double x, double y, double z) {
                if(spinnerY == 0){
                    y = Math.toRadians(y);
                }
                c = Math.sqrt(a * a + b * b - 2 * a * b * Math.cos(y));
                x = 0.0;
                z = 0.0;
                // only one angle can be > 90 deg
                if (a < b){
                    x = Math.toDegrees(Math.asin(a * Math.sin(y) / c));
                    z = 180 - (x + Math.toDegrees(y));
                }else{
                    z = Math.toDegrees(Math.asin(b * Math.sin(y) / c));
                    x = 180 - (z + Math.toDegrees(y));
                }
                setAreaPeriHeight(a,b,c);
                sideCInput.setText(Formatter.formatOutput(c, precision));
                setAngleX(x);
                setAngleZ(z);
            }

            private void sas_ayz(double a, double b, double c, double x, double y, double z) {
                if(spinnerX == 0){
                    x = Math.toRadians(x);
                }
                a = Math.sqrt(c * c + b * b - 2 * c * b * Math.cos(x));
                // only one angle can be > 90 deg
                if (c < b){
                    y = Math.toDegrees(Math.asin(c * Math.sin(x) / a));
                    Log.d(" x is ", Double.toString(Math.toDegrees(x)));
                    Log.d(" y is ", Double.toString(y));
                    z = 180 - (Math.toDegrees(x) + y);
                }
                else{
                    z = Math.toDegrees(Math.asin(b * Math.sin(x) / a));
                    Log.d(" x is ", Double.toString(Math.toDegrees(x)));
                    Log.d(" z is ", Double.toString(z));
                    y = 180 - (Math.toDegrees(x) + z);
                }
                setAreaPeriHeight(a,b,c);
                sideAInput.setText(Formatter.formatOutput(a, precision));
                setAngleY(y);
                setAngleZ(z);
            }

            private void sas_xyb(double a, double b, double c, double x, double y, double z) {
                if(spinnerZ == 0){
                    z = Math.toRadians(z);
                }
                b = Math.sqrt(c * c + a * a - 2 * c * a * (Math.cos(z)));

                if (c < a){  // only one angle can be > 90 deg
                    y = Math.toDegrees(Math.asin(c * Math.sin(z) / b));
                     Log.d(" y is ", Double.toString(y));
                    x = 180 - (y + Math.toDegrees(z));
                }
                else{
                    x = Math.toDegrees(Math.asin(a * Math.sin(z) / b));
                    Log.d(" x is ", Double.toString(x));
                    y = 180 - (x + Math.toDegrees(z));
                }
                setAreaPeriHeight(a,b,c);
                sideBInput.setText(Formatter.formatOutput(b, precision));
                setAngleX(x);
                setAngleY(y);
            }

            private void sss_abc(double a, double b, double c, double x, double y, double z) {
                if(b < c || b < a){
                    Toast.makeText(getActivity(), "Side (b) is the base of the triangle and must be the longest side.", Toast.LENGTH_SHORT).show();
                    return;
                }
                x = Math.toDegrees(Math.acos((c * c + b * b - a * a) / (2 * b * c)));
                y = Math.toDegrees(Math.acos((a * a + b * b - c * c) / (2 * a * b)));
                z = 180 - (x + y);
                if(isNaN(x) || isNaN(y) || isNaN(z)){
                    Toast.makeText(getActivity(), "This is not a triangle.", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    setAngleX(x);
                    setAngleY(y);
                    setAngleZ(z);
                    setAreaPeriHeight(a, b, c);
                }
            }

            private boolean isNaN(double x) {
                String y = Double.toString(x);
                if(y =="NaN")
                return true;
                else{
                    return false;
                }
            }

            private void setAngleX(double x){
                if(spinnerX == 1){
                    angleXInput.setText(Formatter.formatOutput(Math.toRadians(x), precision));
                }else {
                    angleXInput.setText(Formatter.formatOutput(x, precision));
                }
            }

            private void setAngleY(double y){
                if(spinnerY == 1){
                    angleYInput.setText(Formatter.formatOutput(Math.toRadians(y), precision));
                }else {
                    angleYInput.setText(Formatter.formatOutput(y, precision));
                }
            }

            private void setAngleZ(double z){
                if(spinnerZ == 1){
                    angleZInput.setText(Formatter.formatOutput(Math.toRadians(z), precision));
                }else {
                    angleZInput.setText(Formatter.formatOutput(z, precision));
                }
            }

            private double getArea(double a, double b, double c){
                double x = (a + b + c)/2;
                return Math.sqrt(x * (x - a) * (x - b) * (x - c));
            }

            private void setAreaPeriHeight(double a, double b, double c) {
                double area = getArea(a, b, c);
                areaAnswer.setText(Formatter.formatOutput(area, precision));
                perimeterAnswer.setText(Formatter.formatOutput(a + b + c, precision));
                heightAnswer.setText(Formatter.formatOutput(2 / (area / b), precision));
            }

        });

    }

    private void setClearListener(){
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sideAInput.setText("");
                sideBInput.setText("");
                sideCInput.setText("");
                angleXInput.setText("");
                angleYInput.setText("");
                angleZInput.setText("");
                areaAnswer.setText("");
                heightAnswer.setText("");
                perimeterAnswer.setText("");
            }
        });
    }

    private void setAngleXListener() {
        angleXSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerX = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setAngleYListener() {
        angleYSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerY = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setAngleZListener() {
        angleZSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerZ = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setAngleAdapters() {
        angleAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.degree_rad_array, R.layout.spinner_background);
        angleAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
        angleXSpinner.setAdapter(angleAdapter);
        angleYSpinner.setAdapter(angleAdapter);
        angleZSpinner.setAdapter(angleAdapter);
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
