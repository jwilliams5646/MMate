package com.jwilliams.machinistmate.app.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoButton;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoTextView;
import com.jwilliams.machinistmate.app.Formatter;
import com.jwilliams.machinistmate.app.GeometryClasses.Parallelogram;
import com.jwilliams.machinistmate.app.GeometryClasses.ShowImage;
import com.jwilliams.machinistmate.app.R;
import com.squareup.picasso.Picasso;

/**
 * Created by John Williams
 * View-controller for the Geometry option Parallelogram.
 */
public class ParallelogramFragment extends Fragment {

    private ImageView paragramImage;
    private LinearLayout inputLayout1;
    private LinearLayout inputLayout2;
    private RobotoTextView inputView1;
    private RobotoTextView inputView2;
    private RobotoTextView answer;
    private EditText input1;
    private EditText input2;
    private Spinner paraSpinner;
    private RobotoButton calcButton;
    private int setCalc;
    private int precision;
    private ArrayAdapter<CharSequence> paraAdapter;
    //private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private AdView adView;
    private AdRequest adRequest;
    private Parallelogram pg;
    private SharedPreferences sp;

    public ParallelogramFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.parallelogram_detail, container, false);
        setAd(rootView);
        initializeLayout(rootView);
        initialLayout();
        setSpinnerAdapter();
        setSpinnerListener();
        setCalcListener();
        return rootView;
    }

    private void setAd(View rootView){
        adView = (AdView)rootView.findViewById(R.id.para_adView);
        adRequest = new AdRequest.Builder()
/*                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(TEST_DEVICE_ID)*/
                .build();
        adView.loadAd(adRequest);
    }

    private void setCalcListener() {
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pg = new Parallelogram();
                precision = Integer.parseInt(sp.getString("pref_key_geometry_precision", "2"));
                switch (setCalc) {
                    case 0:
                        if(pg.calcArea(input1, input2)){
                            answer.setText(Formatter.formatOutput(pg.getArea(), precision));
                        }else{
                            Toast.makeText(getActivity(), "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        if(pg.calcBase(input1, input2)){
                            answer.setText(Formatter.formatOutput(pg.getB(), precision));
                        }else{
                            Toast.makeText(getActivity(), "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if(pg.calcHeight(input1, input2)){
                            answer.setText(Formatter.formatOutput(pg.getH(), precision));
                        }else{
                            Toast.makeText(getActivity(), "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        if(pg.calcSide(input1, input2)){
                            answer.setText(Formatter.formatOutput(pg.getA(), precision));
                        }else{
                            Toast.makeText(getActivity(), "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 4:
                        if(pg.calcPerimeter(input1, input2)){
                            answer.setText(Formatter.formatOutput(pg.getPerimeter(), precision));
                        }else{
                            Toast.makeText(getActivity(), "One or more inputs are missing or invalid", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 5:
                        if(pg.calcX(input1)){
                            answer.setText(Formatter.formatOutput(pg.getX(), precision));
                        }else{
                            Toast.makeText(getActivity(), "Invalid Input", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 6:
                        if(pg.calcY(input1)){
                          answer.setText(Formatter.formatOutput(pg.getY(), precision));
                        }else{
                            Toast.makeText(getActivity(), "Invalid Input", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });
    }

    private void setSpinnerListener() {
        AdapterView.OnItemSelectedListener paraSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initialLayout();
                setCalc = i;
                switch(i){
                    case 0:
                        inputView1.setText("Base (b)");
                        inputView2.setText("Height (h)");
                        break;
                    case 1:
                        inputView1.setText("Area");
                        inputView2.setText("Height (h)");
                        break;
                    case 2:
                        inputView1.setText("Area");
                        inputView2.setText("Base (b)");
                        break;
                    case 3:
                        inputView1.setText("Perimeter");
                        inputView2.setText("Base (b)");
                        break;
                    case 4:
                        inputView1.setText("Side (a)");
                        inputView2.setText("Base (b)");
                        break;
                    case 5:
                        inputView1.setText("Angle y");
                        inputLayout2.setVisibility(View.INVISIBLE);
                        break;
                    case 6:
                        inputView1.setText("Angle x");
                        inputLayout2.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        inputView1.setText("Base");
                        inputView2.setText("Height");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        paraSpinner.setOnItemSelectedListener(paraSelectedListener);
    }

    private void setSpinnerAdapter() {
        paraAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.para_calc_array, R.layout.spinner_background);
                paraAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
        paraSpinner.setAdapter(paraAdapter);
    }

    private void initialLayout() {
        inputLayout1.setVisibility(View.VISIBLE);
        inputLayout2.setVisibility(View.VISIBLE);
        inputView1.setText("Base");
        inputView2.setText("Height");
    }

    private void initializeLayout(View rootView) {
        paragramImage = (ImageView)rootView.findViewById(R.id.para_image);
        inputLayout1 = (LinearLayout)rootView.findViewById(R.id.para_input1_layout);
        inputLayout2 = (LinearLayout)rootView.findViewById(R.id.para_layout2);
        inputView1 = (RobotoTextView)rootView.findViewById(R.id.para_view1);
        inputView2 = (RobotoTextView)rootView.findViewById(R.id.para_view2);
        answer = (RobotoTextView)rootView.findViewById(R.id.para_answer);
        input1 = (EditText)rootView.findViewById(R.id.para_input1);
        input2 = (EditText)rootView.findViewById(R.id.para_input2);
        paraSpinner = (Spinner)rootView.findViewById(R.id.para_spinner);
        calcButton = (RobotoButton)rootView.findViewById(R.id.para_calc_button);
        setCalc = 0;
        sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        showParallelogram();
    }

    private void showParallelogram() {
        Picasso.with(getActivity())
                .load(R.drawable.parallelogram)
                .fit()
                .centerInside()
                .into(paragramImage);
        if(!sp.getBoolean("isTablet", false)) {
            paragramImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowImage enlarge = new ShowImage(getActivity(), R.drawable.parallelogram);
                    enlarge.setDialog();
                }
            });
        }
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
