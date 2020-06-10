package com.KA2001.selfcare.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.KA2001.selfcare.Adapter.ProductGroupAdatpter;
import com.KA2001.selfcare.R;
import com.KA2001.selfcare.db.SqlLite;

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
