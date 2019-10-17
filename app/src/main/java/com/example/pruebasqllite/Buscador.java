package com.example.pruebasqllite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pruebasqllite.Entidades.Contacto;
import com.example.pruebasqllite.Utilidades.Utilidades;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Buscador extends AppCompatActivity {
    ListView listaCon;
    EditText cajaBuscar;
    ArrayList<String> listainfor;
    ArrayList<Contacto> listacontac;
    ConexionSQLiteHelper conex;
    FloatingActionButton bAgregar,bListaCump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setCustomView(R.layout.actionbarbuscador);
        listaCon = (ListView) findViewById(R.id.listaPersonas);
        cajaBuscar= (EditText) actionBar.getCustomView().findViewById(R.id.cajaBusqueda);
        conex = new ConexionSQLiteHelper(getApplicationContext(), "bd_contactos", null, 1);
        bAgregar= (FloatingActionButton) findViewById(R.id.fab);
        bListaCump= (FloatingActionButton) findViewById(R.id.fab2);
        listar();
        final ArrayAdapter adaptador = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listainfor);
        listaCon.setAdapter(adaptador);

        listaCon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacto contacto = listacontac.get(position);
                Intent info = new Intent(Buscador.this, InformacionContacto.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("contacto", contacto);
                info.putExtras(bundle);
                startActivity(info);
            }
        });
        cajaBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adaptador.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        bListaCump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonListarContactoCumple();
            }
        });
        bAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAgregarContacto();
            }
        });
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM| ActionBar.DISPLAY_SHOW_HOME);
    }

    private void listar() {
        SQLiteDatabase db = conex.getReadableDatabase();
        Contacto cont;
        listacontac = new ArrayList<Contacto>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_CONTACTO, null);
        while (cursor.moveToNext()) {
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
        listainfor = new ArrayList<String>();
        for (int i = 0; i < listacontac.size(); i++) {
            listainfor.add((i+1)+ " - " + listacontac.get(i).getNombre() + " " + listacontac.get(i).getApellido());
        }
    }
    public void buttonAgregarContacto(){
        //Metodo de ejecucion del activity Agregar Contacto
        Intent agregarContacto= new Intent(this,agregar_contacto.class);
        startActivity(agregarContacto);
    }
    public void buttonListarContactoCumple(){
        //Metodo de ejecucion del activity Buscar Contacto
        Intent listaCOntactosCumple= new Intent(this,ContactosCumpleanio.class);
        startActivity(listaCOntactosCumple);
    }
}
