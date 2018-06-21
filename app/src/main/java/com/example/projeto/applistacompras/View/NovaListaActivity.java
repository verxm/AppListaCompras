package com.example.projeto.applistacompras.View;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.projeto.applistacompras.Adapters.ItemLista;
import com.example.projeto.applistacompras.Controller.ItemDAO;
import com.example.projeto.applistacompras.Controller.ListaDAO;
import com.example.projeto.applistacompras.Model.Item;
import com.example.projeto.applistacompras.R;

import java.util.ArrayList;
import java.util.List;

public class NovaListaActivity extends AppCompatActivity {

    private EditText etItem, etQuantidade;
    private Button btnAdicionar;
    private ListView lvItens;
    private Item item;
    private List<Item> lista;
    private int idLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_lista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        idLista = getIntent().getExtras().getInt("idLista");

        etItem = (EditText) findViewById(R.id.etItem);
        etQuantidade = (EditText) findViewById(R.id.etQuantidade);
        lvItens = (ListView) findViewById(R.id.lvItens);
        btnAdicionar = (Button) findViewById(R.id.btnAdicionar);

        lista = new ArrayList<>();

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionar();
                Log.i("inseriu?", "item" + item);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        ListaDAO.excluir(idLista,NovaListaActivity.this);

        super.onDestroy();
    }

    public void carregarItens() {
        lista = ItemDAO.listar(idLista, this);


    }







    private void adicionar() {
        if (item == null)
            item = new Item();

        item.setNome(etItem.getText().toString());
        item.setQuantidade(etQuantidade.getText().toString());
        item.setCodLista(idLista);
        ItemDAO.inserir(item, this);
        carregarItens();
    }

}
