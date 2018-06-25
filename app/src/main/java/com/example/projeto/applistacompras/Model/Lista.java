package com.example.projeto.applistacompras.Model;

import java.util.ArrayList;
import java.util.Date;

public class Lista {

    private int id;
    private String data;
    private ArrayList<Item> listaDeItens;

    @Override
    public String toString() {
        return data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ArrayList<Item> getListaDeItens() {
        return listaDeItens;
    }

    public void setListaDeItens(ArrayList<Item> listaDeItens) {
        this.listaDeItens = listaDeItens;
    }
}
