package com.example.projeto.applistacompras.Controller;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.projeto.applistacompras.Model.Item;

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
                "   " + item.getCodLista() + " ) ";

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

        Log.i("item", "Cont: " + tabela.getCount());
        if (tabela.getCount() > 0) {
            tabela.moveToFirst();

            do {
                Item item = new Item();
                item.setId(tabela.getInt(0));
                item.setNome(tabela.getString(1));
                item.setQuantidade(tabela.getString(2));
                item.setCodLista(tabela.getInt(3));

                Log.i("item", item.getNome());

                listaDeItens.add(item);

            } while (tabela.moveToNext());

        }

        return listaDeItens;
    }


    public static boolean listaVazia(Context contexto, int codLista) {

        boolean vazia;

        String sql = "SELECT * FROM item WHERE codLista = " + codLista;

        Conexao conn = new Conexao(contexto);
        Cursor itens = conn.consulta(sql);

        if (itens.getCount() == 0) {
            vazia = true;
        } else {
            vazia = false;
        }

        return vazia;


    }


}
