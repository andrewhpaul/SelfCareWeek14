package com.KA2001.selfcare.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.KA2001.selfcare.Controller.NotificationReciver;
import com.KA2001.selfcare.R;
import com.KA2001.selfcare.db.SqlLite;

import java.text.DateFormat;
import java.util.Calendar;

public class AddExpiryNotificationActivity extends AppCompatActivity {

    TextView name,expDate;
    Button addNoti;
    private DatePicker picker;
    Calendar c;
    String _name,_expDate,_image,id;
    SqlLite sqlLite;
    SQLiteDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_expiry_notification );
        createNotification();
        sqlLite=new SqlLite( this );
        database=sqlLite.getWritableDatabase();
        addNoti=findViewById( R.id.addNoti );
        picker = (DatePicker) findViewById(R.id.scheduleTimePicker);
        name=findViewById( R.id.name );
        expDate=findViewById( R.id.expiryDate );
        _name=getIntent().getStringExtra( "NAME" );
        _expDate=getIntent().getStringExtra( "EXPIRY_DATE" );
        _image=getIntent().getStringExtra( "IMAGE" );
        id=getIntent().getStringExtra( "ID" );
        name.setText( _name );
        expDate.setText( _expDate );

        addNoti.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateSelectedButtonClick();


              //  Toast.makeText( AddExpiryNotificationActivity.this, "Notification Set", Toast.LENGTH_SHORT ).show();


            }
        } );
    }
    public void onDateSelectedButtonClick(){

        int day = picker.getDayOfMonth();
        int month = picker.getMonth();
        int year = picker.getYear();

       c = Calendar.getInstance();
        c.set(year, month, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set( Calendar.SECOND, 0);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        Intent intent=new Intent( AddExpiryNotificationActivity.this, NotificationReciver.class );
        intent.putExtra( "name",_name );
        intent.putExtra( "id",id );
        PendingIntent pendingIntent=PendingIntent.getBroadcast( AddExpiryNotificationActivity.this,0,intent,0 );
        long timesec=System.currentTimeMillis();
        long ten=1000*10;
        AlarmManager alarmManager= (AlarmManager) getSystemService( ALARM_SERVICE );
        alarmManager.set( AlarmManager.RTC_WAKEUP,/*timesec+ten,pendingIntent*/  c.getTimeInMillis(),pendingIntent );
        Log.i( "qq",String.valueOf(  c.getTimeInMillis() ));
        insertDate(_image,_name,_expDate,currentDateString);
    }


    private void insertDate(String image, String name, String expDate, String currentDateString) {
        long aa=sqlLite.insertInfo( image,name,expDate,currentDateString );


        startActivity( new Intent( AddExpiryNotificationActivity.this, NotificationActivity.class ) );
        finish();
    }

    private void createNotification(){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int imp= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel=new NotificationChannel( "noti" ,"aaa",imp);
            channel.setDescription( "ddd" );
            NotificationManager notificationManager=getSystemService( NotificationManager.class );
            notificationManager.createNotificationChannel( channel );
        }

    }

}
