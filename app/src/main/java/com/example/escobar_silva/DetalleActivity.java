package com.example.escobar_silva;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class DetalleActivity extends AppCompatActivity {

    private TextView tvTituloDetalle, tvPrecioDetalle;
    private ImageView ivImagenPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        setTitle(getString(R.string.txt_detalle));

        tvTituloDetalle = findViewById(R.id.tv_titulo);
        tvPrecioDetalle = findViewById(R.id.tv_precio);
        ivImagenPrincipal = findViewById(R.id.iv_imagen);

        Producto miProductoAtrapado = (Producto) getIntent().getSerializableExtra("producto");

        tvTituloDetalle.setText(miProductoAtrapado.getNombre());
        tvPrecioDetalle.setText("$" + miProductoAtrapado.getPrecio().toString());
        Picasso.get()
                .load(miProductoAtrapado.getUrl())
                .error(R.drawable.ic_launcher_background)
                .into(ivImagenPrincipal);

    }
    public void clickEditar(View view){
        String productoId = getIntent().getStringExtra("producto_id");

        Intent miIntent = new Intent(this, FormularioActivity.class);
        miIntent.putExtra("producto_id", productoId);
        startActivity(miIntent);
    }
}