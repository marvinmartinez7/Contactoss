package com.example.contactos;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.contactos.Configuraciones.Contactos;
import com.example.contactos.Configuraciones.SQLiteConexion;

public class MainActivity extends AppCompatActivity {

    EditText identidad,nombre,numero,nota;
    ImageView ObjImagen;
    Spinner pais;
    Button btnfto,btnGuadar,btnSalvados;
    String CurrentPhotoPath;
    static final int PETICION_ACCESO_CAM = 100;
    static final int TAKE_PIC_REQUEST = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        identidad=(EditText)findViewById(R.id.txtident);
        nombre=(EditText)findViewById(R.id.txtnombre);
        numero=(EditText)findViewById(R.id.txttelefono);
        nota=(EditText)findViewById(R.id.txtdescrip);
        ObjImagen=(ImageView)findViewById(R.id.txtimagen);
        pais=(Spinner)findViewById(R.id.paises);
        btnfto=(Button)findViewById(R.id.agregarfoto);
        btnGuadar=(Button)findViewById(R.id.salvar);
        btnSalvados=(Button)findViewById(R.id.Salvados);
        String [] opciones={"Honduras","Guatemala","Salvador","Costa Rica"};
        ArrayAdapter <String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        pais.setAdapter(adapter);

        btnfto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisos();
            }

           
        });

        btnGuadar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Guadar();
            }
        });

        btnSalvados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patantallaLista();
            }
        });

    }

    private void patantallaLista() {
        Intent pantalla=new Intent(this,listaContac.class);
        startActivity(pantalla);
    }

    private void Guadar() {
        SQLiteConexion conexion = new SQLiteConexion(this, Contactos.NameDatabase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        String ide=identidad.getText().toString();
        String name=nombre.getText().toString();
        String tele=numero.getText().toString();
        String bloc=nota.getText().toString();

        if (!ide.isEmpty() && !name.isEmpty() && !tele.isEmpty() && !bloc.isEmpty()) {
            ContentValues valores = new ContentValues();
            valores.put(Contactos.pais, pais.getSelectedItem().toString());
            valores.put(Contactos.id,identidad.getText().toString());
            valores.put(Contactos.nombre, nombre.getText().toString());
            valores.put(Contactos.telefono, numero.getText().toString());
            valores.put(Contactos.nota, nota.getText().toString());
            //valores.put(Contactos.foto, ObjImagen);

            Long resultado = db.insert(Contactos.tablaContac, Contactos.id, valores);
            Toast.makeText(getApplicationContext(),"Registro ingreso con exito!! Codigo "+ resultado.toString(),
                    Toast.LENGTH_LONG).show();

            db.close();  // Cerramos la conexion a la base de datos

            LimpiarPatalla();
        }else{
            Toast.makeText(this, "LLenar Los Campos Vacios", Toast.LENGTH_SHORT).show();
        }


    }

    private void LimpiarPatalla()
    {
        nombre.setText("");
        numero.setText("");
        nota.setText("");
        identidad.setText("");
    }

    private void permisos() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PETICION_ACCESO_CAM);
        }
        else
        {
            tomarfoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PETICION_ACCESO_CAM)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                tomarfoto();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Se necesitan permisos de acceso a la camara", Toast.LENGTH_LONG).show();
        }
    }

    private void tomarfoto() {
        Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takepic.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(takepic, TAKE_PIC_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Byte[] arreglo;

        if(requestCode == TAKE_PIC_REQUEST && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap imagen = (Bitmap) extras.get("data");
            ObjImagen.setImageBitmap(imagen);
        }
    }






}