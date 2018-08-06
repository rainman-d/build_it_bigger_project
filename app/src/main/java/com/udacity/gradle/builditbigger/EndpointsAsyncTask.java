package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by rainman-d on 8/5/18.
 *
 * Code adopted from template located at:
 * https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/77e9910911d5412e5efede5fa681ec105a0f02ad/HelloEndpoints#2-connecting-your-android-app-to-the-backend
 * Template code has been modified to allow it to be used by any Activity that implements EndpointTaskHandler interface.
 */

public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private EndpointTaskHandler mTaskOutputHandler;

    public interface EndpointTaskHandler{
        void handleTaskOutput(String output);
    }

    public EndpointsAsyncTask(){}

    public EndpointsAsyncTask(EndpointTaskHandler handler){
        this.mTaskOutputHandler = handler;
    }

    @Override
    protected String doInBackground(Void... param) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        mTaskOutputHandler.handleTaskOutput(result);
    }

    public EndpointsAsyncTask setListener(EndpointTaskHandler listener){
        this.mTaskOutputHandler = listener;
        return this;
    }
}
