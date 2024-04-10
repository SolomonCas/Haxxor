package com.example.haxxor.AbilityListView;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haxxor.DatabaseHelper;
import com.example.haxxor.MainActivity;
import com.example.haxxor.R;

import java.util.ArrayList;

public class AbilityActivity extends AppCompatActivity {

    ArrayList<Ability> abilityModel = new ArrayList<>();
    ability_RecyclerAdaptor abilityRecyclerAdaptor;
    RecyclerView ability_Recycler;
    Button doneButton;
    TextView tv_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ability);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.intensity_btn), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DatabaseHelper db = new DatabaseHelper(this);
        ability_Recycler = (RecyclerView) findViewById(R.id.abilityRecycler);
        doneButton = (Button) findViewById(R.id.back_btn);
        tv_type = findViewById(R.id.tv_type);

        tv_type.setText(getIntent().getStringExtra("Type"));
        abilityModel = db.getAbilities(getIntent().getStringExtra("Type")); // For Specific Player
//        abilityModel = db.getAllAbilities();
        abilityRecyclerAdaptor = new ability_RecyclerAdaptor(this, abilityModel);
        ability_Recycler.setAdapter(abilityRecyclerAdaptor);
        ability_Recycler.setLayoutManager(new LinearLayoutManager(this));

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}