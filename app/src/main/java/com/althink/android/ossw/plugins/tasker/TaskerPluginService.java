package com.althink.android.ossw.plugins.tasker;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.preference.PreferenceManager;

import net.dinglisch.android.tasker.TaskerIntent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by krzysiek on 08/06/15.
 */
public class TaskerPluginService extends Service {

    private final static String TAG = TaskerPluginService.class.getSimpleName();

    private final Messenger mMessenger = new Messenger(new OperationHandler());

    @Override
    public IBinder onBind(Intent intent) {
        //Log.d(TAG, "onBind");
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    private class OperationHandler extends Handler {

        public OperationHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (TaskerPluginFunction.resolveById(msg.what)) {
                case INVOKE_TASK: {
                    String taskName = msg.getData().getString("parameter");
                    if (TaskerIntent.testStatus(getApplicationContext()).equals(TaskerIntent.Status.OK)) {
                        TaskerIntent i = new TaskerIntent(taskName);
                        sendBroadcast(i);
                    }
                    break;
                }
                default:
                    // do nothing
                    return;
            }
        }
    }
}