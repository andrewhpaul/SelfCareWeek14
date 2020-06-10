package com.KA2001.selfcare.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.KA2001.selfcare.Adapter.GroupAdapter;
import com.KA2001.selfcare.Model.GroupModel;
import com.KA2001.selfcare.R;
import com.KA2001.selfcare.db.SqlLite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GroupsActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    RecyclerView mRecyclerView;
    Spinner day;
    String _day;
    GroupAdapter adapter;
    ImageView back;
    private SqlLite databaseHelper;
    ArrayList<GroupModel> groupAdapters=new ArrayList<>(  );
    ArrayList<GroupModel> searchList=new ArrayList<>(  );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_groups );

        day=findViewById( R.id.day );
        back=findViewById( R.id.back );
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             onBackPressed();
            }
        } );
        _day=day.getSelectedItem().toString();
        day.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                _day=day.getSelectedItem().toString();
                search(_day);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );


        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( GroupsActivity.this,CreateGroupActivity.class );
                intent.putExtra( "tag","add" );
                startActivity( intent );
            }
        } );


        mRecyclerView = findViewById(R.id.recycler);
        databaseHelper = new SqlLite(this);
        showRecord();
    }

    private void search(String day) {

       searchList.clear();
       boolean TAG=true;
        groupAdapters=databaseHelper.getAllGroups();
        for(int i=0;i<groupAdapters.size();i++){
            GroupModel groupModel=groupAdapters.get( i );
            if(groupModel.getDay().equals( "Date Not Set" )){

               TAG=true;
            }else if(groupModel.getDay().equals( day )){

                searchList.add( groupModel );
                TAG=false;

            }
        }
       if(TAG){
           showRecord();
       }else {
           adapter = new GroupAdapter(GroupsActivity.this, searchList );
           mRecyclerView.setAdapter(adapter);

       }




    }


    private void showRecord() {
        groupAdapters=databaseHelper.getAllGroups();
        adapter = new GroupAdapter(GroupsActivity.this, databaseHelper.getAllGroups() );
        mRecyclerView.setAdapter(adapter);

    }


}
