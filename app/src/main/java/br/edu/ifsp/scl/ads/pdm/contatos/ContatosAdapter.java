package br.edu.ifsp.scl.ads.pdm.contatos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import br.edu.ifsp.scl.ads.pdm.contatos.databinding.ViewContatoBinding;

public class ContatosAdapter extends ArrayAdapter<Contato> {
    public ContatosAdapter(Context contexto, int layout, ArrayList<Contato> contatosList) {
        super(contexto, layout, contatosList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewContatoBinding viewContatoBinding;
        ContatoViewHolder contatoViewHolder;

        // Se é necessario inflar(CRIAR) uma nova celula
        if(convertView == null) {
            //Instancia a classe de View Binding que infla uma nova celula
            viewContatoBinding = ViewContatoBinding.inflate(LayoutInflater.from(getContext()));
            // Atribui a nova celula a View que sera devolvida preenchida para o ListView
            convertView = viewContatoBinding.getRoot();

            // Pega e guarda referencias para as Views internas da celula usando um holder
            contatoViewHolder = new ContatoViewHolder();
            contatoViewHolder.nomeContatoTv = viewContatoBinding.nomeContatoTv;
            contatoViewHolder.emailContatoTv = viewContatoBinding.emailContatoTv;

            // Associa a View da celula ao Holder que referencia suas Views internas
            convertView.setTag(contatoViewHolder);
        }

        // Pega o holder associado a celula (nova ou reciclada)
        contatoViewHolder = (ContatoViewHolder) convertView.getTag();

        // Atualizar os valores dos TextViews
        Contato contato = getItem(position);
        contatoViewHolder.nomeContatoTv.setText(contato.getNome());
        contatoViewHolder.emailContatoTv.setText(contato.getEmail());


        return convertView;
    }

    private class ContatoViewHolder {
        public TextView nomeContatoTv;
        public TextView emailContatoTv;
    }
}
