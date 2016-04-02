package com.alexbousso.ex1;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class SelectFoodActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_food);

        // Changing the title of the activity
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            String orderSentStr = getResources().getString(R.string.SelectFoodActivityTitle);
            actionBar.setTitle(orderSentStr);
        }
    }
}
