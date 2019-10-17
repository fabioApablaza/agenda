package com.example.pruebasqllite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pruebasqllite.Entidades.Contacto;
import com.example.pruebasqllite.Utilidades.Utilidades;

public class InformacionContacto extends AppCompatActivity {
    private static final int REQUEST_CALL=1;
    TextView cajaNom,cajaApe,cajaTel,cajaDireccion,cajaFecha;
    ImageButton botonLlamar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_contacto);
        cajaNom=(TextView) findViewById(R.id.cajaNombre);
        cajaApe=(TextView) findViewById(R.id.cajaApellido);
        cajaTel=(TextView) findViewById(R.id.cajaTelefono);
        cajaDireccion=(TextView) findViewById(R.id.cajaDireccion);
        cajaFecha=(TextView) findViewById(R.id.cajaFecha);
        botonLlamar=(ImageButton) findViewById(R.id.botonLlamar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle contactoEnviado=getIntent().getExtras();
        Contacto contacto=null;
        if(contactoEnviado!=null){
            contacto=(Contacto)contactoEnviado.getSerializable("contacto");
            cajaNom.setText(contacto.getNombre());
            cajaApe.setText(contacto.getApellido());
            cajaTel.setText(contacto.getTelefono());
            cajaDireccion.setText(contacto.getDireccionPostal());
            cajaFecha.setText(contacto.getFechaNac());
        }
        botonLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamar();
            }
        });


    }



    public void eliminarContacto(View view){
        AlertDialog.Builder unaBuild= new AlertDialog.Builder(this);
        unaBuild.setMessage("¿Desea elminar este contacto?");
        unaBuild.setTitle("Alerta de eliminacion");
        unaBuild.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        unaBuild.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminar();
                finish();

            }
        }
        );
        AlertDialog dialog= unaBuild.create();
        dialog.show();

    }
    public void eliminar (){
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this,"bd_contactos",null,1);
        SQLiteDatabase db= conn.getWritableDatabase();
        String delete= "DELETE FROM "+Utilidades.TABLA_CONTACTO+" WHERE "+Utilidades.TELEFONO+" = '"+cajaTel.getText().toString()+"' ;";
        db.execSQL(delete);
        db.close();
        finish();
        Intent mainIntent = new Intent().setClass(
                InformacionContacto.this, Buscador.class);
        startActivity(mainIntent);
    }
    public void  botonModificarContacto(View view){
        Intent modif= new Intent(this, ModificarContacto.class);
        Bundle contactoEnviado=getIntent().getExtras();
        if(contactoEnviado!=null){
            modif.putExtras(contactoEnviado);
            startActivity(modif);
        }
        finish();


    }
    public void llamar (){
        String numTel=cajaTel.getText().toString();
        if(numTel.trim().length()>0){
            if(ActivityCompat.checkSelfPermission(InformacionContacto.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(InformacionContacto.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }
            else{
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+numTel)));
            }
        }else{

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CALL){}
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                llamar();
            }
            else{
                Toast.makeText(InformacionContacto.this,"Permiso denegado",Toast.LENGTH_SHORT).show();
            }
    }
}
