package com.lalagrass.array2chart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        double[] data  = new double[8192];
        for (int i = 0; i < data.length; i++) {
            data[i]  = Math.sin(Math.PI * 2 * i / data.length);
        }
        drawingView = (DrawingView) this.findViewById(R.id.drawingView);
        drawingView.UpdateSpectrum(data);
    }
}
