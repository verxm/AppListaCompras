package com.example.projeto.applistacompras.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto.applistacompras.Controller.ItemDAO;
import com.example.projeto.applistacompras.Model.Item;
import com.example.projeto.applistacompras.R;

import java.util.List;

public class ListaCompras extends BaseAdapter {

    private List<Item> listaItens;
    private LayoutInflater inflater;
    private Context contexto;
    private boolean mostrarTvPreco, mostrarBtnExcluir, lactose, gluten;

    public ListaCompras(Context contexto, List<Item> lista) {
        this.listaItens = lista;
        this.inflater = LayoutInflater.from(contexto);
        this.contexto = contexto;
        this.mostrarTvPreco = false;
        this.mostrarBtnExcluir = false;
    }


    public ListaCompras(Context contexto, List<Item> lista, boolean mostrarTvPreco, boolean mostrarBtnExcluir, boolean lactose, boolean gluten) {
        this.listaItens = lista;
        this.inflater = LayoutInflater.from(contexto);
        this.contexto = contexto;
        this.mostrarTvPreco = mostrarTvPreco;
        this.mostrarBtnExcluir = mostrarBtnExcluir;
        this.lactose = lactose;
        this.gluten = gluten;
    }


    @Override
    public int getCount() {
        return listaItens.size();
    }

    @Override
    public Object getItem(int i) {
        return listaItens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listaItens.get(i).getId();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ItemSuporte suporte;

        final Item item = listaItens.get(i);

        if (view == null) {
            view = inflater.inflate(R.layout.layout_lista_compras, null);
            suporte = new ItemSuporte();
            suporte.tvNome = view.findViewById(R.id.llcItem);
            suporte.tvSubtotal = view.findViewById(R.id.llctvSubtotal);
            suporte.btnExcluir = view.findViewById(R.id.llcBtnExcluir);
            suporte.ivAlerta = view.findViewById(R.id.llcAlerta);


            suporte.btnExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemDAO.excluir(item.getId(), contexto);
                    listaItens.remove(i);
                    notifyDataSetChanged();

                    Toast.makeText(contexto, "Item Excluído!", Toast.LENGTH_SHORT).show();
                }
            });
            suporte.tvPreco = view.findViewById(R.id.llctvPrecoItem);
            suporte.tvQuantidade = view.findViewById(R.id.llcQuantidade);
            suporte.layout = view.findViewById(R.id.llcLayout);

            view.setTag(suporte);

        } else {
            suporte = (ListaCompras.ItemSuporte) view.getTag();
        }


        if (item.getLactose() == 1 && lactose){

                suporte.ivAlerta.setVisibility(View.VISIBLE);

        }

        if (item.getGluten() == 1 && gluten){

                suporte.ivAlerta.setVisibility(View.VISIBLE);

        }




        //----------------------MOSTRAR PRECO E BTNEXCLUIR OU NAO-----------------------
        if (mostrarTvPreco){
            suporte.tvPreco.setVisibility(View.VISIBLE);
        }else {
            suporte.tvPreco.setVisibility(View.GONE);
        }

        if (mostrarBtnExcluir){
            suporte.btnExcluir.setVisibility(View.VISIBLE);
        }else {
            suporte.btnExcluir.setVisibility(View.GONE);
        }


        suporte.tvNome.setText(item.getNome());
        suporte.tvQuantidade.setText(item.getQuantidade());
        String subtotal = String.valueOf( item.getPreco()*Integer.valueOf(item.getQuantidade()));
        suporte.tvSubtotal.setText( "R$ " + subtotal.replace("." , ","));
        String preco = String.valueOf(item.getPreco());
        preco = preco.replace("." , ",");
        suporte.tvPreco.setText("R$ "+ preco);



//        if (i % 2 == 0) {
//            suporte.layout.setBackgroundColor(Color.WHITE);
//        } else {
//            suporte.layout.setBackgroundColor(Color.rgb(230, 230, 230));
//        }

        if (item.getCheck() == 1){
            suporte.layout.setBackgroundColor(Color.rgb(0, 200, 0));
        }

        return view;
    }






    private class ItemSuporte {
        TextView tvNome, tvQuantidade, tvSubtotal;
        Button btnExcluir;
        TextView tvPreco;
        LinearLayout layout;
        ImageView ivAlerta;
    }
}
