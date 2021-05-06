package br.edu.ifsp.scl.ads.pdm.contatos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import br.edu.ifsp.scl.ads.pdm.contatos.databinding.ActivityContatoBinding;

public class ContatoActivity extends AppCompatActivity {

    private ActivityContatoBinding activityContatoBinding;
    private Contato contato;
    private final int CALL_PHONE_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContatoBinding = ActivityContatoBinding.inflate(getLayoutInflater());
        setContentView(activityContatoBinding.getRoot());
    }

    public void onClick(View view) {
        contato = new Contato(
                activityContatoBinding.nomeEt.getText().toString(),
                activityContatoBinding.emailEt.getText().toString(),
                activityContatoBinding.telefoneEt.getText().toString(),
                activityContatoBinding.telefoneCelularEt.getText().toString(),
                activityContatoBinding.siteEt.getText().toString()
        );

        switch (view.getId()) {
            case R.id.salvarBt:
                Intent retornoIntent = new Intent();
                retornoIntent.putExtra(Intent.EXTRA_USER, contato);
                setResult(RESULT_OK, retornoIntent);
                finish();
                break;
            case R.id.emailBt:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{activityContatoBinding.emailEt.getText().toString()});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contato");
                emailIntent.putExtra(Intent.EXTRA_TEXT, activityContatoBinding.nomeEt.getText().toString());

                startActivity(emailIntent);
                break;
            case R.id.ligarBt:
                verificarPermissaoLigar();
                break;
            case R.id.siteBt:
                Intent abrirNavegadorIntent = new Intent(Intent.ACTION_VIEW);
                abrirNavegadorIntent.setData(Uri.parse(activityContatoBinding.siteEt.getText().toString()));
                startActivity(abrirNavegadorIntent);
            case R.id.pdfBt:
                break;
        }
    }

    public void verificarPermissaoLigar() {
        Intent ligarIntent = new Intent(Intent.ACTION_CALL);
        ligarIntent.setData(Uri.parse("tel:" + activityContatoBinding.telefoneEt.getText().toString()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(ligarIntent);
            } else {
                // Solicitar permissao para o usuario em tempo de execucao
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_REQUEST_CODE);
            }
        } else {
            startActivity(ligarIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CALL_PHONE_PERMISSION_REQUEST_CODE) {
            verificarPermissaoLigar();
        }
    }
}