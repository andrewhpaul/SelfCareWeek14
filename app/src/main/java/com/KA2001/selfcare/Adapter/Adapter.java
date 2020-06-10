package com.KA2001.selfcare.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.KA2001.selfcare.Model.Model;
import com.KA2001.selfcare.R;
import com.KA2001.selfcare.db.SqlLite;
import com.KA2001.selfcare.view.AddProductActivity;
import com.KA2001.selfcare.view.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> implements Filterable {
    private Context context;
    private ArrayList<Model> arrayList;
    private ArrayList<Model> exampleArrayList;

    SqlLite databaseHelper;

    public Adapter(Context context, ArrayList<Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        exampleArrayList = new ArrayList<>(arrayList);

        databaseHelper = new SqlLite(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate( R.layout.row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {

        // get for views
        Model model = arrayList.get(position);
        final String id = model.getId();
        final String image = model.getImage();
        final String name = model.getName();
        final String purchaseDate = model.getPurchaseDate();
        final String expiryDate = model.getExpiryDate();
        final String url = model.getUrl();
        final String price = model.getPrice();
        final String location = model.getLocation();
        final String group = model.getGroup();
        final String tag = model.getTag();
        final String notes = model.getNotes();
        final String rating = model.getRating();

        // set data

        holder.name.setText(name);
        holder.purchaseDate.setText("Purchased: "+purchaseDate);
        holder.expiryDate.setText("Expiry: "+expiryDate);
        holder.url.setVisibility( View.GONE );
        holder.price.setVisibility( View.GONE );
        holder.location.setVisibility( View.GONE );
        holder.group.setVisibility( View.GONE );
        holder.tag.setText( tag );
        holder.notes.setVisibility( View.GONE );
        holder.rating.setVisibility( View.GONE );

        if (image.equals( "null" )) {
            holder.profileIv.setImageResource(R.drawable.ic_action_addphoto);
        }
        else {

            holder.profileIv.setImageURI(Uri.parse(image));
        }

        // handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // handle when click on more button
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog(
                        ""+position,
                        ""+id,
                        ""+name,
                        ""+purchaseDate,
                        ""+expiryDate,
                        ""+image,
                        ""+url,
                        ""+group,
                        ""+location,
                        ""+group,
                        ""+tag,
                        ""+notes,
                        ""+rating
                );
            }
        });

        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra( "tag","edit" );
                intent.putExtra("ID", id);
                intent.putExtra("NAME", name);
                intent.putExtra("PURCHASE_DATE", purchaseDate);
                intent.putExtra("EXPIRY_DATE", expiryDate);
                intent.putExtra("IMAGE", image);
                intent.putExtra("URL", url);
                intent.putExtra("PRICE", price);
                intent.putExtra("LOCATION", location);
                intent.putExtra("GROUP", group);
                intent.putExtra("TAG", tag);
                intent.putExtra("NOTES", notes);
                intent.putExtra("RATING", rating);
                intent.putExtra("editMode", true);
                context.startActivity(intent);
            }
        } );

    }

  /*  private void deleteDialog(final String id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_action_delete);
        builder.setCancelable(false);
        builder.setTitle("Delete");
        builder.setMessage("Are you want to delete?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHelper.deleteInfo(id);
                ((MainActivity)context).onResume();
                Toast.makeText(context, "Delete Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }*/

    private void editDialog(String position, final String id, final String name, final String purchaseDate, final String expiryDate, final String image, final String url, final String price, final String location, final String group, final String tag, final String notes, final String rating ) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_action_edit);
        builder.setCancelable(false);
        builder.setTitle("Edit");
        builder.setMessage("Are you want to edit?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(context, AddProductActivity.class);
                intent.putExtra( "tag","edit" );
                intent.putExtra("ID", id);
                intent.putExtra("NAME", name);
                intent.putExtra("PURCHASE_DATE", purchaseDate);
                intent.putExtra("EXPIRY_DATE", expiryDate);
                intent.putExtra("IMAGE", image);
                intent.putExtra("URL", url);
                intent.putExtra("PRICE", price);
                intent.putExtra("LOCATION", location);
                intent.putExtra("GROUP", group);
                intent.putExtra("TAG", tag);
                intent.putExtra("NOTES", notes);
                intent.putExtra("RATING", rating);
                intent.putExtra("editMode", true);
                context.startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView profileIv;
        TextView name, purchaseDate, expiryDate, url, price, location, group, tag, notes, rating;
        ImageButton editButton;

        public Holder(@NonNull View itemView) {
            super(itemView);

            // initialize views
            profileIv = itemView.findViewById(R.id.profileIv);
            name = itemView.findViewById(R.id.name);
            purchaseDate = itemView.findViewById(R.id.purchaseDate);
            expiryDate = itemView.findViewById(R.id.expiryDate);
            url = itemView.findViewById(R.id.url);
            price = itemView.findViewById(R.id.price);
            location = itemView.findViewById(R.id.location);
            group = itemView.findViewById(R.id.group);
            tag = itemView.findViewById(R.id.tag);
            notes = itemView.findViewById(R.id.notes);
            rating = itemView.findViewById(R.id.rating);
            // find id of more button
            editButton = itemView.findViewById(R.id.editBtn);
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Model> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleArrayList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Model item: exampleArrayList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList.clear();
            arrayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
