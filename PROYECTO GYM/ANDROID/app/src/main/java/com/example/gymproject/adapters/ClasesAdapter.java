package com.example.gymproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.entities.Clase;

import java.util.List;

public class ClasesAdapter extends RecyclerView.Adapter<ClasesAdapter.ClaseViewHolder> {
    private List<Clase> clasesList;
    private Context context;
    private OnClaseClickListener onClaseClickListener;  // Interfaz de clic

    // Constructor actualizado para recibir el listener de clics
    public ClasesAdapter(List<Clase> clasesList, Context context, OnClaseClickListener listener) {
        this.clasesList = clasesList;
        this.context = context;
        this.onClaseClickListener = listener;
    }

    @NonNull
    @Override
    public ClaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_clase, parent, false);
        return new ClaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaseViewHolder holder, int position) {
        Clase clase = clasesList.get(position);
        holder.bind(clase);
    }

    @Override
    public int getItemCount() {
        return clasesList.size();
    }

    public void updateData(List<Clase> newClases) {
        clasesList.clear();
        clasesList.addAll(newClases);
        notifyDataSetChanged();
    }

    class ClaseViewHolder extends RecyclerView.ViewHolder {
        private TextView textNombreClase;
        private TextView textHora;

        public ClaseViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombreClase = itemView.findViewById(R.id.textNombreClase);
            textHora = itemView.findViewById(R.id.textHora);

            // Configurar el clic en el item
            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Clase claseSeleccionada = clasesList.get(position);
                    onClaseClickListener.onClaseClick(claseSeleccionada);  // Notificar al listener
                }
            });
        }

        public void bind(Clase clase) {
            textNombreClase.setText(clase.getNombreClase());
            textHora.setText(clase.getHoraInicio() + " - " + clase.getHoraFin());
        }
    }

    // Interfaz para manejar el clic en una clase
    public interface OnClaseClickListener {
        void onClaseClick(Clase clase);
    }
}
