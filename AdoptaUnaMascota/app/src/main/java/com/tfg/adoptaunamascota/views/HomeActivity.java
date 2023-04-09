package com.tfg.adoptaunamascota.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.adapters.CustomExpandableListAdapter;
import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.models.animals.Cats;
import com.tfg.adoptaunamascota.models.animals.Dogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }
}