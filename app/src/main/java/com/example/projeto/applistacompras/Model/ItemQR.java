package com.example.projeto.applistacompras.Model;

public class ItemQR {

    private String nome, validade;
    private Double preco;


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
