package im.mdp.gsmtracker;

import android.util.Log;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by mdp on 7/3/14.
 */

public class JSONLogger {
    private static JSONLogger sJSONLogger;
    private final String mPath;
    private PrintWriter mWriter;

    public static JSONLogger getLogger(String path){
        if (sJSONLogger != null) {
            return sJSONLogger;
        }
        return sJSONLogger = new JSONLogger(path);
    }


    public JSONLogger(String path) {
        mPath = path;
        Log.d("GSMLogger", "Writing to " + path);
    }

    public void log(JSONObject jsonObject) {
        try {
            mWriter = new PrintWriter(new FileWriter(mPath, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("GSMLogger", "Writing " + jsonObject.toString());
        mWriter.println(jsonObject.toString());
        mWriter.flush();
        mWriter.close();
    }
}

