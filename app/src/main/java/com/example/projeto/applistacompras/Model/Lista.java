package com.example.projeto.applistacompras.Model;

import java.util.ArrayList;
import java.util.Date;

public class Lista {

    private int id;
    private Date data;
    private ArrayList<Item> listaDeItens;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public ArrayList<Item> getListaDeItens() {
        return listaDeItens;
    }

    public void setListaDeItens(ArrayList<Item> listaDeItens) {
        this.listaDeItens = listaDeItens;
    }
}
