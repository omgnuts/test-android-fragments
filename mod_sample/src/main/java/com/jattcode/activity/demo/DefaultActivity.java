package com.jattcode.activity.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jattcode.activity.BaseLoggerActivity;
import com.jattcode.activity.RandomActivity;
import com.jattcode.fragment.R;

public class DefaultActivity extends BaseLoggerActivity {

    private EditText getEditText() {
        return (EditText) findViewById(android.R.id.edit);
    }

    private TextView getTextView() {
        return (TextView) findViewById(android.R.id.text1);
    }

    private Button getButtonSimulateInput() {
        return (Button) findViewById(android.R.id.button1);
    }

    private Button getButtonStartActivity() {
        return (Button) findViewById(android.R.id.button2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo_default);

        getButtonSimulateInput().setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getEditText().setText("Today is a good day");
                        getTextView().setText("Today is a good day");
                    }
                });

        getButtonStartActivity().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DefaultActivity.this, RandomActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

}
