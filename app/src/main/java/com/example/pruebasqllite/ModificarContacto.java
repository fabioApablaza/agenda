package com.example.pruebasqllite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pruebasqllite.Entidades.Contacto;
import com.example.pruebasqllite.Utilidades.Utilidades;

import java.util.Calendar;

public class ModificarContacto extends AppCompatActivity {
    EditText cajaNombre,cajaApellido,cajaTelefono,cajaDireccion,cajaFechaNacimiento;
    private int dia,mes, año;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_contacto);
        cajaNombre=(EditText)findViewById(R.id.cajaNombre);
        cajaApellido=(EditText)findViewById(R.id.cajaApellido);
        cajaTelefono=(EditText)findViewById(R.id.cajaTelefono);
        cajaDireccion=(EditText)findViewById(R.id.cajaDireccionPostal);
        cajaFechaNacimiento=(EditText)findViewById(R.id.cajaFechaNac);
        Bundle contactoEnviado=getIntent().getExtras();
        Contacto contacto=null;
        if(contactoEnviado!=null){
            contacto=(Contacto)contactoEnviado.getSerializable("contacto");
            cajaNombre.setText(contacto.getNombre());
            cajaApellido.setText(contacto.getApellido());
            cajaTelefono.setText(contacto.getTelefono());
            cajaDireccion.setText(contacto.getDireccionPostal());
            cajaFechaNacimiento.setText(contacto.getFechaNac());
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
    public void modificar(View view){
        Intent info= new Intent(ModificarContacto.this,InformacionContacto.class);
        Bundle bundle=new Bundle();
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this,"bd_contactos",null,1);
        Bundle contactoEnviado=getIntent().getExtras();
        Contacto contacto=(Contacto)contactoEnviado.getSerializable("contacto");
        SQLiteDatabase db=conn.getWritableDatabase();
        String update="UPDATE "+ Utilidades.TABLA_CONTACTO+" SET "+Utilidades.NOMBRE+" = '"+ cajaNombre.getText().toString()+"' , "+Utilidades.APELLIDO+" = '"+cajaApellido.getText().toString()+"' , "+Utilidades.TELEFONO+" = '"+cajaTelefono.getText().toString()+"' , "+Utilidades.DIRECCIONPOSTAL+" = '"+cajaDireccion.getText().toString()+"' , "+Utilidades.FECHANAC+" = '"+cajaFechaNacimiento.getText().toString()+"' WHERE "+Utilidades.TELEFONO+" LIKE '"+contacto.getTelefono()+"' ;";
        db.execSQL(update);
        db.close();
        contacto.setNombre(cajaNombre.getText().toString());
        contacto.setApellido(cajaApellido.getText().toString());
        contacto.setTelefono(cajaTelefono.getText().toString());
        contacto.setDireccionPostal(cajaDireccion.getText().toString());
        contacto.setFechaNac(cajaFechaNacimiento.getText().toString());
        bundle.putSerializable("contacto",contacto);
        info.putExtras(bundle);
        startActivity(info);
        finish();
    }

}
