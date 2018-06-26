package com.example.projeto.applistacompras.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton; 
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.example.projeto.applistacompras.Adapters.ItemLista;
import com.example.projeto.applistacompras.Controller.ItemDAO;
import com.example.projeto.applistacompras.Controller.ListaDAO;
import com.example.projeto.applistacompras.Model.Lista;
import com.example.projeto.applistacompras.R;

import java.util.Calendar;
import java.util.List;

public class ListasActivity extends AppCompatActivity {

    private Context contexto;
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
                DatePicker calendario = new DatePicker(ListasActivity.this);
                alerta.setView(calendario);

                int ano = calendario.getYear();
                int mes = calendario.getMonth();
                int dia = calendario.getDayOfMonth();

                final String data = dia + "/" + mes + "/" + ano;

                alerta.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ListaDAO.criarLista(data, ListasActivity.this);
                        int idLista = ListaDAO.buscarMaiorIDdaLista(ListasActivity.this);
                        Intent intent = new Intent(ListasActivity.this, NovaListaActivity.class);
                        intent.putExtra("data", data);
                        intent.putExtra("idLista", idLista);
                        Log.i("lista", "id: "+ idLista);
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

        private void carregarListaComprasPendentes(){
        lLista = ListaDAO.listar(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lLista);
        lvLista.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        carregarListaComprasPendentes();
        super.onStart();
    }

    @Override
        protected void onRestart() {
            super.onRestart();
            carregarListaComprasPendentes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listas, menu);
        return true;
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}



