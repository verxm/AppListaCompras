package com.example.projeto.applistacompras.Controller;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.projeto.applistacompras.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO {


    public static void checkItem(Item item, Context contexto){
        int verdadeiroOUfalso = 0;
        if (item.getCheck() == 1){
            verdadeiroOUfalso = 1;
        }else{
            verdadeiroOUfalso = 0;
        }
        String sql ="UPDATE item SET checked = " + verdadeiroOUfalso + " WHERE id = " + item.getId();

        Conexao conn = new Conexao(contexto);
        conn.executar(sql);
    }

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

    public static int inserir(Item item, Context contexto) {

        String sql = "INSERT INTO item ( nome, quantidade, preco, codLista) " +
                "VALUES ( '" + item.getNome() + "' , " +
                "  '" + item.getQuantidade() + "' , " +
                "  '" + item.getPreco() + "' , " +
                "   " + item.getCodLista() + " ) ";

        Conexao conn = new Conexao(contexto);
        conn.executar(sql);

        return buscarMaiorIDoItem(contexto);
    }

    public static int buscarMaiorIDoItem(Context contexto) {
        String sql = "SELECT MAX(ID) FROM item";

        Conexao conn = new Conexao(contexto);
        Cursor c = conn.consulta(sql);
        c.moveToFirst();

        int idMax = c.getInt(0);
        return idMax;
    }

    public static List<Item> listar(int codLista, Context contexto) {
        String sql = " SELECT * FROM item " +
                " WHERE codLista = " + codLista +
                " ORDER BY nome";
        Conexao conn = new Conexao(contexto);
        Cursor tabela = conn.consulta(sql);

        List<Item> listaDeItens = new ArrayList<>();

        Log.i("item", "Cont: " + tabela.getCount());


        List<Item> listaItensChecados = new ArrayList<>();
        if (tabela.getCount() > 0) {
            tabela.moveToFirst();

            do {
                Item item = new Item();
                item.setId(tabela.getInt(0));
                item.setNome(tabela.getString(1));
                item.setQuantidade(tabela.getString(2));
                item.setPreco(tabela.getString(3));
                item.setCodLista(tabela.getInt(4));
                item.setCheck(tabela.getInt(5));


                if (item.getCheck() == 1){
                    listaItensChecados.add(item);
                }else{
                    listaDeItens.add(item);
                }

            } while (tabela.moveToNext());

        }

        for (int i = 0; i < listaItensChecados.size(); i++){
            Item item = listaItensChecados.get(i);
            listaDeItens.add(item);

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
