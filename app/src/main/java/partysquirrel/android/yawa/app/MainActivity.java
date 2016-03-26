package partysquirrel.android.yawa.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


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

        return super.onOptionsItemSelected(item);
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
