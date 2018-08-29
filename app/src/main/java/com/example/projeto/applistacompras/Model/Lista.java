package com.example.projeto.applistacompras.Model;

import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Date;

public class Lista {

    private int id;
    private String data;
    private ArrayList<Item> listaDeItens;
    private boolean gluten;
    private boolean lactose;

    public boolean isGluten() {
        return gluten;
    }

    public void setGluten(boolean gluten) {
        this.gluten = gluten;
    }

    public boolean isLactose() {
        return lactose;
    }

    public void setLactose(boolean lactose) {
        this.lactose = lactose;
    }







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
