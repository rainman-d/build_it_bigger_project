package com.udacity.gradle.builditbigger.com.udacity.gradle.builditbigger.resource;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by rainman-d on 8/7/18.
 * Adopted from https://github.com/googlesamples/android-testing/tree/master/ui/espresso/IdlingResourceSample
 */

public class SimpleIdlingResource implements IdlingResource {
    @Nullable
    private volatile ResourceCallback mCallback;

    private AtomicBoolean bIsIdleNow = new AtomicBoolean(true);
    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return this.bIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.mCallback = callback;
    }

    public void setIsIdleNow(boolean isIdle){
        bIsIdleNow.set((isIdle));
        if(isIdle && mCallback != null){
            mCallback.onTransitionToIdle();
        }
    }
}
