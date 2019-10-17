package com.example.pruebasqllite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pruebasqllite.Utilidades.Utilidades;

import java.time.Year;
import java.util.Calendar;

public class agregar_contacto extends AppCompatActivity {
    private int dia,mes, año;
    EditText cajaNombre,cajaApellido,cajaTelefono,cajaDireccion,cajaFechaNacimiento;
    Button bFecha;
    String nombre,apellido,telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);

        cajaNombre=(EditText)findViewById(R.id.cajaNombre);
        cajaApellido=(EditText)findViewById(R.id.cajaApellido);
        cajaTelefono=(EditText)findViewById(R.id.cajaTelefono);
        cajaDireccion=(EditText)findViewById(R.id.cajaDireccionPostal);
        cajaFechaNacimiento=(EditText)findViewById(R.id.cajaFechaNac);
        bFecha=(Button) findViewById(R.id.buttonFecha);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    public void altaContacto(View v){
        //Metodo del boton agregar Contacto
        String insert;
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this,"bd_contactos",null,1);
        nombre=cajaNombre.getText().toString();
        apellido=cajaApellido.getText().toString();
        telefono=cajaTelefono.getText().toString();
        if(nombre.isEmpty()||apellido.isEmpty()||telefono.isEmpty()){
            if(nombre.isEmpty()){
                Toast.makeText(this, "ERROR: el nombre es necesario", Toast.LENGTH_SHORT).show();
            }
            if(apellido.isEmpty()){
                Toast.makeText(this, "ERROR: el apellido es necesario", Toast.LENGTH_SHORT).show();
            }
            if(telefono.isEmpty()){
                Toast.makeText(this, "ERROR: el número de telefono es necesario", Toast.LENGTH_SHORT).show();
            }
        }else {
            SQLiteDatabase db = conn.getWritableDatabase();

            insert="INSERT INTO "+Utilidades.TABLA_CONTACTO+" ("+Utilidades.NOMBRE+","+Utilidades.APELLIDO+","+Utilidades.TELEFONO+","+Utilidades.DIRECCIONPOSTAL+","+Utilidades.FECHANAC+") VALUES ('"+nombre+"', '"+apellido+"', '"+telefono+"', '"+cajaDireccion.getText().toString()+"','"+cajaFechaNacimiento.getText().toString()+"');";
            db.execSQL(insert);
            db.close();
            Toast.makeText(this, "Contacto agregado", Toast.LENGTH_SHORT).show();
        }
    }
    public void cambiarFecha(View v){
        //Metodo del boton fecha
        final Calendar calendario= Calendar.getInstance();
        dia= calendario.get(Calendar.DAY_OF_MONTH);
        mes= calendario.get(Calendar.MONTH);
        año=calendario.get(Calendar.YEAR);
        DatePickerDialog datepickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cajaFechaNacimiento.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        }
        ,dia,mes,año);
        datepickerDialog.show();
    }

}
