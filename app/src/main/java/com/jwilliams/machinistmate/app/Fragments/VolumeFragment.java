package com.jwilliams.machinistmate.app.Fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jwilliams.machinistmate.app.ConversionClass.Conversions;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoButton;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoTextView;
import com.jwilliams.machinistmate.app.R;
import com.squareup.picasso.Picasso;

/**
 * Created by John Williams
 * Volume Conversion View-Controller
 */
public class VolumeFragment extends Fragment {

    private ImageView convImage;
    private RobotoTextView answer;
    private RobotoTextView answerType;
    private Spinner inputSpinner;
    private Spinner outputSpinner;
    private EditText input;
    private RobotoButton calcButton;
    private int inputPos;
    private int precision;
    private String output;
    //private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private AdView adView;
    private View rootView;

    public VolumeFragment() {
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
                new Conversions(input, answer, 1, inputPos, output, precision, getActivity()).execute();
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
                input.setHint(Html.fromHtml("in<sup><small>3</small></sup>"));
                break;
            case 1:
                input.setHint(Html.fromHtml("ft<sup><small>3</small></sup>"));
                break;
            case 2:
                input.setHint(Html.fromHtml("yd<sup><small>3</small></sup>"));
                break;
            case 3:
                input.setHint(Html.fromHtml("mm<sup><small>3</small></sup>"));
                break;
            case 4:
                input.setHint(Html.fromHtml("cm<sup><small>3</small></sup>"));
                break;
            case 5:
                input.setHint(Html.fromHtml("m<sup><small>3</small></sup>"));
                break;
            default:
                input.setHint(Html.fromHtml("in<sup><small>3</small></sup>"));
                break;
        }
    }

    public void initializeVariables() {
        convImage = (ImageView) rootView.findViewById(R.id.conv_image);
        answer = (RobotoTextView) rootView.findViewById(R.id.conv_answer);
        answerType = (RobotoTextView) rootView.findViewById(R.id.conv_answer_type);
        inputSpinner = (Spinner) rootView.findViewById(R.id.conv_input_spinner);
        outputSpinner = (Spinner) rootView.findViewById(R.id.conv_output_spinner);
        input = (EditText) rootView.findViewById(R.id.conv_input);
        calcButton = (RobotoButton) rootView.findViewById(R.id.conv_calc_button);
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.conv_volume_array, R.layout.spinner_background);
        adapter.setDropDownViewResource(R.layout.spinner_drop_down);
        inputSpinner.setAdapter(adapter);
        outputSpinner.setAdapter(adapter);
    }

    private void setConversionType(int position) {
        answer.setText("");
        switch (position) {
            case 0:
                answerType.setText(Html.fromHtml("in<sup><small>3</small></sup>"));
                output = "cu_in";
                break;
            case 1:
                answerType.setText(Html.fromHtml("ft<sup><small>3</small></sup>"));
                output = "cu_ft";
                break;
            case 2:
                answerType.setText(Html.fromHtml("yd<sup><small>3</small></sup>"));
                output = "cu_yd";
                break;
            case 3:
                answerType.setText(Html.fromHtml("mm<sup><small>3</small></sup>"));
                output = "cu_mm";
                break;
            case 4:
                answerType.setText(Html.fromHtml("cm<sup><small>3</small></sup>"));
                output = "cu_cm";
                break;
            case 5:
                answerType.setText(Html.fromHtml("m<sup><small>3</small></sup>"));
                output = "cu_m";
                break;
            default:
                answerType.setText(Html.fromHtml("in<sup><small>3</small></sup>"));
                output = "cu_in";
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
