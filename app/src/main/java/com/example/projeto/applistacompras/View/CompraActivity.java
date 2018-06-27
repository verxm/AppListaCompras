package com.example.projeto.applistacompras.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.projeto.applistacompras.Controller.ItemDAO;
import com.example.projeto.applistacompras.Model.Item;
import com.example.projeto.applistacompras.R;

import java.util.ArrayList;
import java.util.List;

public class CompraActivity extends AppCompatActivity {

    private ListView lvCompra;
    private List<Item> lista;
    private Item item;
    private int idLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        idLista = getIntent().getExtras().getInt("codLista");
        lvCompra = (ListView) findViewById(R.id.lvCompra);
        lista = new ArrayList<>();

        carregarItens();
    }

    private void carregarItens() {
        lista = ItemDAO.listar(idLista, this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista);
        lvCompra.setAdapter(adapter);
    }
}
