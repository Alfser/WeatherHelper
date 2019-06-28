package com.example.android.bluetoothchat;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

import static com.example.android.bluetoothchat.R.*;


public class ChartFragment extends android.support.v4.app.Fragment {

    public interface OnInterfaceReceived{
        float getOnTemperature();
        float getOnHumidity();
        //int getStatus();
    }
    // Parameters to plot chart
    OnInterfaceReceived mReceived;
    private double mTemperature = 0;
    private double mHumidity = 0;


    //variable to graph
    private final Handler mHandler = new Handler();
    private Runnable mTimer2;
    private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;
    private double graph2LastXValue = 0d;
    private GraphView graph;
    private Boolean isSleep = true;

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(layout.fragment_chart, container, false);

         graph = (GraphView) rootView.findViewById(id.graph1);
        mSeries1 = new LineGraphSeries<>();
        graph.addSeries(mSeries1);
        mSeries1.setColor(Color.RED);
        mSeries1.setDataPointsRadius(10);

        mSeries2 = new LineGraphSeries<>();
        graph.addSeries(mSeries2);
        mSeries2.setTitle("Humidity");
        mSeries2.setColor(Color.BLUE);
        mSeries2.setDataPointsRadius(10);


        //Chart Configs
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(40);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxY(70);
        graph.getViewport().setMinY(0);
        graph.getViewport().setScalable(true);
        graph.setTitle("Humidity and Temperature");
        graph.setTitleColor(Color.BLACK);
        //graph.setClickable(true);

   /*
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSleep) {

                        mHandler.postAtTime(mTimer2, 9000);


                }else{mTimer2.run();}

                isSleep = !isSleep;
            }
        });
        */

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(mTimer2, 4000);

        mTimer2 = new Runnable() {
            @Override
            public void run() {
                //Counter

                    mTemperature = mReceived.getOnTemperature();
                    mHumidity = mReceived.getOnHumidity();


                graph2LastXValue += 1d;

                if(mReceived.getOnHumidity() > 70) {
                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMaxY(100);
                }
                //chart 1
                mSeries2.appendData(new DataPoint(graph2LastXValue, mHumidity), true, 40);
                mHandler.postDelayed(this, 400);

                //chart 2
                mSeries1.appendData(new DataPoint(graph2LastXValue, mTemperature), true, 40 );
            }
        };
        mHandler.postDelayed(mTimer2, 10000);
    }

    @Override
    public void onPause() {
        mHandler.removeCallbacks(mTimer2);
        super.onPause();
    }

   /*
    private DataPoint[] generateData() {
        int count = 30;
        DataPoint[] values = new DataPoint[count];
        for (int i=0; i<count; i++) {
            double x = i;

            double y = mReceived.getOnTemperature();
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }*/



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnInterfaceReceived){
            mReceived = (OnInterfaceReceived) context;
        }else{
            throw new RuntimeException(context.toString()+ " must implement OnInterfaceReceived");
        }
    }

}
