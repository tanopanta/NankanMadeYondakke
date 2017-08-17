package com.example.tattata.nankanmadeyondakke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    ImageButton addButton;
    LinearLayout verticalLayout;
    Shelf shelf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (ImageButton)findViewById(R.id.addButton);
        verticalLayout = (LinearLayout)findViewById(R.id.verticalLayout);
        shelf = new Shelf(this, verticalLayout);
        shelf.loadData();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shelf.addBook("", -1);
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        shelf.saveData();
    }
}
