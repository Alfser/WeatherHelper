/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.example.android.bluetoothchat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.example.android.common.activities.SampleActivityBase;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends SampleActivityBase implements BluetoothChatFragment.OnFragmentDataListener, ChartFragment.OnInterfaceReceived {

    public static final String TAG = "MainActivity";

    // manipulation texts
    private boolean mIconChange;
    private RadioGroup radioSexGroup;
    private RadioButton radioCheckButton;
    private TextView tvMessages;
    private TextView tvMessages2;
    //to class FragmentChart manipulation
    private ChartFragment chart = null;
    private BluetoothChatFragment btFragment = null;
    private float mTemperature = 0;
    private float mHumidity = 0;
    private int mStatus = 0;

    Handler mHandle;
    ViewAnimator anime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anime = (ViewAnimator) findViewById(R.id.sample_output);
        tvMessages = (TextView) findViewById(R.id.tv_info_temp);
        tvMessages2 = (TextView)findViewById(R.id.tv_info_humid);
        radioSexGroup = (RadioGroup) findViewById(R.id.rd_group);
        radioCheckButton = findViewById(R.id.rdTemp);
        radioCheckButton.setChecked(true);
            mHandle = new Handler();
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            btFragment = new BluetoothChatFragment();
            chart = new ChartFragment();
            transaction.add(R.id.sample_content_fragment, this.chart, "SecondFragment").addToBackStack("SecondFragment")
                    .hide(chart);
            transaction.add(R.id.sample_content_fragment, btFragment, "FirstFragment").addToBackStack("FirstFragment");


            transaction.commit();


        }

        mRunnable.run();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
       MenuItem FragmentToggle = menu.findItem(R.id.menu_toggle_fragment);
        FragmentToggle.setVisible(findViewById(R.id.sample_content_fragment) instanceof FrameLayout);
        FragmentToggle.setIcon(mIconChange ? R.drawable.ic_action_inicial : R.drawable.ic_action_name);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_toggle_fragment:
                mIconChange = !mIconChange;

                //ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);

                if (mIconChange) {
                    //output.setDisplayedChild(1);
                    FragmentTransaction transactionChart = getSupportFragmentManager().beginTransaction();
                    //transactionChart.hide(btFragment);
                    transactionChart.show(chart);
                    transactionChart.hide(btFragment);
                    transactionChart.commit();
                    radioSexGroup.setVisibility(View.INVISIBLE);


                } else {
                   // output.setDisplayedChild(0);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.hide(chart);
                    transaction.show(btFragment);
                    transaction.commit();
                    radioSexGroup.setVisibility(View.VISIBLE);
                }
                return true;
            case R.id.menu_humid_info :
                Intent intent = new Intent(getApplicationContext(), HumidityInfo.class);
                startActivity(intent);
                return true;

            case R.id.menu_temp_info:
                Intent intent2 = new Intent(getApplicationContext(), TemperatureInfo.class);
                startActivity(intent2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public float getOnTemperature() {
        return mTemperature;
    }

    @Override
    public float getOnHumidity() {
        return mHumidity;
    }

    @Override
    public void OnSentTemperature(float temperature) {
        this.mTemperature = temperature;
    }

    @Override
    public void OnSentHumidity(float humidity) {
        this.mHumidity = humidity;
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            //it's analysing sensor's temperatures data
            int idChecked = radioSexGroup.getCheckedRadioButtonId();

            if (idChecked == R.id.rdTemp) {
                anime.setDisplayedChild(0);
                if (mTemperature > 39) {
                    tvMessages.setText(R.string.intro_message_temp_More_39);
                } else if (mTemperature > 32 && mTemperature < 38) {
                    tvMessages.setText(R.string.intro_message_temp_More_32_38);
                } else if (mTemperature > 23 && mTemperature < 30) {
                    tvMessages.setText(R.string.intro_message_temp_More_23_30);
                } else if (mTemperature > 17 && mTemperature < 22) {
                    tvMessages.setText(R.string.intro_message_temp_between_17_22);
                } else if (mTemperature < 16 && mTemperature != 0) {
                    tvMessages.setText(R.string.intro_message_temp_less_16);
                } else tvMessages.setText(R.string.intro_message);

            }else {
            //it's analysing sensor's humidity data
            anime.setDisplayedChild(1);
                if(mHumidity > 85 ) {
                    tvMessages2.setText(R.string.intro_message_humid_more_85);
                 } else if (mHumidity > 70 && mHumidity < 85) {
                    tvMessages2.setText(R.string.intro_message_humid_between_70_85);
                }else if (mHumidity > 40 && mHumidity < 70){
                 tvMessages2.setText(R.string.intro_message_humid_between_40_70);
                }else if(mHumidity > 20 && mHumidity < 40){
                    tvMessages2.setText(R.string.intro_message_himid_between_20_40);
                 }else if(mTemperature < 20 && mHumidity !=0) {
                    tvMessages2.setText(R.string.intro_message_humid_less_20);
                }else tvMessages2.setText(R.string.intro_message);

            }
            mHandle.postDelayed(mRunnable,4000);


        }
    };
}
