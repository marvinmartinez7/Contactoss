package com.example.contactos;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactos.Configuraciones.Contactos;
import com.example.contactos.Configuraciones.SQLiteConexion;

public class Edicion extends AppCompatActivity {
    Button btnregr,btncompartir,btneliminar,btnver,btnActualizar;
    EditText nombre,numero,nota,ident,pais;
    SQLiteConexion conexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion);
        conexion = new SQLiteConexion(this, Contactos.NameDatabase, null, 1);
        btnregr=(Button)findViewById(R.id.regresar);
        btneliminar=(Button)findViewById(R.id.Eliminar);
        btncompartir=(Button)findViewById(R.id.Compatir);
        btnActualizar=(Button)findViewById(R.id.Actualizar);


        nombre=(EditText)findViewById(R.id.tvnombre);
        numero=(EditText)findViewById(R.id.tvtelefono);
        nota=(EditText)findViewById(R.id.tvnota);
        ident=(EditText)findViewById(R.id.tvid);
        pais=(EditText)findViewById(R.id.tvpais);

        String pxNombre = getIntent().getStringExtra("pxNombre");
        String pxnumero = getIntent().getStringExtra("pxTele");
        String pxnota = getIntent().getStringExtra("pxNota");
        String pxide = getIntent().getStringExtra("pxId");
        String pxpais = getIntent().getStringExtra("pxPais");


        nombre.setText(pxNombre);
        numero.setText(pxnumero);
        nota.setText(pxnota);
        ident.setText(pxide);
        pais.setText(pxpais);

        ////////////////////////////////////
        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarContacto();
            }
        });


        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarContacto();
            }
        });

        btncompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent callIntent=new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel"+numero));
            startActivity(callIntent);
            }
        });

    }



    private void actualizarContacto() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String [] params = { ident.getText().toString() };

        ContentValues valores = new ContentValues();
        valores.put(Contactos.id, ident.getText().toString());
        valores.put(Contactos.nombre, nombre.getText().toString());
        valores.put(Contactos.telefono,numero.getText().toString());
        valores.put(Contactos.nota,nota.getText().toString());
        db.update(Contactos.tablaContac, valores, Contactos.id + "=?", params);
        // Toast.makeText(getApplicationContext(), "Dato Actualizado", Toast.LENGTH_LONG).show();

        db.close();
        Toast.makeText(this, "Actualizo con exitos!!", Toast.LENGTH_SHORT).show();

        //Intent intent = new Intent(getApplicationContext(),listaContac.class);
        //startActivity(intent);
    }

    private void eliminarContacto() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String [] params = { ident.getText().toString() };

        String wherecond = Contactos.id + "=?";
        db.delete(Contactos.tablaContac, wherecond, params);
        // Toast.makeText(getApplicationContext(), "Dato eliminado", Toast.LENGTH_LONG).show();

        Limpiar();
        db.close();

        Intent intent = new Intent(getApplicationContext(),listaContac.class);
        startActivity(intent);
    }

    private void Limpiar() {
        nombre.setText("");
        numero.setText("");
        ident.setText("");
        nota.setText("");

    }

    public void regresar(View view)
    {
        Intent pantallas=new Intent(this,listaContac.class);
        startActivity(pantallas);

    }
}