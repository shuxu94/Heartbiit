package com.heartbiit.group2.heartbiit;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;

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

    /**
     * This is the Constructor or our class
     * @param c The activity context
     */

    public AnomHeart(Context c) {
        mcontext = c;
        active = true;
    }

    /**
     * This method creates notifications for our Failsafe mechanism
     */

    public void createNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mcontext)
                        .setContentTitle("CALLING EMERGENCY SERVICE")
                        .setContentText("SWIPE TO CANCEL");
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(mcontext, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mcontext);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        int mId = 001;
        mNotificationManager.notify(mId, mBuilder.build());
    }

    /**
     * This method creates dialogues for our countdown to contact Emergency Services
     */

    public void createDialogue() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mcontext);
        builder1.setMessage("Calling Emergency Services in 4 Seconds");
        builder1.setCancelable(true);
        /*builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });*/
        builder1.setNegativeButton("Cancel Call",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert11 = builder1.create();
        alert11.show();

        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                alert11.setMessage("Calling Emergency in "+ (millisUntilFinished/1000) + " Seconds");
            }

            @Override
            public void onFinish() {
                //info.setVisibility(View.GONE);
                //callTest(this);
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:7324477512"));
                mcontext.startActivity(intent);
            }
        }.start();
    }

    /**
     * Starts the systems process and gets the heart rate to print to console
     */

    public void run() {

        while(active) {
            System.out.println("Start anonmoly");
            int hr = getHR();

            System.out.println("Start running :" + hr + "\n");

            if ((heartAnom(hr, false) > 0)) {

                /*Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:7324477512"));
                mcontext.startActivity(intent);*/

                /*
                Intent intent = new Intent(mcontext, FailSafeActivity.class);
                mcontext.startActivity(intent); */
                //this.createNotification();
                //this.createDialogue();



                active = false;

            }
        }
    }

    /**
     * Initializes the user's heart rate
     */
    public void initHeartRate() {
        //initialize resting heart rate
        avgBpm = 60;
    }

    /**
     * Calculate the maximum heart rate with various algorithms discussed based on age and gender
     * @param age The age of the user
     * @param gender The gender of the user (true == boy/false == girl)
     */
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

    /**
     * The main algorithm for detecting arrythmia for exercise/resting brachycardia/tachycardia
     * @param bpm Beats per Minute!!
     * @param exercise True if the user is exercising, and false if the user is resting
     * @return Returns an integer for the different possible outcomes 0 = No Anomaly,
     * 1 = Exercise Tachycardia, 2 = Exercise Brachycardia,
     * 3 = Resting Tachycardia, 4 = Resting Brachycardia
     */
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

    /**
     * Method to get user's heart rate
     * @return Returns the user's heart rate (From Fitbit)
     */

    public int getHR() {
        int max = 101;
        int min = 50;
        Random r = new Random();
        int randomNum = r.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
