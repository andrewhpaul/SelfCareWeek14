package com.a_adevelopers.selfcare.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.a_adevelopers.selfcare.Adapter.AdapterNotification;
import com.a_adevelopers.selfcare.Adapter.GroupProductAdapter;
import com.a_adevelopers.selfcare.Model.GPModel;
import com.a_adevelopers.selfcare.Model.GroupModel;
import com.a_adevelopers.selfcare.R;
import com.a_adevelopers.selfcare.db.Constants;
import com.a_adevelopers.selfcare.db.SqlLite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProductsGroupActivity extends AppCompatActivity {

    ListView list;
    FloatingActionButton add;
    String id;
    SqlLite database;
    ArrayList<GPModel> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    GroupProductAdapter adapter;
    SqlLite sql;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_products_group );

        back=findViewById( R.id.back );
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        } );

        database=new SqlLite( this );
        if(getIntent().getStringExtra( "ID" ).equals( "g_id" )){
            Toast.makeText( this, "popofdsf", Toast.LENGTH_SHORT ).show();
            id= String.valueOf( Constants.G_ID );
        }else {
            id=getIntent().getStringExtra( "ID" );
        }

        recyclerView=findViewById( R.id.recycler );
        sql=new SqlLite( this );
        add=findViewById( R.id.add );
        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialogBox( id );
                Constants.G_ID=Integer.parseInt( id );
                startActivity( new Intent( ProductsGroupActivity.this,SelectGroupProductActivity.class ) );
                finish();
            }
        } );


       String aa=getIntent().getStringExtra( "tag" );

        if("edit".equals( aa )){

            sql.insertgp( getIntent().getStringExtra( "NAME" ), String.valueOf( Constants.G_ID ),getIntent().getStringExtra( "IMAGE" ) );
            showRecord(Integer.parseInt( id ));

        }else {
            showRecord(Integer.parseInt( id ));

        }
    }

    private void showRecord(int id) {


        adapter = new GroupProductAdapter( ProductsGroupActivity.this,
                sql.getAllGP(id));


        recyclerView.setAdapter(adapter);
    }




    private void showDialogBox(final String id) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);

        final EditText text =  dialog.findViewById(R.id.editText2);
        Button dialogButton = (Button) dialog.findViewById(R.id.add);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               dialog.dismiss();
            }
        });

        dialog.show();
    }

   /* public void getAllProducts(int id) {



        // query for select info
        String selectQuery = "SELECT * FROM p_groups WHERE g_id="+id;

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            do {
               String name=cursor.getString(cursor.getColumnIndex("name"));
                arrayList.add(name);
            }while (cursor.moveToNext());
        }

        db.close();
       setListview(arrayList);
    }*/

    private void setListview(ArrayList<String> arrayList) {


    }


    public long insertInfo(String name, String id ) {

        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("g_id", id);

        long iid = db.insert("p_groups", null, values);
        db.close();
       // getAllProducts( Integer.parseInt( id ) );
        return iid;
    }
}
