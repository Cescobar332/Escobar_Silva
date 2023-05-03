package com.example.escobar_silva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Producto> listaPrincipalProductos = new ArrayList<>();
    private RecyclerView rvListadoProductos;
    AdaptadorPersonalizado miAdaptador = new AdaptadorPersonalizado(listaPrincipalProductos);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.txt_listadodeproductos));

        rvListadoProductos = findViewById(R.id.rv_listado_productos);



        miAdaptador.setOnItemClickListener(new AdaptadorPersonalizado.OnItemClickListener() {
            @Override
            public void onItemClick(Producto miProducto, int posicion) {
                Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
                intent.putExtra("producto", miProducto);
                intent.putExtra("producto_id", miProducto.getId());
                startActivity(intent);
            }

            @Override
            public void onItemBtnEliminarClick(Producto miProducto, int posicion) {
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection("productos").document(miProducto.getId()).delete();
                listaPrincipalProductos.remove(posicion);
                miAdaptador.setListadoInformacion(listaPrincipalProductos);
            }
        });

        rvListadoProductos.setAdapter(miAdaptador);
        rvListadoProductos.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaPrincipalProductos.clear();
        cargarDatos();
    }

    public void cargarDatos(){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("productos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (DocumentSnapshot document : task.getResult()){
                        Producto productoAtrapado = document.toObject(Producto.class);
                        productoAtrapado.setId(document.getId());
                        listaPrincipalProductos.add(productoAtrapado);
                    }
                    miAdaptador.setListadoInformacion(listaPrincipalProductos);
                }else{
                    Toast.makeText(MainActivity.this, "No se pudo conectar al servidor", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        Producto producto1 = new Producto();
//        producto1.setNombre("Computador HP");
//        producto1.setPrecio(8000000.0);
//        producto1.setUrl("https://www.alkosto.com/medias/196786419003-001-750Wx750H?context=bWFzdGVyfGltYWdlc3wyMTQyMjh8aW1hZ2UvanBlZ3xoMDUvaDFhLzEzNDY0ODYwMjk1MTk4LzE5Njc4NjQxOTAwM18wMDFfNzUwV3g3NTBIfDcxZjRjYTUxN2M1MzQ0NTkxMGY4ODUwMWQ3Y2RkZWNhODk2Mjg0YjNmYmEyMjllZTc0OWMxZjYxMDIwNDQ5Yjc");
//
//        Producto producto2 = new Producto("Teclado DELL", 250000.0, "https://snpi.dell.com/snp/images/products/large/es-co~580-AFKZ/580-AFKZ.jpg");
//        Producto producto3 = new Producto("Mouse", 50000.0, "https://jyrtechnology.com.co/wp-content/uploads/2020/10/MGJR-033-1.png");
//
//        listaPrincipalProductos = new ArrayList<>();
//
//        listaPrincipalProductos.add(producto1);
//        listaPrincipalProductos.add(producto2);
//        listaPrincipalProductos.add(producto3);
    }
    public void AgregarProducto(View view){
        Intent miIntent = new Intent(this, FormularioActivity.class);
        startActivity(miIntent);
    }
    public void CerrarSesion(View view){
        SharedPreferences misPreferencias = getSharedPreferences("tienda_app", MODE_PRIVATE);
        SharedPreferences.Editor miEditor = misPreferencias.edit();
        miEditor.clear();
        miEditor.apply();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}