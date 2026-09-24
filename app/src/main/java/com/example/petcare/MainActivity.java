package com.example.petcare;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNome, etRaca, etDataNascimento;
    private RadioGroup rgEspecie;
    private Spinner spPorte;
    private CheckBox cbCastrado;
    private Button btnLimpar, btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNome = findViewById(R.id.etNome);
        etRaca = findViewById(R.id.etRaca);
        etDataNascimento = findViewById(R.id.etDataNascimento);
        rgEspecie = findViewById(R.id.rgEspecie);
        spPorte = findViewById(R.id.spPorte);
        cbCastrado = findViewById(R.id.cbCastrado);
        btnLimpar = findViewById(R.id.btnLimpar);
        btnSalvar = findViewById(R.id.btnSalvar);

        String[] portes = {"Pequeno", "Médio", "Grande"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, portes);
        spPorte.setAdapter(adapter);

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparFormulario();
                Toast.makeText(MainActivity.this, "Formulário limpo com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarESalvarFormulario();
            }
        });
    }

    private void limparFormulario() {
        etNome.setText("");
        etRaca.setText("");
        etDataNascimento.setText("");
        rgEspecie.clearCheck();
        cbCastrado.setChecked(false);
        spPorte.setSelection(0);
        etNome.requestFocus();
    }

    private void validarESalvarFormulario() {
        String nome = etNome.getText().toString().trim();
        String raca = etRaca.getText().toString().trim();
        String dataNascimento = etDataNascimento.getText().toString().trim();

        if (nome.isEmpty()) {
            Toast.makeText(this, "Erro: O nome do pet não pode estar vazio!", Toast.LENGTH_SHORT).show();
            etNome.requestFocus();
            return;
        }

        if (raca.isEmpty()) {
            Toast.makeText(this, "Erro: A raça do pet não pode estar vazia!", Toast.LENGTH_SHORT).show();
            etRaca.requestFocus();
            return;
        }

        if (dataNascimento.isEmpty()) {
            Toast.makeText(this, "Erro: A data não pode estar vazia!", Toast.LENGTH_SHORT).show();
            etDataNascimento.requestFocus();
            return;
        }

        int selectedId = rgEspecie.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Erro: Selecione a espécie do pet!", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedId);
        String especie = selectedRadioButton.getText().toString();

        Intent intentRetorno = new Intent();
        intentRetorno.putExtra("nome", nome);
        intentRetorno.putExtra("raca", raca);
        intentRetorno.putExtra("dataNascimento", dataNascimento);
        intentRetorno.putExtra("especie", especie);

        setResult(RESULT_OK, intentRetorno);
        finish();
    }
}