package com.gcm.sample.com.gcmsample;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by shrikanth on 7/9/15.
 */
public class GCMIntentService extends IntentService {


  /**
   * Creates an IntentService.  Invoked by your subclass's constructor.
   *
   */
  public GCMIntentService() {
    super("Message");

  }
  @Override
  protected void onHandleIntent(Intent intent) {
    Bundle extras = intent.getExtras();
    String message = intent.getStringExtra("key1");
    GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
    String messageType = gcm.getMessageType(intent);
    final int notificationID = (int) (Math.random() * 100000000);

    if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
      sendNotification("GCM notification: Send error" + extras.toString(), notificationID);
    } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
      sendNotification("Deleted messages on server" + extras.toString(), notificationID);
    } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
      sendNotification(message, notificationID);
    }
    GCMReceiver.completeWakefulIntent(intent);
  }

  private void sendNotification(String msg, int notificationID) {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
    builder.setSmallIcon(R.drawable.common_ic_googleplayservices);
    builder.setContentTitle("Notification");
    builder.setContentText(msg);
    Intent resultIntent = new Intent(this, MainActivity.class);
    PendingIntent resultPendingIntent = PendingIntent.getActivity(this,0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    mNotifyMgr.notify(notificationID, builder.build());
  }
}

