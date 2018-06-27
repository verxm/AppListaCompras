package com.example.projeto.applistacompras.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projeto.applistacompras.Model.Item;
import com.example.projeto.applistacompras.R;

import java.util.List;

public class ListaCompras extends BaseAdapter {

    private List<Item> listaItens;
    private LayoutInflater inflater;
    private Context contexto;

    public ListaCompras(Context contexto, List<Item> lista) {
        this.listaItens = lista;
        this.inflater = LayoutInflater.from(contexto);
        this.contexto = contexto;
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
            suporte.etNome = view.findViewById(R.id.cnlItem);
            suporte.etQuantidade = view.findViewById(R.id.cnlQuantidade);
            suporte.ckConfirmado = view.findViewById(R.id.ckConfirmado);
            suporte.layout = view.findViewById(R.id.lcLayout);

            view.setTag(suporte);

        } else {
            suporte = (ListaCompras.ItemSuporte) view.getTag();
        }

        suporte.etNome.setText(item.getNome());
        suporte.etQuantidade.setText(item.getQuantidade());

        if (i % 2 == 0) {
            suporte.layout.setBackgroundColor(Color.WHITE);
        } else {
            suporte.layout.setBackgroundColor(Color.rgb(230, 230, 230));
        }

        return view;
    }

    private class ItemSuporte {
        TextView etNome, etQuantidade;
        CheckBox ckConfirmado;
        LinearLayout layout;
    }
}
