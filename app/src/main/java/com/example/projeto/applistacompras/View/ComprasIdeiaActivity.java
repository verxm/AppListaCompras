package com.example.projeto.applistacompras.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto.applistacompras.Adapters.ListaCompras;
import com.example.projeto.applistacompras.Controller.ItemDAO;
import com.example.projeto.applistacompras.Model.Item;
import com.example.projeto.applistacompras.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

public class ComprasIdeiaActivity extends AppCompatActivity {

    private ListView lvCompra;
    private List<Item> lista;
    private Item item;
    private int idLista;
    private Button btnIniciarCompra;
    private TextView tvData, tvSubTotal, tvValorSubTotal;
    private EditText etData;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras_ideia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        idLista = getIntent().getExtras().getInt("codLista");
        lvCompra = (ListView) findViewById(R.id.lvComprasIdeia);
        btnIniciarCompra = (Button) findViewById(R.id.btnIniciarCompra);
        tvData = (TextView) findViewById(R.id.tvData);
        tvSubTotal = (TextView) findViewById(R.id.tvSubTotal);
        tvValorSubTotal = (TextView) findViewById(R.id.tvValorSubTotal);
        etData = (EditText) findViewById(R.id.etData);


        final Activity activity = this;


        carregarItens();


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


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabEditar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




        // -------ELEMENTOS DE TELA QUE INICIAM INVISÌVEIS----------
        fabCamera.setVisibility(View.GONE);
        tvSubTotal.setVisibility(View.GONE);
        tvValorSubTotal.setVisibility(View.GONE);
        etData.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ComprasIdeiaActivity.this);
                alerta.setMessage(result.getContents());
                alerta.show();
                String barcode = result.getContents();
                String[] resp = barcode.split("|");

                String produto = resp[0].substring(0, 10);
                String preco = resp[1].substring(12,19);
                Log.i("meia", "Produto" + produto);
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


    private void carregarItens() {
        lista = ItemDAO.listar(idLista, this);
        ListaCompras adapter = new ListaCompras(this, lista);
        lvCompra.setAdapter(adapter);
    }
    

}
