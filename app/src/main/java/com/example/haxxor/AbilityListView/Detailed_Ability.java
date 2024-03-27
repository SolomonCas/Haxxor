package com.example.haxxor.AbilityListView;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.haxxor.R;

public class Detailed_Ability extends AppCompatActivity {

    TextView detailed_name;
    TextView detailed_effect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed_ability);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        detailed_name = (TextView)  findViewById(R.id.detailed_name);
        detailed_effect = (TextView) findViewById(R.id.detailed_effect);

        detailed_name.setText(getIntent().getStringExtra("Name"));
        detailed_effect.setText(getIntent().getStringExtra("Effect"));

    }
}