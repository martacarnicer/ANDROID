package com.example.gymproject.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymproject.R;
import com.example.gymproject.entities.Centro;
import com.example.gymproject.entities.Reserva;
import com.example.gymproject.utils.FontUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> {

    private List<Reserva> reservas;
    private Context context;

    public ReservaAdapter(List<Reserva> reservas, Context context) {
        this.reservas = reservas;
        this.context = context;
    }

    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reserva, parent, false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        Reserva reserva = reservas.get(position);
        Log.d("ReservaAdapter", "Mostrando reserva: " + reserva.getNombreClase());
        holder.bind(reserva);

        // Aplicar la fuente Poppins a los elementos del ViewHolder
        Typeface poppinsRegular = FontUtil.getPoppinsRegularTypeface(context);
        Typeface poppinsSemiBold = FontUtil.getPoppinsSemiBoldTypeface(context);
        FontUtil.applyFontToViews(poppinsRegular, holder.textDiaReserva, holder.textCentroReserva, holder.textNombreClaseReserva, holder.textHoraReserva);
        holder.textDiaReserva.setTypeface(poppinsSemiBold);
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    public void actualizarReservas(List<Reserva> nuevasReservas) {
        this.reservas.clear();  // Limpiar siempre los datos anteriores

        if (nuevasReservas != null && !nuevasReservas.isEmpty()) {
            this.reservas.addAll(nuevasReservas);  // Añadir los nuevos datos solo si no están vacíos
        }

        notifyDataSetChanged();  // Refrescar la vista, incluso si la lista está vacía
        Log.d("ReservaAdapter", "Reservas actualizadas, size: " + reservas.size());
    }




    static class ReservaViewHolder extends RecyclerView.ViewHolder {
        TextView textDiaReserva, textCentroReserva, textNombreClaseReserva, textHoraReserva;

        public ReservaViewHolder(View itemView) {
            super(itemView);
            textDiaReserva = itemView.findViewById(R.id.textDiaReserva);
            textCentroReserva = itemView.findViewById(R.id.textCentroReserva);
            textNombreClaseReserva = itemView.findViewById(R.id.textNombreClaseReserva);
            textHoraReserva = itemView.findViewById(R.id.textHoraReserva);
        }

        public void bind(Reserva reserva) {
            // Convierte la fecha de String a Date y luego la formatea
            String fechaExactaClase = reserva.getFechaExactaClase();  // Asegúrate de que venga en formato correcto (ejemplo: "2024-10-18")

            SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());  // Formato DÍA/MES/AÑO

            try {
                // Convertir la fecha de entrada (cadena) en un objeto Date
                Date fecha = formatoEntrada.parse(fechaExactaClase);

                // Formatear la fecha en el formato deseado (dd MMMM yyyy)
                String fechaFormateada = formatoSalida.format(fecha);

                // Muestra la fecha formateada en la UI
                textDiaReserva.setText(reserva.getDiaSemana() + " - " + fechaFormateada);
            } catch (Exception e) {
                e.printStackTrace();
                // En caso de error, mostrar la fecha original o un mensaje
                textDiaReserva.setText(reserva.getDiaSemana() + " - " + reserva.getFechaExactaClase());
            }

            // Resto de datos a mostrar
            Centro centro = reserva.getCentro();
            if (centro != null) {
                textCentroReserva.setText(centro.getNombreCentro());
            } else {
                textCentroReserva.setText("Centro no disponible");
            }

            textNombreClaseReserva.setText(reserva.getNombreClase());
            textHoraReserva.setText(reserva.getHoraInicio() + " - " + reserva.getHoraFin());
        }



    }
}
