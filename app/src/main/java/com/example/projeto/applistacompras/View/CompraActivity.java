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
    private List<Item> itemList;
    private Item itens;
    private int idLista;
    private Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        lvCompra = (ListView) findViewById(R.id.lvCompra);
        carregarItens();
    }

    private void carregarItens() {
        int codLista = getIntent().getExtras().getInt("codLista");
        itens = ItemDAO.listarByCodLista(codLista,this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList);
        lvCompra.setAdapter(adapter);
    }
}
