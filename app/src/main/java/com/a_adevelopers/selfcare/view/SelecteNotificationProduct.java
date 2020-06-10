package com.a_adevelopers.selfcare.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.a_adevelopers.selfcare.Adapter.Adapter;
import com.a_adevelopers.selfcare.Adapter.SelectAdapterNotification;
import com.a_adevelopers.selfcare.MainActivity;
import com.a_adevelopers.selfcare.R;
import com.a_adevelopers.selfcare.db.SqlLite;


public class SelecteNotificationProduct extends AppCompatActivity {
    RecyclerView mRecyclerView;
    SelectAdapterNotification adapter;
    private SqlLite databaseHelper;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_selecte_notification_product );

        back=findViewById( R.id.back );
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        } );

        mRecyclerView = findViewById(R.id.recycler);
        databaseHelper = new SqlLite(this);
        showRecord();
    }

    @Override
    public void onBackPressed() {
        startActivity( new Intent( SelecteNotificationProduct.this,NotificationActivity.class ) );
        finish();

    }

    private void showRecord() {

        adapter = new SelectAdapterNotification( SelecteNotificationProduct.this,databaseHelper.getAllData());

        mRecyclerView.setAdapter(adapter);
    }
}
