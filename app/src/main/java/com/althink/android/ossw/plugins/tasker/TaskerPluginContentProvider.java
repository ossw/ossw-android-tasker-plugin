package com.althink.android.ossw.plugins.tasker;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by krzysiek on 10/06/15.
 */
public class TaskerPluginContentProvider extends ContentProvider {

    private final static String TAG = TaskerPluginContentProvider.class.getSimpleName();

    static final String AUTHORITY = "com.althink.android.ossw.plugins.tasker";
    static final String API_FUNCTIONS_PATH = "api/functions";
    static final String API_PROPERTIES_PATH = "api/properties";
    static final String PROVIDER_PROPERTIES = "properties";

    static final Uri PROPERTY_VALUES_URI = Uri.parse("content://" + AUTHORITY + "/" + PROVIDER_PROPERTIES);

    private static final int API_PROPERTIES = 1;
    private static final int API_FUNCTIONS = 2;
    private static final int PROPERTIES = 3;

    private static final UriMatcher uriMatcher;

    private Map<String, Object> values = new HashMap<>();

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, API_PROPERTIES_PATH, API_PROPERTIES);
        uriMatcher.addURI(AUTHORITY, API_FUNCTIONS_PATH, API_FUNCTIONS);
        uriMatcher.addURI(AUTHORITY, PROVIDER_PROPERTIES, PROPERTIES);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    static final String API_COLUMN_ID = "_id";
    static final String API_COLUMN_NAME = "name";
    static final String API_COLUMN_DESCRIPTION = "description";
    static final String API_COLUMN_TYPE = "type";

    private static final String[] API_PROPERTY_COLUMNS = new String[]{
            API_COLUMN_ID,
            API_COLUMN_NAME,
            API_COLUMN_DESCRIPTION,
            API_COLUMN_TYPE
    };

    private static final String[] API_FUNCTION_COLUMNS = new String[]{
            API_COLUMN_ID,
            API_COLUMN_NAME,
            API_COLUMN_DESCRIPTION
    };

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case API_PROPERTIES:
                MatrixCursor cursor = new MatrixCursor(API_PROPERTY_COLUMNS);
                return cursor;
            case API_FUNCTIONS:
                cursor = new MatrixCursor(API_FUNCTION_COLUMNS);
                addApiFunctionRow(cursor, TaskerPluginFunction.INVOKE_TASK, R.string.function_invokeTask);
                return cursor;
            case PROPERTIES:
                cursor = new MatrixCursor(projection);
                return cursor;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    private void addApiFunctionRow(MatrixCursor cursor, TaskerPluginFunction function, int descriptionId) {
        cursor.newRow().add(function.getId()).add(function.getName()).add(getString(descriptionId));
    }

    private String getString(int stringId) {
        return getContext().getResources().getString(stringId);
    }

    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case API_PROPERTIES:
                return "vnd.android.cursor.dir/vnd.com.althink.android.ossw.plugin.api.properties";
            case API_FUNCTIONS:
                return "vnd.android.cursor.dir/vnd.com.althink.android.ossw.plugin.api.functions";
            case PROPERTIES:
                return "vnd.android.cursor.item/vnd.com.althink.android.ossw.plugin.properties";
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }
}
