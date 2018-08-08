package com.udacity.gradle.androidtesting;

import android.support.test.runner.AndroidJUnit4;

import com.udacity.gradle.builditbigger.EndpointsAsyncTask;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

/**
 * Created by rainman-d on 8/5/18.
 * Adopted from Dan Lowe's answer at:
 * https://stackoverflow.com/questions/2321829/android-asynctask-testing-with-android-test-framework
 */
@RunWith(AndroidJUnit4.class)
public class AsyncTest {
    String joke = null;

    @Test
    public void testEndpointAsyncTask() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        EndpointsAsyncTask task = new EndpointsAsyncTask();
        task.setListener(new EndpointsAsyncTask.EndpointTaskHandler() {
            @Override
            public void handleTaskOutput(String output) {
                joke = output;
                signal.countDown();
            }
        });
        task.execute();
        signal.await();

        if(joke.contains("timed out")){
            joke = null;
        }
        Assert.assertNotNull("String returned from asynctask should not be null", joke);
        Assert.assertTrue("String returned from asynctask should not be empty",joke.trim().length() > 0);

    }
}
