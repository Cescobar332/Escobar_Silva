package com.example.escobar_silva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Producto> listaPrincipalProductos;
    private RecyclerView rvListadoProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.txt_listadodeproductos));

        cargarDatos();

        rvListadoProductos = findViewById(R.id.rv_listado_productos);

        AdaptadorPersonalizado miAdaptador = new AdaptadorPersonalizado(listaPrincipalProductos);

        miAdaptador.setOnItemClickListener(new AdaptadorPersonalizado.OnItemClickListener() {
            @Override
            public void onItemClick(Producto miProducto, int posicion) {
                Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
                intent.putExtra("producto", miProducto);
                startActivity(intent);
            }

            @Override
            public void onItemBtnEliminarClick(Producto miProducto, int posicion) {
                listaPrincipalProductos.remove(posicion);
                miAdaptador.setListadoInformacion(listaPrincipalProductos);
            }
        });

        rvListadoProductos.setAdapter(miAdaptador);
        rvListadoProductos.setLayoutManager(new LinearLayoutManager(this));
    }
    public void cargarDatos(){
        Producto producto1 = new Producto();
        producto1.setNombre("Computador HP");
        producto1.setPrecio(8000000.0);
        producto1.setUrl("https://www.alkosto.com/medias/196786419003-001-750Wx750H?context=bWFzdGVyfGltYWdlc3wyMTQyMjh8aW1hZ2UvanBlZ3xoMDUvaDFhLzEzNDY0ODYwMjk1MTk4LzE5Njc4NjQxOTAwM18wMDFfNzUwV3g3NTBIfDcxZjRjYTUxN2M1MzQ0NTkxMGY4ODUwMWQ3Y2RkZWNhODk2Mjg0YjNmYmEyMjllZTc0OWMxZjYxMDIwNDQ5Yjc");

        Producto producto2 = new Producto("Teclado DELL", 250000.0, "https://snpi.dell.com/snp/images/products/large/es-co~580-AFKZ/580-AFKZ.jpg");
        Producto producto3 = new Producto("Mouse", 50000.0, "https://jyrtechnology.com.co/wp-content/uploads/2020/10/MGJR-033-1.png");

        listaPrincipalProductos = new ArrayList<>();

        listaPrincipalProductos.add(producto1);
        listaPrincipalProductos.add(producto2);
        listaPrincipalProductos.add(producto3);
    }
}