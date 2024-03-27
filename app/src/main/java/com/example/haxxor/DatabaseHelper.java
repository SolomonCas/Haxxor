package com.example.haxxor;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.haxxor.AbilityListView.Ability;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static Context context;
    private static final String DATABASE_NAME = "AbilitiesDB";
    private static final int DATABASE_VERSION = 7;
    private static final String TABLE_ABILITIES = "Abilities";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EFFECT = "effect";
    private static final String COLUMN_IMAGE = "image";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpgrade(db, 0, DATABASE_VERSION);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 7) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ABILITIES); // Easier to Update CSV
            String CREATE_ABILITIES_TABLE = "CREATE TABLE " + TABLE_ABILITIES + "("
                    + COLUMN_TYPE + " TEXT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_EFFECT + " TEXT,"
                    + COLUMN_IMAGE + " TEXT" + ")";
            db.execSQL(CREATE_ABILITIES_TABLE);
        }
    }

    public List<List<String>> readCSV (Context context, String csvFilePath) throws IOException {
        List<List<String>> data = new ArrayList<>();
        BufferedReader reader = null;
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(csvFilePath);
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                List<String> row = Arrays.asList(line.split(",")); // Split by comma (adjust delimiter if needed)
                data.add(row);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            // Handle file not found exception
        }
        return data;
    }
    public void importAbilities (List<List<String>> data){
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + TABLE_ABILITIES;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount == 0) {
            ContentValues values = new ContentValues();
            for (List<String> row : data) {
                // Skip the header row if it exists
                if (data.indexOf(row) == 0) {
                    continue;
                }

                // Map CSV data to table columns (assuming same order)
                values.put(COLUMN_TYPE, row.get(0));
                values.put(COLUMN_NAME, row.get(1));
                values.put(COLUMN_EFFECT, row.get(2));
                values.put(COLUMN_IMAGE, row.get(3));
                // ... (add more mappings for other columns)

                db.insert(TABLE_ABILITIES, null, values);
                values.clear(); // Clear for the next row
            }
        }
    }

    public void setupTable(){
        try {
            importAbilities(readCSV(context, "Haxxor_Abilities.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Ability> getAbilities (String type){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Ability> list = new ArrayList<>();
        String[] columns = new String[]{COLUMN_TYPE , COLUMN_NAME , COLUMN_EFFECT, COLUMN_IMAGE};
        String selection = COLUMN_TYPE + " =?";
        String[] selectionArgs = {type};
        Cursor cursor = db.query(TABLE_ABILITIES, columns, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()){
            list.add(new Ability(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            Log.d("TAG", "Retrieved string: from Cursor " + cursor.getString(3));
        }
        return list;
    }

    public ArrayList<Ability> getAllAbilities (){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Ability> list = new ArrayList<>();
        String[] columns = new String[]{COLUMN_TYPE , COLUMN_NAME , COLUMN_EFFECT, COLUMN_IMAGE};
        Cursor cursor = db.query(TABLE_ABILITIES, columns, null, null, null, null, null);
        while (cursor.moveToNext()){
            list.add(new Ability(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            Log.d("TAG", "Retrieved string: from Cursor " + cursor.getString(3));
        }
        return list;
    }
}
