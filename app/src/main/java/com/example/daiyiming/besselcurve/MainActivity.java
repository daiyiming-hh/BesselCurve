package com.example.daiyiming.besselcurve;

import com.example.daiyiming.besselcurve.views.BesselView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private BesselView mBvDisplay = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBvDisplay = (BesselView) findViewById(R.id.bv_display);
    }

    public void clickToAdd(View view) {
        mBvDisplay.add();
    }

}
