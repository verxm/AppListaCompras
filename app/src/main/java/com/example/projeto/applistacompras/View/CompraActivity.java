package com.example.projeto.applistacompras.View;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projeto.applistacompras.Adapters.ListaCompras;
import com.example.projeto.applistacompras.Controller.ItemDAO;
import com.example.projeto.applistacompras.Model.Item;
import com.example.projeto.applistacompras.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class CompraActivity extends AppCompatActivity {

    private ListView lvCompra;
    private List<Item> lista;
    private int idLista;

    private Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        idLista = getIntent().getExtras().getInt("codLista");

        lvCompra = (ListView) findViewById(R.id.lvCompra);

        btnScan = (Button) findViewById(R.id.btnScan);
        lista = new ArrayList<>();

        final Activity activity = this;

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Camera Scan");
                integrator.setCameraId(0);
                integrator.initiateScan();
            }
        });

        carregarItens();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(CompraActivity.this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(getResources().getString(R.string.excluirItem));
        menu.add(getResources().getString(R.string.excluirLista));
        menu.add(getResources().getString(R.string.selecionar));
        return super.onCreateOptionsMenu(menu);
    }
}
