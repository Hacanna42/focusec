package nna.ca.ha.focusec.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class ScreenBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            try {
                SharedPreferences pref = context.getSharedPreferences("focusec", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =pref.edit();
                Date date = new Date();
                Long ontime = date.getTime();
                Long offtime = pref.getLong("offtime", ontime);
                long diff = ontime - offtime;
                long sec = diff / 1000;
                long minutes = sec/60;
                long hours = minutes/60;

                if (hours > 0) {
                    sec = sec%60;
                    minutes = minutes%60;
                    Toast.makeText(context, hours+"시간"+minutes+"분"+sec+"초집중함", Toast.LENGTH_LONG).show();
                }  else if (minutes > 0) {
                    sec = sec%60;
                    Toast.makeText(context, minutes+"분"+sec+"초집중함", Toast.LENGTH_LONG).show();
                } else if (minutes == 0 && hours == 0) {
                    if (sec!=0)
                        Toast.makeText(context, sec + "초집중함", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(context, "안 집중함", Toast.LENGTH_LONG).show();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            try {
                Date date = new Date();
                Long offtime = date.getTime();
                SharedPreferences pref = context.getSharedPreferences("focusec", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =pref.edit();
                editor.putLong("offtime", offtime);
                editor.apply();

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

    }
}