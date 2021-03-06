package com.jwilliams.machinistmate.app.Fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jwilliams.machinistmate.app.Adapters.AddressAdapter;
import com.jwilliams.machinistmate.app.Adapters.CodeAdapter;
import com.jwilliams.machinistmate.app.Adapters.DbHelper;
import com.jwilliams.machinistmate.app.Adapters.GMAddContent;
import com.jwilliams.machinistmate.app.Adapters.GMCodeContent;
import com.jwilliams.machinistmate.app.ExtendedClasses.RobotoButton;
import com.jwilliams.machinistmate.app.R;

import java.util.ArrayList;


/**
 * Created by John Williams
 * G and M Codes View-Controller
 */
public class GMFragment extends Fragment {
    private LinearLayout codeHeader;
    private LinearLayout addressHeader;
    private RobotoButton gButton;
    private RobotoButton mButton;
    private RobotoButton addressButton;
    private ListView codeList;
    public ArrayList<GMCodeContent> codeContent;
    private CodeAdapter codeAdapter;
    private ListView addList;
    public ArrayList<GMAddContent> addContent;
    private AddressAdapter addAdapter;
    private int dbSwitch;
    //private static final String TEST_DEVICE_ID = "03f3f1d189532cca";
    private AdView adView;
    private View rootView;
    private  Animation animate;

    DbHelper myDbHelper;


    public GMFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.g_m_codes, container, false);
        dbSwitch = 0;
        setAd();
        setLayout();
        setCodeAdapter();
        setAddressAdapter();
        setButtonListeners();
        new setList().execute();

        return rootView;
    }

    private void setLayout() {
        codeHeader = (LinearLayout)rootView.findViewById(R.id.gm_codes_header);
        addressHeader = (LinearLayout)rootView.findViewById(R.id.gm_address_header);
        gButton = (RobotoButton)rootView.findViewById(R.id.gm_g_button);
        mButton = (RobotoButton)rootView.findViewById(R.id.gm_m_button);
        addressButton = (RobotoButton)rootView.findViewById(R.id.gm_address_button);
        codeList = (ListView)rootView.findViewById(R.id.gm_code_list);
        addList = (ListView)rootView.findViewById(R.id.gm_address_list);
        animate = AnimationUtils.loadAnimation(getActivity(), R.anim.touch_anim);
    }

    private void setButtonListeners() {
        gButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                gButton.startAnimation(animate);
                dbSwitch = 0;
                new setList().execute();
            }
        });
        mButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButton.startAnimation(animate);
                dbSwitch = 1;
                new setList().execute();
            }
        });
        addressButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressButton.startAnimation(animate);
                dbSwitch = 2;
                new setList().execute();
            }
        });
    }

    private void setAd(){
        adView = (AdView)rootView.findViewById(R.id.drill_adView);
        AdRequest adRequest = new AdRequest.Builder()
/*                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(TEST_DEVICE_ID)*/
                .build();
        adView.loadAd(adRequest);
    }



    private void setCodeAdapter(){
        codeContent = new ArrayList<GMCodeContent>();
        //the adapter, sets the list to the layout
        codeAdapter = new CodeAdapter(codeContent, getActivity());
    }
    private void setAddressAdapter(){
        addContent = new ArrayList<GMAddContent>();
        //instantiates the array list used for the adapter
        //the adapter, sets the list to the layout
        addAdapter = new AddressAdapter(addContent, getActivity());
    }

    public class setList extends AsyncTask {
        Cursor c = null;

        @Override
        protected void onPreExecute(){
            if(codeContent!=null){
                codeContent.clear();
                codeAdapter = null;
            }

            if(addContent!=null){
                addContent.clear();
                addAdapter = null;
            }

            if(dbSwitch <= 1){
                setCodeAdapter();
            }else if(dbSwitch==2){
                setAddressAdapter();
            }

            codeList.setAdapter(null);
            addList.setAdapter(null);
            codeList.setVisibility(View.GONE);
            addList.setVisibility(View.GONE);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            myDbHelper = new DbHelper(getActivity());
            myDbHelper.openDataBase();

            switch (dbSwitch){
                case 0:
                    c = myDbHelper.getGCodes();
                    createListAdapter(c);
                    break;
                case 1:
                    c = myDbHelper.getMCodes();
                    createListAdapter(c);
                    break;
                case 2:
                    c = myDbHelper.getAddCodes();
                    createAddressListAdapter(c);
                    break;
            }

            myDbHelper.close();
            return null;
        }


        private void createListAdapter(Cursor c) {
            codeContent.clear();
            while (c.moveToNext()) {
                GMCodeContent Content = new GMCodeContent();
                Content.setCode(c.getString(c.getColumnIndex("code")));
                Content.setDesc(c.getString(c.getColumnIndex("desc")));
                Content.setMill(c.getString(c.getColumnIndex("mill")));
                Content.setTurn(c.getString(c.getColumnIndex("turn")));
                codeContent.add(Content);
            }
        }

        private void createAddressListAdapter(Cursor c) {
            addContent.clear();
            while(c.moveToNext()) {
                GMAddContent Content = new GMAddContent();
                Content.setCode(c.getString(c.getColumnIndex("code")));
                Content.setDesc(c.getString(c.getColumnIndex("desc")));
                addContent.add(Content);
            }
        }

        @Override
        protected void onPostExecute(Object result){
            switch(dbSwitch){
                case 0:
                    addressHeader.setVisibility(View.GONE);
                    codeList.setAdapter(codeAdapter);
                    codeHeader.setVisibility(View.VISIBLE);
                    codeList.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    addressHeader.setVisibility(View.GONE);
                    codeList.setAdapter(codeAdapter);
                    codeHeader.setVisibility(View.VISIBLE);
                    codeList.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    codeHeader.setVisibility(View.GONE);
                    addList.setAdapter(addAdapter);
                    addressHeader.setVisibility(View.VISIBLE);
                    addList.setVisibility(View.VISIBLE);
                    break;
            }



           if(addAdapter!=null){
                addAdapter=null;
            }
            c = null;
            this.cancel(true);
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
