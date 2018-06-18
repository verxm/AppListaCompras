package com.example.projeto.applistacompras.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;

import com.example.projeto.applistacompras.Model.Item;
import com.example.projeto.applistacompras.View.NovaListaActivity;

import java.util.ArrayList;
import java.util.List;

public class ItensCompraDAO {

    public static void excluirDados(final int id, Item p, final Context contexto) {

        String texto = "Nome: " + p.getNome() + "\n" +
                "Quantidade: " + p.getQuantidade() + "\n" +
                "\n\nDeseja excluir o item?";

        AlertDialog.Builder alerta = new AlertDialog.Builder(contexto);
        alerta.setTitle("Excluir Item");
        alerta.setIcon(android.R.drawable.ic_dialog_alert);
        alerta.setMessage(texto);


        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                excluir(id, contexto);
                NovaListaActivity nl = (NovaListaActivity) contexto;
                nl.carregarItens();

            }


        });
        alerta.setNegativeButton("Não", null);
        alerta.show();
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


    public static void inserir(Item p, Context contexto) {

        String sql = "INSERT INTO item ( nome, quantidade, codLista) " +
                "VALUES ( '" + p.getNome() + "' , " +
                "  '" + p.getQuantidade() + "' , " +
                "   " + p.getCodLista() + " ) " ;

        Conexao conn = new Conexao(contexto);
        conn.executar(sql);
    }

}
