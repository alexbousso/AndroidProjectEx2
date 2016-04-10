package com.alexbousso.ex1.activities.mainWindow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.alexbousso.ex1.FoodItemContent;
import com.alexbousso.ex1.R;
import com.alexbousso.ex1.activities.OrderSentActivity;

public class MainActivity extends AppCompatActivity
        implements MainFragment.OnFragmentInteractionListener,
        FoodListFragment.OnFragmentInteractionListener {
    private MainFragment mainFragment;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);

        // Initialize MainFragment
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            mainFragment = new MainFragment();
            mainFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(
                    R.id.fragment_container, mainFragment).commit();
        }
    }

    @Override
    public void onFoodSelectButtonClicked() {
        if (findViewById(R.id.fragment_container) != null) {
            FoodListFragment foodListFragment = new FoodListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, foodListFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onMakeOrderButtonClicked() {
        startOrderSentActivity(this);
    }

    @Override
    public void onFoodItemSelected(FoodItemContent item) {
        if (findViewById(R.id.fragment_container) != null) {
            getSupportFragmentManager().popBackStackImmediate();
            mainFragment.updateFoodSelection(item);
        } else {
            MainFragment fragment = (MainFragment) getSupportFragmentManager().findFragmentById(
                    R.id.main_fragment);
            fragment.updateFoodSelection(item);
        }
    }

    private void startOrderSentActivity(Context context) {
        Intent intent = new Intent(context, OrderSentActivity.class);
        startActivity(intent);
    }

    public void showToast(String text) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
