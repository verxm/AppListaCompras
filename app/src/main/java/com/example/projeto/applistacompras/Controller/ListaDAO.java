package com.example.projeto.applistacompras.Controller;

import android.content.Context;
import android.database.Cursor;

import com.example.projeto.applistacompras.Model.Lista;

import java.util.ArrayList;
import java.util.List;

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


    public static List<Lista> listar(Context contexto) {
        String sql = " SELECT * FROM lista " +
                " ORDER BY data desc";
        Conexao conn = new Conexao(contexto);
        Cursor tabela = conn.consulta(sql);

        List<Lista> listaDeItens = new ArrayList<>();

        if (tabela.getCount() > 0) {
            tabela.moveToFirst();

            do {
                Lista item = new Lista();
                item.setId(tabela.getInt(0));
                item.setData(tabela.getString(1));


                listaDeItens.add(item);

            } while (tabela.moveToNext());

        }

        return listaDeItens;
    }

    public static void excluir(int id, Context contexto) {

        String sql = " DELETE FROM lista " +
                " WHERE id = " + id;

        Conexao conn = new Conexao(contexto);
        conn.executar(sql);
    }

}
