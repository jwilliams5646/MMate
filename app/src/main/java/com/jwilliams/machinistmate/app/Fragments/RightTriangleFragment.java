package com.jwilliams.machinistmate.app.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoTextView;
import com.jwilliams.machinistmate.app.GeometryClasses.RightTriangle;
import com.jwilliams.machinistmate.app.GeometryClasses.ShowImage;
import com.jwilliams.machinistmate.app.R;
import com.jwilliams.machinistmate.app.Utility;
import com.squareup.picasso.Picasso;

/**
 * Created by John Williams
 * This is the view-controller for Right Triangle
 */
public class RightTriangleFragment extends Fragment {

    private EditText sideH;
    private EditText sideO;
    private EditText sideA;
    private EditText angleX;
    private EditText angleY;
    private RobotoTextView areaAnswer;
    private RobotoTextView perimeterAnswer;
    private Spinner angleXSpinner;
    private Spinner angleYSpinner;
    private ImageButton calcButton;
    private ImageButton clearButton;
    private ImageButton questionButton;
    private int xPos;
    private int yPos;
    private View rootView;
    //private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private AdView adView;
    private RightTriangle rt;
    private ImageView rtImage;

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
        setAd();
        initializeLayout();
        setSpinnerAdapters();
        setSpinnerXListener();
        setSpinnerYListener();
        setClearButtonListener();
        setQuestionButtonListener();
        setCalcButtonListener();
        return rootView;
    }

    private void setImage() {
        Picasso.with(getActivity())
                .load(R.drawable.right_triangle)
                .fit()
                .centerInside()
                .into(rtImage);

        SharedPreferences shpr = getActivity().getPreferences(Context.MODE_PRIVATE);
        if(!shpr.getBoolean("isTablet", false)) {
            rtImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowImage enlarge = new ShowImage(getActivity(), R.drawable.right_triangle);
                    enlarge.setDialog();
                }
            });
        }
    }

    private void setAd() {
        adView = (AdView) rootView.findViewById(R.id.rt_adView);
        AdRequest adRequest = new AdRequest.Builder()
/*                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(TEST_DEVICE_ID)*/
                .build();
        adView.loadAd(adRequest);
    }

    private void setCalcButtonListener() {
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animate = AnimationUtils.loadAnimation(getActivity(), R.anim.touch_anim);
                calcButton.startAnimation(animate);
                rt = new RightTriangle();
                if(rt.calcRightTriangle(sideH,sideO,sideA,angleX,angleY,xPos,yPos,getActivity())) {
                    postAnswers();
                }
            }


            private void postAnswers() {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                int precision = Integer.parseInt(sp.getString("pref_key_geometry_precision", "2"));
                Log.d("Precision", Integer.toString(precision));
                sideH.setText(Utility.formatOutput(rt.getH(), precision));
                sideO.setText(Utility.formatOutput(rt.getO(), precision));
                sideA.setText(Utility.formatOutput(rt.getA(), precision));
                angleX.setText(Utility.formatOutput(rt.getX(), precision));
                angleY.setText(Utility.formatOutput(rt.getY(), precision));
                areaAnswer.setText(Utility.formatOutput(rt.getArea(), precision));
                perimeterAnswer.setText(Utility.formatOutput(rt.getPerimeter(), precision));
            }

        });
    }

    private void setQuestionButtonListener() {
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animate = AnimationUtils.loadAnimation(getActivity(), R.anim.touch_anim);
                questionButton.startAnimation(animate);
                Dialog d = new Dialog(getActivity());
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.info_dialog);
                //Set dialog text
                final RobotoTextView dialog = (RobotoTextView) d.findViewById(R.id.dialog);
                dialog.setText("Input at least 2 values (angles only will not work).\n" +
                        "Side H must always be longer than side A.\n" +
                        "These calculations use the law of sine, cosine and/or tangent.");
                // Make dialog box visible.
                d.show();
            }
        });
    }

    private void setClearButtonListener() {
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animate = AnimationUtils.loadAnimation(getActivity(), R.anim.touch_anim);
                clearButton.startAnimation(animate);
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
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setSpinnerXListener() {
        angleXSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int i, long id) {
                xPos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initializeLayout() {
        rtImage = (ImageView)rootView.findViewById(R.id.rt_image);
        sideH = (EditText) rootView.findViewById(R.id.rt_h_input);
        sideO = (EditText) rootView.findViewById(R.id.rt_o_input);
        sideA = (EditText) rootView.findViewById(R.id.rt_a_input);
        angleX = (EditText) rootView.findViewById(R.id.rt_x_input);
        angleY = (EditText) rootView.findViewById(R.id.rt_y_input);
        areaAnswer = (RobotoTextView) rootView.findViewById(R.id.rt_area_answer);
        perimeterAnswer = (RobotoTextView) rootView.findViewById(R.id.rt_perimeter_answer);
        angleXSpinner = (Spinner) rootView.findViewById(R.id.rt_x_spinner);
        angleYSpinner = (Spinner) rootView.findViewById(R.id.rt_y_spinner);
        calcButton = (ImageButton) rootView.findViewById(R.id.rt_calc_button);
        clearButton = (ImageButton) rootView.findViewById(R.id.rt_clear_button);
        questionButton = (ImageButton) rootView.findViewById(R.id.rt_question_button);
        xPos = 0;
        yPos = 0;
        setImage();
    }

    private void clear() {
        sideH.setText("");
        sideO.setText("");
        sideA.setText("");
        angleX.setText("");
        angleY.setText("");
        areaAnswer.setText("");
        perimeterAnswer.setText("");
    }

    private void setSpinnerAdapters() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.degree_rad_array, R.layout.spinner_background);
        adapter.setDropDownViewResource(R.layout.spinner_drop_down);
        angleXSpinner.setAdapter(adapter);
        angleYSpinner.setAdapter(adapter);
    }

    @Override
    public void onPause() {
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

    /**
     * Called before the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        // Destroy the AdView.
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
