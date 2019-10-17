package com.example.pruebasqllite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pruebasqllite.Entidades.Contacto;
import com.example.pruebasqllite.Utilidades.Utilidades;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ContactosCumpleanio extends AppCompatActivity {
    ListView listaCon;
    ArrayList<String> listainfor;
    ArrayList<Contacto> listacontac;
    ConexionSQLiteHelper conex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        listaCon=(ListView) findViewById(R.id.listaPersonas);
        conex= new ConexionSQLiteHelper(getApplicationContext(),"bd_contactos",null,1);
        listar();
        ArrayAdapter adaptador= new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,listainfor);
        listaCon.setAdapter(adaptador);

        listaCon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacto contacto=listacontac.get(position);
                Intent info= new Intent(ContactosCumpleanio.this,InformacionContacto.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("contacto",contacto);
                info.putExtras(bundle);
                startActivity(info);
                finish();
            }
        });
    }

    private void listar() {
        SQLiteDatabase db= conex.getReadableDatabase();
        Contacto cont;
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("dd/MM");
        String fechaHoy=format.format(date);
        listacontac=new ArrayList<Contacto>();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_CONTACTO+" WHERE "+Utilidades.FECHANAC+" LIKE '"+fechaHoy+"%';",null);
        while(cursor.moveToNext()){
            cont = new Contacto();
            cont.setNombre(cursor.getString(1));
            cont.setApellido(cursor.getString(2));
            cont.setTelefono(cursor.getString(3));
            cont.setDireccionPostal(cursor.getString(4));
            cont.setFechaNac(cursor.getString(5));
            listacontac.add(cont);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listainfor= new ArrayList<String>();
        for(int i=0; i<listacontac.size();i++){
            listainfor.add((i+1)+" - "+listacontac.get(i).getNombre()+" "+listacontac.get(i).getApellido());
        }
    }
}
