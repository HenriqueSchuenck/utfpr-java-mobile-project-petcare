package com.example.petcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class PetAdapter extends ArrayAdapter<Pet> {
    private int layoutResource;

    public PetAdapter(Context context, int resource, List<Pet> objects) {
        super(context, resource, objects);
        this.layoutResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
            tvDetalhes.setText(petAtual.getEspecie() + " - " + petAtual.getRaca());
            tvDataNascimento.setText("Nascimento: " + petAtual.getDataNascimento());
        }

        return convertView;
    }
}