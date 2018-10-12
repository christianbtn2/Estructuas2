package com.example.chris.lab1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Map;

import static com.example.chris.lab1.Main2Activity.*;

public class MainActivity extends AppCompatActivity {

    Button btnCargaArchivo;
    Button btnLzw;
    ListView Lista;

    public static String [] adaptador = new String[50];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Lista = (ListView)findViewById(R.id.Lista);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adaptador.add(nombre);
        Lista.setAdapter(adaptador);

        int i = 0;
        adaptador.add(nombre);
        Lista.setAdapter(adaptador);

        btnCargaArchivo = (Button)findViewById(R.id.btnCargaArchivo);

        btnCargaArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent siguiente = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(siguiente);
            }
        });

        btnLzw = (Button)findViewById(R.id.btnLzw);

        btnLzw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lzw = new Intent(MainActivity.this , Main3Activity.class);
                startActivity(lzw);
            }
        });


    }

}
