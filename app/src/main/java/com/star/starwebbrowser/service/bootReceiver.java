package com.star.starwebbrowser.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.star.starwebbrowser.activity.MainActivity;

public class bootReceiver extends BroadcastReceiver {

    public bootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {//开机启动
            Intent bootActivityIntent = new Intent(context, MainActivity.class);
            bootActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(bootActivityIntent);
        }
    }

}
