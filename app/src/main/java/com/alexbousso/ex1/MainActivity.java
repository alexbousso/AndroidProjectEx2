package com.alexbousso.ex1;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends Activity {

    private final int seekBarMaxValue = 100;

    private EditText editText;
    private Button makeOrderButton;
    private SeekBar seekBar;
    private CheckBox checkBox;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
    }

    private void initializeComponents() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        editText = (EditText) findViewById(R.id.editText);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        makeOrderButton = (Button) findViewById(R.id.makeOrderButton);

        initializeEditText();
        initializeSeekBar();
        initializeCheckBox();
        initializeMakeOrderButton();
    }

    private void initializeMakeOrderButton() {
        makeOrderButton.setEnabled(false);

        makeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getResources().getString(R.string.OrderSent);
                showToast(text);
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
                makeOrderButton.setEnabled(false);
                return;
            }
        } catch (NumberFormatException e) {
            String text = getResources().getString(R.string.BigNumberError);
            showToast(text);
            makeOrderButton.setEnabled(false);
            return;
        }

        if (!checkBox.isChecked()) {
            makeOrderButton.setEnabled(false);
            return;
        }

        makeOrderButton.setEnabled(true);
    }

    private void showToast(String text) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
