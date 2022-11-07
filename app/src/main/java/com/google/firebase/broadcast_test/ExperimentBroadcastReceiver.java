package com.google.firebase.broadcast_test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class ExperimentBroadcastReceiver extends BroadcastReceiver {

  private final static String TAG = "FCM-BROADCAST";
  private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
  private PendingResult pendingBroadcastResult;

  @Override
  public void onReceive(Context context, Intent intent) {
    long receivedTime = System.currentTimeMillis();

    pendingBroadcastResult = goAsync();

    executorService.submit(()-> receive(intent, receivedTime));
  }

  public void receive(Intent intent, long receivedTime) {
    //do some work, simulate processing time.
    // try {
    //   Thread.sleep(20);
    // } catch (InterruptedException e) {
    //   e.printStackTrace();
    // }
    long t1 = SystemClock.currentThreadTimeMillis();
    for (int i=0; i<10000; i++) {
      System.out.println("simulate processing time");
    }
    long t2 = SystemClock.currentThreadTimeMillis();

    Bundle bundle = intent.getExtras();
    long sendTime = (long) bundle.get("time");
    int index = (int) bundle.get("index");
    long finishedTime = System.currentTimeMillis();
    if (index % 20 == 0) {
      // Log.d(TAG, index+ ": (receivedTime-sendTime): " + (receivedTime-sendTime) + " "
      //     + "(finishedTime-sendTime): " + (finishedTime-sendTime) +
      //     " (processing time):)" + (t2-t1));
      Log.d(TAG, index + ": " + "processing time: " + (t2-t1) + " " + "total delay%" + (finishedTime-sendTime));
    }
    pendingBroadcastResult.finish();A
  }
}
