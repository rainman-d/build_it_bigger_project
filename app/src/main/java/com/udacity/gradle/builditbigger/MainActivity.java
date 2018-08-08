package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.drainey.jokelib.JokeUtils;
import com.drainey.lib.JokeActivity;
import com.udacity.gradle.builditbigger.com.udacity.gradle.builditbigger.resource.SimpleIdlingResource;


public class MainActivity extends AppCompatActivity implements EndpointsAsyncTask.EndpointTaskHandler {
    @Nullable
    private SimpleIdlingResource mIdlingResource;
    private String returnedJoke;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public void tellJoke(View view) {
        String joke = this.getLibraryJoke();
        Toast.makeText(this, joke, Toast.LENGTH_LONG).show();
    }

    public void launchJokeActivity(View view){
        new EndpointsAsyncTask(this).execute();
    }

    private void startJokeActivity(String joke){
        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, joke);
        startActivity(intent);
    }

    private String getLibraryJoke(){
        return JokeUtils.getJoke();
    }

    public void handleTaskOutput(String joke){
        returnedJoke = joke;
        if(joke != null && !joke.isEmpty()){
            startJokeActivity(joke);
        }
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    public String getReturnedJoke(){
        return returnedJoke;
    }

}
