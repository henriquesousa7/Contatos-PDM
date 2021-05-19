package br.edu.ifsp.scl.ads.pdm.contatos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import br.edu.ifsp.scl.ads.pdm.contatos.databinding.ActivityContatoBinding;

public class ContatoActivity extends AppCompatActivity {

    private ActivityContatoBinding activityContatoBinding;
    private Contato contato;
    private int posicao = -1;
    private final int PERMISSAO_ESCRITA_ARMAZENAMENTO_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContatoBinding = ActivityContatoBinding.inflate(getLayoutInflater());
        setContentView(activityContatoBinding.getRoot());

        contato = (Contato) getIntent().getSerializableExtra(Intent.EXTRA_USER);

        if(contato != null) {
            // Receber posicao
            posicao = getIntent().getIntExtra(Intent.EXTRA_INDEX, -1);

            //Alterando a ativacao das views
            boolean ativo = (posicao != -1) ? true : false;
            alterarAtivacaoViews(ativo);
            if (ativo) {
                getSupportActionBar().setSubtitle("Edicao de contato");
            } else {
                getSupportActionBar().setSubtitle("Detalhes de contato");
            }

            // Usando dados do contato para preencher valores das views
            activityContatoBinding.nomeEt.setText(contato.getNome());
            activityContatoBinding.emailEt.setText(contato.getEmail());
            activityContatoBinding.telefoneEt.setText(contato.getTelefone());
            activityContatoBinding.telefoneCelularEt.setText(contato.getTelefoneCelular());
            activityContatoBinding.siteEt.setText(contato.getSite());
        } else {
            getSupportActionBar().setSubtitle("Novo contato");
        }
    }

    private void alterarAtivacaoViews(boolean ativo) {
        activityContatoBinding.nomeEt.setEnabled(ativo);
        activityContatoBinding.emailEt.setEnabled(ativo);
        activityContatoBinding.telefoneEt.setEnabled(ativo);
        activityContatoBinding.telefoneCelularEt.setEnabled(ativo);
        activityContatoBinding.siteEt.setEnabled(ativo);
        activityContatoBinding.comercialRb.setEnabled(ativo);
        activityContatoBinding.residencialRb.setEnabled(ativo);
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
                retornoIntent.putExtra(Intent.EXTRA_INDEX, posicao);
                setResult(RESULT_OK, retornoIntent);
                finish();
                break;
            case R.id.pdfBt:
                verificarPermissaoEscritaArmazenamentoExterno();
                break;
        }
    }

    public void verificarPermissaoEscritaArmazenamentoExterno() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                gerarDocumentoPdf();
            } else {
                // Solicitar permissao para o usuario em tempo de execucao
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSAO_ESCRITA_ARMAZENAMENTO_REQUEST_CODE);
            }
        } else {
            gerarDocumentoPdf();
        }
    }

    private void gerarDocumentoPdf() {
        // Pegando a alturar e a largura da View raiz para gerar a imagem que vai no pdf
        View conteudo = activityContatoBinding.inputsLL;
        int largura = conteudo.getWidth();
        int altura = conteudo.getHeight();

        // Criando o documento PDF
        PdfDocument documentoPdf = new PdfDocument();

        // Criando a configuracao de uma pagina e iniciando uma pagina a partir da configuracao
        PdfDocument.PageInfo configuracaoPagina = new PdfDocument.PageInfo.Builder(largura, altura, 1).create();
        PdfDocument.Page pagina = documentoPdf.startPage(configuracaoPagina);

        // Criando uma screenshot da View na página PDF
        conteudo.draw(pagina.getCanvas());

        documentoPdf.finishPage(pagina);

        // Salvar arquivo PDF
        File diretorioDocumentos = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
        try {
            File documento = new File(diretorioDocumentos, contato.getNome().replace(" ", "_") + ".pdf");
            documento.createNewFile();
            documentoPdf.writeTo(new FileOutputStream(documento));
            documentoPdf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSAO_ESCRITA_ARMAZENAMENTO_REQUEST_CODE) {
            verificarPermissaoEscritaArmazenamentoExterno();
        }
    }
}