package com.example.light.matzip;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.light.matzip.fragment.IntroducingFragment;
import com.example.light.matzip.fragment.ResListFragment;

public class MainActivity extends AppCompatActivity implements ResListFragment.ListButton{
    public static final String LIST_FRAGMENT = "list_fragment";
    private static final String INTRO_FRAGMENT = "intro_fragment";
    public static final String RESTAURANT_CHILD = "restaurants";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ResListFragment savedFragment = (ResListFragment) getSupportFragmentManager().findFragmentByTag(LIST_FRAGMENT);
        if (savedFragment == null) {
            ResListFragment fragment = new ResListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.placeHolder, fragment, LIST_FRAGMENT);
            transaction.commit();
        }
    }

    @Override
    public void onListClicked(String primaryKey) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        IntroducingFragment fragment = new IntroducingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IntroducingFragment.INDEX, primaryKey);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.placeHolder, fragment, INTRO_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_new){
            startActivity(new Intent(MainActivity.this, NewRestaurantActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
