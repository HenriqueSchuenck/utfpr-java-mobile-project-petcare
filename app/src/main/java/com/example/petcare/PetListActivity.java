package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

import java.util.ArrayList;

public class PetListActivity extends AppCompatActivity {

    private ListView lvPets;
    private ArrayList<Pet> listaPets;
    private PetAdapter adapter;
    private Button btnAdicionar, btnSobre;

    private static final int REQUEST_CODE_CADASTRO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);
        setTitle("Meus Pets");

        lvPets = findViewById(R.id.lvPets);
        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnSobre = findViewById(R.id.btnSobre);

        listaPets = new ArrayList<>();
        adapter = new PetAdapter(this, R.layout.item_pet, listaPets);
        lvPets.setAdapter(adapter);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetListActivity.this, MainActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CADASTRO);
            }
        });

        btnSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetListActivity.this, SobreActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CADASTRO && resultCode == RESULT_OK && data != null) {
            String nome = data.getStringExtra("nome");
            String raca = data.getStringExtra("raca");
            String dataNascimento = data.getStringExtra("dataNascimento");
            String especie = data.getStringExtra("especie");

            Pet novoPet = new Pet(nome, especie, raca, dataNascimento);
            listaPets.add(novoPet);
            adapter.notifyDataSetChanged();
        }
    }
}