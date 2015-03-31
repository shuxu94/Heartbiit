package com.heartbiit.group2.heartbiit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.Random;

/**
 * Created by shuxu on 3/28/15.
 */
public class AnomHeart implements Runnable {
    private int avgBpm;
    private int threshold;
    private double hrmax;
    public boolean active;
    Context mcontext;

    public AnomHeart(Context c) {
        mcontext = c;
        active = true;
    }

    public void run() {

        while(active) {
            System.out.println("Start anonmoly shit");
            int hr = getHR();

            System.out.println("Start running :" + hr + "\n");

            if ((heartAnom(hr, false) > 0)) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:7324477512"));
                mcontext.startActivity(intent);

                /*
                Intent intent = new Intent(mcontext, FailSafeActivity.class);
                mcontext.startActivity(intent); */



                active = false;

            }
        }
    }

    public void initHeartRate() {
        //initialize resting heart rate
        avgBpm = 60;
    }

    public void initHrmax(int age, boolean gender) {
        double oakland = 191.5 - (0.007 * age * age);
        double robland = 205.8 - (0.685 * age);
        double gulatiFemale = (double) 206 - (0.88 * age);
        double gellishMale = 203.7 / (1 + java.lang.Math.exp(0.033 * (age - 104.3))); //Cast Double?

        if (gender) { //Male
            hrmax = (oakland + robland + gellishMale) / 3;
        } else { //Female
            hrmax = (oakland + robland + gulatiFemale) / 3;
        }
    }

    public int heartAnom(int bpm, boolean exercise) {
        if (exercise) {
            if (bpm > hrmax) {
                return 1; //exercise tachycardia
            } else if (bpm < hrmax * 0.4) {
                return 2; //exercise brachycardia

            } else {
                return 0;
            }
        } else {
            if(bpm > (avgBpm + 40)){
                return 3; //resting tachycardia;
            } else if(bpm < (avgBpm - 40)){
                return 4; //resting brachycardia
            } else{
                return 0;
            }
        }
    }

    public int getHR() {
        int max = 110;
        int min = 50;
        Random r = new Random();
        int randomNum = r.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
