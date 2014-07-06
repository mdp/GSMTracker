package im.mdp.gsmtracker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.BoringLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class MainFragment extends Fragment {

        private static final String TAG = "GSMTracker:MainFragmentActivity";

        public MainFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            final Button btn = (Button) rootView.findViewById(R.id.button);
            final TextView statusText = (TextView) rootView.findViewById(R.id.status_text);
            if (getAlarmStatus()) {
                btn.setText("Disarm");
                statusText.setText("Status: Recording");
            } else {
                btn.setText("Arm");
                statusText.setText("Status: Not Recording!");
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(toggleAlarm()) {
                        btn.setText("Disarm");
                        statusText.setText("Status: Recording");
                    } else {
                        btn.setText("Arm");
                        statusText.setText("Status: Not Recording!");
                    }
                }
            });
            return rootView;
        }

        protected boolean getAlarmStatus() {
            Intent intent = new Intent(getActivity(), AlarmReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_NO_CREATE);
            return (pi != null);
        }

        protected boolean toggleAlarm(){
            Context context = getActivity();
            AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmReceiver.class);
            PendingIntent alarmIntent;
            if (!getAlarmStatus()) {
                Log.d(TAG, "Alarm on");
                alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
                alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        0,
                        AlarmManager.INTERVAL_HOUR, alarmIntent);
                return true;
            } else {
                alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
                alarmMgr.cancel(alarmIntent);
                alarmIntent.cancel();
                Log.d(TAG, "Alarm off");
                return false;
            }
        }

    }
}
