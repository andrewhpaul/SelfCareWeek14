package com.KA2001.selfcare.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.KA2001.selfcare.R;

public class ProductDetailsActivity extends AppCompatActivity {
    private ImageView pImageView;
    private TextView pNameEt, pPurchaseDateEt, pExpiryDateEt, pUrlEt, pPriceEt, pLocationEt, pGroupEt, pTagEt, pNotesEt, pRatingEt;

    Uri imageUri;
    ImageView back;

    private String name, purchaseDate, expiryDate, url, price, location, group, tag, notes, rating,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_product_details );

        back=findViewById( R.id.back );
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        } );

        pImageView = findViewById(R.id.pImageView);
        pNameEt = findViewById(R.id.name);
        pPurchaseDateEt = findViewById(R.id.date);
        pExpiryDateEt = findViewById(R.id.exp);
        pUrlEt = findViewById(R.id.url);
        pPriceEt = findViewById(R.id.price);
       // pLocationEt = findViewById(R.id.productLocation);
        pGroupEt = findViewById(R.id.group);
        pTagEt = findViewById(R.id.tags);
        pNotesEt = findViewById(R.id.notes);
        pRatingEt = findViewById(R.id.rating);
        Intent intent=getIntent();
        id = intent.getStringExtra("ID");
        name = intent.getStringExtra("NAME");
        purchaseDate = intent.getStringExtra("PURCHASE_DATE");
        expiryDate = intent.getStringExtra("EXPIRY_DATE");
        imageUri = Uri.parse(intent.getStringExtra("IMAGE"));
        url = intent.getStringExtra("URL");
        price = intent.getStringExtra("PRICE");
        location = intent.getStringExtra("LOCATION");
        group = intent.getStringExtra("GROUP");
        tag = intent.getStringExtra("TAG");
        notes = intent.getStringExtra("NOTES");
        rating = intent.getStringExtra("RATING");
        pNameEt.setText("Name: "+name);
        pPurchaseDateEt.setText("Purchase Date: "+purchaseDate);
        pExpiryDateEt.setText("Expiry Date: "+expiryDate);
        pUrlEt.setText( "URL: "+url );
        pPriceEt.setText( "Price: $"+price );
      //  pLocationEt.setText( "Location: "+location );
        pGroupEt.setText( "Location: "+group );
        pTagEt.setText( "Tags: "+tag );
        pNotesEt.setText( "Notes: "+notes );
        pRatingEt.setText( "Rating: "+rating +"/5.0" );
        if (imageUri.toString().equals("null")) {
            pImageView.setImageResource(R.drawable.ic_action_addphoto);
        }
        else {
            pImageView.setImageURI(imageUri);
        }
    }
}
