package com.jwilliams.machinistmate.app.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoButton;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoTextView;
import com.jwilliams.machinistmate.app.Formatter;
import com.jwilliams.machinistmate.app.GeometryClasses.ObliqueTriangle;
import com.jwilliams.machinistmate.app.GeometryClasses.ShowImage;
import com.jwilliams.machinistmate.app.R;
import com.squareup.picasso.Picasso;

/**
 * Created by John Williams
 * View-Controller for the Oblique Triangle Geometry tool.
 */
public class ObliqueTriangleFragment extends Fragment {

    private ImageView ObTriangle;
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
    private ArrayAdapter<CharSequence> angleAdapter;
    private View rootView;
    //private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private AdView adView;
    private AdRequest adRequest;
    private ObliqueTriangle ot;
    private SharedPreferences sharedPref;
    private Boolean isTablet;

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
        initializeLayout();
        return rootView;
    }

    private void setAd(View rootView){
        adView = (AdView)rootView.findViewById(R.id.oblique_adView);
        adRequest = new AdRequest.Builder()
/*                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(TEST_DEVICE_ID)*/
                .build();
        adView.loadAd(adRequest);
    }

    private void initializeLayout() {
        ObTriangle = (ImageView)rootView.findViewById(R.id.oblique_image);
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
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        isTablet = sharedPref.getBoolean("isTablet", false);
        setAngleAdapters();
        setAngleXListener();
        setAngleYListener();
        setAngleZListener();
        setClearListener();
        setCalcButtonListener();
        setQuestionButtonListener();
        setImage();
    }

    private void setImage() {
        Picasso.with(getActivity())
                .load(R.drawable.oblique_triangle)
                .fit()
                .centerInside()
                .into(ObTriangle);

        if(!isTablet){
            ObTriangle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowImage enlarge = new ShowImage(getActivity(), R.drawable.oblique_triangle);
                    enlarge.setDialog();
                }
            });
        }
    }

    private void setQuestionButtonListener() {
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(getActivity());

                // Set GUI of login screen
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.info_dialog);
                RobotoTextView dialog = (RobotoTextView)d.findViewById(R.id.dialog);
                dialog.setText("Input at least 3 values (angles only will not work).\n" +
                        "Only angle (z) can be 90 degrees or greater.\n" +
                        "These calculations use the law of sine, cosine and/or tangent.");
                d.show();
            }
        });
    }

    private void setCalcButtonListener() {
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ot = new ObliqueTriangle();
                int errorCode = 0;
                boolean ca = true;
                boolean cb = true;
                boolean cc = true;
                boolean cx = true;
                boolean cy = true;
                boolean cz = true;
                int count = 0;
                ot.setxPos(spinnerX);
                ot.setyPos(spinnerY);
                ot.setzPos(spinnerZ);

                try {
                    ot.setA(Double.parseDouble(sideAInput.getText().toString()));
                } catch (NumberFormatException e) {
                    ca=false;
                    count++;
                }

                try {
                    ot.setB(Double.parseDouble(sideBInput.getText().toString()));
                } catch (NumberFormatException e) {
                    cb=false;
                    count++;
                }

                try {
                    ot.setC(Double.parseDouble(sideCInput.getText().toString()));
                } catch (NumberFormatException e) {
                    cc=false;
                    count++;
                }

                try {
                    ot.setX(Double.parseDouble(angleXInput.getText().toString()));
                } catch (NumberFormatException e) {
                    cx=false;
                    count++;
                }

                try {
                    ot.setY(Double.parseDouble(angleYInput.getText().toString()));
                } catch (NumberFormatException e) {
                    cy=false;
                    count++;
                }

                try {
                    ot.setZ(Double.parseDouble(angleZInput.getText().toString()));
                } catch (NumberFormatException e) {
                    cz=false;
                    count++;
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
                if(ot.getDegree(spinnerX,ot.getX()) >= 90 || ot.getDegree(spinnerY,ot.getY()) >= 90){
                    Toast.makeText(getActivity(), "Only angle (z) can be 90 degrees or greater", Toast.LENGTH_SHORT).show();
                    return;
                }

                //a-b-c -> x-y-z
                if (cc && ca && cb){
                    errorCode = ot.calcFromABC();
                }
                //a-c-z -> x-y-b
                else if (cc && ca && cz){
                    errorCode = ot.calcFromCAZ();
                }
                //c-b-x -> a-y-z
                else if (cc && cb && cx){
                    errorCode = ot.calcFromCBX();
                }
                //a-b-y -> c-x-z
                else if (ca && cb && cy){
                    errorCode = ot.calcFromABY();
                }
                //c-a-y -> b-x-z
                else if (cc && ca && cy){
                    errorCode = ot.calcFromCAY();
                }
                //c-a-x -> b-y-z
                else if (cc && ca && cx){
                    errorCode = ot.calcFromCAX();
                }
                   //c-b-y -> a-x-z
                else if (cc && cb && cy){
                    errorCode = ot.calcFromCBY();
                }
                //c-b-z -> a-x-y
                else if (cc && cb && cz){
                    errorCode = ot.calcFromCBZ();
                }
                //a-b-x -> c-y-z
                else if (ca && cb && cx){
                    errorCode = ot.calcFromABX();
                }
                //a-b-z -> c-x-y
                else if (ca && cb && cz){
                    errorCode = ot.calcFromABZ();
                }
                //c-y-x -> a-b-z
                else if (cc && cy && cx){
                    errorCode = ot.calcFromCYX();
                }

                else if (cc && cy && cz){
                    errorCode = ot.calcFromCYZ();
                }

                else if (cc && cx && cz){
                    errorCode = ot.calcFromCXZ();
                }

                else if (ca && cy && cx){
                    errorCode = ot.calcFromAYX();
                }

                else if (ca && cy && cz){
                    errorCode = ot.calcFromAYZ();
                }

                else if (ca && cx && cz){
                    errorCode = ot.calcFromAXZ();
                }

                else if (cb && cy && cx){
                    errorCode = ot.calcFromBYX();
                }

                else if (cb && cy && cz){
                    errorCode = ot.calcFromBYZ();
                }

                else if (cb && cx && cz){
                    errorCode = ot.calcFromBXZ();
                }

                switch(errorCode){
                    case 0:
                        postAnswers();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "This is not a triangle", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "Side(b) is the base of the triangle and must be the longest side and greater than the sum of Side(a) and Side(c).", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(), "Side b cannot be shorter than side c if angle (z) is greater than 90 degrees", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void postAnswers() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int precision = Integer.parseInt(sp.getString("pref_key_geometry_precision", "2"));
        sideAInput.setText(Formatter.formatOutput(ot.getA(), precision));
        sideBInput.setText(Formatter.formatOutput(ot.getB(), precision));
        sideCInput.setText(Formatter.formatOutput(ot.getC(), precision));
        angleXInput.setText(Formatter.formatOutput(ot.getX(), precision));
        angleYInput.setText(Formatter.formatOutput(ot.getY(), precision));
        angleZInput.setText(Formatter.formatOutput(ot.getZ(), precision));
        areaAnswer.setText(Formatter.formatOutput(ot.getArea(), precision));
        heightAnswer.setText(Formatter.formatOutput(ot.getHeight(), precision));
        perimeterAnswer.setText(Formatter.formatOutput(ot.getPerimeter(), precision));
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
                spinnerX = 0;
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
                spinnerY = 0;
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
                spinnerZ = 0;
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
