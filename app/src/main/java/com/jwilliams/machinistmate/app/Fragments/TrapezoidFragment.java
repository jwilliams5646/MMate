package com.jwilliams.machinistmate.app.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoButton;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoTextView;
import com.jwilliams.machinistmate.app.GeometryClasses.ShowImage;
import com.jwilliams.machinistmate.app.GeometryClasses.Trapezoid;
import com.jwilliams.machinistmate.app.R;
import com.squareup.picasso.Picasso;

/**
 * Created by John Williams
 * View-Controller for Trapezoid.
 */
public class TrapezoidFragment extends Fragment {

    private View rootView;
    private Spinner trapChoice;
    private LinearLayout input1Layout;
    private LinearLayout input2Layout;
    private LinearLayout input3Layout;
    private LinearLayout input4Layout;
    private RobotoTextView input1View;
    private RobotoTextView input2View;
    private RobotoTextView input3View;
    private RobotoTextView input4View;
    private RobotoTextView answer;
    private EditText input1;
    private EditText input2;
    private EditText input3;
    private EditText input4;
    private RobotoButton calcButton;
    private int choice;
    //private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private AdView adView;


    public TrapezoidFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.trapezoid_detail, container, false);
        setAd();
        initializeLayout();
        setSpinnerAdapter();
        setSpinnerListener();
        setCalcListener();
        return rootView;
    }

    private void setAd(){
        adView = (AdView)rootView.findViewById(R.id.trap_adView);
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
                Trapezoid trap = new Trapezoid();
                trap.calcTrapezoid(input1, input2, input3, input4, answer, choice, getActivity());

            }
        });
    }



    private void setSpinnerListener() {
        AdapterView.OnItemSelectedListener trapSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choice = i;
                switch(i){
                    case 0:
                        clearLayouts();
                        setInitialLayout();
                        set3Layouts();
                        break;
                    case 1:
                        clearLayouts();
                        input1View.setText("Base (b)");
                        input2View.setText("Height (h)");
                        input3View.setText("Area");
                        set3Layouts();
                        break;
                    case 2:
                        clearLayouts();
                        input1View.setText("Base (a)");
                        input2View.setText("Height (h)");
                        input3View.setText("Area");
                        set3Layouts();
                        break;
                    case 3:
                        clearLayouts();
                        input1View.setText("Base (a)");
                        input2View.setText("Base (b)");
                        input3View.setText("Side (d)");
                        input4View.setText("Perimeter");
                        set4Layouts();
                        break;
                    case 4:
                        clearLayouts();
                        input1View.setText("Base (a)");
                        input2View.setText("Base (b)");
                        input3View.setText("Side (c)");
                        input4View.setText("Perimeter");
                        set4Layouts();
                        break;
                    case 5:
                        clearLayouts();
                        input1View.setText("Base (a)");
                        input2View.setText("Base (b)");
                        input3View.setText("Area");
                        set3Layouts();
                        break;
                    case 6:
                        clearLayouts();
                        input1View.setText("Base (a)");
                        input2View.setText("Base (b)");
                        input1Layout.setVisibility(View.VISIBLE);
                        input2Layout.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        clearLayouts();
                        input1View.setText("Base (a)");
                        input2View.setText("Base (b)");
                        input3View.setText("Side (c)");
                        input4View.setText("Side (d)");
                        set4Layouts();
                        break;
                    default:
                        clearLayouts();
                        setInitialLayout();
                        set3Layouts();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        trapChoice.setOnItemSelectedListener(trapSelectedListener);
    }

    private void setSpinnerAdapter() {
        ArrayAdapter<CharSequence> trapAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.trap_calc_array, R.layout.spinner_background);
        trapAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
        trapChoice.setAdapter(trapAdapter);
    }

    private void clearLayouts(){
        input1Layout.setVisibility(View.INVISIBLE);
        input2Layout.setVisibility(View.INVISIBLE);
        input3Layout.setVisibility(View.INVISIBLE);
        input4Layout.setVisibility(View.INVISIBLE);
    }

    private void set3Layouts(){
        input1Layout.setVisibility(View.VISIBLE);
        input2Layout.setVisibility(View.VISIBLE);
        input3Layout.setVisibility(View.VISIBLE);
        input4Layout.setVisibility(View.INVISIBLE);
    }

    private void set4Layouts(){
        input1Layout.setVisibility(View.VISIBLE);
        input2Layout.setVisibility(View.VISIBLE);
        input3Layout.setVisibility(View.VISIBLE);
        input4Layout.setVisibility(View.VISIBLE);
    }

    private void initializeLayout() {
        trapChoice = (Spinner)rootView.findViewById(R.id.trap_spinner);
        input1Layout =(LinearLayout)rootView.findViewById(R.id.trap_input1_layout);
        input2Layout =(LinearLayout)rootView.findViewById(R.id.trap_input2_layout);
        input3Layout =(LinearLayout)rootView.findViewById(R.id.trap_input3_layout);
        input4Layout =(LinearLayout)rootView.findViewById(R.id.trap_input4_layout);
        input1View = (RobotoTextView)rootView.findViewById(R.id.trap_input1_view);
        input2View = (RobotoTextView)rootView.findViewById(R.id.trap_input2_view);
        input3View = (RobotoTextView)rootView.findViewById(R.id.trap_input3_view);
        input4View = (RobotoTextView)rootView.findViewById(R.id.trap_input4_view);
        answer = (RobotoTextView)rootView.findViewById(R.id.trap_answer);
        input1 = (EditText)rootView.findViewById(R.id.trap_input1);
        input2 = (EditText)rootView.findViewById(R.id.trap_input2);
        input3 = (EditText)rootView.findViewById(R.id.trap_input3);
        input4 = (EditText)rootView.findViewById(R.id.trap_input4);
        calcButton = (RobotoButton)rootView.findViewById(R.id.trap_calc_button);
        choice = 0;
        showImage();
        setInitialLayout();
    }

    private void showImage() {
        ImageView trapImage = (ImageView)rootView.findViewById(R.id.trap_image);
        Picasso.with(getActivity())
                .load(R.drawable.trapezoid)
                .fit()
                .centerInside()
                .into(trapImage);

        SharedPreferences shpr = getActivity().getPreferences(Context.MODE_PRIVATE);
        if(!shpr.getBoolean("isTablet", false)) {
            trapImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ShowImage enlarge = new ShowImage(getActivity(), R.drawable.trapezoid);
                    enlarge.setDialog();
                }
            });
        }
    }

    private void setInitialLayout() {
        input1View.setText("Base (a)");
        input2View.setText("Base (b)");
        input3View.setText("Height (h)");
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
