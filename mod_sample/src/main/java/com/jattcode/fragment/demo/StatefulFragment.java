package com.jattcode.fragment.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jattcode.activity.RandomActivity;
import com.jattcode.fragment.BaseLoggerFragment;
import com.jattcode.fragment.R;
import com.jattcode.fragment.Screen;
import com.jattcode.fragment.ScreenCompatActivity;
import com.jattcode.fragment.ScreenSwitcher;
import com.jattcode.fragment.SwitcherActivity;

/**
 * Created by Javan on 13/7/2015.
 */
public class StatefulFragment extends BaseLoggerFragment implements Screen {

    private TextView getTextView() {
        return (TextView) parentView.findViewById(android.R.id.text1);
    }

    private Button getButtonSimulateProperty() {
        return (Button) parentView.findViewById(android.R.id.button1);
    }

    private Button getButtonDisplayProperty() {
        return (Button) parentView.findViewById(android.R.id.button3);
    }

    private Button getButtonStartActivity() {
        return (Button) parentView.findViewById(android.R.id.button2);
    }

    private ListView getListView() {
        return (ListView) parentView.findViewById(android.R.id.list);
    }

    private Button getButtonStartFragment() {
        return (Button) parentView.findViewById(R.id.button4);
    }

    protected ScreenSwitcher getSwitcher() {
        return ((ScreenCompatActivity)getActivity()).getSwitcher();
    }

    Context context;
    View parentView;

    private String property = "(property is unset)";
    private ArrayAdapter<String> adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        parentView = inflater.inflate(R.layout.fragment_demo_stateful, container, false);
        context = getActivity();

        onLoadModel();
        onInitViews();
        return parentView;
    }

    private void onLoadModel() {
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

        adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, items);
    }

    private void onInitViews() {
        getButtonSimulateProperty().setOnClickListener(clicker);
        getButtonDisplayProperty().setOnClickListener(clicker);
        getButtonStartActivity().setOnClickListener(clicker);

        getListView().setAdapter(adapter);
    }


    private final View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            int position;

            logState("clicker is called");

            switch (v.getId()) {
                case android.R.id.button1: // getButtonSimulateProperty
                    property = "Coffee is beautiful.";
                    position = 10; // simulate click yogurt;
                    getListView().setSelection(position);
                    break;

                case android.R.id.button3: // getButtonDisplayProperty
                    try {
                        position = getListView().getFirstVisiblePosition();
                        String item = (String) getListView().getAdapter().getItem(position);
                        getTextView().setText(property + " And so is " + item);
                    } catch (NullPointerException e) {
                        getTextView().setText(property + " And so is " + "(unset)");
                    }

                    break;
                case android.R.id.button2: // getButtonStartActivity()
                    intent = new Intent(context, RandomActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;

                case R.id.button4: //getButtonStartFragment
                    getSwitcher().changeScreen(SwitcherActivity.ScreenType.RANDOM_FRAGMENT);
                    break;
            }
        }
    };

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("save:property", property);
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        logState("onViewStateRestored: savedInstanceState = " + savedInstanceState);
        if (savedInstanceState != null)
            property = savedInstanceState.getString("save:property", null);
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
