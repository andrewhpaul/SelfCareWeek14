package com.KA2001.selfcare.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.KA2001.selfcare.R;
import com.KA2001.selfcare.db.SqlLite;

public class NotificationReciver extends BroadcastReceiver {
    SqlLite database;
    @Override
    public void onReceive(Context context, Intent intent) {
        database=new SqlLite( context );
        String name=intent.getStringExtra( "name" );
        String id=intent.getStringExtra( "id" );
        NotificationCompat.Builder builder=new NotificationCompat.Builder( context,"noti" )
                .setContentTitle(name)
                .setContentText(name+" is going to be expiring soon!")
                .setSmallIcon(R.drawable.logoclear);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from( context );
        notificationManagerCompat.notify( 200,builder.build() );
        database.deleteNoti( id);





    }
}
