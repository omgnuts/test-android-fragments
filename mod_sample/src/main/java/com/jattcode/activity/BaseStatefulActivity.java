package com.jattcode.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jattcode.activity.demo.RandomActivity;
import com.jattcode.activity.demo.StatefulActivity;
import com.jattcode.fragment.R;

public abstract class BaseStatefulActivity extends AppCompatActivity {

    protected EditText getEditText() {
        return (EditText) findViewById(android.R.id.edit);
    }

    protected TextView getTextView() {
        return (TextView) findViewById(android.R.id.text1);
    }

    protected Button getButtonSimulateInput() {
        return (Button) findViewById(android.R.id.button1);
    }

    protected Button getButtonStartActivity() {
        return (Button) findViewById(android.R.id.button2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stateful);

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
                Intent intent = new Intent(BaseStatefulActivity.this, RandomActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

}
