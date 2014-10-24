package com.jwilliams.machinistmate.app.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoButton;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoRadioButton;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoTextView;
import com.jwilliams.machinistmate.app.Formatter;
import com.jwilliams.machinistmate.app.R;
import com.jwilliams.machinistmate.app.SpeedsandFeedsClasses.Speeds;
import com.squareup.picasso.Picasso;


/**
 * Created by John Williams on 3/26/2014.
 * This class is the View-Controller for Speeds.
 */
public class SpeedsFragment extends Fragment {

    private RobotoTextView speedAnswer;
    private RobotoTextView surfaceType;
    private EditText surfaceInput;
    private EditText diameterInput;
    private boolean speedsType;
    private RadioGroup speedRadioGroup;
    private RobotoButton speedsCalc;
    private RobotoRadioButton standardButton;
    private RobotoRadioButton metricButton;
    private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private AdView adView;
    private AdRequest adRequest;
    private Speeds speeds;
    private Context context;

    public SpeedsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.speeds_item_detail, container, false);
        try {
            setAd(rootView);
        }catch(Exception e){
            e.printStackTrace();
        }
        setLayout(rootView);
        setRadioGroupListener();
        setCalcButtonListener();
        return rootView;
    }

    public void getContext(){
        context = getActivity();
    }

    private void setCalcButtonListener() {
        speedsCalc.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validInput()) {
                    speedAnswer.setText(Formatter.formatOutput(speeds.getSpeed(), 0));
                } else {
                    Toast.makeText(getActivity(), "One or more inputs are invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setRadioGroupListener() {
        speedRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.speeds_standard_radio:
                        surfaceType.setText(getText(R.string.surface_standard));
                        diameterInput.setHint(getText(R.string.in));
                        speedsType = true;
                        break;
                    case R.id.speeds_metric_radio:
                        surfaceType.setText(getText(R.string.surface_metric));
                        diameterInput.setHint(getText(R.string.mm));
                        speedsType = false;
                        break;
                }
            }
        });
    }

    private void setAd(View rootView) {
        adView = (AdView) rootView.findViewById(R.id.speeds_adView);
        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(TEST_DEVICE_ID)
                .build();
        try {
            adView.loadAd(adRequest);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setLayout(View rootView) {
        speedRadioGroup = (RadioGroup) rootView.findViewById(R.id.speed_radio_group);
        surfaceType = (RobotoTextView) rootView.findViewById(R.id.surface_view);
        surfaceInput = (EditText) rootView.findViewById(R.id.surfaceInput);
        diameterInput = (EditText) rootView.findViewById(R.id.diameterInput);
        speedsCalc = (RobotoButton) rootView.findViewById(R.id.speed_calc);
        speedAnswer = (RobotoTextView) rootView.findViewById(R.id.speed_answer);
        standardButton = (RobotoRadioButton) rootView.findViewById(R.id.speeds_standard_radio);
        metricButton = (RobotoRadioButton) rootView.findViewById(R.id.speeds_metric_radio);
        speedsType = true;
        showImage(rootView);
    }

    private void showImage(View rootView) {
        ImageView speedsImage = (ImageView) rootView.findViewById(R.id.speeds_image);
        Picasso.with(getActivity())
                .load(R.drawable.speeds)
                .fit()
                .centerInside()
                .into(speedsImage);
    }

    private boolean validInput() {
        speeds = new Speeds(speedsType);
        boolean valid = true;

        try {
            speeds.setSurface(Double.parseDouble(surfaceInput.getText().toString()));
        } catch (NumberFormatException e) {
            surfaceInput.setHint("Invalid");
            valid = false;
        }

        try {
            speeds.setDiameter(Double.parseDouble(diameterInput.getText().toString()));
        } catch (NumberFormatException e) {
            diameterInput.setHint("Invalid");
            valid = false;
        }
        return valid;
    }

    @Override
    public void onResume() {
        Log.d("Resuming...",".......");
        super.onResume();
        if (adView != null) {
            try {
                adView.resume();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPause() {
        Log.d("Pausing...",".......");
        if (adView != null) {
            try {
                adView.pause();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.d("Destroying...",".......");
        // Destroy the AdView.
        if (adView != null) {
            try {
                adView.destroy();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}




