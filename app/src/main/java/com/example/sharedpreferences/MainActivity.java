package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    EditText editText;
    ListView listView;
    ArrayList<String> items = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        SharedPreferences shared = getSharedPreferences("demo", MODE_PRIVATE);
        String savedItemsString = shared.getString("items", "");
        if (!savedItemsString.isEmpty()) {
            String[] savedItems = savedItemsString.split(",");
            items.addAll(Arrays.asList(savedItems));
        }

        String value = shared.getString("string", "Default value");
        textView.setText(value);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editText.getText().toString();

                SharedPreferences.Editor editor = shared.edit();
                editor.putString("string", msg);
                editor.apply();

                items.add(msg);
                adapter.notifyDataSetChanged();
                textView.setText(msg);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        StringBuilder savedItemsString = new StringBuilder();
        for (String item : items) {
            savedItemsString.append(item).append(",");
        }
        getSharedPreferences("demo", MODE_PRIVATE).edit().putString("items", savedItemsString.toString()).apply();
    }
}
