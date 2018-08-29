package com.example.projeto.applistacompras.Model;

public class ItemQR {

    private String nome, validade;
    private int gluten;
    private int lactose;
    private Double preco;

    public int getGluten() {
        return gluten;
    }

    public void setGluten(int gluten) {
        this.gluten = gluten;
    }

    public int getLactose() {
        return lactose;
    }

    public void setLactose(int lactose) {
        this.lactose = lactose;
    }





    @Override
    public String toString() {
        return super.toString();
    }



    public String getNome() {
        return nome;
    }



    public void setNome(String nome) {

        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }
}
