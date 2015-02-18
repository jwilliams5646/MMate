package com.jwilliams.machinistmate.app.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoButton;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoTextView;
import com.jwilliams.machinistmate.app.GeometryClasses.ObliqueTriangle;
import com.jwilliams.machinistmate.app.GeometryClasses.ShowImage;
import com.jwilliams.machinistmate.app.R;
import com.jwilliams.machinistmate.app.Utility;
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
    private ImageButton calcButton;
    private ImageButton clearButton;
    private ImageButton questionButton;
    private int spinnerX;
    private int spinnerY;
    private int spinnerZ;
    private View rootView;
    //private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private AdView adView;
    private ObliqueTriangle ot;

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
        setAd();
        initializeLayout();
        return rootView;
    }

    private void setAd(){
        adView = (AdView)rootView.findViewById(R.id.oblique_adView);
        AdRequest adRequest = new AdRequest.Builder()
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
        calcButton = (ImageButton)rootView.findViewById(R.id.oblique_calc_button);
        clearButton = (ImageButton)rootView.findViewById(R.id.oblique_clear_button);
        questionButton = (ImageButton)rootView.findViewById(R.id.ot_question_button);
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
        setImage();
    }

    private void setImage() {
        Picasso.with(getActivity())
                .load(R.drawable.oblique_triangle)
                .fit()
                .centerInside()
                .into(ObTriangle);

        SharedPreferences shpr = getActivity().getPreferences(Context.MODE_PRIVATE);
        if(!shpr.getBoolean("isTablet", false)) {
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
                Animation animate = AnimationUtils.loadAnimation(getActivity(), R.anim.touch_anim);
                questionButton.startAnimation(animate);
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
                Animation animate = AnimationUtils.loadAnimation(getActivity(), R.anim.touch_anim);
                calcButton.startAnimation(animate);
                ot = new ObliqueTriangle();
                if(ot.calcObliqueTriangle(sideAInput, sideBInput, sideCInput,
                        angleXInput, angleYInput, angleZInput,
                        spinnerX, spinnerY, spinnerZ, getActivity())){
                    postAnswers();
                }
            }
        });
    }

    private void postAnswers() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int precision = Integer.parseInt(sp.getString("pref_key_geometry_precision", "2"));
        sideAInput.setText(Utility.formatOutput(ot.getA(), precision));
        sideBInput.setText(Utility.formatOutput(ot.getB(), precision));
        sideCInput.setText(Utility.formatOutput(ot.getC(), precision));
        angleXInput.setText(Utility.formatOutput(ot.getX(), precision));
        angleYInput.setText(Utility.formatOutput(ot.getY(), precision));
        angleZInput.setText(Utility.formatOutput(ot.getZ(), precision));
        areaAnswer.setText(Utility.formatOutput(ot.getArea(), precision));
        heightAnswer.setText(Utility.formatOutput(ot.getHeight(), precision));
        perimeterAnswer.setText(Utility.formatOutput(ot.getPerimeter(), precision));
    }

    private void setClearListener(){
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animate = AnimationUtils.loadAnimation(getActivity(), R.anim.touch_anim);
                clearButton.startAnimation(animate);
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
        ArrayAdapter<CharSequence> angleAdapter = ArrayAdapter.createFromResource(getActivity(),
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
