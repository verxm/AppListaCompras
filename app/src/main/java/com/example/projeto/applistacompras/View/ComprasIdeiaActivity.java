package com.example.projeto.applistacompras.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto.applistacompras.Adapters.ListaCompras;
import com.example.projeto.applistacompras.Controller.ItemDAO;
import com.example.projeto.applistacompras.Controller.ListaDAO;
import com.example.projeto.applistacompras.Model.Item;
import com.example.projeto.applistacompras.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

public class ComprasIdeiaActivity extends AppCompatActivity {

    private ListView lvCompra;
    private List<Item> lista;
    private Item item;
    private int idLista;
    private Button btnIniciarCompra, btnSalvar;
    private TextView tvData, tvSubTotal, tvValorSubTotal;
    private EditText etData;
    private  String dataLista;





    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras_ideia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        idLista = getIntent().getExtras().getInt("codLista");
        dataLista = getIntent().getExtras().getString("dataLista");

        lvCompra = (ListView) findViewById(R.id.lvComprasIdeia);
        btnIniciarCompra = (Button) findViewById(R.id.btnIniciarCompra);
        tvData = (TextView) findViewById(R.id.tvData);
        tvSubTotal = (TextView) findViewById(R.id.tvSubTotal);
        tvValorSubTotal = (TextView) findViewById(R.id.tvValorSubTotal);
        etData = (EditText) findViewById(R.id.etData);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        tvData.setText(dataLista);
        etData.setText(dataLista);


        final Activity activity = this;

        FloatingActionButton fabCamera = (FloatingActionButton) findViewById(R.id.fabCamera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Camera Scan");
                integrator.setCameraId(0);
                integrator.initiateScan();

            }
        });

        final FloatingActionButton fabEditar = (FloatingActionButton) findViewById(R.id.fabEditar);




        //-----------------------------------ACAO DO fabAdicionar---------------------------------------
        final FloatingActionButton fabAdicionar = (FloatingActionButton) findViewById(R.id.fabAdicionar);
        fabAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(ComprasIdeiaActivity.this);

                alerta.setTitle("Adicionar Item");


                final EditText etItem = new EditText(ComprasIdeiaActivity.this);
                etItem.setHint("Informe o Item");

                final EditText etQuantidade = new EditText(ComprasIdeiaActivity.this);
                etQuantidade.setHint("Quantidade");
                etQuantidade.setInputType(InputType.TYPE_CLASS_NUMBER);

                final LinearLayout layout = new LinearLayout(ComprasIdeiaActivity.this);
                layout.addView(etItem);
                layout.addView(etQuantidade);
                layout.setOrientation(LinearLayout.VERTICAL);

                alerta.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (etItem.equals("")){
                            alert("Informe o Item que deseja adicionar");
                        }else{
                            Item item = new Item();
                            item.setNome(etItem.getText().toString());
                            if (etQuantidade.equals("")){
                                item.setQuantidade("-x-");
                            }else{
                                item.setQuantidade(etQuantidade.getText().toString());
                            }
                            item.setCodLista(idLista);
                            ItemDAO.inserir(item, ComprasIdeiaActivity.this);
                            carregarItens();

                        }
                    }

                });

                alerta.setNegativeButton("Cancelar", null);

                alerta.setView(layout);

                alerta.show();


            }
        });






        //-----------------------------------ACAO DO fabEditar---------------------------------------
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabEditar.getVisibility() == View.VISIBLE){
                    fabEditar.setVisibility(View.GONE);
                    fabAdicionar.setVisibility(View.VISIBLE);
                    btnSalvar.setVisibility(View.VISIBLE);
                    btnIniciarCompra.setVisibility(View.GONE);
                    etData.setVisibility(View.VISIBLE);
                    tvData.setVisibility(View.GONE);
                    lvCompra.setEnabled(true);

                    carregarItensEditar();

                }
            }
        });








        //-----------------------------------ACAO DO btnSalvar---------------------------------------
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnSalvar.getVisibility() == View.VISIBLE){
                    btnSalvar.setVisibility(View.GONE);
                    btnIniciarCompra.setVisibility(View.VISIBLE);
                    fabAdicionar.setVisibility(View.GONE);
                    fabEditar.setVisibility(View.VISIBLE);
                    tvData.setVisibility(View.VISIBLE);

                    String dataLista = etData.getText().toString();
                    ListaDAO.editar(dataLista, idLista, ComprasIdeiaActivity.this);

                    etData.setVisibility(View.GONE);

                    lvCompra.setEnabled(false);

                    alert("Lista Editada");
                    carregarItens();
                }
            }
        });






        //-----------------------------------ACAO DO clickLvCompra---------------------------------------
        lvCompra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Item item = (Item) lvCompra.getItemAtPosition(i);
                int idItem = item.getId();
                String nomeItem = item.getNome();
                String quantidade = item.getQuantidade();

                AlertDialog.Builder alerta = new AlertDialog.Builder(ComprasIdeiaActivity.this);

                final EditText etItem = new EditText(ComprasIdeiaActivity.this);
                etItem.setHint("Informe o Item");
                etItem.setText(nomeItem);

                final EditText etQuantidade = new EditText(ComprasIdeiaActivity.this);
                etQuantidade.setHint("Quantidade");
                etQuantidade.setInputType(InputType.TYPE_CLASS_NUMBER);
                etQuantidade.setText(quantidade);

                final LinearLayout layout = new LinearLayout(ComprasIdeiaActivity.this);
                layout.addView(etItem);
                layout.addView(etQuantidade);
                layout.setOrientation(LinearLayout.VERTICAL);

                alerta.setView(layout);

                alerta.setPositiveButton("EDITAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (etItem.equals("")){
                            alert("Informe o novo Item");
                        }else{
                            item.setNome(etItem.getText().toString());
                            if (etQuantidade.equals("")){
                                item.setQuantidade("-x-");
                            }else{
                                item.setQuantidade(etQuantidade.getText().toString());
                            }
                            item.setCodLista(idLista);
                            ItemDAO.editar(item, ComprasIdeiaActivity.this);
                            alert("Item Editado!");
                            carregarItens();

                        }
                    }

                });
                alerta.setNegativeButton("Cancelar", null);
                alerta.show();

            }
        });






        carregarItens();



        // -------ELEMENTOS DE TELA QUE INICIAM INVISÌVEIS----------
        fabCamera.setVisibility(View.GONE);
        tvSubTotal.setVisibility(View.GONE);
        tvValorSubTotal.setVisibility(View.GONE);
        etData.setVisibility(View.GONE);
        btnSalvar.setVisibility(View.GONE);
        lvCompra.setEnabled(false);

        //----------------------------------------------------------
    }










    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                alert(result.getContents());
            } else {
                alert("Scan cancelado");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }






    private void alert(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }





    //IMPLEMENTAR OUTROS CARREGAR ITENS
    private void carregarItens() {
        lista = ItemDAO.listar(idLista, this);
        ListaCompras adapter = new ListaCompras(this, lista);
        lvCompra.setAdapter(adapter);
    }



    private void carregarItensEditar(){
        lista = ItemDAO.listar(idLista, this);
        ListaCompras adapter = new ListaCompras(this, lista, false, true);
        lvCompra.setAdapter(adapter);
    }











    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Excluir Lista");
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String nomeMenu = "Excluir Lista";

        if (item.toString().equals(nomeMenu)){
            AlertDialog.Builder alerta = new AlertDialog.Builder(ComprasIdeiaActivity.this);
            alerta.setTitle("Certeza que deseja excluir essa lista?");
            alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ListaDAO.excluir(idLista, ComprasIdeiaActivity.this);
                    Intent intent = new Intent(ComprasIdeiaActivity.this, ListasActivity.class);
                    startActivity(intent);
                    alert("Lista Excluida!");
                }
            });
            alerta.setNegativeButton("NÃO",null);
            alerta.show();
        }

        return super.onOptionsItemSelected(item);
    }




















}
