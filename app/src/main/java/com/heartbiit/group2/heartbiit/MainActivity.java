package com.heartbiit.group2.heartbiit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;


public class MainActivity extends ActionBarActivity {

    boolean male;
    AnomHeart detector;
    FitbitData fitbitData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // testing

        detector = new AnomHeart(this);
        //(new FitbitTask()).execute();
    }

    /**
     * Method that creates dialogue for calling emergency services
     */
    public void createDialogue() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
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
                startActivity(intent);
            }
        }.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Creates the call button
     * @param view The view that's called by the system
     */

    public void callButton(View view) {
        //callTest(this);
        createDialogue();

    }

    /**
     * Calls the number inputted
     * @param c Context!
     */

    public static void callTest(Context c) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:7324477512"));
        c.startActivity(intent);
    }

    /**
     * Displays the view for entering user information
     * @param view Current View
     */

    public void getUserInfo(View view) {
        int userAge;
        EditText mEdit = (EditText)findViewById(R.id.editText);
        userAge = Integer.parseInt(mEdit.getText().toString());
        mEdit.getText().clear();
        detector.initHrmax(userAge, male);
        detector.initHeartRate();

        (new Thread(detector)).start();

    }

    /**
     * Creates the button for the user to select gender
     * @param view Current View
     */

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    // Pirates are the best
                    male = true;
                    break;
            case R.id.radio_female:
                if (checked)
                    // Ninjas rule
                    male = false;
                    break;
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
