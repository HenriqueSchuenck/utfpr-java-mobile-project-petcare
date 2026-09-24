package com.example.petcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Date;

public class PetListActivity extends AppCompatActivity {

    private ListView lvPets;
    private ArrayList<Pet> listaPets;
    private PetAdapter adapter;
    private static final int REQUEST_CODE_CADASTRO = 1;
    private static final int REQUEST_CODE_EDICAO = 2;

    private int posicaoSelecionada = -1;
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);
        setTitle(getString(R.string.title_list));

        SharedPreferences prefs = getSharedPreferences("PetCarePrefs", MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("dark_mode", false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lvPets), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvPets = findViewById(R.id.lvPets);
        listaPets = new ArrayList<>();
        adapter = new PetAdapter(this, R.layout.item_pet, listaPets);
        lvPets.setAdapter(adapter);

        lvPets.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (actionMode != null) {
                    return false;
                }
                posicaoSelecionada = position;
                view.setSelected(true);
                actionMode = startActionMode(actionModeCallback);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_adicionar) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, REQUEST_CODE_CADASTRO);
            return true;
        } else if (item.getItemId() == R.id.item_config) {
            startActivity(new Intent(this, ConfigActivity.class));
            return true;
        } else if (item.getItemId() == R.id.item_sobre) {
            startActivity(new Intent(this, SobreActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_context_lista, menu);
            // Internacionalização do título do menu contextual
            mode.setTitle(getString(R.string.menu_options_title));
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.item_editar) {
                Pet pet = listaPets.get(posicaoSelecionada);
                Intent intent = new Intent(PetListActivity.this, MainActivity.class);

                intent.putExtra("posicao", posicaoSelecionada);
                intent.putExtra("nome", pet.getNome());
                intent.putExtra("raca", pet.getRaca());
                intent.putExtra("dataNascimento", pet.getDataNascimento().getTime());
                intent.putExtra("especie", pet.getEspecie());
                intent.putExtra("porte", pet.getPorte());         // Envia o Porte
                intent.putExtra("castrado", pet.isCastrado());    // Envia o Castrado

                startActivityForResult(intent, REQUEST_CODE_EDICAO);
                mode.finish();
                return true;
            } else if (item.getItemId() == R.id.item_excluir) {
                listaPets.remove(posicaoSelecionada);
                adapter.notifyDataSetChanged();
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            posicaoSelecionada = -1;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            String nome = data.getStringExtra("nome");
            String raca = data.getStringExtra("raca");
            String especie = data.getStringExtra("especie");
            String porte = data.getStringExtra("porte");
            boolean castrado = data.getBooleanExtra("castrado", false);

            long dataMillis = data.getLongExtra("dataNascimento", -1);
            Date dataNascimentoObj = (dataMillis != -1) ? new Date(dataMillis) : new Date();

            if (requestCode == REQUEST_CODE_CADASTRO) {
                listaPets.add(new Pet(nome, especie, raca, dataNascimentoObj, porte, castrado));

            } else if (requestCode == REQUEST_CODE_EDICAO) {
                int pos = data.getIntExtra("posicao", -1);
                if (pos != -1) {
                    Pet petEditado = listaPets.get(pos);
                    petEditado.setNome(nome);
                    petEditado.setRaca(raca);
                    petEditado.setDataNascimento(dataNascimentoObj);
                    petEditado.setEspecie(especie);
                    petEditado.setPorte(porte);
                    petEditado.setCastrado(castrado);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}