package com.example.projeto.applistacompras.Model;

public class ItemQR {

    private String nome, preco, validade;


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

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }
}
