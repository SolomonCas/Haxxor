package com.example.haxxor;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.haxxor.AbilityListView.AbilityActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button AbilityList_Button;
    Button GenerateInspiration_Button;
    Button Mode_Button;
    ImageView Inspiration_ImageView;
    HexagonalGridView HaxxorGrid;
    HexagonalGridView SentinelGrid;
    int intensity;
    String currentMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.intensity_btn), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Setup separate grids by role (for convenience to save highlighted hexagons when switching roles)
        HaxxorGrid = (HexagonalGridView) findViewById(R.id.haxxorGrid);
        SentinelGrid = (HexagonalGridView) findViewById(R.id.sentinelGrid);
        HaxxorGrid.setGridMode("haxxor");
        SentinelGrid.setGridMode("sentinel");

        // Setup Initial View State (By default, Haxxor mode)
        Button Mode_Button = (Button) findViewById(R.id.mode_button);
        currentMode = getIntent().getStringExtra("mode");

        Mode_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentMode.equals("haxxor")) {
                    currentMode = "sentinel";
                    GenerateInspiration_Button.setVisibility(View.VISIBLE);
                    Inspiration_ImageView.setVisibility(View.VISIBLE);
                    HaxxorGrid.setVisibility(View.INVISIBLE);
                    SentinelGrid.setVisibility(View.VISIBLE);
                }
                else {
                    currentMode = "haxxor";
                    GenerateInspiration_Button.setVisibility(View.INVISIBLE);
                    Inspiration_ImageView.setVisibility(View.INVISIBLE);
                    SentinelGrid.setVisibility(View.INVISIBLE);
                    HaxxorGrid.setVisibility(View.VISIBLE);
                }
                Mode_Button.setText(currentMode);
            }
        });


        DatabaseHelper db = new DatabaseHelper(MainActivity.this);
        db.setupTable();
        AbilityList_Button = (Button) findViewById(R.id.ability_list_button);
        AbilityList_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AbilityActivity.class);
                intent.putExtra("Type", "Haxxor"); // example only
                startActivity(intent);
            }
        });

        GenerateInspiration_Button = (Button) findViewById(R.id.inspiration_rng_button);
        Inspiration_ImageView = (ImageView) findViewById(R.id.inspiration_img);
        // button click -> generates a random index -> opens a modal designed as a card -> card shows trap details
        String[] inspiration = {
                "Decomposition", // #c05bb6
                "Deception", // #1a65bc
                "Constriction", // #0fb2eb
                "Incision", // #f0d6ec
                "Prescription", // #4b8e7f
                "Landmine", // #73116c
                "Nullification" // #f57a29
        };

        AssetManager assetManager = getAssets();
        GenerateInspiration_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int randomIdx = random.nextInt(inspiration.length);

                try {
                    InputStream inputStream = assetManager.open(inspiration[randomIdx] + ".png");
                    Drawable drawable = Drawable.createFromStream(inputStream, null);
                    Inspiration_ImageView.setImageDrawable(drawable);
                    Inspiration_ImageView.setVisibility(View.VISIBLE);
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Inspiration_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inspiration_ImageView.setImageDrawable(null);
                Inspiration_ImageView.setVisibility(View.INVISIBLE);
            }
        });

    }
}