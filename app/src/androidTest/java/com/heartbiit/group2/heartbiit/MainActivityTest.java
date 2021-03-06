package com.heartbiit.group2.heartbiit;

import android.test.ActivityUnitTestCase;
import android.test.UiThreadTest;
import android.widget.Button;

/**
 * Created by shuxu on 4/2/15.
 */
public class MainActivityTest extends ActivityUnitTestCase{

    private MainActivity mAct;
    private Button submitButton;
    private Button callButton;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mAct = (MainActivity) getActivity();

        submitButton = (Button) mAct.findViewById(R.id.submit_button);
        callButton = (Button) mAct.findViewById(R.id.button);

    }
    @UiThreadTest
    public void testSubmitButton() {
        mAct.runOnUiThread(
            new Runnable() {
                public void run() {
                    submitButton.performClick();
                }
            }
        );
    }

    @UiThreadTest
    public void testCallButton() {
        mAct.runOnUiThread(
                new Runnable() {
                    public void run() {
                        callButton.performClick();
                    }
                }
        );
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
