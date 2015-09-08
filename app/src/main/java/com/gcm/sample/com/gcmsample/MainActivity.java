package com.gcm.sample.com.gcmsample;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class MainActivity extends ActionBarActivity {

  Button register_button;
  String PROJECT_NUMBER = "108732723955 ";
  GoogleCloudMessaging gcmObj;
  String regId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    register_button = (Button) findViewById(R.id.register);
    register_button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        registerInBackground();
      }
    });
  }

  private void registerInBackground() {
    new AsyncTask<Void, Void, String>() {
      @Override
      protected String doInBackground(Void... params) {
        String msg = "";
        try {
          if (gcmObj == null) {
            gcmObj = GoogleCloudMessaging.getInstance(getApplicationContext());
          }
          regId = gcmObj.register(PROJECT_NUMBER);
          msg = "Registration ID :" + regId;

        } catch (IOException ex) {
          msg = ex.getMessage();
        }
        return msg;
      }

      @Override
      protected void onPostExecute(String msg) {
        if (!TextUtils.isEmpty(regId)) {
          Toast.makeText(getApplicationContext(),"Registered with GCM Server successfully.\n\n"+ msg, Toast.LENGTH_SHORT).show();
          Log.d("MainActivity", regId);
        } else {
          Toast.makeText(getApplicationContext(),"Reg ID Creation Failed."+ msg, Toast.LENGTH_LONG).show();
        }
      }
    }.execute(null, null, null);
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
}
