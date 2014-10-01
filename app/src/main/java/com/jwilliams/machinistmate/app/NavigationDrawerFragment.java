package com.jwilliams.machinistmate.app;


import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.jwilliams.machinistmate.app.Adapters.ExpandableListAdapter;
import com.jwilliams.machinistmate.app.Fragments.CircleFragment;
import com.jwilliams.machinistmate.app.Fragments.DrillChartFragment;
import com.jwilliams.machinistmate.app.Fragments.FeedsFragment;
import com.jwilliams.machinistmate.app.Fragments.FractionMetricFragment;
import com.jwilliams.machinistmate.app.Fragments.GMFragment;
import com.jwilliams.machinistmate.app.Fragments.LengthFragment;
import com.jwilliams.machinistmate.app.Fragments.ObliqueTriangleFragment;
import com.jwilliams.machinistmate.app.Fragments.ParallelogramFragment;
import com.jwilliams.machinistmate.app.Fragments.RectangleFragment;
import com.jwilliams.machinistmate.app.Fragments.RightTriangleFragment;
import com.jwilliams.machinistmate.app.Fragments.SpeedsFragment;
import com.jwilliams.machinistmate.app.Fragments.SquareFragment;
import com.jwilliams.machinistmate.app.Fragments.TrapezoidFragment;
import com.jwilliams.machinistmate.app.Fragments.VolumeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;


    private ExpandableListAdapter listAdapter;
    private ExpandableListView mDrawerListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private int lastGroupExpandedPosition;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mDrawerListView = (ExpandableListView) inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false);
        lastGroupExpandedPosition = -1;
        setHomeFragment();
        prepareListData();
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        mDrawerListView.setAdapter(listAdapter);
        setGroupListener();
        setChildListener();
        return mDrawerListView;
    }
    //

    private void setHomeFragment() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int homeScreenChoice = Integer.parseInt(sp.getString("pref_key_home_choice", "0"));
        Fragment fragment;
        FragmentManager fragmentManager = getFragmentManager();
        switch(homeScreenChoice){
            case 0:
                fragment = new SpeedsFragment();
                break;
            case 1:
                fragment = new FeedsFragment();
                break;
            case 2:
                fragment = new GMFragment();
                break;
            case 3:
                fragment = new LengthFragment();
                break;
            case 4:
                fragment = new VolumeFragment();
                break;
            case 5:
                fragment = new RightTriangleFragment();
                break;
            case 6:
                fragment = new ObliqueTriangleFragment();
                break;
            case 7:
                fragment = new CircleFragment();
                break;
            case 8:
                fragment = new SquareFragment();
                break;
            case 9:
                fragment = new RectangleFragment();
                break;
            case 10:
                fragment = new TrapezoidFragment();
                break;
            case 11:
                fragment = new ParallelogramFragment();
                break;
            case 12:
                fragment = new FractionMetricFragment();
                break;
            case 13:
                fragment = new DrillChartFragment();
                break;
            default:
                fragment = new SpeedsFragment();
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private void setGroupListener() {
        mDrawerListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                if(lastGroupExpandedPosition != -1
                        && i != lastGroupExpandedPosition){
                    mDrawerListView.collapseGroup(lastGroupExpandedPosition);
                }
                lastGroupExpandedPosition = i;
            }
        });
    }

    private void setChildListener() {
        mDrawerListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Fragment fragment = new Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                switch (groupPosition) {
                    case 0:
                        switch (childPosition) {
                            case 0:
                                fragment = new SpeedsFragment();
                                break;
                            case 1:
                                fragment = new FeedsFragment();
                                break;
                            case 2:
                                fragment = new GMFragment();
                                break;
                        }
                        break;
                    case 1:
                        switch (childPosition) {
                            case 0:
                                fragment = new LengthFragment();
                                break;
                            case 1:
                                fragment = new VolumeFragment();
                                break;
                        }
                        break;
                    case 2:
                        switch (childPosition) {
                            case 0:
                                fragment = new RightTriangleFragment();
                                break;
                            case 1:
                                fragment = new ObliqueTriangleFragment();
                                break;
                            case 2:
                                fragment = new CircleFragment();
                                break;
                            case 3:
                                fragment = new SquareFragment();
                                break;
                            case 4:
                                fragment = new RectangleFragment();
                                break;
                            case 5:
                                fragment = new TrapezoidFragment();
                                break;
                            case 6:
                                fragment = new ParallelogramFragment();
                                break;
                        }
                        break;
                    case 3:
                        switch (childPosition) {
                            case 0:
                                fragment = new FractionMetricFragment();
                                break;
                            case 1:
                                fragment = new DrillChartFragment();
                                break;
                        }
                        break;
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                return false;
            }
        });
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    private void prepareListData() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        //Group Items Capture and Contend Cloud
        listDataHeader.add("CNC");
        listDataHeader.add("Conversions");
        listDataHeader.add("Geometry");
        listDataHeader.add("Reference");

        //CNC group items
        List<String> cnc = new ArrayList<String>();
        cnc.add("Speeds");
        cnc.add("Feeds");
        cnc.add("Codes");

        //Conversion group items
        List<String> conversion = new ArrayList<String>();
        conversion.add("Length");
        conversion.add("Volume");

        //Geometry group items
        List<String> geometry = new ArrayList<String>();
        geometry.add("Right Triangle");
        geometry.add("Oblique Triangle");
        geometry.add("Circle");
        geometry.add("Square");
        geometry.add("Rectangle");
        geometry.add("Trapezoid");
        geometry.add("Parallelogram");

        //Reference group items
        List<String> reference = new ArrayList<String>();
        reference.add("Conversion Chart");
        reference.add("Drill Chart");

        listDataChild.put(listDataHeader.get(0), cnc);
        listDataChild.put(listDataHeader.get(1), conversion);
        listDataChild.put(listDataHeader.get(2), geometry);
        listDataChild.put(listDataHeader.get(3), reference);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

/*        if (item.getItemId() == R.id.action_example) {
            Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    private ActionBar getActionBar() {
        return getActivity().getActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }
}
