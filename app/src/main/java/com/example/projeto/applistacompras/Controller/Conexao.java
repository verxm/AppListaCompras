package com.example.projeto.applistacompras.Controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static final String NOME = "listaDeCompras";
    private static final int VERSAO = 8;

    public Conexao(Context contexto) {
        super(contexto, NOME, null, VERSAO);
    }

    public void executar(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public Cursor consulta(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        onCreate(db);
        return db.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS lista (" +
                " id INTEGER NOT NULL PRIMARY KEY , " +
                " data TEXT NOT NULL , " +
                " gluten INTEGER DEFAULT 0 , " +
                " lactose INTEGER DEFAULT 0 )");


        db.execSQL("CREATE TABLE IF NOT EXISTS item (" +
                " id INTEGER NOT NULL PRIMARY KEY ," +
                " nome TEXT NOT NULL , " +
                " quantidade TEXT , " +
                " preco TEXT , " +
                " codLista INTEGER NOT NULL, " +
                " checked INTEGER DEFAULT 0, " +
                " lactose INTEGER DEFAULT 0, " +
                " gluten INTEGER DEFAULT 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i <= 7) {
            sqLiteDatabase.execSQL("DROP TABLE item");
            sqLiteDatabase.execSQL("DROP TABLE lista");
            onCreate(sqLiteDatabase);
        }
    }
}
