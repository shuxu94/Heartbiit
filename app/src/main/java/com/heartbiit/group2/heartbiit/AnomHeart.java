package com.heartbiit.group2.heartbiit;

/**
 * Created by shuxu on 3/28/15.
 */
public class AnomHeart implements Runnable {
    private int avgBpm;
    private int threshold;
    private double hrmax;
    public int active;

    public void run() {
        System.out.println("Start anonmoly shit");
    }

    public void initHeartRate() {
        //initialize resting heart rate
        avgBpm = 60;
    }

    public void initHrmax(int age, boolean gender) {
        double oakland = 191.5 - (0.007 * age * age);
        double robland = (double) 205.8 - (0.685 * age);
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
}
