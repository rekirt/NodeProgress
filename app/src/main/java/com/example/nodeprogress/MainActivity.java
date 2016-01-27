package com.example.nodeprogress;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawableUtils;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    NodeProgress mp_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp_4 = (NodeProgress)findViewById(R.id.mp_4);
//        Drawable drawable = getResources().getDrawable(R.drawable.circle_red, getTheme());
        Drawable drawable = getResources().getDrawable(R.drawable.circle_red);
        mp_4.setProgressingDrawable(drawable);
    }

}
