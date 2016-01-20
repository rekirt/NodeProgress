package com.example.nodeprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    NodeProgress mp_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp_4 = (NodeProgress)findViewById(R.id.mp_4);
        mp_4.setNodeCount(6);
        mp_4.setNodeRadius(20);
        mp_4.setCurrentNodeNo(2);

    }

}
