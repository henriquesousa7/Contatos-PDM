package br.edu.ifsp.scl.ads.pdm.contatos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import br.edu.ifsp.scl.ads.pdm.contatos.databinding.ActivityContatosBinding;

public class ContatosActivity extends AppCompatActivity {

    private ActivityContatosBinding activityContatosBinding;
    private ArrayList<Contato> contatosList;
    private ArrayAdapter<String> contatosAdapter;
    private final int NOVO_CONTATO_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContatosBinding = ActivityContatosBinding.inflate(getLayoutInflater());
        setContentView(activityContatosBinding.getRoot());

        // Instanciar data source
        contatosList = new ArrayList<>();
        // popuplarContatosList();

        // Instanciar o adapter
        contatosAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                contatosList
                );

        // Associando o adapter com a listView
        activityContatosBinding.contatosLv.setAdapter(contatosAdapter);
    }

    private void popuplarContatosList() {
        for(int i = 0; i < 20; i++) {
            contatosList.add(new Contato(
                        "Nome " + i,
                        "Email " + i,
                        "Telefone " + i,
                        "Celular " + i,
                        "www.site" + i + ".com.br"
                    )
            );
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contatos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.novoContatoMi) {
            Intent novoContatoIntent = new Intent(this, ContatoActivity.class);
            startActivityForResult(novoContatoIntent, NOVO_CONTATO_REQUEST_CODE);
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOVO_CONTATO_REQUEST_CODE && resultCode == RESULT_OK) {
            Contato contato = (Contato) data.getSerializableExtra(Intent.EXTRA_USER);
            if (contato != null) {
                contatosList.add(contato);
                contatosAdapter.notifyDataSetChanged();
            }
        }
    }
}