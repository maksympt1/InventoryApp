package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.inventoryapp.data.ProductContract.ProductEntry;

/**
 * Created by Maks on 27/07/2017.
 */

public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.list_item_name);
        TextView priceTextView = (TextView) view.findViewById(R.id.list_item_price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.list_item_quantity);
        Button saleBtn = (Button) view.findViewById(R.id.list_item_sale_button);


        // Find the columns of product attributes that we're interested in
        int idColumnIndex = cursor.getColumnIndex(ProductEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);


        // Read the product attributes from the Cursor for the current product
        String productName = cursor.getString(nameColumnIndex);
        // getInt instead of getString
        int productPrice = cursor.getInt(priceColumnIndex);
        final int productQuantity = cursor.getInt(quantityColumnIndex);
        final int itemId = cursor.getInt(idColumnIndex);


        // Update the TextViews with the attributes for the current product
        nameTextView.setText(productName);
        priceTextView.setText(Integer.toString(productPrice));
        quantityTextView.setText(Integer.toString(productQuantity));

        saleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productQuantity > 0) {
                    int tempQuantity = productQuantity;
                    tempQuantity--;

                    ContentValues values = new ContentValues();
                    values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, tempQuantity);
                    Uri currentItemUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, itemId);

                    // create the Uri of the current product
                    context.getContentResolver().update(currentItemUri, values, null, null);
                }
            }
        });
    }
}
