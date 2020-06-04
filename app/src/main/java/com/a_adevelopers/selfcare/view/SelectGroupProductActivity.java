package com.a_adevelopers.selfcare.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.a_adevelopers.selfcare.Adapter.ProductGroupAdatpter;
import com.a_adevelopers.selfcare.Adapter.SelectAdapterNotification;
import com.a_adevelopers.selfcare.R;
import com.a_adevelopers.selfcare.db.SqlLite;

public class SelectGroupProductActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ProductGroupAdatpter adapter;
    private SqlLite databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_selecte_group_product );
        mRecyclerView = findViewById(R.id.recycler);
        databaseHelper = new SqlLite(this);
        showRecord();
    }
    private void showRecord() {

        adapter = new ProductGroupAdatpter( SelectGroupProductActivity.this,databaseHelper.getAllData());

        mRecyclerView.setAdapter(adapter);
    }
}
