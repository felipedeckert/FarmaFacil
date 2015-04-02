package com.example.farmafacil_v1_3_2.adapter;
 
import com.example.farmafacil_v1_3_2.BuscaFragment;
import com.example.farmafacil_v1_3_2.ReceitasFragment;
import com.example.farmafacil_v1_3_2.SeguindoFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class TabsPagerAdapter extends FragmentPagerAdapter {
 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Busca fragment activity
            return new BuscaFragment();
        case 1:
            // receitas fragment activity
            return new ReceitasFragment();
        //case 2:
            // Seguindo fragment activity
            //return new SeguindoFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }
 
}