package com.tfg.adoptaunamascota;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    //Interface para que MainActivity o cualquier otra actividad implemente el listener y poder capturar el evento
    //fuera del adaptador
    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    //Lo utilizamos para poder capturar los clics sobre los elementos fuera del adaptador
    private ItemClickListener clicListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Aquí obtenemos la referencia a nuestros elementos visuales
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            view.setOnClickListener(this);
            //tvNombreLista = view.findViewById(R.id.NameList);
        }

        //Esto propaga el evento hacía fuera, así podemos capturarlo en el punto que queramos de
        //nuestra aplicación
        @Override
        public void onClick(View view) {
            clicListener.onClick(view, getAdapterPosition());
        }
    }

    //Este metodo se utiliza desde la actividad que captura el evento de clic de los items
    public void setOnClickListener(ItemClickListener clicListener) {
        this.clicListener = clicListener;
    }

}

