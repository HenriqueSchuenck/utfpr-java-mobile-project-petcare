package com.example.petcare;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class PetListActivity extends AppCompatActivity {

    private ListView lvPets;
    private ArrayList<Pet> listaPets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        lvPets = findViewById(R.id.lvPets);
        listaPets = new ArrayList<>();

        carregarDadosDoResource();

        PetAdapter adapter = new PetAdapter(this, R.layout.item_pet, listaPets);
        lvPets.setAdapter(adapter);

        lvPets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pet petClicado = listaPets.get(position);
                String mensagem = "Pet selecionado: " + petClicado.getNome() +
                        " (" + petClicado.getEspecie() + ")";
                Toast.makeText(PetListActivity.this, mensagem, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregarDadosDoResource() {
        String[] nomes = getResources().getStringArray(R.array.pet_nomes);
        String[] especies = getResources().getStringArray(R.array.pet_especies);
        String[] racas = getResources().getStringArray(R.array.pet_racas);
        String[] datas = getResources().getStringArray(R.array.pet_datas_nascimento);

        for (int i = 0; i < nomes.length; i++) {
            listaPets.add(new Pet(nomes[i], especies[i], racas[i], datas[i]));
        }
    }
}