package com.example.projeto.applistacompras.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto.applistacompras.Adapters.ListaCompras;
import com.example.projeto.applistacompras.Adapters.Mascaras;
import com.example.projeto.applistacompras.Controller.ItemDAO;
import com.example.projeto.applistacompras.Controller.ListaDAO;
import com.example.projeto.applistacompras.Model.Item;
import com.example.projeto.applistacompras.Model.ItemQR;
import com.example.projeto.applistacompras.Model.Lista;
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
    private int idLista, gluten = 0, lactose = 0;
    private Button btnIniciarCompra, btnSalvar;
    private TextView tvData, tvSubTotal, tvValorSubTotal;
    private EditText etData;
    private String dataLista;
    private LinearLayout llIniciarCompraSuperior;
    private ItemQR itemQR;
    private Lista list;
    private CheckBox cbGluten;
    private CheckBox cbLactose;
    int glutenItem;
    int lactoseItem;
    double precoItem;
    String nomeItemQR;

    private int restringeGlutem, restringeLactose;

    private int retorno;

    private static final int SEM_RESTRICAO_SALVAR = 1;
    private static final int COM_RESTRICAO_SALVAR = 2;
    private static final int COM_RESTRICAO_NAO_SALVAR = 3;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras_ideia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        idLista = getIntent().getExtras().getInt("codLista");
        dataLista = getIntent().getExtras().getString("dataLista");

        cbGluten = (CheckBox) findViewById(R.id.cbGluten);
        cbLactose = (CheckBox) findViewById(R.id.cbLactose);


        lvCompra = (ListView) findViewById(R.id.lvComprasIdeia);
        btnIniciarCompra = (Button) findViewById(R.id.btnIniciarCompra);
        tvData = (TextView) findViewById(R.id.tvData);
        tvSubTotal = (TextView) findViewById(R.id.tvSubTotal);
        tvValorSubTotal = (TextView) findViewById(R.id.tvValorSubTotal);
        etData = (EditText) findViewById(R.id.etData);
        etData.addTextChangedListener(Mascaras.mask(etData, Mascaras.FORMAT_DATE));
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        llIniciarCompraSuperior = (LinearLayout) findViewById(R.id.llIniciarCompraSuperior);
        final FloatingActionButton fabCamera = (FloatingActionButton) findViewById(R.id.fabCamera);

        // -------ELEMENTOS DE TELA QUE INICIAM INVISÌVEIS----------
        fabCamera.setVisibility(View.GONE);
        tvSubTotal.setVisibility(View.GONE);
        tvValorSubTotal.setVisibility(View.GONE);
        etData.setVisibility(View.GONE);
        btnSalvar.setVisibility(View.GONE);
        lvCompra.setEnabled(true);

        list = ListaDAO.getListaById(this, idLista);
        restringeGlutem = list.getGluten();
        restringeLactose = list.getLactose();

        //----------------------------------------------------------

        tvData.setText(dataLista);
        etData.setText(dataLista);


        final Activity activity = this;

        //-----------------ACAO DO fabCamera---------------------
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


        //-----------------------------------------------ACAO DO fabAdicionar-----------------------------------------------------------
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
                        if (etItem.getText().toString().equals("")) {
                            alert("Nenhum item adicionado!");
                        } else {
                            Item item = new Item();
                            item.setNome(etItem.getText().toString());
                            if (etQuantidade.getText().toString().equals("")) {
                                item.setQuantidade("-x-");
                            } else {
                                item.setQuantidade(etQuantidade.getText().toString());
                            }
                            item.setCodLista(idLista);
                            ItemDAO.inserir(item, ComprasIdeiaActivity.this);
                            carregarItens(false, false);

                        }
                    }

                });

                alerta.setNegativeButton("Cancelar", null);

                alerta.setView(layout);

                alerta.show();


            }
        });
        //-----------------------------------------------------------XXX---------------------------------------------------------------------


        //-----------------------------------------------------ACAO DO fabEditar-----------------------------------------------------------
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fabEditar.getVisibility() == View.VISIBLE) {
                    lvCompra.setEnabled(true);
                    fabEditar.setVisibility(View.GONE);
                    fabAdicionar.setVisibility(View.VISIBLE);
                    btnSalvar.setVisibility(View.VISIBLE);
                    btnIniciarCompra.setVisibility(View.GONE);
                    etData.setVisibility(View.VISIBLE);
                    tvData.setVisibility(View.GONE);

                    carregarItens(false, false);


                }
            }
        });
        //-----------------------------------------------------------XXX---------------------------------------------------------------------


        //-------------------------------------------------------ACAO DO btnSalvar---------------------------------------------------------
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnSalvar.getVisibility() == View.VISIBLE) {
                    btnSalvar.setVisibility(View.GONE);
                    btnIniciarCompra.setVisibility(View.VISIBLE);
                    fabAdicionar.setVisibility(View.GONE);
                    fabEditar.setVisibility(View.VISIBLE);
                    tvData.setVisibility(View.VISIBLE);


                    lista = ItemDAO.listar(idLista, ComprasIdeiaActivity.this);
                    if (lista.size() == 0) {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ComprasIdeiaActivity.this);
                        alerta.setTitle("Esta lista esta vazia!");
                        alerta.setMessage("Deseja salvar essa lista mesmo estando vazia?");
                        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String dataLista = etData.getText().toString();
                                ListaDAO.editar(dataLista, idLista, ComprasIdeiaActivity.this);
                            }
                        });


                        alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ListaDAO.excluir(idLista, ComprasIdeiaActivity.this);
                                alert("Essa lista foi excluida!");
                                Intent intent = new Intent(ComprasIdeiaActivity.this, ListasActivity.class);
                                startActivity(intent);
                            }
                        });

                        alerta.show();

                    } else {
                        String dataLista = etData.getText().toString();
                        ListaDAO.editar(dataLista, idLista, ComprasIdeiaActivity.this);
                        alert("Lista Editada");
                        tvData.setText(dataLista);
                    }


                    etData.setVisibility(View.GONE);

                    carregarItens(false, false);
                }
            }
        });
        //-------------------------------------------------------XXX-----------------------------------------------------------------------


        //-------------------------------------------------ACAO DO clickLvCompra---------------------------------------------------------
        lvCompra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (fabAdicionar.getVisibility() == View.VISIBLE) {
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
                            if (etItem.equals("")) {
                                alert("Informe o novo Item");
                            } else {
                                item.setNome(etItem.getText().toString());
                                if (etQuantidade.equals("")) {
                                    item.setQuantidade("-x-");
                                } else {
                                    item.setQuantidade(etQuantidade.getText().toString());
                                }
                                item.setCodLista(idLista);
                                ItemDAO.editar(item, ComprasIdeiaActivity.this);
                                alert("Item Editado!");
                                carregarItens(false, false);

                            }
                        }

                    });
                    alerta.setNegativeButton("Cancelar", null);
                    alerta.show();

                }
            }
        });
        //-------------------------------------------------------XXX-----------------------------------------------------------------------


        //-----------------------------------------------ACAO DO btnIniciarCompra------------------------------------------------------------
        btnIniciarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnIniciarCompra.setVisibility(View.GONE);
                fabEditar.setVisibility(View.GONE);
                tvData.setVisibility(View.GONE);
                tvSubTotal.setVisibility(View.VISIBLE);
                tvValorSubTotal.setVisibility(View.VISIBLE);
                fabCamera.setVisibility(View.VISIBLE);
                llIniciarCompraSuperior.setVisibility(View.VISIBLE);
                carregarItens(true, false);
            }
        });
        //----------------------------------------------------------XXX-----------------------------------------------------------------------


        carregarItens(false, false);


    }//-----------------------------------------------------FIM onCreate--------------------------------------------------------------------


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                String resultado = result.getContents();
                String itemGetByQR[] = resultado.split("&");
                nomeItemQR = itemGetByQR[0];
                final String precoItemQR = itemGetByQR[1];
                String validadeItemQR = itemGetByQR[2];
                String glutenItemQR = itemGetByQR[3];
                String lactoseItemQR = itemGetByQR[4];

                precoItem = Double.parseDouble(precoItemQR);
                glutenItem = Integer.parseInt(glutenItemQR);
                lactoseItem = Integer.parseInt(lactoseItemQR);

                itemQR = new ItemQR();
                itemQR.setNome(nomeItemQR);
                itemQR.setPreco(precoItem);
                itemQR.setValidade(validadeItemQR);
                itemQR.setGluten(glutenItem);
                itemQR.setLactose(lactoseItem);

                boolean acheiItem = acheiItem(itemQR);

                if (!acheiItem) {

                    if (verificarPreferencia() == SEM_RESTRICAO_SALVAR) {

                        AlertDialog.Builder alerta = new AlertDialog.Builder(ComprasIdeiaActivity.this);
                        alerta.setTitle("Item não encontrado!");
                        alerta.setMessage("Deseja adicionar o " + nomeItemQR + " à lista?");
                        alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Item itemNovo = new Item();
                                itemNovo.setNome(nomeItemQR);
                                itemNovo.setCheck(1);
                                itemNovo.setPreco(precoItem);
                                itemNovo.setQuantidade("1");
                                itemNovo.setCodLista(idLista);
                                itemNovo.setId(ItemDAO.inserir(itemNovo, ComprasIdeiaActivity.this));
                                ItemDAO.checkItem(itemNovo, ComprasIdeiaActivity.this);
                                alert("Item adicionado à lista!");

                                carregarItens(true, false);
                            }
                        });
                        alerta.setNegativeButton("Não", null);
                        alerta.show();

                    }
                } else {

                    carregarItens(true, false);
                }

//                String textoTeste = "Nome: " + nomeItemQR + "; Quantidade: " + quantidadeItemQR + "; Unidade: " + unidadeItemQR + "; Preco: " + precoItemQR + "Validade: " + validadeItemQR;
//                AlertDialog.Builder alerta = new AlertDialog.Builder(ComprasIdeiaActivity.this);
//                alerta.setMessage(textoTeste);
//                alerta.setPositiveButton("OK", null);
//                alerta.show();


            } else {
                alert("Scan cancelado");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private int verificarPreferencia() {
        boolean temRestricao = false;
        retorno = 0;
//        list = ListaDAO.getListaById(this, idLista);
        String msg = "";

        if( restringeLactose  == 1  && restringeGlutem == 1 ){

            if ( lactoseItem == 1 &&  glutenItem == 1 ) {
                msg = "Item contém Glúten e Lactose!";
                temRestricao = true;
            } else {
                if (glutenItem == 1 ) {
                    msg = "Item contém Glúten!";
                    temRestricao = true;
                }
                if (lactoseItem == 1) {
                    msg = "Item contém lactose!";
                    temRestricao = true;
                }
            }
        }else {
            if ( restringeGlutem == 1 ){
                if (glutenItem == 1 ) {
                    msg = "Item contém Glúten!";
                    temRestricao = true;
                }
            }
            if (restringeLactose == 1) {
                if (glutenItem == 1 ) {
                    msg = "Item contém lactose!";
                    temRestricao = true;
                }
            }


        }



        if (temRestricao) {

            //TERMINAR AMANHA
            AlertDialog.Builder alerta = new AlertDialog.Builder(ComprasIdeiaActivity.this);
            alerta.setTitle(msg);
            alerta.setMessage("Deseja adicionar mesmo assim?");
            alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Item itemNovo = new Item();
                    itemNovo.setNome(nomeItemQR);
                    itemNovo.setCheck(1);
                    itemNovo.setPreco(precoItem);
                    itemNovo.setQuantidade("1");
                    itemNovo.setCodLista(idLista);
                    itemNovo.setId(ItemDAO.inserir(itemNovo, ComprasIdeiaActivity.this));
                    ItemDAO.checkItem(itemNovo, ComprasIdeiaActivity.this);
                    alert("Item adicionado à lista!");

                    carregarItens(true, false);
                    retorno = COM_RESTRICAO_SALVAR;
                }
            });
            alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    retorno = COM_RESTRICAO_NAO_SALVAR;
                }
            });
            alerta.show();

        } else {
            retorno = SEM_RESTRICAO_SALVAR;
        }

        return retorno;
    }


    private void alert(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }


    //IMPLEMENTAR OUTROS CARREGAR ITENS
    private void carregarItens(boolean mostrarTvPreco, boolean mostrarBtnExcluir) {
        lista = ItemDAO.listar(idLista, this);
        Lista listinha = ListaDAO.getListaById(ComprasIdeiaActivity.this, idLista);

        ListaCompras adapter = new ListaCompras(this, lista, mostrarTvPreco, mostrarBtnExcluir);
        lvCompra.setAdapter(adapter);
        calcular();
    }


    private boolean acheiItem(ItemQR itemQR) {
        boolean acheiItem = false;
        for (int i = 0; i < lvCompra.getCount(); i++) {

            Item item = (Item) lvCompra.getItemAtPosition(i);
            final String nomeItemQR = itemQR.getNome().toLowerCase();
            final Double precoItemQR = itemQR.getPreco();

            if (nomeItemQR.toLowerCase().contains(item.getNome())) {
                item.setCheck(1);
                item.setPreco(precoItemQR);
                item.setQuantidade(String.valueOf(Integer.valueOf(item.getQuantidade()) + 1));
                ItemDAO.checkItem(item, ComprasIdeiaActivity.this);
                acheiItem = true;
                break;
            } else {
                acheiItem = false;
            }

        }
        return acheiItem;
    }


//    private void carregarItensEditar(){
//        lista = ItemDAO.listar(idLista, this);
//        ListaCompras adapter = new ListaCompras(this, lista, false, true);
//        lvCompra.setAdapter(adapter);
//
//    }


    private void calcular() {
        Double total = 0d;

        for (Item item : lista) {
            total += item.getPreco() * Integer.valueOf(item.getQuantidade());


        }
        String textoTotal = String.valueOf(total);
        tvValorSubTotal.setText("R$" + textoTotal.replace(".", ","));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Excluir Lista");
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String nomeMenu = "Excluir Lista";

        if (item.toString().equals(nomeMenu)) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ComprasIdeiaActivity.this);
            alerta.setTitle("Certeza que deseja excluir essa lista?");
            alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ListaDAO.excluir(idLista, ComprasIdeiaActivity.this);
                    ItemDAO.excluirItensByCodLista(ComprasIdeiaActivity.this, idLista);
                    Intent intent = new Intent(ComprasIdeiaActivity.this, ListasActivity.class);
                    startActivity(intent);
                    alert("Lista Excluida!");
                }
            });
            alerta.setNegativeButton("NÃO", null);
            alerta.show();
        }

        return super.onOptionsItemSelected(item);
    }


}
