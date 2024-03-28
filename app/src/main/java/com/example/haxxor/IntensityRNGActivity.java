package com.example.haxxor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class IntensityRNGActivity extends AppCompatActivity {

    Button generate_btn;
    Button start_game_btn;
    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intensity_rng);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.intensity_btn), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        generate_btn = findViewById(R.id.intensity_btn);
        tv_result = findViewById(R.id.tv_result);
        start_game_btn = findViewById(R.id.start_game_btn);
        generate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int random = new Random().nextInt(3);
                tv_result.setText(String.valueOf(random + 1));
                start_game_btn.setVisibility(View.VISIBLE);
            }
        });

        start_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntensityRNGActivity.this, MainActivity.class);
                i.putExtra("intensity", tv_result.getText().toString());
                startActivity(i);
            }
        });
    }
}