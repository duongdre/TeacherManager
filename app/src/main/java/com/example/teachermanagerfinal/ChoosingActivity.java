package com.example.teachermanagerfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ChoosingActivity extends AppCompatActivity {

    ImageButton nextToGV, nextToMajor, nextToBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextToGV = findViewById(R.id.btn_teacher);
        nextToMajor = findViewById(R.id.btn_major);
        nextToBook = findViewById(R.id.btn_book);

        nextToGV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTeacherActivity();
            }
        });

        nextToMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBoMonActivity();
            }
        });
    }

    public void openTeacherActivity(){
        Intent intent = new Intent(this, GVActivity.class);
        startActivity(intent);
    }

    public void openBoMonActivity(){
        Intent intent = new Intent(this, BMActivity.class);
        startActivity(intent);
    }
}
