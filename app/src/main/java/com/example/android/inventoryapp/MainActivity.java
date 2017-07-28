package com.example.android.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.ProductContract.ProductEntry;

import static com.example.android.inventoryapp.data.ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // Identifier for the product data loader
    private static final int PRODUCT_LOADER = 0;

    // Adapter for the ListView
    ProductCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup FAB to open DetailActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });

        // Find the ListView which will be populated with the product data
        ListView productListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        productListView.setEmptyView(emptyView);

        // Setup an Adapter to create a list item for each row of product data in the Cursor.
        mCursorAdapter = new ProductCursorAdapter(this, null);
        productListView.setAdapter(mCursorAdapter);

        // Setup the item click listener
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                // Form the content URI that represents the specific product that was clicked on
                Uri currentProductUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentProductUri);

                // Launch the DetailActivity to display the data for the current product
                startActivity(intent);
            }
        });

        // Kick off the loader
        getLoaderManager().initLoader(PRODUCT_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_PRICE,
                COLUMN_PRODUCT_QUANTITY };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,
                ProductEntry.CONTENT_URI,
                projection,
                null,                       // No selection clause
                null,                       // No selection arguments
                null);                      // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update PetCursorAdapter with this new cursor containing updated product data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}
