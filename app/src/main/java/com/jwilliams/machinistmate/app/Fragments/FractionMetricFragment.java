package com.jwilliams.machinistmate.app.Fragments;

import android.app.Fragment;
import android.content.Context;
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
 * Created by John on 4/20/2014.
 */
public class FractionMetricFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    private static final String KEY_POSITION="position";
    private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private static AdView adView;
    private static AdRequest adRequest;

    public static FractionMetricFragment newInstance(int position) {
        FractionMetricFragment frag=new FractionMetricFragment();
        Bundle args=new Bundle();

        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return(frag);
    }

    public static String getTitle(Context ctxt, int position) {
        return(String.format(ctxt.getString(R.string.fraction_metric_chart), position + 1));
    }

    GridView referenceGridView;

    public FractionMetricFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fraction_metric_detail, container, false);
        setAd(rootView);

        referenceGridView = (GridView)rootView.findViewById(R.id.chart_reference_grid);

        ArrayAdapter<CharSequence> metricStandardAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.fraction_metric_list, R.layout.grid_item_layout);
        referenceGridView.setAdapter(metricStandardAdapter);

        return rootView;
    }

    private void setAd(View rootView){
        adView = (AdView)rootView.findViewById(R.id.frac_adView);
        adRequest = new AdRequest.Builder()
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
