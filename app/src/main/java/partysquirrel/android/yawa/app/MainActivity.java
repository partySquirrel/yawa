package partysquirrel.android.yawa.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Timer timer = new Timer();
        ColorTimer mt = new ColorTimer(toolbar);

        //We schedule the timer task to run after 1000 ms and continue to run every 1000 ms.
        timer.schedule(mt, 10, 10);

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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        if (id == R.id.action_map) {
            openPreferredLocationInMap();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationInMap() {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String location = sharedPrefs.getString(
                getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));

        // Using the URI scheme for showing a location found on a map.  This super-handy
        // intent can is detailed in the "Common Intents" page of Android's developer site:
        // http://developer.android.com/guide/components/intents-common.html#Maps
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", location)
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        // open indent only if there is something handling it
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, "Couldn't open location " + location + ", no map apps installed!");
        }

    }


    class ColorTimer extends TimerTask {
        View rootView;

        int red = 255;
        int green = 0;
        int blue = 0;

        public ColorTimer(View rootView) {
            this.rootView = rootView;
        }

        public void run() {

            //This runs in a background thread.
            //We cannot call the UI from this thread, so we must call the main UI thread and pass a runnable
            runOnUiThread(new Runnable() {
                public void run() {
                    if (red > 0 && blue == 0) {
                        red--;
                        green++;
                    }
                    if (green > 0 && red == 0) {
                        green--;
                        blue++;
                    }
                    if (blue > 0 && green == 0) {
                        red++;
                        blue--;
                    }

                    //The first parameter in argb() is the alpha.
                    rootView.setBackgroundColor(Color.argb(255, red, green, blue));
                }
            });
        }
    }
}
