package com.example.projeto.applistacompras.Controller;

import android.content.Context;
import android.database.Cursor;

public class ListaDAO {


    public static boolean criarLista(String data, Context contexto){
        String sql = "INSERT INTO lista (data) VALUES ( '" + data + "' )";

        Conexao conn = new Conexao(contexto);
        conn.executar(sql);
        return true;
    }

    public static int buscarMaiorIDdaLista(Context contexto){
        String sql = "SELECT MAX(ID) FROM lista";

        Conexao conn = new Conexao(contexto);
        Cursor c = conn.consulta(sql);
        c.moveToFirst();

        int idMax = c.getInt(0);
        return idMax;
    }


    public static void excluir(int id, Context contexto) {

        String sql = " DELETE FROM lista " +
                " WHERE id = " + id;

        Conexao conn = new Conexao(contexto);
        conn.executar(sql);
    }



}
