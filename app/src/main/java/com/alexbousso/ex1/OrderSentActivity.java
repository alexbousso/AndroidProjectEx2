package com.alexbousso.ex1;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class OrderSentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_sent);

        // Changing the title of the activity
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            String orderSentStr = getResources().getString(R.string.OrderSentActivityTitle);
            actionBar.setTitle(orderSentStr);
        }
    }
}
