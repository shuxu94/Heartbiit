package com.heartbiit.group2.heartbiit;

import android.app.Application;
import android.app.Instrumentation;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private Application mApp;
    private Instrumentation mInstrumentation;

    public ApplicationTest() {
        super(Application.class);

    }

}