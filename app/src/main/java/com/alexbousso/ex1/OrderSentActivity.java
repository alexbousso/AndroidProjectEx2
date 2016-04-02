package com.alexbousso.ex1;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class OrderSentActivity extends AppCompatActivity {

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
    }
}
