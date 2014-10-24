package com.jwilliams.machinistmate.app.Fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoTextView;
import com.jwilliams.machinistmate.app.Formatter;
import com.jwilliams.machinistmate.app.R;
import com.jwilliams.machinistmate.app.SpeedsandFeedsClasses.Feeds;
import com.squareup.picasso.Picasso;

/**
 * Created by John on 4/4/2014.
 * <p/>
 * This class is the View-Controller for the Feeds calculation.
 */
public class FeedsFragment extends Fragment {

    private View rootView;
    private RobotoTextView feedAnswer;
    private EditText feedSpeedInput;
    private EditText feedPerToothInput;
    private EditText numberTeethInput;
    private RobotoTextView feedAnswerType;
    private RobotoButton feedCalc;
    private RadioGroup feedRadioGroup;
    private int precision;
    private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private AdView adView;
    private Feeds feeds;
    private SharedPreferences sp;

    public FeedsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.feeds_item_detail, container, false);
        setAd();
        setLayoutVariables();
        setRadioButtonListeners();
        setCalcButtonListener();
        return rootView;
    }

    private void setAd(){
        adView = (AdView)rootView.findViewById(R.id.feeds_adView);
        AdRequest adRequest = new AdRequest.Builder()
/*                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(TEST_DEVICE_ID)*/
                .build();
        adView.loadAd(adRequest);
    }

    private void setCalcButtonListener() {
        feedCalc.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                feeds = new Feeds();
                precision = Integer.parseInt(sp.getString("pref_key_feeds_precision", "4"));
                if (feeds.calcFeed(feedSpeedInput,feedPerToothInput,numberTeethInput)) {
                    feedAnswer.setText(Formatter.formatOutput(feeds.getFeedRate(), precision));
                } else {
                    Toast.makeText(getActivity(), "One or more inputs are invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setRadioButtonListeners() {
        feedRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.feeds_standard_radio:
                        feedAnswerType.setText(getText(R.string.ipm));
                        feedPerToothInput.setHint(R.string.in);
                        break;
                    case R.id.feeds_metric_radio:
                        feedAnswerType.setText(getText(R.string.mmpm));
                        feedPerToothInput.setHint(R.string.mm);
                        break;
                }
            }
        });
    }

    private void setLayoutVariables(){
        feedAnswer = (RobotoTextView) rootView.findViewById(R.id.feed_answer);
        feedSpeedInput = (EditText) rootView.findViewById(R.id.feed_speed_input);
        feedPerToothInput = (EditText) rootView.findViewById(R.id.feed_per_tooth_input);
        numberTeethInput = (EditText) rootView.findViewById(R.id.number_teeth_input);
        feedAnswerType = (RobotoTextView) rootView.findViewById(R.id.feed_answer_type);
        feedRadioGroup = (RadioGroup) rootView.findViewById(R.id.feed_radio_group);
        feedCalc = (RobotoButton) rootView.findViewById(R.id.feed_calc);
        feedAnswerType.setText(getText(R.string.ipm));
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        showImage(rootView);
    }

    private void showImage(View rootView) {
        ImageView feedImage = (ImageView)rootView.findViewById(R.id.feeds_image);
        Picasso.with(getActivity())
                .load(R.drawable.feeds)
                .fit()
                .centerInside()
                .into(feedImage);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
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

