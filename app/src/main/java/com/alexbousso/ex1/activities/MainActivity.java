package com.alexbousso.ex1.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.alexbousso.ex1.FoodItemContent;
import com.alexbousso.ex1.R;

public class MainActivity extends AppCompatActivity {

    private final int seekBarMaxValue = 100;
    private static final int FOOD_SELECTION_REQUEST = 1;

    private EditText editText;
    private Button makeOrderButton;
    private Button selectFoodButton;
    private SeekBar seekBar;
    private CheckBox checkBox;
    private Toast toast;
    private MenuItem sendOrderItem;

    private boolean isSendOrderEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);

        initializeComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        sendOrderItem = menu.findItem(R.id.action_sendOrder);
        sendOrderItem.setEnabled(isSendOrderEnabled);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sendOrder:
                startOrderSentActivity(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FOOD_SELECTION_REQUEST && resultCode == RESULT_OK) {
            FoodItemContent item = (FoodItemContent)data.getSerializableExtra(
                    SelectFoodActivity.SERIALIZED_FOOD_ITEM_INTENT_RESPONSE_TAG);
            if (item == null) {
                Log.w("MainActivity", "Deserialization of getSerializableExtra() has failed.");
                return;
            }

            showToast(String.format(getString(R.string.FoodSelectionToast), item.getFoodName()));
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        sendOrderItem.setEnabled(isSendOrderEnabled);
        return super.onPrepareOptionsMenu(menu);
    }

    private void startOrderSentActivity(Context context) {
        Intent intent = new Intent(context, OrderSentActivity.class);
        startActivity(intent);
    }

    private void initializeComponents() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        editText = (EditText) findViewById(R.id.editText);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        makeOrderButton = (Button) findViewById(R.id.makeOrderButton);
        selectFoodButton = (Button) findViewById(R.id.selectFoodButton);

        initializeEditText();
        initializeSeekBar();
        initializeCheckBox();
        initializeMakeOrderButton();
        initializeSelectFoodButton();
    }

    private void initializeSelectFoodButton() {
        selectFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SelectFoodActivity.class);
                startActivityForResult(intent, FOOD_SELECTION_REQUEST);
            }
        });
    }

    private void initializeMakeOrderButton() {
        makeOrderButton.setEnabled(false);

        makeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOrderSentActivity(v.getContext());
            }
        });
    }

    private void initializeEditText() {
        editText.addTextChangedListener(new TextWatcher() {
            boolean ignoreChange = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!ignoreChange) {
                    String str = s.toString();
                    str = str.replace(".", "");

                    Integer i = 0;
                    try {
                        if (str.length() > 0) {
                            i = Integer.parseInt(str);
                        }
                    } catch (NumberFormatException ignored) {
                    }

                    ignoreChange = true;
                    editText.setText(str);
                    editText.setSelection(editText.getText().length());
                    setSeekBarProgress(i);
                    ignoreChange = false;

                    setMakeOrderButtonEnabledStatus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initializeSeekBar() {
        seekBar.setMax(seekBarMaxValue);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    editText.setText(String.format("%d", progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initializeCheckBox() {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setMakeOrderButtonEnabledStatus();
            }
        });
    }

    private void setSeekBarProgress(Integer progress) {
        if (progress >= 0 && progress <= seekBarMaxValue) {
            seekBar.setProgress(progress);
        } else if (progress > seekBarMaxValue) {
            seekBar.setProgress(seekBarMaxValue);
        } else if (progress < 0) {
            seekBar.setProgress(0);
        }
    }

    private void setMakeOrderButtonEnabledStatus() {
        String str = editText.getText().toString();
        try {
            if (str.length() <= 0 || Integer.parseInt(str) <= 0) {
                setSendOrderEnabled(false);
                return;
            }
        } catch (NumberFormatException e) {
            String text = getResources().getString(R.string.BigNumberError);
            showToast(text);
            setSendOrderEnabled(false);
            return;
        }

        if (!checkBox.isChecked()) {
            setSendOrderEnabled(false);
            return;
        }

        setSendOrderEnabled(true);
    }

    private void setSendOrderEnabled(boolean isEnabled) {
        isSendOrderEnabled = isEnabled;
        makeOrderButton.setEnabled(isEnabled);
    }

    private void showToast(String text) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
