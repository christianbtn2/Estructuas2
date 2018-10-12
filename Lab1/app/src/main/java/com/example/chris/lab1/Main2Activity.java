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
import static com.example.chris.lab1.MainActivity.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import  java.io.IOException;
import java.io.FileReader;

import static com.example.chris.lab1.Huffman.*;

public class Main2Activity extends AppCompatActivity {

    public static final int PERMISSION_REQUEST_STORAGE = 100;
    public static final int READ_REQUEST_CODE = 42;
    public static String cadena = "";
    public static  String nombre = "";
    public static  String direccion = "";

    TextView Salida;
    Button btnAnterior;
    Button btnBuscar;
    Button btnComprimir;
    TextView Cifrado;
    TextView Desencriptado;
    String[] arreglo;
    Boolean arreglolleno;
    Button btnBuscar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String []{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }


        btnAnterior = (Button)findViewById(R.id.btnAnterior);
        btnBuscar = (Button)findViewById(R.id.btnBuscar);
        btnComprimir = (Button)findViewById(R.id.btnComprimir);
        final Boolean arrelolleno = false;
        Salida = (TextView)findViewById(R.id.txtSalida);
        btnBuscar2 = (Button)findViewById(R.id.btnBuscar2);

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent anteriro = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(anteriro);
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }


        btnComprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Huffman h = new Huffman();
                final HuffmanResultado res = h.compresion(cadena);
                String comprimido = res.codigo;

                String descomprimido = "";
                try {
                    descomprimido = h.descompresion(res);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                ObtenerNombre();

                String filename = "Compresion("+nombre+").huff";


                try {
                    saveText(filename,comprimido);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });






        btnBuscar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusquedaArchivo2();
            }
        });


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusquedaArchivo();
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

    //Lee el contenido del archivo
    private String readText(String salida){
        File file = new File(salida);
        StringBuilder texto = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String linea;
            while ((linea = br.readLine()) != null){
                texto.append(linea);
                texto.append("\n");

            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return  texto.toString();
    }

    private void BusquedaArchivo2(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("huff/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
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
                Salida.setText(direccion);
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


    public void ObtenerNombre(){
        String [] ruta = direccion.split("/");
        nombre = ruta[ruta.length-1];
    }





}
