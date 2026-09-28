package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNome, etRaca, etDataNascimento;
    private RadioGroup rgEspecie;
    private Spinner spPorte;
    private CheckBox cbCastrado;

    private int posicaoEdicao = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etNome = findViewById(R.id.etNome);
        etRaca = findViewById(R.id.etRaca);
        etDataNascimento = findViewById(R.id.etDataNascimento);
        rgEspecie = findViewById(R.id.rgEspecie);
        spPorte = findViewById(R.id.spPorte);
        cbCastrado = findViewById(R.id.cbCastrado);

        etDataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario = Calendar.getInstance();

                String dataTexto = etDataNascimento.getText().toString().trim();
                if (!dataTexto.isEmpty()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Date dataParseada = sdf.parse(dataTexto);
                        if (dataParseada != null) {
                            calendario.setTime(dataParseada);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                int ano = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int dia = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String dataFormatada = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                                etDataNascimento.setText(dataFormatada);
                            }
                        }, ano, mes, dia);

                datePickerDialog.show();
            }
        });

        String[] portes = {
                getString(R.string.size_small),
                getString(R.string.size_medium),
                getString(R.string.size_large)
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, portes);
        spPorte.setAdapter(adapter);

        Intent intent = getIntent();
        posicaoEdicao = intent.getIntExtra("id_pet", -1);

        if (posicaoEdicao != -1) {
            setTitle(getString(R.string.title_edit));

            etNome.setText(intent.getStringExtra("nome"));
            etRaca.setText(intent.getStringExtra("raca"));

            long dataMillis = intent.getLongExtra("dataNascimento", -1);
            if (dataMillis != -1) {
                Date data = new Date(dataMillis);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                etDataNascimento.setText(sdf.format(data));
            }

            String especie = intent.getStringExtra("especie");
            if (especie != null) {
                if (especie.equals(getString(R.string.species_dog))) {
                    ((RadioButton) findViewById(R.id.rbCao)).setChecked(true);
                } else if (especie.equals(getString(R.string.species_cat))) {
                    ((RadioButton) findViewById(R.id.rbGato)).setChecked(true);
                }
            }

            String porte = intent.getStringExtra("porte");
            if (porte != null) {
                if (porte.equals(getString(R.string.size_small))) spPorte.setSelection(0);
                else if (porte.equals(getString(R.string.size_medium))) spPorte.setSelection(1);
                else if (porte.equals(getString(R.string.size_large))) spPorte.setSelection(2);
            }

            cbCastrado.setChecked(intent.getBooleanExtra("castrado", false));
        } else {
            setTitle(getString(R.string.title_add));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.item_limpar) {
            limparFormulario();
            Toast.makeText(this, getString(R.string.msg_cleared), Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.item_salvar) {
            validarESalvarFormulario();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        String dataNascimentoStr = etDataNascimento.getText().toString().trim();

        if (nome.isEmpty()) {
            Toast.makeText(this, getString(R.string.err_name_empty), Toast.LENGTH_SHORT).show();
            etNome.requestFocus();
            return;
        }

        if (raca.isEmpty()) {
            Toast.makeText(this, getString(R.string.err_breed_empty), Toast.LENGTH_SHORT).show();
            etRaca.requestFocus();
            return;
        }

        if (dataNascimentoStr.isEmpty()) {
            Toast.makeText(this, getString(R.string.err_birth_empty), Toast.LENGTH_SHORT).show();
            etDataNascimento.requestFocus();
            return;
        }

        java.util.Date dataNascimentoObj;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dataNascimentoObj = sdf.parse(dataNascimentoStr);
        } catch (ParseException e) {
            Toast.makeText(this, getString(R.string.err_date_format), Toast.LENGTH_SHORT).show();
            etDataNascimento.requestFocus();
            return;
        }

        int selectedId = rgEspecie.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, getString(R.string.err_species_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedId);
        String especie = selectedRadioButton.getText().toString();
        String porte = spPorte.getSelectedItem().toString();
        boolean castrado = cbCastrado.isChecked();

        Intent intentRetorno = new Intent();
        intentRetorno.putExtra("id_pet", posicaoEdicao);
        intentRetorno.putExtra("nome", nome);
        intentRetorno.putExtra("raca", raca);
        intentRetorno.putExtra("dataNascimento", Objects.requireNonNull(dataNascimentoObj).getTime());
        intentRetorno.putExtra("especie", especie);
        intentRetorno.putExtra("porte", porte);
        intentRetorno.putExtra("castrado", castrado);
        intentRetorno.putExtra("posicao", posicaoEdicao);

        setResult(RESULT_OK, intentRetorno);
        finish();
    }
}