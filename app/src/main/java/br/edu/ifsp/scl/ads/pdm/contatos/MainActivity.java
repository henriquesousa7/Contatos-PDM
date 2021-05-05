package br.edu.ifsp.scl.ads.pdm.contatos;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import br.edu.ifsp.scl.ads.pdm.contatos.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private final int CALL_PHONE_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        //Listener telefone celular
        activityMainBinding.telefoneCelularCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()) {
                    activityMainBinding.telefoneCelularLl.setVisibility(View.VISIBLE);
                } else {
                    activityMainBinding.telefoneCelularLl.setVisibility(View.GONE);
                    activityMainBinding.telefoneCelularEt.setText("");
                }
            }
        });
    }

    public void onClickButtonSalvar(View view) {
    }

    public void onClickButtonEmail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{activityMainBinding.emailEt.getText().toString()});
        emailIntent.setType("message/rfc822");

        startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));
    }

    public void onClickButtonLigar(View view) {
        Intent ligarIntent = new Intent(Intent.ACTION_CALL);
        ligarIntent.setData(Uri.parse("tel:" + activityMainBinding.telefoneEt.getText().toString()));

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

    public void onClickButtonSite(View view) {
        Intent abrirNavegadorIntent = new Intent(Intent.ACTION_VIEW);
        abrirNavegadorIntent.setData(Uri.parse(activityMainBinding.siteEt.getText().toString()));
        startActivity(abrirNavegadorIntent);
    }

    public void onClickButtonPdf(View view) {
    }
}