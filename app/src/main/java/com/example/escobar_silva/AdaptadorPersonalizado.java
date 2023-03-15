package com.example.escobar_silva;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorPersonalizado extends RecyclerView.Adapter<AdaptadorPersonalizado.ViewHolder>{

    public void setListadoInformacion(ArrayList<Producto> listadoInformacion) {
        this.listadoInformacion = listadoInformacion;
        notifyDataSetChanged();
    }

    private ArrayList<Producto> listadoInformacion;
    private OnItemClickListener onItemClickListener;

    public AdaptadorPersonalizado(ArrayList<Producto> listadoInformacion) {
        this.listadoInformacion = listadoInformacion;
        this.onItemClickListener = null;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AdaptadorPersonalizado.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View miView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_productos, parent, false);
        return new ViewHolder(miView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPersonalizado.ViewHolder holder, int position) {
        Producto miProducto = listadoInformacion.get(position);
        holder.enlazar(miProducto);

    }

    @Override
    public int getItemCount() {
        return listadoInformacion.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNombre, tvPrecio;
        private ImageView ivProducto;
        private Button btnEliminar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tv_item_producto);
            tvPrecio = itemView.findViewById(R.id.tv_item_precio);
            ivProducto = itemView.findViewById(R.id.iv_producto);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar);
        }
        public void enlazar(Producto miProducto){
            tvNombre.setText(miProducto.getNombre());
            tvPrecio.setText(miProducto.getPrecio().toString());
            Picasso.get()
                    .load(miProducto.getUrl())
                    .error(R.drawable.ic_launcher_background)
                    .into(ivProducto);

            if(onItemClickListener != null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(miProducto, getAdapterPosition());
                    }
            });
            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemBtnEliminarClick(miProducto, getAdapterPosition());
                }
            });
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Producto miProducto, int posicion);
        void onItemBtnEliminarClick(Producto miProducto, int posicion);
    }
}
