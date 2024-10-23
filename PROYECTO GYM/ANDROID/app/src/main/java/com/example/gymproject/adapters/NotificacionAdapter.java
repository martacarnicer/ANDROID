package com.example.gymproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymproject.R;
import com.example.gymproject.entities.Notificacion;
import java.util.List;

public class NotificacionAdapter extends RecyclerView.Adapter<NotificacionAdapter.NotificacionViewHolder> {

    private List<Notificacion> notificaciones;

    public NotificacionAdapter(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    @NonNull
    @Override
    public NotificacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout del item de notificación
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notificacion, parent, false);
        return new NotificacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificacionViewHolder holder, int position) {
        // Obtener la notificación en la posición actual
        Notificacion notificacion = notificaciones.get(position);

        // Establecer los valores en los TextViews
        holder.titulo.setText(notificacion.getTitulo());
        holder.contenido.setText(notificacion.getContenido());
    }

    @Override
    public int getItemCount() {
        return notificaciones.size();
    }

    // ViewHolder que contiene los TextViews
    public static class NotificacionViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, contenido;

        public NotificacionViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);  // Referencia al TextView de título
            contenido = itemView.findViewById(R.id.contenido);  // Referencia al TextView de contenido
        }
    }
}
