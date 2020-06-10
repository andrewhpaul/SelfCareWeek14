package com.KA2001.selfcare.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.KA2001.selfcare.MainActivity;
import com.KA2001.selfcare.R;
import com.KA2001.selfcare.db.Constants;
import com.KA2001.selfcare.db.SqlLite;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

public class AddProductActivity extends AppCompatActivity {

    Button saveInfoBt, edit, delete;
    Uri data1;
    Bitmap selectedImage;
    String TAG;
    private ImageView pImageView;


    private static EditText pNameEt, pPurchaseDateEt, pExpiryDateEt, pUrlEt, pPriceEt, pLocationEt, pGroupEt, pTagEt, pNotesEt ;
   RatingBar pRatingEt;
    private String name, purchaseDate, expiryDate, url, price, location, group, tag, notes, rating, id;
    private Uri imageUri;
    private SqlLite dbHelper;
    public static int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_product );
        dbHelper = new SqlLite( this );
        pImageView = findViewById( R.id.personImage );
        pImageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage( AddProductActivity.this );
            }
        } );
        pNameEt = findViewById( R.id.productName );
        pPurchaseDateEt = findViewById( R.id.productPurchaseDate );
        pPurchaseDateEt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=0;
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        } );
        pExpiryDateEt = findViewById( R.id.productExpiryDate );
        pExpiryDateEt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=1;
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        } );
        pUrlEt = findViewById( R.id.productURL );
        pPriceEt = findViewById( R.id.productPrice );
        pLocationEt = findViewById( R.id.productLocation );
        pTagEt = findViewById( R.id.productTag );
        pNotesEt = findViewById( R.id.productNotes );
        pRatingEt = findViewById( R.id.productRatings );
        pRatingEt.setRating( 5 );
        saveInfoBt = findViewById( R.id.addInfoBtn );
        edit = findViewById( R.id.edit );
        delete = findViewById( R.id.delete );
        saveInfoBt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();

            }
        } );
        TAG = getIntent().getStringExtra( "tag" );
        if (TAG.equals( "add" )) {
            saveInfoBt.setVisibility( View.VISIBLE );
        } else if (TAG.equals( "edit" )) {
            edit.setVisibility( View.VISIBLE );
            delete.setVisibility( View.VISIBLE );
            Intent intent = getIntent();
            id = intent.getStringExtra( "ID" );
            name = intent.getStringExtra( "NAME" );
            purchaseDate = intent.getStringExtra( "PURCHASE_DATE" );
            expiryDate = intent.getStringExtra( "EXPIRY_DATE" );
            imageUri = Uri.parse( intent.getStringExtra( "IMAGE" ) );
            url = intent.getStringExtra( "URL" );
            price = intent.getStringExtra( "PRICE" );
            location = intent.getStringExtra( "LOCATION" );
            group = intent.getStringExtra( "GROUP" );
            tag = intent.getStringExtra( "TAG" );
            notes = intent.getStringExtra( "NOTES" );
            rating = intent.getStringExtra( "RATING" );
            pNameEt.setText( name );
            pPurchaseDateEt.setText( purchaseDate );
            pExpiryDateEt.setText( expiryDate );
            pUrlEt.setText( "" + url );
            pPriceEt.setText( "" + price );
            pLocationEt.setText( "" + location );
            pGroupEt.setText( "" + group );
            pTagEt.setText( "" + tag );
            pNotesEt.setText( "" + notes );
           // pRatingEt.setText( "" + rating );
            if(rating.equals( "1" )){
                pRatingEt.setRating( 1 );
            }else if(rating.equals( "2" )){
                pRatingEt.setRating( 2 );
            }else if(rating.equals( "3" )){
                pRatingEt.setRating( 3 );
            }else if(rating.equals( "4" )){
                pRatingEt.setRating( 4 );
            }else if(rating.equals( "5" )){
                pRatingEt.setRating( 5 );
            }else {
                pRatingEt.setRating( 5 );
            }

            if (imageUri.toString().equals( "null" )) {
                pImageView.setImageResource( R.drawable.ic_action_addphoto );
            } else {
                pImageView.setImageURI( imageUri );
            }
        }
        edit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editData();
            }
        } );
        delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteInfo( id );
                startActivity( new Intent( AddProductActivity.this, MainActivity.class ) );
                finish();

            }
        } );
    }


    private void editData() {
        name = "" + pNameEt.getText().toString().trim();
        purchaseDate = "" + pPurchaseDateEt.getText().toString().trim();
        expiryDate = "" + pExpiryDateEt.getText().toString().trim();
        url = "" + pUrlEt.getText().toString().trim();
        price = "" + pPriceEt.getText().toString().trim();
        location = "" + pLocationEt.getText().toString().trim();
        price = "" + pPriceEt.getText().toString().trim();
        group = "" + pGroupEt.getText().toString().trim();
        tag = "" + pTagEt.getText().toString().trim();
        notes = "" + pNotesEt.getText().toString().trim();
        rating = "" + /*pRatingEt.getText().toString().trim();*/ pRatingEt.getRating();

        if (!name.isEmpty() && !purchaseDate.isEmpty() && !expiryDate.isEmpty()) {
            dbHelper.updateInfo(
                    "" + id,
                    "" + data1,
                    "" + name,
                    "" + purchaseDate,
                    "" + expiryDate,
                    "" + url,
                    "" + price,
                    "" + location,
                    "" + group,
                    "" + tag,
                    "" + notes,
                    "" + rating
            );
            startActivity( new Intent( AddProductActivity.this, MainActivity.class ) );
            finish();
        } else {
            Toast.makeText( this, "Something Missing", Toast.LENGTH_SHORT ).show();
        }

    }

    private void saveData() {
        name = "" + pNameEt.getText().toString().trim();
        purchaseDate = "" + pPurchaseDateEt.getText().toString().trim();
        expiryDate = "" + pExpiryDateEt.getText().toString().trim();
        url = "" + pUrlEt.getText().toString().trim();
        price = "" + pPriceEt.getText().toString().trim();
        location = "" + pLocationEt.getText().toString().trim();
        price = "" + pPriceEt.getText().toString().trim();
        group = "" + pGroupEt.getText().toString().trim();
        tag = "" + pTagEt.getText().toString().trim();
        notes = "" + pNotesEt.getText().toString().trim();
        rating = "" +pRatingEt.getRating();

        if (!name.isEmpty() && !purchaseDate.isEmpty() && !expiryDate.isEmpty()) {
            long aa = dbHelper.insertInfo(
                    "" + data1,
                    "" + name,
                    "" + purchaseDate,
                    "" + expiryDate,
                    "" + url,
                    "" + price,
                    "" + location,
                    "" + group,
                    "" + tag,
                    "" + notes,
                    "" + rating
            );

            startActivity( new Intent( AddProductActivity.this, MainActivity.class ) );
            finish();
        } else {
            Toast.makeText( this, "Something Missing", Toast.LENGTH_SHORT ).show();
        }


    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder( context );
        builder.setTitle( "Choose your profile picture" );

        builder.setItems( options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals( "Take Photo" )) {
                    Intent takePicture = new Intent( android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
                    startActivityForResult( takePicture, 0 );

                } else if (options[item].equals( "Choose from Gallery" )) {
                    Intent pickPhoto = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                    startActivityForResult( pickPhoto, 1 );

                } else if (options[item].equals( "Cancel" )) {
                    dialog.dismiss();
                }
            }
        } );
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {

                        selectedImage = (Bitmap) data.getExtras().get( "data" );
                        data1 = getImageUri( getApplicationContext(), selectedImage );

                        pImageView.setImageBitmap( selectedImage );
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        selectedImage.compress( Bitmap.CompressFormat.PNG, 100, stream );
                        byte[] byteArray = stream.toByteArray();
                        Log.i( "urrii", String.valueOf( byteArray ) );


                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        data1 = data.getData();

                        try {
                            selectedImage = MediaStore.Images.Media.getBitmap( getContentResolver(), data1 );
                            pImageView.setImageBitmap( selectedImage );

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                    break;
            }
        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress( Bitmap.CompressFormat.JPEG, 100, bytes );
        String path = MediaStore.Images.Media.insertImage( inContext.getContentResolver(), inImage, "Title", null );
        return Uri.parse( path );
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {



        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            //dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            return  dialog;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

            if(i==0){
                Constants.DAY = day;
                pPurchaseDateEt.setText(currentDateString);

            }else {
                    pExpiryDateEt.setText(currentDateString);
                }

            }
    }
}
