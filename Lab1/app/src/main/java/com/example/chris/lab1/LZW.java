package com.example.chris.lab1;

import java.util.HashMap;
import java.util.Map;

public class LZW {

    public static String Compresion(String entrada){


        String codificado = "";
        Map<String,Integer> diccionarioAlreves = new HashMap<>();
        Map<Integer,String> diccionario = new HashMap<>();

        diccionario = ObtenerDiccionarioBase(entrada);
        diccionarioAlreves = ObtenerDiccionarioBaseAlreves(entrada);
        int z =0;

        for(int i = 0; i < entrada.length(); i++){
            String caracterActual = String.valueOf(entrada.charAt(i));
            String temporal = "";
            String caracterSiguiente;

            while(diccionario.containsValue(caracterActual)){
                if(z + 1 < entrada.length()){
                    z++;
                    temporal = caracterActual;
                    caracterSiguiente = String.valueOf(entrada.charAt(z));
                    caracterActual = temporal + caracterSiguiente;
                    if(z - i > 1){
                        i = z - 1;
                    }
                }else{
                    break;
                }

            }

            if(!diccionario.containsValue(caracterActual)){
                codificado = codificado + diccionarioAlreves.get(temporal) + "-";
                diccionario.put(diccionario.size() + 1, caracterActual);
                diccionarioAlreves.put(caracterActual,diccionarioAlreves.size() + 1 );
            }else{
                codificado = codificado + diccionarioAlreves.get(caracterActual);
            }
        }
        double numeroCaracteres = 0;
        int exponente = 0;
        while(numeroCaracteres < diccionario.size()){
            exponente++;
            numeroCaracteres = Math.pow(2, exponente);
        }


        String [] binario = codificado.split("-");

        for(int i = 0; i < binario.length; i++){
            String temporal = Integer.toBinaryString(Integer.parseInt(binario[i]));
            while(temporal.length() < exponente){
                temporal = "0" + temporal;
            }
            binario[i] = temporal;
        }

        String cadenaBinarios = "";

        for(int i = 0; i < binario.length; i++){
            cadenaBinarios += binario[i];
        }

        while(cadenaBinarios.length() % 8 == 0){
            cadenaBinarios += "0";
        }

        String byts = "";
        int contadorDigitos = 0;
        for(int i = 0; i < cadenaBinarios.length(); i++){
            contadorDigitos ++;
            if(contadorDigitos == 9){
                contadorDigitos = 0;
                if(i + 1 < cadenaBinarios.length()){
                    byts += "-";
                }
            }else{
                byts += cadenaBinarios.charAt(i);
            }
        }
        binario = byts.split("-");
        String toAscii = "";
        for(int i = 0; i < binario.length ; i++){
            int temporal = Integer.parseInt(binario[i], 2);
            toAscii += String.valueOf((char) temporal);
        }



        return toAscii;
    }

    public static String Descompresion(String ascii, Map<Integer, String> base, int numeroCaracteres){
        String cadenaBinarios = "";
        for(int i = 0; i < ascii.length(); i++){
            char c = ascii.charAt(i);
            int decimal = (int)c;
            cadenaBinarios += decimal;
            if(i != ascii.length() - 1){
                cadenaBinarios += "-";
            }
        }

        String[] binarios = cadenaBinarios.split("-");
        String [] ArregloBinarios = new String[binarios.length];
        cadenaBinarios = "";
        for(int i = 0; i < binarios.length; i++){
            ArregloBinarios[i] = Integer.toBinaryString(Integer.parseInt(binarios[i]));
            while(ArregloBinarios[i].length() != 8){
                ArregloBinarios[i] = "0" + ArregloBinarios[i];
            }
            cadenaBinarios += ArregloBinarios[i];
        }

        String byts = "";
        int contadorDigitos = 0;
        for(int i = 0; i < cadenaBinarios.length(); i++){
            contadorDigitos ++;
            if(contadorDigitos == numeroCaracteres + 1){
                contadorDigitos = 0;
                if(i + 1 < cadenaBinarios.length()){
                    byts += "-";
                }
            }else{
                byts += cadenaBinarios.charAt(i);
            }
        }

        String[] Enteros = byts.split("-");

        for(int i = 0; i <  Enteros.length; i++){
            Enteros[i] = String.valueOf(Integer.parseInt(Enteros[i],2));
        }

        // aqui va la cadena de numeros  separada por -
        //Error los numeros no son los que realmente tiene que tener
        //Descomprime mal

        String cadena = "";


        String PrevioActual = "";
        String caracterPrevio = "";
        String [] ArregloCadena = cadena.split("-");
        String descomprimido = "";

        for(int i = 0; i < ArregloCadena.length ; i++){
            String caracterActual = ArregloCadena[i];
            if(i == 0){
                caracterPrevio = "";
            }else{
                caracterPrevio =  ArregloCadena[i - 1];
            }

            if(caracterPrevio != ""){
                PrevioActual = base.get(Integer.parseInt(caracterPrevio)) + base.get(Integer.parseInt(caracterActual)).charAt(0);

                int z = i;
                while(base.containsValue(PrevioActual)){
                    if(z < ArregloCadena.length){
                        z++;
                        String tempActual = ArregloCadena[z];
                        String tempPrevio = PrevioActual;
                        PrevioActual = base.get(base.get(Integer.parseInt(caracterPrevio)) + base.get(Integer.parseInt(caracterActual)).charAt(0));
                    }

                }
                descomprimido += base.get(Integer.parseInt(caracterActual));
                base.put(base.size() + 1, PrevioActual);
            }else{
                descomprimido += base.get(Integer.parseInt(caracterActual));
            }

        }
        return descomprimido;
    }




    public static Map<Integer, String> ObtenerDiccionarioBase(String cadena){
        Map<Integer, String> Dbase = new HashMap<>();

        for(int i = 0; i < cadena.length(); i++){
            char c = cadena.charAt(i);
            if(!Dbase.containsValue(String.valueOf(c))){
                Dbase.put(i + 1, String.valueOf(c));
            }
        }

        return Dbase;
    }
    public static Map<String, Integer> ObtenerDiccionarioBaseAlreves(String cadena){
        Map<String, Integer> Dbase = new HashMap<>();

        for(int i = 0; i < cadena.length(); i++){
            char c = cadena.charAt(i);
            if(!Dbase.containsKey(String.valueOf(c))){
                Dbase.put(String.valueOf(c), i + 1);
            }
        }

        return Dbase;
    }

    public String ObtenerNombre(String cadena){
        String nombre = "";

        String [] separar = cadena.split("/");
        nombre = separar[separar.length - 1];
        return nombre;
    }






}
