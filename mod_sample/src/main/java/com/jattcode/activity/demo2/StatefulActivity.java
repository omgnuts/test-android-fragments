package com.jattcode.activity.demo2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jattcode.activity.BaseLoggerActivity;
import com.jattcode.activity.RandomActivity;
import com.jattcode.fragment.R;

public class StatefulActivity extends BaseLoggerActivity {

    private TextView getTextView() {
        return (TextView) findViewById(android.R.id.text1);
    }

    private Button getButtonSimulateProperty() {
        return (Button) findViewById(android.R.id.button1);
    }

    private Button getButtonDisplayProperty() {
        return (Button) findViewById(android.R.id.button3);
    }

    private Button getButtonStartActivity() {
        return (Button) findViewById(android.R.id.button2);
    }

    private ListView getListView() {
        return (ListView) findViewById(android.R.id.list);
    }

    private String property = "(property is unset)";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        logState("onCreate: savedInstanceState = " + savedInstanceState);

        setContentView(R.layout.activity_demo2_stateful);

        getButtonSimulateProperty().setOnClickListener(clicker);
        getButtonDisplayProperty().setOnClickListener(clicker);
        getButtonStartActivity().setOnClickListener(clicker);

        String[] items = {
                "START",
                "Milk", "Butter", "Yogurt", "Toothpaste", "Ice Cream",
                "Milk", "Butter", "Yogurt", "Toothpaste", "Ice Cream",
                "Milk", "Butter", "Yogurt", "Toothpaste", "Ice Cream",
                "Milk", "Butter", "Yogurt", "Toothpaste", "Ice Cream",
                "Milk", "Butter", "Yogurt", "Toothpaste", "Ice Cream",
                "Milk", "Butter", "Yogurt", "Toothpaste", "Ice Cream",
                "END"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        getListView().setAdapter(adapter);

    }


    private final View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            int position;

            switch (v.getId()) {
                case android.R.id.button1: // getButtonSimulateProperty
                    property = "Coffee is beautiful.";

                    position = 10; // simulate click yogurt;
                    getListView().setSelection(position);
                    break;

                case android.R.id.button3: // getButtonDisplayProperty
                    position = getListView().getFirstVisiblePosition();
                    String item = (String) getListView().getAdapter().getItem(position);

                    getTextView().setText(property + " And so is " + item);

                    break;
                case android.R.id.button2: // getButtonStartActivity()
                    intent = new Intent(StatefulActivity.this, RandomActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
            }
        }
    };


    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("save:property", property);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        logState("onRestoreInstanceState: savedInstanceState = " + savedInstanceState);

        if (savedInstanceState != null)
            property = savedInstanceState.getString("save:property", null);

    }
}
