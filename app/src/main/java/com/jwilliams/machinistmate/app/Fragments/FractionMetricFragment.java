package com.jwilliams.machinistmate.app.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jwilliams.machinistmate.app.R;


/**
 * Created by John Williams
 * Fraction Metric Chart View-Controller
 */
public class FractionMetricFragment extends Fragment {

    //private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private static AdView adView;
    private View rootView;

    public FractionMetricFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fraction_metric_detail, container, false);
        setAd();
        initializeVariables();

        return rootView;
    }

    private void initializeVariables() {
        GridView referenceGridView = (GridView) rootView.findViewById(R.id.chart_reference_grid);
        ArrayAdapter<CharSequence> metricStandardAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.fraction_metric_list, R.layout.grid_item_layout);
        referenceGridView.setAdapter(metricStandardAdapter);
    }

    private void setAd(){
        adView = (AdView)rootView.findViewById(R.id.frac_adView);
        AdRequest adRequest = new AdRequest.Builder()
/*                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(TEST_DEVICE_ID)*/
                .build();
        adView.loadAd(adRequest);
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
