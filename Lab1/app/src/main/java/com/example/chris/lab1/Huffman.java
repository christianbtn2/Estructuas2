package com.example.chris.lab1;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {
    private static final int ALPHABET_SIZE = 256;

    public HuffmanResultado compresion (final String dato){
        final int [] frec = TablaFrec(dato);
        final Nodo raiz = Arbol(frec);
        final Map<Character,String> tabla = TablaCod(raiz);

        return new HuffmanResultado(GenerarCompresion(dato,tabla), raiz);

    }


    private String GenerarCompresion(String dato, Map<Character, String> tabla) {
        final StringBuilder cadena = new StringBuilder();
        for (final char caracter: dato.toCharArray()){
            cadena.append(tabla.get(caracter));
        }
        return cadena.toString();
    }

    public static Map<Character,String> TablaCod (final Nodo raiz){
        final Map<Character,String> tabla = new HashMap<>();

        TablaCodImpl(raiz,"",tabla);

        return  tabla;
    }

    private static void TablaCodImpl(Nodo nodo, String s, Map<Character, String> tabla) {
        if (!nodo.EsHoja()){
            TablaCodImpl(nodo.izquierda, s + '0', tabla);
            TablaCodImpl(nodo.derecha, s + '1' , tabla);
        }else{
            tabla.put(nodo.caracter,s);
        }
    }


    public static int [] TablaFrec (final String dato){
        final int[] frec = new int[ALPHABET_SIZE];
        for(final char character: dato.toCharArray()){
            frec[character] ++;
        }
        return frec;
    }

    static class Nodo implements Comparable<Nodo>{
        private  final char caracter;
        private final  int frecuencia;
        private  final Nodo izquierda;
        private  final Nodo derecha;

        private Nodo(final char caracter, final int frecuencia, final Nodo izquierda, final Nodo derecha) {

            this.caracter = caracter;
            this.frecuencia = frecuencia;
            this.izquierda = izquierda;
            this.derecha = derecha;
        }

        public Nodo(char c, int i, Nodo der) {

            frecuencia = 0;
            caracter = 0;
            izquierda = null;
            derecha = null;
        }


        boolean EsHoja(){
            return  this.izquierda == null && this.derecha == null;
        }


        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public int compareTo(final Nodo o) {
            final int compresionfrecuencia = Integer.compare(this.frecuencia, o.frecuencia);
            if (compresionfrecuencia != 0){
                return compresionfrecuencia;
            }
            return Integer.compare(this.caracter,o.caracter);
        }
    }

    public static Nodo Arbol(int [] frec){
        final PriorityQueue<Nodo> pq = new PriorityQueue<>();
        for (char i = 0; i< ALPHABET_SIZE; i++){
            if (frec[i] > 0){
                pq.add(new Nodo(i, frec[i], null,null));
            }
        }

        if (pq.size() == 1){
            pq.add(new Nodo('\0', 1, null,null));
        }

        while (pq.size() > 1){
            final Nodo iz = pq.poll();
            final Nodo der = pq.poll();
            final Nodo padre = new Nodo('\0',iz.frecuencia + der.frecuencia, iz , der );
            pq.add(padre);
        }
        return pq.poll();
    }



    public String descompresion(final HuffmanResultado resultado) throws IllegalAccessException {
        final StringBuilder resultadoB = new StringBuilder();

        Nodo current = resultado.getRoot();
        int i =0;
        while (i < resultado.getEncodedData().length()){
            while (!current.EsHoja()){
                char bit = resultado.getEncodedData().charAt(i);
                if (bit == '1'){
                    current = current.derecha;
                }else if(bit == '0'){
                    current = current.izquierda;
                }else {
                    throw new IllegalAccessException("bit invalido" + bit);
                }
                i++;
            }
            resultadoB.append(current.caracter);
            current = resultado.getRoot();
        }
        return resultadoB.toString();
    }



    static class HuffmanResultado implements Serializable {
        Nodo raiz;
        String codigo;


        HuffmanResultado(final String codigo , final Nodo raiz){

            this.codigo = codigo;
            this.raiz = raiz;
        }

        public Nodo getRoot(){
            return this.raiz;
        }

        public String getEncodedData(){
            return this.codigo;
        }


    }








}
