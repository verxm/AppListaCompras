package com.example.projeto.applistacompras.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto.applistacompras.Adapters.ItemLista;
import com.example.projeto.applistacompras.Controller.ItemDAO;
import com.example.projeto.applistacompras.Controller.ListaDAO;
import com.example.projeto.applistacompras.Model.Item;
import com.example.projeto.applistacompras.Model.Lista;
import com.example.projeto.applistacompras.R;

import java.util.ArrayList;
import java.util.List;

public class NovaListaActivity extends AppCompatActivity {

    private EditText etItem, etQuantidade;
    private Button btnAdicionar;
    private ListView lvItens;
    private Item item;
    private Lista list;
    private List<Item> lista;
    private int idLista;
    private CheckBox cbGluten;
    private CheckBox cbLactose;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_lista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        idLista = getIntent().getExtras().getInt("idLista");

        etItem = (EditText) findViewById(R.id.etItem);
        cbLactose = (CheckBox) findViewById(R.id.cbLactose);
        cbGluten = (CheckBox) findViewById(R.id.cbGluten);
        etQuantidade = (EditText) findViewById(R.id.etQuantidade);
        lvItens = (ListView) findViewById(R.id.lvItens);
        btnAdicionar = (Button) findViewById(R.id.btnAdicionar);


        lista = new ArrayList<>();

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etItem.getText().toString().equals("")){
                    Toast.makeText(NovaListaActivity.this, "Informe o item!", Toast.LENGTH_LONG).show();
                }else{
                    adicionar();
                    carregarItens();
                    limpar();
                }

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean listaVazia = ItemDAO.listaVazia(NovaListaActivity.this, idLista);

                if (listaVazia == true){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(NovaListaActivity.this);
                    alerta.setTitle("Sua lista esta vazia, deseja continuar criando-a?");
                    alerta.setPositiveButton("Sim", null);
                    alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ListaDAO.excluir(idLista, NovaListaActivity.this);
                            Intent intent = new Intent(NovaListaActivity.this, ListasActivity.class);
                            startActivity(intent);
                        }
                    });
                    alerta.show();
                }else{
                    int nQroGluten = 0;
                    int nQroLactose = 0;
                    if (cbGluten.isChecked()){
                        nQroGluten = 1;
                    }
                    if (cbLactose.isChecked()){
                        nQroLactose = 1;
                    }
                    ListaDAO.editarPreferencias(idLista, nQroLactose, nQroGluten, NovaListaActivity.this);
                    Intent i = new Intent(NovaListaActivity.this, ListasActivity.class);
                    Toast.makeText(NovaListaActivity.this, "Lista Adicionada", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                }

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        ListaDAO.excluir(idLista, NovaListaActivity.this);
        super.onDestroy();
    }


    public void carregarItens() {
        lista = ItemDAO.listar(idLista, this);
        ItemLista itemLista = new ItemLista(this, lista);
        lvItens.setAdapter(itemLista);
    }

    private void limpar() {
        etItem.setText("");
        etQuantidade.setText("");
    }

    private void adicionar() {

        if (item == null) {
            item = new Item();
        }

        item.setNome(etItem.getText().toString());
        item.setQuantidade(etQuantidade.getText().toString());
        item.setCodLista(idLista);
        item.setCheck(0);
        ItemDAO.inserir(item, this);

    }

}
