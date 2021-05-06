package br.edu.ifsp.scl.ads.pdm.contatos;

import java.io.Serializable;

public class Contato implements Serializable {
    private String nome;
    private String email;
    private String telefone;
    private String telefoneCelular;
    private String site;

    public Contato(String nome, String email, String telefone, String telefoneCelular, String site) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.telefoneCelular = telefoneCelular;
        this.site = site;
    }

    public Contato() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", telefoneCelular='" + telefoneCelular + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}
