package br.edu.ifsp.scl.ads.pdm.contatos;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.edu.ifsp.scl.ads.pdm.contatos.databinding.ViewContatoBinding;

public class ContatosRvAdapter extends
        RecyclerView.Adapter<ContatosRvAdapter.ContatoViewHolder> {
    public class ContatoViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private final TextView nomeContatoTv;
        private final TextView emailContatoTv;

        public ContatoViewHolder(View viewContato) {
            super(viewContato);
            nomeContatoTv = viewContato.findViewById(R.id.nomeContatoTv);
            emailContatoTv = viewContato.findViewById(R.id.emailContatoTv);
            viewContato.setOnCreateContextMenuListener(this);
        }

        public TextView getNomeContatoTv() {
            return nomeContatoTv;
        }

        public TextView getEmailContatoTv() {
            return emailContatoTv;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//            menu.add(R.id.acoesMg, R.id.enviarEmailMi, 0, R.string.enviar_e_mail);
//            menu.add(R.id.acoesMg, R.id.ligarMi, 0, R.string.chamar);
//            menu.add(R.id.acoesMg, R.id.acessarSiteMi, 0, R.string.acessar_site);
//            menu.add(R.id.contatoMg, R.id.editarContatoMi, 0, R.string.editar);
//            menu.add(R.id.contatoMg, R.id.removerContatoMi, 0, R.string.remover);
            menuInflater.inflate(R.menu.context_menu_contato,  menu);
        }
    }

    private ArrayList<Contato> contatosList;
    private OnContatoClickListener onContatoClickListener;
    private int posicao;
    private MenuInflater menuInflater;

    public ContatosRvAdapter(ArrayList<Contato> contatosList,
                             OnContatoClickListener onContatoClickListener,
                             MenuInflater menuInflater) {
        this.contatosList = contatosList;
        this.onContatoClickListener = onContatoClickListener;
        this.menuInflater = menuInflater;
    }

    // Chamado pelo layout manager sempre que uma nova célula precisa ser criada
    @NonNull
    @Override
    public ContatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewContatoBinding viewContatoBinding = ViewContatoBinding.inflate(LayoutInflater.from(parent.getContext()));
        ContatoViewHolder contatoViewHolder = new ContatoViewHolder(viewContatoBinding.getRoot());
        return contatoViewHolder;
    }

    // Chamado quando é necessario atualizar o valor da célula
    @Override
    public void onBindViewHolder(@NonNull ContatoViewHolder holder, int position) {
        // Busca o contato
        Contato contato = contatosList.get(position);

        // Seta os valores no ViewHolder
        holder.getNomeContatoTv().setText(contato.getNome());
        holder.getEmailContatoTv().setText(contato.getEmail());

        // Seta onClickListener de cada célula
        holder.itemView.setOnClickListener( (v) ->
                onContatoClickListener.onContatoClick(position)
        );

        // Seta onLongClickListener
        holder.itemView.setOnLongClickListener( (v) -> {
            posicao = position;
            return false;
        });
    }

    // Chamado para prever a quantidade de células necessarias
    @Override
    public int getItemCount() {
        return contatosList.size();
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }
}
