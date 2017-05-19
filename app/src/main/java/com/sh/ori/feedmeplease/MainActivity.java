package com.sh.ori.feedmeplease;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.app.NotificationCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    //aqua: 31.776640, 35.197886
    //dorms: 31.768740, 35.198462
    private FirebaseAuth mAuth;

    public Map picMap;
    ImageButton imBtFeedMe, imBtGotoEventFeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(getApplicationContext(),SigningActivity.class));
            finish();
        }

        imBtFeedMe = (ImageButton) findViewById(R.id.im_bt_goto_feed_me);
        imBtFeedMe.setOnClickListener(this);
        imBtGotoEventFeed = (ImageButton) findViewById(R.id.im_bt_open_event_feed);
        imBtGotoEventFeed.setOnClickListener(this);


//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.right_errow_blue_large)
//                        .setContentTitle("My notification")
//                        .setContentText("Hello World!");
//// Creates an explicit intent for an Activity in your app
//        Intent resultIntent = new Intent(this, ResultActivity.class);
//
//// The stack builder object will contain an artificial back stack for the
//// started Activity.
//// This ensures that navigating backward from the Activity leads out of
//// your application to the Home screen.
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//// Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(ResultActivity.class);
//// Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        mBuilder.setContentIntent(resultPendingIntent);
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//// mId allows you to update the notification later on.
//        mNotificationManager.notify(mId, mBuilder.build());



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.im_bt_goto_feed_me):
                Intent feedMeAct = new Intent(getApplicationContext(),FeedMeActivity.class);
                startActivity(feedMeAct);
                break;
            case(R.id.im_bt_open_event_feed):
                Intent eventFeedAct = new Intent(getApplicationContext(),EventFeedActivity.class);
                startActivity(eventFeedAct);
                break;
        }
    }

}
