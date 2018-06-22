package com.example.projeto.applistacompras.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;

import com.example.projeto.applistacompras.Model.Item;
import com.example.projeto.applistacompras.View.NovaListaActivity;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    public static void editar(Item p, Context contexto) {
        String sql = "UPDATE item SET " +
                " nome = '" + p.getNome() + "', " +
                " quantidade = '" + p.getQuantidade() + "' " +
                " WHERE id = " + p.getId();

        Conexao conn = new Conexao(contexto);
        conn.executar(sql);
    }

    public static void excluir(int id, Context contexto) {

        String sql = " DELETE FROM item " +
                " WHERE id = " + id;

        Conexao conn = new Conexao(contexto);
        conn.executar(sql);
    }

    public static void inserir(Item item, Context contexto) {

        String sql = "INSERT INTO item ( nome, quantidade, codLista) " +
                "VALUES ( '" + item.getNome() + "' , " +
                "  '" + item.getQuantidade() + "' , " +
                "   " + item.getCodLista() + " ) " ;

        Conexao conn = new Conexao(contexto);
        conn.executar(sql);
    }
    public static List<Item> listar(int codLista, Context contexto) {
        String sql = " SELECT * FROM item " +
                " WHERE codLista = " + codLista +
                " ORDER BY nome";
        Conexao conn = new Conexao(contexto);
        Cursor tabela = conn.consulta(sql);

        List<Item> listaDeItens = new ArrayList<>();

        if (tabela.getCount() > 0) {
            tabela.moveToFirst();

            do {
                Item item = new Item();
                item.setId(tabela.getInt(0));
                item.setNome(tabela.getString(1));
                item.setCodLista(tabela.getInt(2));

                listaDeItens.add(item);

            } while (tabela.moveToNext());

        }

        return listaDeItens;
    }


}
