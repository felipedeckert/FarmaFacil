package com.example.farmafacil_v1_3_2;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.farmafacil_v1_3_2.adapter.TabsPagerAdapter;
import com.example.farmafacil_v1_3_2.helper.DatabaseHelper;

public class HomeActivity extends FragmentActivity implements ActionBar.TabListener{

	private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
	
    //DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
 
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
        // Tabs to be used
        String[] tabs = {"Busca", "Receitas"};
        
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));}
        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }
         
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
         
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    	}
                   	
    	@Override
        public void onTabReselected(Tab tab, FragmentTransaction ft) {
    		View focus = getCurrentFocus();
            if (focus != null) {
                hiddenKeyboard(focus);
            }
        }
     
        @Override
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            // on tab selected
            // show respected fragment view
            viewPager.setCurrentItem(tab.getPosition());
        }
        // hide soft keyboard when fragments change
        @Override
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        	View focus = getCurrentFocus();
            if (focus != null) {
                hiddenKeyboard(focus);
            }
        }
        // hide keyboard
        private void hiddenKeyboard(View v) {
            InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        
}
