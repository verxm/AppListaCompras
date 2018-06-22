package com.example.projeto.applistacompras.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto.applistacompras.Controller.ItemDAO;
import com.example.projeto.applistacompras.Model.Item;
import com.example.projeto.applistacompras.R;
import com.example.projeto.applistacompras.View.NovaListaActivity;

import java.util.List;

public class ItemLista extends BaseAdapter {

    private List<Item> listaItens;
    private LayoutInflater inflater;
    private Context contexto;

    public ItemLista(Context contexto, List<Item> lista) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemSuporte suporte;

        final Item item = listaItens.get(i);

        if (view == null) {
            view = inflater.inflate(R.layout.layout_lista_itens, null);
            suporte = new ItemSuporte();
            suporte.etNome = view.findViewById(R.id.cnlItem);
            suporte.etQuantidade = view.findViewById(R.id.cnlQuantidade);
            suporte.btnExcluir = view.findViewById(R.id.cnlBtnExcluir);
            suporte.layout = view.findViewById(R.id.llLayout);
            suporte.btnExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemDAO.excluir(item.getId(), contexto);
                    notifyDataSetChanged();
                    Toast.makeText(contexto, "Item Excluído!", Toast.LENGTH_SHORT).show();
                }
            });
            view.setTag(suporte);
        } else {
            suporte = (ItemSuporte) view.getTag();
        }

        suporte.etNome.setText("ITEM: " +item.getNome());
        suporte.etQuantidade.setText("Quantidade: " + item.getQuantidade());

        if (i % 2 == 0) {
            suporte.layout.setBackgroundColor(Color.WHITE);
        } else {
            suporte.layout.setBackgroundColor(Color.rgb(230, 230, 230));
        }

        return view;
    }

    private class ItemSuporte {
        TextView etNome, etQuantidade;
        LinearLayout layout;
        Button btnExcluir;
    }

}



