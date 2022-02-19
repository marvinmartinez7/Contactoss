package com.example.contactos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.contactos.Configuraciones.Contactos;
import com.example.contactos.Configuraciones.SQLiteConexion;
import com.example.contactos.tablas.Contact;

import java.util.ArrayList;

public class listaContac extends AppCompatActivity {

    Button btnregr;

    ListView lista;
    SQLiteConexion conexion;
    ArrayList<Contact> listaContac;
    ArrayList<String> ArregloContac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contac);
        btnregr=(Button)findViewById(R.id.regresar);
        conexion  = new SQLiteConexion(this,Contactos.NameDatabase, null, 1);
        lista = (ListView) findViewById(R.id.lista);
        ObtenerLista();
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ArregloContac);
        lista.setAdapter(adp);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact e = listaContac.get(i);
                Intent intent = new Intent(getApplicationContext(),Edicion.class);

                intent.putExtra("pxPais", e.getPais());
                intent.putExtra("pxId", e.getId().toString());
                intent.putExtra("pxNombre", e.getNombres());
                intent.putExtra("pxTele", e.getTelefono().toString());
                intent.putExtra("pxNota", e.getNota());
                startActivity(intent);
            }
        });


        btnregr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pantallaAnterior();
            }
        });


    }


    public void pantallaEdicion() {




    }




    private void pantallaAnterior() {
        Intent pantalla=new Intent(this,MainActivity.class);
        startActivity(pantalla);
    }

    private void ObtenerLista()
    {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Contact list_emple = null;
        listaContac = new ArrayList<Contact>();

        Cursor cursor = db.rawQuery("SELECT * FROM "+Contactos.tablaContac,null);

        while(cursor.moveToNext())
        {
            list_emple = new Contact();
            list_emple.setId(cursor.getInt(0));
            list_emple.setNombres(cursor.getString(1));
            list_emple.setTelefono(cursor.getInt(2));
            list_emple.setNota(cursor.getString(3));

            listaContac.add(list_emple);
        }

        cursor.close();

        llenalista();
    }

    private void llenalista()
    {
        ArregloContac = new ArrayList<String>();

        for(int i=0; i<listaContac.size(); i++)
        {
            ArregloContac.add(
                    listaContac.get(i).getNombres() +" | "+
                    listaContac.get(i).getTelefono());
        }

    }


}