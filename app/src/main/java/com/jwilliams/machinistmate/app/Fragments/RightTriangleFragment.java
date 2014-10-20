package com.jwilliams.machinistmate.app.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoButton;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoTextView;
import com.jwilliams.machinistmate.app.Formatter;
import com.jwilliams.machinistmate.app.GeometryClasses.RightTriangle;
import com.jwilliams.machinistmate.app.R;

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
    private RobotoButton calcButton;
    private RobotoButton clearButton;
    private RobotoButton questionButton;
    private int xPos;
    private int yPos;
    private ArrayAdapter<CharSequence> adapter;
    private View rootView;
    private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private AdView adView;
    private AdRequest adRequest;
    private RightTriangle rt;


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
        setAd(rootView);
        initializeLayout(rootView);
        setSpinnerAdapters();
        setSpinnerXListener();
        setSpinnerYListener();
        setClearButtonListener();
        setQuestionButtonListener();
        setCalcButtonListener();
        return rootView;
    }

    private void setAd(View rootView) {
        adView = (AdView) rootView.findViewById(R.id.rt_adView);
        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(TEST_DEVICE_ID)
                .build();
        adView.loadAd(adRequest);
    }

    private void setCalcButtonListener() {
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rt = new RightTriangle();
                rt.setXPos(xPos);
                rt.setYPos(yPos);
                boolean ch = false;
                boolean co = false;
                boolean ca = false;
                boolean cx = false;
                boolean cy = false;
                int count = 0;
                Log.d("I'm in the clicker thingy", "");
                Log.d("count = ", Integer.toString(count));


                try {
                    rt.setH(Double.parseDouble(sideH.getText().toString()));
                } catch (NumberFormatException e) {
                    ch = true;
                    count++;
                }
                try {
                    rt.setO(Double.parseDouble(sideO.getText().toString()));
                } catch (NumberFormatException e) {
                    co = true;
                    count++;
                }
                try {
                    rt.setA(Double.parseDouble(sideA.getText().toString()));
                } catch (NumberFormatException e) {
                    ca = true;
                    count++;
                }
                try {
                    rt.setX(Double.parseDouble(angleX.getText().toString()));
                } catch (NumberFormatException e) {
                    cx = true;
                    count++;
                }
                try {
                    rt.setY(Double.parseDouble(angleY.getText().toString()));
                } catch (NumberFormatException e) {
                    cy = true;
                    count++;
                }

                Log.d("count = ", Integer.toString(count));

                if (count > 4 || count < 2) {
                    Toast.makeText(getActivity(), "You must enter 2 values, and they cannot both be angles.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!cx && !cy) {
                    Toast.makeText(getActivity(), "You must enter 2 values, and they cannot both be angles.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!cx) {
                    if (rt.getX() < 1 || rt.getX() >= 90) {
                        Toast.makeText(getActivity(), "Angle x can't be greater than 90 or less than 1", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (!cy) {
                    if (rt.getY() < 1 || rt.getY() >= 90) {
                        Toast.makeText(getActivity(), "Angle y can't be greater than 90 or less than 1", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (rt.getH() < rt.getA() && rt.getH() != 0) {
                    Toast.makeText(getActivity(), "Side H must always be longer than side A.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!ch && !co) {
                    rt.calcFromHO();
                } else if (!ch && !ca) {
                    rt.calcFromHA();
                } else if (!ch && !cx) {
                    rt.calcFromHX();
                } else if (!ch && !cy) {
                    rt.calcFromHY();
                } else if (!co && !ca) {
                    rt.calcFromOA();
                } else if (!co && !cx) {
                    rt.calcFromOX();
                } else if (!co && !cy) {
                    rt.calcFromOY();
                } else if (!ca && !cx) {
                    rt.calcFromAX();
                } else if (!ca && !cy) {
                    rt.calcFromAY();
                }

                postAnswers();
            }


            private void postAnswers() {
                Toast.makeText(getActivity(), "I haz all the answers.", Toast.LENGTH_SHORT).show();
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                int precision = Integer.parseInt(sp.getString("pref_key_geometry_precision", "2"));
                sideH.setText(Formatter.formatOutput(rt.getH(), precision));
                sideO.setText(Formatter.formatOutput(rt.getO(), precision));
                sideA.setText(Formatter.formatOutput(rt.getA(), precision));
                angleX.setText(Formatter.formatOutput(rt.getX(), precision));
                angleY.setText(Formatter.formatOutput(rt.getY(), precision));
                areaAnswer.setText(Formatter.formatOutput(rt.getArea(), precision));
                perimeterAnswer.setText(Formatter.formatOutput(rt.getPerimeter(), precision));
            }

        });
    }

    private void setQuestionButtonListener() {
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    private void initializeLayout(View rootView) {
        sideH = (EditText) rootView.findViewById(R.id.rt_h_input);
        sideO = (EditText) rootView.findViewById(R.id.rt_o_input);
        sideA = (EditText) rootView.findViewById(R.id.rt_a_input);
        angleX = (EditText) rootView.findViewById(R.id.rt_x_input);
        angleY = (EditText) rootView.findViewById(R.id.rt_y_input);
        areaAnswer = (RobotoTextView) rootView.findViewById(R.id.rt_area_answer);
        perimeterAnswer = (RobotoTextView) rootView.findViewById(R.id.rt_perimeter_answer);
        angleXSpinner = (Spinner) rootView.findViewById(R.id.rt_x_spinner);
        angleYSpinner = (Spinner) rootView.findViewById(R.id.rt_y_spinner);
        calcButton = (RobotoButton) rootView.findViewById(R.id.rt_calc_button);
        clearButton = (RobotoButton) rootView.findViewById(R.id.rt_clear_button);
        questionButton = (RobotoButton) rootView.findViewById(R.id.rt_question_button);
        xPos = 0;
        yPos = 0;
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
        adapter = ArrayAdapter.createFromResource(getActivity(),
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
