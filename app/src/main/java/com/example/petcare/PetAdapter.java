package com.example.petcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Locale;

import java.util.List;

public class PetAdapter extends ArrayAdapter<Pet> {
    private final int layoutResource;

    public PetAdapter(Context context, int resource, List<Pet> objects) {
        super(context, resource, objects);
        this.layoutResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layoutResource, parent, false);
        }

        Pet petAtual = getItem(position);

        if (petAtual != null) {
            TextView tvNome = convertView.findViewById(R.id.tvItemNome);
            TextView tvDetalhes = convertView.findViewById(R.id.tvItemDetalhes);
            TextView tvDataNascimento = convertView.findViewById(R.id.tvItemDataNascimento);

            tvNome.setText(petAtual.getNome());
            tvDetalhes.setText(getContext().getString(R.string.format_species_breed, petAtual.getEspecie(), petAtual.getRaca()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String dataFormatada = sdf.format(petAtual.getDataNascimento());
            tvDataNascimento.setText(getContext().getString(R.string.label_born_format, dataFormatada));
        }



        return convertView;
    }
}