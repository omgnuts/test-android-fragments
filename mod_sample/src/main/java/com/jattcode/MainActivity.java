package com.jattcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jattcode.activity.demo.DefaultActivity;
import com.jattcode.activity.demo.StatefulActivity;
import com.jattcode.fragment.R;
import com.jattcode.fragment.SwitcherActivity;

/**
 * Created by Javan on 13/7/2015.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        String[] model = onLoadModel();
        onInitViews(model);
    }

    private String[] onLoadModel() {
        return new String[] {
                "Screen Activity (Fragment Tests)",
                "Default Activity (Activity Tests)",
                "Stateful Activity (Activity Tests)"
        };
    }

    private void onInitViews(String[] model) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, model);

        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(clicker);

    }

    private final AdapterView.OnItemClickListener clicker = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = null;
            switch(position) {
                case 0:
                    intent = new Intent(MainActivity.this, SwitcherActivity.class);
                    break;
                case 1:
                    intent = new Intent(MainActivity.this, StatefulActivity.class);
                    break;
                default: //0
                    intent = new Intent(MainActivity.this, DefaultActivity.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    };
}
