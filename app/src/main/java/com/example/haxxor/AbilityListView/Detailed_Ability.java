package com.example.haxxor.AbilityListView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.haxxor.R;

import java.io.IOException;
import java.io.InputStream;

public class Detailed_Ability extends AppCompatActivity {

    TextView detailed_name;
    TextView detailed_effect;
    ImageView detailed_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed_ability);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.intensity_btn), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        detailed_name = (TextView)  findViewById(R.id.detailed_name);
        detailed_effect = (TextView) findViewById(R.id.detailed_effect);
        detailed_image = (ImageView) findViewById(R.id.detailed_image);

        detailed_name.setText(getIntent().getStringExtra("Name"));
        detailed_effect.setText(getIntent().getStringExtra("Effect"));
        detailed_image.setImageDrawable(getImage(getIntent().getStringExtra("Image")));
    }

    public Drawable getImage(String filename){
        InputStream imageStream = null;
        try {
            imageStream = getAssets().open(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Drawable image = Drawable.createFromStream(imageStream, null);
        return image;
    }
}