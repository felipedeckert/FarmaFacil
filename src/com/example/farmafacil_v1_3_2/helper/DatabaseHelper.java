package com.example.farmafacil_v1_3_2.helper;

import java.util.ArrayList;
import java.util.List;

import com.example.farmafacil_v1_3_2.model.Receita;
import com.example.farmafacil_v1_3_2.model.Remedio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	// Logcat tag
    private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "FarmaManager";
 
    // Table Names
    private static final String TABLE_REMEDIO = "remedio";
    private static final String TABLE_RECEITA = "receita";
    private static final String TABLE_RECEITA_REMEDIO = "receita_remedio";
    
    // Common column names
    private static final String KEY_ID = "id";
     
    // REMEDIO Table - column names
    private static final String KEY_NOME = "nome";
    private static final String KEY_DOSAGEM = "dosagem";
    private static final String KEY_USO = "uso";
    
    // RECEITA Table - column names
    private static final String KEY_DATA = "data";
    private static final String KEY_NOMEMEDICO = "nomeMedico";
    private static final String KEY_CRM = "crm";
 
    // RECEITA_REMEDIO Table - column names
    private static final String KEY_RECEITA_ID = "receita_id";
    private static final String KEY_REMEDIO_ID = "remedio_id";
 
    // Table Create Statements
    // Remedio table create statement
    private static final String CREATE_TABLE_REMEDIO = "CREATE TABLE "
            + TABLE_REMEDIO + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOME
            + " TEXT," + KEY_DOSAGEM + " TEXT," + KEY_USO
            + " TEXT" + ")";
 
    // Receita table create statement
    private static final String CREATE_TABLE_RECEITA = "CREATE TABLE " + TABLE_RECEITA
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATA + " TEXT,"
            + KEY_NOMEMEDICO + " TEXT," + KEY_CRM + " TEXT" + ")";
 
    // Receita_remedio table create statement
    private static final String CREATE_TABLE_RECEITA_REMEDIO = "CREATE TABLE "
            + TABLE_RECEITA_REMEDIO + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_REMEDIO_ID + " INTEGER," + KEY_RECEITA_ID + " INTEGER" + ")";
 
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_TABLE_REMEDIO);
        db.execSQL(CREATE_TABLE_RECEITA);
        db.execSQL(CREATE_TABLE_RECEITA_REMEDIO);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMEDIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEITA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEITA_REMEDIO);
 
        // create new tables
        onCreate(db);
    }
    /*
     * Criando um remedio
     */
    public long createRemedio(Remedio rem, long[] tag_ids) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_NOME, rem.getNome());
        values.put(KEY_DOSAGEM, rem.getDosagem());
        values.put(KEY_USO, rem.getUso());
     
        // insert row
        long rem_id = db.insert(TABLE_REMEDIO, null, values);
         
        return rem_id;
    }
    
    /*
     * pegar um remedio
     */
    public Remedio getRemedio(long rem_id) {
        SQLiteDatabase db = this.getReadableDatabase();
     
        String selectQuery = "SELECT  * FROM " + TABLE_REMEDIO + " WHERE "
                + KEY_ID + " = " + rem_id;
     
        Log.e(LOG, selectQuery);
     
        Cursor c = db.rawQuery(selectQuery, null);
     
        if (c != null)
            c.moveToFirst();
     
        Remedio rem = new Remedio();
        rem.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        rem.setNome((c.getString(c.getColumnIndex(KEY_NOME))));
        rem.setDosagem(c.getString(c.getColumnIndex(KEY_DOSAGEM)));
        rem.setUso(c.getString(c.getColumnIndex(KEY_USO)));
     
        return rem;
    }
    
    /*
     * pegar todos os remedios
     * */
    public List<Remedio> getTodosRemedios() {
        List<Remedio> remedios = new ArrayList<Remedio>();
        String selectQuery = "SELECT  * FROM " + TABLE_REMEDIO;
     
        Log.e(LOG, selectQuery);
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
            	Remedio rem = new Remedio();
            	rem.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                rem.setNome((c.getString(c.getColumnIndex(KEY_NOME))));
                rem.setDosagem(c.getString(c.getColumnIndex(KEY_DOSAGEM)));
                rem.setUso(c.getString(c.getColumnIndex(KEY_USO)));
     
                // adding to todo list
                remedios.add(rem);
            } while (c.moveToNext());
        }
     
        return remedios;
    }
    
    /*
     * Update em remedio
     
    public int updateRemedio(Remedio rem) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_NOME, rem.getNome());
        values.put(KEY_DOSAGEM, rem.getDosagem());
        values.put(KEY_USO, rem.getUso());
        // updating row
        return db.update(TABLE_REMEDIO, values, KEY_ID + " = ?",
                new String[] { String.valueOf(rem.getId()) });
    }
    */
    /*
     * Deletar um remedio
     */
    public void deleteRemedio(long rem_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMEDIO, KEY_ID + " = ?",
                new String[] { String.valueOf(rem_id) });
        deleteRemRec(rem_id);
    }
    
    /*
     * Criando uma receita
     */
    public long createReceita(Receita rec) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_DATA, rec.getData());
        values.put(KEY_NOMEMEDICO, rec.getNomeMedico());
        values.put(KEY_CRM, rec.getCrm());
     
        // insert row
        long rec_id = db.insert(TABLE_RECEITA, null, values);
     
        return rec_id;
    }
    
    /*
     * pegar todas as receitas
     * */
    public List<Receita> getTodasReceitas() {
        List<Receita> receitas = new ArrayList<Receita>();
        String selectQuery = "SELECT  * FROM " + TABLE_RECEITA;
     
        Log.e(LOG, selectQuery);
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
            	Receita rec = new Receita();
                rec.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                rec.setData(c.getString(c.getColumnIndex(KEY_DATA)));
                rec.setNomeMedico(c.getString(c.getColumnIndex(KEY_NOMEMEDICO)));
                
     
                // adding to tags list
                receitas.add(rec);
            } while (c.moveToNext());
        }
        return receitas;
    }
    
    /*
     * Criar RECEITA_REMEDIO
     */
    public long createReceitaRemedio(long rec_id, long rem_id) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_RECEITA_ID, rec_id);
        values.put(KEY_REMEDIO_ID, rem_id);
        //values.put(KEY_CREATED_AT, getDateTime());
 
        long id = db.insert(TABLE_RECEITA_REMEDIO, null, values);
 
        return id;
    }
    
    /*
     * Deletar um remedio de uma receita
     */
    public void deleteRemRec(long rem_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECEITA_REMEDIO, KEY_REMEDIO_ID + " = ?",
                new String[] { String.valueOf(rem_id) });
    }
    
    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
    /*
     * getting todo count
     */
    public int getRemedioCount() {
        String countQuery = "SELECT  * FROM " + TABLE_REMEDIO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
 
        int count = cursor.getCount();
        cursor.close();
 
        // return count
        return count;
    }
}
