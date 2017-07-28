package com.example.android.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Maks on 27/07/2017.
 */

public class ProductContract {
    // To prevent someone from accidentally instantiating
    private ProductContract() {}
    // Content Authority
    public static final String CONTENT_AUTHORITY = "com.example.android.inventoryapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PRODUCTS = "products";

    // Products database table definition
    public static final class ProductEntry implements BaseColumns{
        // The content URI to access the product data in the provider
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);

        // The MIME type of the Content URI for a list of products
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        // The MIME type of the Content URI for a single product
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        // Name of the database table for products
        public final static String TABLE_NAME = "products";

        // Columns
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PRODUCT_NAME = "name";
        public final static String COLUMN_PRODUCT_PRICE = "price";
        public final static String COLUMN_PRODUCT_QUANTITY = "quantity";

    }
}
