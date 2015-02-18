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
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoTextView;
import com.jwilliams.machinistmate.app.GeometryClasses.ShowImage;
import com.jwilliams.machinistmate.app.GeometryClasses.Square;
import com.jwilliams.machinistmate.app.R;
import com.jwilliams.machinistmate.app.Utility;
import com.squareup.picasso.Picasso;

/**
 * Created by John Williams
 * Square View-Controller
 */
public class SquareFragment extends Fragment {

    private EditText input;
    private RobotoTextView inputView;
    private RobotoTextView answer;
    private Spinner answerChoice;
    private Spinner sideChoice;
    private ImageButton calcButton;
    private int precision;
    private int sidePos;
    private int answerPos;
    private double inputValue;
    //private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private AdView adView;
    private SharedPreferences sp;
    private View rootView;

    public SquareFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.square_detail, container, false);
        setAd();
        initializeLayout();
        setAnswerChoiceAdapter();
        setSideChoiceAdapter();
        setAnswerChoiceListener();
        setSideChoiceListener();
        setCalcListener();
        return rootView;

    }

    private void setSquare() {
        ImageView image = (ImageView) rootView.findViewById(R.id.square_image);
        Picasso.with(getActivity())
                .load(R.drawable.square)
                .fit()
                .centerInside()
                .into(image);

        SharedPreferences shpr = getActivity().getPreferences(Context.MODE_PRIVATE);
        if(!shpr.getBoolean("isTablet", false)) {
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowImage enlarge = new ShowImage(getActivity(), R.drawable.square);
                    enlarge.setDialog();
                }
            });
        }
    }

    private void setAd(){
        adView = (AdView)rootView.findViewById(R.id.sq_adView);
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
                Animation animate = AnimationUtils.loadAnimation(getActivity(), R.anim.touch_anim);
                calcButton.startAnimation(animate);
                sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                precision = Integer.parseInt(sp.getString("pref_key_geometry_precision", "2"));                if(validInput()) {
                    Square sq = new Square(inputValue);
                    switch (answerPos) {
                        case 0:
                            answer.setText(Utility.formatOutput(sq.calcArea(), precision));
                            break;
                        case 1:
                            answer.setText(Utility.formatOutput(sq.calcDiagonal(), precision));
                            break;
                        case 2:
                            switch (sidePos) {
                                case 0:
                                    answer.setText(Utility.formatOutput(sq.calcSideByArea(), precision));
                                    break;
                                case 1:
                                    answer.setText(Utility.formatOutput(sq.calcSideByDiagonal(), precision));
                                    break;
                                case 2:
                                    answer.setText(Utility.formatOutput(sq.calcSideByPerimeter(), precision));
                                    break;
                            }
                            break;
                        case 3:
                            answer.setText(Utility.formatOutput(sq.calcPerimeter(), precision));
                            break;
                    }
                }else{
                    Toast.makeText(getActivity(), "Please enter a valid input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean validInput(){
        try {
            inputValue = Double.parseDouble(input.getText().toString());
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    private void setSideChoiceListener() {
        sideChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sidePos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}});
    }

    private void setAnswerChoiceListener() {
        answerChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                answerPos = i;
                clearInput();
                switch(i){
                    case 0:
                        setBasicLayout();
                        break;
                    case 1:
                        setBasicLayout();
                        break;
                    case 2:
                        setSideLayout();
                        break;
                    case 3:
                        setBasicLayout();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}});
    }

    private void setSideChoiceAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.square_side_array, R.layout.spinner_background);
        adapter.setDropDownViewResource(R.layout.spinner_drop_down);
        sideChoice.setAdapter(adapter);
    }

    private void setAnswerChoiceAdapter() {
        ArrayAdapter<CharSequence> answerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.square_calc_array, R.layout.spinner_background);
        answerAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
        answerChoice.setAdapter(answerAdapter);
    }

    private void initializeLayout() {
        input = (EditText)rootView.findViewById(R.id.sq_input);
        inputView = (RobotoTextView)rootView.findViewById(R.id.sq_input_view);
        answer = (RobotoTextView)rootView.findViewById(R.id.sq_answer);
        answerChoice = (Spinner)rootView.findViewById(R.id.sq_choice);
        sideChoice = (Spinner)rootView.findViewById(R.id.sq_side_choice);
        calcButton = (ImageButton)rootView.findViewById(R.id.sq_calc);
        sidePos = 0;
        answerPos = 0;
        setSquare();
        setBasicLayout();
    }

    private void setBasicLayout() {
        inputView.setText("Side (s)");
        sideChoice.setVisibility(View.GONE);
        inputView.setVisibility(View.VISIBLE);
    }

    private void setSideLayout(){
        sideChoice.setVisibility(View.VISIBLE);
        inputView.setVisibility(View.GONE);
    }

    private void clearInput(){
        input.setText("");
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
