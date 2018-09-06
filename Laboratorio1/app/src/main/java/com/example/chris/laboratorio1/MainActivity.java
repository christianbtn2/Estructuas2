
package com.example.chris.laboratorio1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnAgregar;
    Button btnOrdenarNombreA;
    Button btnOrdenarNombreD;
    Button btnOrdenarDuracionA;
    Button btnOrdenarDuracionD;
    ListView lista;
    ListView Lista2;
    EditText txtCancion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Cancion cancion = new Cancion();

        btnOrdenarDuracionD = (Button) findViewById(R.id.btnOrdenarDuracionD);
        btnOrdenarDuracionA = (Button) findViewById(R.id.btnOrdenarDuracionA);
        btnOrdenarNombreD = (Button) findViewById(R.id.btnOrdenarNombreD);
        btnOrdenarNombreA = (Button) findViewById(R.id.btnOrdenarNombreA);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        Lista2 = (ListView) findViewById(R.id.Lista2);
        lista = (ListView) findViewById(R.id.lista);
        txtCancion = (EditText) findViewById(R.id.txtCancion);

        cancion.Agregar();

        final ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                cancion.adaptador);

        lista.setAdapter(adaptador2);
        ;

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancion.AgregarCancion(txtCancion.getText().toString());
                final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,
                        cancion.mostrarPlaylist);
                Lista2.setAdapter(adaptador);
            }
        });




        btnOrdenarNombreA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancion.OrdenarNombreA();
                final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,
                        cancion.mostrarPlaylist);
                Lista2.setAdapter(adaptador);
            }
        });

        btnOrdenarDuracionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancion.OrdenarDuracionD();
                final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,
                        cancion.mostrarPlaylist);
                Lista2.setAdapter(adaptador);
            }
        });








    }
}
