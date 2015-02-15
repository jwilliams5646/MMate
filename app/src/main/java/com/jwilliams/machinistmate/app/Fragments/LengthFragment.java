package com.jwilliams.machinistmate.app.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.jwilliams.machinistmate.app.ConversionClass.Conversions;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoTextView;
import com.jwilliams.machinistmate.app.R;
import com.squareup.picasso.Picasso;

/**
 * Created by John Williams
 * Length Conversion View-Controller
 */
public class LengthFragment extends Fragment {

    private ImageView convImage;
    private RobotoTextView answer;
    private RobotoTextView answerType;
    private Spinner inputSpinner;
    private Spinner outputSpinner;
    private EditText input;
    private ImageButton calcButton;
    private Context context;
    private int inputPos;
    private int precision;
    private String output;
    //private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private AdView adView;
    private View rootView;

    public LengthFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.conversion_layout, container, false);
        setAd();
        initializeVariables();
        setSpinnerAdapter();
        setInputListener();
        setOutputListener();
        setCalcListener();
        return rootView;
    }

    private void setAd() {
        adView = (AdView) rootView.findViewById(R.id.rt_adView);
        AdRequest adRequest = new AdRequest.Builder()
/*                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(TEST_DEVICE_ID)*/
                .build();
        adView.loadAd(adRequest);
    }

    private void setCalcListener() {
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animate = AnimationUtils.loadAnimation(context, R.anim.touch_anim);
                calcButton.startAnimation(animate);
                new Conversions(input, answer, 0, inputPos, output, precision, getActivity()).execute();
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
        answer.setText("");
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

    private void initializeVariables() {
        convImage = (ImageView) rootView.findViewById(R.id.conv_image);
        answer = (RobotoTextView) rootView.findViewById(R.id.conv_answer);
        answerType = (RobotoTextView) rootView.findViewById(R.id.conv_answer_type);
        inputSpinner = (Spinner) rootView.findViewById(R.id.conv_input_spinner);
        outputSpinner = (Spinner) rootView.findViewById(R.id.conv_output_spinner);
        input = (EditText) rootView.findViewById(R.id.conv_input);
        calcButton = (ImageButton) rootView.findViewById(R.id.conv_calc_button);
        context = getActivity();
        inputPos = 0;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        precision = Integer.parseInt(sp.getString("pref_key_conversion_precision", "2"));
        showImage();
    }

    private void showImage() {
        Picasso.with(getActivity())
                .load(R.drawable.conversion)
                .fit()
                .centerInside()
                .into(convImage);
    }

    private void setSpinnerAdapter() {
        ArrayAdapter<CharSequence> convAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.conv_length_array, R.layout.spinner_background);
        convAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
        inputSpinner.setAdapter(convAdapter);
        outputSpinner.setAdapter(convAdapter);
    }

    private void setConversionType(int position) {
        answer.setText("");
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
