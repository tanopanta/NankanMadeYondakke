package com.example.tattata.nankanmadeyondakke;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton addButton;
    ImageButton saveButton;
    ImageButton closeButton;
    LinearLayout verticalLayout;
    Shelf shelf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (ImageButton)findViewById(R.id.addButton);
        saveButton = (ImageButton)findViewById(R.id.saveButton);
        closeButton = (ImageButton)findViewById(R.id.closeButton);
        verticalLayout = (LinearLayout)findViewById(R.id.verticalLayout);
        shelf = new Shelf(this, verticalLayout);
        shelf.loadData();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shelf.addBook("", -1);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shelf.saveData();
                Toast.makeText(getApplicationContext(), "保存が完了しました。", Toast.LENGTH_SHORT).show();
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAndRemoveTask();
                } else {
                    finish();
                }
            }
        });
    }
}
