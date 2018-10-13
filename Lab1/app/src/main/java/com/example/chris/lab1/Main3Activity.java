package com.example.chris.lab1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.chris.lab1.Main2Activity.READ_REQUEST_CODE;
import static com.example.chris.lab1.MainActivity.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import  java.io.IOException;
import java.io.FileReader;
import java.util.Map;

import static com.example.chris.lab1.Main2Activity.PERMISSION_REQUEST_STORAGE;


public class Main3Activity extends AppCompatActivity {

    Button btnBuscar;
    Button btnBuscar2;
    Button btnComprimir;
    Button btnLista;
    Button btnDescomprimir;
    TextView txtDireccion;
    TextView txtDireccion2;
    public static String direccion = "";
    public static String nombre = "";
    public static String cadena = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btnBuscar = (Button)findViewById(R.id.btnBuscar);
        btnBuscar2 = (Button)findViewById(R.id.btnBuscar2);
        btnComprimir = (Button)findViewById(R.id.btnComprimir);
        btnDescomprimir = (Button)findViewById(R.id.btnDescomprimir);
        txtDireccion = (TextView)findViewById(R.id.txtDireccion);
        txtDireccion2 = (TextView)findViewById(R.id.txtDireccion2);
        btnLista = (Button)findViewById(R.id.btnLista);


        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ListaComprimidos = new Intent(Main3Activity.this , MainActivity.class);
                startActivity(ListaComprimidos);
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String []{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }


        btnBuscar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusquedaArchivo();
            }
        });


        btnComprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LZW lz = new LZW();
                String texto = "";
                nombre = lz.ObtenerNombre(direccion);
                texto = lz.Compresion(cadena) + "\n";
                Map<Integer,String> base = lz.ObtenerDiccionarioBase(cadena);
                for (int i = 0; i <= base.size() ; i++){
                    texto += i + "->" + base.get(i) + "\n";
                }
                String filename = "CompresionLZW("+ nombre +")";

                try {
                    saveText(filename,texto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnDescomprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_STORAGE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"permiso concedido",Toast.LENGTH_SHORT);
            }else{
                Toast.makeText(this,"permiso concedido",Toast.LENGTH_SHORT);
                finish();
            }

        }
    }

    private String readText(String salida){
        File file = new File(salida);
        StringBuilder texto = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String linea;
            while ((linea = br.readLine()) != null){
                texto.append(linea);


            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return  texto.toString();
    }




    //Selecciona el archivo
    private void BusquedaArchivo(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if (data != null){
                Uri uri = data.getData();
                direccion = uri.getPath();
                direccion = direccion.substring(direccion.indexOf(":")+ 1);
                Toast.makeText(this,""+ direccion,Toast.LENGTH_SHORT).show();
                txtDireccion.setText(direccion);
                cadena = readText(direccion);
            }
        }
    }




    private void saveText(String filename, String contenido) throws IOException {
        String fileName = filename + ".txt";
        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), fileName);

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(contenido.getBytes());
        fos.close();
        Toast.makeText(this, "Guardado",Toast.LENGTH_SHORT).show();

    }






}
