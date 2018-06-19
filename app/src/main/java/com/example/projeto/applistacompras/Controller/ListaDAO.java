package com.example.projeto.applistacompras.Controller;

import android.content.Context;
import com.example.projeto.applistacompras.Model.Lista;

public class ListaDAO {

    public static void editar(Lista p, Context contexto) {
        String sql = "UPDATE lista SET " +
                " data = '" + p.getData() + "', " +
                " listaDeItens = '" + p.getListaDeItens() + "' " +
                " WHERE id = " + p.getId();

        Conexao conn = new Conexao(contexto);
        conn.executar(sql);
    }

    public static void excluir(int id, Context contexto) {

        String sql = " DELETE FROM lista " +
                " WHERE id = " + id;

        Conexao conn = new Conexao(contexto);
        conn.executar(sql);
    }

    public static void inserir(Lista p, Context contexto) {

        String sql = "INSERT INTO lista ( id, data, listaDeItens) " +
                "VALUES ( '" + p.getId() + "' , " +
                "  '" + p.getData() + "' , " +
                "   " + p.getListaDeItens() + " ) " ;

        Conexao conn = new Conexao(contexto);
        conn.executar(sql);
    }

}
