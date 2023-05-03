package com.example.escobar_silva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FormularioActivity extends AppCompatActivity {
    private EditText etNombre, etPrecio, etImagen;
    private String productoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etNombre = findViewById(R.id.et_nombre_formulario);
        etPrecio = findViewById(R.id.et_precio_formulario);
        etImagen = findViewById(R.id.et_imagen_formulario);
        productoId = getIntent().getStringExtra("producto_id");

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("productos").document(productoId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists()){
                        Producto producto = documentSnapshot.toObject(Producto.class);
                        etNombre.setText(producto.getNombre());
                        etPrecio.setText(producto.getPrecio().toString());
                        etImagen.setText(producto.getUrl());
                    }else {
                        Toast.makeText(this, "El producto no existe", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al buscar el producto" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                });
    }

    public void clickGuardar(View view) {
        String nombre = etNombre.getText().toString();
        Double precio = Double.parseDouble(etPrecio.getText().toString());
        String url = etImagen.getText().toString();

        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre(nombre);
        nuevoProducto.setPrecio(precio);
        nuevoProducto.setUrl(url);

       FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("productos").document(productoId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists()){
                        firestore.collection("productos").document(productoId).set(nuevoProducto);
                        Toast.makeText(this, "Se actualiza el producto", Toast.LENGTH_SHORT).show();
                    }else {
                        firestore.collection("productos").document().set(nuevoProducto);
                        Toast.makeText(this, "Se crea el producto", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    startActivity(new Intent(FormularioActivity.this, MainActivity.class));
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al buscar el producto", Toast.LENGTH_SHORT).show();
                });
        finish();

    }
}