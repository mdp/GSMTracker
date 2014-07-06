package im.mdp.gsmtracker;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "GSMTracker AlarmReceiver";

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        File sdCard = Environment.getExternalStorageDirectory();
        Log.d(TAG, "Mounted: " + Boolean.toString(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)));
        File dir = new File (sdCard.getAbsolutePath() + "/gsmlogger");
        dir.mkdirs();
        JSONLogger logger = JSONLogger.getLogger(dir.getPath() + "/gsm.log");
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        GsmCellLocation cellLocation = (GsmCellLocation) tm.getCellLocation();
        JSONObject jsonObject = new JSONObject();
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.US);
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        if (cellLocation != null) {
            try {
                jsonObject.put("created_at", nowAsISO);
                jsonObject.put("Lac", cellLocation.getLac());
                jsonObject.put("Cid", cellLocation.getCid());
                jsonObject.put("MCCMNC", tm.getNetworkOperator());
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, e.toString());
            }
            Log.d(TAG, jsonObject.toString());
            logger.log(jsonObject);
        } else {
            try {
                jsonObject.put("created_at", nowAsISO);
                jsonObject.put("error", "No cellular radio information");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, e.toString());
            }
            Log.d(TAG, jsonObject.toString());
            logger.log(jsonObject);

        }
    }
}

