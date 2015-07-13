package com.jattcode.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jattcode.fragment.R;

public class RandomActivity extends AppCompatActivity {

    private TextView getTextView() {
        return (TextView) findViewById(android.R.id.text1);
    }

    private Button getButtonStartActivity() {
        return (Button) findViewById(android.R.id.button2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_random);

        getTextView().setText(String.valueOf(System.currentTimeMillis()));

        getButtonStartActivity().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RandomActivity.this, RandomActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

}
