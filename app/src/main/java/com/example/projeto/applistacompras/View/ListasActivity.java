package com.example.projeto.applistacompras.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;

import com.example.projeto.applistacompras.Controller.ListaDAO;
import com.example.projeto.applistacompras.Model.Lista;
import com.example.projeto.applistacompras.R;

import java.util.List;

public class ListasActivity extends AppCompatActivity {

    private ListView lvLista;
    private List<Lista> lLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvLista = (ListView) findViewById(R.id.lvListas);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                AlertDialog.Builder alerta = new AlertDialog.Builder(ListasActivity.this);
                alerta.setTitle("Quando as compras serão realizadas?");
                final DatePicker calendario = new DatePicker(ListasActivity.this);
                calendario.setMinDate(System.currentTimeMillis() - 1000);
                alerta.setView(calendario);

                alerta.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        int ano = calendario.getYear();
                        int mes = calendario.getMonth();
                        int dia = calendario.getDayOfMonth();

                        final String data = dia + "/" + (mes + 1) + "/" + ano;

                        ListaDAO.criarLista(data, ListasActivity.this);
                        int idLista = ListaDAO.buscarMaiorIDdaLista(ListasActivity.this);

                        Intent intent = new Intent(ListasActivity.this, NovaListaActivity.class);
                        intent.putExtra("data", data);
                        intent.putExtra("idLista", idLista);
                        startActivity(intent);
                    }
                });
                alerta.show();
            }
        });

        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Lista li = (Lista) lvLista.getItemAtPosition(i);
                Intent intent = new Intent(ListasActivity.this, CompraActivity.class);
                intent.putExtra("codLista", li.getId());
                startActivity(intent);
            }
        });

    }

    private void carregarListas() {
        lLista = ListaDAO.listar(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lLista);
        lvLista.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        carregarListas();
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        carregarListas();
    }

}



